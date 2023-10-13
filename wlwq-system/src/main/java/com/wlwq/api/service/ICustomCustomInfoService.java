package com.wlwq.api.service;

import java.text.ParseException;
import java.util.List;
import com.wlwq.api.domain.CustomCustomInfo;
import com.wlwq.api.resultVO.*;
import com.wlwq.common.apiResult.ApiResult;
import com.wlwq.common.apiResult.PageParam;
import com.wlwq.common.core.domain.AjaxResult;

/**
 * 客户Service接口
 * 
 * @author wlwq
 * @date 2023-06-02
 */
public interface ICustomCustomInfoService {
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
    public int insertCustomCustomInfo(CustomCustomInfo customCustomInfo) throws ParseException;

    /**
     * 修改客户
     * 
     * @param customCustomInfo 客户
     * @return 结果
     */
    public int updateCustomCustomInfo(CustomCustomInfo customCustomInfo);

    /**
     * 批量删除客户
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteCustomCustomInfoByIds(String ids);

    /**
     * 删除客户信息
     * 
     * @param customId 客户ID
     * @return 结果
     */
    public int deleteCustomCustomInfoById(String customId);

    /**
     * 查询客户详细信息及跟进状态
     */
    ApiResult getCustomInfoVO(String customId,String userId);

    /**
     * 查询可认领的客户
     */
    List<CustomVO> findCustomInfo(String customName,String customSource, Long companyId,String type);
    /**
     * app认领
     */
    int claimCustom(Long userId,String deptId,String companyId,String customId) throws ParseException;
    /**
     * 查询我的客户
     */
    List<MyCustomVO> findMyCustomVO(CustomFindVO customVO);
    /**
     * 释放客户
     */
    int releaseCustom(String customId,String customClaimId);

    /**
     * 批量释放客户
     */
    int releaseCustoms(List<String> customIds,List<String> customClaimIds);

    /**
     * 查询客户数-成交量
     */
    CustomCountVO getCustomCountVO(String userId);
    /**
     * 查询数据字典
     */
    List<CustomDictVO> findCustomDictVO(String dictType);
    /**
     * 修改客户标签
     */
    int updateCustomLabel(String customId,String customLabel);
    /**
     * 查询成交排行榜
     */
    CustomRankingVO getCustomRankingVO(PageParam pageParam,String userId, String companyId);

    /**
     * 查询需要扔的用户
     */
    List<CustomThrowVO> findCustomThrowVO(Integer type);

    /**
     * 查询我认领的客户
     */
    List<MyCustomVO> findMyCustom(Long userId,String customName);
}
