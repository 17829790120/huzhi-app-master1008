package com.wlwq.constant;

/**
 * @ClassName IdCardNumberTypeConstant
 * @Description 校验身份证号码常量
 * @Date 2021/2/7 16:20
 * @Author Rick wlwq
 */
public class IdCardNumberTypeConstant {

    /**
     * 身份证正面所有字段全为空
     */
    public final static Integer ALL_NULL = -1;

    /**
     * 身份证证号不合法，此情况下不返回身份证证号
     */
    public final static Integer ILLEGAL_CARD = 0;

    /**
     * 身份证证号和性别、出生信息一致
     */
    public final static Integer NO_DIFFERENCE = 1;

    /**
     * 身份证证号和性别、出生信息都不一致
     */
    public final static Integer DIFFERENCE_ALL = 2;

    /**
     * 身份证证号和出生信息不一致
     */
    public final static Integer DIFFERENCE_BIRTHDAY = 3;

    /**
     * 身份证证号和性别信息不一致
     */
    public final static Integer DIFFERENCE_SEX = 4;

    /**
     * 根据code获取对应的中文说明
     * @param code
     * @return
     */
    public static String getMsg(Integer code){
        if (code.equals(ALL_NULL)){
            return "身份证正面所有字段全为空";
        }if (code.equals(ILLEGAL_CARD)){
            return "身份证证号不合法";
        }if (code.equals(NO_DIFFERENCE)){
            return "身份证证号和性别、出生信息一致";
        }if (code.equals(DIFFERENCE_ALL)){
            return "身份证证号和性别、出生信息都不一致";
        }if (code.equals(DIFFERENCE_BIRTHDAY)){
            return "身份证证号和出生信息不一致";
        }if (code.equals(DIFFERENCE_SEX)){
            return "身份证证号和性别信息不一致";
        }
        return "未知！";
    }

}
