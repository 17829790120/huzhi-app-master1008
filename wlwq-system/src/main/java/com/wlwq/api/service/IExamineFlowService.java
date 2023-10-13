package com.wlwq.api.service;

import java.util.List;

import com.wlwq.api.domain.ExamineFlow;
import com.wlwq.api.domain.TaskInitiate;
import com.wlwq.api.resultVO.examine.ExamineFlowAccountResultVO;
import com.wlwq.api.resultVO.examine.ExamineFlowResultVO;

/**
 * 审批流程Service接口
 *
 * @author gaoce
 * @date 2023-05-10
 */
public interface IExamineFlowService {
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
     * 批量删除审批流程
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteExamineFlowByIds(String ids);

    /**
     * 删除审批流程信息
     *
     * @param examineFlowId 审批流程ID
     * @return 结果
     */
    public int deleteExamineFlowById(String examineFlowId);


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
     * 查询员工第一次发起的审批管理人员列表
     * @param examineFlow
     * @return
     */
    public List<ExamineFlowResultVO> selectStaffInitiateExaminePeopleList(ExamineFlow examineFlow);



    /**
     * 根据模块和类型查询统一审批等级查看的人数及审批顺序
     *
     * @param examineFlow 审批流程
     * @return 结果
     */
    public List<ExamineFlowResultVO> selectExamineListGroupBySequence(ExamineFlow examineFlow);

    /**
     * 查询审批管理人员列表（除查询员工第一次发起外）
     * @param examineFlow
     * @param initiateId 审批发起ID
     * @param examineTag  同一批审核唯一标识
     * @return
     */
    public List<ExamineFlowResultVO> selectStaffInitiateExaminePeopleList(ExamineFlow examineFlow,String initiateId,String examineTag);


    /**
     * 查询任务审批管理人员列表
     * @param examineFlow
     * @param taskReceiveId 任务接收ID
     * @param status        1:延期审批 2:完成审批
     * @return
     */
    public List<ExamineFlowResultVO> selectTaskExaminePeopleList(ExamineFlow examineFlow, TaskInitiate taskInitiate,String taskReceiveId, Integer status);

    /**
     * 查询审批管理人员列表
     * @param examineFlow
     * @param accountScoreId 用户积分ID
     * @return
     */
    public List<ExamineFlowResultVO> selectAccountScoreExaminePeopleList(ExamineFlow examineFlow,String accountScoreId);
}
