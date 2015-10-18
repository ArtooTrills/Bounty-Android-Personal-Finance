package model;

import java.util.Date;
import java.util.List;

public class Transaction implements Cloneable {
	public static final int EXPENSE = 0, INCOME = 1;
	private int id;
	private float amount;
	private int type;
	private String desc;
	private Date transactionDate;
	private String source;
	private String sender;

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	TransactionCategory category;
	private List<TransactionHistory> transactionHistories;

	public Transaction(Transaction t) {
		super();

		this.amount = t.amount;
		this.type = t.type;
		this.desc = t.desc;
		this.transactionDate = t.transactionDate;
		this.source = t.source;
		this.sender = t.sender;
	}

	public Transaction(float amount, int type, String desc, Date transactionDate) {
		super();
		sender = "";
		this.amount = amount;
		this.type = type;
		this.desc = desc;
		this.transactionDate = transactionDate;
		source = "";
		this.category = new TransactionCategory(type);
	}

	public Transaction(int id, float amount, int type, String desc,
			Date transactionDate, String source, TransactionCategory category) {
		super();
		this.id = id;
		this.amount = amount;
		this.type = type;
		this.desc = desc;
		this.transactionDate = transactionDate;
		this.source = source;
		this.category = category;
	}

	public Transaction(int id, float amount, int type, String desc,
			Date transactionDate, String source) {
		super();
		this.id = id;
		this.amount = amount;
		this.type = type;
		this.desc = desc;
		this.transactionDate = transactionDate;
		this.source = source;
	}

	public Transaction() {
		super();
		source = "";
	}

	public List<TransactionHistory> getTransactionHistories() {
		return transactionHistories;
	}

	public void setTransactionHistories(
			List<TransactionHistory> transactionHistories) {
		this.transactionHistories = transactionHistories;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	/**
	 * get the type of transaction, wether it is manualy entered or read from
	 * sms
	 * 
	 * @return 0 if manualy entered, 1 otherwise
	 */
	public int getType() {
		return type;
	}

	/**
	 * set the type of transaction, wether it is manualy entered or read from
	 * sms
	 * 
	 * @param type
	 *            , 0 if manualy entered, 1 otherwise
	 */
	public void setType(int type) {
		this.type = type;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public TransactionCategory getCategory() {
		return category;
	}

	public void setCategory(TransactionCategory category) {
		this.category = category;
	}
}
