package com.wlwq.api.service;

import java.util.List;

import com.wlwq.api.domain.ExamineFile;

/**
 * 审批文件Service接口
 *
 * @author gaoce
 * @date 2023-05-11
 */
public interface IExamineFileService {
    /**
     * 查询审批文件
     *
     * @param examineFileId 审批文件ID
     * @return 审批文件
     */
    public ExamineFile selectExamineFileById(String examineFileId);

    /**
     * 查询审批文件列表
     *
     * @param examineFile 审批文件
     * @return 审批文件集合
     */
    public List<ExamineFile> selectExamineFileList(ExamineFile examineFile);

    /**
     * 新增审批文件
     *
     * @param examineFile 审批文件
     * @return 结果
     */
    public int insertExamineFile(ExamineFile examineFile);

    /**
     * 修改审批文件
     *
     * @param examineFile 审批文件
     * @return 结果
     */
    public int updateExamineFile(ExamineFile examineFile);

    /**
     * 批量删除审批文件
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteExamineFileByIds(String ids);

    /**
     * 删除审批文件信息
     *
     * @param examineFileId 审批文件ID
     * @return 结果
     */
    public int deleteExamineFileById(String examineFileId);

    /**
     *  删除之前的文件
     * @param examineInitiateId
     * @return
     */
    public int deleteExamineBeforeFileByInitiateId(String examineInitiateId);
}
