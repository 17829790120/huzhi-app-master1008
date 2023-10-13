package com.wlwq.api.service;

import com.wlwq.api.domain.Course;
import com.wlwq.api.resultVO.course.CourseDetailResultVO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 课程Service接口
 *
 * @author Rick Jen
 * @date 2021-01-13
 */
public interface ICourseService {
    /**
     * 查询课程
     *
     * @param courseId 课程ID
     * @return 课程
     */
    public Course selectCourseById(Long courseId);

    /**
     * 查询课程列表
     *
     * @param course 课程
     * @return 课程集合
     */
    public List<Course> selectCourseList(Course course);

    /**
     * 新增课程
     *
     * @param course 课程
     * @return 结果
     */
    public int insertCourse(Course course);

    /**
     * 修改课程
     *
     * @param course 课程
     * @return 结果
     */
    public int updateCourse(Course course);

    /**
     * 修改课程的推荐和不推荐
     *
     * @param course 课程
     * @return 结果
     */
    public int recommendEdit(Course course);

    /**
     * 批量删除课程
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteCourseByIds(String ids);

    /**
     * 删除课程信息
     *
     * @param courseId 课程ID
     * @return 结果
     */
    public int deleteCourseById(Long courseId);

    /**
     * 查询首页课程列表
     *
     * @param course
     * @return
     */
    List<Map<String, Object>> selectCourseIndexList(Course course);

    /**
     * 根据课程ID获取课程详情
     *
     * @param courseId
     * @param accountId
     * @return
     */
    CourseDetailResultVO selectCourseByCourseId(Long courseId, Long accountId);

    /**
     * 答完题随机推荐一个课程
     *
     * @param oneCategoryId
     * @param twoCategoryId
     * @return
     */
    Map<String, Object> recommendCourse(Long oneCategoryId, Long twoCategoryId, Long threeCategoryId);
    /**
     * 查询未观看课程
     * @param accountId
     * @return
     */
    List<Map<String, Object>> selectNoWatchStateList(String accountId,Integer categoryId);
    /**
     * 查询观看中的课程
     * @param accountId
     * @return
     */
    List<Map<String, Object>> selectWatchIngStateList(String accountId,Integer categoryId);
    /**
     * 查询已看完的课程
     * @param accountId
     * @return
     */
    List<Map<String, Object>> selectWatchFinishStateList(String accountId,Integer categoryId);
    /**
     * 查询课程对应的章，节，学习人数
     * @param courseId
     * @return
     */
    HashMap<String, Long> selectCount(Long courseId);

    /**
     * 我的学习列表，查询观看中的课程
     * @param accountId
     * @return
     */
    public List<Map<String,Object>> selectMyWatchIngStateList(String accountId,String keyword);


    /**
     * 我的学习列表，查询观看中的课程数量
     * @param accountId
     * @param keyword
     * @return
     */
    public int selectMyWatchIngStateCount(String accountId,String keyword);

    /**
     * 查询总可数与已看完的课程，统计数
     * @param categoryId 类别id
     * @param accountId 用户id
     * @return
     */
    Map<String, Object> selectWatchFinishCount(Long categoryId, String accountId);
}
