package com.wlwq.api.service;

import java.util.List;
import com.wlwq.api.domain.CustomExamineRecord;
import com.wlwq.api.resultVO.CustomExamineVO;

/**
 * 客户成交Service接口
 * 
 * @author wlwq
 * @date 2023-06-10
 */
public interface ICustomExamineRecordService {
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
     * 批量删除客户成交
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteCustomExamineRecordByIds(String ids);

    /**
     * 删除客户成交信息
     * 
     * @param customExamineRecordId 客户成交ID
     * @return 结果
     */
    public int deleteCustomExamineRecordById(Long customExamineRecordId);

    /**
     * 成交详情
     */
    CustomExamineVO getCustomExamineVO(String customClaimId);

}
