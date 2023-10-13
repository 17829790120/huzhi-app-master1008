package com.wlwq.privatePhone.axService;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

import com.wlwq.common.exception.ApiException;
import com.wlwq.privatePhone.config.HuaWeiPrivatePhoneConfig;
import com.wlwq.privatePhone.domain.AxServiceLog;
import com.wlwq.privatePhone.domain.VirtualNumber;
import com.wlwq.privatePhone.mapper.AxServiceLogMapper;
import com.wlwq.privatePhone.mapper.VirtualNumberMapper;
import com.wlwq.privatePhone.service.IAxServiceLogService;
import com.wlwq.privatePhone.service.IVirtualNumberService;
import com.wlwq.privatePhone.utils.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Rick wlwq
 * @Description AX模式接口测试
 * @Date 2021/4/6 10:51
 */
@Slf4j
public class AXInterfaceImpl implements IAXInterface {

    /**
     * APP_Key
     */
    private final String appKey;
    /**
     * APP_Secret
     */
    private final String appSecret;

    /**
     * APP接入地址
     */
    private final String ompDomainName;

    public AXInterfaceImpl(String appKey, String appSecret, String ompDomainName) {
        this.appKey = appKey;
        this.appSecret = appSecret;
        this.ompDomainName = ompDomainName;
    }

    /**
     * Build the real url of https request | 构建隐私保护通话平台请求路径
     *
     * @param path 接口访问URI
     * @return
     */
    private String buildOmpUrl(String path) {
        return ompDomainName + path;
    }

    @Override
    public String axBindNumber(String origNum, String privateNum, String calleeNumDisplay, String callDirection) {
        if (StringUtils.isBlank(origNum) || StringUtils.isBlank(privateNum) || StringUtils.isBlank(calleeNumDisplay)) {
            log.error("axBindNumber set params error 参数为空");
            throw new ApiException("参数为空");
        }
        // 必填,AX模式绑定接口访问URI
        String url = HuaWeiPrivatePhoneConfig.AX_BIND_URL;
        String realUrl = buildOmpUrl(url);

        // 封装JOSN请求
        JSONObject json = new JSONObject();
        // A方真实号码(手机或固话)
        json.put("origNum", origNum);
        // 已订购的隐私号码(X号码)
        json.put("privateNum", privateNum);
        // 设置非A用户呼叫X时,A接到呼叫时的主显号码
        json.put("calleeNumDisplay", calleeNumDisplay);

        //选填,各参数要求请参考"AX模式绑定接口"
        //固定为mobile-virtual
//        json.put("privateNumType", "mobile-virtual");
        //城市码
//        json.put("areaCode", "0755");
        //号码筛选方式
//        json.put("areaMatchMode", "1");
        //是否通话录音
        json.put("recordFlag", true);
        //录音提示音
//        json.put("recordHintTone", "recordHintTone.wav");
        //是否支持短信功能
        json.put("privateSms", false);
//        JSONObject preVoice = new JSONObject();
        //设置A拨打X号码时的通话前等待音
//        preVoice.put("callerHintTone", "callerHintTone.wav");
        //设置非A用户拨打X号码时的通话前等待音
//        preVoice.put("calleeHintTone", "calleeHintTone.wav");
        //个性化通话前等待音
//        json.put("preVoice", preVoice);
        //设置允许单次通话进行的最长时间，单位为分钟
//        json.put("maxDuration", 120);
        //设置通话剩余最后一分钟时的提示音
//        json.put("lastMinVoice", "lastMinVoice.wav");
        //用户自定义数据
//        json.put("userData", "test123");
        //呼叫方向控制  0：允许双向呼叫。 1：只允许A呼叫X号码。 2：只允许其他号码呼叫X号码。如果不携带该参数，系统默认该参数为0。
        json.put("callDirection", callDirection);

        String result = HttpUtil.sendPost(appKey, appSecret, realUrl, json.toString());
        log.info("AX绑定响应 :" + result);
        return result;
    }

    @Override
    public void axModifyNumber(String subscriptionId, String origNum, String privateNum, boolean privateSms) {
        if (StringUtils.isBlank(subscriptionId) && StringUtils.isBlank(privateNum)) {
            log.info("axModifyNumber set params error");
            return;
        }

        // 必填,AX模式绑定信息修改接口访问URI
        String url = "/rest/provision/caas/privatenumber/v1.0";
        String realUrl = buildOmpUrl(url);

        // 封装JOSN请求
        JSONObject json = new JSONObject();
        if (StringUtils.isNotBlank(subscriptionId)) {
            // 绑定关系ID
            json.put("subscriptionId", subscriptionId);
        } else {
            // AX中的X号码
            json.put("privateNum", privateNum);
        }
        if (StringUtils.isNotBlank(origNum)) {
            // AX中的A号码
            json.put("origNum", origNum);
        }
        // 是否支持短信功能
        json.put("privateSms", privateSms);

        /**
         * 选填,各参数要求请参考"AX模式绑定信息修改接口"
         */
//        json.put("recordFlag", true); //是否通话录音
//        json.put("maxDuration", 120); //修改允许单次通话进行的最长时间，单位为分钟
//        json.put("lastMinVoice", "lastMinVoice.wav"); //设置通话剩余最后一分钟时的提示音
//        json.put("userData", "test123"); //用户自定义数据
//        json.put("callDirection", 0); //呼叫方向控制

        String result = HttpUtil.sendPut(appKey, appSecret, realUrl, json.toString());
        log.info("Response is :" + result);
    }

