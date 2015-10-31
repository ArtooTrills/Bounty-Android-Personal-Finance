package com.artoo.finac;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

public class AddTxn extends Fragment {

    //  DM
    private Button buttonAddTxn;
    private RadioButton radioButtonCredit;
    private RadioButton radioButtonDebit;
    private Spinner spinnerCategory;
    private EditText editTextAmount;

    private SharedPreferences settings;
    private SQLiteDatabase db;

    final static String TAG = "Finac AddTxn";

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        db = getActivity().openOrCreateDatabase(Constants.DB_NAME, Context.MODE_PRIVATE, null);
        settings = PreferenceManager.getDefaultSharedPreferences(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_txn, container, false);

        final ArrayAdapter spinnerAdapterCredit = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.CreditCategory));
        final ArrayAdapter spinnerAdapterDebit = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.DebitCategory));

        //  Referencing
        buttonAddTxn = (Button) view.findViewById(R.id.buttonAddTxn);
        radioButtonCredit = (RadioButton) view.findViewById(R.id.radioButtonCredit);
        radioButtonDebit = (RadioButton) view.findViewById(R.id.radioButtonDebit);
        spinnerCategory = (Spinner) view.findViewById(R.id.spinnerCategory);
        editTextAmount = (EditText) view.findViewById(R.id.editTextAmount);

        //  Default Adapter
        spinnerCategory.setAdapter(spinnerAdapterDebit);

        //  Listeners
        buttonAddTxn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    String am = editTextAmount.getText().toString();
                    Float amount = Float.parseFloat(am);
                    String category = spinnerCategory.getSelectedItem().toString();
                    String type = radioButtonCredit.isChecked()?"CR":"DB";

                    String query = "INSERT INTO `transaction` (`amount`, `category`, `type`) VALUES(" + amount + ", '" + category + "', '"+type+"')";
                    try {
                        db.execSQL(query);
                        Log.d(TAG, "Transaction added to the database!");

                        editTextAmount.setText("");
                        Toast.makeText(AddTxn.this.getActivity(), "" + amount + " " + (type.equals("CR")?"Credited!":"Debited!"), Toast.LENGTH_SHORT).show();

                        //  Update Preference for quick reference.
                        SharedPreferences.Editor editor = settings.edit();
                        Float inHand = settings.getFloat("inHand",0.0f);
                        inHand += type.equals("CR")?(amount):(-amount);
                        editor.putFloat("inHand",inHand);
                        editor.apply();

                        //  Send Broadcasts.
                        Intent intent = new Intent();
                        intent.setAction(Constants.COM_ARTOO_FINAC_ADDED_TRANSACTIONS);
                        getActivity().sendBroadcast(intent);

                    } catch (SQLException e) {

                        Log.d(TAG, "Error SQL Insertion. Message: " + e.getMessage());
                    }
                } catch (Exception e) {

                    Log.d(TAG, "Error Parsing. Mesasge: " + e.getMessage());
                }
            }
        });

        radioButtonCredit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                spinnerCategory.setAdapter(spinnerAdapterCredit);
            }
        });

        radioButtonDebit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                spinnerCategory.setAdapter(spinnerAdapterDebit);
            }
        });

        return view;
    }
}
