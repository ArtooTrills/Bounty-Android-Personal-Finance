package com.examples.ankit.breakpoint.agreements;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.examples.ankit.breakpoint.R;
import com.examples.ankit.breakpoint.prefences.MyPreferenceManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnAgreementInteractionListener} interface
 * to handle interaction events.
 */
public class SmsAgreementFragment extends Fragment {

    private OnAgreementInteractionListener mListener;
    @BindView(R.id.txt_accept)
    TextView accept;

    public SmsAgreementFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_agreement, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnAgreementInteractionListener) {
            mListener = (OnAgreementInteractionListener) context;
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
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnAgreementInteractionListener {
        // TODO: Update argument type and name
        void onAgreementAccepted(boolean accepted);
    }

    @OnClick(R.id.txt_accept)
    public void accept(View view){
        MyPreferenceManager.setUserConsent(true);
        if (mListener != null) {
            mListener.onAgreementAccepted(true);
        }
    }
}
