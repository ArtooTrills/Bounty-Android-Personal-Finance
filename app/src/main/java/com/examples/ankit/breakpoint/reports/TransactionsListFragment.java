package com.examples.ankit.breakpoint.reports;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.examples.ankit.breakpoint.Gson;
import com.examples.ankit.breakpoint.R;
import com.examples.ankit.breakpoint.TransactionDetailsActivity;
import com.examples.ankit.breakpoint.models.Transaction;
import com.examples.ankit.breakpoint.models.Transactions;
import com.examples.ankit.breakpoint.prefences.MyPreferenceManager;
import com.examples.ankit.breakpoint.sms.SmsUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A fragment representing a list of Expenses.
 * <p/>
 */
public class TransactionsListFragment extends ListFragment {
    private static final String TRANSACTION_TYPE = "transaction_type";
    @BindView(R.id.list)
    ListView mListView;

    private int transactionsType;
    private TransactionsAdapter mAdapter;
    private List<Transaction> transactionsList;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TransactionsListFragment() {
    }

    public static TransactionsListFragment getInstance(int type) {
        TransactionsListFragment fragment = new TransactionsListFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(TRANSACTION_TYPE, type);

        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.transactions_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        transactionsType = getArguments().getInt(TRANSACTION_TYPE);
        initialize();
        mAdapter = new TransactionsAdapter(getActivity(), transactionsList);
        mListView.setAdapter(mAdapter);
        getActivity().setTitle(transactionsType == SmsUtil.EXPENSE ? getString(R.string.expense) : getString(R.string.income));
    }

    private void initializeAdapterList(int transactionsType) {
        Transactions transactions = MyPreferenceManager.getTransactions();
        transactionsList = new ArrayList<>();
        if (transactions == null && transactions.getTransactions() == null && transactions.getTransactions().isEmpty()) {
            return;
        }

        for (Transaction transaction : transactions.getTransactions()) {
            if (transaction.getType() == transactionsType) {
                transactionsList.add(transaction);
            }
        }

        Collections.sort(transactionsList, new Comparator<Transaction>() {
            @Override
            public int compare(Transaction transaction1, Transaction transaction2) {
                return (transaction1.getDate().getTime() > transaction2.getDate().getTime() ? -1 : 1);
            }
        });
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent(getActivity(), TransactionDetailsActivity.class);
        intent.putExtra(TransactionDetailsActivity.TRANSACTION, Gson.getInstance().toJson(transactionsList.get(position)));
        startActivity(intent);
    }

    private void initialize() {
        initializeAdapterList(transactionsType);
    }
}
