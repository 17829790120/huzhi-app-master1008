package com.wlwq.api.mapper;

import java.util.List;
import com.wlwq.api.domain.CustomExamineRecord;
import com.wlwq.api.resultVO.CustomExamineVO;
import org.apache.ibatis.annotations.Param;

/**
 * 客户成交Mapper接口
 * 
 * @author wlwq
 * @date 2023-06-10
 */
public interface CustomExamineRecordMapper {
    /**
     * 查询客户成交
     * 
     * @param customExamineRecordId 客户成交ID
     * @return 客户成交
     */
    public CustomExamineRecord selectCustomExamineRecordById(String customExamineRecordId);

    /**
     * 查询客户成交列表
     * 
     * @param customExamineRecord 客户成交
     * @return 客户成交集合
     */
    public List<CustomExamineRecord> selectCustomExamineRecordList(CustomExamineRecord customExamineRecord);

    /**
     * 新增客户成交
     * 
     * @param customExamineRecord 客户成交
     * @return 结果
     */
    public int insertCustomExamineRecord(CustomExamineRecord customExamineRecord);

    /**
     * 修改客户成交
     * 
     * @param customExamineRecord 客户成交
     * @return 结果
     */
    public int updateCustomExamineRecord(CustomExamineRecord customExamineRecord);

    /**
     * 删除客户成交
     * 
     * @param customExamineRecordId 客户成交ID
     * @return 结果
     */
    public int deleteCustomExamineRecordById(Long customExamineRecordId);

    /**
     * 批量删除客户成交
     * 
     * @param customExamineRecordIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteCustomExamineRecordByIds(String[] customExamineRecordIds);

    /**
     * 查询客户成交详情
     */
    CustomExamineVO getCustomExamineVO(@Param("customClaimId") String customClaimId);
}
