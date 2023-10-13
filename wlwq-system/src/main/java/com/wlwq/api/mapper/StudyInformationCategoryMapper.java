package com.wlwq.api.mapper;

import java.util.List;

import com.wlwq.api.domain.StudyInformationCategory;

/**
 * 资讯分类Mapper接口
 *
 * @author web
 * @date 2023-05-16
 */
public interface StudyInformationCategoryMapper {
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
     * 删除资讯分类
     *
     * @param studyInformationCategoryId 资讯分类ID
     * @return 结果
     */
    public int deleteStudyInformationCategoryById(Long studyInformationCategoryId);

    /**
     * 批量删除资讯分类
     *
     * @param studyInformationCategoryIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteStudyInformationCategoryByIds(String[] studyInformationCategoryIds);

    int countSonNumberByParentId(Long studyInformationCategoryId);

    List<StudyInformationCategory> selectStudyInformationCategoryVoList(StudyInformationCategory studyInformationCategory);
}
