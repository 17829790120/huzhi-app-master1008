package com.wlwq.privatePhone.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wlwq.common.exception.ApiException;
import com.wlwq.common.utils.StringUtils;
import com.wlwq.privatePhone.axService.AXService;
import com.wlwq.privatePhone.domain.AxTicketRecord;
import com.wlwq.privatePhone.domain.VirtualNumber;
import com.wlwq.privatePhone.service.IAxTicketRecordService;
import com.wlwq.privatePhone.service.IVirtualNumberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName AXCallStatusNoticeReceiveController
 * @Description 接收呼叫状态通知
 * @Date 2021/4/6 14:14
 * @Author Rick wlwq
 */
@RestController
@RequestMapping(value = "/huawei/privatePhone")
@Slf4j
public class AXCallStatusNoticeReceiveController {

    @Autowired
    private IVirtualNumberService virtualNumberService;
    @Autowired
    private IAxTicketRecordService recordService;
    @Autowired
    private AXService axService;
    /**
     * @Description 话单通知接口
     * @author Rick wlwq
     * @Date 2021/7/9 15:40
     * 官网文档地址 https://support.huaweicloud.com/api-PrivateNumber/privatenumber_02_0006_1a.html
     */
    @PostMapping(value = "/ticketReceive")
    public void ticketReceive(@RequestBody String jsonBody) {
        log.info("接收到话单通知事件数据:{{}}", jsonBody);
        if (StringUtils.isBlank(jsonBody)){
            throw new ApiException("未接收到数据：华为云话单通知数据为空！");
        }
        // 封装JSON请求
        JSONObject json = JSON.parseObject(jsonBody);
        String eventType = json.getString("eventType"); // 通知事件类型

        if (!("fee".equalsIgnoreCase(eventType))) {
            log.error("话单通知的事件类型错误，错误的通知类型: {{}}", eventType);
            throw new ApiException("话单通知的事件类型错误，错误的通知类型为 " + eventType);
        }
        JSONArray feeLst = json.getJSONArray("feeLst"); // 呼叫话单事件信息
        // 短时间内有多个通话结束时隐私保护通话平台会将话单合并推送,每条消息最多携带50个话单
        if (feeLst.size() > 0) {
            for (Object loop : feeLst) {
                JSONObject loopJson = (JSONObject) loop;
                if (loopJson.containsKey("sessionId")) {
                    AxTicketRecord record = AxTicketRecord.builder()
                            .direction(loopJson.getInteger("direction"))
                            .spId(loopJson.getString("spId"))
                            .appKey(loopJson.getString("appKey"))
                            .icId(loopJson.getString("icid"))
                            .bindNum(loopJson.getString("bindNum"))
                            .sessionId(loopJson.getString("sessionId"))
                            .callerNum(loopJson.getString("callerNum"))
                            .calleeNum(loopJson.getString("calleeNum"))
                            .callinTime(loopJson.getString("callInTime"))
                            .callEndTime(loopJson.getString("callEndTime"))
                            .recordFlag(loopJson.getInteger("recordFlag"))
                            .serviceType(loopJson.getString("serviceType"))
                            .hostName(loopJson.getString("hostName"))
                            .subscriptionId(loopJson.getString("subscriptionId"))
                            .build();
                    // 若有录音
                    if (loopJson.getInteger("recordFlag").equals(1)){
                        record.setRecordStartTime(loopJson.getString("recordStartTime"));
                        record.setRecordObjectName(loopJson.getString("recordObjectName"));
                        record.setRecordBucketName(loopJson.getString("recordBucketName"));
                        record.setRecordDomain(loopJson.getString("recordDomain"));
                        // 获取隐私通话录音地址
//                        record.setRecordFileUrl(axService.axGetRecordDownloadLink(loopJson.getString("recordDomain"),loopJson.getString("recordObjectName")));
                    }
                    // 是否返回区域码
                    if (loopJson.containsKey("areaCode")) {
                        record.setAreaCode(loopJson.getString("areaCode"));
                    }
                    // 是否返回通话时长
                    if (loopJson.containsKey("callDuration")) {
                        record.setCallDuration(loopJson.getLong("callDuration"));
                    }
                    recordService.insertAxTicketRecord(record);
                }
            }
        } else {
            log.error("话单数据为空！");
        }
    }

    /**
    *  @Description X号码状态接收
    *  @author Rick wlwq
    *  @Date   2021/7/12 11:39
    */
    @PostMapping(value = "/receive")
    public void receive(@RequestBody String jsonBody) {
        log.error("X号码状态接收:{{}}", jsonBody);
        if (StringUtils.isBlank(jsonBody)) {
            throw new ApiException("未接收到数据：华为云X号码状态数据为空！");
        }
        // 封装JOSN请求
        JSONObject json = JSON.parseObject(jsonBody);
        // 通知事件类型
        String eventType = json.getString("eventType");

        if ("fee".equalsIgnoreCase(eventType)) {
            log.error("X号码状态通知的事件类型错误，错误的通知类型: {{}}", eventType);
            throw new ApiException("X号码状态通知的事件类型错误，错误的通知类型为 " + eventType);
        }
        // 呼叫状态事件信息
        JSONObject statusInfo = json.getJSONObject("statusInfo");
        // 打印通知事件类型
        log.info("eventType: " + eventType);
        //disconnect：挂机事件
        if ("disconnect".equalsIgnoreCase(eventType)) {
            /**
             * Example: 此处以解析sessionId为例,请按需解析所需参数并自行实现相关处理
             *
             * 'timestamp': 呼叫事件发生时隐私保护通话平台的UNIX时间戳
             * 'userData': 用户附属信息,仅X模式场景携带
             * 'sessionId': 通话链路的标识ID
             * 'caller': 主叫号码
             * 'called': 被叫号码
             * 'stateCode': 通话挂机的原因值
             * 'stateDesc': 通话挂机的原因值的描述
             * 'subscriptionId': 绑定关系ID
             */
            String sessionId = statusInfo.getString("sessionId");
            log.info("sessionId: " + sessionId);
            String subId = statusInfo.getString("subscriptionId");
            // 修改号码状态
            VirtualNumber virtualNumber = virtualNumberService.selectVirtualNumberBySubId(subId);
            if (StringUtils.isNotNull(virtualNumber)) {
                axService.axUnbindNumber(virtualNumber.getVirtualNumber(),null);
                // 修改状态
                virtualNumberService.updateVirtualNumber(VirtualNumber.builder()
                        .virtualNumberId(virtualNumber.getVirtualNumberId())
                        .realNumber("0")
                        .subId("0")
                        .status(0)
                        .build());
            }
        }
    }
}
