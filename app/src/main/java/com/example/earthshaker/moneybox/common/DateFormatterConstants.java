package com.example.earthshaker.moneybox.common;

import java.text.SimpleDateFormat;
import java.util.Locale;


public class DateFormatterConstants {

  public static final SimpleDateFormat calendarReminderFormatter =
      new SimpleDateFormat("hh:mm a", Locale.UK);
  public static final SimpleDateFormat minutesFormatter = new SimpleDateFormat("mm:ss", Locale.UK);
  public static final SimpleDateFormat hourFormatter = new SimpleDateFormat("HH:mm:ss", Locale.UK);
  public static final SimpleDateFormat lastSmsDateFormat =
      new SimpleDateFormat("dd/MM/yyyy hh:mm", Locale.UK);
  public static final SimpleDateFormat tokenExpiryDateFormatter =
      new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.UK);
  public static final SimpleDateFormat alternateTokenExpiryFormatter =
      new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.UK);
  public static final SimpleDateFormat reminderTimeDateFormat =
      new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.UK);

  public static final SimpleDateFormat reminderDateFormat =
      new SimpleDateFormat("dd-MM-yyyy", Locale.UK);
  public static final SimpleDateFormat filterDateFormat =
      new SimpleDateFormat("dd MMM yy", Locale.UK);
  public static final SimpleDateFormat reminderTimeFormat =
      new SimpleDateFormat("HH:mm", Locale.UK);
  public static final SimpleDateFormat budgetDateFormat =
      new SimpleDateFormat("MM-yyyy", Locale.UK);
  public static final SimpleDateFormat txnMonthFormat = new SimpleDateFormat("MM-yyyy", Locale.UK);
  public static final SimpleDateFormat budgetMonthDateFormat =
      new SimpleDateFormat("MMM-yyyy", Locale.UK);
  public static final SimpleDateFormat dayFormatter = new SimpleDateFormat("dd", Locale.UK);
  public static final SimpleDateFormat curFormatter =
      new SimpleDateFormat("dd/MM/yyyy hh:mm:ss", Locale.UK);
  public static final SimpleDateFormat saveExpenseDateFormat =
      new SimpleDateFormat("dd-MM-yyyy H:mm:ss", Locale.UK);
  public static final SimpleDateFormat dashboardReminderDate =
      new SimpleDateFormat("EEE, dd MMM", Locale.UK);
  public static final SimpleDateFormat mobileBalanceDate =
      new SimpleDateFormat("dd-MMM-yyyy", Locale.UK);
  public static final SimpleDateFormat dashboardDateFormat =
      new SimpleDateFormat("dd-MMMM-yyyy", Locale.UK);
  public static final SimpleDateFormat budgetDetailFormatter =
      new SimpleDateFormat("dd/MM/yyyy hh:mm:ss", Locale.UK);
  public static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.UK);
  public static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.UK);
  public static final SimpleDateFormat dayFormat = new SimpleDateFormat("EEE", Locale.UK);
  public static final SimpleDateFormat billingCycleDateFormat =
      new SimpleDateFormat("dd MMM ''yy", Locale.UK);
  public static final SimpleDateFormat monthFormatter = new SimpleDateFormat("MMM", Locale.UK);
  public static final SimpleDateFormat emailDateFormatter =
      new SimpleDateFormat("E, d MMM yyyy HH:mm:ss Z", Locale.UK);

  public static final SimpleDateFormat loanDateOfBirthFormatter =
      new SimpleDateFormat("yyyy/MM/dd", Locale.UK);
  public static final SimpleDateFormat loanFormatter =
      new SimpleDateFormat("dd-mm-yyyy", Locale.UK);
  public static final SimpleDateFormat filterYearFormatter =
      new SimpleDateFormat("yyyy", Locale.UK);
}