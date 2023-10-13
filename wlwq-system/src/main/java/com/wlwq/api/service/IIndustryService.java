package com.wlwq.api.service;

import java.util.List;

import com.wlwq.api.domain.Industry;
import com.wlwq.common.core.domain.Ztree;

/**
 * 行业信息Service接口
 *
 * @author wwb
 * @date 2023-05-23
 */
public interface IIndustryService {
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
     * 批量删除行业信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteIndustryByIds(String ids);

    /**
     * 删除行业信息信息
     *
     * @param id 行业信息ID
     * @return 结果
     */
    public int deleteIndustryById(Long id);

    /**
     * 查询行业信息树列表
     *
     * @return 所有行业信息信息
     */
    public List<Ztree> selectIndustryTree();
}
