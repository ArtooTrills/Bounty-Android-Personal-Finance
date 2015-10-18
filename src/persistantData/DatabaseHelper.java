package persistantData;

import java.util.ArrayList;
import java.util.List;

import utills.CommonUtility;
import model.IgnoreItem;
import model.Transaction;
import model.TransactionCategory;
import model.TransactionHistory;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * database helper class to perform all the database operations
 * 
 * @author nayan
 *
 */
public class DatabaseHelper extends SQLiteOpenHelper {

	private final static String DB_NAME = "finance_db";

	private static final int version = 1;

	private static final String TRANSACTION_TABLE_NAME = "money_transaction";

	private static final String IGNORE_LIST_TABLE_NAME = "ignore_list";

	private static final String[] IGNORE_LIST_COLUMNS = { "id", "source",
			"ban_date" };

	private static final String TRANSACTION_HISTORY_TABLE_NAME = "transaction_history";

	private static final String[] TRANSACTION_HISTORY_COLUMN_NAMES = { "id",
			"tran_id", "modification_date", "message" };

	private static final String[] TRANSACTION_TABLE_COLUMNS = { "id", "amount",
			"type", "description", "date", "source", "transaction_type" };

	private static final String TRANSACTION_CATEGORIES_TABLE_NAME = "transaction_category";

	private static final String TRANSACTION_CATEGORIES_COLUMNS[] = {
			"transaction_type", "category_name" };

	private static final List<TransactionCategory> CATEGORIES;

	private static final List<IgnoreItem> IGNORE_ITEMS = new ArrayList<IgnoreItem>();
	static {
		CATEGORIES = new ArrayList<TransactionCategory>();

		// adding default categories

		// -----------------expense-----------//
		CATEGORIES
				.add(new TransactionCategory(Transaction.EXPENSE, "UNDEFINED"));
		CATEGORIES.add(new TransactionCategory(Transaction.EXPENSE, "BILLS"));
		CATEGORIES
				.add(new TransactionCategory(Transaction.EXPENSE, "CLOTHING"));
		CATEGORIES.add(new TransactionCategory(Transaction.EXPENSE,
				"COMMUNICATIONS"));
		CATEGORIES.add(new TransactionCategory(Transaction.EXPENSE,
				"CREDIT CARD"));
		CATEGORIES.add(new TransactionCategory(Transaction.EXPENSE, "MOVIES"));
		CATEGORIES.add(new TransactionCategory(Transaction.EXPENSE,
				"HOUSE HOLD"));
		CATEGORIES.add(new TransactionCategory(Transaction.EXPENSE,
				"HOUSE RENT"));
		CATEGORIES.add(new TransactionCategory(Transaction.EXPENSE, "FUEL"));
		CATEGORIES.add(new TransactionCategory(Transaction.EXPENSE,
				"VEHICLE SERVICE"));
		CATEGORIES.add(new TransactionCategory(Transaction.EXPENSE,
				"TRANSPORTATION"));
		CATEGORIES
				.add(new TransactionCategory(Transaction.EXPENSE, "INTERNET"));
		CATEGORIES.add(new TransactionCategory(Transaction.EXPENSE, "PARTY"));
		CATEGORIES.add(new TransactionCategory(Transaction.EXPENSE, "OUTING"));
		CATEGORIES.add(new TransactionCategory(Transaction.EXPENSE,
				"INCOME TAX"));

		// ---------income--------------//
		CATEGORIES
				.add(new TransactionCategory(Transaction.INCOME, "UNDEFINED"));
		CATEGORIES.add(new TransactionCategory(Transaction.INCOME, "SALARY"));
		CATEGORIES.add(new TransactionCategory(Transaction.INCOME, "GIFT"));
		CATEGORIES.add(new TransactionCategory(Transaction.INCOME,
				"LOAN RETURN"));
		CATEGORIES.add(new TransactionCategory(Transaction.INCOME,
				"PAYING GUEST RENT"));

	}

