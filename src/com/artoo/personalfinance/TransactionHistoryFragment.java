package com.artoo.personalfinance;

import java.util.ArrayList;
import java.util.List;

import persistantData.DatabaseHelper;
import model.Transaction;
import model.TransactionCategory;
import model.TransactionHistory;
import adapter.TransactionHistoryAdapter;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TransactionHistoryFragment extends Fragment {
	private RecyclerView recyclerViewTransactionHistory;
	private RecyclerView.LayoutManager transactionHistoryLayoutManager;
	private List<TransactionHistory> transactionHistoryList;
	private TransactionHistoryAdapter historyAdapter;
	Transaction transaction;
	public DatabaseHelper dbHelper;
	public List<TransactionCategory> categories;
	private List<String> categoryNames;

	public TransactionHistoryFragment(Transaction transaction) {
		super();
		this.transaction = transaction;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.recycle_view_layout, container,
				false);
		recyclerViewTransactionHistory = (RecyclerView) view
				.findViewById(R.id.tranaction_rv);
		transactionHistoryLayoutManager = new LinearLayoutManager(
				getActivity(), LinearLayoutManager.VERTICAL, false);
		recyclerViewTransactionHistory
				.setLayoutManager(transactionHistoryLayoutManager);
		
		categoryNames = new ArrayList<String>();
		dbHelper=new DatabaseHelper(getActivity());
		new TransactionHistoryAndCategoryLoader().execute();
		return view;
	}

	class TransactionHistoryAndCategoryLoader extends
			AsyncTask<Void, Void, Void> {
		List<TransactionHistory> tempList;
		ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(getActivity());
			pDialog.setMessage("Loading history");
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			transactionHistoryList = dbHelper.getTransactionHistory(transaction
					.getId());
			categories = dbHelper.getAllCategory();

			for (TransactionCategory t : categories) {
				if (t.getTransactionType() == Transaction.INCOME)
					categoryNames.add(t.getCategoryName());
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			pDialog.dismiss();
			if (transactionHistoryList == null)
				transactionHistoryList = new ArrayList<TransactionHistory>();
			transaction.setTransactionHistories(transactionHistoryList);
			historyAdapter = new TransactionHistoryAdapter(getActivity(),
					transactionHistoryList, transaction);
			historyAdapter.setCategories(categories, categoryNames);
			recyclerViewTransactionHistory.setAdapter(historyAdapter);
		}
	}
}
