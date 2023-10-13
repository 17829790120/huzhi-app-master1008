package com.wlwq.api.service.impl;

import com.wlwq.api.domain.AccountScore;
import com.wlwq.api.domain.CourseChapter;
import com.wlwq.api.mapper.CourseChapterMapper;
import com.wlwq.api.resultVO.course.ChapterDetailResultVO;
import com.wlwq.api.resultVO.course.ChapterParentResultVO;
import com.wlwq.api.resultVO.course.ChapterSonResultVO;
import com.wlwq.api.service.ICourseChapterService;
import com.wlwq.common.core.domain.Ztree;
import com.wlwq.common.core.text.Convert;
import com.wlwq.common.exception.ApiException;
import com.wlwq.common.utils.DateUtils;
import com.wlwq.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 课程章节Service业务层处理
 *
 * @author Rick Jen
 * @date 2021-01-18
 */
@Service
public class CourseChapterServiceImpl implements ICourseChapterService {
    @Autowired
    private CourseChapterMapper courseChapterMapper;

//    @Autowired
//    private AccountCourseMapper accountCourseMapper;

    /**
     * 查询课程章节
     *
     * @param chapterId 课程章节ID
     * @return 课程章节
     */
    @Override
    public CourseChapter selectCourseChapterById(Long chapterId) {
        return courseChapterMapper.selectCourseChapterById(chapterId);
    }

    /**
     * 查询课程章节列表
     *
     * @param courseChapter 课程章节
     * @return 课程章节
     */
    @Override
    public List<CourseChapter> selectCourseChapterList(CourseChapter courseChapter) {
        return courseChapterMapper.selectCourseChapterList(courseChapter);
    }

    /**
     * 新增课程章节
     *
     * @param courseChapter 课程章节
     * @return 结果
     */
    @Override
    public int insertCourseChapter(CourseChapter courseChapter) {
        courseChapter.setCreateTime(DateUtils.getNowDate());
        return courseChapterMapper.insertCourseChapter(courseChapter);
    }

    /**
     * 修改课程章节
     *
     * @param courseChapter 课程章节
     * @return 结果
     */
    @Override
    public int updateCourseChapter(CourseChapter courseChapter) {
        return courseChapterMapper.updateCourseChapter(courseChapter);
    }

    /**
     * 删除课程章节对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteCourseChapterByIds(String ids) {
        return courseChapterMapper.deleteCourseChapterByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除课程章节信息
     *
     * @param chapterId 课程章节ID
     * @return 结果
     */
    @Override
    public int deleteCourseChapterById(Long chapterId) {
        return courseChapterMapper.deleteCourseChapterById(chapterId);
    }

