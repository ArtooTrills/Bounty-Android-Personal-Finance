package com.artoo.personalfinance;

import java.util.ArrayList;
import java.util.List;

import com.artoo.personalfinance.services.SMSFilteringService;
import com.google.gson.Gson;

import persistantData.DatabaseHelper;
import model.Transaction;
import adapter.TransactionRecyclerViewAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentHome extends Fragment {

	// this holds the list of transactions user has made
	private RecyclerView transactionsRecyclerView;

	private TransactionRecyclerViewAdapter transactionAdapter;
	private List<Transaction> transactionList;
	private DatabaseHelper dbHelper;
	private TransactionDetailsPresenter fragmentHistoryPresenter;
	private SMSTransactionReceiver receiver;

	public FragmentHome(TransactionDetailsPresenter fragmentPresenter) {
		super();
		this.fragmentHistoryPresenter = fragmentPresenter;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.recycle_view_layout, container,
				false);
		transactionList = new ArrayList<Transaction>();
		dbHelper = new DatabaseHelper(getActivity());
		transactionsRecyclerView = (RecyclerView) view
				.findViewById(R.id.tranaction_rv);
		LayoutManager layoutManager = new LinearLayoutManager(getActivity(),
				LinearLayoutManager.VERTICAL, false);
		transactionsRecyclerView.setLayoutManager(layoutManager);
		receiver = new SMSTransactionReceiver();
		new TransactionLoader().execute();
		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		IntentFilter filter = new IntentFilter();
		filter.addAction(SMSFilteringService.BROADCAST_ACTION_KEY);
		getActivity().registerReceiver(receiver, filter);

	}

	@Override
	public void onPause() {
		super.onPause();
		getActivity().unregisterReceiver(receiver);
	}

	class TransactionLoader extends AsyncTask<Void, Void, Void> {
		List<Transaction> tempList;
		

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(Void... params) {
			tempList = dbHelper.getAllTransaction();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);

			transactionList.clear();
			if (tempList != null) {
				transactionList.addAll(tempList);
			}
			tempList = null;

			transactionAdapter = new TransactionRecyclerViewAdapter(
					fragmentHistoryPresenter, transactionList, getActivity());
			transactionsRecyclerView.setAdapter(transactionAdapter);

		}
	}

	//
	private class SMSTransactionReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			Bundle databuBundle = intent.getExtras();
			if (databuBundle != null) {
				if (databuBundle
						.containsKey(SMSFilteringService.TRANSACTION_KEY)) {
					Transaction transaction = new Gson().fromJson(databuBundle
							.getString(SMSFilteringService.TRANSACTION_KEY),
							Transaction.class);
					notifyWhenBroadCast(transaction);
				}
			}
		}
	}

	public void notifyWhenBroadCast(Transaction transaction) {
		transactionList.add(0, transaction);
		if (transactionAdapter == null) {
			transactionAdapter = new TransactionRecyclerViewAdapter(
					fragmentHistoryPresenter, transactionList, getActivity());
			transactionsRecyclerView.setAdapter(transactionAdapter);
		} else {

			transactionAdapter.notifyDataSetChanged();
		}
	}
}
