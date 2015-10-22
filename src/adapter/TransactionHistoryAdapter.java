package adapter;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import persistantData.DatabaseHelper;
import utills.CommonUtility;
import widgets.TextDatePicker;

import com.artoo.personalfinance.R;

import model.IgnoreItem;
import model.Transaction;
import model.TransactionCategory;
import model.TransactionHistory;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class TransactionHistoryAdapter extends
		RecyclerView.Adapter<TransactionHistoryAdapter.ViewHolder> {

	private List<TransactionHistory> transactionHistoryList;
	private Transaction transaction;
	private LayoutInflater inflater;
	private Context context;
	private List<String> categoryNames;
	private DatabaseHelper dbHelper;
	private List<TransactionCategory> categories;

	public void setCategories(List<TransactionCategory> categories,
			List<String> categoryNames) {
		this.categories = categories;
		this.categoryNames = categoryNames;
	}

	public TransactionHistoryAdapter(Context context,
			List<TransactionHistory> transactionHistoryList,
			Transaction transaction) {
		super();
		this.context = context;
		this.transactionHistoryList = transactionHistoryList;
		this.transaction = transaction;
		this.inflater = LayoutInflater.from(context);
		dbHelper = new DatabaseHelper(context);
	}

	class ViewHolder extends RecyclerView.ViewHolder implements OnClickListener {
		// controls of transaction, header
		LinearLayout smsLayout;
		private TextView textViewDate, textViewTransactionCategory,
				textViewIgnoreItem;
		private RadioGroup radioGrpTransactionType;
		private Button buttonUpdateTransaction, buttonDeleteTransaction,
				buttonShowTranSource;
		private EditText editTextAmount, editTextDesc;

		// controls of history
		TextView textViewTransactionHistoryDetails;

		public ViewHolder(View itemView, int viewType) {
			super(itemView);
			if (viewType == 0) {
				smsLayout = (LinearLayout) itemView.findViewById(R.id.sms_ll);
				textViewIgnoreItem = (TextView) itemView
						.findViewById(R.id.ignore_sender_tv);
				textViewTransactionCategory = (TextView) itemView
						.findViewById(R.id.tran_category_text_view_history);
				buttonShowTranSource = (Button) itemView
						.findViewById(R.id.btn_tran_source_show);
				buttonDeleteTransaction = (Button) itemView
						.findViewById(R.id.delete_transaction_btn);
				textViewDate = (TextView) itemView
						.findViewById(R.id.tran_date_edt_history);
				radioGrpTransactionType = (RadioGroup) itemView
						.findViewById(R.id.tran_type_radio_grp_history);
				buttonUpdateTransaction = (Button) itemView
						.findViewById(R.id.tran_save_btn_history);
				editTextAmount = (EditText) itemView
						.findViewById(R.id.tran_amt_edt_history);
				editTextDesc = (EditText) itemView
						.findViewById(R.id.tran_note_edt_history);

				textViewTransactionCategory.setOnClickListener(this);
				buttonShowTranSource.setOnClickListener(this);
				buttonUpdateTransaction.setOnClickListener(this);
				buttonDeleteTransaction.setOnClickListener(this);
				textViewIgnoreItem.setOnClickListener(this);
				// changing transaction categories at runtime
				radioGrpTransactionType
						.setOnCheckedChangeListener(new OnCheckedChangeListener() {

							@Override
							public void onCheckedChanged(RadioGroup group,
									int checkedId) {
								textViewTransactionCategory
										.setText(R.string.default_category);
								fillCategoryNames(checkedId);
							}
						});
			} else {
				textViewTransactionHistoryDetails = (TextView) itemView
						.findViewById(R.id.text_view_utility);
			}
		}

		@Override
		public void onClick(View v) {
			if (v.getId() == textViewIgnoreItem.getId()) {
				IgnoreItem ignoreItem = new IgnoreItem();
				ignoreItem.setDate(new Date());
				ignoreItem.setSource(transaction.getSender());
				new IgnoreItemSaver(ignoreItem).execute();
			}
			if (v.getId() == textViewTransactionCategory.getId()) {
				showCategoryChooser(radioGrpTransactionType,
						textViewTransactionCategory);
			} else if (buttonShowTranSource.getId() == v.getId()) {
				AlertDialog.Builder transactionSourceDialogBuilder = new Builder(
						context);
				transactionSourceDialogBuilder.setMessage(transaction
						.getSource());
				transactionSourceDialogBuilder.setPositiveButton("OK",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
							}
						});
				transactionSourceDialogBuilder.show();
			}
			if (buttonDeleteTransaction != null
					&& v.getId() == buttonDeleteTransaction.getId()) {
				showConfirmationDialogOnDelete(transaction);

			} else if (buttonUpdateTransaction != null
					&& v.getId() == buttonUpdateTransaction.getId()) {

				StringBuilder modificationMessage = null;

				modificationMessage = new StringBuilder(
						"On "
								+ CommonUtility.DATE_FORMATTER_WITHOUT_TIME
										.format(new Date()) + " ");

				if (editTextAmount.getText() != null
						&& editTextAmount.getText().toString().length() > 0) {

					boolean hasInformationChanged = false;
					String messageStarter = "You changed ";

					if (!editTextAmount.getText().toString().trim()
							.equals(transaction.getAmount() + "")) {

						hasInformationChanged = true;

						modificationMessage
								.append("You changed Transaction amount from "
										+ transaction.getAmount() + " to "
										+ editTextAmount.getText().toString()
										+ ".\n");
						float transactionAmount = Float
								.parseFloat(editTextAmount.getText().toString()
										.trim());
						transactionAmount = (float) Math
								.round(transactionAmount * 100) / 100;
						transaction.setAmount(transactionAmount);
					}
					if (!editTextDesc.getText().toString().trim()
							.equals(transaction.getDesc())) {

						modificationMessage.append(messageStarter);

						modificationMessage
								.append("Transaction description from \""
										+ transaction.getDesc()
										+ "\" to \""
										+ editTextDesc.getText().toString()
												.trim() + ".\n");

						hasInformationChanged = true;

						transaction.setDesc(editTextDesc.getText().toString()
								.trim());

					}
					String transactionDate = CommonUtility.DATE_FORMATTER_WITHOUT_TIME
							.format(transaction.getTransactionDate());

					if (!textViewDate.getText().toString().trim()
							.equals(transactionDate)) {

						modificationMessage.append(messageStarter);

						modificationMessage.append("Transaction date from "
								+ transactionDate + " to "
								+ textViewDate.getText().toString() + ".\n");

						hasInformationChanged = true;
						try {
							transaction
									.setTransactionDate(CommonUtility.DATE_FORMATTER_WITHOUT_TIME
											.parse(textViewDate.getText()
													.toString().trim()));
						} catch (ParseException e) {
							e.printStackTrace();
						}

					}
					if (transaction.getType() == Transaction.EXPENSE) {
						if (radioGrpTransactionType.getCheckedRadioButtonId() == R.id.rbg_inc_history) {

							modificationMessage.append(messageStarter);
							modificationMessage
									.append("Transaction type to Income.\n");
							hasInformationChanged = true;
							transaction.setType(Transaction.INCOME);
						}

					} else {
						if (radioGrpTransactionType.getCheckedRadioButtonId() == R.id.rbg_exp_history) {

							modificationMessage.append(messageStarter);

							modificationMessage
									.append("Transaction type to Expense.");
							hasInformationChanged = true;
							transaction.setType(Transaction.EXPENSE);
						}
					}
					if (!textViewTransactionCategory
							.getText()
							.toString()
							.equals(transaction.getCategory().getCategoryName())) {
						modificationMessage.append(messageStarter);
						modificationMessage
								.append("Changed Transaction category from "
										+ transaction.getCategory()
												.getCategoryName() + " to "
										+ textViewTransactionCategory.getText());
						transaction.setCategory(new TransactionCategory(
								transaction.getType(),
								textViewTransactionCategory.getText()
										.toString()));
						hasInformationChanged = true;
					}
					if (hasInformationChanged) {
						try {

							(new TransactionUpdaterAsync(
									new TransactionHistory(
											transaction.getId(),
											modificationMessage.toString(),
											CommonUtility.DATE_FORMATTER_WITHOUT_TIME
													.parse(CommonUtility.DATE_FORMATTER_WITHOUT_TIME
															.format(new Date())))))
									.execute();

						} catch (ParseException e) {
							e.printStackTrace();
							Toast.makeText(context,
									"Something went wrong! please retry",
									Toast.LENGTH_SHORT).show();
						}
					}
				} else {
					Toast.makeText(context, "Please enter amount",
							Toast.LENGTH_SHORT).show();
				}
			}
		}
	}

	@Override
	public int getItemCount() {
		return transactionHistoryList.size() + 1;
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {

		// header of recyclerview
		if (getItemViewType(position) == 0) {

			holder.textViewDate
					.setText(CommonUtility.DATE_FORMATTER_WITHOUT_TIME
							.format(transaction.getTransactionDate()));
			TextDatePicker datePicker = new TextDatePicker(context,
					holder.textViewDate);
			holder.textViewDate.setOnClickListener(datePicker);
			if (transaction.getType() == Transaction.INCOME)
				holder.radioGrpTransactionType.check(R.id.rbg_inc_history);
			else
				holder.radioGrpTransactionType.check(R.id.rbg_exp_history);
			if (transaction.getSource().toString().trim().length() > 0) {

				holder.smsLayout.setVisibility(View.VISIBLE);
			} else {
				holder.smsLayout.setVisibility(View.GONE);
			}
			holder.editTextAmount.setText(transaction.getAmount() + "");
			holder.editTextDesc.setText(transaction.getDesc());

			holder.textViewTransactionCategory.setText(transaction
					.getCategory().getCategoryName());
		}
		// rows of recyclerview
		else {
			position--;
			holder.textViewTransactionHistoryDetails
					.setText(transactionHistoryList.get(position).getMessage());
		}
	}

	private void showCategoryChooser(final RadioGroup radioGrpTransactionType,
			final TextView textViewTransactionCategory) {

		Builder builderCategoryChooser = new Builder(context);
		String title = "Select ";

		if (radioGrpTransactionType.getCheckedRadioButtonId() == R.id.rbg_inc_history) {
			title += "Income Category";
		} else {
			title += "Expense Category";
		}
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		lp.setMargins(10, 10, 10, 20);
		builderCategoryChooser.setTitle(title);

		final Button buttonCreateNewCategory = new Button(context);
		buttonCreateNewCategory.setText("Create New Category");
		buttonCreateNewCategory.setLayoutParams(lp);
		builderCategoryChooser.setView(buttonCreateNewCategory);

		final ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(
				context, android.R.layout.select_dialog_singlechoice,
				categoryNames);
		builderCategoryChooser.setAdapter(categoryAdapter,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						textViewTransactionCategory.setText(categoryAdapter
								.getItem(which));
					}
				});
		final AlertDialog dialog = builderCategoryChooser.create();
		buttonCreateNewCategory.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
				showCategoryCreationDialog(radioGrpTransactionType,
						textViewTransactionCategory);
			}
		});

		dialog.show();
	}

	// show dialog to create new category
	private void showCategoryCreationDialog(
			final RadioGroup radioGrpTransactionType,
			final TextView textViewTransactionCategory) {
		AlertDialog.Builder categoryCreationBuilder = new Builder(context);
		categoryCreationBuilder.setTitle("Create new category");
		final EditText edittextCategoryName = new EditText(context);
		edittextCategoryName
				.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
		categoryCreationBuilder.setView(edittextCategoryName);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		edittextCategoryName.setLayoutParams(lp);
		edittextCategoryName.setGravity(Gravity.CENTER);
		categoryCreationBuilder.setPositiveButton("Create",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (!(edittextCategoryName.getText().toString().trim()
								.length() == 0)) {
							textViewTransactionCategory
									.setText(edittextCategoryName.getText()
											.toString().trim()
											.toUpperCase(Locale.ENGLISH));
							int tranType = Transaction.EXPENSE;
							if (radioGrpTransactionType
									.getCheckedRadioButtonId() == R.id.rbg_inc) {
								tranType = Transaction.INCOME;

							}
							TransactionCategory newCategory = new TransactionCategory(
									tranType, textViewTransactionCategory
											.getText().toString());
							new CategoryCreatorAsync().execute(newCategory);
						}
					}
				});
		categoryCreationBuilder.show();
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup viewGrp, int viewType) {
		View view = null;
		if (viewType == 0) {
			view = inflater.inflate(R.layout.transaction_history_row_layout,
					viewGrp, false);
		} else {
			view = inflater.inflate(R.layout.simple_text_view, viewGrp, false);
		}
		return new ViewHolder(view, viewType);
	}

	@Override
	public int getItemViewType(int position) {
		if (position == 0)
			return 0;
		return 1;
	}

	/**
	 * class updates transaction information asynchrously
	 * 
	 * @author nayan
	 *
	 */
	class TransactionUpdaterAsync extends AsyncTask<Void, Void, Void> {
		ProgressDialog pDialog;
		long result;
		TransactionHistory transactionHistory;

		TransactionUpdaterAsync(TransactionHistory transactionHistory) {
			this.transactionHistory = transactionHistory;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(context);
			pDialog.setMessage("Saving your changes");
		}

		@Override
		protected Void doInBackground(Void... params) {
			DatabaseHelper dbHelper = new DatabaseHelper(context);
			result = dbHelper.addTransactionHistory(transactionHistory,
					transaction);
			return null;
		}

		@Override
		protected void onPostExecute(Void res) {
			super.onPostExecute(res);
			pDialog.dismiss();
			if (result > 0) {
				transactionHistoryList.add(0, transactionHistory);
				notifyDataSetChanged();
			} else {
				Toast.makeText(context, "Something went wrong! please retry",
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	// perform db operations of creating a new category in worker thread
	class CategoryCreatorAsync extends
			AsyncTask<TransactionCategory, Void, Void> {
		boolean isError;

		TransactionCategory transactionCategory;

		@Override
		protected Void doInBackground(TransactionCategory... params) {
			try {
				transactionCategory = params[0];
				dbHelper.addTransactionCategory(params[0]);

			} catch (Exception e) {
				isError = true;
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if (isError) {
				Toast.makeText(
						context,
						transactionCategory.getCategoryName()
								+ " could not be created! please retry",
						Toast.LENGTH_SHORT).show();
			} else {
				categories.add(transactionCategory);
				categoryNames.add(transactionCategory.getCategoryName());
			}

		}
	}

	// fill category names to list as user changes preferences
	private void fillCategoryNames(int checkedId) {
		int tranType = Transaction.EXPENSE;
		if (checkedId == R.id.rbg_inc_history) {
			tranType = Transaction.INCOME;
		}
		categoryNames.clear();
		for (TransactionCategory categoryinstannce : categories) {
			if (categoryinstannce.getTransactionType() == tranType)
				categoryNames.add(categoryinstannce.getCategoryName());
		}
	}

	/**
	 * saves sender in the ignore list
	 * 
	 * @author nayan
	 *
	 */
	private class IgnoreItemSaver extends AsyncTask<Void, Void, Void> {
		IgnoreItem item;
		boolean isException;

		IgnoreItemSaver(IgnoreItem item) {
			this.item = item;
		}

		@Override
		protected Void doInBackground(Void... params) {
			try {
				dbHelper.addIgonreItem(item);
			} catch (Exception e) {
				isException = true;
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if (isException) {
				Toast.makeText(context, "Preference could not be saved",
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(context, "Preference has been saved",
						Toast.LENGTH_SHORT).show();
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
}
