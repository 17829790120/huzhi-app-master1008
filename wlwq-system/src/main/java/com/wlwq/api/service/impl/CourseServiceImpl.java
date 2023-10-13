package com.wlwq.api.service.impl;

import com.wlwq.api.domain.Categorys;
import com.wlwq.api.domain.Course;
import com.wlwq.api.mapper.CategoryMapper;
import com.wlwq.api.mapper.CourseMapper;
import com.wlwq.api.resultVO.course.CourseDetailResultVO;
import com.wlwq.api.service.ICourseService;
import com.wlwq.common.core.text.Convert;
import com.wlwq.common.exception.ApiException;
import com.wlwq.common.exception.BusinessException;
import com.wlwq.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 课程Service业务层处理
 *
 * @author Rick Jen
 * @date 2021-01-13
 */
@Service
public class CourseServiceImpl implements ICourseService {
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private CategoryMapper categoryMapper;
//    @Autowired
//    private CourseChapterMapper chapterMapper;
//    @Autowired
//    private CourseCollectMapper collectMapper;
//    @Autowired
//    private CourseCommentMapper commentMapper;
//    @Autowired
//    private AccountCourseMapper accountCourseMapper;

    /**
     * 查询课程
     *
     * @param courseId 课程ID
     * @return 课程
     */
    @Override
    public Course selectCourseById(Long courseId) {
        return courseMapper.selectCourseById(courseId);
    }

    /**
     * 查询课程列表
     *
     * @param course 课程
     * @return 课程
     */
    @Override
    public List<Course> selectCourseList(Course course) {
        return courseMapper.selectCourseList(course);
    }

    /**
     * 新增课程
     *
     * @param course 课程
     * @return 结果
     */
    @Override
    public int insertCourse(Course course) {
/*        Categorys categorys = getCategorys(course.getThreeCategoryId());
        course.setOneCategoryId(getCategorys(categorys.getParentId()) == null ? null : getCategorys(categorys.getParentId()).getParentId());
        course.setTwoCategoryId(categorys.getParentId());
        course.setThreeCategoryId(course.getThreeCategoryId());
        course.setCreateTime(DateUtils.getNowDate());
        return courseMapper.insertCourse(course);*/
        Categorys categorys = getCategorys(course.getTwoCategoryId());
        course.setOneCategoryId(categorys.getParentId());
        course.setTwoCategoryId(course.getTwoCategoryId());
        course.setCreateTime(DateUtils.getNowDate());
        return courseMapper.insertCourse(course);
    }

    /**
     * 修改课程
     *
     * @param course 课程
     * @return 结果
     */
    @Override
    public int updateCourse(Course course) {
/*        Categorys categorys = getCategorys(course.getThreeCategoryId());
        course.setOneCategoryId(getCategorys(categorys.getParentId()) == null ? null : getCategorys(categorys.getParentId()).getParentId());
        course.setTwoCategoryId(categorys.getParentId());
        course.setThreeCategoryId(course.getThreeCategoryId());
        return courseMapper.updateCourse(course);*/
        Categorys categorys = getCategorys(course.getTwoCategoryId());
        course.setOneCategoryId(categorys.getParentId());
        course.setTwoCategoryId(course.getThreeCategoryId());
        return courseMapper.updateCourse(course);
    }

    /**
     * 修改课程的推荐和不推荐
     *
     * @param course 课程
     * @return 结果
     */
    @Override
    public int recommendEdit(Course course) {
        return courseMapper.updateCourse(course);
    }

    /**
     * 根据分类id获取分类信息
     *
     * @param categoryId
     * @return
     */
    private Categorys getCategorys(Long categoryId) {
        Categorys categorys = categoryMapper.selectCategoryById(categoryId);
        if (categorys == null) {
            throw new BusinessException("未获取到分类信息！");
        }
        if (categorys.getParentId() == 0) {
            throw new BusinessException("请选择子分类！");
        }
/*        if (categorys.getLevel() == 1) {
            throw new BusinessException("请选择子分类！");
        }*/
        return categorys;
    }

    /**
     * 删除课程对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteCourseByIds(String ids) {
        return courseMapper.deleteCourseByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除课程信息
     *
     * @param courseId 课程ID
     * @return 结果
     */
    @Override
    public int deleteCourseById(Long courseId) {
        return courseMapper.deleteCourseById(courseId);
    }

    /**
     * 查询首页课程列表
     *
     * @param course
     * @return
     */
    @Override
    public List<Map<String, Object>> selectCourseIndexList(Course course) {
        return courseMapper.selectCourseIndexList(course);
    }

