package com.wlwq.api.service.impl;

import java.util.List;
import java.util.Map;

import cn.hutool.core.util.IdUtil;
import com.wlwq.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.api.mapper.TaskFlowAccountMapper;
import com.wlwq.api.domain.TaskFlowAccount;
import com.wlwq.api.service.ITaskFlowAccountService;
import com.wlwq.common.core.text.Convert;

/**
 * 用户任务审批流程Service业务层处理
 * 
 * @author gaoce
 * @date 2023-05-30
 */
@Service
public class TaskFlowAccountServiceImpl implements ITaskFlowAccountService {

    @Autowired
    private TaskFlowAccountMapper taskFlowAccountMapper;

    /**
     * 查询用户任务审批流程
     * 
     * @param taskFlowAccountId 用户任务审批流程ID
     * @return 用户任务审批流程
     */
    @Override
    public TaskFlowAccount selectTaskFlowAccountById(String taskFlowAccountId) {
        return taskFlowAccountMapper.selectTaskFlowAccountById(taskFlowAccountId);
    }

    /**
     * 查询用户任务审批流程列表
     * 
     * @param taskFlowAccount 用户任务审批流程
     * @return 用户任务审批流程
     */
    @Override
    public List<TaskFlowAccount> selectTaskFlowAccountList(TaskFlowAccount taskFlowAccount) {
        return taskFlowAccountMapper.selectTaskFlowAccountList(taskFlowAccount);
    }

    /**
     * 新增用户任务审批流程
     * 
     * @param taskFlowAccount 用户任务审批流程
     * @return 结果
     */
    @Override
    public int insertTaskFlowAccount(TaskFlowAccount taskFlowAccount) {
        taskFlowAccount.setTaskFlowAccountId(IdUtil.getSnowflake(1,1).nextIdStr());
        taskFlowAccount.setCreateTime(DateUtils.getNowDate());
        return taskFlowAccountMapper.insertTaskFlowAccount(taskFlowAccount);
    }

    /**
     * 修改用户任务审批流程
     * 
     * @param taskFlowAccount 用户任务审批流程
     * @return 结果
     */
    @Override
    public int updateTaskFlowAccount(TaskFlowAccount taskFlowAccount) {
        // 更新已读状态不更新时间
        if(taskFlowAccount.getReadTag() == null){
            taskFlowAccount.setUpdateTime(DateUtils.getNowDate());
        }
        return taskFlowAccountMapper.updateTaskFlowAccount(taskFlowAccount);
    }

    /**
     * 删除用户任务审批流程对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTaskFlowAccountByIds(String ids) {
        return taskFlowAccountMapper.deleteTaskFlowAccountByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除用户任务审批流程信息
     * 
     * @param taskFlowAccountId 用户任务审批流程ID
     * @return 结果
     */
    @Override
    public int deleteTaskFlowAccountById(String taskFlowAccountId) {
        return taskFlowAccountMapper.deleteTaskFlowAccountById(taskFlowAccountId);
    }

    /**
     *  只查询就近的一条
     * @param taskFlowAccount
     * @return
     */
    @Override
    public TaskFlowAccount selectNearLimitTaskFlowAccount(TaskFlowAccount taskFlowAccount){
        return taskFlowAccountMapper.selectNearLimitTaskFlowAccount(taskFlowAccount);
    }

    /**
     * 更新数据
     * @return
     */
    @Override
    public Integer updateTaskFlowAccountByReceiveId(TaskFlowAccount taskFlowAccount){
        return taskFlowAccountMapper.updateTaskFlowAccountByReceiveId(taskFlowAccount);
    }

    /**
     * 删除相关信息
     * @param taskReceiveId 接收ID
     * @param examineType 1:逾期审批 2:完成审批
     * @return
     */
    @Override
    public Integer deleteTaskFlowAccountByReceiveId(String taskReceiveId,Integer examineType){
        return taskFlowAccountMapper.deleteTaskFlowAccountByReceiveId(taskReceiveId,examineType);
    }

    /**
     * api查询相关列表
     * @param taskFlowAccount
     * @return
     */
    @Override
    public List<Map<String,Object>> selectApiTaskFlowAccountList(TaskFlowAccount taskFlowAccount){
        return taskFlowAccountMapper.selectApiTaskFlowAccountList(taskFlowAccount);
    }

    /**
     * 查询审批详情
     * @param taskFlowAccount
     * @return
     */
    @Override
    public Map<String,Object> selectApiTaskFlowAccountDetail(TaskFlowAccount taskFlowAccount){
        return taskFlowAccountMapper.selectApiTaskFlowAccountDetail(taskFlowAccount);
    }

    /**
     * 根据唯一标识查询审批人员信息
     * @param taskFlowAccount
     * @return
     */
    @Override
    public List<TaskFlowAccount> selectNearTaskFlowAccountList(TaskFlowAccount taskFlowAccount){
        return taskFlowAccountMapper.selectNearTaskFlowAccountList(taskFlowAccount);
    }
}
