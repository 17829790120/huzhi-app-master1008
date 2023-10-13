package com.wlwq.api.mapper;

import com.wlwq.api.domain.Course;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 课程Mapper接口
 *
 * @author Rick Jen
 * @date 2021-01-13
 */
public interface CourseMapper {
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
     * 删除课程
     *
     * @param courseId 课程ID
     * @return 结果
     */
    public int deleteCourseById(Long courseId);

    /**
     * 批量删除课程
     *
     * @param courseIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteCourseByIds(String[] courseIds);

    /**
     * 查询首页课程列表
     *
     * @param course
     * @return
     */
    List<Map<String, Object>> selectCourseIndexList(Course course);

    /**
     * 答完题随机推荐一个课程
     * @param oneCategoryId
     * @param twoCategoryId
     * @return
     */
    Map<String, Object> recommendCourse(@Param("oneCategoryId") Long oneCategoryId, @Param("twoCategoryId") Long twoCategoryId, @Param("threeCategoryId") Long threeCategoryId);
    /**
     * 查询未观看课程
     * @param accountId
     * @return
     */
    List<Map<String, Object>> selectNoWatchStateList(@Param("accountId")String accountId,@Param("categoryId")Integer categoryId);
    /**
     * 查询观看中的课程
     * @param accountId
     * @return
     */
    List<Map<String, Object>> selectWatchIngStateList(@Param("accountId")String accountId,@Param("categoryId")Integer categoryId);
    /**
     * 查询已看完的课程
     * @param accountId
     * @return
     */
    List<Map<String, Object>> selectWatchFinishStateList(@Param("accountId")String accountId,@Param("categoryId")Integer categoryId);
    /**
     * 查询课程对应的章，节，学习人数
     * @param courseId
     * @return
     */
    HashMap<String, Long> selectCount(Long courseId);

    /**
     * 我的学习列表，查询观看中的课程
     * @param accountId 用户Id
     * @param keyword 关键字搜索
     * @return
     */
    public List<Map<String,Object>> selectMyWatchIngStateList(@Param("accountId") String accountId,@Param("keyword") String keyword);

    /**
     * 我的学习列表，查询观看中的课程数量
     * @param accountId
     * @param keyword
     * @return
     */
    public int selectMyWatchIngStateCount(@Param("accountId") String accountId,@Param("keyword") String keyword);
    /**
     * 查询总可数与已看完的课程，统计数
     * @param categoryId 类别id
     * @param accountId 用户id
     * @return
     */
    public Map<String, Object> selectWatchFinishCount(@Param("categoryId")Long categoryId,@Param("accountId") String accountId);
}
