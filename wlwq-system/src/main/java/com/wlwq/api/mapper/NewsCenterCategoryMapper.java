package com.wlwq.api.mapper;

import java.util.List;

import com.wlwq.api.domain.NewsCenterCategory;

/**
 * 新闻中心Mapper接口
 *
 * @author wwb
 * @date 2023-05-06
 */
public interface NewsCenterCategoryMapper {
    /**
     * 查询新闻中心
     *
     * @param newsCenterCategoryId 新闻中心ID
     * @return 新闻中心
     */
    public NewsCenterCategory selectNewsCenterCategoryById(Long newsCenterCategoryId);

    /**
     * 查询新闻中心列表
     *
     * @param newsCenterCategory 新闻中心
     * @return 新闻中心集合
     */
    public List<NewsCenterCategory> selectNewsCenterCategoryList(NewsCenterCategory newsCenterCategory);

    /**
     * 新增新闻中心
     *
     * @param newsCenterCategory 新闻中心
     * @return 结果
     */
    public int insertNewsCenterCategory(NewsCenterCategory newsCenterCategory);

    /**
     * 修改新闻中心
     *
     * @param newsCenterCategory 新闻中心
     * @return 结果
     */
    public int updateNewsCenterCategory(NewsCenterCategory newsCenterCategory);

    /**
     * 删除新闻中心
     *
     * @param newsCenterCategoryId 新闻中心ID
     * @return 结果
     */
    public int deleteNewsCenterCategoryById(Long newsCenterCategoryId);

    /**
     * 批量删除新闻中心
     *
     * @param newsCenterCategoryIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteNewsCenterCategoryByIds(String[] newsCenterCategoryIds);
    /**
     * 查询新闻中心列表
     *
     * @param newsCenterCategory 新闻中心
     * @return 新闻中心集合
     */
    List<NewsCenterCategory> selectNewsCenterCategoryVoList(NewsCenterCategory newsCenterCategory);
}
