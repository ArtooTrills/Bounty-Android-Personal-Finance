package com.manage.ak.moneyreport;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// activity to show cash transactions in the cash_transactions.xml layout
public class CashTransactions extends AppCompatActivity {

    private Context context = this;

    // A list of sms required
    private List<Sms> cashList = new ArrayList<>();

    // variables used to store and calculate balance in the action bar add button
    private String amt, type, balance;
    private double bal;
    private String cashSpent = "0.0";

    RecyclerView readCash;

    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cash_transactions);

        // To add a custom action bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        readCash = (RecyclerView) findViewById(R.id.readCash);
        readCash.setHasFixedSize(true);
        readCash.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("DATA");
        cashSpent = bundle.getString("Spent");
        cashList = (ArrayList<Sms>) bundle.getSerializable("CASH");

        myAdapter = new MyAdapter(cashList, context);
        readCash.setAdapter(myAdapter);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    // setting the result cashList back to the main activity on back button press
    @Override
    public void onBackPressed() {
        Intent i = new Intent();
        Bundle b = new Bundle();
        b.putSerializable("cash", (Serializable) cashList);
        b.putString("SPENT", cashSpent);
        i.putExtra("Result", b);
        setResult(1, i);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cash_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // setting the result cashList back to the main activity on back button press
            case android.R.id.home:
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("cash", (Serializable) cashList);
                bundle.putString("SPENT", cashSpent);
                intent.putExtra("Result", bundle);
                setResult(1, intent);
                finish();
                break;
            case R.id.add:
                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.add_dialog);

                final EditText addAmt = (EditText) dialog.findViewById(R.id.addAmt);

                final Spinner description = (Spinner) dialog.findViewById(R.id.description);
                ArrayAdapter<CharSequence> desc_adapter = ArrayAdapter.createFromResource(this, R.array.desc_array, android.R.layout.simple_spinner_item);
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

                Button save = (Button) dialog.findViewById(R.id.save);

                dialog.show();

                // set onclick listener to add button in the custom dialog box to add transaction
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
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
                                    bal = Double.parseDouble(balance) - Double.parseDouble(amt);
                                    balance = Double.toString(bal);
                                } else {
                                    // negative in case of expenses
                                    balance = "-" + amt;
                                }
                                break;
                            case "CR":
                                if (index >= 0) {
                                    balance = cashList.get(0).getMsgBal();
                                    bal = Double.parseDouble(balance) + Double.parseDouble(amt);
                                    balance = Double.toString(bal);
                                } else {
                                    // positive in case of income
                                    balance = amt;
                                }
                                break;
                        }
                        // finally the sms object is added to the list
                        // the date in this sms object is set ot the current date of the system
                        long time = System.currentTimeMillis();

                        Sms s = new Sms(type, amt, Long.toString(time), balance);
                        cashList.add(0, s);

                        dialog.dismiss();

                        readCash.scrollToPosition(0);
                        myAdapter.notifyItemInserted(0);

                        Toast.makeText(CashTransactions.this, "Transaction Added", Toast.LENGTH_SHORT).show();
                        if (s.getMsgType().equals("Personal Expenses") || s.getMsgType().equals("Food") || s.getMsgType().equals("Transport")) {
                            cashSpent = Double.toString(Double.parseDouble(cashSpent) + Double.parseDouble(amt));
                        }
                    }
                });
                break;
            case R.id.forward:
                List<Sms> smsList1 = new ArrayList<>();
                for (Sms s : cashList) {
                    if (s.getMsgType().equals("Personal Expenses") || s.getMsgType().equals("Food") || s.getMsgType().equals("Transport")) {
                        smsList1.add(s);
                    }
                }
                // when forward action button is clicked a bar chart is displayed whose values are calculated here
                if (smsList1.size() > 0) {
                    Intent i = new Intent(CashTransactions.this, report.class);
                    Bundle b = new Bundle();
                    b.putSerializable("SMS", (Serializable) smsList1);
                    // color is sent to the report activity depending on click of bank or cash card
                    b.putString("color", "#467fd9");
                    i.putExtra("DATA", b);
                    startActivity(i);
                } else {
                    // if no messages are there then a toast is displayed
                    Toast.makeText(CashTransactions.this, "You have not spent money", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public String getDate(long milliSeconds) {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MMM/yy");
        return formatter.format(new Date(milliSeconds));
    }
}
