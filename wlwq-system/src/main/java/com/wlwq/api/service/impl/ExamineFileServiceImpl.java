package com.wlwq.api.service.impl;

import java.util.List;

import cn.hutool.core.util.IdUtil;
import com.wlwq.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.api.mapper.ExamineFileMapper;
import com.wlwq.api.domain.ExamineFile;
import com.wlwq.api.service.IExamineFileService;
import com.wlwq.common.core.text.Convert;

/**
 * 审批文件Service业务层处理
 *
 * @author gaoce
 * @date 2023-05-11
 */
@Service
public class ExamineFileServiceImpl implements IExamineFileService {

    @Autowired
    private ExamineFileMapper examineFileMapper;

    /**
     * 查询审批文件
     *
     * @param examineFileId 审批文件ID
     * @return 审批文件
     */
    @Override
    public ExamineFile selectExamineFileById(String examineFileId) {
        return examineFileMapper.selectExamineFileById(examineFileId);
    }

    /**
     * 查询审批文件列表
     *
     * @param examineFile 审批文件
     * @return 审批文件
     */
    @Override
    public List<ExamineFile> selectExamineFileList(ExamineFile examineFile) {
        return examineFileMapper.selectExamineFileList(examineFile);
    }

    /**
     * 新增审批文件
     *
     * @param examineFile 审批文件
     * @return 结果
     */
    @Override
    public int insertExamineFile(ExamineFile examineFile) {
        examineFile.setExamineFileId(IdUtil.getSnowflake(1, 1).nextIdStr());
        examineFile.setCreateTime(DateUtils.getNowDate());
        return examineFileMapper.insertExamineFile(examineFile);
    }

    /**
     * 修改审批文件
     *
     * @param examineFile 审批文件
     * @return 结果
     */
    @Override
    public int updateExamineFile(ExamineFile examineFile) {
        examineFile.setUpdateTime(DateUtils.getNowDate());
        return examineFileMapper.updateExamineFile(examineFile);
    }

    /**
     * 删除审批文件对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteExamineFileByIds(String ids) {
        return examineFileMapper.deleteExamineFileByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除审批文件信息
     *
     * @param examineFileId 审批文件ID
     * @return 结果
     */
    @Override
    public int deleteExamineFileById(String examineFileId) {
        return examineFileMapper.deleteExamineFileById(examineFileId);
    }

    /**
     *  删除之前的文件
     * @param examineInitiateId
     * @return
     */
    @Override
    public int deleteExamineBeforeFileByInitiateId(String examineInitiateId){
        return examineFileMapper.deleteExamineBeforeFileByInitiateId(examineInitiateId);
    }
}
