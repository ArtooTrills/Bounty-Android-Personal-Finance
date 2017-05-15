package com.example.earthshaker.moneybox.common;

import java.util.regex.Pattern;

/**
 * Created by earthshaker on 15/5/17.
 */

public class StringUtils {
    public static boolean isBlank(String input) {
        int strLen;
        if (input == null || input.equals("null") || (strLen = input.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(input.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static double getAmount(String doubleString) {
        if (doubleString == null) {
            return 0;
        } else {
            return isDouble(doubleString.replace(",", "")) ? Double.parseDouble(
                    doubleString.replace(",", "")) : 0.0;
        }
    }

    public static Boolean isDouble(String amount) {

        Boolean isDouble;
        final String Digits = "(\\p{Digit}+)";
        final String HexDigits = "(\\p{XDigit}+)";
        // an exponent is 'e' or 'E' followed by an optionally
        // signed decimal integer.
        final String Exp = "[eE][+-]?" + Digits;
        final String fpRegex = ("[\\x00-\\x20]*"
                +
                // Optional leading "whitespace"
                "[+-]?("
                +
                // Optional sign character
                "NaN|"
                +
                // "NaN" string
                "Infinity|"
                +
                // "Infinity" string

                // A decimal floating-point string representing a finite positive
                // number without a leading sign has at most five basic pieces:
                // Digits . Digits ExponentPart FloatTypeSuffix
                //
                // Since this method allows integer-only strings as input
                // in addition to strings of floating-point literals, the
                // two sub-patterns below are simplifications of the grammar
                // productions from the Java Language Specification, 2nd
                // edition, section 3.10.2.

                // Digits ._opt Digits_opt ExponentPart_opt FloatTypeSuffix_opt
                "((("
                + Digits
                + "(\\.)?("
                + Digits
                + "?)("
                + Exp
                + ")?)|"
                +

                // . Digits ExponentPart_opt FloatTypeSuffix_opt
                "(\\.("
                + Digits
                + ")("
                + Exp
                + ")?)|"
                +

                // Hexadecimal strings
                "(("
                +
                // 0[xX] HexDigits ._opt BinaryExponent FloatTypeSuffix_opt
                "(0[xX]"
                + HexDigits
                + "(\\.)?)|"
                +

                // 0[xX] HexDigits_opt . HexDigits BinaryExponent FloatTypeSuffix_opt
                "(0[xX]"
                + HexDigits
                + "?(\\.)"
                + HexDigits
                + ")"
                +

                ")[pP][+-]?"
                + Digits
                + "))"
                + "[fFdD]?))"
                + "[\\x00-\\x20]*");// Optional trailing "whitespace"

        if (Pattern.matches(fpRegex, amount)) {
            Double.valueOf(amount);
            isDouble = true;// Will not throw NumberFormatException
        } else {
            isDouble = false;// Perform suitable alternative action
        }
        return isDouble;
    }
}
