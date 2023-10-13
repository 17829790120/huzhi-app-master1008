package com.wlwq.api.mapper;

import java.util.List;

import com.wlwq.api.domain.ExamineFlow;
import com.wlwq.api.resultVO.examine.ExamineFlowAccountResultVO;
import com.wlwq.api.resultVO.examine.ExamineFlowResultVO;

/**
 * 审批流程Mapper接口
 *
 * @author gaoce
 * @date 2023-05-10
 */
public interface ExamineFlowMapper {
    /**
     * 查询审批流程
     *
     * @param examineFlowId 审批流程ID
     * @return 审批流程
     */
    public ExamineFlow selectExamineFlowById(String examineFlowId);

    /**
     * 查询审批流程列表
     *
     * @param examineFlow 审批流程
     * @return 审批流程集合
     */
    public List<ExamineFlow> selectExamineFlowList(ExamineFlow examineFlow);

    /**
     * 新增审批流程
     *
     * @param examineFlow 审批流程
     * @return 结果
     */
    public int insertExamineFlow(ExamineFlow examineFlow);

    /**
     * 修改审批流程
     *
     * @param examineFlow 审批流程
     * @return 结果
     */
    public int updateExamineFlow(ExamineFlow examineFlow);

    /**
     * 删除审批流程
     *
     * @param examineFlowId 审批流程ID
     * @return 结果
     */
    public int deleteExamineFlowById(String examineFlowId);

    /**
     * 批量删除审批流程
     *
     * @param examineFlowIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteExamineFlowByIds(String[] examineFlowIds);


    /**
     * 查询某一条数据是否存在
     *
     * @param examineFlow 审批流程
     * @return 结果
     */
    public ExamineFlow selectExamineFlow(ExamineFlow examineFlow);

    /**
     * 根据模块和类型查询统一审批等级查看审批顺序最小值
     *
     * @param examineFlow 审批流程
     * @return 结果
     */
    public ExamineFlowResultVO selectMinExamineGroupBySequence(ExamineFlow examineFlow);

    /**
     * app查询发起的审批列表
     *
     * @param examineFlow 审批流程
     * @return 结果
     */
    public List<ExamineFlowAccountResultVO> selectAppExamineFlowList(ExamineFlow examineFlow);

    /**
     * 根据模块和类型和等级查询下一个等级
     *
     * @param examineFlow 审批流程
     * @return 结果
     */
    public ExamineFlowResultVO selectNextExamineSequence(ExamineFlow examineFlow);


    /**
     * 根据模块和类型查询统一审批等级查看的人数及审批顺序
     *
     * @param examineFlow 审批流程
     * @return 结果
     */
    public List<ExamineFlowResultVO> selectExamineListGroupBySequence(ExamineFlow examineFlow);
}