    /**
     * 查询课程章节树列表
     *
     * @return 所有课程章节信息
     */
    @Override
    public List<Ztree> selectCourseChapterTree(CourseChapter chapter) {
        List<CourseChapter> courseChapterList = courseChapterMapper.selectCourseChapterList(chapter);
        List<Ztree> ztrees = new ArrayList<Ztree>();
        for (CourseChapter courseChapter : courseChapterList) {
            Ztree ztree = new Ztree();
            ztree.setId(courseChapter.getChapterId());
            ztree.setpId(courseChapter.getParentId());
            ztree.setName(courseChapter.getChapterName());
            ztree.setTitle(courseChapter.getChapterName());
            ztrees.add(ztree);
        }
        return ztrees;
    }


//    /**
//     * 获取课程的章节集合
//     * @param courseId
//     * @param accountId
//     * @return
//     */
//    @Override
//    public List<ChapterParentResultVO> selectChapterListByCourseId(Long courseId, Long accountId) {
//        // 查询课程所有的父章节集合
//        List<CourseChapter> parentList = courseChapterMapper.selectCourseChapterList(CourseChapter.builder()
//                .parentId(0L)
//                .courseId(courseId)
//                .build());
//        List<ChapterParentResultVO> chapterParentResultVOList = new ArrayList<>();
//        for (CourseChapter chapterParent : parentList) {
//            // 获取子集集合
//            List<CourseChapter> sonList = courseChapterMapper.selectCourseChapterList(CourseChapter.builder()
//                    .parentId(chapterParent.getChapterId())
//                    .courseId(courseId)
//                    .build());
//            List<ChapterSonResultVO> chapterSonResultVOList = new ArrayList<>();
//            Integer totalSonMinuteNumber = 0;
//            for (CourseChapter chapterSon : sonList) {
//                Integer chapterSonMinuteNumber = (int)Math.ceil(chapterSon.getTotalTime()) / 60;
//                totalSonMinuteNumber += chapterSonMinuteNumber;
//                String chapterVideoUrl = "";
//                String chapterVideoImage = "";
//                Integer watchStatus = 0;
//                if (StringUtils.isNotBlank(chapterSon.getVideoUrl())){
//                    chapterVideoUrl = chapterSon.getVideoUrl();
//                    chapterVideoImage = chapterSon.getVideoUrl() + "?vframe/jpg/offset/2";
//                }
//                // 若为试看
//                if (chapterSon.getTryStatus() == 1){
//                    watchStatus = 1;
//                }
//                // 若不为试看
//                else{
//                    if (accountId != 0L){
//                        AccountCourse accountCourse = accountCourseMapper.selectLastAccountCourseByAccountIdAndCourseId(accountId,courseId);
//                        // 用户是否已经购买过此课程
//                        if (accountCourse != null){
//                            // 若课程不在有效状态 链接置空
//                            if (accountCourse.getValidStatus() == 0){
//                                chapterVideoUrl = "";
//                            }
//                        }else {
//                            // 若已到期 chapterVideoUrl置为空
//                            chapterVideoUrl = "";
//                        }
//                        // 若未购买 chapterVideoUrl置为空
//                    }else {
//                        chapterVideoUrl = "";
//                    }
//                }
//                chapterSonResultVOList.add(ChapterSonResultVO.builder()
//                        .chapterSonId(chapterSon.getChapterId())
//                        .chapterSonName(chapterSon.getChapterName())
//                        .totalMinuteNumber(Convert.toStr(chapterSonMinuteNumber))
//                        .tryStatus(chapterSon.getTryStatus())
//                        .chapterVideoImage(chapterVideoImage)
//                        .chapterVideoUrl(chapterVideoUrl)
//                        .watchStatus(watchStatus)
//                        .build());
//            }
//            chapterParentResultVOList.add(ChapterParentResultVO.builder()
//                    .chapterParentId(chapterParent.getChapterId())
//                    .chapterParentName(chapterParent.getChapterName())
//                    .totalChapterSonNumber(sonList == null ? 0 : sonList.size())
//                    .totalMinuteNumber(Convert.toStr(totalSonMinuteNumber))
//                    .chapterSonResultVOList(chapterSonResultVOList)
//                    .build());
//        }
//        return chapterParentResultVOList;
//    }

