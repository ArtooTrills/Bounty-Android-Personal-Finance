package bounty.android.example.com.bounty;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

/**
 * Created by RamizMehran on 25/10/2015.
 */
public class WelcomeActivity extends Activity{
    private Button mExpense;
    private Button mIncome;
    private Switch mUpdateProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_page);

        mExpense = (Button) findViewById(R.id.Nav_To_Expense);
        mExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mIncome = (Button) findViewById(R.id.Nav_To_Income);
        mIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mUpdateProfile = (Switch) findViewById(R.id.sms_read_status);
        mUpdateProfile.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });
    }
}
