package com.wlwq.service;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.wlwq.common.exception.ApiException;
import com.wlwq.common.utils.StringUtils;
import com.wlwq.common.utils.uuid.IdUtils;
import com.wlwq.constant.AliSmsConstant;
import com.wlwq.constant.SmsTypeConstant;
import com.wlwq.framework.redis.RedisCache;
import com.wlwq.note.domain.*;
import com.wlwq.note.service.*;
import com.wlwq.request.SendNoticeRequest;
import com.wlwq.result.CodeResult;
import com.wlwq.service.aliyun.AliSmsService;
import com.wlwq.service.tencent.TencentSmsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName SmsService
 * @Description 短信发送服务
 * @Date 2021/1/6 15:32
 * @Author Rick wlwq
 */
@Component
@Slf4j
public class SmsService {

    /**
     * 阿里云短信回调成功的值
     */
    private final static String A_LI_SMS_SUCCESS_VALUE = "OK";

    /**
     * redis的key前缀
     */
    private final static String REDIS_KEY_PREFIX = "sms_code:";

    /**
     * 验证码有效期 单位分
     */
    private final static Integer SMS_VALID_TIME = 5;

    @Autowired
    private RedisCache redisCache;




    /**
     * @Description 发送验证码
     * @author Rick wlwq
     * @Date 2021/7/8 11:52
     */
    public String sendSms(SendNoticeRequest request) {
        Objects.requireNonNull(request, "发送短信请求参数不能是空！");
        // 获取短信配置
        NoteConfig noteConfig = noteConfigService.selectNoteConfigById(request.getConfigId());
        if (StringUtils.isNull(noteConfig)) {
            throw new ApiException("未获取到配置信息！");
        }
        NoteSign noteSign = noteSignService.selectNoteSignById(request.getSignId());
        if (StringUtils.isNull(noteSign)) {
            throw new ApiException("未获取到签名信息！");
        }
        // 判断配置是否一致
        if (!noteConfig.getNoteConfigId().equals(noteSign.getNoteConfigId())) {
            throw new ApiException("签名与配置不符！");
        }
        NoteTemplate noteTemplate = noteTemplateService.selectNoteTemplateById(request.getTemplateId());
        if (StringUtils.isNull(noteTemplate)) {
            throw new ApiException("未获取到模版信息！");
        }
        if (!noteConfig.getNoteConfigId().equals(noteTemplate.getNoteConfigId())) {
            throw new ApiException("模版与配置不符！");
        }
        if (AliSmsConstant.ROBOT_FLAG) {
            // 若短信模板类型为验证码
            if (noteTemplate.getNoteTemplateType().equals(AliSmsConstant.SMS_TYPE_CODE)) {
                if (StringUtils.isBlank(request.getRobotValidConfigId())) {
                    throw new ApiException("发送验证码必须进行人机验证！");
                }
                if (StringUtils.isBlank(request.getSessionId())) {
                    throw new ApiException("人机验证sessionId为空！");
                }
                if (StringUtils.isBlank(request.getSig())) {
                    throw new ApiException("人机验证签名字符串为空！");
                }
                if (StringUtils.isBlank(request.getToken())) {
                    throw new ApiException("人机验证请求唯一标识为空！");
                }
                if (StringUtils.isBlank(request.getClientIp())) {
                    throw new ApiException("客户端真实IP为空！");
                }
            }
        }
        NoteValidConfig noteValidConfig = null;
        if (StringUtils.isNotBlank(request.getRobotValidConfigId())) {
            noteValidConfig = noteValidConfigService.selectNoteValidConfigById(request.getRobotValidConfigId());
            if (StringUtils.isNull(noteValidConfig)) {
                throw new ApiException("人机验证配置为空！");
            }
            if (!noteConfig.getNoteConfigId().equals(noteValidConfig.getNoteConfigId())) {
                throw new ApiException("人机验证与配置不符！");
            }
        }
        // 生成短信验证码
        String smsCode = RandomUtil.randomNumbers(6);
        if (noteConfig.getNoteConfigType().equals(SmsTypeConstant.ALI)) {
            SendSmsResponse response = AliSmsService.sendSms(request, noteConfig, noteSign, noteTemplate, noteValidConfig, smsCode);
            // 存储发送记录
            noteSendRecordService.insertNoteSendRecord(NoteSendRecord.builder()
                    .noteConfigId(noteConfig.getNoteConfigId())
                    .noteConfigType(noteConfig.getNoteConfigType())
                    .noteTemplateId(noteTemplate.getNoteTemplateId())
                    .noteTemplateName(noteTemplate.getNoteTemplateName())
                    .noteTemplateCode(noteTemplate.getNoteTemplateCode())
                    .noteTemplateType(noteTemplate.getNoteTemplateType())
                    .receivePhone(request.getPhone())
                    .noteContent(noteTemplate.getNoteTemplateType().equals(AliSmsConstant.SMS_TYPE_CODE) ? StrUtil.replace(noteTemplate.getNoteTemplateContent(), "${code}", smsCode) : noteTemplate.getNoteTemplateContent())
                    .resultBizId(response.getBody().getBizId())
                    .resultCode(response.getBody().getCode())
                    .resultMessage(response.getBody().getMessage())
                    .resultRequestId(response.getBody().getRequestId())
                    .build());
            // 校验返回值
            // 发送成功
            if (response.getBody().getCode().equals(AliSmsConstant.SEND_SUCCESS)) {
                log.info("发送的短信内容：{{}}", noteTemplate.getNoteTemplateType().equals(AliSmsConstant.SMS_TYPE_CODE) ? StrUtil.replace(noteTemplate.getNoteTemplateContent(), "${code}", smsCode) : noteTemplate.getNoteTemplateContent());
                // redis存储验证码
                // 生成随机字符串，作为redis的key
                String uuid = IdUtils.simpleUUID();
                String redisKey = REDIS_KEY_PREFIX + uuid;
                redisCache.setCacheObject(redisKey, CodeResult.builder()
                        .code(smsCode)
                        .phone(request.getPhone())
                        .build(), SMS_VALID_TIME, TimeUnit.MINUTES);
                return uuid;
            }
            // 发送失败
            throw new ApiException(response.getBody().getMessage());
        } else if (noteConfig.getNoteConfigType().equals(SmsTypeConstant.TENCENT)) {
            // 生成随机字符串，作为redis的key
            String uuid = IdUtils.simpleUUID();
            String redisKey = REDIS_KEY_PREFIX + uuid;
            com.tencentcloudapi.sms.v20210111.models.SendSmsResponse response =  TencentSmsService.sendSms(request.getPhone(),noteConfig,noteTemplate,smsCode);
            // 存储发送记录
            noteSendRecordService.insertNoteSendRecord(NoteSendRecord.builder()
                    .noteConfigId(noteConfig.getNoteConfigId())
                    .noteConfigType(noteConfig.getNoteConfigType())
                    .noteTemplateId(noteTemplate.getNoteTemplateId())
                    .noteTemplateName(noteTemplate.getNoteTemplateName())
                    .noteTemplateCode(noteTemplate.getNoteTemplateCode())
                    .noteTemplateType(noteTemplate.getNoteTemplateType())
                    .receivePhone(request.getPhone())
                    .noteContent(noteTemplate.getNoteTemplateType().equals(AliSmsConstant.SMS_TYPE_CODE) ? StrUtil.replace(noteTemplate.getNoteTemplateContent(), "{1}", smsCode) : noteTemplate.getNoteTemplateContent())
                    .resultBizId(Arrays.stream(response.getSendStatusSet()).findFirst().get().getSerialNo())
                    .resultCode(Arrays.stream(response.getSendStatusSet()).findFirst().get().getCode())
                    .resultMessage(Arrays.stream(response.getSendStatusSet()).findFirst().get().getMessage())
                    .resultRequestId(response.getRequestId())
                    .build());
            // 发送成功
            if (Arrays.stream(response.getSendStatusSet()).findFirst().get().getCode().equals(AliSmsConstant.TENCENT_SEND_SUCCESS)) {
                log.info("验证码：{{}}", smsCode);
                // 存储验证码
                redisCache.setCacheObject(redisKey, CodeResult.builder()
                        .code(smsCode)
                        .phone(request.getPhone())
                        .build(), SMS_VALID_TIME, TimeUnit.MINUTES);
                return uuid;
            }else if(Arrays.stream(response.getSendStatusSet()).findFirst().get().getCode().equals(AliSmsConstant.TENCENT_PhoneNumberDailyLimit)){
                // 发送失败
                throw new ApiException("单个手机号日下发短信条数超过设定的上限");
            }else if(Arrays.stream(response.getSendStatusSet()).findFirst().get().getCode().equals(AliSmsConstant.TENCENT_PhoneNumberOneHourLimit)){
                // 发送失败
                throw new ApiException("单个手机号1小时内下发短信条数超过设定的上限");
            }else if(Arrays.stream(response.getSendStatusSet()).findFirst().get().getCode().equals(AliSmsConstant.TENCENT_PhoneNumberThirtySecondLimit)){
                // 发送失败
                throw new ApiException("单个手机号30秒内下发短信条数超过设定的上限");
            }else if(Arrays.stream(response.getSendStatusSet()).findFirst().get().getCode().equals(AliSmsConstant.TENCENT_SerivceSuspendDueToArrears)){
                // 发送失败
                throw new ApiException("欠费被停止服务，可联系管理员充值来缴清欠款");
            }
            // 发送失败
            throw new ApiException(Arrays.stream(response.getSendStatusSet()).findFirst().get().getMessage());
        } else {
            throw new ApiException("仅开通了阿里云/腾讯云 短信，其他短信未开通！");
        }
    }

    /**
     * @Description 校验验证码
     * @author Rick wlwq
     * @Date 2021/1/6 18:25
     */
    public boolean checkCode(String uuid, String code, String phone) {
        String redisKey = REDIS_KEY_PREFIX + uuid;
        // 获取验证码
        CodeResult codeResult = redisCache.getCacheObject(redisKey);
        if (StringUtils.isNull(codeResult)) {
            return false;
        }
        if (!codeResult.getPhone().equals(phone)) {
            throw new ApiException("请勿尝试破解验证码，笼子里光景不好过！");
        }
        if (!codeResult.getCode().equals(code)) {
            return false;
        }
        // 删除redis中验证码
        redisCache.deleteObject(redisKey);
        return true;
    }

    @Autowired
    private INoteConfigService noteConfigService;

    @Autowired
    private INoteSignService noteSignService;

    @Autowired
    private INoteTemplateService noteTemplateService;

    @Autowired
    private INoteValidConfigService noteValidConfigService;

    @Autowired
    private INoteSendRecordService noteSendRecordService;


}
