package com.wlwq.params.courseExam;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 直播报名记录
 * @author EDZ
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class LiveRegistrationParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @NotNull(message = "请选择报名者")
    private String accountId;

    /**
     * 昵称
     */
    @NotNull(message = "请填写报名者昵称")
    private String nickName;

    /**
     * 头像
     */
    private String headPortrait;

    /**
     * 公司ID
     */
    private Long companyId;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 用户归属部门ID
     */
    private Long deptId;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 直播id
     */
    @NotNull(message = "请选择需要报名的直播")
    private String liveVideoId;

}
