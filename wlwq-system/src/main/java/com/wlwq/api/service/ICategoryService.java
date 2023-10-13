package com.wlwq.api.service;

import com.wlwq.api.domain.Categorys;
import com.wlwq.common.core.domain.Ztree;

import java.util.List;

/**
 * 考试类目Service接口
 *
 * @author Renbowen
 * @date 2021-01-07
 */
public interface ICategoryService {
    /**
     * 查询考试类目
     *
     * @param categoryId 考试类目ID
     * @return 考试类目
     */
    public Categorys selectCategoryById(Long categoryId);

    /**
     * 查询考试类目列表
     *
     * @param categorys 考试类目
     * @return 考试类目集合
     */
    public List<Categorys> selectCategoryList(Categorys categorys);

    /**
     * 新增考试类目
     *
     * @param categorys 考试类目
     * @return 结果
     */
    public int insertCategory(Categorys categorys);

    /**
     * 修改考试类目
     *
     * @param categorys 考试类目
     * @return 结果
     */
    public int updateCategory(Categorys categorys);

    /**
     * 批量删除考试类目
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteCategoryByIds(String ids);

    /**
     * 删除考试类目信息
     *
     * @param categoryId 考试类目ID
     * @return 结果
     */
    public int deleteCategoryById(Long categoryId);

    /**
     * 查询考试类目树列表
     *
     * @return 所有考试类目信息
     */
    public List<Ztree> selectCategoryTree();

    /**
     * 根据分类的id查询其中一条分类
     *
     * @param parentId 考试类目ID
     * @return 考试类目
     */
    public Categorys selectCategoryByCategoryById(Long parentId);

    /**
     * 根据分类的id查询其对应的老师微信二维码
     *
     * @param categoryId 考试分类ID
     * @return 考试类目对应老师二维码
     */
    public Categorys selectCategoryCode(Long categoryId);


    /**
     * 根据父类ID查询子类
     *
     * @param parentId 考试类目ID
     * @return 考试类目
     */
    public List<Categorys> selectCategoryListByParentId(String parentId);

    /**
     * 查询二级分类列表
     *
     * @return 二级分类列表
     */
    List<Categorys> selectTwoCategoryList();

    /**
     * 手机端查看分类
     * @param categorys
     * @return
     */
    List<Categorys> selectApiCategoryList(Categorys categorys);
}
