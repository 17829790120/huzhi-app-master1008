package com.wlwq.api.mapper;

import java.util.List;
import java.util.Map;

import com.wlwq.api.domain.TaskFlowAccount;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 用户任务审批流程Mapper接口
 * 
 * @author gaoce
 * @date 2023-05-30
 */
public interface TaskFlowAccountMapper {
    /**
     * 查询用户任务审批流程
     * 
     * @param taskFlowAccountId 用户任务审批流程ID
     * @return 用户任务审批流程
     */
    public TaskFlowAccount selectTaskFlowAccountById(String taskFlowAccountId);

    /**
     * 查询用户任务审批流程列表
     * 
     * @param taskFlowAccount 用户任务审批流程
     * @return 用户任务审批流程集合
     */
    public List<TaskFlowAccount> selectTaskFlowAccountList(TaskFlowAccount taskFlowAccount);

    /**
     * 新增用户任务审批流程
     * 
     * @param taskFlowAccount 用户任务审批流程
     * @return 结果
     */
    public int insertTaskFlowAccount(TaskFlowAccount taskFlowAccount);

    /**
     * 修改用户任务审批流程
     * 
     * @param taskFlowAccount 用户任务审批流程
     * @return 结果
     */
    public int updateTaskFlowAccount(TaskFlowAccount taskFlowAccount);

    /**
     * 删除用户任务审批流程
     * 
     * @param taskFlowAccountId 用户任务审批流程ID
     * @return 结果
     */
    public int deleteTaskFlowAccountById(String taskFlowAccountId);

    /**
     * 批量删除用户任务审批流程
     * 
     * @param taskFlowAccountIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteTaskFlowAccountByIds(String[] taskFlowAccountIds);

    /**
     *  只查询就近的一条
     * @param taskFlowAccount
     * @return
     */
    public TaskFlowAccount selectNearLimitTaskFlowAccount(TaskFlowAccount taskFlowAccount);

    /**
     * 更新数据
     * @return
     */
    public Integer updateTaskFlowAccountByReceiveId(TaskFlowAccount taskFlowAccount);

    /**
     * 删除相关信息
     * @param taskReceiveId 接收ID
     * @return
     */
    public Integer deleteTaskFlowAccountByReceiveId(@Param("taskReceiveId") String taskReceiveId,@Param("examineType") Integer examineType);

    /**
     * api查询相关列表
     * @param taskFlowAccount
     * @return
     */
    public List<Map<String,Object>> selectApiTaskFlowAccountList(TaskFlowAccount taskFlowAccount);

    /**
     * 查询审批详情
     * @param taskFlowAccount
     * @return
     */
    public Map<String,Object> selectApiTaskFlowAccountDetail(TaskFlowAccount taskFlowAccount);

    /**
     * 根据唯一标识查询审批人员信息
     * @param taskFlowAccount
     * @return
     */
    public List<TaskFlowAccount> selectNearTaskFlowAccountList(TaskFlowAccount taskFlowAccount);
}
