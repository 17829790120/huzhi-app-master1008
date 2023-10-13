package com.wlwq.api.mapper;

import java.util.List;
import java.util.Map;

import com.wlwq.api.domain.ExamineFlowCopyAccount;
import com.wlwq.api.domain.ExamineInitiate;
import org.apache.ibatis.annotations.Param;

/**
 * 用户审批的抄送流程Mapper接口
 *
 * @author gaoce
 * @date 2023-05-11
 */
public interface ExamineFlowCopyAccountMapper {
    /**
     * 查询用户审批的抄送流程
     *
     * @param examineFlowCopyAccountId 用户审批的抄送流程ID
     * @return 用户审批的抄送流程
     */
    public ExamineFlowCopyAccount selectExamineFlowCopyAccountById(String examineFlowCopyAccountId);

    /**
     * 查询用户审批的抄送流程列表
     *
     * @param examineFlowCopyAccount 用户审批的抄送流程
     * @return 用户审批的抄送流程集合
     */
    public List<ExamineFlowCopyAccount> selectExamineFlowCopyAccountList(ExamineFlowCopyAccount examineFlowCopyAccount);

    /**
     * 新增用户审批的抄送流程
     *
     * @param examineFlowCopyAccount 用户审批的抄送流程
     * @return 结果
     */
    public int insertExamineFlowCopyAccount(ExamineFlowCopyAccount examineFlowCopyAccount);

    /**
     * 修改用户审批的抄送流程
     *
     * @param examineFlowCopyAccount 用户审批的抄送流程
     * @return 结果
     */
    public int updateExamineFlowCopyAccount(ExamineFlowCopyAccount examineFlowCopyAccount);

    /**
     * 删除用户审批的抄送流程
     *
     * @param examineFlowCopyAccountId 用户审批的抄送流程ID
     * @return 结果
     */
    public int deleteExamineFlowCopyAccountById(String examineFlowCopyAccountId);

    /**
     * 批量删除用户审批的抄送流程
     *
     * @param examineFlowCopyAccountIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteExamineFlowCopyAccountByIds(String[] examineFlowCopyAccountIds);

    /**
     * 抄送我的审批列表
     * @param examineInitiate
     * @return
     */
    public List<Map<String,Object>> selectMyCopyExamineList(ExamineInitiate examineInitiate);

    /**
     * 抄送我的审批详情
     * @param accountId 账号ID
     * @param flowCopyAccountId 抄送ID
     * @return
     */
    public Map<String,Object> selectCopyMyExamineDetail(@Param("accountId") String accountId, @Param("flowCopyAccountId") String flowCopyAccountId);

    /**
     * 更新抄送某个用户的已读状态
     * @param copyAccountId 抄送者ID
     * @return
     */
    public Integer updateReadStatusByCopyAccountById(String copyAccountId);

    /**
     * 查询数量
     * @param examineFlowCopyAccount
     * @return
     */
    public Integer selectExamineFlowCopyAccountCount(ExamineFlowCopyAccount examineFlowCopyAccount);
}