    @Override
    public String axUnbindNumber(String subscriptionId, String privateNum) {
        if (StringUtils.isBlank(subscriptionId) && StringUtils.isBlank(privateNum)) {
            log.info("axUnbindNumber set params error");
            throw new ApiException("参数为空！");
        }

        // 必填,AX模式解绑接口访问URI
        String url = HuaWeiPrivatePhoneConfig.AX_UNBIND_URL;
        String realUrl = buildOmpUrl(url);

        // 申明对象
        Map<String, Object> map = new HashMap<String, Object>(1);
        if (StringUtils.isNotBlank(subscriptionId)) {
            // 绑定关系ID
            map.put("subscriptionId", subscriptionId);
        } else {
            // AX中的X号码
            map.put("privateNum", privateNum);
        }

        String result = HttpUtil.sendDelete(appKey, appSecret, realUrl, HttpUtil.map2UrlEncodeString(map));
        log.info("Response is :" + result);
        return result;
    }

    @Override
    public void axQueryBindRelation(String subscriptionId, String origNum, String privateNum) {
        if (StringUtils.isBlank(subscriptionId) && StringUtils.isBlank(origNum) && StringUtils.isBlank(privateNum)) {
            log.error("axQueryBindRelation set parsms error");
            return;
        }

        // 必填,AX模式绑定信息查询接口访问URI
        String url = "/rest/provision/caas/privatenumber/v1.0";
        String realUrl = buildOmpUrl(url);

        // 申明对象
        Map<String, Object> map = new HashMap<String, Object>(1);
        if (StringUtils.isNotBlank(subscriptionId)) {
            // 指定绑定关系ID进行查询
            map.put("subscriptionId", subscriptionId);
        } else {
            if (StringUtils.isNotBlank(origNum)) {
                // 指定A号码进行查询
                map.put("origNum", origNum);
            } else {
                // 指定X号码进行查询
                map.put("privateNum", privateNum);
            }
        }

        String result = HttpUtil.sendGet(appKey, appSecret, realUrl, HttpUtil.map2UrlEncodeString(map));
        log.info("Response is :" + result);
    }

    @Override
    public void axSetCalleeNumber(String subscriptionId, String privateNum, String calleeNum) {
        if (StringUtils.isBlank(calleeNum)
                || (StringUtils.isBlank(subscriptionId) && StringUtils.isBlank(privateNum))) {
            log.info("axSetCalleeNumber set params error");
            return;
        }

        // 必填,AX模式设置临时被叫接口
        String url = HuaWeiPrivatePhoneConfig.AX_SET_CALLEE_NUMBER_URL;
        String realUrl = buildOmpUrl(url);

        // 封装JOSN请求
        JSONObject json = new JSONObject();
        if (StringUtils.isNotBlank(subscriptionId)) {
            // 绑定关系ID
            json.put("subscriptionId", subscriptionId);
        } else {
            // AX中的X号码
            json.put("privateNum", privateNum);
        }
        // 本次呼叫的真实被叫号码
        json.put("calleeNum", calleeNum);

        // 选填,各参数要求请参考"AX模式设置临时被叫接口"
        //临时被叫关系保持时间,单位为秒
        json.put("duration", 120);
        //用户自定义数据
        json.put("userData", "test666");

        String result = HttpUtil.sendPut(appKey, appSecret, realUrl, json.toString());
        log.info("Response is :" + result);
    }

    @Override
    public String axGetRecordDownloadLink(String recordDomain, String fileName) {
        if (StringUtils.isBlank(recordDomain) || StringUtils.isBlank(fileName)) {
            log.error("axGetRecordDownloadLink set params error");
            throw new ApiException("必填参数为空！");
        }
        // 必填,AX模式获取录音文件下载地址接口访问URI
        String url = HuaWeiPrivatePhoneConfig.AX_GET_RECORD_DOWNLOAD_LINK_URL;
        String realUrl = buildOmpUrl(url);

        // 申明对象
        Map<String, Object> map = new HashMap<String, Object>(2);
        // 录音文件存储的服务器域名
        map.put("recordDomain", recordDomain);
        // 录音文件名
        map.put("fileName", fileName);

        String result = HttpUtil.sendGet(appKey, appSecret, realUrl, HttpUtil.map2UrlEncodeString(map));
        log.info("获取通话录音结果：{{}} :" , result);

        return result;
    }

    @Override
    public void axStopCall(String sessionId) {
        if (StringUtils.isBlank(sessionId)) {
            log.info("axStopCall set params error");
            return;
        }

        // 必填,AX模式终止呼叫接口访问URI
        String url = HuaWeiPrivatePhoneConfig.AX_STOP_CALL_URL;
        String realUrl = buildOmpUrl(url);

        // 封装JOSN请求
        JSONObject json = new JSONObject();
        // 呼叫会话ID
        json.put("sessionid", sessionId);
        // 取值固定为"call_stop"
        json.put("signal", "call_stop");

        String result = HttpUtil.sendPost(appKey, appSecret, realUrl, json.toString());
        log.info("Response is :" + result);
    }
}