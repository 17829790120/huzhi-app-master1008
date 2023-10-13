package com.wlwq.api.service.impl;

import java.util.List;

import cn.hutool.core.util.IdUtil;
import com.wlwq.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.api.mapper.TaskFileMapper;
import com.wlwq.api.domain.TaskFile;
import com.wlwq.api.service.ITaskFileService;
import com.wlwq.common.core.text.Convert;

/**
 * 任务附件Service业务层处理
 *
 * @author gaoce
 * @date 2023-05-29
 */
@Service
public class TaskFileServiceImpl implements ITaskFileService {

    @Autowired
    private TaskFileMapper taskFileMapper;

    /**
     * 查询任务附件
     *
     * @param taskFileId 任务附件ID
     * @return 任务附件
     */
    @Override
    public TaskFile selectTaskFileById(String taskFileId) {
        return taskFileMapper.selectTaskFileById(taskFileId);
    }

    /**
     * 查询任务附件列表
     *
     * @param taskFile 任务附件
     * @return 任务附件
     */
    @Override
    public List<TaskFile> selectTaskFileList(TaskFile taskFile) {
        return taskFileMapper.selectTaskFileList(taskFile);
    }

    /**
     * 新增任务附件
     *
     * @param taskFile 任务附件
     * @return 结果
     */
    @Override
    public int insertTaskFile(TaskFile taskFile) {
        taskFile.setTaskFileId(IdUtil.getSnowflake(1, 1).nextIdStr());
        taskFile.setCreateTime(DateUtils.getNowDate());
        return taskFileMapper.insertTaskFile(taskFile);
    }

    /**
     * 修改任务附件
     *
     * @param taskFile 任务附件
     * @return 结果
     */
    @Override
    public int updateTaskFile(TaskFile taskFile) {
        taskFile.setUpdateTime(DateUtils.getNowDate());
        return taskFileMapper.updateTaskFile(taskFile);
    }

    /**
     * 删除任务附件对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTaskFileByIds(String ids) {
        return taskFileMapper.deleteTaskFileByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除任务附件信息
     *
     * @param taskFileId 任务附件ID
     * @return 结果
     */
    @Override
    public int deleteTaskFileById(String taskFileId) {
        return taskFileMapper.deleteTaskFileById(taskFileId);
    }
}
