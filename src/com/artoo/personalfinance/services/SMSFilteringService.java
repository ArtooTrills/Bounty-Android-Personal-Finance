package com.artoo.personalfinance.services;

import java.util.Date;
import java.util.Locale;

import persistantData.DatabaseHelper;
import model.Transaction;

import com.artoo.personalfinance.broadcastReceiver.SMSReceiver;
import com.google.gson.Gson;

import android.app.IntentService;
import android.content.Intent;
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

	static final String[] EXPENSE_PATTERNS_AMOUNT_BEFORE = { "withdrawn", "dr",
			"dr.", "debited", "has been withdrawn", "have been withdrawn",
			"was withdrawn", "is withdrawn", "is debited", "was debited",
			"has been debited" };

	static final String[] EXPENSE_PATTERNS_AMOUNT_AFTER = { "withdrawn",
			"debited", "debited with", "debited for", "debited by",
			"withdrawn with", "dr", "dr.", "dr with", "dr for", "dr by",
			"dr. with", "dr. for", "dr. by", "withdrawn for", "paid",
			"paid an amount of", "withdrawn an amount of" };

	static final String[] INCOME_PATTERNS_AMOUNT_BEFORE = { "depositted", "cr",
			"cr.", "credited", "has been depositted", "have been depositted",
			"was depositted", "is depositted", "was credited", "is credited",
			"has been credited" };

	static final String[] INCOME_PATTERNS_AMOUNT_AFTER = { "have dopsitted",
			"credited", "credited with", "credited by", "credited for", "cr",
			"cr.", "cr with", "cr by", " cr for", "cr. with", "cr. by",
			" cr. for", "depositted an amount of", "received",
			"received an amount of" };

	static final String[] EXPENSE_CATEGORY_FOOD_TERMS = { "hotel",
			"restaurant", "pizza", "pizzeria", "lunch", "dinner", "breakfast",
			"dominos", "faasos", "zomato", "foodpanda", "mcdonalds" };

	static final String[] EXPENSE_CATEGORY_COMMUNICATION_TERMS = { "recharge",
			"rchrg", "talktime" };

	static final String FILTERED_CATEGORY_FOOD = "FOOD",
			FILTERED_CATEGORY_COMMUNICATION = "COMMUNICATIONS";

	/**
	 * class performs message filtering and transaction extraction on a worker
	 * thread
	 * 
	 * @author nayan
	 *
	 */
	private void filterMessage(String msgBody, String senderName) {
		boolean isTransaction = false;
		Transaction transaction = null;

		msgBody = msgBody.toLowerCase(Locale.ENGLISH);
		String[] msgBodyChunks = msgBody.replaceAll("(\\r|\\n|\\t|\\s)+", " ")
				.split(" ");

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
						if (utilityStringBUilder.toString().equals(
								EXPENSE_PATTERNS_AMOUNT_BEFORE[expBeforeCount])) {
							shouldCheck = true;
						} else {
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
												.toString().replace(",", ""));
								transaction = new Transaction(amount,
										Transaction.EXPENSE, senderName,
										new Date());
								transaction.setSource(senderName
										.toUpperCase(Locale.ENGLISH)
										+ "\n"
										+ msgBody);
								for (String categoryTerms : EXPENSE_CATEGORY_FOOD_TERMS) {
									if (msgBody.contains(categoryTerms)) {
										transaction.getCategory()
												.setCategoryName(
														FILTERED_CATEGORY_FOOD);
										break;
									}
								}
								if (transaction.getCategory().getCategoryName()
										.equals("NONE")) {
									for (String categoryTerms : EXPENSE_CATEGORY_COMMUNICATION_TERMS) {
										if (msgBody.contains(categoryTerms)) {
											transaction
													.getCategory()
													.setCategoryName(
															FILTERED_CATEGORY_COMMUNICATION);
											break;
										}
									}
								}
								transaction.setSender(senderName);
								DatabaseHelper dbHelper = new DatabaseHelper(
										getBaseContext());
								dbHelper.addTransaction(transaction);
								isTransaction = true;
								break;
							} catch (Exception e) {
							}
						}

					}
				}

			}
			if (isTransaction)
				break;
			for (int expAfterCount = 0; expAfterCount < EXPENSE_PATTERNS_AMOUNT_AFTER.length; expAfterCount++) {
				if (EXPENSE_PATTERNS_AMOUNT_AFTER[expAfterCount]
						.startsWith(chunk)) {
					shouldCheck = false;
					try {
						utilityStringBUilder.setLength(0);
						utilityStringBUilder.append(chunk);
						int x = i;
						if (utilityStringBUilder.toString().equals(
								EXPENSE_PATTERNS_AMOUNT_AFTER[expAfterCount])) {
							shouldCheck = true;

						} else {
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
						}
						if (shouldCheck) {
							utilityStringBUilder.setLength(0);
							if ((x + 1) < msgBodyChunks.length
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
							} else if (x + 1 < msgBodyChunks.length) {
								if (msgBodyChunks[x + 1].startsWith("inr")) {
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
									.parseFloat(utilityStringBUilder.toString()
											.replace(",", ""));
							transaction = new Transaction(amount,
									Transaction.EXPENSE, senderName, new Date());
							transaction.setSource(senderName
									.toUpperCase(Locale.ENGLISH)
									+ "\n"
									+ msgBody);
							transaction.setSender(senderName);
							for (String categoryTerms : EXPENSE_CATEGORY_FOOD_TERMS) {
								if (msgBody.contains(categoryTerms)) {
									transaction.getCategory().setCategoryName(
											FILTERED_CATEGORY_FOOD);
									break;
								}
							}
							if (transaction.getCategory().getCategoryName()
									.equals("NONE")) {
								for (String categoryTerms : EXPENSE_CATEGORY_COMMUNICATION_TERMS) {
									if (msgBody.contains(categoryTerms)) {
										transaction
												.getCategory()
												.setCategoryName(
														FILTERED_CATEGORY_COMMUNICATION);
										break;
									}
								}
							}
							DatabaseHelper dbHelper = new DatabaseHelper(
									getBaseContext());
							dbHelper.addTransaction(transaction);
							isTransaction = true;
							break;
						}

					} catch (Exception e) {

					}

				}
			}
			if (isTransaction)
				break;
			// ------------------- INCOME filtering
			// block--------------------//
			for (int incBeforeCount = 0; incBeforeCount < INCOME_PATTERNS_AMOUNT_BEFORE.length; incBeforeCount++) {
				if (INCOME_PATTERNS_AMOUNT_BEFORE[incBeforeCount]
						.startsWith(chunk)) {

					if (i > 0) {
						shouldCheck = false;
						utilityStringBUilder.setLength(0);
						utilityStringBUilder.append(chunk);
						if (utilityStringBUilder.toString().equals(
								INCOME_PATTERNS_AMOUNT_BEFORE[incBeforeCount])) {
							shouldCheck = true;

						} else {
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
										.parseFloat(utilityStringBUilder
												.toString().replace(",", ""));
								int tranType = Transaction.INCOME;

								// check if sms is a mobile recharge message
								if (INCOME_PATTERNS_AMOUNT_BEFORE[incBeforeCount]
										.contains("credited")
										&& msgBody.contains("recharge")) {
									tranType = Transaction.EXPENSE;

								}

								transaction = new Transaction(amount, tranType,
										senderName, new Date());

								if (tranType == Transaction.EXPENSE) {
									transaction.getCategory().setCategoryName(
											FILTERED_CATEGORY_COMMUNICATION);
								}

								transaction.setSender(senderName);
								DatabaseHelper dbHelper = new DatabaseHelper(
										getBaseContext());
								transaction.setSource(senderName
										.toUpperCase(Locale.ENGLISH)
										+ "\n"
										+ msgBody);
								dbHelper.addTransaction(transaction);
								isTransaction = true;
								break;
							} catch (Exception e) {

							}
						}
					}
				}
			}
			if (isTransaction)
				break;
			for (int incAfterCount = 0; incAfterCount < INCOME_PATTERNS_AMOUNT_AFTER.length; incAfterCount++) {
				if (INCOME_PATTERNS_AMOUNT_AFTER[incAfterCount]
						.startsWith(chunk)) {

					shouldCheck = false;
					try {
						utilityStringBUilder.setLength(0);
						utilityStringBUilder.append(chunk);
						int x = i;
						if (utilityStringBUilder.toString().equals(
								INCOME_PATTERNS_AMOUNT_AFTER[incAfterCount])) {
							shouldCheck = true;
						} else {
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
						}
						if (shouldCheck) {
							utilityStringBUilder.setLength(0);
							if ((x + 1) < msgBodyChunks.length
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
							} else if (x + 1 < msgBodyChunks.length) {
								if (msgBodyChunks[x + 1].startsWith("inr")) {
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
									.parseFloat(utilityStringBUilder.toString()
											.replace(",", ""));
							int tranType = Transaction.INCOME;
							// check if message is a mobile recharge message
							if (INCOME_PATTERNS_AMOUNT_BEFORE[incAfterCount]
									.contains("credited")
									&& msgBody.contains("recharge")) {
								tranType = Transaction.EXPENSE;
							}
							transaction = new Transaction(amount, tranType,
									senderName, new Date());
							if (tranType == Transaction.EXPENSE)
								transaction.getCategory().setCategoryName(
										FILTERED_CATEGORY_COMMUNICATION);
							transaction.setSender(senderName);
							DatabaseHelper dbHelper = new DatabaseHelper(
									getBaseContext());
							transaction.setSource(senderName
									.toUpperCase(Locale.ENGLISH)
									+ "\n"
									+ msgBody);
							dbHelper.addTransaction(transaction);
							isTransaction = true;
							break;
						}
					} catch (Exception e) {
					}
				}

			}
			if (isTransaction)
				break;
		}

		if (isTransaction && transaction != null) {
			Intent intent = new Intent();
			intent.setAction(BROADCAST_ACTION_KEY);
			String transactionString = new Gson().toJson(transaction);
			intent.putExtra(TRANSACTION_KEY, transactionString);
			sendBroadcast(intent);
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
			filterMessage(messageBody, senderName);
			System.out.println("message from service- " + messageBody);
		}
	}
}
