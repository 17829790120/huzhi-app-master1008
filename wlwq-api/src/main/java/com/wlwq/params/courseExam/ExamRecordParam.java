package com.wlwq.params.courseExam;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 考试记录
 * @author EDZ
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ExamRecordParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 考试记录表id
     */
    private String examRecordId;

    /**
     * 用户id
     */
    private String accountId;

    /**
     * 考试开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date beginTime;

    /**
     * 考试结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date endTime;

    /**
     * 0:否 1:删除
     */
    private Long delStatus;

    /**
     * 分数
     */
    private BigDecimal score;

    /**
     * 头像
     */
    private String headPortrait;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 章节ID
     */
    @NotNull(message = "请传入章节ID")
    private Long chapterId;

    /**
     * 课程ID
     */
    @NotNull(message = "请传入课程ID")
    private Long courseId;

    /**
     * 考试试卷记录表id
     */
    private String examPaperRecordId;

}
