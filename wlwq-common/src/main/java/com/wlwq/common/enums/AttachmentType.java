package com.wlwq.common.enums;

/**
 *  Create By Renbowen
 *  @Date: 2020/9/26 17:51
 *  @Description: 附件上传类型
 */
public enum AttachmentType implements ValueEnum<Integer> {

    /**
     * 服务器
     */
    LOCAL(0),

    /**
     * 又拍云
     */
    UPOSS(1),

    /**
     * 七牛云
     */
    QINIUOSS(2),

    /**
     * sm.ms
     */
    SMMS(3),

    /**
     * 阿里云
     */
    ALIOSS(4),

    /**
     * 百度云
     */
    BAIDUBOS(5),

    /**
     * 腾讯云
     */
    TENCENTCOS(6),

    /**
     * 华为云
     */
    HUAWEIOBS(7);

    private final Integer value;

    AttachmentType(Integer value) {
        this.value = value;
    }

    /**
     * 获取值
     *
     * @return enum value
     */
    @Override
    public Integer getValue() {
        return value;
    }
}
