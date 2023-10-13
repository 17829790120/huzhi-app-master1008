package com.wlwq.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HumpToGlideUtils {
    private static final Pattern TPATTERN = Pattern.compile("[A-Z0-9]");
    public static String teseDemo(String str) {
        Matcher matcher = TPATTERN.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }


}
