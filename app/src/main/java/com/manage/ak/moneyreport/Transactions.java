package com.manage.ak.moneyreport;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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

public class Transactions extends AppCompatActivity {

    private Context context = this;

    // A list of transactions
    List<Sms> transactions = new ArrayList<>();

    // Total cash spent from the cash list
    private String CASHSPENT = "0.0";

    // To display list of transactions
    RecyclerView transList;

    // Database to store the transactions added
    DatabaseHandler databaseHandler = new DatabaseHandler(context);

    // Custom adapter to link recycler view
    private MyAdapter myAdapter;

    // card type and color of the card received from the main activity are store in these var
    private String color;
    private String card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transactions);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("DATA");
        card = bundle.getString("CARD");
        color = bundle.getString("COLOR");
        if (card.equals("CASH_CARD")) {
            CASHSPENT = bundle.getString("SPENT");
            transactions = (ArrayList<Sms>) bundle.getSerializable("CASH");
        } else {
            transactions = (ArrayList<Sms>) bundle.getSerializable("SMS");
        }

        // To add a custom action bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(Color.parseColor(color));

        transList = (RecyclerView) findViewById(R.id.transList);
        transList.setHasFixedSize(true);
        transList.setLayoutManager(new LinearLayoutManager(this));

        myAdapter = new MyAdapter(transactions, context);
        transList.setAdapter(myAdapter);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    // setting the result cashList back to the main activity on back button press
    @Override
    public void onBackPressed() {
        sendDataBack();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (card.equals("CASH_CARD"))
            getMenuInflater().inflate(R.menu.cash_menu, menu);
        else
            getMenuInflater().inflate(R.menu.bank_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // setting the result cashList back to the main activity on back button press
            case android.R.id.home:
                sendDataBack();
                break;
            case R.id.add:
                final AddDialog dialog = new AddDialog(context, transactions);
                dialog.show();
                dialog.setOnSaveClickListener(new AddDialog.OnSaveClickListener() {
                    @Override
                    public void OnSaveClick() {
                        Sms s = dialog.getTransaction();
                        transactions.add(0, s);
                        databaseHandler.addCashSms(s);

                        transList.scrollToPosition(0);
                        myAdapter.notifyItemInserted(0);

                        if (s.getDrOrCr().equals("DR")) {
                            CASHSPENT = Double.toString(Double.parseDouble(CASHSPENT) + s.getAmtDouble());
                        }

                        Toast.makeText(Transactions.this, "Transaction Added", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.forward:
                List<Sms> smsList1 = new ArrayList<>();
                for (Sms s : transactions) {
                    if (s.getDrOrCr().equals("DR")) {
                        smsList1.add(s);
                    }
                }
                // when forward action button is clicked a bar chart is displayed whose values are calculated here
                if (smsList1.size() > 0) {
                    Intent i = new Intent(Transactions.this, report.class);
                    Bundle b = new Bundle();
                    b.putSerializable("SMS", (Serializable) smsList1);
                    // color is sent to the report activity depending on click of bank or cash card
                    b.putString("color", color);
                    i.putExtra("DATA", b);
                    startActivity(i);
                } else {
                    // if no messages are there then a toast is displayed
                    Toast.makeText(Transactions.this, "You have not spent money", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void sendDataBack() {
        if (card.equals("CASH_CARD")) {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putSerializable("cash", (Serializable) transactions);
            bundle.putString("SPENT", CASHSPENT);
            intent.putExtra("Result", bundle);
            setResult(1, intent);
        }
        finish();
    }
}
