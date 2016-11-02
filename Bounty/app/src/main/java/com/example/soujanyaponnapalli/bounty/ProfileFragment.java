package com.example.soujanyaponnapalli.bounty;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by soujanyaponnapalli on 29/10/16.
 */
public class ProfileFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // setting the username in the profile

        View v = inflater.inflate(R.layout.profilelayout,container,false);
        String myvalue = this.getArguments().getString("username");
        TextView e = (TextView) v.findViewById(R.id.profileusername);
        e.setText(myvalue);
        return v;
    }
}
