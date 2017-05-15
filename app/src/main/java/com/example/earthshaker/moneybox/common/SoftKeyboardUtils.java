package com.example.earthshaker.moneybox.common;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;


public class SoftKeyboardUtils {

  /**
   * @param editText EditText to focus on
   * @param context Context
   */
  public static void focusAndOpenKeyboard(EditText editText, Context context) {
    editText.setFocusableInTouchMode(true);
    editText.requestFocus();
    final InputMethodManager inputMethodManager =
        (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
    inputMethodManager.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
  }

  /**
   * Hide keyboard
   *
   * @param view View
   * @param context Context
   */
  public static void hideSoftKeyboard(View view, Context context) {
    InputMethodManager in =
        (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
    in.hideSoftInputFromWindow(view.getApplicationWindowToken(),
        InputMethodManager.HIDE_NOT_ALWAYS);
  }
}
