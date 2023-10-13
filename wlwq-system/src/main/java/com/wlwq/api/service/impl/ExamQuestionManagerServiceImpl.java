package com.wlwq.api.service.impl;

import java.util.List;

import cn.hutool.core.util.IdUtil;
import com.wlwq.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.api.mapper.ExamQuestionManagerMapper;
import com.wlwq.api.domain.ExamQuestionManager;
import com.wlwq.api.service.IExamQuestionManagerService;
import com.wlwq.common.core.text.Convert;

/**
 * 考试记录试题Service业务层处理
 *
 * @author wwb
 * @date 2023-05-26
 */
@Service
public class ExamQuestionManagerServiceImpl implements IExamQuestionManagerService {

    @Autowired
    private ExamQuestionManagerMapper examQuestionManagerMapper;

    /**
     * 查询考试记录试题
     *
     * @param examQuestionManagerId 考试记录试题ID
     * @return 考试记录试题
     */
    @Override
    public ExamQuestionManager selectExamQuestionManagerById(String examQuestionManagerId) {
        return examQuestionManagerMapper.selectExamQuestionManagerById(examQuestionManagerId);
    }

    /**
     * 查询考试记录试题列表
     *
     * @param examQuestionManager 考试记录试题
     * @return 考试记录试题
     */
    @Override
    public List<ExamQuestionManager> selectExamQuestionManagerList(ExamQuestionManager examQuestionManager) {
        return examQuestionManagerMapper.selectExamQuestionManagerList(examQuestionManager);
    }

    /**
     * 新增考试记录试题
     *
     * @param examQuestionManager 考试记录试题
     * @return 结果
     */
    @Override
    public int insertExamQuestionManager(ExamQuestionManager examQuestionManager) {
        examQuestionManager.setExamQuestionManagerId(IdUtil.getSnowflake(1, 1).nextIdStr());
        examQuestionManager.setCreateTime(DateUtils.getNowDate());
        return examQuestionManagerMapper.insertExamQuestionManager(examQuestionManager);
    }

    /**
     * 修改考试记录试题
     *
     * @param examQuestionManager 考试记录试题
     * @return 结果
     */
    @Override
    public int updateExamQuestionManager(ExamQuestionManager examQuestionManager) {
        examQuestionManager.setUpdateTime(DateUtils.getNowDate());
        return examQuestionManagerMapper.updateExamQuestionManager(examQuestionManager);
    }

    /**
     * 删除考试记录试题对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteExamQuestionManagerByIds(String ids) {
        return examQuestionManagerMapper.deleteExamQuestionManagerByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除考试记录试题信息
     *
     * @param examQuestionManagerId 考试记录试题ID
     * @return 结果
     */
    @Override
    public int deleteExamQuestionManagerById(String examQuestionManagerId) {
        return examQuestionManagerMapper.deleteExamQuestionManagerById(examQuestionManagerId);
    }
}