    /**
     * 根据课程ID获取课程详情
     *
     * @param courseId
     * @param accountId
     * @return
     */
    @Override
    public CourseDetailResultVO selectCourseByCourseId(Long courseId, Long accountId) {
/*        Course course = courseMapper.selectCourseById(courseId);
        if (course == null) {
            throw new ApiException("该课程已下架！");
        }
        //TODO 分享链接
        String shareUrl = "http://barns1.com";
        // 购买人数 直接写到build里面
        Integer buyNumber = accountCourseMapper.countByCourseId(courseId);
        // 收藏标识 true已收藏false为收藏
        boolean collectFlag = false;
        // 是否已评论 true已评论false未评论
        boolean commentStatus = false;
        // 购买状态 0未购买过1已购买2已购买但过期
        Integer buyStatus = 0;
        if (accountId != 0) {
            // 查询用户是否已收藏该课程
            CourseCollect collect = collectMapper.selectCourseCollectByCourseIdAndAccountId(courseId, accountId);
            if (collect != null) {
                collectFlag = true;
            }
            // 查询用户是否已评论该课程
            CourseComment comment = commentMapper.selectCourseCommentByCourseIdAndAccountId(courseId, accountId);
            if (comment != null) {
                commentStatus = true;
            }
            if (accountId != 0L) {
                // 查询用户是否已购买该课程
                AccountCourse accountCourse = accountCourseMapper.selectLastAccountCourseByAccountIdAndCourseId(accountId, courseId);
                if (accountCourse != null) {
                    if (accountCourse.getValidStatus() == 1) {
                        buyStatus = 1;
                    } else {
                        buyStatus = 2;
                    }
                }
            }
        }
        // 查询课程第一节课信息
        CourseChapter chapter = chapterMapper.selectFirstChapterByCourseId(courseId);
        return CourseDetailResultVO.builder()
                .courseId(courseId)
                .videoImage(chapter == null ? course.getVideoImage() : StringUtils.isBlank(chapter.getVideoUrl()) ? course.getVideoImage() : QiNiuVideoDetailUtils.getVideoPic(chapter.getVideoUrl(), 1.00))
                .videoUrl(chapter == null ? "" : StringUtils.isBlank(chapter.getVideoUrl()) ? "" : chapter.getVideoUrl())
                .coursePrice(course.getCoursePrice())
                .applePrice(course.getApplePriceMoney())
                .courseTitle(course.getCourseTitle())
                .briefTitle(course.getBriefTitle())
                .courseContent(course.getCourseDetail())
                .collectFlag(collectFlag)
                .shareUrl(shareUrl)
                .buyNumber(buyNumber)
                .teacherWechatQRCode(course.getTeacherWechatImage())
                .teacherWechatNumber(course.getTeacherWechatNumber())
                .buyStatus(buyStatus)
                .commentStatus(commentStatus)
                .tryWatchFlag(chapter == null ? false : chapter.getTryStatus() == 1 ? true : false)
                .build();*/
        return null;
    }


    /**
     * 答完题随机推荐一个课程
     *
     * @param oneCategoryId
     * @param twoCategoryId
     * @return
     */
    @Override
    public Map<String, Object> recommendCourse(Long oneCategoryId, Long twoCategoryId, Long threeCategoryId) {
        return courseMapper.recommendCourse(oneCategoryId, twoCategoryId, threeCategoryId);
    }
    /**
     * 查询未观看课程
     * @param accountId
     * @return
     */
    @Override
    public List<Map<String, Object>> selectNoWatchStateList(String accountId,Integer categoryId) {
        return courseMapper.selectNoWatchStateList(accountId,categoryId);
    }
    /**
     * 查询观看中的课程
     * @param accountId
     * @return
     */
    @Override
    public List<Map<String, Object>> selectWatchIngStateList(String accountId,Integer categoryId) {
        return courseMapper.selectWatchIngStateList(accountId,categoryId);
    }
    /**
     * 查询已看完的课程
     * @param accountId
     * @return
     */
    @Override
    public List<Map<String, Object>> selectWatchFinishStateList(String accountId,Integer categoryId) {
        return courseMapper.selectWatchFinishStateList(accountId,categoryId);
    }
    /**
     * 查询课程对应的章，节，学习人数
     * @param courseId
     * @return
     */
    @Override
    public HashMap<String, Long> selectCount(Long courseId) {
        return courseMapper.selectCount(courseId);
    }

    /**
     * 我的学习列表，查询观看中的课程
     * @param accountId
     * @return
     */
    @Override
    public List<Map<String,Object>> selectMyWatchIngStateList(String accountId,String keyword){
        return courseMapper.selectMyWatchIngStateList(accountId,keyword);
    }

    /**
     * 我的学习列表，查询观看中的课程数量
     * @param accountId
     * @param keyword
     * @return
     */
    @Override
    public int selectMyWatchIngStateCount(String accountId,String keyword){
        return courseMapper.selectMyWatchIngStateCount(accountId,keyword);
    }
    /**
     * 查询总可数与已看完的课程，统计数
     * @param categoryId 类别id
     * @param accountId 用户id
     * @return
     */
    @Override
    public Map<String, Object> selectWatchFinishCount(Long categoryId, String accountId) {
        return courseMapper.selectWatchFinishCount(categoryId,accountId);
    }

}
