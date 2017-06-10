package com.example.earthshaker.moneybox;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.example.earthshaker.moneybox.dashboard.activity.DashboardActivity;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by earthshaker on 6/10/17.
 */
@RunWith(AndroidJUnit4.class) @LargeTest

public class DashboardTestCase {
  @Rule public ActivityTestRule<DashboardActivity> mActivityTestRule =
      new ActivityTestRule<>(DashboardActivity.class);

  @Test public void openDashboardActivity() throws Exception {

    onView(withId(R.id.fab_menu)).check(matches(isDisplayed())).perform(click());

    addTransactions();
    //addBudget();
  }

  private void addBudget() {
    onView(withId(R.id.fab_budget)).check(matches(isDisplayed())).perform(click());
  }

  private void addTransactions() throws Exception {
    Thread.sleep(3000);
    onView(withId(R.id.fab_transaction)).check(matches(isDisplayed())).perform(click());
    TransactionTestActivity transactionTestActivity = new TransactionTestActivity();
    transactionTestActivity.addDetails();
  }
}
