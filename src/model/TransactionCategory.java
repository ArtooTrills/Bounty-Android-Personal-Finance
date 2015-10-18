package model;

public class TransactionCategory {

	public TransactionCategory(int transactionType) {
		super();
		this.transactionType = transactionType;
		this.categoryName = "UNDEFINED";
	}

	int transactionType;
	String categoryName;

	public int getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(int transactionType) {
		this.transactionType = transactionType;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public TransactionCategory(int transactionType, String categoryName) {
		super();
		this.transactionType = transactionType;
		this.categoryName = categoryName;
	}
}
