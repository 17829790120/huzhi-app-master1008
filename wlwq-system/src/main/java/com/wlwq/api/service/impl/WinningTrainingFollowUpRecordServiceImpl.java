package com.wlwq.api.service.impl;

import java.util.List;

import cn.hutool.core.util.IdUtil;
import com.wlwq.common.utils.DateUtils;
import com.wlwq.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.api.mapper.WinningTrainingFollowUpRecordMapper;
import com.wlwq.api.domain.WinningTrainingFollowUpRecord;
import com.wlwq.api.service.IWinningTrainingFollowUpRecordService;
import com.wlwq.common.core.text.Convert;

/**
 * 训练跟进记录Service业务层处理
 *
 * @author wwb
 * @date 2023-06-08
 */
@Service
public class WinningTrainingFollowUpRecordServiceImpl implements IWinningTrainingFollowUpRecordService {

    @Autowired
    private WinningTrainingFollowUpRecordMapper winningTrainingFollowUpRecordMapper;

    /**
     * 查询训练跟进记录
     *
     * @param winningTrainingFollowUpRecordId 训练跟进记录ID
     * @return 训练跟进记录
     */
    @Override
    public WinningTrainingFollowUpRecord selectWinningTrainingFollowUpRecordById(String winningTrainingFollowUpRecordId) {
        return winningTrainingFollowUpRecordMapper.selectWinningTrainingFollowUpRecordById(winningTrainingFollowUpRecordId);
    }

    /**
     * 查询训练跟进记录列表
     *
     * @param winningTrainingFollowUpRecord 训练跟进记录
     * @return 训练跟进记录
     */
    @Override
    public List<WinningTrainingFollowUpRecord> selectWinningTrainingFollowUpRecordList(WinningTrainingFollowUpRecord winningTrainingFollowUpRecord) {
        return winningTrainingFollowUpRecordMapper.selectWinningTrainingFollowUpRecordList(winningTrainingFollowUpRecord);
    }

    /**
     * 新增训练跟进记录
     *
     * @param winningTrainingFollowUpRecord 训练跟进记录
     * @return 结果
     */
    @Override
    public int insertWinningTrainingFollowUpRecord(WinningTrainingFollowUpRecord winningTrainingFollowUpRecord) {
        if(StringUtils.isEmpty(winningTrainingFollowUpRecord.getWinningTrainingFollowUpRecordId())){
            winningTrainingFollowUpRecord.setWinningTrainingFollowUpRecordId(IdUtil.getSnowflake(1, 1).nextIdStr());
        }
        winningTrainingFollowUpRecord.setCreateTime(DateUtils.getNowDate());
        return winningTrainingFollowUpRecordMapper.insertWinningTrainingFollowUpRecord(winningTrainingFollowUpRecord);
    }

    /**
     * 修改训练跟进记录
     *
     * @param winningTrainingFollowUpRecord 训练跟进记录
     * @return 结果
     */
    @Override
    public int updateWinningTrainingFollowUpRecord(WinningTrainingFollowUpRecord winningTrainingFollowUpRecord) {
        winningTrainingFollowUpRecord.setUpdateTime(DateUtils.getNowDate());
        return winningTrainingFollowUpRecordMapper.updateWinningTrainingFollowUpRecord(winningTrainingFollowUpRecord);
    }

    /**
     * 删除训练跟进记录对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteWinningTrainingFollowUpRecordByIds(String ids) {
        return winningTrainingFollowUpRecordMapper.deleteWinningTrainingFollowUpRecordByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除训练跟进记录信息
     *
     * @param winningTrainingFollowUpRecordId 训练跟进记录ID
     * @return 结果
     */
    @Override
    public int deleteWinningTrainingFollowUpRecordById(String winningTrainingFollowUpRecordId) {
        return winningTrainingFollowUpRecordMapper.deleteWinningTrainingFollowUpRecordById(winningTrainingFollowUpRecordId);
    }
}
