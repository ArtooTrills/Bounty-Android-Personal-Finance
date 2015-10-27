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

                Cursor cursor = getContentResolver().query(Uri.parse("content://sms/inbox"), null, null, null, null);
                if (cursor.moveToFirst()) { // must check the result to prevent exception
                    do {
                        try {
                            String[] list = {"", cursor.getString(2), cursor.getString(cursor.getColumnIndexOrThrow("body"))};


                            Sms newSms = new Sms();
                            newSms.setFrom(list[1]);
                            Log.d("List Values", "0 ->" + list[0] + " 1 ->" + list[1] + " 2 ->" + list[2]);
                            if (list[2].contains("credit"))
                                newSms.setType("Income");
                            else if (list[2].contains("debit"))
                                newSms.setType("Expense");
                            else
                                continue;

                            String[] amt = list[2].split("Rs.");
                            amt = amt[1].split(" ");
                            newSms.setAmount(amt[1]);

                            dbHandler.addSms(newSms);
                        }
                        catch(Exception e){}
                    } while (cursor.moveToNext());
                }
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
