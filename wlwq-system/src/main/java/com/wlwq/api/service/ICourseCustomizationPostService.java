package com.wlwq.api.service;

import java.util.List;

import com.wlwq.api.domain.CourseCustomizationPost;

/**
 * 课程定制资讯Service接口
 *
 * @author wwb
 * @date 2023-05-19
 */
public interface ICourseCustomizationPostService {
    /**
     * 查询课程定制资讯
     *
     * @param courseCustomizationPostId 课程定制资讯ID
     * @return 课程定制资讯
     */
    public CourseCustomizationPost selectCourseCustomizationPostById(String courseCustomizationPostId);

    /**
     * 查询课程定制资讯列表
     *
     * @param courseCustomizationPost 课程定制资讯
     * @return 课程定制资讯集合
     */
    public List<CourseCustomizationPost> selectCourseCustomizationPostList(CourseCustomizationPost courseCustomizationPost);

    /**
     * 新增课程定制资讯
     *
     * @param courseCustomizationPost 课程定制资讯
     * @return 结果
     */
    public int insertCourseCustomizationPost(CourseCustomizationPost courseCustomizationPost);

    /**
     * 修改课程定制资讯
     *
     * @param courseCustomizationPost 课程定制资讯
     * @return 结果
     */
    public int updateCourseCustomizationPost(CourseCustomizationPost courseCustomizationPost);

    /**
     * 批量删除课程定制资讯
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteCourseCustomizationPostByIds(String ids);

    /**
     * 删除课程定制资讯信息
     *
     * @param courseCustomizationPostId 课程定制资讯ID
     * @return 结果
     */
    public int deleteCourseCustomizationPostById(String courseCustomizationPostId);
}
