package com.example.earthshaker.moneybox.common;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by earthshaker on 6/10/17.
 */
public class StringUtilsTest {
  @Test public void isBlankTest() {
    assertEquals(true, StringUtils.isBlank(null));
    assertEquals(false, StringUtils.isBlank("zbc"));
  }

}