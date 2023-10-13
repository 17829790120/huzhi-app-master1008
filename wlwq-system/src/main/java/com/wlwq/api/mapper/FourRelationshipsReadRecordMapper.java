package com.wlwq.api.mapper;

import com.wlwq.api.domain.FourRelationshipsReadRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 四类关系已读记录Mapper接口
 * 
 * @author dxy
 */
public interface FourRelationshipsReadRecordMapper {
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
     * 删除四类关系已读记录
     * 
     * @param fourRelationshipsReadRecordId 四类关系已读记录ID
     * @return 结果
     */
    public int deleteFourRelationshipsReadRecordById(String fourRelationshipsReadRecordId);

    /**
     * 批量删除四类关系已读记录
     * 
     * @param fourRelationshipsReadRecordIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteFourRelationshipsReadRecordByIds(String[] fourRelationshipsReadRecordIds);

    /**
     * 查询已读数量
     * @param build
     * @return
     */
    int selectFourRelationshipsReadRecordCount(FourRelationshipsReadRecord build);

    /**
     * 只查询一条记录
     * @param build
     * @return
     */
    FourRelationshipsReadRecord selectFourRelationshipsReadRecord(FourRelationshipsReadRecord build);

    /**
     * 删除已读的记录
     * @param build
     */
    public int deleteFourRelationshipsReadRecord(FourRelationshipsReadRecord build);

    /**
     * app查询已读数量
     * @param accountId
     * @param companyId
     * @param classIds
     * @return
     */
    Map<String, Integer> selectFourRelationshipsReadRecordCountByClassIds(@Param("accountId") String accountId, @Param("companyId")Long companyId, @Param("classIds")List<String> classIds);
}
