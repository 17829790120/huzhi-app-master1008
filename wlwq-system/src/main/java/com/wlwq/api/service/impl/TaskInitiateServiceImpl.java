package com.wlwq.api.service.impl;

import java.util.List;
import cn.hutool.core.util.IdUtil;
import com.wlwq.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.api.mapper.TaskInitiateMapper;
import com.wlwq.api.domain.TaskInitiate;
import com.wlwq.api.service.ITaskInitiateService;
import com.wlwq.common.core.text.Convert;

/**
 * 任务发起Service业务层处理
 * 
 * @author gaoce
 * @date 2023-05-29
 */
@Service
public class TaskInitiateServiceImpl implements ITaskInitiateService {

    @Autowired
    private TaskInitiateMapper taskInitiateMapper;

    /**
     * 查询任务发起
     * 
     * @param taskInitiateId 任务发起ID
     * @return 任务发起
     */
    @Override
    public TaskInitiate selectTaskInitiateById(String taskInitiateId) {
        return taskInitiateMapper.selectTaskInitiateById(taskInitiateId);
    }

    /**
     * 查询任务发起列表
     * 
     * @param taskInitiate 任务发起
     * @return 任务发起
     */
    @Override
    public List<TaskInitiate> selectTaskInitiateList(TaskInitiate taskInitiate) {
        return taskInitiateMapper.selectTaskInitiateList(taskInitiate);
    }

    /**
     * 新增任务发起
     * 
     * @param taskInitiate 任务发起
     * @return 结果
     */
    @Override
    public int insertTaskInitiate(TaskInitiate taskInitiate) {
        taskInitiate.setTaskInitiateId(IdUtil.getSnowflake(1,1).nextIdStr());
        taskInitiate.setCreateTime(DateUtils.getNowDate());
        return taskInitiateMapper.insertTaskInitiate(taskInitiate);
    }

    /**
     * 修改任务发起
     * 
     * @param taskInitiate 任务发起
     * @return 结果
     */
    @Override
    public int updateTaskInitiate(TaskInitiate taskInitiate) {
        taskInitiate.setUpdateTime(DateUtils.getNowDate());
        return taskInitiateMapper.updateTaskInitiate(taskInitiate);
    }

    /**
     * 删除任务发起对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTaskInitiateByIds(String ids) {
        return taskInitiateMapper.deleteTaskInitiateByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除任务发起信息
     * 
     * @param taskInitiateId 任务发起ID
     * @return 结果
     */
    @Override
    public int deleteTaskInitiateById(String taskInitiateId) {
        return taskInitiateMapper.deleteTaskInitiateById(taskInitiateId);
    }

    /**
     * 待接收的任务列表
     * @param taskInitiate
     * @return
     */
    @Override
    public List<TaskInitiate> selectApiTaskInitiateList(TaskInitiate taskInitiate){
        return taskInitiateMapper.selectApiTaskInitiateList(taskInitiate);
    }

    /**
     * 查询待接收详情
     * @param taskInitiate
     * @return
     */
    @Override
    public TaskInitiate selectApiTaskInitiateDetail(TaskInitiate taskInitiate){
        return taskInitiateMapper.selectApiTaskInitiateDetail(taskInitiate);
    }


    /**
     * 已接收任务列表
     * @param taskInitiate
     * @return
     */
    @Override
    public List<TaskInitiate> selectApiTaskReceiveList(TaskInitiate taskInitiate){
        return taskInitiateMapper.selectApiTaskReceiveList(taskInitiate);
    }

    /**
     * 已接收任务数量/已逾期任务数量/已完成任务数量
     * @param taskInitiate
     * @return
     */
    @Override
    public Integer selectApiTaskReceiveCount(TaskInitiate taskInitiate){
        return taskInitiateMapper.selectApiTaskReceiveCount(taskInitiate);
    }

    /**
     * 待接收数量
     * @param taskInitiate
     * @return
     */
    @Override
    public Integer selectApiTaskInitiateCount(TaskInitiate taskInitiate){
        return taskInitiateMapper.selectApiTaskInitiateCount(taskInitiate);
    }
}
