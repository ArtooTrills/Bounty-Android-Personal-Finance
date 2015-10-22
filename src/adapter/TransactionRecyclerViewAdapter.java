package adapter;

import java.util.Date;
import java.util.List;

import persistantData.DatabaseHelper;
import utills.CommonUtility;
import model.Transaction;

import com.artoo.personalfinance.TransactionDetailsPresenter;
import com.artoo.personalfinance.R;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class TransactionRecyclerViewAdapter extends
		RecyclerView.Adapter<TransactionRecyclerViewAdapter.ViewHolder> {
	private LayoutInflater inflater;
	private List<Transaction> transactionList;
	private Context context;
	private TransactionDetailsPresenter transactionDetailsPresenter;

	private static final String NO_RECORD_STRING = "<h1>No Transaction found.</h1><br/>"
			+ "<h3>Use the + button on the top to add a transaction</h3>";

	public TransactionRecyclerViewAdapter(
			TransactionDetailsPresenter fragmentPresenter,
			List<Transaction> transactionList, Context context) {
		super();
		this.transactionDetailsPresenter = fragmentPresenter;
		this.transactionList = transactionList;
		this.context = context;
		this.inflater = LayoutInflater.from(this.context);
	}

	class ViewHolder extends RecyclerView.ViewHolder implements OnClickListener {
		// controls for transaction rows
		View mRootView;
		TextView textViewTransactionAmount, textViewTransactionDate,
				textViewTransactionDesc;
		ImageView imgViewTransactionType;
		Button buttonDeleteTransaction;

		// controls for recycler view header
		TextView textViewheaderRow, textViewTransactionPeriod,
				textViewTotalIncome, textViewTotalExepense,
				TextViewTotalTransaction, textViewViewMore;
		LinearLayout headerMainLayout, incomelayout, expenseLayout;

		public ViewHolder(View itemView, int itemType) {
			super(itemView);
			if (itemType == 0) {

				textViewheaderRow = (TextView) itemView
						.findViewById(R.id.transaction_first_row_header_textView);

				TextViewTotalTransaction = (TextView) itemView
						.findViewById(R.id.total_transaction_text_view);
				textViewTransactionPeriod = (TextView) itemView
						.findViewById(R.id.transaction_first_row_summmary_period_textView);
				textViewTotalIncome = (TextView) itemView
						.findViewById(R.id.transaction_first_row_income_value_textView);
				textViewTotalExepense = (TextView) itemView
						.findViewById(R.id.transaction_first_row_expense_value_textView);
				headerMainLayout = (LinearLayout) itemView
						.findViewById(R.id.transaction_first_row_main_layout);
				incomelayout = (LinearLayout) itemView
						.findViewById(R.id.income_layout);
				textViewViewMore = (TextView) itemView
						.findViewById(R.id.transaction_first_row_header_view_more_textView);
				expenseLayout = (LinearLayout) itemView
						.findViewById(R.id.expense_layout);
				itemView.setOnClickListener(this);
			} else {

				mRootView = itemView;
				buttonDeleteTransaction = (Button) itemView
						.findViewById(R.id.delete_transaction_row_btn);
				textViewTransactionAmount = (TextView) itemView
						.findViewById(R.id.transaction_amount_text_view);
				textViewTransactionDate = (TextView) itemView
						.findViewById(R.id.transaction_date_text_view);
				textViewTransactionDesc = (TextView) itemView
						.findViewById(R.id.transaction_desc_text_view);
				imgViewTransactionType = (ImageView) itemView
						.findViewById(R.id.tran_type_symbol_img);

			}
		}

		@Override
		public void onClick(View v) {

			// onclick for summary
			if (!transactionList.isEmpty())
				transactionDetailsPresenter.showReportFragment(transactionList);
		}
	}

	@Override
	public int getItemCount() {
		return transactionList.size() + 1;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		if (position == 0) {

			// if there is no transaction
			if (getItemCount() == 1) {
				holder.textViewViewMore.setVisibility(View.GONE);
				holder.incomelayout.setVisibility(View.GONE);
				holder.expenseLayout.setVisibility(View.GONE);
				holder.textViewheaderRow.setVisibility(View.GONE);
				holder.textViewTransactionPeriod.setGravity(Gravity.CENTER);
				holder.textViewTransactionPeriod.setText(Html
						.fromHtml(NO_RECORD_STRING));
				holder.headerMainLayout.setBackground(null);
			}// if there are transactions
			else {
				holder.textViewViewMore.setVisibility(View.VISIBLE);
				holder.incomelayout.setVisibility(View.VISIBLE);
				holder.expenseLayout.setVisibility(View.VISIBLE);
				holder.textViewheaderRow.setVisibility(View.VISIBLE);
				holder.headerMainLayout
						.setBackground(context.getResources().getDrawable(
								android.R.drawable.dialog_holo_light_frame));
				if (transactionList.get(position).getTransactionDate() != null) {
					try {
						String[] dateRange = getRangeDates();
						holder.textViewTransactionPeriod
								.setText("Transactions from " + dateRange[0]
										+ " to " + dateRange[1]);
					} catch (Exception e) {

					}
				}
				float[] transactions = getTotalTransactionByType();
				holder.textViewTotalExepense.setText("- " + transactions[1]);
				holder.textViewTotalIncome.setText("+ " + transactions[0] + "");
				float totalTran = transactions[0] - transactions[1];
				totalTran = (float) Math.round(totalTran * 100) / 100;
				if (totalTran >= 0) {
					holder.TextViewTotalTransaction.setTextColor(Color
							.parseColor("#43a047"));
					if (totalTran == 0)
						holder.TextViewTotalTransaction.setText(totalTran + "");
					else
						holder.TextViewTotalTransaction.setText("+ "
								+ totalTran);
				} else {
					holder.TextViewTotalTransaction.setTextColor(Color
							.parseColor("#ef5350"));
					holder.TextViewTotalTransaction.setText("- "
							+ (totalTran * -1));
				}
			}

		} else if (position > 0) {
			position--;
			final int accessPosition = position;

			holder.mRootView.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					transactionDetailsPresenter
							.showHistoryFragment(transactionList
									.get(accessPosition));
				}
			});

			holder.buttonDeleteTransaction
					.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							if (accessPosition >= 0)
								showConfirmationDialogOnDelete(transactionList
										.get(accessPosition));
						}
					});

			holder.textViewTransactionAmount.setText(transactionList.get(
					position).getAmount()
					+ "");

			if (transactionList.get(position).getType() == Transaction.INCOME) {
				holder.imgViewTransactionType.setImageDrawable(context
						.getResources().getDrawable(R.drawable.income_symbol));
			} else {
				holder.imgViewTransactionType.setImageDrawable(context
						.getResources().getDrawable(R.drawable.expense_symbol));
			}

			if (transactionList.get(position).getTransactionDate() != null) {
				holder.textViewTransactionDate
						.setText(CommonUtility.DATE_FORMATTER_WITHOUT_TIME
								.format(transactionList.get(position)
										.getTransactionDate()));
			}
			if (transactionList.get(accessPosition).getDesc() != null) {
				if (transactionList.get(position).getDesc().length() > 10)
					holder.textViewTransactionDesc.setText(transactionList
							.get(position).getDesc().substring(0, 10)
							+ "...");
				else {
					holder.textViewTransactionDesc.setText(transactionList.get(
							position).getDesc());
				}
			}
		}
	}

	/**
	 * shows a dialog seeking user confirmation if user really wants to delete
	 * the transaction
	 * 
	 * @param transaction
	 */
	private void showConfirmationDialogOnDelete(final Transaction transaction) {
		AlertDialog.Builder conBuilder = new Builder(context);
		conBuilder.setMessage("Do you really want to delete this transaction");
		conBuilder.setNegativeButton("No",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

					}
				});
		conBuilder.setPositiveButton("Yes",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						new DeleteTransactionAsync(context, transaction)
								.execute();
					}
				});
		conBuilder.show();
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup root, int viewType) {
		ViewHolder viewHolder = null;
		View view;
		if (viewType == 0) {
			view = inflater
					.inflate(R.layout.transaction_first_row, root, false);
		} else {
			view = inflater.inflate(R.layout.transaction_row, root, false);

		}
		viewHolder = new ViewHolder(view, viewType);
		return viewHolder;
	}

	/**
	 * returns the maximum and minimum dates of transaction
	 * 
	 * @return
	 */
	public String[] getRangeDates() {
		String[] dates = null;

		if (transactionList != null && !transactionList.isEmpty()) {
			dates = new String[2];
			Date minDate = transactionList.get(0).getTransactionDate();
			Date maxDate = transactionList.get(0).getTransactionDate();
			for (int i = 1; i < transactionList.size(); i++) {
				if (transactionList.get(i).getTransactionDate().after(maxDate)) {
					maxDate = transactionList.get(i).getTransactionDate();
				}
				if (transactionList.get(i).getTransactionDate().before(minDate)) {
					minDate = transactionList.get(i).getTransactionDate();
				}
			}
			dates[0] = CommonUtility.DATE_FORMATTER_WITHOUT_TIME
					.format(minDate);
			dates[1] = CommonUtility.DATE_FORMATTER_WITHOUT_TIME
					.format(maxDate);
		}
		return dates;
	}

	/**
	 * returns total exppenses and incomes
	 * 
	 * @return
	 */
	private float[] getTotalTransactionByType() {
		float[] transactions = new float[2];
		for (Transaction transaction : transactionList) {
			if (transaction.getType() == Transaction.INCOME) {
				transactions[0] += transaction.getAmount();
			} else {
				transactions[1] += transaction.getAmount();
			}
		}
		return transactions;
	}

	@Override
	public int getItemViewType(int position) {
		if (position == 0) {
			return 0;
		}
		return 1;
	}

	/**
	 * delete transaction from home screen
	 * 
	 * @author nayan
	 *
	 */
	class DeleteTransactionAsync extends AsyncTask<Void, Void, Void> {
		private ProgressDialog pDialog;
		private boolean result = false;
		private Context context;
		private Transaction transaction;

		public DeleteTransactionAsync(Context context, Transaction transaction) {
			super();
			this.context = context;
			this.transaction = transaction;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(context);
			pDialog.setMessage("Removing Transaction");
			pDialog.setCancelable(false);
		}

		@Override
		protected Void doInBackground(Void... params) {
			result = (new DatabaseHelper(context))
					.deleteTransaction(transaction.getId());

			return null;
		}

		@Override
		protected void onPostExecute(Void res) {
			super.onPostExecute(res);
			if (result) {
				transactionList.remove(transaction);
				notifyDataSetChanged();
				pDialog.dismiss();
			} else {
				pDialog.dismiss();
				Toast.makeText(context, "Something went wrong!",
						Toast.LENGTH_SHORT).show();
			}
		}
	}
}
