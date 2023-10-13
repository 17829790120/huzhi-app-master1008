package com.wlwq.controller.api;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wlwq.annotation.PassToken;
import com.wlwq.api.domain.AccountScore;
import com.wlwq.api.domain.ApiAccount;
import com.wlwq.api.domain.Course;
import com.wlwq.api.service.IAccountScoreService;
import com.wlwq.api.service.IApiAccountService;
import com.wlwq.api.service.ICourseService;
import com.wlwq.common.apiResult.ApiCode;
import com.wlwq.common.apiResult.ApiController;
import com.wlwq.common.apiResult.ApiResult;
import com.wlwq.common.apiResult.PageParam;
import com.wlwq.service.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wwb
 * 自己与自己的关系--课程
 */
@RestController
@RequestMapping(value = "/api/course")
@AllArgsConstructor
public class CourseApiController extends ApiController {

    private final ICourseService courseService;

    private final TokenService tokenService;

    private final IApiAccountService accountService;

    private final IAccountScoreService accountScoreService;

    /**
     * 课程列表
     *
     * @param keyword 关键字搜索
     * @return
     */
    //@PassToken
    @GetMapping(value = "/list")
    public ApiResult list(HttpServletRequest request,PageParam pageParam, String keyword, @RequestParam(defaultValue = "") Long categoryId) {
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (pageParam == null) {
            pageParam = PageParam.builder().build();
        }
        PageHelper.startPage(pageParam.getPageNo(), pageParam.getPageSize());
        List<Course> list = courseService.selectCourseList(Course
                .builder()
                .categoryId(categoryId)
                .courseTitle(keyword)
                .build());
        PageInfo<Course> pageInfo = new PageInfo<>(list);
        list.forEach(
                obj -> {
                    HashMap<String, Long> map = courseService.selectCount(obj.getCourseId());
                    obj.setZhangCount(map.get("zhangCount"));
                    obj.setJieCount(map.get("jieCount"));
                    obj.setLearnersCount(map.get("learnersCount"));
                    //获取课程我获取的积分
                    Integer integral = accountScoreService.selectCourseScore(AccountScore
                            .builder()
                            .courseId(obj.getCourseId())
                            .accountId(account != null ? account.getAccountId() : null)
                            .build());
                    obj.setIntegral(integral);
                }
        );
        return ok(pageInfo);
    }

    /**
     * 课程详情
     *
     * @param courseId  课程ID
     * @return
     */
    @PassToken
    @GetMapping(value = "/details")
    public ApiResult details(@RequestParam(defaultValue = "-1") Long courseId) {
        Course course = courseService.selectCourseById(courseId);
        if(course != null){
            HashMap<String, Long> map = courseService.selectCount(course.getCourseId());
            course.setZhangCount(map.get("zhangCount"));
            course.setJieCount(map.get("jieCount"));
            course.setLearnersCount(map.get("learnersCount"));
        }
        return ok(course);
    }

    /**
     * 课程观看列表
     *
     * @param watchState:观看状态：0：未观看，1：观看中，2：已完成
     * @return
     */
    //@PassToken
    @GetMapping(value = "/watchList")
    public ApiResult watchList(HttpServletRequest request, PageParam pageParam,
                               @RequestParam(defaultValue = "0") Integer watchState,
                               @RequestParam(defaultValue = "0") Integer categoryId) {
        if (pageParam == null) {
            pageParam = PageParam.builder().build();
        }
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return fail(ApiCode.DONT_LOGIN.getMsg());
        }

        if (watchState == 0) {
            PageHelper.startPage(pageParam.getPageNo(), pageParam.getPageSize());
            List<Map<String, Object>> list = courseService.selectNoWatchStateList(account.getAccountId(), categoryId);
            PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list);
            return ok(pageInfo);
        }
        if (watchState == 1) {
            PageHelper.startPage(pageParam.getPageNo(), pageParam.getPageSize());
            List<Map<String, Object>> list = courseService.selectWatchIngStateList(account.getAccountId(), categoryId);
            PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list);
            return ok(pageInfo);
        }
        if (watchState == 2) {
            PageHelper.startPage(pageParam.getPageNo(), pageParam.getPageSize());
            List<Map<String, Object>> list = courseService.selectWatchFinishStateList(account.getAccountId(), categoryId);
            PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list);
            return ok(pageInfo);
        }
        return ok(null);
    }

    /**
     * 我的学习列表
     * keyword 关键字搜索
     * @return
     */
    @PassToken
    @GetMapping(value = "/myWatchList")
    public ApiResult watchList(HttpServletRequest request,String keyword, PageParam pageParam) {
        if (pageParam == null) {
            pageParam = PageParam.builder().build();
        }
        PageHelper.startPage(pageParam.getPageNo(), pageParam.getPageSize());
        // 查询列表
        List<Map<String, Object>> list = courseService.selectMyWatchIngStateList(tokenService.getNoAccountId(request),keyword);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list);
        Map<String,Object> map = new HashMap<>(5);
        map.put("pageInfo",pageInfo);
        // 列表数量
        map.put("totalCount",courseService.selectMyWatchIngStateCount(tokenService.getNoAccountId(request),keyword));
        list.forEach(
                obj -> {
                    HashMap<String, Long> maps = courseService.selectCount(Long.valueOf(obj.get("courseId").toString()));
                    obj.put("zhangCount",maps.get("zhangCount"));
                    obj.put("jieCount",maps.get("jieCount"));
                    obj.put("learnersCount",maps.get("learnersCount"));
                    //获取课程我获取的积分
                    Integer integral = accountScoreService.selectCourseScore(AccountScore
                            .builder()
                            .courseId(Long.valueOf(obj.get("courseId").toString()))
                            .accountId(tokenService.getNoAccountId(request))
                            .build());
                    obj.put("integral",integral);
                }
        );
        return ok(map);
    }
}
