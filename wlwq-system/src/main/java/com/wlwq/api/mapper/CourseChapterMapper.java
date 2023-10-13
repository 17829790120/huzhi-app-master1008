package com.wlwq.api.mapper;

import com.wlwq.api.domain.AccountScore;
import com.wlwq.api.domain.CourseChapter;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * 课程章节Mapper接口
 *
 * @author Rick Jen
 * @date 2021-01-18
 */
public interface CourseChapterMapper {
    /**
     * 查询课程章节
     *
     * @param chapterId 课程章节ID
     * @return 课程章节
     */
    public CourseChapter selectCourseChapterById(Long chapterId);

    /**
     * 查询课程章节列表
     *
     * @param courseChapter 课程章节
     * @return 课程章节集合
     */
    public List<CourseChapter> selectCourseChapterList(CourseChapter courseChapter);

    /**
     * 新增课程章节
     *
     * @param courseChapter 课程章节
     * @return 结果
     */
    public int insertCourseChapter(CourseChapter courseChapter);

    /**
     * 修改课程章节
     *
     * @param courseChapter 课程章节
     * @return 结果
     */
    public int updateCourseChapter(CourseChapter courseChapter);

    /**
     * 删除课程章节
     *
     * @param chapterId 课程章节ID
     * @return 结果
     */
    public int deleteCourseChapterById(Long chapterId);

    /**
     * 批量删除课程章节
     *
     * @param chapterIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteCourseChapterByIds(String[] chapterIds);

    /**
     * 查询该课程第一节信息
     * @param courseId
     * @return
     */
    CourseChapter selectFirstChapterByCourseId(@Param("courseId") Long courseId);
    /**
     * 查询章节观看状态
     * @param courseId
     * @param accountId
     * @return
     */
    HashMap<String, Long> selectCourseChapterWatch(@Param("courseId")Long courseId,@Param("accountId") String accountId);
    /**
     * 查询章节观看状态,根据章id进行查询
     * @param chapterId ：章节id
     * @param accountId
     * @return
     */
    HashMap<String, Long> selectCourseChapterWatchByChapterId(@Param("chapterId")Long chapterId,@Param("accountId") String accountId);



}
