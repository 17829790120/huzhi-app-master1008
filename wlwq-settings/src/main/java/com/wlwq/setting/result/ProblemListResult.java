package com.wlwq.setting.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName ProblemListResult
 * @Description 协议列表
 * @Date 2021/6/7 17:24
 * @Author Rick wlwq
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ProblemListResult implements Serializable {

    /** 问题ID. */
    private Long problemId;

    /** 问题标题. */
    private String problemTitle;

    /**
     * 内容
     */
    private String problemAnswer;
}