	public DatabaseHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	public DatabaseHelper(Context context) {
		super(context, DB_NAME, null, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		// utility stringbuilder for building table script of various tables
		StringBuilder tblScript = new StringBuilder();

		// creating transaction table
		tblScript.append("CREATE TABLE " + TRANSACTION_TABLE_NAME + "("
				+ TRANSACTION_TABLE_COLUMNS[0]
				+ " integer primary key AUTOINCREMENT");
		for (int i = 1; i < TRANSACTION_TABLE_COLUMNS.length; i++) {
			tblScript.append(", " + TRANSACTION_TABLE_COLUMNS[i]);
		}
		tblScript.append(");");
		String transactionTableScript = tblScript.toString();

		tblScript.setLength(0);

		// creating ignore list table
		tblScript
				.append("CREATE TABLE " + IGNORE_LIST_TABLE_NAME + "("
						+ IGNORE_LIST_COLUMNS[0]
						+ " integer primary key AUTOINCREMENT");
		for (int i = 1; i < IGNORE_LIST_COLUMNS.length; i++) {
			tblScript.append(", " + IGNORE_LIST_COLUMNS[i]);
		}
		tblScript.append(");");
		String ignoreListTableScript = tblScript.toString();

		tblScript.setLength(0);

		// creating history table
		tblScript.append("CREATE TABLE " + TRANSACTION_HISTORY_TABLE_NAME + "("
				+ TRANSACTION_HISTORY_COLUMN_NAMES[0]
				+ " integer primary key AUTOINCREMENT");
		for (int i = 1; i < TRANSACTION_HISTORY_COLUMN_NAMES.length; i++) {
			tblScript.append(", " + TRANSACTION_HISTORY_COLUMN_NAMES[i]);
		}
		tblScript.append(");");
		String transactionHistoryTableScript = tblScript.toString();

		tblScript.setLength(0);

		// creating category_table
		tblScript.append("CREATE TABLE " + TRANSACTION_CATEGORIES_TABLE_NAME
				+ "(");
		for (int i = 0; i < TRANSACTION_CATEGORIES_COLUMNS.length; i++) {
			tblScript.append(TRANSACTION_CATEGORIES_COLUMNS[i]);
			if (i < TRANSACTION_CATEGORIES_COLUMNS.length - 1) {
				tblScript.append(',');
			}
		}
		tblScript.append(");");

		String categoryTableScript = tblScript.toString();

		// creating tables
		db.execSQL(transactionTableScript);
		db.execSQL(ignoreListTableScript);
		db.execSQL(transactionHistoryTableScript);
		db.execSQL(categoryTableScript);

	}

	/**
	 * delete a transaction with history
	 * 
	 * @param transactionId
	 * @return
	 */
	public boolean deleteTransaction(int transactionId) {
		boolean returnValue = false;
		SQLiteDatabase db = getWritableDatabase();
		db.beginTransaction();
		try {
			String[] selectionArgs = { transactionId + "" };

			db.delete(TRANSACTION_HISTORY_TABLE_NAME,
					TRANSACTION_HISTORY_COLUMN_NAMES[1] + "=?", selectionArgs);
			db.delete(TRANSACTION_TABLE_NAME, TRANSACTION_TABLE_COLUMNS[0]
					+ "=?", selectionArgs);
			db.setTransactionSuccessful();
			returnValue = true;
		} catch (Exception e) {
			returnValue = false;
			System.out.println("Exception in deleting task- " + e.getMessage());

		} finally {
			db.endTransaction();
			db.close();
		}
		return returnValue;
	}

	public List<IgnoreItem> getIgnoreItemList() {
		if (IGNORE_ITEMS.isEmpty()) {

			SQLiteDatabase db = getReadableDatabase();
			try {
				Cursor dbCursor = db.query(true, IGNORE_LIST_TABLE_NAME,
						IGNORE_LIST_COLUMNS, null, null, null, null, null,
						null, null);
				if (dbCursor.moveToFirst()) {

					do {
						IgnoreItem ignoreItem = new IgnoreItem();
						ignoreItem
								.setDate(CommonUtility.DATE_FORMATTER_WITHOUT_TIME
										.parse(dbCursor.getString(2)));
						ignoreItem.setSource(dbCursor.getString(1));
						IGNORE_ITEMS.add(ignoreItem);
					} while (dbCursor.moveToNext());
				}
			} catch (Exception e) {

			}
		}
		return IGNORE_ITEMS;
	}

	/**
	 * return list of transaction history of corresponding to a particular
	 * transaction
	 * 
	 * @param id
	 *            , id of the transaction
	 * @return
	 */
	public List<TransactionHistory> getTransactionHistory(int id) {
		List<TransactionHistory> histories = null;
		SQLiteDatabase db = getReadableDatabase();
		try {
			Cursor dbCursor = db.query(true, TRANSACTION_HISTORY_TABLE_NAME,
					TRANSACTION_HISTORY_COLUMN_NAMES,
					TRANSACTION_HISTORY_COLUMN_NAMES[1] + "=?",
					new String[] { id + "" }, null, null, null, null, null);
			if (dbCursor.moveToFirst()) {
				histories = new ArrayList<TransactionHistory>();
				do {
					TransactionHistory transactionHistory = new TransactionHistory(
							Integer.parseInt(dbCursor.getString(1)),
							dbCursor.getString(3),
							CommonUtility.DATE_FORMATTER_WITHOUT_TIME
									.parse(dbCursor.getString(2)));
					transactionHistory.setId(dbCursor.getInt(0));

					histories.add(transactionHistory);
				} while (dbCursor.moveToNext());
			}
		} catch (Exception e) {
			System.out.println(" exception in - " + e);
		}
		return histories;
	}

	/**
	 * return all categories
	 * 
	 * @return
	 */
	public List<TransactionCategory> getAllCategory() {
		List<TransactionCategory> categories = new ArrayList<TransactionCategory>();
		categories.addAll(CATEGORIES);
		SQLiteDatabase db = getReadableDatabase();
		try {
			Cursor dbCursor = db.query(true, TRANSACTION_CATEGORIES_TABLE_NAME,
					TRANSACTION_CATEGORIES_COLUMNS, null, null, null, null,
					null, null, null);
			if (dbCursor.moveToFirst()) {
				do {
					TransactionCategory category = new TransactionCategory(
							Integer.parseInt(dbCursor.getString(0)),
							dbCursor.getString(1));
					categories.add(category);
				} while (dbCursor.moveToNext());
			}
		} catch (Exception e) {
			System.out.println("exception in - " + e);
		}
		return categories;
	}

	/**
	 * return list of transaction that have been added in reverse order
	 * 
	 * @return
	 */
	public List<Transaction> getAllTransaction() {
		List<Transaction> transactions = null;
		SQLiteDatabase db = getReadableDatabase();
		try {
			Cursor dbCursor = db.query(true, TRANSACTION_TABLE_NAME,
					TRANSACTION_TABLE_COLUMNS, null, null, null, null, null,
					null, null);
			if (dbCursor != null && dbCursor.moveToLast()) {
				transactions = new ArrayList<Transaction>();
				do {
					Transaction transaction = new Transaction();
					transactions.add(transaction);
					transaction.setId(dbCursor.getInt(0));
					transaction.setAmount(Float.parseFloat(dbCursor
							.getString(1)));
					transaction.setDesc(dbCursor.getString(3));
					transaction
							.setType(Integer.parseInt(dbCursor.getString(2)));
					try {
						transaction
								.setTransactionDate(CommonUtility.DATE_FORMATTER_WITHOUT_TIME
										.parse(dbCursor.getString(4)));
					} catch (Exception e) {
						System.out.println(e);
					}
					transaction.setSource(dbCursor.getString(5));

					transaction.setCategory(new TransactionCategory(transaction
							.getType(), dbCursor.getString(6)));
				} while (dbCursor.moveToPrevious());
			}
		} catch (Exception e) {
		} finally {
			db.close();
		}
		return transactions;
	}

	/**
	 * add a new transaction into the transaction table
	 * 
	 * @param transaction
	 * @return
	 */
	public long addTransaction(Transaction transaction) {
		long returnValue = 0;
		SQLiteDatabase db = getWritableDatabase();
		System.out.println("transaction added in start");
		try {
			ContentValues values = new ContentValues();
			values.put(TRANSACTION_TABLE_COLUMNS[1], transaction.getAmount()
					+ "");
			values.put(TRANSACTION_TABLE_COLUMNS[2], transaction.getType() + "");
			values.put(TRANSACTION_TABLE_COLUMNS[3], transaction.getDesc());
			values.put(TRANSACTION_TABLE_COLUMNS[4],
					CommonUtility.DATE_FORMATTER_WITHOUT_TIME
							.format(transaction.getTransactionDate()));
			values.put(TRANSACTION_TABLE_COLUMNS[5], transaction.getSource());
			values.put(TRANSACTION_TABLE_COLUMNS[6], transaction.getCategory()
					.getCategoryName());
			returnValue = db.insert(TRANSACTION_TABLE_NAME, null, values);
			System.out.println("transaction added- " + transaction.getAmount());
		} catch (Exception e) {
			returnValue = -1;
		} finally {
			if (db != null && db.isOpen())
				db.close();
		}
		return returnValue;
	}

	/**
	 * saves a user defined transaction category into database
	 * 
	 * @param category
	 * @return
	 */
	public long addTransactionCategory(TransactionCategory category) {
		long returnValue = 0;
		SQLiteDatabase db = getWritableDatabase();
		try {
			ContentValues values = new ContentValues();
			values.put(TRANSACTION_CATEGORIES_COLUMNS[0],
					category.getTransactionType() + "");
			values.put(TRANSACTION_CATEGORIES_COLUMNS[1],
					category.getCategoryName());
			returnValue = db.insert(TRANSACTION_CATEGORIES_TABLE_NAME, null,
					values);
		} catch (Exception e) {
			returnValue = -1;
		} finally {
			if (db != null && db.isOpen())
				db.close();
		}
		return returnValue;
	}

	/**
	 * saves changes user has made in a transaction
	 * 
	 * @param transactionHistory
	 * @return
	 */
	public long addTransactionHistory(TransactionHistory transactionHistory,
			Transaction transaction) {
		long returnValue = 0;
		SQLiteDatabase db = getWritableDatabase();
		db.beginTransaction();
		try {

			ContentValues values = new ContentValues();
			values.put(TRANSACTION_TABLE_COLUMNS[1], transaction.getAmount()
					+ "");
			values.put(TRANSACTION_TABLE_COLUMNS[2], transaction.getType() + "");
			values.put(TRANSACTION_TABLE_COLUMNS[3], transaction.getDesc());
			values.put(TRANSACTION_TABLE_COLUMNS[4],
					CommonUtility.DATE_FORMATTER_WITHOUT_TIME
							.format(transaction.getTransactionDate()));
			values.put(TRANSACTION_TABLE_COLUMNS[5], transaction.getSource());
			values.put(TRANSACTION_TABLE_COLUMNS[6], transaction.getCategory()
					.getCategoryName());
			// updating transaction information
			returnValue = db.update(TRANSACTION_TABLE_NAME, values,
					TRANSACTION_TABLE_COLUMNS[0] + "=?",
					new String[] { transaction.getId() + "" });

			// saving a note of this history
			values = new ContentValues();
			values.put(TRANSACTION_HISTORY_COLUMN_NAMES[1],
					transactionHistory.getTransactionId() + "");
			values.put(TRANSACTION_HISTORY_COLUMN_NAMES[2],
					CommonUtility.DATE_FORMATTER_WITHOUT_TIME
							.format(transactionHistory.getModificationDate()));
			values.put(TRANSACTION_HISTORY_COLUMN_NAMES[3],
					transactionHistory.getMessage());
			returnValue = db.insert(TRANSACTION_HISTORY_TABLE_NAME, null,
					values);
			db.setTransactionSuccessful();
			if (returnValue == 0)
				returnValue = 1;
		} catch (Exception e) {
			returnValue = -1;
		} finally {
			db.endTransaction();
			db.close();
		}
		return returnValue;
	}

	/**
	 * adds a new source into ignore list
	 * 
	 * @param ignoreItems
	 * @return -1 if not successfully inserted, otherwise >-1
	 */
	public long addIgonreItem(IgnoreItem ignoreItems) {
		long returnValue = 0;
		if (isIgnoreItemNotExisting(ignoreItems.getSource())) {

			SQLiteDatabase db = getWritableDatabase();

			try {
				ContentValues values = new ContentValues();
				values.put(IGNORE_LIST_COLUMNS[1], ignoreItems.getSource());
				values.put(IGNORE_LIST_COLUMNS[2],
						CommonUtility.DATE_FORMATTER_WITHOUT_TIME
								.format(ignoreItems.getDate()));
				returnValue = db.insert(IGNORE_LIST_TABLE_NAME, null, values);
				IGNORE_ITEMS.add(ignoreItems);
			} catch (Exception e) {
				returnValue = -1;
			} finally {
				if (db != null && db.isOpen())
					db.close();
			}
		}
		return returnValue;
	}

	/**
	 * checks if new source is already into the list or not
	 * 
	 * @param source
	 * @return true if not, false otherwise
	 */
	public boolean isIgnoreItemNotExisting(String source) {
		SQLiteDatabase db = getReadableDatabase();
		Cursor dbCursor = db.query(true, IGNORE_LIST_TABLE_NAME,
				IGNORE_LIST_COLUMNS, IGNORE_LIST_COLUMNS[1] + " =?",
				new String[] { source }, null, null, null, null, null);
		return dbCursor.getCount() == 0;
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TRANSACTION_TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + IGNORE_LIST_TABLE_NAME);
		onCreate(db);
	}

}
