package com.wlwq.service;

import cn.hutool.http.HttpUtil;
import com.wlwq.config.BaiDuAIConfig;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName AuthService
 * @Description 百度AI授权服务
 * @Date 2021/3/13 10:21
 * @Author Rick wlwq
 */
@Component
public class AuthService {

    /**
     * @Description 百度AI授权->获取token
     * @author Rick wlwq
     * @Date 2021/2/7 11:07
     */
    public String getAuth() {
        Map<String, Object> params = new HashMap<>(3);
        params.put("grant_type", BaiDuAIConfig.GRANT_TYPE);
        params.put("client_id", BaiDuAIConfig.API_KEY);
        params.put("client_secret", BaiDuAIConfig.SECRET_KEY);
        String resultJson = HttpUtil.post(BaiDuAIConfig.AUTH_URL, params);
        JSONObject jsonObject = new JSONObject(resultJson);
        return jsonObject.getString("access_token");
    }
}
