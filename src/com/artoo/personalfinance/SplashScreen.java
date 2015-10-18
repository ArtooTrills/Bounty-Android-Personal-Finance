package com.artoo.personalfinance;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * this screen will stay for the time set as DELAY_TIME
 * 
 * @author nayan
 *
 */
public class SplashScreen extends Activity {
	private static final int DELAY_TIME = 2500;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);

		// start a background thread
		Thread background = new Thread() {
			public void run() {
				try {

					// sleep for delay_time
					sleep(DELAY_TIME);

					// start home activity after delay_time
					Intent i = new Intent(getBaseContext(), Home.class);
					startActivity(i);
					finish();
				} catch (Exception e) {

				}
			}
		};

		// start thread
		background.start();
	}
}
