package com.wlwq.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName ContentAuditResult
 * @Description 文本审核结果
 * @Date 2021/3/13 10:39
 * @Author Rick wlwq
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ContentAuditResult implements Serializable {

    /** 审核结果类型，可取值1.合规，2.不合规，3.疑似，4.审核失败. */
    private Integer conclusionType;

    /** 审核结果，可取值：合规、不合规、疑似、审核失败. */
    private String conclusion;

    /** 音频识别内容. */
    private String rawText;

}
