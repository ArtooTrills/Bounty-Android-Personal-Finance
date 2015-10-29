package com.manage.ak.moneyreport;

import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Context context = this;

    // A list to store bank list and cash sms
    private List<Sms> bankList = new ArrayList<>();
    private List<Sms> cashList = new ArrayList<>();

    // this is used to filter already read messages
    String FILTER = null;

    // Main balance in the bank
    // In many places the balance is needed in String form.
    private String BALANCE = "0.0";
    // Total cash spent from cash in hand
    private String CASHSPENT = "0.0";

    // bank balance and date TextView inside the bank card
    private TextView bankBalance;
    private TextView estimateDate;

    // spent amount and cash in hand inside the cash card
    private TextView spentAmount;
    private TextView cashBalance;

    // report TextViews
    private TextView bankReport;
    private TextView cashReport;

    // BroadcastReceiver listening to the incoming messages
    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    IntentFilter intentFilter = new IntentFilter(SMS_RECEIVED);
    private BroadcastReceiver sms_receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(SMS_RECEIVED)) {
                readMessages();
                Toast.makeText(MainActivity.this, "Reading New SMS...", Toast.LENGTH_SHORT).show();
            }
        }
    };

    DatabaseHandler databaseHandler = new DatabaseHandler(context);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // To add a custom action bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        SharedPreferences savedData = getSharedPreferences("KEY", Context.MODE_PRIVATE);
        if (!savedData.getString("BALANCE", "").equals(""))
            BALANCE = savedData.getString("BALANCE", "");

        if (BALANCE.equals("0.0")) {
            final Dialog bal_dialog = new Dialog(context);
            bal_dialog.setCancelable(false);
            bal_dialog.setContentView(R.layout.balance_dialog);

            final Sms smsBal = new Sms();
            smsBal.setMsgType("Bank Balance");
            smsBal.setMsgDate(Long.toString(System.currentTimeMillis()));

            bal_dialog.setTitle("Current Bank Balance -");

            final EditText etBalance = (EditText) bal_dialog.findViewById(R.id.Balance);
            Button saveBalance = (Button) bal_dialog.findViewById(R.id.saveBalance);
            bal_dialog.show();

            saveBalance.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BALANCE = etBalance.getText().toString();
                    if (BALANCE.trim().equals("")) {
                        BALANCE = "0.0";
                    }
                    bal_dialog.dismiss();

                    smsBal.setMsgAmt(BALANCE);
                    smsBal.setMsgBal(BALANCE);
                    bankList.add(0, smsBal);
                    databaseHandler.addBankSms(smsBal);
                    setBankBalance();
                }
            });
        }

        try {
            // object read is in the form of list<Sms> so iterate over the list to extract all Sms objects.
            for (Sms r : databaseHandler.getAllSms("bankTransactions")) {
                bankList.add(r);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            // object read is in the form of list<Sms> so iterate over the list to extract all Sms objects.
            for (Sms r : databaseHandler.getAllSms("cashTransactions")) {
                cashList.add(r);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!savedData.getString("SPENT", "").equals(""))
            CASHSPENT = savedData.getString("SPENT", "");

        // getting the saved filter
        FILTER = savedData.getString("FILTER", "");

        bankBalance = (TextView) findViewById(R.id.bankBalance);
        estimateDate = (TextView) findViewById(R.id.estimateDate);

        spentAmount = (TextView) findViewById(R.id.spendAmount);
        cashBalance = (TextView) findViewById(R.id.cashBalance);

        setCashBalance();

        setBankBalance();

        CardView bankCard = (CardView) findViewById(R.id.bankCard);
        bankCard.setOnClickListener(this);

        CardView cashCard = (CardView) findViewById(R.id.cashCard);
        cashCard.setOnClickListener(this);

        TextView addCash = (TextView) findViewById(R.id.addCash);
        addCash.setOnClickListener(this);

        bankReport = (TextView) findViewById(R.id.bankReport);
        bankReport.setOnClickListener(this);

        cashReport = (TextView) findViewById(R.id.cashReport);
        cashReport.setOnClickListener(this);

        readMessages();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bankCard:
                if (bankList.size() > 0) {
                    Intent bank = new Intent(MainActivity.this, Transactions.class);
                    Bundle b = new Bundle();
                    b.putSerializable("SMS", (Serializable) bankList);
                    b.putString("CARD", "BANK_CARD");
                    b.putString("COLOR", "#6ed036");
                    bank.putExtra("DATA", b);
                    startActivity(bank);
                } else {
                    Toast.makeText(MainActivity.this, "No Bank Transactions to display", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.cashCard:
                Intent cash = new Intent(MainActivity.this, Transactions.class);
                Bundle b = new Bundle();
                b.putSerializable("CASH", (Serializable) cashList);
                b.putString("CARD", "CASH_CARD");
                b.putString("COLOR", "#467fd9");
                b.putString("SPENT", CASHSPENT);
                cash.putExtra("DATA", b);
                startActivityForResult(cash, 1);
                break;
            case R.id.addCash:
                final AddDialog dialog = new AddDialog(context, cashList);
                dialog.show();
                dialog.setOnSaveClickListener(new AddDialog.OnSaveClickListener() {
                    @Override
                    public void OnSaveClick() {
                        Sms s = dialog.getTransaction();
                        cashList.add(0, s);
                        databaseHandler.addCashSms(s);

                        if (s.getDrOrCr().equals("DR")) {
                            CASHSPENT = Double.toString(Double.parseDouble(CASHSPENT) + s.getAmtDouble());
                        }
                        setCashBalance();

                        Toast.makeText(MainActivity.this, "Transaction Added", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.bankReport:
                if (getSpendList(bankList).size() > 0)
                    startFragment(R.id.bankReportContainer, getSpendList(bankList));
                else
                    Toast.makeText(MainActivity.this, "Not Enough Data To Display", Toast.LENGTH_SHORT).show();
                break;
            case R.id.cashReport:
                if (getSpendList(cashList).size() > 0)
                    startFragment(R.id.cashReportContainer, getSpendList(cashList));
                else
                    Toast.makeText(MainActivity.this, "Not Enough Data To Display", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void startFragment(int id, List<Sms> spendList) {
        FrameLayout f = (FrameLayout) findViewById(id);
        if (f.getVisibility() == View.GONE) {
            f.setVisibility(View.VISIBLE);
            cashReport.setText("Hide Today's Report");
        } else if (f.getVisibility() == View.VISIBLE) {
            f.setVisibility(View.GONE);
            cashReport.setText("Show Today's Report");
        }

        Bundle bundle = new Bundle();
        bundle.putSerializable("SPEND LIST", (Serializable) spendList);

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        TransFragment cashFragment = new TransFragment();
        cashFragment.setArguments(bundle);
        fragmentTransaction.replace(id, cashFragment);
        fragmentTransaction.commit();
    }

    private List<Sms> getSpendList(List<Sms> originalList) {
        List<Sms> spentList = new ArrayList<>();
        for (Sms s : originalList) {
            if (s.getDrOrCr().equals("DR") && s.getDay().equals(new SimpleDateFormat("dd/MM").format(new Date(System.currentTimeMillis())))) {
                spentList.add(s);
            }
        }
        return spentList;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                Toast.makeText(MainActivity.this, "Hello Friend!!!", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            cashList = (ArrayList<Sms>) data.getBundleExtra("Result").getSerializable("cash");
            CASHSPENT = data.getBundleExtra("Result").getString("SPENT");

            setCashBalance();
        }
    }

    private void readMessages() {

        // new sms object declared
        Sms sms;

        // read sms are stored in cursor
        Cursor c = getContentResolver().query(Uri.parse("content://sms/inbox"), new String[]{"date", "body"}, FILTER, null, null);
        int total = c.getCount();

        // all messages are read from bottom because when new sms gets inserted they are inserted in the position zero
        // thus to keep the latest messages up in the list
        if (c.moveToLast()) {
            for (int i = 0; i < total; i++) {
                sms = new Sms();

                // body and date read from cursor
                String date = c.getString(c.getColumnIndexOrThrow("date"));
                String body = c.getString(c.getColumnIndexOrThrow("body"));
                // keeping track of a filter to prevent reading of messages already read
                FILTER = "date>" + date;

                String t = "";

                // date is set to the sms object
                sms.setMsgDate(date);

                body = body.toLowerCase();

                if (!Pattern.compile("(recharge|paytm|ola)").matcher(body).find()) {
                    if (Pattern.compile("(debit|transaction|withdrawn)").matcher(body).find())
                        t = "Personal Expenses";
                    else if (Pattern.compile("(credit|deposited)").matcher(body).find())
                        t = "Income";
                }

                // switched according to the type to extract information from the message
                switch (t) {
                    case "Personal Expenses":
                        sms.setMsgType(t);
                        String a = getAmount(body);
                        // getAmount is a method which gives the amount using pattern and matcher
                        if (a != null) {
                            sms.setMsgAmt(a);
                            sms.setMsgBal(Double.toString(Double.parseDouble(BALANCE) - Double.parseDouble(a)));
                            BALANCE = sms.getMsgBal();
                            bankList.add(0, sms);
                            databaseHandler.addBankSms(sms);
                        } else {
                            c.moveToPrevious();
                            continue;
                        }
                        break;

                    // for type of transaction income first the amount is extracted and then the balance is extracted
                    case "Income":
                        sms.setMsgType(t);
                        String a1 = getAmount(body);
                        if (a1 != null) {
                            sms.setMsgAmt(a1);
                            sms.setMsgBal(Double.toString(Double.parseDouble(BALANCE) + Double.parseDouble(a1)));
                            BALANCE = sms.getMsgBal();
                            bankList.add(0, sms);
                            databaseHandler.addBankSms(sms);
                        } else {
                            c.moveToPrevious();
                            continue;
                        }
                        break;
                }
                c.moveToPrevious();
            }
        } else {
            // if no messages to read than a toast is displayed
            Toast.makeText(MainActivity.this, "No sms to read!!", Toast.LENGTH_SHORT).show();
        }
        c.close();
        setBankBalance();
    }

    // getting amount by matching the pattern
    public String getAmount(String data) {
        // pattern - rs. **,***.**
        String pattern1 = "(inr)+[\\s]?+[0-9]*+[\\\\,]*+[0-9]*+[\\\\.][0-9]{2}";
        Pattern regex1 = Pattern.compile(pattern1);
        // pattern - inr **,***.**
        String pattern2 = "(rs)+[\\\\.][\\s]*+[0-9]*+[\\\\,]*+[0-9]*+[\\\\.][0-9]{2}";
        Pattern regex2 = Pattern.compile(pattern2);

        Matcher matcher1 = regex1.matcher(data);
        Matcher matcher2 = regex2.matcher(data);
        if (matcher1.find()) {
            try {
                String a = (matcher1.group(0));
                a = a.replace("inr", "");
                a = a.replace(" ", "");
                a = a.replace(",", "");
                return a;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (matcher2.find()) {
            try {
                // searched for rs or inr preceding number in the form of rs. **,***.**
                String a = (matcher2.group(0));
                a = a.replace("rs", "");
                a = a.replaceFirst(".", "");
                a = a.replace(" ", "");
                a = a.replace(",", "");
                return a;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private void setBankBalance() {
        if (bankList.size() > 0) {
            bankBalance.setText("₹ " + bankList.get(0).getMsgBal());
            estimateDate.setText(bankList.get(0).getFormatDate());
        } else {
            bankBalance.setText("₹ " + "0.0");
            estimateDate.setText(" ");
        }
    }

    private void setCashBalance() {
        spentAmount.setText("₹ " + CASHSPENT);

        if (cashList.size() > 0) {
            cashBalance.setText("In Hand:   ₹ " + cashList.get(0).getMsgBal());
        } else {
            cashBalance.setText("In Hand:   ₹ " + "0.0");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // registering a BroadcastReceiver to listen to incoming messages
        registerReceiver(sms_receiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        save();
        unregisterReceiver(sms_receiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        save();
    }

    public void save() {
        // saving the total cash spent and the bank balance
        SharedPreferences saveSpent = getSharedPreferences("KEY", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = saveSpent.edit();
        editor.putString("SPENT", CASHSPENT);
        editor.putString("BALANCE", BALANCE);
        editor.putString("FILTER", FILTER);
        editor.apply();
    }
}
