package com.artoo.personalfinance.services;

import java.util.Date;
import java.util.Locale;
import persistantData.DatabaseHelper;
import model.Transaction;
import com.artoo.personalfinance.broadcastReceiver.SMSReceiver;
import com.google.gson.Gson;
import android.app.IntentService;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

public class SMSFilteringService extends IntentService {

	public SMSFilteringService(String name) {
		super(name);
	}

	public SMSFilteringService() {
		super("SMSFilteringService");
	}

	public static final String TRANSACTION_KEY = "tran";
	public static final String BROADCAST_ACTION_KEY = "com.artoo.personalfinance.services.inernal_sms_broadcast";
	private static final String[] EXPENSE_PATTERNS_AMOUNT_BEFORE = {
			"has been withdrawn", "have been withdrawn", "was withdrawn",
			"is withdrawn", "is debited", "was debited", "has been debited" };

	private static final String[] EXPENSE_PATTERNS_AMOUNT_AFTER = {
			"have withdrawn", "paid", "paid an amount of" };

	private static final String[] INCOME_PATTERNS_AMOUNT_BEFORE = {
			"has been depositted", "have been depositted", "was depositted",
			"is depositted", "was credited", "is credited", "has been credited" };

	private static final String[] INCOME_PATTERNS_AMOUNT_AFTER = {
			"have dopsitted", "have withdrawn an amount of", "received",
			"received an amount of" };

	/**
	 * class performs message filtering and transaction extraction on a worker
	 * thread
	 * 
	 * @author nayan
	 *
	 */
	class msgFilterAsync extends AsyncTask<Void, Void, Void> {
		String msgBody, senderName;
		boolean isTransaction = false;
		Transaction transaction;

		msgFilterAsync(String messageBody, String senderName) {
			this.msgBody = messageBody;
			this.senderName = senderName;
		}

