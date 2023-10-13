package com.wlwq.api.mapper;

import java.util.List;

import com.wlwq.api.domain.TaskReceive;

/**
 * 任务接收Mapper接口
 *
 * @author gaoce
 * @date 2023-05-29
 */
public interface TaskReceiveMapper {
    /**
     * 查询任务接收
     *
     * @param taskReceiveId 任务接收ID
     * @return 任务接收
     */
    public TaskReceive selectTaskReceiveById(String taskReceiveId);

    /**
     * 查询任务接收列表
     *
     * @param taskReceive 任务接收
     * @return 任务接收集合
     */
    public List<TaskReceive> selectTaskReceiveList(TaskReceive taskReceive);

    /**
     * 新增任务接收
     *
     * @param taskReceive 任务接收
     * @return 结果
     */
    public int insertTaskReceive(TaskReceive taskReceive);

    /**
     * 修改任务接收
     *
     * @param taskReceive 任务接收
     * @return 结果
     */
    public int updateTaskReceive(TaskReceive taskReceive);

    /**
     * 删除任务接收
     *
     * @param taskReceiveId 任务接收ID
     * @return 结果
     */
    public int deleteTaskReceiveById(String taskReceiveId);

    /**
     * 批量删除任务接收
     *
     * @param taskReceiveIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteTaskReceiveByIds(String[] taskReceiveIds);
}
