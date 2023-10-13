package com.wlwq.fluorite.service;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSONObject;
import com.wlwq.common.exception.ApiException;
import com.wlwq.common.utils.StringUtils;
import com.wlwq.fluorite.config.FluoriteCloudConfig;
import com.wlwq.fluorite.constant.FluoriteCloudConstant;
import com.wlwq.fluorite.params.AddVideoRobotParams;
import com.wlwq.fluorite.params.PlayParams;
import com.wlwq.fluorite.params.TakePictureParams;
import com.wlwq.fluorite.params.UpdateVideoRobotParams;
import com.wlwq.fluorite.result.PlayResultVO;
import com.wlwq.framework.redis.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName FluoriteCloudService
 * @Description 萤石云监控服务
 * @Date 2021/4/13 14:42
 * @Author Rick wlwq
 */
@Component
public class FluoriteCloudService {

    @Autowired
    private RedisCache redisCache;

    /**
    *  @Description 获取播放地址
    *  @author Rick wlwq
    *  @Date   2021/4/13 16:24
    */
    public PlayResultVO playAddressUrl(PlayParams params){
        // 创建参数对象
        Map<String, Object> paramsMap = new HashMap<>(10);
        paramsMap.put("accessToken", getAccessToken());
        paramsMap.put("deviceSerial", params.getDeviceSerial());
        paramsMap.put("channelNo", params.getChannelNo());
        paramsMap.put("code", params.getCode());
        paramsMap.put("expireTime", params.getExpireTime());
        paramsMap.put("protocol", params.getProtocol());
        paramsMap.put("quality", params.getQuality());
        paramsMap.put("startTime", params.getStartTime());
        paramsMap.put("stopTime", params.getStopTime());
        paramsMap.put("type", params.getType());
        String result = HttpRequest.post(FluoriteCloudConfig.PLAY_URL)
                .form(paramsMap)
                //超时，毫秒
                .timeout(20000)
                .execute().body();
        JSONObject jsonObject = JSONObject.parseObject(result);
        if (FluoriteCloudConstant.NOT_AUTH_VALUE.equals(jsonObject.getString(FluoriteCloudConstant.RESULT_KEY))){
            refreshAccessToken();
            playAddressUrl(params);
        }
        // 若返回值不等于200
        if (!FluoriteCloudConstant.SUCCESS_CODE_VALUE.equals(jsonObject.getString(FluoriteCloudConstant.RESULT_KEY))){
            throw new ApiException(jsonObject.getString(FluoriteCloudConstant.RESULT_KEY) + ":" + jsonObject.getString("msg"));
        }
        // 返回图片
        return PlayResultVO.builder()
                .id(jsonObject.getJSONObject("data").getString("id"))
                .url(jsonObject.getJSONObject("data").getString("url"))
                .expireTime(jsonObject.getJSONObject("data").getString("expireTime"))
                .build();
    }

    /**
    *  @Description 设备抓拍
    *  @author Rick wlwq
    *  @Date   2021/4/13 16:14
    */
    public String takePicture(TakePictureParams params){
        // 创建参数对象
        Map<String, Object> paramsMap = new HashMap<>(2);
        paramsMap.put("accessToken", getAccessToken());
        paramsMap.put("deviceSerial", params.getDeviceSerial());
        paramsMap.put("channelNo", params.getChannelNo());
        String result = HttpRequest.post(FluoriteCloudConfig.TAKE_PICTURE_URL)
                .form(paramsMap)
                //超时，毫秒
                .timeout(20000)
                .execute().body();
        JSONObject jsonObject = JSONObject.parseObject(result);
        if (FluoriteCloudConstant.NOT_AUTH_VALUE.equals(jsonObject.getString(FluoriteCloudConstant.RESULT_KEY))){
            refreshAccessToken();
            takePicture(params);
        }
        // 若返回值不等于200
        if (!FluoriteCloudConstant.SUCCESS_CODE_VALUE.equals(jsonObject.getString(FluoriteCloudConstant.RESULT_KEY))){
            throw new ApiException(jsonObject.getString(FluoriteCloudConstant.RESULT_KEY) + ":" + jsonObject.getString("msg"));
        }
        // 返回图片
        return jsonObject.getJSONObject("data").getString("picUrl");
    }


