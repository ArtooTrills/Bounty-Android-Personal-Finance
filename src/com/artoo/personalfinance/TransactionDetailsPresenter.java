package com.artoo.personalfinance;

import java.util.List;

import model.Transaction;

public interface TransactionDetailsPresenter {

	/**
	 * shows history fragment to display history of a particular transaction and
	 * user can modifications here
	 * 
	 * @param transaction
	 */
	public void showHistoryFragment(Transaction transaction);
	
	public void showReportFragment(List<Transaction> transactions);
}
