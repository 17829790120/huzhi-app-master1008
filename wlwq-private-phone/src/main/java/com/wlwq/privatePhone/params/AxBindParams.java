package com.wlwq.privatePhone.params;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName AxBindParams
 * @Description AX模式绑定接口
 * @Date 2021/4/6 14:08
 * @Author Rick wlwq
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AxBindParams implements Serializable {

    /** 用户手机号码. */
    private String accountPhone;

    /** 是否显示用户号码 0：显示X号码； 1：显示真实主叫号码。. */
    private String calleeNumDisplay;

    /** 呼叫方向控制0：允许双向呼叫。 1：只允许A呼叫X号码。 2：只允许其他号码呼叫X号码。. */
    private String callDirection;

    /** 允许单次通话进行的最长时间，单位为分钟. */
//    private Integer maxDuration;
}
