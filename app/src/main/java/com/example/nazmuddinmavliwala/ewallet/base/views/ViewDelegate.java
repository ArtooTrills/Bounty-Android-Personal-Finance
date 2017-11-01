package com.example.nazmuddinmavliwala.ewallet.base.views;

import android.content.Intent;

/**
 * Created by nazmuddinmavliwala on 28/10/2017.
 */

public interface ViewDelegate<DATA> {

    void show();

    void hide();

    void bind(DATA data);

    void onResume();

    void onPause();

    void onCreate();

    void onStop();

    void onActivityResult(int requestCode, int resultCode, Intent data);

}
