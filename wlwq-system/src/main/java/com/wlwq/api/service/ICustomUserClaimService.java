package com.wlwq.api.service;

import java.util.List;
import com.wlwq.api.domain.CustomUserClaim;
import com.wlwq.api.resultVO.CustomUserVO;
import org.apache.ibatis.annotations.Param;

/**
 * 客户认领Service接口
 * 
 * @author wlwq
 * @date 2023-06-02
 */
public interface ICustomUserClaimService {
    /**
     * 查询客户认领
     * 
     * @param customClaimId 客户认领ID
     * @return 客户认领
     */
    public CustomUserClaim selectCustomUserClaimById(Long customClaimId);

    /**
     * 查询客户认领列表
     * 
     * @param customUserClaim 客户认领
     * @return 客户认领集合
     */
    public List<CustomUserClaim> selectCustomUserClaimList(CustomUserClaim customUserClaim);

    /**
     * 新增客户认领
     * 
     * @param customUserClaim 客户认领
     * @return 结果
     */
    public int insertCustomUserClaim(CustomUserClaim customUserClaim);

    /**
     * 修改客户认领
     * 
     * @param customUserClaim 客户认领
     * @return 结果
     */
    public int updateCustomUserClaim(CustomUserClaim customUserClaim);

    /**
     * 批量删除客户认领
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteCustomUserClaimByIds(String ids);

    /**
     * 删除客户认领信息
     * 
     * @param customClaimId 客户认领ID
     * @return 结果
     */
    public int deleteCustomUserClaimById(Long customClaimId);
    /**
     * 修改记录为已成交
     */
    int updateCustomUserClaimStatus(String customInfoId,String customExamineRecordId);

    /**
     * pc查询客户认领
     */
    List<CustomUserVO> findCustomUserVO(String companyId,String customName);
}
