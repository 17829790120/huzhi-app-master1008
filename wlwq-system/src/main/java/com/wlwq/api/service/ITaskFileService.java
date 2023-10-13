package com.wlwq.api.service;

import java.util.List;
import com.wlwq.api.domain.TaskFile;

/**
 * 任务附件Service接口
 * 
 * @author gaoce
 * @date 2023-05-29
 */
public interface ITaskFileService {
    /**
     * 查询任务附件
     * 
     * @param taskFileId 任务附件ID
     * @return 任务附件
     */
    public TaskFile selectTaskFileById(String taskFileId);

    /**
     * 查询任务附件列表
     * 
     * @param taskFile 任务附件
     * @return 任务附件集合
     */
    public List<TaskFile> selectTaskFileList(TaskFile taskFile);

    /**
     * 新增任务附件
     * 
     * @param taskFile 任务附件
     * @return 结果
     */
    public int insertTaskFile(TaskFile taskFile);

    /**
     * 修改任务附件
     * 
     * @param taskFile 任务附件
     * @return 结果
     */
    public int updateTaskFile(TaskFile taskFile);

    /**
     * 批量删除任务附件
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTaskFileByIds(String ids);

    /**
     * 删除任务附件信息
     * 
     * @param taskFileId 任务附件ID
     * @return 结果
     */
    public int deleteTaskFileById(String taskFileId);
}
