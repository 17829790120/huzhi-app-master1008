package com.wlwq.api.resultVO;

import com.github.pagehelper.PageInfo;
import lombok.Data;

import java.util.List;

@Data
public class CustomRankingVO {
    /**
     * 用户id
     */
    private String customUserId;
    /**
     * 员工姓名
     */
    private String userName;
    /**
     * 所在部门
     */
    private String deptName;
    /**
     * 成交量
     */
    private String dealAmount;
    /**
     * 序号
     */
    private String rankNo;
    /**
     * 排行榜
     */
    private PageInfo<CustomRankingListVO> customRankingList;
}
