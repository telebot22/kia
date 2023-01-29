package com.kia.career.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
    public static String OnlyAlphaAndNumber(String msg){

        String newStr = "";
        String regExpAlphaAndNumber = "[^a-zA-Z0-9]";

        Pattern pattern = Pattern.compile(regExpAlphaAndNumber);
        Matcher matcher = pattern.matcher(msg);
        StringBuffer buffer = new StringBuffer();

        while (matcher.find()) {
            matcher.appendReplacement(buffer, "");
        }

        newStr = buffer.toString();

        return newStr;
    }

}
