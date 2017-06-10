package com.example.earthshaker.moneybox.common;

import com.example.earthshaker.moneybox.R;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by earthshaker on 6/10/17.
 */
public class CategoryUtilsTest {
  @Test public void getCategoryIcon() throws Exception {
    assertEquals(R.drawable.exp_transport, CategoryUtils.getCategoryIcon("Automobile"));
    assertEquals(R.drawable.exp_travel, CategoryUtils.getCategoryIcon("Travel"));
    assertEquals(R.drawable.exp_food, CategoryUtils.getCategoryIcon("Food"));
    assertEquals(R.drawable.exp_personal, CategoryUtils.getCategoryIcon("Personal"));
    assertEquals(R.drawable.exp_shopping, CategoryUtils.getCategoryIcon("Shopping"));
  }
}