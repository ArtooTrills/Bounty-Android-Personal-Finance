package com.example.earthshaker.moneybox;

import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.example.earthshaker.moneybox.transaction.activity.TransactionsActivity;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by earthshaker on 6/10/17.
 */
@RunWith(AndroidJUnit4.class) @LargeTest

public class TransactionTestActivity {
  @Rule public ActivityTestRule<TransactionsActivity> mActivityTestRule =
      new ActivityTestRule<>(TransactionsActivity.class);

  @Test public void addDetails() throws Exception {
    onView(withId(R.id.amount_et)).check(matches(isDisplayed()))
        .perform(clearText())
        .perform(typeText("1200"), ViewActions.closeSoftKeyboard());

    onView(withId(R.id.category_text)).check(matches(isDisplayed()))
        .perform(click());


    onView(withId(R.id.recycler_view)).check(matches(isDisplayed()))
        .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
    Thread.sleep(3000);

    onView(withId(R.id.save)).check(matches(isDisplayed()))
        .perform(click());

    Thread.sleep(3000);

  }
}
