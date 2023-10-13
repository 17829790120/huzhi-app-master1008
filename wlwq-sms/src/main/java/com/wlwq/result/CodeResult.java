package com.wlwq.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName CodeResult
 * @Description 验证码存储redis缓存对象
 * @Date 2021/7/19 14:38
 * @Author Rick wlwq
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CodeResult implements Serializable {

    /** 手机号. */
    private String phone;

    /** 验证码. */
    private String code;
}
