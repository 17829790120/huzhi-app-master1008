package com.wlwq.common.utils;

import java.util.Random;

/**
 * @Author:gaoce
 * @Date:2022/4/19 15:52
 */
public class InvitationCodeUtils {

    /**
     * 6位邀请码，字母数字混合，数字最多不超过四位。
     * @return
     */
    public static String generateCode() {
        String charList = "ABCDEFGHIJKLMNPQRSTUVWXY";
        String numList = "0123456789";
        String rev = "";
        int maxNumCount = 4;
        int length = 6;
        Random f = new Random();
        for (int i = 0; i < length; i++) {
            if (f.nextBoolean() && maxNumCount > 0) {
                maxNumCount--;
                rev += numList.charAt(f.nextInt(numList.length()));
            } else {
                rev += charList.charAt(f.nextInt(charList.length()));
            }
        }
        return rev;
    }

    public static void main(String[] args) {
        String code = generateCode();
        System.err.println(code);
    }
}
