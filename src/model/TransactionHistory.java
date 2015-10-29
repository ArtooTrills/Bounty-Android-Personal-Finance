package model;

import java.util.Date;

public class TransactionHistory {
	private int id;
	private int transactionId;
	private String message;
	private Date modificationDate;

	public TransactionHistory( int transactionId, String message,
			Date modificationDate) {
		super();
		this.transactionId = transactionId;
		this.message = message;
		this.modificationDate = modificationDate;
	}

	public TransactionHistory() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getModificationDate() {
		return modificationDate;
	}

	public void setModificationDate(Date modificationDate) {
		this.modificationDate = modificationDate;
	}
}
