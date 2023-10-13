package com.wlwq.api.service;

import java.util.List;
import java.util.Map;

import com.wlwq.api.domain.SixStructuresReadRecord;

/**
 * 六大架构已读记录Service接口
 * 
 * @author wlwq
 * @date 2023-08-28
 */
public interface ISixStructuresReadRecordService {
    /**
     * 查询六大架构已读记录
     * 
     * @param sixStructuresReadRecordId 六大架构已读记录ID
     * @return 六大架构已读记录
     */
    public SixStructuresReadRecord selectSixStructuresReadRecordById(String sixStructuresReadRecordId);

    /**
     * 查询六大架构已读记录列表
     * 
     * @param sixStructuresReadRecord 六大架构已读记录
     * @return 六大架构已读记录集合
     */
    public List<SixStructuresReadRecord> selectSixStructuresReadRecordList(SixStructuresReadRecord sixStructuresReadRecord);

    /**
     * 新增六大架构已读记录
     * 
     * @param sixStructuresReadRecord 六大架构已读记录
     * @return 结果
     */
    public int insertSixStructuresReadRecord(SixStructuresReadRecord sixStructuresReadRecord);

    /**
     * 修改六大架构已读记录
     * 
     * @param sixStructuresReadRecord 六大架构已读记录
     * @return 结果
     */
    public int updateSixStructuresReadRecord(SixStructuresReadRecord sixStructuresReadRecord);

    /**
     * 批量删除六大架构已读记录
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSixStructuresReadRecordByIds(String ids);

    /**
     * 删除六大架构已读记录信息
     * 
     * @param sixStructuresReadRecordId 六大架构已读记录ID
     * @return 结果
     */
    public int deleteSixStructuresReadRecordById(String sixStructuresReadRecordId);

    /**
     * 查询已读数量
     * @param build
     * @return
     */
    int selectSixStructuresReadRecordCount(SixStructuresReadRecord sixStructuresReadRecord);

    /**
     * 只查询一条记录
     * @param sixStructuresReadRecord
     * @return
     */
    SixStructuresReadRecord selectSixStructuresReadRecord(SixStructuresReadRecord sixStructuresReadRecord);

    /**
     * 删除已读的记录
     * @param sixStructuresReadRecord
     */
    public int deleteSixStructuresReadRecord(SixStructuresReadRecord sixStructuresReadRecord);

    /**
     * app查询已读数量
     * @param accountId
     * @param companyId
     * @param classIds
     * @return
     */
    Map<String, Integer> selectSixStructuresReadRecordCountByClassIds(String accountId, Long companyId, List<String> classIds);
}
