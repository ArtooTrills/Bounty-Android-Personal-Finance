package com.examples.ankit.breakpoint;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.examples.ankit.breakpoint.models.Transaction;
import com.examples.ankit.breakpoint.models.Transactions;
import com.examples.ankit.breakpoint.prefences.MyPreferenceManager;
import com.examples.ankit.breakpoint.utils.DateUtil;
import com.examples.ankit.breakpoint.view.DatePickerFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnAddExpenseListener} interface
 * to handle interaction events.
 */
public class AddExpenseFragment extends Fragment implements DatePickerDialog.OnDateSetListener {
    private static final String TRANSACTION = "transaction";
    private OnAddExpenseListener mListener;
    @BindView(R.id.expense_category_spinner)
    Spinner mExpenseCategorySpinner;

    @BindView(R.id.date_spinner)
    Spinner mDateSpinner;

    @BindView(R.id.expense_spinner)
    Spinner mExpenseSpinner;

    @BindView(R.id.et_name)
    EditText mExpenseName;

    @BindView(R.id.et_amount)
    EditText mExpenseAmount;

    private Calendar mCalendar;
    private boolean mDateSet;
    private Transaction mTransaction;

    public AddExpenseFragment() {
        // Required empty public constructor
    }

    public static AddExpenseFragment getInstance(String transaction) {
        Bundle extra = new Bundle();
        extra.putString(TRANSACTION, transaction);
        AddExpenseFragment fragment = new AddExpenseFragment();
        fragment.setArguments(extra);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mCalendar = Calendar.getInstance();
        initializeExpenseCategorySpinnerText();

        if (getArguments() != null && getArguments().containsKey(TRANSACTION)) {
            //this is the case of edit a transaction.
            mTransaction = Gson.getInstance().fromJson(getArguments().getString(TRANSACTION), Transaction.class);
            mExpenseName.setText(mTransaction.getName());
            setPointerInLast(mExpenseName);

            mExpenseAmount.setText(Double.toString(mTransaction.getAmount()));
            setPointerInLast(mExpenseAmount);

            mExpenseCategorySpinner.setSelection(mTransaction.getExpenseOrIncomeCategory());
            mExpenseSpinner.setSelection(mTransaction.getType());
            mCalendar.setTimeInMillis(mTransaction.getDate().getTime());
            setSpinnerText(DateUtil.dateToString(mTransaction.getDate()));

            mDateSet = true;
            getActivity().setTitle(getString(R.string.edit));
        } else {
            setSpinnerText(getString(R.string.expense_date));
            mDateSet = false;
            getActivity().setTitle(getString(R.string.add));
        }

    }

    public boolean validateInput() {
        boolean valid = true;
        if (TextUtils.isEmpty(mExpenseName.getText())) {
            mExpenseName.setError(getString(R.string.required));
            valid = false;
        }

        if (TextUtils.isEmpty(mExpenseAmount.getText())) {
            mExpenseAmount.setError(getString(R.string.required));
            valid = false;
        }

        if (mExpenseCategorySpinner.getSelectedItemPosition() == 0) {
            TextView textview = (TextView) mExpenseCategorySpinner.getSelectedView();
            textview.setError(getString(R.string.required));
            textview.setTextColor(getActivity().getResources().getColor(android.R.color.holo_red_dark));
            valid = false;
        }

        if (!mDateSet) {
            TextView textview = (TextView) mDateSpinner.getSelectedView();
            textview.setError(getString(R.string.required));
            textview.setTextColor(getActivity().getResources().getColor(android.R.color.holo_red_dark));
            valid = false;
        }
        return valid;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnAddExpenseListener) {
            mListener = (OnAddExpenseListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnAddExpenseListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @OnTouch(R.id.date_spinner)
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            int year = mCalendar.get(Calendar.YEAR);
            int month = mCalendar.get(Calendar.MONTH);
            int day = mCalendar.get(Calendar.DAY_OF_MONTH);

            DatePickerFragment datePickerFragment = DatePickerFragment.getInstance(
                    year, month, day, true);
            datePickerFragment.setDateChangeListener(this);
            datePickerFragment.show(getActivity().getFragmentManager(), "datePicker");
            return true;
        }
        return false;
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        mCalendar.set(Calendar.YEAR, year);
        mCalendar.set(Calendar.MONTH, month);
        mCalendar.set(Calendar.DAY_OF_MONTH, day);
        setDate();
    }

    public void setDate() {
        mDateSet = true;
        setSpinnerText(DateUtil.dateToString(mCalendar.getTime()));
    }

    private void initializeExpenseCategorySpinnerText() {
        String[] categoryArray = getResources().getStringArray(R.array.expense_category);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, categoryArray);
        mExpenseCategorySpinner.setAdapter(adapter);
    }

    private void setSpinnerText(String text) {
        String[] dateArray = {text};

        // Checking this to work around a bug in Jelly Bean which calls onDateSet twice.
        if (mDateSpinner.getAdapter() != null && mDateSpinner.getAdapter().getItem(0).equals(text)) {
            return;
        } else {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_spinner_dropdown_item, dateArray);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mDateSpinner.setAdapter(adapter);
        }
    }

    @OnClick(R.id.btn_save_expense)
    public void onClick(View view) {
        if (validateInput()) {
            Transaction transaction = new Transaction();
            transaction.setId(mCalendar.getTimeInMillis());
            transaction.setType(mExpenseSpinner.getSelectedItemPosition());
            transaction.setExpenseOrIncomeCategory(mExpenseCategorySpinner.getSelectedItemPosition());
            transaction.setName(mExpenseName.getText().toString().trim());
            transaction.setAmount(Double.parseDouble(mExpenseAmount.getText().toString().trim()));
            transaction.setDate(mCalendar.getTime());
            Transactions transactions = MyPreferenceManager.getTransactions();
            if (transactions == null) {
                transactions = new Transactions();
            }
            List<Transaction> transactionsList = transactions.getTransactions();
            if (transactionsList.contains(mTransaction)) {
                //this will remove if this transaction exists
                Log.e(TAG, "List contains mTransaction: " + transactionsList.remove(mTransaction));
            }
            transactionsList.add(0, transaction);
            transactions.setLastChecked(System.currentTimeMillis());
            int month = DateUtil.getMonthFromDate(mCalendar.getTimeInMillis());
            LinkedHashMap<Integer, List<Transaction>> existingMonthlyTransactions = transactions.getMonthlyTransactions();
            List<Transaction> expensesOfMonth = existingMonthlyTransactions.get(month);
            if (expensesOfMonth == null) {
                expensesOfMonth = new ArrayList<>();
            }
            if (expensesOfMonth.contains(mTransaction)) {
                //this will remove if this transaction exists in monthly list
                Log.e(TAG, "monthly List contains mTransaction: " + expensesOfMonth.remove(mTransaction));
            }
            expensesOfMonth.add(transaction);
            existingMonthlyTransactions.put(month, expensesOfMonth);
            transactions.setMonthlyTransactions(existingMonthlyTransactions);
            MyPreferenceManager.setTransactions(transactions);
            if (mListener != null) {
                mListener.onAddExpense(transaction);
            }
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnAddExpenseListener {
        // TODO: Update argument type and name
        void onAddExpense(Transaction transaction);
    }

    /**
     * This will set pointer in last of given edit text.
     */
    private void setPointerInLast(EditText editText) {
        editText.setSelection(editText.getText().length());
    }
}
