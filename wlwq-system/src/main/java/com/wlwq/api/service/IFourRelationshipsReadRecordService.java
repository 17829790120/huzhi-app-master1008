package com.wlwq.api.service;

import com.wlwq.api.domain.FourRelationshipsReadRecord;

import java.util.List;
import java.util.Map;

/**
 * 四类关系已读记录Service接口
 * 
 * @author dxy
 */
public interface IFourRelationshipsReadRecordService {
    /**
     * 查询四类关系已读记录
     * 
     * @param fourRelationshipsReadRecordId 四类关系已读记录ID
     * @return 四类关系已读记录
     */
    public FourRelationshipsReadRecord selectFourRelationshipsReadRecordById(String fourRelationshipsReadRecordId);

    /**
     * 查询四类关系已读记录列表
     * 
     * @param fourRelationshipsReadRecord 四类关系已读记录
     * @return 四类关系已读记录集合
     */
    public List<FourRelationshipsReadRecord> selectFourRelationshipsReadRecordList(FourRelationshipsReadRecord fourRelationshipsReadRecord);

    /**
     * 新增四类关系已读记录
     * 
     * @param fourRelationshipsReadRecord 四类关系已读记录
     * @return 结果
     */
    public int insertFourRelationshipsReadRecord(FourRelationshipsReadRecord fourRelationshipsReadRecord);

    /**
     * 修改四类关系已读记录
     * 
     * @param fourRelationshipsReadRecord 四类关系已读记录
     * @return 结果
     */
    public int updateFourRelationshipsReadRecord(FourRelationshipsReadRecord fourRelationshipsReadRecord);

    /**
     * 批量删除四类关系已读记录
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteFourRelationshipsReadRecordByIds(String ids);

    /**
     * 删除四类关系已读记录信息
     * 
     * @param fourRelationshipsReadRecordId 四类关系已读记录ID
     * @return 结果
     */
    public int deleteFourRelationshipsReadRecordById(String fourRelationshipsReadRecordId);

    /**
     * 查询已读数量
     * @param
     * @return
     */
    int selectFourRelationshipsReadRecordCount(FourRelationshipsReadRecord fourRelationshipsReadRecord);

    /**
     * 只查询一条记录
     * @param fourRelationshipsReadRecord
     * @return
     */
    FourRelationshipsReadRecord selectFourRelationshipsReadRecord(FourRelationshipsReadRecord fourRelationshipsReadRecord);

    /**
     * 删除已读的记录
     * @param fourRelationshipsReadRecord
     */
    public int deleteFourRelationshipsReadRecord(FourRelationshipsReadRecord fourRelationshipsReadRecord);

    /**
     * app查询已读数量
     * @param accountId
     * @param companyId
     * @param classIds
     * @return
     */
    Map<String, Integer> selectFourRelationshipsReadRecordCountByClassIds(String accountId, Long companyId, List<String> classIds);
}