    /**
    *  @Description 修改设备名称
    *  @author Rick wlwq
    *  @Date   2021/4/13 16:08
    */
    public void updateVideoRobotName(UpdateVideoRobotParams params){
        // 创建参数对象
        Map<String, Object> paramsMap = new HashMap<>(2);
        paramsMap.put("accessToken", getAccessToken());
        paramsMap.put("deviceSerial", params.getDeviceSerial());
        paramsMap.put("deviceName", params.getDeviceName());
        String result = HttpRequest.post(FluoriteCloudConfig.UPDATE_VIDEO_ROBOT_NAME_URL)
                .form(paramsMap)
                //超时，毫秒
                .timeout(20000)
                .execute().body();
        JSONObject jsonObject = JSONObject.parseObject(result);
        if (FluoriteCloudConstant.NOT_AUTH_VALUE.equals(jsonObject.getString(FluoriteCloudConstant.RESULT_KEY))){
            refreshAccessToken();
            updateVideoRobotName(params);
        }
        // 若返回值不等于200
        if (!FluoriteCloudConstant.SUCCESS_CODE_VALUE.equals(jsonObject.getString(FluoriteCloudConstant.RESULT_KEY))){
            throw new ApiException(jsonObject.getString(FluoriteCloudConstant.RESULT_KEY) + ":" + jsonObject.getString("msg"));
        }
    }

    /**
    *  @Description 删除设备
    *  @author Rick wlwq
    *  @Date   2021/4/13 16:03
     * @Param deviceSerial 设备编号
    */
    public void deleteVideoRobot(String deviceSerial){
        // 创建参数对象
        Map<String, Object> paramsMap = new HashMap<>(2);
        paramsMap.put("accessToken", getAccessToken());
        paramsMap.put("deviceSerial", deviceSerial);
        String result = HttpRequest.post(FluoriteCloudConfig.DELETE_VIDEO_ROBOT_URL)
                .form(paramsMap)
                //超时，毫秒
                .timeout(20000)
                .execute().body();
        JSONObject jsonObject = JSONObject.parseObject(result);
        if (FluoriteCloudConstant.NOT_AUTH_VALUE.equals(jsonObject.getString(FluoriteCloudConstant.RESULT_KEY))){
            refreshAccessToken();
            deleteVideoRobot(deviceSerial);
        }
        // 若返回值不等于200
        if (!FluoriteCloudConstant.SUCCESS_CODE_VALUE.equals(jsonObject.getString(FluoriteCloudConstant.RESULT_KEY))){
            throw new ApiException(jsonObject.getString(FluoriteCloudConstant.RESULT_KEY) + ":" + jsonObject.getString("msg"));
        }
    }


    /**
    *  @Description 添加设备到账号下
    *  @author Rick wlwq
    *  @Date   2021/4/13 15:45
    */
    public void addVideoRobot(AddVideoRobotParams params){
        // 创建参数对象
        Map<String, Object> paramsMap = new HashMap<>(3);
        paramsMap.put("accessToken", getAccessToken());
        paramsMap.put("deviceSerial", params.getDeviceSerial());
        paramsMap.put("validateCode", params.getValidateCode());
        String result = HttpRequest.post(FluoriteCloudConfig.ADD_VIDEO_ROBOT_URL)
                .form(paramsMap)
                //超时，毫秒
                .timeout(20000)
                .execute().body();
        JSONObject jsonObject = JSONObject.parseObject(result);
        // 10002 未授权或授权过期值
        if (FluoriteCloudConstant.NOT_AUTH_VALUE.equals(jsonObject.getString(FluoriteCloudConstant.RESULT_KEY))){
            refreshAccessToken();
            addVideoRobot(params);
        }
        // 若返回值不等于200
        if (!FluoriteCloudConstant.SUCCESS_CODE_VALUE.equals(jsonObject.getString(FluoriteCloudConstant.RESULT_KEY))){
            throw new ApiException(jsonObject.getString(FluoriteCloudConstant.RESULT_KEY) + ":" + jsonObject.getString("msg"));
        }
    }

