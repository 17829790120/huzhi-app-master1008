package com.wlwq.api.service.impl;

import java.util.List;
import java.util.Map;

import cn.hutool.core.util.IdUtil;
import com.wlwq.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.api.mapper.SixStructuresReadRecordMapper;
import com.wlwq.api.domain.SixStructuresReadRecord;
import com.wlwq.api.service.ISixStructuresReadRecordService;
import com.wlwq.common.core.text.Convert;

/**
 * 六大架构已读记录Service业务层处理
 * 
 * @author wlwq
 * @date 2023-08-28
 */
@Service
public class SixStructuresReadRecordServiceImpl implements ISixStructuresReadRecordService {

    @Autowired
    private SixStructuresReadRecordMapper sixStructuresReadRecordMapper;

    /**
     * 查询六大架构已读记录
     * 
     * @param sixStructuresReadRecordId 六大架构已读记录ID
     * @return 六大架构已读记录
     */
    @Override
    public SixStructuresReadRecord selectSixStructuresReadRecordById(String sixStructuresReadRecordId) {
        return sixStructuresReadRecordMapper.selectSixStructuresReadRecordById(sixStructuresReadRecordId);
    }

    /**
     * 查询六大架构已读记录列表
     * 
     * @param sixStructuresReadRecord 六大架构已读记录
     * @return 六大架构已读记录
     */
    @Override
    public List<SixStructuresReadRecord> selectSixStructuresReadRecordList(SixStructuresReadRecord sixStructuresReadRecord) {
        return sixStructuresReadRecordMapper.selectSixStructuresReadRecordList(sixStructuresReadRecord);
    }

    /**
     * 新增六大架构已读记录
     * 
     * @param sixStructuresReadRecord 六大架构已读记录
     * @return 结果
     */
    @Override
    public int insertSixStructuresReadRecord(SixStructuresReadRecord sixStructuresReadRecord) {
        sixStructuresReadRecord.setSixStructuresReadRecordId(IdUtil.getSnowflake(1,1).nextIdStr());
        sixStructuresReadRecord.setCreateTime(DateUtils.getNowDate());
        return sixStructuresReadRecordMapper.insertSixStructuresReadRecord(sixStructuresReadRecord);
    }

    /**
     * 修改六大架构已读记录
     * 
     * @param sixStructuresReadRecord 六大架构已读记录
     * @return 结果
     */
    @Override
    public int updateSixStructuresReadRecord(SixStructuresReadRecord sixStructuresReadRecord) {
        sixStructuresReadRecord.setUpdateTime(DateUtils.getNowDate());
        return sixStructuresReadRecordMapper.updateSixStructuresReadRecord(sixStructuresReadRecord);
    }

    /**
     * 删除六大架构已读记录对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSixStructuresReadRecordByIds(String ids) {
        return sixStructuresReadRecordMapper.deleteSixStructuresReadRecordByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除六大架构已读记录信息
     * 
     * @param sixStructuresReadRecordId 六大架构已读记录ID
     * @return 结果
     */
    @Override
    public int deleteSixStructuresReadRecordById(String sixStructuresReadRecordId) {
        return sixStructuresReadRecordMapper.deleteSixStructuresReadRecordById(sixStructuresReadRecordId);
    }

    /**
     * 查询已读数量
     * @param sixStructuresReadRecord
     * @return
     */
    @Override
    public int selectSixStructuresReadRecordCount(SixStructuresReadRecord sixStructuresReadRecord) {
        return sixStructuresReadRecordMapper.selectSixStructuresReadRecordCount(sixStructuresReadRecord);
    }

    /**
     * 只查询一条记录
     * @param sixStructuresReadRecord
     * @return
     */
    @Override
    public SixStructuresReadRecord selectSixStructuresReadRecord(SixStructuresReadRecord sixStructuresReadRecord) {
        return sixStructuresReadRecordMapper.selectSixStructuresReadRecord(sixStructuresReadRecord);
    }

    /**
     * 删除已读的记录
     * @param sixStructuresReadRecord
     */
    @Override
    public int deleteSixStructuresReadRecord(SixStructuresReadRecord sixStructuresReadRecord) {
        return sixStructuresReadRecordMapper.deleteSixStructuresReadRecord(sixStructuresReadRecord);
    }

    /**
     *  app查询已读数量
     * @param accountId
     * @param companyId
     * @param classIds
     * @return
     */
    @Override
    public Map<String, Integer> selectSixStructuresReadRecordCountByClassIds(String accountId, Long companyId, List<String> classIds) {
        return sixStructuresReadRecordMapper.selectSixStructuresReadRecordCountByClassIds(accountId,companyId,classIds);
    }
}
