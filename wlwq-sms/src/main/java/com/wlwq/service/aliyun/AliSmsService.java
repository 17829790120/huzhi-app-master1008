package com.wlwq.service.aliyun;

import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.teaopenapi.models.Config;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.afs.model.v20180112.AuthenticateSigRequest;
import com.aliyuncs.afs.model.v20180112.AuthenticateSigResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.wlwq.common.exception.ApiException;
import com.wlwq.common.utils.StringUtils;
import com.wlwq.constant.AliSmsConstant;
import com.wlwq.note.domain.NoteConfig;
import com.wlwq.note.domain.NoteSign;
import com.wlwq.note.domain.NoteTemplate;
import com.wlwq.note.domain.NoteValidConfig;
import com.wlwq.request.SendNoticeRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName AliSmsService
 * @Description 阿里云短信服务
 * @Date 2021/1/6 15:18
 * @Author Rick wlwq
 */
@Slf4j
public class AliSmsService {

    /**
     * @Description 发送验证码
     * @author Rick wlwq
     * @Date 2021/7/8 15:06
     */
    public static SendSmsResponse sendSms(SendNoticeRequest request, NoteConfig noteConfig, NoteSign noteSign, NoteTemplate noteTemplate, NoteValidConfig noteValidConfig, String smsCode) {
        // 判断是否开启人机验证
        if (AliSmsConstant.ROBOT_FLAG){
            // 若为发送验证码 执行人机验证
            if (noteTemplate.getNoteTemplateType().equals(AliSmsConstant.SMS_TYPE_CODE)){
                // 若不通过
                if (!validRobot(request,noteConfig,noteValidConfig)){
                    throw new ApiException("未通过人机验证！不能使用短信功能！");
                }
            }
        }
        // 使用AK&SK初始化账号Client
        Config config = new Config()// 您的AccessKey ID
                .setAccessKeyId(noteConfig.getAccessKeyId())
                // 您的AccessKey Secret
                .setAccessKeySecret(noteConfig.getAccessKeySecret());
        // 访问的域名
        config.endpoint = AliSmsConstant.SMS_DOMAIN;
        try {
            com.aliyun.dysmsapi20170525.Client client = new com.aliyun.dysmsapi20170525.Client(config);
            SendSmsRequest sendSmsRequest = new SendSmsRequest()
                    .setPhoneNumbers(request.getPhone())
                    .setSignName(noteSign.getNoteSignValue())
                    .setTemplateCode(noteTemplate.getNoteTemplateCode());
            // 若为发送验证码 传入参数
            if (noteTemplate.getNoteTemplateType().equals(AliSmsConstant.SMS_TYPE_CODE) && StringUtils.isNotBlank(smsCode)){
                sendSmsRequest.setTemplateParam("{\"code\":\"" + smsCode + "\"}");
            }
            // 复制代码运行请自行打印 API 的返回值
            SendSmsResponse response = client.sendSms(sendSmsRequest);
            log.info("发送短信结果返回：{{}}",response.toString());
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiException("短信发送异常！异常原因：" + e.getMessage());
        }
    }


    /**
     * @Description 人机校验
     * @author Rick wlwq
     * @Date 2021/7/8 15:30
     * 通过返回true 不通过返回false
     */
    public static boolean validRobot(SendNoticeRequest request, NoteConfig noteConfig,NoteValidConfig noteValidConfig) {
        try {
            // 初始化IClientProfile instance
            IClientProfile profile = DefaultProfile.getProfile(AliSmsConstant.VALID_ROBOT_REGION_ID, noteConfig.getAccessKeyId(), noteConfig.getAccessKeySecret());
            IAcsClient client = new DefaultAcsClient(profile);
            DefaultProfile.addEndpoint(AliSmsConstant.VALID_ROBOT_ENDPOINT_NAME, AliSmsConstant.VALID_ROBOT_REGION_ID, AliSmsConstant.VALID_ROBOT_PRODUCT, AliSmsConstant.VALID_ROBOT_DOMAIN);
            AuthenticateSigRequest authenticateSigRequest = new AuthenticateSigRequest();
            // 会话ID。必填参数，从前端获取，不可更改。
            authenticateSigRequest.setSessionId(request.getSessionId());
            // 签名串。必填参数，从前端获取，不可更改。
            authenticateSigRequest.setSig(request.getSig());
            // 请求唯一标识。必填参数，从前端获取，不可更改。
            authenticateSigRequest.setToken(request.getToken());
            // 场景标识。必填参数，从前端获取，不可更改。
            authenticateSigRequest.setScene(noteValidConfig.getScene());
            // 应用类型标识。必填参数，后端填写。
            authenticateSigRequest.setAppKey(noteValidConfig.getAppKey());
            // 客户端IP。必填参数，后端填写。
            authenticateSigRequest.setRemoteIp(request.getClientIp());
            //response的code枚举：100验签通过，900验签失败。
            AuthenticateSigResponse response = client.getAcsResponse(authenticateSigRequest);
            log.info("收到人机验证信息code:{{}},message:{{}},requestId:{{}},detail:{{}}",response.getCode(),response.getMsg(),response.getRequestId(),response.getDetail());
            if (response.getCode() == 100) {
                log.info("人机验证验签通过！");
                return true;
            } else {
                if (response.getCode() == 900){
                    log.error("阿里云验证码验签失败！");
                    return false;
                }
                log.error("人机验证验签失败！");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiException("人机校验异常！");
        }
    }

}
