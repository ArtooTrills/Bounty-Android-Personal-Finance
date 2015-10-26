package bounty.android.example.com.bounty;

import android.app.Activity;
import android.app.Fragment;
import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

/**
 * Created by RamizMehran on 25/10/2015.
 */
public class WelcomeActivity extends Fragment implements View.OnClickListener{
    private Button mExpense;
    private Button mIncome;
    private Switch mUpdateProfile;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.welcome_page, container, false);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        Switch mSms = (Switch) v;

        if(mSms.isChecked())
            enableBroadcastReceiver();
        else
            disableBroadcastReceiver();
    }

    /**
     * This method enables the Broadcast receiver registered in the AndroidManifest file.
     */
    public void enableBroadcastReceiver(){
        ComponentName receiver = new ComponentName(getActivity(), SmsBroadcastReceiver.class);
        PackageManager pm = getActivity().getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
        Toast.makeText(getActivity(), "Enabled sms receiver", Toast.LENGTH_SHORT).show();
    }
    /**
     * This method disables the Broadcast receiver registered in the AndroidManifest file.
     */
    public void disableBroadcastReceiver(){
        ComponentName receiver = new ComponentName(getActivity(), SmsBroadcastReceiver.class);
        PackageManager pm = getActivity().getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
        Toast.makeText(getActivity(), "Disabled sms receiver", Toast.LENGTH_SHORT).show();
    }


}
