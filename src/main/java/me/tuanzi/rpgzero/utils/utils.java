package me.tuanzi.rpgzero.utils;

import java.text.DecimalFormat;

public class utils {

    public static String formatNumber(double number) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        String formattedNumber = decimalFormat.format(number);
        if (formattedNumber.endsWith(".00")) {
            return formattedNumber.substring(0, formattedNumber.indexOf('.'));
        }
        return formattedNumber;
    }



}
