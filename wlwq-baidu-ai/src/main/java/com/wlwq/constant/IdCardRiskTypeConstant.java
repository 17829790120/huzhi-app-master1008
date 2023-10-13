package com.wlwq.constant;

/**
 * @ClassName IdCardRiskTypeConstant
 * @Description 身份证类型常量
 * @Date 2021/2/7 16:15
 * @Author Rick wlwq
 */
public class IdCardRiskTypeConstant {

    public final static String NORMAL = "normal";

    public final static String COPY = "copy";

    public final static String TEMPORARY = "temporary";

    public final static String SCREEN = "screen";

    public final static String UNKNOWN = "unknown";

    /**
     * 获取code对应的中文说明
     * @param code
     * @return
     */
    public static String getMsg(String code){
        if (code.equals(NORMAL)){
            return "正常身份证";
        }if (code.equals(COPY)){
            return "复印件";
        }if (code.equals(TEMPORARY)){
            return "临时身份证";
        }if (code.equals(SCREEN)){
            return "翻拍";
        }if (code.equals(UNKNOWN)){
            return "其他未知情况";
        }
        return "未知！";
    }
}
