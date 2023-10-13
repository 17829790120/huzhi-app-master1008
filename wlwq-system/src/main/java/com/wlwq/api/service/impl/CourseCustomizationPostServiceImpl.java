package com.wlwq.api.service.impl;

import java.util.List;

import cn.hutool.core.util.IdUtil;
import com.wlwq.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.api.mapper.CourseCustomizationPostMapper;
import com.wlwq.api.domain.CourseCustomizationPost;
import com.wlwq.api.service.ICourseCustomizationPostService;
import com.wlwq.common.core.text.Convert;

/**
 * 课程定制资讯Service业务层处理
 *
 * @author wwb
 * @date 2023-05-19
 */
@Service
public class CourseCustomizationPostServiceImpl implements ICourseCustomizationPostService {

    @Autowired
    private CourseCustomizationPostMapper courseCustomizationPostMapper;

    /**
     * 查询课程定制资讯
     *
     * @param courseCustomizationPostId 课程定制资讯ID
     * @return 课程定制资讯
     */
    @Override
    public CourseCustomizationPost selectCourseCustomizationPostById(String courseCustomizationPostId) {
        return courseCustomizationPostMapper.selectCourseCustomizationPostById(courseCustomizationPostId);
    }

    /**
     * 查询课程定制资讯列表
     *
     * @param courseCustomizationPost 课程定制资讯
     * @return 课程定制资讯
     */
    @Override
    public List<CourseCustomizationPost> selectCourseCustomizationPostList(CourseCustomizationPost courseCustomizationPost) {
        return courseCustomizationPostMapper.selectCourseCustomizationPostList(courseCustomizationPost);
    }

    /**
     * 新增课程定制资讯
     *
     * @param courseCustomizationPost 课程定制资讯
     * @return 结果
     */
    @Override
    public int insertCourseCustomizationPost(CourseCustomizationPost courseCustomizationPost) {
        courseCustomizationPost.setCourseCustomizationPostId(IdUtil.getSnowflake(1, 1).nextIdStr());
        courseCustomizationPost.setCreateTime(DateUtils.getNowDate());
        return courseCustomizationPostMapper.insertCourseCustomizationPost(courseCustomizationPost);
    }

    /**
     * 修改课程定制资讯
     *
     * @param courseCustomizationPost 课程定制资讯
     * @return 结果
     */
    @Override
    public int updateCourseCustomizationPost(CourseCustomizationPost courseCustomizationPost) {
        courseCustomizationPost.setUpdateTime(DateUtils.getNowDate());
        return courseCustomizationPostMapper.updateCourseCustomizationPost(courseCustomizationPost);
    }

    /**
     * 删除课程定制资讯对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteCourseCustomizationPostByIds(String ids) {
        return courseCustomizationPostMapper.deleteCourseCustomizationPostByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除课程定制资讯信息
     *
     * @param courseCustomizationPostId 课程定制资讯ID
     * @return 结果
     */
    @Override
    public int deleteCourseCustomizationPostById(String courseCustomizationPostId) {
        return courseCustomizationPostMapper.deleteCourseCustomizationPostById(courseCustomizationPostId);
    }
}
