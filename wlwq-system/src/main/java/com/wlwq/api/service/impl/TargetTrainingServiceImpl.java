package com.wlwq.api.service.impl;

import java.util.List;

import cn.hutool.core.util.IdUtil;
import com.wlwq.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.api.mapper.TargetTrainingMapper;
import com.wlwq.api.domain.TargetTraining;
import com.wlwq.api.service.ITargetTrainingService;
import com.wlwq.common.core.text.Convert;

/**
 * 目标训练管理Service业务层处理
 *
 * @author wwb
 * @date 2023-06-01
 */
@Service
public class TargetTrainingServiceImpl implements ITargetTrainingService {

    @Autowired
    private TargetTrainingMapper targetTrainingMapper;

    /**
     * 查询目标训练管理
     *
     * @param targetTrainingId 目标训练管理ID
     * @return 目标训练管理
     */
    @Override
    public TargetTraining selectTargetTrainingById(String targetTrainingId) {
        return targetTrainingMapper.selectTargetTrainingById(targetTrainingId);
    }

    /**
     * 查询目标训练管理列表
     *
     * @param targetTraining 目标训练管理
     * @return 目标训练管理
     */
    @Override
    public List<TargetTraining> selectTargetTrainingList(TargetTraining targetTraining) {
        return targetTrainingMapper.selectTargetTrainingList(targetTraining);
    }

    /**
     * 新增目标训练管理
     *
     * @param targetTraining 目标训练管理
     * @return 结果
     */
    @Override
    public int insertTargetTraining(TargetTraining targetTraining) {
        targetTraining.setTargetTrainingId(IdUtil.getSnowflake(1, 1).nextIdStr());
        targetTraining.setCreateTime(DateUtils.getNowDate());
        return targetTrainingMapper.insertTargetTraining(targetTraining);
    }

    /**
     * 修改目标训练管理
     *
     * @param targetTraining 目标训练管理
     * @return 结果
     */
    @Override
    public int updateTargetTraining(TargetTraining targetTraining) {
        targetTraining.setUpdateTime(DateUtils.getNowDate());
        return targetTrainingMapper.updateTargetTraining(targetTraining);
    }

    /**
     * 删除目标训练管理对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTargetTrainingByIds(String ids) {
        return targetTrainingMapper.deleteTargetTrainingByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除目标训练管理信息
     *
     * @param targetTrainingId 目标训练管理ID
     * @return 结果
     */
    @Override
    public int deleteTargetTrainingById(String targetTrainingId) {
        return targetTrainingMapper.deleteTargetTrainingById(targetTrainingId);
    }
}
