package com.wlwq.api.service.impl;

import java.util.List;

import cn.hutool.core.util.IdUtil;
import com.wlwq.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.api.mapper.SysDeptScoreMapper;
import com.wlwq.api.domain.SysDeptScore;
import com.wlwq.api.service.ISysDeptScoreService;
import com.wlwq.common.core.text.Convert;

/**
 * 公司积分设置Service业务层处理
 *
 * @author gaoce
 * @date 2023-06-06
 */
@Service
public class SysDeptScoreServiceImpl implements ISysDeptScoreService {

    @Autowired
    private SysDeptScoreMapper sysDeptScoreMapper;

    /**
     * 查询公司积分设置
     *
     * @param sysDeptScoreId 公司积分设置ID
     * @return 公司积分设置
     */
    @Override
    public SysDeptScore selectSysDeptScoreById(String sysDeptScoreId) {
        return sysDeptScoreMapper.selectSysDeptScoreById(sysDeptScoreId);
    }

    /**
     * 查询公司积分设置列表
     *
     * @param sysDeptScore 公司积分设置
     * @return 公司积分设置
     */
    @Override
    public List<SysDeptScore> selectSysDeptScoreList(SysDeptScore sysDeptScore) {
        return sysDeptScoreMapper.selectSysDeptScoreList(sysDeptScore);
    }

    /**
     * 新增公司积分设置
     *
     * @param sysDeptScore 公司积分设置
     * @return 结果
     */
    @Override
    public int insertSysDeptScore(SysDeptScore sysDeptScore) {
        sysDeptScore.setSysDeptScoreId(IdUtil.getSnowflake(1, 1).nextIdStr());
        sysDeptScore.setCreateTime(DateUtils.getNowDate());
        return sysDeptScoreMapper.insertSysDeptScore(sysDeptScore);
    }

    /**
     * 修改公司积分设置
     *
     * @param sysDeptScore 公司积分设置
     * @return 结果
     */
    @Override
    public int updateSysDeptScore(SysDeptScore sysDeptScore) {
        sysDeptScore.setUpdateTime(DateUtils.getNowDate());
        return sysDeptScoreMapper.updateSysDeptScore(sysDeptScore);
    }

    /**
     * 删除公司积分设置对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysDeptScoreByIds(String ids) {
        return sysDeptScoreMapper.deleteSysDeptScoreByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除公司积分设置信息
     *
     * @param sysDeptScoreId 公司积分设置ID
     * @return 结果
     */
    @Override
    public int deleteSysDeptScoreById(String sysDeptScoreId) {
        return sysDeptScoreMapper.deleteSysDeptScoreById(sysDeptScoreId);
    }

    /**
     * 只查询最新的一条
     * @param sysDeptScore
     * @return
     */
    @Override
    public SysDeptScore selectSysDeptScore(SysDeptScore sysDeptScore){
        return sysDeptScoreMapper.selectSysDeptScore(sysDeptScore);
    }
}
