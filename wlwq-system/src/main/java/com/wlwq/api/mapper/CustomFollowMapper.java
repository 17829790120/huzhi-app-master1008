package com.wlwq.api.mapper;

import java.util.List;
import com.wlwq.api.domain.CustomFollow;
import com.wlwq.api.resultVO.CustomFollowListVO;
import com.wlwq.api.resultVO.CustomFollowVO;
import org.apache.ibatis.annotations.Param;

/**
 * 客户跟进Mapper接口
 * 
 * @author wlwq
 * @date 2023-06-02
 */
public interface CustomFollowMapper {
    /**
     * 查询客户跟进
     * 
     * @param customFollowId 客户跟进ID
     * @return 客户跟进
     */
    public CustomFollow selectCustomFollowById(String customFollowId);

    /**
     * 查询客户跟进列表
     * 
     * @param customFollow 客户跟进
     * @return 客户跟进集合
     */
    public List<CustomFollow> selectCustomFollowList(CustomFollow customFollow);

    /**
     * 查询客户跟进记录
     */
    List<CustomFollowListVO> findCustomFollows(@Param("companyId") String companyId,@Param("customName") String customName);

    /**
     * 新增客户跟进
     * 
     * @param customFollow 客户跟进
     * @return 结果
     */
    public int insertCustomFollow(CustomFollow customFollow);

    /**
     * 修改客户跟进
     * 
     * @param customFollow 客户跟进
     * @return 结果
     */
    public int updateCustomFollow(CustomFollow customFollow);

    /**
     * 删除客户跟进
     * 
     * @param customFollowId 客户跟进ID
     * @return 结果
     */
    public int deleteCustomFollowById(String customFollowId);

    /**
     * 批量删除客户跟进
     * 
     * @param customFollowIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteCustomFollowByIds(String[] customFollowIds);

    /**
     * 查询跟进记录
     */
    List<CustomFollowVO> findCustomFollowVO(@Param("customId") String customId,@Param("userId") String userId);
}
