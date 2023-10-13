package com.wlwq.privatePhone.axService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wlwq.common.exception.ApiException;
import com.wlwq.common.utils.StringUtils;
import com.wlwq.privatePhone.config.HuaWeiPrivatePhoneConfig;
import com.wlwq.privatePhone.domain.AxServiceLog;
import com.wlwq.privatePhone.domain.VirtualNumber;
import com.wlwq.privatePhone.params.AxBindParams;
import com.wlwq.privatePhone.service.IAxServiceLogService;
import com.wlwq.privatePhone.service.IVirtualNumberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Rick wlwq
 * @Description AX模式
 * @Date 2021/4/6 10:01
 */
@Slf4j
@Component
public class AXService {

    /**
     * 隐私号码服务
     */
    @Autowired
    private IVirtualNumberService virtualNumberService;

    @Autowired
    private IAxServiceLogService logService;

    /**
     * @param recordDomain 录音文件存储的服务器域名
     * @param fileName     录音文件名
     * @Description 获取录音文件
     * @author Rick wlwq
     * @Date 2021/7/12 10:18
     */
    public String axGetRecordDownloadLink(String recordDomain, String fileName) {
        IAXInterface ax = new AXInterfaceImpl(HuaWeiPrivatePhoneConfig.OMP_APP_KEY, HuaWeiPrivatePhoneConfig.OMP_APP_SECRET, HuaWeiPrivatePhoneConfig.OMP_DOMAIN_NAME);
        return ax.axGetRecordDownloadLink(recordDomain,fileName);
//        String result = ax.axGetRecordDownloadLink(recordDomain,fileName);
//        JSONObject  resultJson = JSON.parseObject(result);
//        // 判断响应Code是否为0 若为301则成功
//        if (HuaweiResultCodeEnum.GET_VOICE_SUCCESS.getCode().equals(resultJson.getString("Code"))) {
//            return resultJson.getString("Location");
//        } else {
//            throw new ApiException("获取录音文件失败！" + resultJson.getString("resultcode") + "-" + resultJson.getString("resultdesc"));
//        }
    }

    /**
     * @Description AX隐私号码解绑
     * @author Rick wlwq
     * @Date 2021/4/6 16:50
     * 参数二选一 都传入优先使用subId
     */
    @Transactional(rollbackFor = Exception.class)
    public void axUnbindNumber(String privateNumber, String subId) {
        // 根据隐私号码查询隐私号码对象
        VirtualNumber virtualNumber = virtualNumberService.selectVirtualNumberByPrivatePhone(privateNumber);
        if (StringUtils.isNull(virtualNumber)) {
            throw new ApiException("未获取到隐私手机号信息！");
        }
        IAXInterface ax = new AXInterfaceImpl(HuaWeiPrivatePhoneConfig.OMP_APP_KEY, HuaWeiPrivatePhoneConfig.OMP_APP_SECRET, HuaWeiPrivatePhoneConfig.OMP_DOMAIN_NAME);
        String result = ax.axUnbindNumber(subId, HuaWeiPrivatePhoneConfig.CHINESE_PHONE_PREFIX + privateNumber);
        JSONObject resultJson = JSON.parseObject(result);
        // 判断响应resultcode是否为0 若为0则成功
        if (HuaweiResultCodeEnum.SUCCESS.getCode().equals(resultJson.getString("resultcode"))) {
            // 修改状态
            virtualNumberService.updateVirtualNumber(VirtualNumber.builder()
                    .virtualNumberId(virtualNumber.getVirtualNumberId())
                    .realNumber("0")
                    .subId("0")
                    .status(0)
                    .build());
        } else {
            throw new ApiException("解绑失败！" + resultJson.getString("resultcode") + "-" + resultJson.getString("resultdesc"));
        }
    }

    /**
     * @Description AX绑定手机号
     * @author Rick wlwq
     * @Date 2021/4/6 14:34
     * @Return 虚拟号码
     */
    @Transactional(rollbackFor = Exception.class)
    public String axBind(AxBindParams params) {
        IAXInterface ax = new AXInterfaceImpl(HuaWeiPrivatePhoneConfig.OMP_APP_KEY, HuaWeiPrivatePhoneConfig.OMP_APP_SECRET, HuaWeiPrivatePhoneConfig.OMP_DOMAIN_NAME);
        // 获取可用隐私号码
        VirtualNumber virtualNumber = getPrivatePhoneNumber();
        String result = ax.axBindNumber(HuaWeiPrivatePhoneConfig.CHINESE_PHONE_PREFIX + params.getAccountPhone(), HuaWeiPrivatePhoneConfig.CHINESE_PHONE_PREFIX + virtualNumber.getVirtualNumber(), params.getCalleeNumDisplay(), params.getCallDirection());
        JSONObject resultJson = JSON.parseObject(result);
        // 判断响应resultcode是否为0 若为0则成功
        if (HuaweiResultCodeEnum.SUCCESS.getCode().equals(resultJson.getString("resultcode"))) {
            // 解析值 修改虚拟号码状态
            virtualNumberService.updateVirtualNumber(VirtualNumber.builder()
                    .virtualNumberId(virtualNumber.getVirtualNumberId())
                    .realNumber(params.getAccountPhone())
                    .subId(resultJson.getString("subscriptionId"))
                    .status(1)
                    .build());
            // 记录绑定日志
            logService.insertAxServiceLog(AxServiceLog.builder()
                    .virtualNumber(virtualNumber.getVirtualNumber())
                    .realNumber(params.getAccountPhone())
                    .subId(resultJson.getString("subscriptionId"))
                    .remarks(resultJson.getString("resultdesc"))
                    .status(1)
                    .build());
            return virtualNumber.getVirtualNumber();
        }
        // 否则 请求失败
        else {
            // 数据库修改该号码状态
            virtualNumberService.updateVirtualNumber(VirtualNumber.builder()
                    .virtualNumberId(virtualNumber.getVirtualNumberId())
                    .status(0)
                    .build());
            // 存储绑定异常信息
            logService.insertAxServiceLog(AxServiceLog.builder()
                    .virtualNumber(virtualNumber.getVirtualNumber())
                    .realNumber(params.getAccountPhone())
                    .remarks(resultJson.getString("resultcode") + "-" + resultJson.getString("resultdesc"))
                    .status(0)
                    .build());
            throw new ApiException(resultJson.getString("resultcode") + "-" + resultJson.getString("resultdesc"));
        }
    }

    /**
     * @Description 获取一个可用的隐私号码
     * @author Rick wlwq
     * @Date 2021/4/6 14:35
     */
    @Transactional(rollbackFor = Exception.class)
    public synchronized VirtualNumber getPrivatePhoneNumber() {
        VirtualNumber virtualNumber = virtualNumberService.selectCanUseVirtualNumber();
        if (StringUtils.isNull(virtualNumber)) {
            throw new ApiException("当前隐私号码池无空闲，请稍后重试！");
        }
        // 获取到之后直接修改号码状态为已占用
        virtualNumberService.updateVirtualNumber(VirtualNumber.builder()
                .virtualNumberId(virtualNumber.getVirtualNumberId())
                .status(1)
                .build());
        // 返回隐私号码对象
        return virtualNumber;
    }
}