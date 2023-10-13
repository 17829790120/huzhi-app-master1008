package com.wlwq.api.mapper;

import java.util.List;

import com.wlwq.api.domain.ExamineCopyFlow;

/**
 * 审批抄送流程Mapper接口
 *
 * @author gaoce
 * @date 2023-05-10
 */
public interface ExamineCopyFlowMapper {
    /**
     * 查询审批抄送流程
     *
     * @param examineCopyFlowId 审批抄送流程ID
     * @return 审批抄送流程
     */
    public ExamineCopyFlow selectExamineCopyFlowById(String examineCopyFlowId);

    /**
     * 查询审批抄送流程列表
     *
     * @param examineCopyFlow 审批抄送流程
     * @return 审批抄送流程集合
     */
    public List<ExamineCopyFlow> selectExamineCopyFlowList(ExamineCopyFlow examineCopyFlow);

    /**
     * api查询审批抄送流程列表
     *
     * @param examineCopyFlow 审批抄送流程
     * @return 审批抄送流程集合
     */
    public List<ExamineCopyFlow> selectApiExamineCopyFlowList(ExamineCopyFlow examineCopyFlow);

    /**
     * 新增审批抄送流程
     *
     * @param examineCopyFlow 审批抄送流程
     * @return 结果
     */
    public int insertExamineCopyFlow(ExamineCopyFlow examineCopyFlow);

    /**
     * 修改审批抄送流程
     *
     * @param examineCopyFlow 审批抄送流程
     * @return 结果
     */
    public int updateExamineCopyFlow(ExamineCopyFlow examineCopyFlow);

    /**
     * 删除审批抄送流程
     *
     * @param examineCopyFlowId 审批抄送流程ID
     * @return 结果
     */
    public int deleteExamineCopyFlowById(String examineCopyFlowId);

    /**
     * 批量删除审批抄送流程
     *
     * @param examineCopyFlowIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteExamineCopyFlowByIds(String[] examineCopyFlowIds);
}
