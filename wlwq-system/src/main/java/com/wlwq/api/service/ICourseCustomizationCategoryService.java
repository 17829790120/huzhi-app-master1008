package com.wlwq.api.service;

import java.util.List;

import com.wlwq.api.domain.CourseCustomizationCategory;

/**
 * 课程定制类别Service接口
 *
 * @author wwb
 * @date 2023-05-19
 */
public interface ICourseCustomizationCategoryService {
    /**
     * 查询课程定制类别
     *
     * @param courseCustomizationCategoryId 课程定制类别ID
     * @return 课程定制类别
     */
    public CourseCustomizationCategory selectCourseCustomizationCategoryById(Long courseCustomizationCategoryId);

    /**
     * 查询课程定制类别列表
     *
     * @param courseCustomizationCategory 课程定制类别
     * @return 课程定制类别集合
     */
    public List<CourseCustomizationCategory> selectCourseCustomizationCategoryList(CourseCustomizationCategory courseCustomizationCategory);

    /**
     * 新增课程定制类别
     *
     * @param courseCustomizationCategory 课程定制类别
     * @return 结果
     */
    public int insertCourseCustomizationCategory(CourseCustomizationCategory courseCustomizationCategory);

    /**
     * 修改课程定制类别
     *
     * @param courseCustomizationCategory 课程定制类别
     * @return 结果
     */
    public int updateCourseCustomizationCategory(CourseCustomizationCategory courseCustomizationCategory);

    /**
     * 批量删除课程定制类别
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteCourseCustomizationCategoryByIds(String ids);

    /**
     * 删除课程定制类别信息
     *
     * @param courseCustomizationCategoryId 课程定制类别ID
     * @return 结果
     */
    public int deleteCourseCustomizationCategoryById(Long courseCustomizationCategoryId);
}
