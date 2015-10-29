package com.manage.ak.moneyreport;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class AddDialog extends Dialog implements View.OnClickListener {

    private String amt, type, balance;

    private EditText addAmt;

    Context context;

    List<Sms> cashList = new ArrayList<>();

    Sms transaction;

    public OnSaveClickListener onSaveClickListener;

    public AddDialog(Context context, List<Sms> cashList) {
        super(context);
        this.context = context;
        this.cashList = cashList;

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCancelable(true);
        setContentView(R.layout.add_dialog);

        addAmt = (EditText) findViewById(R.id.addAmt);
        Spinner description = (Spinner) findViewById(R.id.description);
        Button save = (Button) findViewById(R.id.save);

        ArrayAdapter<CharSequence> desc_adapter = ArrayAdapter.createFromResource(context, R.array.desc_array, android.R.layout.simple_spinner_item);
        desc_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        description.setAdapter(desc_adapter);

        description.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                type = parent.getItemAtPosition(0).toString();
            }
        });

        save.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save:
                // then according to the id of check button balance is calculated
                int index = cashList.size() - 1;
                amt = addAmt.getText().toString();
                if (amt.trim().equals("")) {
                    amt = "0.0";
                }

                String t = "";
                if (type.equals("Personal Expenses") || type.equals("Food") || type.equals("Transport"))
                    t = "DR";
                else if (type.equals("Income") || type.equals("Salary"))
                    t = "CR";

                switch (t) {
                    case "DR":
                        // index will be zero if there are no messages in the list so balance will be equal to the amount
                        if (index >= 0) {
                            balance = cashList.get(0).getMsgBal();
                            balance = Double.toString(Double.parseDouble(balance) - Double.parseDouble(amt));
                        } else {
                            // negative in case of expenses
                            balance = "-" + amt;
                        }
                        break;
                    case "CR":
                        if (index >= 0) {
                            balance = cashList.get(0).getMsgBal();
                            balance = Double.toString(Double.parseDouble(balance) + Double.parseDouble(amt));
                        } else {
                            // positive in case of income
                            balance = amt;
                        }
                        break;
                }
                // finally the sms object is added to the list
                // the date in this sms object is set ot the current date of the system
                long time = System.currentTimeMillis();
                transaction = new Sms(type, amt, Long.toString(time), balance);
                dismiss();
                onSaveClickListener.OnSaveClick();
                break;
        }
    }

    public void setOnSaveClickListener(OnSaveClickListener onSaveClickListener) {
        this.onSaveClickListener = onSaveClickListener;
    }

    public interface OnSaveClickListener {
        public void OnSaveClick();
    }

    public Sms getTransaction() {
        return transaction;
    }
}
