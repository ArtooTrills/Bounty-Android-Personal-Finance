package com.example.earthshaker.moneybox.budget;

import android.content.Context;
import android.text.format.DateUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by earthshaker on 6/10/17.
 */
public class BugdetHelperTest {
  private Context context;

  @Before public void setUp() throws Exception {
    context = Mockito.mock(Context.class);
    //mContext = new ContextThemeWrapper(getInstrumentation().getTargetContext(), R.style.AppTheme);
  }

  @Test public void getBudgetAmountString() throws Exception {
      String expected = BugdetHelper.getBudgetAmountString(2000d,5000d);
    assertEquals("₹ 2,000/ ₹ 5,000", expected);
  }

}