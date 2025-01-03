package org.example.utils;

import java.util.regex.Pattern;

public class DigitalUtils {
    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^-?\\d+$");

        return pattern.matcher(str).matches();
    }


    public static String trimBothEndsChars(String srcStr, String splitter) {
        String regex = "^" + splitter + "*|" + splitter + "*$";
        return srcStr.replaceAll(regex, "");
    }
}
