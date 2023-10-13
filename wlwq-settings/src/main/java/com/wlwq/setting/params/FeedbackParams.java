package com.wlwq.setting.params;

import com.wlwq.common.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @ClassName FeedbackParams
 * @Description 意见反馈参数封装
 * @Date 2021/6/7 18:03
 * @Author Rick wlwq
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class FeedbackParams implements Serializable {

    /** 反馈类型. */
    @NotBlank(message = "反馈类型为空！")
    private String feedbackType;

    /** 反馈内容. */
    @NotBlank(message = "反馈内容为空！")
    private String content;

    /** 联系方式 */
    private String contactWay;


    /** 反馈图片. */
    private String images;

}
