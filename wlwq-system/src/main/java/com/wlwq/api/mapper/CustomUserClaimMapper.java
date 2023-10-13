package com.wlwq.api.mapper;

import java.util.Date;
import java.util.List;
import com.wlwq.api.domain.CustomUserClaim;
import com.wlwq.api.resultVO.CustomUserVO;
import org.apache.ibatis.annotations.Param;

/**
 * 客户认领Mapper接口
 * 
 * @author wlwq
 * @date 2023-06-02
 */
public interface CustomUserClaimMapper {
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
     * 删除客户认领
     * 
     * @param customClaimId 客户认领ID
     * @return 结果
     */
    public int deleteCustomUserClaimById(Long customClaimId);

    /**
     * 批量删除客户认领
     * 
     * @param customClaimIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteCustomUserClaimByIds(String[] customClaimIds);
    /**
     * 修改客户表状态
     */
    int updateCustomUser(@Param("status") Long status,@Param("customClaimId") String customClaimId);

    /**
     * 批量修改客户表状态
     */
    int updateCustomUsers(@Param("status") Long status,@Param("customClaimIds") List<String> customClaimIds);
    /**
     * 修改回访期限
     */
    int updateCustomFollowTime(@Param("customClaimId") String customClaimId);
    /**
     * 修改记录为已成交
     */
    int updateCustomUserClaimStatus(@Param("customInfoId") String customInfoId);

    /**
     * 查询公司客户
     */
    List<CustomUserVO> findCustomUserVO(@Param("companyId") String companyId,@Param("customName") String customName);
}
