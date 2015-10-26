package com.artoo.personalfinance;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import persistantData.DatabaseHelper;
import model.Transaction;
import model.TransactionCategory;
import utills.CommonUtility;
import widgets.TextDatePicker;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

/**
 * fragment allows user to manually enter transaction
 * 
 * @author nayan
 *
 */
public class ManualTransactionEntryFragment extends Fragment implements
		OnClickListener {
	private TextView textViewDate, textViewTransactionCategory;
	private RadioGroup radioGrpTransactionType;
	private Button buttonSaveTransaction;
	private EditText editTextAmount, editTextDesc;
	private List<TransactionCategory> categories;
	private List<String> categoryNames;
	Button buttonCreateNewCategory;
	DatabaseHelper dbHelper;
	AlertDialog categoryDialog;
	AlertDialog.Builder builderCategoryChooser;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.manual_transaction_entry_screen,
				container, false);

		// intializing UI controls
		textViewDate = (TextView) view.findViewById(R.id.tran_date_edt);
		textViewTransactionCategory = (TextView) view
				.findViewById(R.id.tran_category_text_view);
		radioGrpTransactionType = (RadioGroup) view
				.findViewById(R.id.tran_type_radio_grp);
		buttonSaveTransaction = (Button) view.findViewById(R.id.tran_save_btn);
		editTextAmount = (EditText) view.findViewById(R.id.tran_amt_edt);
		editTextDesc = (EditText) view.findViewById(R.id.tran_note_edt);

		categoryNames = new ArrayList<String>();
		// setting todays date as default date into date selecter textview
		// datetextview
		textViewDate.setText(CommonUtility.DATE_FORMATTER_WITHOUT_TIME
				.format(new Date()));
		dbHelper = new DatabaseHelper(getActivity());
		new CategoryLoaderAsync().execute();

		// creating new textdatepicker obbject to be opened on click of
		// datetextview
		TextDatePicker datePicker = new TextDatePicker(getActivity(),
				textViewDate);
		textViewDate.setOnClickListener(datePicker);
		textViewTransactionCategory.setOnClickListener(this);
		buttonSaveTransaction.setOnClickListener(this);

		// changing transaction categories at runtime
		radioGrpTransactionType
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						textViewTransactionCategory
								.setText(R.string.default_category);
						fillCategoryNames(checkedId);
					}
				});
		return view;
	}

	@Override
	public void onStart() {
		super.onStart();

	}

	/**
	 * validates if user has entered amount and selected transaction type, if
	 * not warns user
	 * 
	 * @return
	 */
	private boolean validateData() {
		if (editTextAmount.getText().toString() == null
				|| editTextAmount.getText().toString().trim().length() == 0) {
			Toast.makeText(getActivity(), "Please Enter Transaction Amount",
					Toast.LENGTH_SHORT).show();
			return false;
		}
		if (radioGrpTransactionType.getCheckedRadioButtonId() == -1) {
			Toast.makeText(getActivity(), "Please Select Transaction Type",
					Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == buttonSaveTransaction.getId()) {
			InputMethodManager imm = (InputMethodManager) getActivity()
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(getActivity().getCurrentFocus()
					.getWindowToken(), 0);
			if (validateData()) {
				int tranType = Transaction.EXPENSE;
				if (radioGrpTransactionType.getCheckedRadioButtonId() == R.id.rbg_inc) {
					tranType = Transaction.INCOME;
				}
				try {
					float transactionAmount = Float.parseFloat(editTextAmount
							.getText().toString());
					transactionAmount = (float) Math
							.round(transactionAmount * 100) / 100;
					Transaction transaction = new Transaction(
							transactionAmount, (byte) tranType, editTextDesc
									.getText().toString().trim(),
							CommonUtility.DATE_FORMATTER_WITHOUT_TIME
									.parse(textViewDate.getText().toString()));
					if (!textViewTransactionCategory
							.getText()
							.toString()
							.equals(getResources().getString(
									R.string.default_category))) {

						TransactionCategory transactionCategory = new TransactionCategory(
								transaction.getType(),
								textViewTransactionCategory.getText()
										.toString());

						transaction.setCategory(transactionCategory);
					}
					dbHelper.addTransaction(transaction);
					getActivity().onBackPressed();
				} catch (Exception e) {
					Toast.makeText(getActivity(),
							"Something went wrong! please retry",
							Toast.LENGTH_SHORT).show();
				}

			}
		} else if (v.getId() == textViewTransactionCategory.getId()) {
			showCategoryChooser();
		}

		// dialog controls for category selection and creation
		else if (v.getId() == buttonCreateNewCategory.getId()) {
			showCategoryCreationDialog();
			categoryDialog.dismiss();
		}
	}

	private void fillCategoryNames(int checkedId) {
		int tranType = Transaction.EXPENSE;
		if (checkedId == R.id.rbg_inc) {
			tranType = Transaction.INCOME;
		}
		categoryNames.clear();
		for (TransactionCategory categoryinstannce : categories) {
			if (categoryinstannce.getTransactionType() == tranType)
				categoryNames.add(categoryinstannce.getCategoryName());
		}
	}

	private void showCategoryChooser() {

		builderCategoryChooser = new Builder(getActivity());
		String title = "Select ";

		if (radioGrpTransactionType.getCheckedRadioButtonId() == R.id.rbg_inc) {
			title += "Income Category";
		} else {
			title += "Expense Category";
		}
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		lp.setMargins(10, 10, 10, 20);
		builderCategoryChooser.setTitle(title);

		buttonCreateNewCategory = new Button(getActivity());
		buttonCreateNewCategory.setText("Create New Category");
		buttonCreateNewCategory.setLayoutParams(lp);
		builderCategoryChooser.setView(buttonCreateNewCategory);
		buttonCreateNewCategory.setOnClickListener(this);
		final ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(
				getActivity(), android.R.layout.select_dialog_singlechoice,
				categoryNames);
		builderCategoryChooser.setAdapter(categoryAdapter,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						textViewTransactionCategory.setText(categoryAdapter
								.getItem(which));
					}
				});

		categoryDialog = builderCategoryChooser.create();
		categoryDialog.show();

	}

	// show dialog to create new category
	private void showCategoryCreationDialog() {
		AlertDialog.Builder categoryCreationBuilder = new Builder(getActivity());
		categoryCreationBuilder.setTitle("Create new category");
		final EditText edittextCategoryName = new EditText(getActivity());
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
						getActivity(),
						transactionCategory.getCategoryName()
								+ " could not be created! please retry",
						Toast.LENGTH_SHORT).show();
			} else {
				categories.add(transactionCategory);
				categoryNames.add(transactionCategory.getCategoryName());
			}

		}
	}

	/**
	 * class loads transaction categories
	 * 
	 * @author nayan
	 *
	 */
	class CategoryLoaderAsync extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			categories = dbHelper.getAllCategory();
			for (TransactionCategory t : categories) {
				if (t.getTransactionType() == Transaction.INCOME)
					categoryNames.add(t.getCategoryName());
			}
			return null;
		}

	}
}
