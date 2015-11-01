package com.artoo.finac;

import android.app.ActivityManager;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.artoo.finac.services.FinacService;

public class Splash extends AppCompatActivity {

    //  DM
    private SharedPreferences settings;
    private Dialog dialog;
    private SQLiteDatabase db;

    final static String TAG = "Finac Splash";

    //  CL
    private class InitialCheckUp extends AsyncTask<String, Void, Void> {

        private ProgressDialog dialog;
        private boolean seekPersonalDetails;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog =  new ProgressDialog(Splash.this);
            dialog.setMessage("Setting up Accounts!");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected Void doInBackground(String... params) {

            boolean existAccount = settings.getBoolean("existAccount", false);
            if (!existAccount) {

                seekPersonalDetails = true;

                //  Setup the database as required.
                String query = "CREATE TABLE `transaction` (" +
                        "`_id` INTEGER AUTO_INCREMENT PRIMARY KEY," +       //  For Cursor
                        "`amount` REAL NOT NULL," +                         //  Amount
                        "`category` VARCHAR(15) NOT NULL," +                //  Tags
                        "`type` VARCHAR(2) NOT NULL," +                     //  Credit or Debit
                        "`timestamp` TIMESTAMP DEFAULT CURRENT_TIMESTAMP)"; //  Timestamp for Analytics

                try {

                    db.execSQL(query);
                    Log.d(TAG, "Transaction table created!");
                } catch (SQLException e) {

                    Log.d(TAG, "Transaction table exist! Message: " + e.getMessage());
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(final Void i) {
//            super.onPostExecute(i);

            if (dialog != null && dialog.isShowing())
                dialog.dismiss();

            //  Do further stuff!

            if (seekPersonalDetails) {

                Log.d(TAG, "Need Personal Credentials!");
                Splash.this.raiseDialogForPersonalDetails();
            }
            else {

                //  Everything is alright.
                //  Start the dashboard.

                //  Check to see if the background service is running. Start it on readMessages = true;
                if (settings.getBoolean("readMessages",true)) {

                    if (!runningService(FinacService.class)) {

                        Intent intent = new Intent(Splash.this, FinacService.class);
                        startService(intent);
                    }
                }

                Log.d(TAG, "Starting the Dashboard Activity!");
                Splash.this.raiseDashboard();
            }
        }
    }

    //  FN

    private boolean runningService(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        try {
            for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
                if (serviceClass.getName().equals(service.service.getClassName())) {
                    return true;
                }
            }
        } catch(SecurityException e) {

            Log.d(TAG, "Exception, Message: " + e.getMessage());
        }

        return false;
    }

    private void raiseDashboard() {

        Intent intent = new Intent(Splash.this, Dashboard.class);
        startActivity(intent);
        Splash.this.finish();
    }

    private void raiseDialogForPersonalDetails() {

        AlertDialog.Builder builder = new AlertDialog.Builder(Splash.this);
        LayoutInflater inflater = Splash.this.getLayoutInflater();
        final View view = inflater.inflate(R.layout.personal_details, null);
        builder.setView(view);

        //  References
        final EditText editTextPhone  = (EditText) view.findViewById(R.id.editTextPhone);
        final EditText editTextName  = (EditText) view.findViewById(R.id.editTextName);
        final CheckBox checkBoxReadMessages = (CheckBox) view.findViewById(R.id.checkBoxReadMessages);
        final CheckBox checkBoxSyncService = (CheckBox) view.findViewById(R.id.checkBoxSyncService);
        Button buttonSave = (Button) view.findViewById(R.id.buttonSave);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean err = false;

                String phone = editTextPhone.getText().toString();
                String name = editTextName.getText().toString();
                boolean readMessages = checkBoxReadMessages.isChecked();
                boolean syncService = checkBoxSyncService.isChecked();

                if (TextUtils.isEmpty(name)) {

                    editTextName.setError("Required!");
                    err = true;
                    editTextName.requestFocus();
                    Log.d(TAG, "Name missing!");
                }

                if (TextUtils.isEmpty(phone)) {

                    editTextPhone.setError("Required!");
                    err = true;
                    editTextPhone.requestFocus();
                    Log.d(TAG, "Phone missing!");
                }

                if (!err) {

                    //  Set preferences
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("name", name);
                    editor.putString("phone", phone);
                    editor.putBoolean("existAccount", true);
                    editor.putBoolean("readMessages", readMessages);
                    editor.putBoolean("syncService", syncService);
                    editor.putFloat("credit",0.0f);
                    editor.putFloat("debit",0.0f);

                    editor.apply();
                    dialog.dismiss();
                    raiseDashboard();
                }

            }
        });

        dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //  Prior Initializations
        settings = PreferenceManager.getDefaultSharedPreferences(Splash.this);
        db = openOrCreateDatabase(Constants.DB_NAME, Context.MODE_PRIVATE, null);

        //  Initial Check up
        new InitialCheckUp().execute();
    }
}