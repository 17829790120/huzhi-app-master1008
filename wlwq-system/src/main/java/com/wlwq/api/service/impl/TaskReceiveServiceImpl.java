package com.wlwq.api.service.impl;

import java.util.List;
import cn.hutool.core.util.IdUtil;
import com.wlwq.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.api.mapper.TaskReceiveMapper;
import com.wlwq.api.domain.TaskReceive;
import com.wlwq.api.service.ITaskReceiveService;
import com.wlwq.common.core.text.Convert;

/**
 * 任务接收Service业务层处理
 * 
 * @author gaoce
 * @date 2023-05-29
 */
@Service
public class TaskReceiveServiceImpl implements ITaskReceiveService {

    @Autowired
    private TaskReceiveMapper taskReceiveMapper;

    /**
     * 查询任务接收
     * 
     * @param taskReceiveId 任务接收ID
     * @return 任务接收
     */
    @Override
    public TaskReceive selectTaskReceiveById(String taskReceiveId) {
        return taskReceiveMapper.selectTaskReceiveById(taskReceiveId);
    }

    /**
     * 查询任务接收列表
     * 
     * @param taskReceive 任务接收
     * @return 任务接收
     */
    @Override
    public List<TaskReceive> selectTaskReceiveList(TaskReceive taskReceive) {
        return taskReceiveMapper.selectTaskReceiveList(taskReceive);
    }

    /**
     * 新增任务接收
     * 
     * @param taskReceive 任务接收
     * @return 结果
     */
    @Override
    public int insertTaskReceive(TaskReceive taskReceive) {
        taskReceive.setTaskReceiveId(IdUtil.getSnowflake(1,1).nextIdStr());
        taskReceive.setCreateTime(DateUtils.getNowDate());
        return taskReceiveMapper.insertTaskReceive(taskReceive);
    }

    /**
     * 修改任务接收
     * 
     * @param taskReceive 任务接收
     * @return 结果
     */
    @Override
    public int updateTaskReceive(TaskReceive taskReceive) {
        taskReceive.setUpdateTime(DateUtils.getNowDate());
        return taskReceiveMapper.updateTaskReceive(taskReceive);
    }

    /**
     * 删除任务接收对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTaskReceiveByIds(String ids) {
        return taskReceiveMapper.deleteTaskReceiveByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除任务接收信息
     * 
     * @param taskReceiveId 任务接收ID
     * @return 结果
     */
    @Override
    public int deleteTaskReceiveById(String taskReceiveId) {
        return taskReceiveMapper.deleteTaskReceiveById(taskReceiveId);
    }
}
