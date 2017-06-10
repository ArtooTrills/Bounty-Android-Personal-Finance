package com.example.earthshaker.moneybox.budget;

import java.text.DecimalFormat;

/**
 * Created by earthshaker on 14/5/17.
 */

public class BugdetHelper {

  public static String getBudgetAmountString(double totalSpent, double totalAllotted) {
    return String.format("₹ %s/ ₹ %s", removeDecimalConvToString(totalSpent),
        removeDecimalConvToString(totalAllotted));
  }

  public static String removeDecimalConvToString(Double number) {
    DecimalFormat mFormat;
    mFormat = new DecimalFormat("##,##,##,###");
    return mFormat.format(number);
  }
}
