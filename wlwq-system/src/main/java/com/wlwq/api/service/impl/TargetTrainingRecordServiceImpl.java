package com.wlwq.api.service.impl;

import java.util.List;
import cn.hutool.core.util.IdUtil;
import com.wlwq.common.utils.DateUtils;
import com.wlwq.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.api.mapper.TargetTrainingRecordMapper;
import com.wlwq.api.domain.TargetTrainingRecord;
import com.wlwq.api.service.ITargetTrainingRecordService;
import com.wlwq.common.core.text.Convert;

/**
 * 目标训练记录Service业务层处理
 * 
 * @author wwb
 * @date 2023-06-05
 */
@Service
public class TargetTrainingRecordServiceImpl implements ITargetTrainingRecordService {

    @Autowired
    private TargetTrainingRecordMapper targetTrainingRecordMapper;

    /**
     * 查询目标训练记录
     * 
     * @param targetTrainingRecordId 目标训练记录ID
     * @return 目标训练记录
     */
    @Override
    public TargetTrainingRecord selectTargetTrainingRecordById(String targetTrainingRecordId) {
        return targetTrainingRecordMapper.selectTargetTrainingRecordById(targetTrainingRecordId);
    }

    /**
     * 查询目标训练记录列表
     * 
     * @param targetTrainingRecord 目标训练记录
     * @return 目标训练记录
     */
    @Override
    public List<TargetTrainingRecord> selectTargetTrainingRecordList(TargetTrainingRecord targetTrainingRecord) {
        return targetTrainingRecordMapper.selectTargetTrainingRecordList(targetTrainingRecord);
    }

    /**
     * 新增目标训练记录
     * 
     * @param targetTrainingRecord 目标训练记录
     * @return 结果
     */
    @Override
    public int insertTargetTrainingRecord(TargetTrainingRecord targetTrainingRecord) {
        if(StringUtils.isEmpty(targetTrainingRecord.getTargetTrainingRecordId())){
            targetTrainingRecord.setTargetTrainingRecordId(IdUtil.getSnowflake(1,1).nextIdStr());
        }
        targetTrainingRecord.setCreateTime(DateUtils.getNowDate());
        return targetTrainingRecordMapper.insertTargetTrainingRecord(targetTrainingRecord);
    }

    /**
     * 修改目标训练记录
     * 
     * @param targetTrainingRecord 目标训练记录
     * @return 结果
     */
    @Override
    public int updateTargetTrainingRecord(TargetTrainingRecord targetTrainingRecord) {
        targetTrainingRecord.setUpdateTime(DateUtils.getNowDate());
        return targetTrainingRecordMapper.updateTargetTrainingRecord(targetTrainingRecord);
    }

    /**
     * 删除目标训练记录对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTargetTrainingRecordByIds(String ids) {
        return targetTrainingRecordMapper.deleteTargetTrainingRecordByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除目标训练记录信息
     * 
     * @param targetTrainingRecordId 目标训练记录ID
     * @return 结果
     */
    @Override
    public int deleteTargetTrainingRecordById(String targetTrainingRecordId) {
        return targetTrainingRecordMapper.deleteTargetTrainingRecordById(targetTrainingRecordId);
    }
}
