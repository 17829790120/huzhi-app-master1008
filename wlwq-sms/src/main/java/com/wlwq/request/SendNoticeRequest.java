package com.wlwq.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @ClassName SendNoticeRequest
 * @Description 发送短信参数封装
 * @Date 2021/7/8 10:59
 * @Author Rick wlwq
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SendNoticeRequest implements Serializable {

    @NotBlank(message = "手机号为空！")
    private String phone;

    @NotBlank(message = "配置标识为空！")
    private String configId;

    @NotBlank(message = "签名配置标识为空！")
    private String signId;

    @NotBlank(message = "模版配置标识为空！")
    private String templateId;

    /** 人机验证配置标识. */
//    @NotBlank(message = "人机验证配置标识为空！")
    private String robotValidConfigId;

//    @NotBlank(message = "人机验证sessionId为空！")
    private String sessionId;

//    @NotBlank(message = "人机验证签名字符串为空！")
    private String sig;

//    @NotBlank(message = "人机验证请求唯一标识为空！")
    private String token;

    /** 后端接口内获取（前端不需要传）. */
    private String clientIp;
}