		@Override
		protected Void doInBackground(Void... params) {

			String[] msgBodyChunks = msgBody
					.replaceAll("(\\r|\\n|\\t|\\s)+", " ")
					.toLowerCase(Locale.ENGLISH).split(" ");

			StringBuilder utilityStringBUilder = new StringBuilder();
			boolean shouldCheck = false;

			for (int i = 0; i < msgBodyChunks.length; i++) {

				// ------------ EXPENSE filtering block -------------------//
				String chunk = msgBodyChunks[i];

				for (int expBeforeCount = 0; expBeforeCount < EXPENSE_PATTERNS_AMOUNT_BEFORE.length; expBeforeCount++) {
					if (EXPENSE_PATTERNS_AMOUNT_BEFORE[expBeforeCount]
							.startsWith(chunk)) {
						if (i > 0) {
							utilityStringBUilder.setLength(0);
							utilityStringBUilder.append(chunk);
							for (int x = i + 1; x < msgBodyChunks.length; x++) {
								utilityStringBUilder.append(" ").append(
										msgBodyChunks[x]);
								if (utilityStringBUilder.length() >= EXPENSE_PATTERNS_AMOUNT_BEFORE[expBeforeCount]
										.length()) {
									if (utilityStringBUilder
											.toString()
											.equals(EXPENSE_PATTERNS_AMOUNT_BEFORE[expBeforeCount])) {
										shouldCheck = true;

									}
									break;
								}
							}
							if (shouldCheck) {
								utilityStringBUilder.setLength(0);
								if (i > 1
										&& (msgBodyChunks[i - 2]
												.equalsIgnoreCase("inr")
												|| msgBodyChunks[i - 2]
														.equalsIgnoreCase("rs")
												|| msgBodyChunks[i - 2]
														.equalsIgnoreCase("rs.")
												|| msgBodyChunks[i - 2]
														.equals('\u20A8' + "") || msgBodyChunks[i - 2]
												.equalsIgnoreCase('\u20A8' + "."))) {
									utilityStringBUilder
											.append(msgBodyChunks[i - 1]);
								} else if (i > 0) {
									if (msgBodyChunks[i - 1].startsWith("inr")) {
										utilityStringBUilder
												.append(msgBodyChunks[i - 1]
														.substring(
																3,
																msgBodyChunks[i - 1]
																		.length()));
									} else if (msgBodyChunks[i - 1]
											.startsWith("rs.")) {
										utilityStringBUilder
												.append(msgBodyChunks[i - 1]
														.substring(
																3,
																msgBodyChunks[i - 1]
																		.length()));
									} else if (msgBodyChunks[i - 1]
											.startsWith("rs")) {
										utilityStringBUilder
												.append(msgBodyChunks[i - 1]
														.substring(
																2,
																msgBodyChunks[i - 1]
																		.length()));
									} else if (msgBodyChunks[i - 1]
											.startsWith(('\u20A8' + "."))) {
										utilityStringBUilder
												.append(msgBodyChunks[i - 1]
														.substring(2));

									} else if (msgBodyChunks[i - 1]
											.startsWith(('\u20A8' + ""))) {
										utilityStringBUilder
												.append(msgBodyChunks[i - 1]
														.substring(1));
									} else {
										utilityStringBUilder
												.append(msgBodyChunks[i - 1]);
									}
								}
								try {
									float amount = Float
											.parseFloat(utilityStringBUilder
													.toString());
									transaction = new Transaction(amount,
											Transaction.EXPENSE, senderName,
											new Date());
									transaction.setSource(senderName.toUpperCase(Locale.ENGLISH)
											+ "\n" + msgBody);
									DatabaseHelper dbHelper = new DatabaseHelper(
											getBaseContext());
									dbHelper.addTransaction(transaction);
									isTransaction = true;
									return null;
								} catch (Exception e) {
								}
							}

						}
					}
				}

				for (int expAfterCount = 0; expAfterCount < EXPENSE_PATTERNS_AMOUNT_AFTER.length; expAfterCount++) {
					if (EXPENSE_PATTERNS_AMOUNT_AFTER[expAfterCount]
							.startsWith(chunk)) {

						if (i > 1) {
							shouldCheck = false;
							try {
								utilityStringBUilder.setLength(0);
								utilityStringBUilder.append(chunk);
								int x;
								for (x = i + 1; x < msgBodyChunks.length; x++) {
									utilityStringBUilder.append(" ").append(
											msgBodyChunks[x]);
									if (utilityStringBUilder.length() >= EXPENSE_PATTERNS_AMOUNT_AFTER[expAfterCount]
											.length()) {
										if (utilityStringBUilder
												.toString()
												.equals(EXPENSE_PATTERNS_AMOUNT_AFTER[expAfterCount])) {
											shouldCheck = true;

										}
										break;
									}
								}
								if (shouldCheck) {
									utilityStringBUilder.setLength(0);
									if (i > 1
											&& (msgBodyChunks[x + 1]
													.equalsIgnoreCase("inr")
													|| msgBodyChunks[x + 1]
															.equalsIgnoreCase("rs")
													|| msgBodyChunks[x + 1]
															.equalsIgnoreCase("rs.")
													|| msgBodyChunks[x + 1]
															.equals('\u20A8' + "") || msgBodyChunks[x + 1]
													.equalsIgnoreCase('\u20A8' + "."))) {
										utilityStringBUilder
												.append(msgBodyChunks[x + 2]);
									} else if (i > 0) {
										if (msgBodyChunks[x + 1]
												.startsWith("inr")) {
											utilityStringBUilder
													.append(msgBodyChunks[x + 1]
															.substring(
																	3,
																	msgBodyChunks[x + 1]
																			.length()));
										} else if (msgBodyChunks[x + 1]
												.startsWith("rs.")) {
											utilityStringBUilder
													.append(msgBodyChunks[x + 1]
															.substring(
																	3,
																	msgBodyChunks[x + 1]
																			.length()));
										} else if (msgBodyChunks[x + 1]
												.startsWith("rs")) {
											utilityStringBUilder
													.append(msgBodyChunks[x + 1]
															.substring(
																	2,
																	msgBodyChunks[x + 1]
																			.length()));
										} else {
											utilityStringBUilder
													.append(msgBodyChunks[x + 1]);
										}
									}

									float amount = Float
											.parseFloat(utilityStringBUilder
													.toString());
									transaction = new Transaction(amount,
											Transaction.EXPENSE, senderName,
											new Date());
									transaction.setSource(senderName.toUpperCase(Locale.ENGLISH)
											+ "\n" + msgBody);
									DatabaseHelper dbHelper = new DatabaseHelper(
											getBaseContext());
									dbHelper.addTransaction(transaction);
									isTransaction = true;
									return null;
								}

							} catch (Exception e) {

							}
						}
					}
				}

				// ------------------- INCOME filtering
				// block--------------------//
				for (int incBeforeCount = 0; incBeforeCount < INCOME_PATTERNS_AMOUNT_BEFORE.length; incBeforeCount++) {
					if (INCOME_PATTERNS_AMOUNT_BEFORE[incBeforeCount]
							.startsWith(chunk)) {

						if (i > 0) {
							shouldCheck = false;
							utilityStringBUilder.setLength(0);
							utilityStringBUilder.append(chunk);
							for (int x = i + 1; x < msgBodyChunks.length; x++) {
								utilityStringBUilder.append(" ").append(
										msgBodyChunks[x]);
								if (utilityStringBUilder.length() >= INCOME_PATTERNS_AMOUNT_BEFORE[incBeforeCount]
										.length()) {
									if (utilityStringBUilder
											.toString()
											.equals(INCOME_PATTERNS_AMOUNT_BEFORE[incBeforeCount])) {
										shouldCheck = true;

									}
									break;
								}
							}
							if (shouldCheck) {
								utilityStringBUilder.setLength(0);
								if (i > 1
										&& (msgBodyChunks[i - 2]
												.equalsIgnoreCase("inr")
												|| msgBodyChunks[i - 2]
														.equalsIgnoreCase("rs")
												|| msgBodyChunks[i - 2]
														.equalsIgnoreCase("rs.")
												|| msgBodyChunks[i - 2]
														.equals('\u20A8' + "") || msgBodyChunks[i - 2]
												.equalsIgnoreCase('\u20A8' + "."))) {
									utilityStringBUilder
											.append(msgBodyChunks[i - 1]);
								} else if (i > 0) {
									if (msgBodyChunks[i - 1].startsWith("inr")) {
										utilityStringBUilder
												.append(msgBodyChunks[i - 1]
														.substring(
																3,
																msgBodyChunks[i - 1]
																		.length()));
									} else if (msgBodyChunks[i - 1]
											.startsWith("rs.")) {
										utilityStringBUilder
												.append(msgBodyChunks[i - 1]
														.substring(
																3,
																msgBodyChunks[i - 1]
																		.length()));
									} else if (msgBodyChunks[i - 1]
											.startsWith("rs")) {
										utilityStringBUilder
												.append(msgBodyChunks[i - 1]
														.substring(
																2,
																msgBodyChunks[i - 1]
																		.length()));
									} else {
										utilityStringBUilder
												.append(msgBodyChunks[i - 1]);
									}
								}

								try {
									float amount = Float
											.parseFloat(msgBodyChunks[i - 1]);
									transaction = new Transaction(amount,
											Transaction.INCOME, senderName,
											new Date());
									DatabaseHelper dbHelper = new DatabaseHelper(
											getBaseContext());
									transaction.setSource(senderName.toUpperCase(Locale.ENGLISH)
											+ "\n" + msgBody);
									dbHelper.addTransaction(transaction);
									isTransaction = true;
									return null;
								} catch (Exception e) {

								}
							}
						}
					}
				}

				for (int incAfterCount = 0; incAfterCount < INCOME_PATTERNS_AMOUNT_AFTER.length; incAfterCount++) {
					if (INCOME_PATTERNS_AMOUNT_AFTER[incAfterCount]
							.startsWith(chunk)) {

						if (i > 1) {
							shouldCheck = false;
							try {
								utilityStringBUilder.setLength(0);
								utilityStringBUilder.append(chunk);
								int x;
								for (x = i + 1; x < msgBodyChunks.length; x++) {
									utilityStringBUilder.append(" ").append(
											msgBodyChunks[x]);
									if (utilityStringBUilder.length() >= INCOME_PATTERNS_AMOUNT_AFTER[incAfterCount]
											.length()) {
										if (utilityStringBUilder
												.toString()
												.equals(INCOME_PATTERNS_AMOUNT_AFTER[incAfterCount])) {
											shouldCheck = true;
										}
										break;
									}
								}
								if (shouldCheck) {
									utilityStringBUilder.setLength(0);
									if (i > 1
											&& (msgBodyChunks[x + 1]
													.equalsIgnoreCase("inr")
													|| msgBodyChunks[x + 1]
															.equalsIgnoreCase("rs")
													|| msgBodyChunks[x + 1]
															.equalsIgnoreCase("rs.")
													|| msgBodyChunks[x + 1]
															.equals('\u20A8' + "") || msgBodyChunks[x + 1]
													.equalsIgnoreCase('\u20A8' + "."))) {
										utilityStringBUilder
												.append(msgBodyChunks[x + 2]);
									} else if (i > 0) {
										if (msgBodyChunks[x + 1]
												.startsWith("inr")) {
											utilityStringBUilder
													.append(msgBodyChunks[x + 1]
															.substring(
																	3,
																	msgBodyChunks[x + 1]
																			.length()));
										} else if (msgBodyChunks[x + 1]
												.startsWith("rs.")) {
											utilityStringBUilder
													.append(msgBodyChunks[x + 1]
															.substring(
																	3,
																	msgBodyChunks[x + 1]
																			.length()));
										} else if (msgBodyChunks[x + 1]
												.startsWith("rs")) {
											utilityStringBUilder
													.append(msgBodyChunks[x + 1]
															.substring(
																	2,
																	msgBodyChunks[x + 1]
																			.length()));
										} else {
											utilityStringBUilder
													.append(msgBodyChunks[x + 1]);
										}
									}
									float amount = Float
											.parseFloat(msgBodyChunks[i + 1]);
									transaction = new Transaction(amount,
											Transaction.INCOME, senderName,
											new Date());
									DatabaseHelper dbHelper = new DatabaseHelper(
											getBaseContext());
									transaction.setSource(senderName.toUpperCase(Locale.ENGLISH)
											+ "\n" + msgBody);
									dbHelper.addTransaction(transaction);
									isTransaction = true;
									return null;
								}
							} catch (Exception e) {
							}
						}
					}
				}
			}
			isTransaction = false;
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if (isTransaction) {
				Intent intent = new Intent();
				intent.setAction(BROADCAST_ACTION_KEY);
				String transactionString = new Gson().toJson(transaction);
				intent.putExtra(TRANSACTION_KEY, transactionString);
				sendBroadcast(intent);
			}
		}
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		if (intent != null) {

			Bundle dataBundle = intent.getExtras();
			String senderName = dataBundle
					.getString(SMSReceiver.SENDER_NAME_KEY);
			String messageBody = dataBundle
					.getString(SMSReceiver.MESSAGE_BODY_KEY);
			new msgFilterAsync(messageBody, senderName).execute();
			System.out.println("message from service- " + messageBody);
		}
	}
}
