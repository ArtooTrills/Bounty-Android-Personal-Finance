package com.artoo.personalfinance.services;

import java.util.Date;
import java.util.Locale;

import com.google.gson.Gson;

import static com.artoo.personalfinance.services.SMSFilteringService.*;
import model.Transaction;
import persistantData.DatabaseHelper;
import utills.CommonUtility;
import android.app.IntentService;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;

public class ArchiveSMSReaderService extends IntentService {

	private static final String[] REQUIRED_SMS_FIELDS = { "address", "body",
			"date" };
	public static final String IS_TRANSACTION = "isTran";

	public ArchiveSMSReaderService() {
		super("ArchiveSMSReaderService");
	}

	public ArchiveSMSReaderService(String name) {
		super(name);
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		ContentResolver contentResolver = getContentResolver();
		Uri message = Uri.parse("content://sms/inbox");
		Cursor smsCursor = contentResolver.query(message, REQUIRED_SMS_FIELDS,
				null, null, null);
		if (smsCursor.moveToFirst()) {
			do {
				try {
					SMS sms = new SMS();
					sms.setAddress(smsCursor.getString(0));
					sms.setBody(smsCursor.getString(1));
					sms.setDate(smsCursor.getString(2));
					filterMessage(sms);
				} catch (Exception e) {

				}
			} while (smsCursor.moveToNext());
		}
	}

	// ------------------------filtering messages-----------------//
	private void filterMessage(SMS sms) {
		String[] msgBodyChunks = sms.body.replaceAll("(\\r|\\n|\\t|\\s)+", " ")
				.toLowerCase(Locale.ENGLISH).split(" ");
		String msgBody = sms.body.replaceAll("(\\r|\\n|\\t|\\s)+", " ")
				.toLowerCase(Locale.ENGLISH);
		boolean isTransaction = false;
		Transaction transaction = null;

		StringBuilder utilityStringBUilder = new StringBuilder();
		boolean shouldCheck = false;

		for (int i = 0; i < msgBodyChunks.length; i++) {

			// ------------ EXPENSE filtering block -------------------//
			String chunk = msgBodyChunks[i];

			for (int expBeforeCount = 0; expBeforeCount < SMSFilteringService.EXPENSE_PATTERNS_AMOUNT_BEFORE.length; expBeforeCount++) {
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
										Transaction.EXPENSE, sms.address,
										sms.getDate());
								transaction.setSource(sms.address
										.toUpperCase(Locale.ENGLISH)
										+ "\n"
										+ msgBody);
								transaction.setSender(sms.address);
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
										.parseFloat(utilityStringBUilder
												.toString());
								transaction = new Transaction(amount,
										Transaction.EXPENSE, sms.address,
										sms.getDate());
								transaction.setSource(sms.address
										.toUpperCase(Locale.ENGLISH)
										+ "\n"
										+ msgBody);
								transaction.setSender(sms.address);
								DatabaseHelper dbHelper = new DatabaseHelper(
										getBaseContext());
								dbHelper.addTransaction(transaction);
								isTransaction = true;
							}

						} catch (Exception e) {

						}
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
										Transaction.INCOME, sms.address,
										sms.getDate());
								transaction.setSender(sms.address);
								DatabaseHelper dbHelper = new DatabaseHelper(
										getBaseContext());
								transaction.setSource(sms.address
										.toUpperCase(Locale.ENGLISH)
										+ "\n"
										+ msgBody);
								dbHelper.addTransaction(transaction);
								isTransaction = true;
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
										.parseFloat(msgBodyChunks[i + 1]);
								transaction = new Transaction(amount,
										Transaction.INCOME, sms.address,
										sms.getDate());
								transaction.setSender(sms.address);
								DatabaseHelper dbHelper = new DatabaseHelper(
										getBaseContext());
								transaction.setSource(sms.address
										.toUpperCase(Locale.ENGLISH)
										+ "\n"
										+ msgBody);
								dbHelper.addTransaction(transaction);
								isTransaction = true;
							}
						} catch (Exception e) {
						}
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
			intent.putExtra(IS_TRANSACTION, "true");
			sendBroadcast(intent);
		}
	}

}

/**
 * class holds sms
 * 
 * @author nayan
 *
 */
class SMS {
	String address;
	String body;
	Date date;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		try {
			this.date = date;
		} catch (Exception e) {
			this.date = new Date();
		}
	}

	public void setDate(String date) {
		try {
			long x = Long.parseLong(date);
			setDate(CommonUtility.millisToDate(x));
		} catch (Exception e) {
			setDate(CommonUtility.DATE_FORMATTER_WITHOUT_TIME.format(date));
		}
	}
}
