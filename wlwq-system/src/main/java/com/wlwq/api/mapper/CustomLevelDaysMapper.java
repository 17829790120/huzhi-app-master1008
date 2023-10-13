package com.wlwq.api.mapper;

import java.util.List;
import com.wlwq.api.domain.CustomLevelDays;
import com.wlwq.api.resultVO.CustomLevelVO;
import org.apache.ibatis.annotations.Param;

/**
 * 客户等级回访日维护Mapper接口
 * 
 * @author wlwq
 * @date 2023-06-07
 */
public interface CustomLevelDaysMapper {
    /**
     * 查询客户等级回访日维护
     * 
     * @param id 客户等级回访日维护ID
     * @return 客户等级回访日维护
     */
    public CustomLevelDays selectCustomLevelDaysById(String id);

    /**
     * 查询客户等级回访日维护列表
     * 
     * @param customLevelDays 客户等级回访日维护
     * @return 客户等级回访日维护集合
     */
    public List<CustomLevelDays> selectCustomLevelDaysList(CustomLevelDays customLevelDays);

    /**
     * 新增客户等级回访日维护
     * 
     * @param customLevelDays 客户等级回访日维护
     * @return 结果
     */
    public int insertCustomLevelDays(CustomLevelDays customLevelDays);

    /**
     * 修改客户等级回访日维护
     * 
     * @param customLevelDays 客户等级回访日维护
     * @return 结果
     */
    public int updateCustomLevelDays(CustomLevelDays customLevelDays);

    /**
     * 删除客户等级回访日维护
     * 
     * @param id 客户等级回访日维护ID
     * @return 结果
     */
    public int deleteCustomLevelDaysById(String id);

    /**
     * 批量删除客户等级回访日维护
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteCustomLevelDaysByIds(String[] ids);
    /**
     * 根据等级查询回访日
     */
    int getCustomDays(@Param("level") String level,@Param("companyId") String companyId);

    /**
     * 查询各公司等级
     */
    List<CustomLevelVO> findCustomLevelVO(@Param("companyId") String companyId);

    /**
     * 进入下一轮回访
     */
    int updateCustomFollowTime(@Param("customClaimIds") List<String> customClaimIds);
}
