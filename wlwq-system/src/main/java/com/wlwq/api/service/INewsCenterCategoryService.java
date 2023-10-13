package com.wlwq.api.service;

import java.util.List;

import com.wlwq.api.domain.NewsCenterCategory;
import com.wlwq.api.resultVO.SysDictTypeVO;
import org.apache.ibatis.annotations.Param;

/**
 * 新闻中心Service接口
 *
 * @author wwb
 * @date 2023-05-06
 */
public interface INewsCenterCategoryService {
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
     * 批量删除新闻中心
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteNewsCenterCategoryByIds(String ids);

    /**
     * 删除新闻中心信息
     *
     * @param newsCenterCategoryId 新闻中心ID
     * @return 结果
     */
    public int deleteNewsCenterCategoryById(Long newsCenterCategoryId);
    /**
     * 查询新闻中心列表
     *
     * @param newsCenterCategory 新闻中心
     * @return 新闻中心集合
     */
    List<NewsCenterCategory> selectNewsCenterCategoryVoList(NewsCenterCategory newsCenterCategory);

    StringBuffer selectAncestorsById(Long informationCategoryId, StringBuffer buf);

    /**
     * 热门搜索
     */
    List<SysDictTypeVO> findSysDictTypeVO(List<String> dictTypes);
}
