package com.feifandaiyu.feifan.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Created by davidzhao on 2017/7/25.
 */

public class SmallNumberUtils {
    public static String getSmallNumber(double number) {

        String s = number + "";
        LogUtils.d("--------------->>>" + s);

        DecimalFormat formater = new DecimalFormat();
        formater.setMaximumFractionDigits(2);
        formater.setGroupingSize(0);
        formater.setRoundingMode(RoundingMode.CEILING);
        String format = formater.format(number);
        return format;

    }

    public static String toReal(double a, double b) {
        BigDecimal bigDecimala = new BigDecimal(Double.toString(a));
        BigDecimal bigDecimalb = new BigDecimal(Double.toString(b));

        return bigDecimala.multiply(bigDecimalb).doubleValue() + "";

    }

    public static String toReal(double a, double b,double c) {
        BigDecimal bigDecimala = new BigDecimal(Double.toString(a));
        BigDecimal bigDecimalb = new BigDecimal(Double.toString(b));
        BigDecimal bigDecimalc = new BigDecimal(Double.toString(c));

        return bigDecimala.multiply(bigDecimalb).multiply(bigDecimalc).doubleValue() + "";

    }

    public static double toMinus(double value1, double value2) {
        BigDecimal b1 = new BigDecimal(Double.toString(value1));
        BigDecimal b2 = new BigDecimal(Double.toString(value2));
        return b1.subtract(b2).doubleValue();
    }

    public static double toMinus(double a, double b, double c) {
        BigDecimal bigDecimala = new BigDecimal(Double.toString(a));
        BigDecimal bigDecimalb = new BigDecimal(Double.toString(b));
        BigDecimal bigDecimalc = new BigDecimal(Double.toString(c));
        return bigDecimala.subtract(bigDecimalb).subtract(bigDecimalc).doubleValue();

    }

    public static double toMinus(double a, double b, double c, double d) {
        BigDecimal bigDecimala = new BigDecimal(Double.toString(a));
        BigDecimal bigDecimalb = new BigDecimal(Double.toString(b));
        BigDecimal bigDecimalc = new BigDecimal(Double.toString(c));
        BigDecimal bigDecimald = new BigDecimal(Double.toString(d));
        return bigDecimala.subtract(bigDecimalb).subtract(bigDecimalc).subtract(bigDecimald).doubleValue();

    }

    public static double toMinus(double a, double b, double c, double d, double e) {
        BigDecimal bigDecimala = new BigDecimal(Double.toString(a));
        BigDecimal bigDecimalb = new BigDecimal(Double.toString(b));
        BigDecimal bigDecimalc = new BigDecimal(Double.toString(c));
        BigDecimal bigDecimald = new BigDecimal(Double.toString(d));
        BigDecimal bigDecimale = new BigDecimal(Double.toString(e));
        return bigDecimala.subtract(bigDecimalb).subtract(bigDecimalc).subtract(bigDecimald).subtract(bigDecimale).doubleValue();

    }

    public static double toMinus(double a, double b, double c, double d, double e, double f) {
        BigDecimal bigDecimala = new BigDecimal(Double.toString(a));
        BigDecimal bigDecimalb = new BigDecimal(Double.toString(b));
        BigDecimal bigDecimalc = new BigDecimal(Double.toString(c));
        BigDecimal bigDecimald = new BigDecimal(Double.toString(d));
        BigDecimal bigDecimale = new BigDecimal(Double.toString(e));
        BigDecimal bigDecimalf = new BigDecimal(Double.toString(f));
        return bigDecimala.subtract(bigDecimalb).subtract(bigDecimalc).subtract(bigDecimald).subtract(bigDecimale).subtract(bigDecimalf).doubleValue();

    }

}