package com.wlwq.api.mapper;

import java.util.List;

import com.wlwq.api.domain.Industry;

/**
 * 行业信息Mapper接口
 *
 * @author wwb
 * @date 2023-05-23
 */
public interface IndustryMapper {
    /**
     * 查询行业信息
     *
     * @param id 行业信息ID
     * @return 行业信息
     */
    public Industry selectIndustryById(Long id);

    /**
     * 查询行业信息列表
     *
     * @param industry 行业信息
     * @return 行业信息集合
     */
    public List<Industry> selectIndustryList(Industry industry);

    /**
     * 新增行业信息
     *
     * @param industry 行业信息
     * @return 结果
     */
    public int insertIndustry(Industry industry);

    /**
     * 修改行业信息
     *
     * @param industry 行业信息
     * @return 结果
     */
    public int updateIndustry(Industry industry);

    /**
     * 删除行业信息
     *
     * @param id 行业信息ID
     * @return 结果
     */
    public int deleteIndustryById(Long id);

    /**
     * 批量删除行业信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteIndustryByIds(String[] ids);
}
