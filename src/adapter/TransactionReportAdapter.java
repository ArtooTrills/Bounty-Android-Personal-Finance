package adapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import com.artoo.personalfinance.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendDirection;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.components.Legend.LegendPosition;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import model.Transaction;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TransactionReportAdapter extends
		RecyclerView.Adapter<TransactionReportAdapter.ViewHolder> {
	static ArrayList<Integer> INCOME_COLORS, EXPENSE_COLORS;
	int type;
	List<Transaction> transactions;
	private LayoutInflater inflater;
	private Context context;
	private static Typeface tf;
	List<String> categories;
	List<Float> amountCategoryWise;
	private float totalTransaction;
	static {
		INCOME_COLORS = new ArrayList<Integer>();
		EXPENSE_COLORS = new ArrayList<Integer>();

		for (int c : ColorTemplate.JOYFUL_COLORS)
			INCOME_COLORS.add(c);
		for (int c : ColorTemplate.LIBERTY_COLORS)
			INCOME_COLORS.add(c);

		for (int c : ColorTemplate.VORDIPLOM_COLORS)
			INCOME_COLORS.add(c);

		for (int c : ColorTemplate.PASTEL_COLORS)
			INCOME_COLORS.add(c);
		for (int c : ColorTemplate.COLORFUL_COLORS)
			INCOME_COLORS.add(c);
		for (int i = INCOME_COLORS.size() - 1; i > -1; i--) {
			EXPENSE_COLORS.add(INCOME_COLORS.get(i));
		}
	}

	/**
	 * sets and resets data when list of transaction changes
	 */
	public void setDataValues() {
		totalTransaction = 0f;
		Collections.sort(transactions, new Comparator<Transaction>() {

			@Override
			public int compare(Transaction lhs, Transaction rhs) {
				if (lhs.getCategory().getCategoryName()
						.equals(rhs.getCategory().getCategoryName())) {
					return 0;
				}
				return lhs.getCategory().getCategoryName()
						.compareTo(rhs.getCategory().getCategoryName());
			}
		});
		categories.clear();
		amountCategoryWise.clear();
		if (!transactions.isEmpty()) {
			categories.add(transactions.get(0).getCategory().getCategoryName());
			amountCategoryWise.add(transactions.get(0).getAmount());
			totalTransaction += transactions.get(0).getAmount();
			for (int i = 1; i < transactions.size(); i++) {
				if (transactions.get(i).getCategory().getCategoryName()
						.equals(categories.get(categories.size() - 1))) {
					amountCategoryWise
							.set(amountCategoryWise.size() - 1,
									amountCategoryWise.get(amountCategoryWise
											.size() - 1)
											+ transactions.get(i).getAmount());
				} else {
					categories.add(transactions.get(i).getCategory()
							.getCategoryName());
					amountCategoryWise.add(transactions.get(i).getAmount());
				}
				totalTransaction += transactions.get(i).getAmount();
			}
		}

	}

	public TransactionReportAdapter(int type, List<Transaction> transactions,
			Context context) {
		super();
		this.type = type;
		this.transactions = transactions;
		this.context = context;
		this.inflater = LayoutInflater.from(this.context);
		categories = new ArrayList<String>();
		amountCategoryWise = new ArrayList<Float>();
		setDataValues();
	}

	class ViewHolder extends RecyclerView.ViewHolder {
		PieChart pieChart;
		TextView textViewPeriodChooser;

		TextView textViewTransactionCategoryName,
				textViewTransactionCategoryValue;

		public ViewHolder(View itemView, int itemType) {
			super(itemView);
			if (itemType == 0) {
				pieChart = (PieChart) itemView
						.findViewById(R.id.pie_chart_expense);
				pieChart.setDescription("");
			} else {
				textViewTransactionCategoryName = (TextView) itemView
						.findViewById(R.id.tran_cat_name_text_View);
				textViewTransactionCategoryValue = (TextView) itemView
						.findViewById(R.id.tran_cat_value_text_View);
			}
		}
	}

	@Override
	public int getItemCount() {
		if (categories == null || categories.size() == 0) {
			return 1;
		} else {
			return categories.size() + 1;
		}
	}

	@SuppressLint("NewApi")
	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		if (position == 0) {
			holder.pieChart.setVisibility(View.VISIBLE);
			holder.pieChart.setUsePercentValues(true);
			holder.pieChart.setExtraOffsets(5, 5, 5, 5);
			holder.pieChart.setDragDecelerationFrictionCoef(.95f);
			holder.pieChart.setDrawHoleEnabled(true);
			holder.pieChart.setHoleColorTransparent(true);
			tf = Typeface.createFromAsset(context.getAssets(),
					"OpenSans-Regular.ttf");
			holder.pieChart.setTransparentCircleColor(Color.WHITE);
			holder.pieChart.setTransparentCircleAlpha(150);
			holder.pieChart.setHoleRadius(58f);
			holder.pieChart.setTransparentCircleRadius(60f);
			holder.pieChart.setDrawCenterText(true);
			holder.pieChart.setRotationAngle(0);
			holder.pieChart.setRotationEnabled(true);
			holder.pieChart.setHighlightEnabled(true);
			holder.pieChart.animateY(1400, Easing.EasingOption.EaseInOutCirc);
			setData(holder.pieChart);
		} else {
			holder.textViewTransactionCategoryName.setText(categories
					.get(position - 1));
			holder.textViewTransactionCategoryValue.setText(amountCategoryWise
					.get(position - 1) + "");
		}
	}

	@Override
	public int getItemViewType(int position) {

		return position;
	}

	/**
	 * sets data into pie chart and displays transaction by category
	 */
	@SuppressLint("NewApi")
	private void setData(final PieChart pieChart) {

		List<Entry> yValues = new ArrayList<Entry>();

		for (int i = 0; i < categories.size(); i++) {
			yValues.add(new Entry(
					((amountCategoryWise.get(i)) / totalTransaction) * 100, i));
		}
		PieDataSet dataSet = new PieDataSet(yValues, "");

		dataSet.setSliceSpace(3.4f);
		dataSet.setSelectionShift(8f);
		PieData data = new PieData(categories, dataSet);
		if (type == Transaction.EXPENSE)
			dataSet.setColors(EXPENSE_COLORS);
		else {
			dataSet.setColors(INCOME_COLORS);
		}

		data.setValueFormatter(new PercentFormatter());
		data.setValueTextSize(11f);
		data.setDrawValues(true);
		String noDataText;
		if (type == Transaction.EXPENSE)
			noDataText = "NO EXPENSES FOUND";
		else {
			noDataText = "NO INCOME FOUND";
		}
		pieChart.setDrawHoleEnabled(true);
		pieChart.setNoDataText(noDataText);
		pieChart.setDrawSliceText(false);
		data.setValueTypeface(tf);
		data.setValueTextColor(Color.BLACK);
		pieChart.setData(data);
		pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
			@Override
			public void onNothingSelected() {
			}

			@Override
			public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
				pieChart.highlightValue(e.getXIndex(), dataSetIndex);
				pieChart.setCenterText(new SpannableString(amountCategoryWise
						.get(e.getXIndex()) + ""));
			}
		});
		// pieChart.setHovered(true);

		// pieChart.getChartBitmap().setWidth(280);

		Legend l = pieChart.getLegend();
		l.setFormToTextSpace(2);
		l.setWordWrapEnabled(true);
		l.setPosition(LegendPosition.BELOW_CHART_CENTER);
		l.setForm(LegendForm.CIRCLE);
		l.setDirection(LegendDirection.LEFT_TO_RIGHT);
		l.setXEntrySpace(2f);
		l.setYEntrySpace(0);
		l.setYOffset(2f);
		pieChart.highlightValues(null);
		pieChart.invalidate();

		// pieChart.getChartBitmap().setDensity(60*200);
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
		ViewHolder holder;
		View view;
		if (viewType == 0) {
			view = inflater.inflate(R.layout.transaction_report_layout,
					viewGroup, false);
		} else {
			view = inflater
					.inflate(
							R.layout.transaction_report_category_detail_recycler_view_row,
							viewGroup, false);
		}
		holder = new ViewHolder(view, viewType);
		return holder;
	}
}
