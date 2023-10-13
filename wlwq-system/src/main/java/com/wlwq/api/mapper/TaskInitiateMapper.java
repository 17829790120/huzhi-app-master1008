package com.wlwq.api.mapper;

import java.util.List;

import com.wlwq.api.domain.TaskInitiate;

/**
 * 任务发起Mapper接口
 *
 * @author gaoce
 * @date 2023-05-29
 */
public interface TaskInitiateMapper {
    /**
     * 查询任务发起
     *
     * @param taskInitiateId 任务发起ID
     * @return 任务发起
     */
    public TaskInitiate selectTaskInitiateById(String taskInitiateId);

    /**
     * 查询任务发起列表
     *
     * @param taskInitiate 任务发起
     * @return 任务发起集合
     */
    public List<TaskInitiate> selectTaskInitiateList(TaskInitiate taskInitiate);

    /**
     * 新增任务发起
     *
     * @param taskInitiate 任务发起
     * @return 结果
     */
    public int insertTaskInitiate(TaskInitiate taskInitiate);

    /**
     * 修改任务发起
     *
     * @param taskInitiate 任务发起
     * @return 结果
     */
    public int updateTaskInitiate(TaskInitiate taskInitiate);

    /**
     * 删除任务发起
     *
     * @param taskInitiateId 任务发起ID
     * @return 结果
     */
    public int deleteTaskInitiateById(String taskInitiateId);

    /**
     * 批量删除任务发起
     *
     * @param taskInitiateIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteTaskInitiateByIds(String[] taskInitiateIds);

    /**
     * 待接收的任务列表
     * @param taskInitiate
     * @return
     */
    public List<TaskInitiate> selectApiTaskInitiateList(TaskInitiate taskInitiate);

    /**
     * 查询待接收详情
     * @param taskInitiate
     * @return
     */
    public TaskInitiate selectApiTaskInitiateDetail(TaskInitiate taskInitiate);

    /**
     * 已接收任务列表/已逾期任务列表/已完成任务列表
     * @param taskInitiate
     * @return
     */
    public List<TaskInitiate> selectApiTaskReceiveList(TaskInitiate taskInitiate);

    /**
     * 已接收任务数量/已逾期任务数量/已完成任务数量
     * @param taskInitiate
     * @return
     */
    public Integer selectApiTaskReceiveCount(TaskInitiate taskInitiate);

    /**
     * 待接收数量
     * @param taskInitiate
     * @return
     */
    public Integer selectApiTaskInitiateCount(TaskInitiate taskInitiate);
}
