package com.wlwq.api.mapper;

import java.math.BigDecimal;
import java.util.List;
import com.wlwq.api.domain.CustomCustomInfo;
import com.wlwq.api.resultVO.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 客户Mapper接口
 * 
 * @author wlwq
 * @date 2023-06-02
 */
@Mapper
public interface CustomCustomInfoMapper {
    /**
     * 查询客户
     * 
     * @param customId 客户ID
     * @return 客户
     */
    public CustomCustomInfo selectCustomCustomInfoById(String customId);

    /**
     * 查询客户列表
     * 
     * @param customCustomInfo 客户
     * @return 客户集合
     */
    public List<CustomCustomInfo> selectCustomCustomInfoList(CustomCustomInfo customCustomInfo);

    /**
     * 新增客户
     * 
     * @param customCustomInfo 客户
     * @return 结果
     */
    public int insertCustomCustomInfo(CustomCustomInfo customCustomInfo);

    /**
     * 修改客户
     * 
     * @param customCustomInfo 客户
     * @return 结果
     */
    public int updateCustomCustomInfo(CustomCustomInfo customCustomInfo);

    /**
     * 删除客户
     * 
     * @param customId 客户ID
     * @return 结果
     */
    public int deleteCustomCustomInfoById(String customId);

    /**
     * 批量删除客户
     * 
     * @param customIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteCustomCustomInfoByIds(String[] customIds);

    /**
     * 查询客户
     */
    CustomInfoVO getCustomInfoVO(String customId);

    /**
     * 根据公司id查询
     */
    List<CustomVO> findCustomVO(@Param("customName") String customName,@Param("customSource") String customSource,@Param("companyId") Long companyId);

    /**
     * 查询自己的客户
     */
    List<MyCustomVO> findMyCustomVO(CustomFindVO customFindVO);

    /**
     * 修改客户状态为 未认领
     */
    int updateClaimStatus(@Param("status") Long status,@Param("customId") String customId);

    /**
     * 批量修改客户状态为 未认领
     */
    int updateClaimStatuses(@Param("status") Long status,@Param("customIds") List<String> customIds);


    /**
     * 查询客户
     */
    BigDecimal getCustom(@Param("userId") String userId,@Param("status") Long status, @Param("startTime") String startTime, @Param("endTime") String endTime);
    /**
     * 查询成交
     */
    BigDecimal getAmount(@Param("userId") String userId,@Param("status") Long status,@Param("startTime") String startTime,@Param("endTime") String endTime);

    /**
     * 查询数据字典
     */
    List<CustomDictVO> findCustomDictVO(String dictType);

    /**
     * 修改客户标签
     */
    int updateCustomLabel(@Param("customId") String customId,@Param("customLabel") String customLabel);

    /**
     * 查询排行榜
     */
    List<CustomRankingListVO> findCustomRankingVO(@Param("companyId") String companyId);

    /**
     * 扔掉过期客户的id
     */
    List<CustomThrowVO> findCustomThrowVO(@Param("type") Integer type);

    /**
     * 查询我认领的客户
     */
    List<MyCustomVO> findMyCustom(@Param("userId") Long userId,@Param("customName") String customName);
}
