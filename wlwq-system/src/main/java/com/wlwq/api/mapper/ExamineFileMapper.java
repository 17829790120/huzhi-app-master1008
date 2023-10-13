package com.wlwq.api.mapper;

import java.util.List;

import com.wlwq.api.domain.ExamineFile;

/**
 * 审批文件Mapper接口
 *
 * @author gaoce
 * @date 2023-05-11
 */
public interface ExamineFileMapper {
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
     * 删除审批文件
     *
     * @param examineFileId 审批文件ID
     * @return 结果
     */
    public int deleteExamineFileById(String examineFileId);

    /**
     * 批量删除审批文件
     *
     * @param examineFileIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteExamineFileByIds(String[] examineFileIds);

    /**
     *  删除之前的文件
     * @param examineInitiateId
     * @return
     */
    public int deleteExamineBeforeFileByInitiateId(String examineInitiateId);
}
