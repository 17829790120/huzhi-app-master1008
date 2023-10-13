package com.wlwq.fluorite.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName PlayResultVO
 * @Description 获取播放地址返回值封装
 * @Date 2021/4/13 16:34
 * @Author Rick wlwq
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PlayResultVO implements Serializable {

    /** id. */
    private String id;

    /** 直播地址. */
    private String url;

    /** 直播地址有效期。expireTime参数为空时该字段无效. */
    private String expireTime;

}
