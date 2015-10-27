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

    private Button mRefresh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sms_transactions);

        mRefresh = (Button)findViewById(R.id.refresh);
        mRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTable();
            }
        });
        updateTable();
    }

    public void updateTable(){
        MyDBHandlerSms dbHandler = new MyDBHandlerSms(this, null, null, 1);

        List<Sms> list= dbHandler.findSms();

        TableLayout l1 = (TableLayout)findViewById(R.id.sms_list);

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
