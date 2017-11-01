package com.nazmuddinmavliwala.ewallet.utils;

import java.util.Random;

/**
 * Created by nazmuddinmavliwala on 29/10/2017.
 */

public class RandomPriceUtil {

    public static long getRandomPrice() {
        Random r = new Random();
        int Low = 100;
        int High = 1000;
        return r.nextInt(High-Low) + Low;
    }
}
