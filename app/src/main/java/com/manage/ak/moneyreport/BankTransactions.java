package com.manage.ak.moneyreport;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// activity to show cash transactions in the bank_transactions.xml layout
public class BankTransactions extends AppCompatActivity {

    private Context context = this;

    // A list of sms required
    private List<Sms> bankList = new ArrayList<>();

    RecyclerView readMessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bank_transactions);

        // To add a custom action bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        readMessages = (RecyclerView) findViewById(R.id.readMessages);
        readMessages.setHasFixedSize(true);
        readMessages.setLayoutManager(new LinearLayoutManager(this));

        // bankList received from MainActivity class
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("DATA");
        bankList = (ArrayList<Sms>) bundle.getSerializable("SMS");

        MyAdapter myAdapter = new MyAdapter(bankList, context);
        readMessages.setAdapter(myAdapter);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bank_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            // click on the graph icon starts the report activity
            case R.id.forward:
                List<Sms> smsList1 = new ArrayList<>();
                for (Sms s : bankList) {
                    if (s.getMsgType().equals("Personal Expenses") || s.getMsgType().equals("Food") || s.getMsgType().equals("Transport")) {
                        smsList1.add(s);
                    }
                }
                // when graph action button is clicked a bar chart is displayed whose values are calculated here
                if (smsList1.size() > 0) {
                    Intent i = new Intent(BankTransactions.this, report.class);
                    Bundle b = new Bundle();
                    b.putSerializable("SMS", (Serializable) smsList1);
                    // color is sent to the report activity depending on click of bank card
                    b.putString("color", "#6ed036");
                    i.putExtra("DATA", b);
                    startActivity(i);
                } else {
                    // if no messages are there then a toast is displayed
                    Toast.makeText(BankTransactions.this, "You have not spent money", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
