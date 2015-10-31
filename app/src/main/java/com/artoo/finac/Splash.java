package com.artoo.finac;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Splash extends AppCompatActivity {

    //  DM
    private SharedPreferences settings;
    private Dialog dialog;

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
            if (existAccount) {

                //  Start the Service
            }
            else {

                seekPersonalDetails = true;

                //  Setup the database as required.

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

                Splash.this.raiseDialogForPersonalDetails();
            }
            else {

                //  Everything is alright.
                //  Start the dashboard.
                Splash.this.raiseDashboard();
            }
        }
    }

    //  FN

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
        Button buttonSave = (Button) view.findViewById(R.id.buttonSave);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean err = false;

                String phone = editTextPhone.getText().toString();
                String name = editTextName.getText().toString();

                if (TextUtils.isEmpty(name)) {

                    editTextName.setError("Required!");
                    err = true;
                    editTextName.requestFocus();
                }

                if (TextUtils.isEmpty(phone)) {

                    editTextPhone.setError("Required!");
                    err = true;
                    editTextPhone.requestFocus();
                }

                if (!err) {

                    //  Set preferences
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("name", name);
                    editor.putString("phone", phone);
                    editor.putBoolean("existAccount", true);

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

        //  Initial Check up
        new InitialCheckUp().execute();
    }
}
