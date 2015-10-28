package com.artoo.personalfinance;

import java.util.ArrayList;
import java.util.List;

import persistantData.DatabaseHelper;
import model.Transaction;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendPosition;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.LargeValueFormatter;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;

@SuppressWarnings("deprecation")
public class ComparativeReport extends Fragment {

	private RadioGroup radioGroupPeriodSelector;
	private Spinner spnrMonthSelector, spnrYearSelector;
	private BarChart barChartWeekly, barChartMonthly;
	private List<Transaction> totalTransactions;
	private static final String[] MONTHS = { "Jan", "Feb", "Mar", "Apr", "May",
			"June", "July", "Aug", "Sept", "Oct", "Nov", "Dec" };
	private List<Integer> years;
	private DatabaseHelper dbHelper;
	private List<Float> weeklyIncome;
	private List<Float> weeklyExpense;
	private List<Float> monthlyIncome;
	private List<Float> monthlyExpense;
	private Typeface tf;

	private static final String[] WEEK_XVALUES = { "Week1", "Week2", "Week3",
			"Week4" };

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.comparative_report, container,
				false);

		dbHelper = new DatabaseHelper(getActivity());
		totalTransactions = new ArrayList<Transaction>();
		List<Transaction> temp = dbHelper.getAllTransaction();
		if (temp != null) {
			totalTransactions.addAll(temp);
		}
		radioGroupPeriodSelector = (RadioGroup) view
				.findViewById(R.id.comp_period_selector_rgp);
		spnrMonthSelector = (Spinner) view
				.findViewById(R.id.comp_month_selector);
		spnrYearSelector = (Spinner) view.findViewById(R.id.comp_year_selector);
		barChartWeekly = (BarChart) view.findViewById(R.id.weekly_cahrt);
		barChartMonthly = (BarChart) view.findViewById(R.id.monthly_chart);

		getYearRange();
		if (!years.isEmpty()) {
			spnrMonthSelector
					.setAdapter(new ArrayAdapter<String>(getActivity(),
							android.R.layout.simple_list_item_1, MONTHS));

			spnrYearSelector.setAdapter(new ArrayAdapter<Integer>(
					getActivity(), android.R.layout.simple_list_item_1, years));
		}
		radioGroupPeriodSelector
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						if (radioGroupPeriodSelector.getCheckedRadioButtonId() == R.id.monthly_rgb) {
							barChartMonthly.setVisibility(View.VISIBLE);
							barChartWeekly.setVisibility(View.GONE);
							spnrMonthSelector.setVisibility(View.GONE);
						} else {
							barChartMonthly.setVisibility(View.GONE);
							barChartWeekly.setVisibility(View.VISIBLE);
							spnrMonthSelector.setVisibility(View.VISIBLE);
						}
					}
				});

		barChartWeekly.setDescription("");

		barChartMonthly.setDescription("");

		tf = Typeface.createFromAsset(getActivity().getAssets(),
				"OpenSans-Regular.ttf");

		Legend l = barChartWeekly.getLegend();
		l.setPosition(LegendPosition.BELOW_CHART_CENTER);
		l.setTypeface(tf);
		l.setYOffset(0f);
		l.setYEntrySpace(0f);
		l.setTextSize(8f);

		XAxis xl = barChartWeekly.getXAxis();
		xl.setTypeface(tf);

		YAxis leftAxis = barChartWeekly.getAxisLeft();
		leftAxis.setTypeface(tf);
		leftAxis.setValueFormatter(new LargeValueFormatter());
		leftAxis.setDrawGridLines(false);
		leftAxis.setSpaceTop(30f);

		barChartWeekly.getAxisRight().setEnabled(false);

		l = barChartMonthly.getLegend();
		l.setPosition(LegendPosition.BELOW_CHART_CENTER);
		l.setTypeface(tf);
		l.setYOffset(0f);
		l.setYEntrySpace(0f);
		l.setTextSize(8f);
		xl = barChartMonthly.getXAxis();
		xl.setTypeface(tf);
		leftAxis = barChartMonthly.getAxisLeft();
		leftAxis.setTypeface(tf);
		leftAxis.setValueFormatter(new LargeValueFormatter());
		leftAxis.setDrawGridLines(false);
		leftAxis.setSpaceTop(30f);

		setWeeklyReportData();
		setMonthlyReportData();

		spnrMonthSelector
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						setWeeklyReportData();
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
					}
				});

		spnrYearSelector
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						if (barChartWeekly.getVisibility() == View.VISIBLE) {
							setWeeklyReportData();
						} else {
							setMonthlyReportData();
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
					}
				});

		return view;
	}

	private void getYearRange() {
		int maxYear = -1, minYear = Integer.MAX_VALUE;
		for (Transaction t : totalTransactions) {
			if (t.getTransactionDate().getYear() + 1900 < minYear) {
				minYear = t.getTransactionDate().getYear() + 1900;
			}
			if (t.getTransactionDate().getYear() + 1900 > maxYear) {
				maxYear = t.getTransactionDate().getYear() + 1900;
			}
		}
		if (maxYear != -1) {
			years = new ArrayList<Integer>();
			for (int i = minYear; i <= maxYear; i++) {
				years.add(i);
			}
		}

	}

	private void setWeeklyReportData() {
		int year = years.get(spnrYearSelector.getSelectedItemPosition()) - 1900;
		int month = spnrMonthSelector.getSelectedItemPosition();

		if (weeklyIncome == null) {
			weeklyIncome = new ArrayList<Float>(4);
		}

		if (weeklyExpense == null) {
			weeklyExpense = new ArrayList<Float>(4);
		}
		if (weeklyExpense.isEmpty()) {
			for (int i = 0; i < 4; i++) {
				weeklyIncome.add(0f);
				weeklyExpense.add(0f);
			}
		} else {
			for (int i = 0; i < weeklyIncome.size(); i++) {
				weeklyIncome.set(i, 0f);
				weeklyExpense.set(i, 0f);
			}
		}
		for (Transaction transaction : totalTransactions) {
			if (transaction.getTransactionDate().getYear() == year
					&& transaction.getTransactionDate().getMonth() == month) {
				if (transaction.getType() == Transaction.INCOME) {
					if (transaction.getTransactionDate().getDate() < 8) {
						weeklyIncome.set(0,
								weeklyIncome.get(0) + transaction.getAmount());
					} else if (transaction.getTransactionDate().getDate() < 15) {
						weeklyIncome.set(1,
								weeklyIncome.get(1) + transaction.getAmount());
					} else if (transaction.getTransactionDate().getDate() < 22) {
						weeklyIncome.set(2,
								weeklyIncome.get(2) + transaction.getAmount());
					} else {
						weeklyIncome.set(3,
								weeklyIncome.get(3) + transaction.getAmount());
					}
				} else {
					if (transaction.getTransactionDate().getDate() < 8) {
						weeklyExpense.set(0,
								weeklyExpense.get(0) + transaction.getAmount());
					} else if (transaction.getTransactionDate().getDate() < 15) {
						weeklyExpense.set(1,
								weeklyExpense.get(1) + transaction.getAmount());
					} else if (transaction.getTransactionDate().getDate() < 22) {
						weeklyExpense.set(2,
								weeklyExpense.get(2) + transaction.getAmount());
					} else {
						weeklyExpense.set(3,
								weeklyExpense.get(3) + transaction.getAmount());
					}
				}
			}
		}

		List<BarEntry> incomeYValues = new ArrayList<BarEntry>();
		List<BarEntry> expenseYValues = new ArrayList<BarEntry>();

		for (int i = 0; i < weeklyExpense.size(); i++) {
			incomeYValues.add(new BarEntry(weeklyIncome.get(i), i));
			expenseYValues.add(new BarEntry(weeklyExpense.get(i), i));
		}

		BarDataSet incomeDataSet = new BarDataSet(incomeYValues, "Income");
		BarDataSet expenseDataSet = new BarDataSet(expenseYValues, "Expense");

		incomeDataSet.setColor(getActivity().getResources().getColor(
				R.color.income_color));
		expenseDataSet.setColor(getActivity().getResources().getColor(
				R.color.expense_color));
		ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
		dataSets.add(expenseDataSet);
		dataSets.add(incomeDataSet);
		barChartWeekly.setData(new BarData(WEEK_XVALUES, dataSets));
		barChartWeekly.invalidate();
	}

	private void setMonthlyReportData() {

		int year = years.get(spnrYearSelector.getSelectedItemPosition()) - 1900;

		if (monthlyIncome == null) {
			monthlyIncome = new ArrayList<Float>(12);
		}

		if (monthlyExpense == null) {
			monthlyExpense = new ArrayList<Float>(12);
		}

		if (monthlyExpense.isEmpty()) {
			for (int i = 0; i < MONTHS.length; i++) {
				monthlyIncome.add(0f);
				monthlyExpense.add(0f);
			}
		} else {
			for (int i = 0; i < monthlyIncome.size(); i++) {
				monthlyIncome.set(i, 0f);
				monthlyExpense.set(i, 0f);
			}
		}
		for (Transaction transaction : totalTransactions) {
			if (transaction.getTransactionDate().getYear() == year) {
				if (transaction.getType() == Transaction.EXPENSE) {
					monthlyExpense.set(
							transaction.getTransactionDate().getMonth(),
							monthlyExpense.get(transaction.getTransactionDate()
									.getMonth()) + transaction.getAmount());
				} else {
					monthlyIncome.set(
							transaction.getTransactionDate().getMonth(),
							monthlyExpense.get(transaction.getTransactionDate()
									.getMonth()) + transaction.getAmount());
				}
			}
		}

		List<BarEntry> incomeYValues = new ArrayList<BarEntry>();
		List<BarEntry> expenseYValues = new ArrayList<BarEntry>();

		for (int i = 0; i < monthlyExpense.size(); i++) {
			incomeYValues.add(new BarEntry(monthlyIncome.get(i), i));
			expenseYValues.add(new BarEntry(monthlyExpense.get(i), i));
		}

		BarDataSet incomeDataSet = new BarDataSet(incomeYValues, "Income");
		BarDataSet expenseDataSet = new BarDataSet(expenseYValues, "Expense");

		incomeDataSet.setColor(getActivity().getResources().getColor(
				R.color.income_color));
		expenseDataSet.setColor(getActivity().getResources().getColor(
				R.color.expense_color));
		ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
		dataSets.add(expenseDataSet);
		dataSets.add(incomeDataSet);
		barChartMonthly.setData(new BarData(MONTHS, dataSets));
		barChartMonthly.invalidate();
	}

}
