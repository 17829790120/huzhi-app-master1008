package com.wlwq.api.service.impl;

import java.util.List;
import java.util.Map;

import cn.hutool.core.util.IdUtil;
import com.wlwq.common.utils.DateUtils;
import com.wlwq.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.api.mapper.WinningTrainingRecordMapper;
import com.wlwq.api.domain.WinningTrainingRecord;
import com.wlwq.api.service.IWinningTrainingRecordService;
import com.wlwq.common.core.text.Convert;

/**
 * 人与人pkService业务层处理
 *
 * @author wwb
 * @date 2023-06-07
 */
@Service
public class WinningTrainingRecordServiceImpl implements IWinningTrainingRecordService {

    @Autowired
    private WinningTrainingRecordMapper winningTrainingRecordMapper;

    /**
     * 查询人与人pk
     *
     * @param winningTrainingRecordId 人与人pkID
     * @return 人与人pk
     */
    @Override
    public WinningTrainingRecord selectWinningTrainingRecordById(String winningTrainingRecordId) {
        return winningTrainingRecordMapper.selectWinningTrainingRecordById(winningTrainingRecordId);
    }

    /**
     * 查询人与人pk列表
     *
     * @param winningTrainingRecord 人与人pk
     * @return 人与人pk
     */
    @Override
    public List<WinningTrainingRecord> selectWinningTrainingRecordList(WinningTrainingRecord winningTrainingRecord) {
        return winningTrainingRecordMapper.selectWinningTrainingRecordList(winningTrainingRecord);
    }

    /**
     * 新增人与人pk
     *
     * @param winningTrainingRecord 人与人pk
     * @return 结果
     */
    @Override
    public int insertWinningTrainingRecord(WinningTrainingRecord winningTrainingRecord) {
        if(StringUtils.isEmpty(winningTrainingRecord.getWinningTrainingRecordId())){
            winningTrainingRecord.setWinningTrainingRecordId(IdUtil.getSnowflake(1, 1).nextIdStr());
        }
        winningTrainingRecord.setCreateTime(DateUtils.getNowDate());
        return winningTrainingRecordMapper.insertWinningTrainingRecord(winningTrainingRecord);
    }

    /**
     * 修改人与人pk
     *
     * @param winningTrainingRecord 人与人pk
     * @return 结果
     */
    @Override
    public int updateWinningTrainingRecord(WinningTrainingRecord winningTrainingRecord) {
        winningTrainingRecord.setUpdateTime(DateUtils.getNowDate());
        return winningTrainingRecordMapper.updateWinningTrainingRecord(winningTrainingRecord);
    }

    /**
     * 删除人与人pk对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteWinningTrainingRecordByIds(String ids) {
        return winningTrainingRecordMapper.deleteWinningTrainingRecordByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除人与人pk信息
     *
     * @param winningTrainingRecordId 人与人pkID
     * @return 结果
     */
    @Override
    public int deleteWinningTrainingRecordById(String winningTrainingRecordId) {
        return winningTrainingRecordMapper.deleteWinningTrainingRecordById(winningTrainingRecordId);
    }
    /**
     * 查询人与人pk列表
     *
     * @param winningTrainingRecord
     * @return 汇报训练集合
     */
    @Override
    public List<Map<String, Object>> selectWebWinningTrainingList(WinningTrainingRecord winningTrainingRecord) {
        return winningTrainingRecordMapper.selectWebWinningTrainingList(winningTrainingRecord);
    }
}
