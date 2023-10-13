package com.wlwq.params.courseExam;

import com.wlwq.api.domain.ExamineFile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.List;

/**
 * 会议测试
 * @author EDZ
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class MeetingMinutesParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 会议训练纪要主键id
     */
    private String meetingTrainingMinutesId;

    /**
     * 会议训练主键id
     */
    private String meetingTrainingId;

    /**
     * 会议纪要
     */
    private String minutes;

    /**
     * 会议总结
     */
    private String summary;

    /**
     * 公司ID
     */
    private Long companyId;

    /**
     * 图片
     */
    private String picUrl;

    /**
     * 附件
     */
    private String fileUrl;
    /**
     * 文件列表
     */
    private List<ExamineFile> fileList;

    /**
     * 用户id
     */
    private String accountId;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 头像
     */
    private String headPortrait;

    /**
     * 用户手机号
     */
    private String phone;

    /**
     * 用户归属部门ID
     */
    private Long deptId;

    /**
     * 岗位IDS
     */
    private String postIds;

}
