package com.example.earthshaker.moneybox.common;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by earthshaker on 13/5/17.
 */

public class ActivityNavigator {

    public static void startActivity(Activity context, Class targetActivity) {
        Intent intent = new Intent(context, targetActivity);
        context.startActivity(intent);
    }
}