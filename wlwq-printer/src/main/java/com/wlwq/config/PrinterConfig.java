package com.wlwq.config;

import net.xpyun.platform.opensdk.util.HashSignUtil;
import net.xpyun.platform.opensdk.vo.RestRequest;

/**
 * @ClassName PrinterConfig
 * @Description 打印配置类
 * @Date 2021/1/16 9:28
 * @Author Rick wlwq
 */
public class PrinterConfig {

    /**
     * *必填*：芯烨云后台注册账号（即邮箱地址或开发者ID），开发者用户注册成功之后，登录芯烨云后台，在【个人中心=》开发者信息】下可查看开发者ID
     */
    public static final String USER_NAME = "19913145893@163.com";


    /**
     * *必填*：芯烨云后台注册账号后自动生成的开发者密钥，开发者用户注册成功之后，登录芯烨云后台，在【个人中心=》开发者信息】下可查看开发者密钥
     */
    public static final String USER_KEY = "0e8aa88defc3475cb9ea954662537eec";



    /**
     * 生成通用的请求头
     *
     * @param request 所有请求都必须传递的参数。
     */
    public static void createRequestHeader(RestRequest request) {
        //*必填*：芯烨云平台注册用户名（开发者 ID）
        request.setUser(USER_NAME);
        //*必填*：当前UNIX时间戳
        request.setTimestamp(System.currentTimeMillis() + "");
        //*必填*：对参数 user + UserKEY + timestamp 拼接后（+号表示连接符）进行SHA1加密得到签名，值为40位小写字符串，其中 UserKEY 为用户开发者密钥
        request.setSign(HashSignUtil.sign(request.getUser() + USER_KEY + request.getTimestamp()));

        //debug=1返回非json格式的数据，仅测试时候使用
        request.setDebug("0");
    }
}
