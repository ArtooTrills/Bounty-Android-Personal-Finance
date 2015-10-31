package bounty.android.example.com.bounty;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.DeflaterOutputStream;

/**
 * Created by RamizMehran on 27/10/2015.
 */
public class SmsActivity extends Activity{

    public Button mRefresh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sms_transactions);

        mRefresh = (Button)findViewById(R.id.refresh);
        mRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDBHandlerSms dbHandler = new MyDBHandlerSms(SmsActivity.this, null, null, 1);

                dbHandler.deleteSms();

                Boolean isIncome = false;
                double sumI = 0;
                double sumE = 0;
                Cursor cursor = getContentResolver().query(Uri.parse("content://sms/inbox"), null, null, null, null);
                if (cursor.moveToFirst()) { // must check the result to prevent exception
                    do {
                        try {
                            String[] list = {"", cursor.getString(2), cursor.getString(cursor.getColumnIndexOrThrow("body"))};


                            Sms newSms = new Sms();
                            newSms.setFrom(list[1]);
                            Log.d("List Values", "0 ->" + list[0] + " 1 ->" + list[1] + " 2 ->" + list[2]);
                            if (list[2].contains("credit") || list[2].contains("deposit")) {
                                newSms.setType("Income");
                                isIncome = true;
                            }
                            else if (list[2].contains("debit") || list[2].contains("purchase") || list[2].contains("withdraw")) {
                                newSms.setType("Expense");
                                isIncome = false;
                            }
                            else
                                continue;

                            Log.d("List Values", "Starting division");
                            int ind = list[2].indexOf("Rs");
                            String[] amt = list[2].split("Rs");
                            Log.d("List Values", "list2 ->" + list[2] + " amt0 -> "+ amt[0]+" amt1 -> "+amt[1]+" ind->"+ind);

                            amt = amt[1].split(" ");

                            Log.d("List Values", "amt0 ->" + amt[0]);
                            Log.d("List Values", "amt1 ->" + amt[1]);

                            int index = 0;
                            if(amt[0].equals("") || amt[0].equals("."))
                                index = 1;
                            else if(true){
                                try{
                                    int i = Integer.parseInt((amt[1].split(" "))[0]);
                                    index = 1;
                                }catch(Exception i){
                                    index = 0;
                                }
                            }
                            Log.d("List Values", "index ->" + index);

                            double money;
                            int i = 0;
                            while(true){
                                if(amt[index].substring(i).charAt(0) == '.'){
                                    i++;
                                    continue;
                                }
                                String s = amt[index].substring(i);
                                try{
                                    money = Double.parseDouble(s);
                                    break;
                                } catch(Exception e){
                                    i++;
                                    continue;
                                }
                            }
                            Log.d("List Values", "i ->" + i + " money ->" + money);

                            String amount = amt[index].substring(i);

                            Log.d("List Values", "amt ->" + amount);

                            newSms.setAmount(amount);
                            if(isIncome)
                                sumI += Double.parseDouble(amount);
                            else
                                sumE += Double.parseDouble(amount);

                            dbHandler.addSms(newSms);
                        }
                        catch(Exception e){}
                    } while (cursor.moveToNext());
                }
                MyDBHandler handler = new MyDBHandler(SmsActivity.this, null, null, 1);

                Total_ product = new Total_("sms income", sumI);
                handler.addTotal(product);
                Log.d("Total", "sms income --> " +  product.getSource() + " " + product.getAmount());

                product = new Total_("sms expense", sumE);
                handler.addTotal(product);
                Log.d("Total", "sms expense -->" + product.getSource() + " " + product.getAmount());
                updateTable();
            }
        });

        mRefresh.performClick();
    }

    public void updateTable(){
        MyDBHandlerSms dbHandler = new MyDBHandlerSms(this, null, null, 1);

        List<Sms> list= dbHandler.findSms();

        TableLayout l1 = (TableLayout) findViewById(R.id.sms_list);

        l1.removeAllViews();

        for(Sms src : list){
            TableRow tr =  new TableRow(getBaseContext());

            TextView t1 = new TextView(getBaseContext());
            TextView t2 = new TextView(getBaseContext());
            TextView t3 = new TextView(getBaseContext());

            t1.setTextColor(Color.BLACK);
            t2.setTextColor(Color.BLACK);
            t3.setTextColor(Color.BLACK);

            t1.setTextSize(15);
            t2.setTextSize(15);
            t3.setTextSize(15);

            t1.setText(src.getFrom());
            t2.setText(src.getType());
            t3.setText(src.getAmount());

            t1.setGravity(Gravity.CENTER);
            t2.setGravity(Gravity.CENTER);
            t3.setGravity(Gravity.CENTER);

            tr.addView(t1);
            tr.addView(t2);
            tr.addView(t3);


            l1.addView(tr);
        }
        //experimenting
    }
}
