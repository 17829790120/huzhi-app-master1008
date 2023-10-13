package com.wlwq.api.mapper;

import java.util.List;
import java.util.Map;

import com.wlwq.api.domain.SixStructuresReadRecord;
import org.apache.ibatis.annotations.Param;

/**
 * 六大架构已读记录Mapper接口
 * 
 * @author wlwq
 * @date 2023-08-28
 */
public interface SixStructuresReadRecordMapper {
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
     * 删除六大架构已读记录
     * 
     * @param sixStructuresReadRecordId 六大架构已读记录ID
     * @return 结果
     */
    public int deleteSixStructuresReadRecordById(String sixStructuresReadRecordId);

    /**
     * 批量删除六大架构已读记录
     * 
     * @param sixStructuresReadRecordIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSixStructuresReadRecordByIds(String[] sixStructuresReadRecordIds);

    /**
     * 查询已读数量
     * @param build
     * @return
     */
    int selectSixStructuresReadRecordCount(SixStructuresReadRecord build);

    /**
     * 只查询一条记录
     * @param build
     * @return
     */
    SixStructuresReadRecord selectSixStructuresReadRecord(SixStructuresReadRecord build);

    /**
     * 删除已读的记录
     * @param build
     */
    public int deleteSixStructuresReadRecord(SixStructuresReadRecord build);

    /**
     * app查询已读数量
     * @param accountId
     * @param companyId
     * @param classIds
     * @return
     */
    Map<String, Integer> selectSixStructuresReadRecordCountByClassIds(@Param("accountId") String accountId, @Param("companyId")Long companyId, @Param("classIds")List<String> classIds);
}