    /**
     * 获取课程的章节集合
     *
     * @param courseId
     * @param accountId
     * @return
     */
    @Override
    public List<ChapterParentResultVO> selectChapterListByCourseId(Long courseId, Long accountId) {
        // 查询课程所有的父章节集合
        List<CourseChapter> parentList = courseChapterMapper.selectCourseChapterList(CourseChapter.builder()
                .parentId(0L)
                .courseId(courseId)
                .build());
        List<ChapterParentResultVO> chapterParentResultVOList = new ArrayList<>();
        for (CourseChapter chapterParent : parentList) {
            // 获取子集集合
            List<CourseChapter> sonList = courseChapterMapper.selectCourseChapterList(CourseChapter.builder()
                    .parentId(chapterParent.getChapterId())
                    .courseId(courseId)
                    .build());
            List<ChapterSonResultVO> chapterSonResultVOList = new ArrayList<>();
            double totalSonMinuteNumber = 0;
            for (CourseChapter chapterSon : sonList) {
//                Integer chapterSonMinuteNumber = (int)Math.ceil(chapterSon.getTotalTime()) / 60;
                double chapterSonMinuteNumber = chapterSon.getTotalTime();
                totalSonMinuteNumber += chapterSonMinuteNumber;
                String chapterVideoUrl = "";
                String chapterVideoImage = "";
                Integer watchStatus = 0;
                if (StringUtils.isNotBlank(chapterSon.getVideoUrl())) {
                    chapterVideoUrl = chapterSon.getVideoUrl();
                    chapterVideoImage = chapterSon.getVideoUrl() + "?vframe/jpg/offset/2";
                }
                // 若为试看
                if (chapterSon.getTryStatus() == 1) {
                    watchStatus = 1;
                }
                // 若不为试看
/*                else{
                    if (accountId != 0L){
                        AccountCourse accountCourse = accountCourseMapper.selectLastAccountCourseByAccountIdAndCourseId(accountId,courseId);
                        // 用户是否已经购买过此课程
                        if (accountCourse != null){
                            // 若课程不在有效状态 链接置空
                            if (accountCourse.getValidStatus() == 0){
                                chapterVideoUrl = "";
                            }
                        }else {
                            // 若已到期 chapterVideoUrl置为空
                            chapterVideoUrl = "";
                        }
                        // 若未购买 chapterVideoUrl置为空
                    }else {
                        chapterVideoUrl = "";
                    }
                }*/
                chapterSonResultVOList.add(ChapterSonResultVO.builder()
                        .chapterSonId(chapterSon.getChapterId())
                        .chapterSonName(chapterSon.getChapterName())
                        //.totalMinuteNumber(ChangeTimeFormatUtil.changeTimeFormat(chapterSonMinuteNumber))
                        .tryStatus(chapterSon.getTryStatus())
                        .chapterVideoImage(chapterVideoImage)
                        .chapterVideoUrl(chapterVideoUrl)
                        .watchStatus(watchStatus)
                        .build());
            }
            chapterParentResultVOList.add(ChapterParentResultVO.builder()
                    .chapterParentId(chapterParent.getChapterId())
                    .chapterParentName(chapterParent.getChapterName())
                    .totalChapterSonNumber(sonList == null ? 0 : sonList.size())
                    //.totalMinuteNumber(ChangeTimeFormatUtil.changeTimeFormat(totalSonMinuteNumber))
                    .chapterSonResultVOList(chapterSonResultVOList)
                    .build());
        }
        return chapterParentResultVOList;
    }


    /**
     * 获取章节详情
     *
     * @param chapterSonId
     * @return
     */
    @Override
    @Deprecated
    public ChapterDetailResultVO selectChapterDetailById(Long chapterSonId) {
        CourseChapter chapter = courseChapterMapper.selectCourseChapterById(chapterSonId);
        if (chapter == null) {
            throw new ApiException("章节信息不存在！");
        }
        String chapterVideoUrl = "";
        String chapterVideoImage = "";
        Integer watchStatus = 0;
        if (StringUtils.isNotBlank(chapter.getVideoUrl())) {
            chapterVideoUrl = chapter.getVideoUrl();
            chapterVideoImage = chapter.getVideoUrl() + "?vframe/jpg/offset/2";
        }
        // 若为试看
        if (chapter.getTryStatus() == 1) {
            watchStatus = 1;
        }
        // 若不为试看
        else {
            // 获取用户是否已经购买过此课程
            // 若未购买或已到期 chapterVideoUrl置为空
        }
        return ChapterDetailResultVO.builder()
                .chapterId(chapter.getChapterId())
                .chapterName(chapter.getChapterName())
                .chapterVideoImage(chapterVideoImage)
                .chapterVideoUrl(chapterVideoUrl)
                .watchStatus(watchStatus)
                .build();
    }

    /**
     * 查询章节观看状态
     *
     * @param courseId
     * @param accountId
     * @return
     */
    @Override
    public HashMap<String, Long> selectCourseChapterWatch(Long courseId, String accountId) {
        return courseChapterMapper.selectCourseChapterWatch(courseId, accountId);
    }

    /**
     * 查询章节观看状态,根据章id进行查询
     *
     * @param chapterId ：章节id
     * @param accountId
     * @return
     */
    @Override
    public HashMap<String, Long> selectCourseChapterWatchByChapterId(Long chapterId, String accountId) {
        return courseChapterMapper.selectCourseChapterWatchByChapterId(chapterId, accountId);
    }

}
