package com.wlwq.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName ClientIpUtil
 * @Description 获取客户端ip工具类
 * @Date 2021/7/8 17:04
 * @Author Rick wlwq
 */
public class ClientIpUtil {

    /**
     * 获取客户端ip地址
     * @param request
     * @return
     */
    public static String getClientIp(HttpServletRequest request)
    {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || "".equals(ip.trim()) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || "".equals(ip.trim()) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || "".equals(ip.trim()) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        // 多个路由时，取第一个非unknown的ip
        final String[] arr = ip.split(",");
        for (final String str : arr) {
            if (!"unknown".equalsIgnoreCase(str)) {
                ip = str;
                break;
            }
        }
        return ip;
    }

}
