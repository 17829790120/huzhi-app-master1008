package com.wlwq.api.service.impl;

import java.util.List;
import cn.hutool.core.util.IdUtil;
import com.wlwq.api.domain.ApiAccount;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.annotation.DataScope;
import com.wlwq.common.utils.DateUtils;
import com.wlwq.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.api.mapper.ExamRecordMapper;
import com.wlwq.api.domain.ExamRecord;
import com.wlwq.api.service.IExamRecordService;
import com.wlwq.common.core.text.Convert;

/**
 * 考试记录列表Service业务层处理
 *
 * @author wwb
 * @date 2023-04-23
 */
@Service
public class ExamRecordServiceImpl implements IExamRecordService {

    @Autowired
    private ExamRecordMapper examRecordMapper;

    /**
     * 查询考试记录列表
     *
     * @param examRecordId 考试记录列表ID
     * @return 考试记录列表
     */
    @Override
    public ExamRecord selectExamRecordById(String examRecordId) {
        return examRecordMapper.selectExamRecordById(examRecordId);
    }

    /**
     * 查询考试记录列表列表
     *
     * @param examRecord 考试记录列表
     * @return 考试记录列表
     */
    @Override
    public List<ExamRecord> selectExamRecordList(ExamRecord examRecord) {
        return examRecordMapper.selectExamRecordList(examRecord);
    }

    /**
     * 新增考试记录列表
     *
     * @param examRecord 考试记录列表
     * @return 结果
     */
    @Override
    public int insertExamRecord(ExamRecord examRecord) {
        if(StringUtils.isEmpty(examRecord.getExamRecordId())){
            examRecord.setExamRecordId(IdUtil.getSnowflake(1, 1).nextIdStr());
        }
        examRecord.setCreateTime(DateUtils.getNowDate());
        return examRecordMapper.insertExamRecord(examRecord);
    }

    /**
     * 修改考试记录列表
     *
     * @param examRecord 考试记录列表
     * @return 结果
     */
    @Override
    public int updateExamRecord(ExamRecord examRecord) {
        examRecord.setUpdateTime(DateUtils.getNowDate());
        return examRecordMapper.updateExamRecord(examRecord);
    }

    /**
     * 删除考试记录列表对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteExamRecordByIds(String ids) {
        return examRecordMapper.deleteExamRecordByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除考试记录列表信息
     *
     * @param examRecordId 考试记录列表ID
     * @return 结果
     */
    @Override
    public int deleteExamRecordById(String examRecordId) {
        return examRecordMapper.deleteExamRecordById(examRecordId);
    }
    /**
     * 考试打分
     *
     * @param examRecord 考试答题记录
     * @param score 得分
     * @return 结果
     */
    @Override
    public AjaxResult examScoring(ExamRecord examRecord, Double score, ApiAccount account) {
        if(examRecord == null){
            return AjaxResult.error("没有考试记录。");
        }
        examRecord.setScore(score);
        examRecord.setScoreStatus(1);
        int count = examRecordMapper.updateExamRecord(examRecord);
        if(count < 0){
            return AjaxResult.error("打分失败。");
        }
        return AjaxResult.success("打分成功。");
    }
}
