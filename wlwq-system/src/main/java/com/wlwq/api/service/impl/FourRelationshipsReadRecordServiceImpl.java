package com.wlwq.api.service.impl;

import cn.hutool.core.util.IdUtil;
import com.wlwq.api.domain.FourRelationshipsReadRecord;
import com.wlwq.api.mapper.FourRelationshipsReadRecordMapper;
import com.wlwq.api.service.IFourRelationshipsReadRecordService;
import com.wlwq.common.core.text.Convert;
import com.wlwq.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 四类关系已读记录Service业务层处理
 * 
 * @author dxy
 */
@Service
public class FourRelationshipsReadRecordServiceImpl implements IFourRelationshipsReadRecordService {

    @Autowired
    private FourRelationshipsReadRecordMapper fourRelationshipsReadRecordMapper;

    /**
     * 查询四类关系已读记录
     * 
     * @param fourRelationshipsReadRecordId 四类关系已读记录ID
     * @return 四类关系已读记录
     */
    @Override
    public FourRelationshipsReadRecord selectFourRelationshipsReadRecordById(String fourRelationshipsReadRecordId) {
        return fourRelationshipsReadRecordMapper.selectFourRelationshipsReadRecordById(fourRelationshipsReadRecordId);
    }

    /**
     * 查询四类关系已读记录列表
     * 
     * @param fourRelationshipsReadRecord 四类关系已读记录
     * @return 四类关系已读记录
     */
    @Override
    public List<FourRelationshipsReadRecord> selectFourRelationshipsReadRecordList(FourRelationshipsReadRecord fourRelationshipsReadRecord) {
        return fourRelationshipsReadRecordMapper.selectFourRelationshipsReadRecordList(fourRelationshipsReadRecord);
    }

    /**
     * 新增四类关系已读记录
     * 
     * @param fourRelationshipsReadRecord 四类关系已读记录
     * @return 结果
     */
    @Override
    public int insertFourRelationshipsReadRecord(FourRelationshipsReadRecord fourRelationshipsReadRecord) {
        fourRelationshipsReadRecord.setFourRelationshipsReadRecordId(IdUtil.getSnowflake(1,1).nextIdStr());
        fourRelationshipsReadRecord.setCreateTime(DateUtils.getNowDate());
        return fourRelationshipsReadRecordMapper.insertFourRelationshipsReadRecord(fourRelationshipsReadRecord);
    }

    /**
     * 修改四类关系已读记录
     * 
     * @param fourRelationshipsReadRecord 四类关系已读记录
     * @return 结果
     */
    @Override
    public int updateFourRelationshipsReadRecord(FourRelationshipsReadRecord fourRelationshipsReadRecord) {
        fourRelationshipsReadRecord.setUpdateTime(DateUtils.getNowDate());
        return fourRelationshipsReadRecordMapper.updateFourRelationshipsReadRecord(fourRelationshipsReadRecord);
    }

    /**
     * 删除四类关系已读记录对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteFourRelationshipsReadRecordByIds(String ids) {
        return fourRelationshipsReadRecordMapper.deleteFourRelationshipsReadRecordByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除四类关系已读记录信息
     * 
     * @param fourRelationshipsReadRecordId 四类关系已读记录ID
     * @return 结果
     */
    @Override
    public int deleteFourRelationshipsReadRecordById(String fourRelationshipsReadRecordId) {
        return fourRelationshipsReadRecordMapper.deleteFourRelationshipsReadRecordById(fourRelationshipsReadRecordId);
    }

    /**
     * 查询已读数量
     * @param fourRelationshipsReadRecord
     * @return
     */
    @Override
    public int selectFourRelationshipsReadRecordCount(FourRelationshipsReadRecord fourRelationshipsReadRecord) {
        return fourRelationshipsReadRecordMapper.selectFourRelationshipsReadRecordCount(fourRelationshipsReadRecord);
    }

    /**
     * 只查询一条记录
     * @param fourRelationshipsReadRecord
     * @return
     */
    @Override
    public FourRelationshipsReadRecord selectFourRelationshipsReadRecord(FourRelationshipsReadRecord fourRelationshipsReadRecord) {
        return fourRelationshipsReadRecordMapper.selectFourRelationshipsReadRecord(fourRelationshipsReadRecord);
    }

    /**
     * 删除已读的记录
     * @param fourRelationshipsReadRecord
     */
    @Override
    public int deleteFourRelationshipsReadRecord(FourRelationshipsReadRecord fourRelationshipsReadRecord) {
        return fourRelationshipsReadRecordMapper.deleteFourRelationshipsReadRecord(fourRelationshipsReadRecord);
    }

    /**
     *  app查询已读数量
     * @param accountId
     * @param companyId
     * @param classIds
     * @return
     */
    @Override
    public Map<String, Integer> selectFourRelationshipsReadRecordCountByClassIds(String accountId, Long companyId, List<String> classIds) {
        return fourRelationshipsReadRecordMapper.selectFourRelationshipsReadRecordCountByClassIds(accountId,companyId,classIds);
    }
}
