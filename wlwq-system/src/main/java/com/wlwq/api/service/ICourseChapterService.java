package com.wlwq.api.service;

import com.wlwq.api.domain.AccountScore;
import com.wlwq.api.domain.CourseChapter;
import com.wlwq.api.resultVO.course.ChapterDetailResultVO;
import com.wlwq.api.resultVO.course.ChapterParentResultVO;
import com.wlwq.common.core.domain.Ztree;

import java.util.HashMap;
import java.util.List;

/**
 * 课程章节Service接口
 *
 * @author Rick Jen
 * @date 2021-01-18
 */
public interface ICourseChapterService
{
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
     * 批量删除课程章节
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteCourseChapterByIds(String ids);

    /**
     * 删除课程章节信息
     *
     * @param chapterId 课程章节ID
     * @return 结果
     */
    public int deleteCourseChapterById(Long chapterId);

    /**
     * 查询课程章节树列表
     *
     * @return 所有课程章节信息
     */
    public List<Ztree> selectCourseChapterTree(CourseChapter courseChapter);

    /**
     * 获取课程的章节集合
     * @param courseId
     * @param accountId
     * @return
     */
    List<ChapterParentResultVO> selectChapterListByCourseId(Long courseId, Long accountId);

    /**
     * 获取章节详情
     * @param chapterSonId
     * @return
     */
    ChapterDetailResultVO selectChapterDetailById(Long chapterSonId);
    /**
     * 查询章节观看状态
     * @param courseId ：课程id
     * @param accountId
     * @return
     */
    HashMap<String, Long> selectCourseChapterWatch(Long courseId, String accountId);
    /**
     * 查询章节观看状态,根据章id进行查询
     * @param chapterId ：章节id
     * @param accountId
     * @return
     */
    HashMap<String, Long> selectCourseChapterWatchByChapterId(Long chapterId, String accountId);



}
