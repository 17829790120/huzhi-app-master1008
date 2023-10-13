package com.wlwq.api.service.impl;

import java.util.List;

import com.wlwq.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.api.mapper.CourseCustomizationCategoryMapper;
import com.wlwq.api.domain.CourseCustomizationCategory;
import com.wlwq.api.service.ICourseCustomizationCategoryService;
import com.wlwq.common.core.text.Convert;

/**
 * 课程定制类别Service业务层处理
 *
 * @author wwb
 * @date 2023-05-19
 */
@Service
public class CourseCustomizationCategoryServiceImpl implements ICourseCustomizationCategoryService {

    @Autowired
    private CourseCustomizationCategoryMapper courseCustomizationCategoryMapper;

    /**
     * 查询课程定制类别
     *
     * @param courseCustomizationCategoryId 课程定制类别ID
     * @return 课程定制类别
     */
    @Override
    public CourseCustomizationCategory selectCourseCustomizationCategoryById(Long courseCustomizationCategoryId) {
        return courseCustomizationCategoryMapper.selectCourseCustomizationCategoryById(courseCustomizationCategoryId);
    }

    /**
     * 查询课程定制类别列表
     *
     * @param courseCustomizationCategory 课程定制类别
     * @return 课程定制类别
     */
    @Override
    public List<CourseCustomizationCategory> selectCourseCustomizationCategoryList(CourseCustomizationCategory courseCustomizationCategory) {
        return courseCustomizationCategoryMapper.selectCourseCustomizationCategoryList(courseCustomizationCategory);
    }

    /**
     * 新增课程定制类别
     *
     * @param courseCustomizationCategory 课程定制类别
     * @return 结果
     */
    @Override
    public int insertCourseCustomizationCategory(CourseCustomizationCategory courseCustomizationCategory) {
        courseCustomizationCategory.setCreateTime(DateUtils.getNowDate());
        return courseCustomizationCategoryMapper.insertCourseCustomizationCategory(courseCustomizationCategory);
    }

    /**
     * 修改课程定制类别
     *
     * @param courseCustomizationCategory 课程定制类别
     * @return 结果
     */
    @Override
    public int updateCourseCustomizationCategory(CourseCustomizationCategory courseCustomizationCategory) {
        courseCustomizationCategory.setUpdateTime(DateUtils.getNowDate());
        return courseCustomizationCategoryMapper.updateCourseCustomizationCategory(courseCustomizationCategory);
    }

    /**
     * 删除课程定制类别对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteCourseCustomizationCategoryByIds(String ids) {
        return courseCustomizationCategoryMapper.deleteCourseCustomizationCategoryByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除课程定制类别信息
     *
     * @param courseCustomizationCategoryId 课程定制类别ID
     * @return 结果
     */
    @Override
    public int deleteCourseCustomizationCategoryById(Long courseCustomizationCategoryId) {
        return courseCustomizationCategoryMapper.deleteCourseCustomizationCategoryById(courseCustomizationCategoryId);
    }
}
