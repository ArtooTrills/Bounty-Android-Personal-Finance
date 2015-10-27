package com.artoo.personalfinance;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Transaction;
import adapter.TransactionReportAdapter;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentTransactionReport extends Fragment implements
		OnClickListener {
	private ArrayAdapter<String> arrayAdapterDeafultPeriodChoice;
	private List<Transaction> transactionList, expenseTransaction, incomeTransaction;
	private RecyclerView recyclerViewExpense, recyclerViewIncome;
	private LinearLayoutManager layoutManagerExpRV, layoutManagerIncRV;
	private TransactionReportAdapter expenseAdapter, incomeAdapter;
	private Button textViewRangeSelector;
	private RadioGroup radioGrpCategorySelector;
	private static final String[] choices = { "CURRENT MONTH",
			"LAST TWO MONTHS", "THIS YEAR", "ALL" };
	private int lastSelectedPeriodIndex = 3;

	public FragmentTransactionReport(List<Transaction> transactionList) {
		super();
		this.transactionList = transactionList;
		expenseTransaction = new ArrayList<Transaction>();
		incomeTransaction = new ArrayList<Transaction>();
		for (Transaction t : transactionList) {
			if (t.getType() == Transaction.EXPENSE) {
				expenseTransaction.add(t);
			} else {
				incomeTransaction.add(t);
			}
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.transaction_report_recycler_view,
				container, false);

		recyclerViewExpense = (RecyclerView) view
				.findViewById(R.id.expense_rv_report);
		recyclerViewIncome = (RecyclerView) view
				.findViewById(R.id.income_rv_report);
		textViewRangeSelector = (Button) view
				.findViewById(R.id.textview_range_selector);
		radioGrpCategorySelector = (RadioGroup) view
				.findViewById(R.id.cat_selector_rgp);
		layoutManagerExpRV = new LinearLayoutManager(getActivity(),
				LinearLayoutManager.VERTICAL, false);
		layoutManagerIncRV = new LinearLayoutManager(getActivity(),
				LinearLayoutManager.VERTICAL, false);
		recyclerViewExpense.setLayoutManager(layoutManagerExpRV);
		recyclerViewIncome.setLayoutManager(layoutManagerIncRV);

		expenseAdapter = new TransactionReportAdapter(Transaction.EXPENSE,
				expenseTransaction, getActivity());
		incomeAdapter = new TransactionReportAdapter(Transaction.INCOME,
				incomeTransaction, getActivity());
		recyclerViewExpense.setHasFixedSize(true);
		recyclerViewIncome.setHasFixedSize(true);
		recyclerViewIncome.setAdapter(incomeAdapter);
		recyclerViewExpense.setAdapter(expenseAdapter);
		textViewRangeSelector.setOnClickListener(this);

		radioGrpCategorySelector
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						if (group.getCheckedRadioButtonId() == R.id.expense_rgb) {
							if (recyclerViewExpense.getVisibility() != View.VISIBLE) {
								if (expenseTransaction.isEmpty()) {
									Toast.makeText(getActivity(),
											"No Expenses found",
											Toast.LENGTH_LONG).show();
								}
								recyclerViewExpense.setVisibility(View.VISIBLE);
								recyclerViewIncome.setVisibility(View.GONE);

							}
						} else if (recyclerViewIncome.getVisibility() != View.VISIBLE) {
							if (incomeTransaction.isEmpty()) {
								Toast.makeText(getActivity(),
										"No Income found", Toast.LENGTH_LONG)
										.show();
							}
							recyclerViewExpense.setVisibility(View.GONE);
							recyclerViewIncome.setVisibility(View.VISIBLE);

						}
					}
				});
		return view;
	}

	@Override
	public void onStart() {
		super.onStart();
		arrayAdapterDeafultPeriodChoice = new ArrayAdapter<String>(
				getActivity(), android.R.layout.select_dialog_singlechoice,
				choices);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == textViewRangeSelector.getId()) {
			getUserInputOnPeriodSelection();
		}
	}

	private void getUserInputOnPeriodSelection() {
		AlertDialog.Builder builder = new Builder(getActivity());
		builder.setTitle("Select Period");
		builder.setAdapter(arrayAdapterDeafultPeriodChoice,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						textViewRangeSelector.setText(choices[which]);
						if (which != lastSelectedPeriodIndex) {
							lastSelectedPeriodIndex = which;
							filterDataGivenRange(which);
						}
					}
				});

		builder.show();
	}

	@SuppressWarnings("deprecation")
	private void filterDataGivenRange(int index) {
		expenseTransaction.clear();
		incomeTransaction.clear();
		Date currentDate = new Date();
		int currentMonth = currentDate.getMonth();
		int currentYear = currentDate.getYear();
		if (index == 0) {
			for (Transaction transaction : transactionList) {
				int month = transaction.getTransactionDate().getMonth();
				int year = transaction.getTransactionDate().getYear();

				if (month == currentMonth && year == currentYear) {
					if (transaction.getType() == Transaction.EXPENSE) {
						expenseTransaction.add(transaction);
					} else {
						incomeTransaction.add(transaction);
					}
				}
			}
		} else if (index == 1) {
			for (Transaction transaction : transactionList) {
				int month = transaction.getTransactionDate().getMonth();
				int year = transaction.getTransactionDate().getYear();
				int endMonth = currentMonth - 1;
				if (endMonth < 0) {
					endMonth += 12;
				}
				if (month <= currentMonth && month >= endMonth
						&& year == currentYear) {
					if (transaction.getType() == Transaction.EXPENSE) {
						expenseTransaction.add(transaction);
					} else {
						incomeTransaction.add(transaction);
					}
				}
			}
		} else if (index == 2) {
			for (Transaction transaction : transactionList) {
				int year = transaction.getTransactionDate().getYear();
				if (year == currentYear) {
					if (transaction.getType() == Transaction.EXPENSE) {
						expenseTransaction.add(transaction);
					} else {
						incomeTransaction.add(transaction);
					}
				}
			}
		} else if (index == 3) {
			for (Transaction t : transactionList) {
				if (t.getType() == Transaction.EXPENSE) {
					expenseTransaction.add(t);
				} else {
					incomeTransaction.add(t);
				}
			}
		}
		expenseAdapter.setDataValues();
		incomeAdapter.setDataValues();
		expenseAdapter.notifyDataSetChanged();
		incomeAdapter.notifyDataSetChanged();
	}

	public void notifyNewSMSTransaction(Transaction transaction) {

	}

}
