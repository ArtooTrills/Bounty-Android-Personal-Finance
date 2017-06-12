package com.examples.ankit.breakpoint.sms;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.examples.ankit.breakpoint.R;
import com.examples.ankit.breakpoint.models.Transactions;
import com.examples.ankit.breakpoint.prefences.MyPreferenceManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SmsLoadingFragment extends Fragment {
    public static final int REQUEST_CODE_SMS_PERMISSION = 123;
    public static final String READ_SMS = Manifest.permission.READ_SMS;
    private OnSmsLoadingListener mListener;
    @BindView(R.id.loading)
    ProgressBar mLoading;

    @BindView(R.id.message_loading_container)
    RelativeLayout mContainer;

    @BindView(R.id.txt_loading)
    TextView mLoadingText;

    public SmsLoadingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sms_loading, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateTransactionsDb();
    }

    /**
     * It checks for permission, read from SMS and updates transactions DB.
     */
    private void updateTransactionsDb() {
        if (mayRequestContacts()) {
            Transactions transactions = SmsUtil.fetchInbox();
            if (transactions != null && transactions.getTransactions() != null && !transactions.getTransactions().isEmpty()) {
                MyPreferenceManager.setTransactions(transactions);
            }

            if (MyPreferenceManager.getTransactions() != null) {
                mListener.onSmsLoaded();
            } else {
                mLoading.setVisibility(View.INVISIBLE);
                mLoadingText.setText(R.string.no_sms);
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSmsLoadingListener) {
            mListener = (OnSmsLoadingListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnSmsLoadingListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * Checks for SMS Read permission if user has not given, it shows a dialog asking for it.
     */
    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (ContextCompat.checkSelfPermission(getActivity(), READ_SMS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_SMS)) {
            Snackbar.make(mLoading, R.string.sms_permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_SMS}, REQUEST_CODE_SMS_PERMISSION);
                        }
                    }).show();
        } else {
            requestPermissions(new String[]{READ_SMS}, REQUEST_CODE_SMS_PERMISSION);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_SMS_PERMISSION) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                updateTransactionsDb();
            }
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     **/
    public interface OnSmsLoadingListener {
        // TODO: Update argument type and name
        void onSmsLoaded();
    }

}