    /**
     * @Description 获取萤石云AccessToken
     * @author Rick wlwq
     * @Date 2021/4/13 14:44
     */
    public String getAccessToken() {
        // 先从redis中获取AccessToke 每个有效期为7天 频繁调用会造成接口调用次数浪费
        if (StringUtils.isNotBlank(redisCache.getCacheObject(FluoriteCloudConstant.FLUORITE_CLOUD_ACCESS_TOKEN_REDIS_KEY))){
            // 返回redis中存储的accessToken
            return redisCache.getCacheObject(FluoriteCloudConstant.FLUORITE_CLOUD_ACCESS_TOKEN_REDIS_KEY);
        }
        String result = HttpRequest.post(FluoriteCloudConfig.AUTH_URL)
                .form(getAuthParamsMap())
                //超时，毫秒
                .timeout(20000)
                .execute().body();
        JSONObject jsonObject = JSONObject.parseObject(result);
        if (FluoriteCloudConstant.SUCCESS_CODE_VALUE.equals(jsonObject.getString(FluoriteCloudConstant.RESULT_KEY))){
            String accessToken = jsonObject.getJSONObject("data").getString("accessToken");
            // 存入redis中  过期时间为7天 604800秒 少存入100秒为了防止存入redis中与实际时间不符而导致token失效
            redisCache.setCacheObject(FluoriteCloudConstant.FLUORITE_CLOUD_ACCESS_TOKEN_REDIS_KEY,accessToken,604700, TimeUnit.SECONDS);
            return accessToken;
        }
        throw new ApiException("获取萤石云AccessToken异常！");
    }


    /**
    *  @Description 刷新accessToken
    *  @author Rick wlwq
    *  @Date   2021/4/13 15:47
    */
    public void refreshAccessToken() {
        // 删除redis中的accessToken
        redisCache.deleteObject(FluoriteCloudConstant.FLUORITE_CLOUD_ACCESS_TOKEN_REDIS_KEY);
        String result = HttpRequest.post(FluoriteCloudConfig.AUTH_URL)
                .form(getAuthParamsMap())
                //超时，毫秒
                .timeout(20000)
                .execute().body();
        JSONObject jsonObject = JSONObject.parseObject(result);
        if (FluoriteCloudConstant.SUCCESS_CODE_VALUE.equals(jsonObject.getString(FluoriteCloudConstant.RESULT_KEY))){
            String accessToken = jsonObject.getJSONObject("data").getString("accessToken");
            // 存入redis中  过期时间为7天 604800秒 少存入100秒为了防止存入redis中与实际时间不符而导致token失效
            redisCache.setCacheObject(FluoriteCloudConstant.FLUORITE_CLOUD_ACCESS_TOKEN_REDIS_KEY,accessToken,604700, TimeUnit.SECONDS);
        }
    }

    /**
    *  @Description 获取授权参数对象
    *  @author Rick wlwq
    *  @Date   2021/4/13 15:51
    */
    public Map<String,Object> getAuthParamsMap(){
        // 创建参数对象
        Map<String, Object> paramsMap = new HashMap<>(2);
        paramsMap.put("appKey", FluoriteCloudConfig.APP_KEY);
        paramsMap.put("appSecret", FluoriteCloudConfig.APP_SECRET);
        return paramsMap;
    }
}
