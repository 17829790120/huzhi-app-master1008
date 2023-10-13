package com.wlwq.api.service;

import java.util.List;

import com.wlwq.api.domain.StudyInformationCategory;

/**
 * 资讯分类Service接口
 *
 * @author web
 * @date 2023-05-16
 */
public interface IStudyInformationCategoryService {
    /**
     * 查询资讯分类
     *
     * @param studyInformationCategoryId 资讯分类ID
     * @return 资讯分类
     */
    public StudyInformationCategory selectStudyInformationCategoryById(Long studyInformationCategoryId);

    /**
     * 查询资讯分类列表
     *
     * @param studyInformationCategory 资讯分类
     * @return 资讯分类集合
     */
    public List<StudyInformationCategory> selectStudyInformationCategoryList(StudyInformationCategory studyInformationCategory);

    /**
     * 新增资讯分类
     *
     * @param studyInformationCategory 资讯分类
     * @return 结果
     */
    public int insertStudyInformationCategory(StudyInformationCategory studyInformationCategory);

    /**
     * 修改资讯分类
     *
     * @param studyInformationCategory 资讯分类
     * @return 结果
     */
    public int updateStudyInformationCategory(StudyInformationCategory studyInformationCategory);

    /**
     * 批量删除资讯分类
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteStudyInformationCategoryByIds(String ids);

    /**
     * 删除资讯分类信息
     *
     * @param studyInformationCategoryId 资讯分类ID
     * @return 结果
     */
    public int deleteStudyInformationCategoryById(Long studyInformationCategoryId);

    /**
     * 查询资讯分类列表
     *
     * @return 所有资讯分类信息
     */
    List<StudyInformationCategory> selectInformationCategoryVoList(StudyInformationCategory studyInformationCategory);

    StringBuffer selectAncestorsById(Long studyInformationCategoryId, StringBuffer buf);
}
