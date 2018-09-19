package com.feifandaiyu.feifan.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.ParseException;

/**
 * @author David Zhao.Created on:2017/12/12.
 */

public class NumberUtil {


    public static double getNumber(String number) {
        double num = new BigDecimal(number).setScale(2, RoundingMode.CEILING).doubleValue();
        return num;
    }

    public static String toReal(String s) {
        if (s.indexOf(".") > 0) {
            s = s.replaceAll("0+?$", "");
            s = s.replaceAll("[.]$", "");

        }

        return s;
    }

    /**
     * 等额本息计算获取还款方式为等额本息的每月偿还本金和利息
     * <p>
     * 公式：每月偿还本息=〔贷款本金×月利率×(1＋月利率)＾还款月数〕÷〔(1＋月利率)＾还款月数-1〕
     *
     * @param invest     总借款额（贷款本金）
     * @param yearRate   年利率
     * @param totalmonth 还款总月数
     * @return 每月偿还本金和利息, 不四舍五入，直接截取小数点最后两位
     */
    public static double getPerMonthPrincipalInterest(double invest, double yearRate, int totalmonth) {
        double monthRate = yearRate / 12;
        BigDecimal monthIncome = new BigDecimal(invest)
                .multiply(new BigDecimal(monthRate * Math.pow(1 + monthRate, totalmonth)))
                .divide(new BigDecimal(Math.pow(1 + monthRate, totalmonth) - 1), 2, BigDecimal.ROUND_HALF_UP);
        return monthIncome.doubleValue();
    }

    public static double baifenshuToXiaoshu(String number) {
        NumberFormat nf = NumberFormat.getPercentInstance();
        Number m = null;
        try {
            m = nf.parse(number);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (double) m;
    }

}
