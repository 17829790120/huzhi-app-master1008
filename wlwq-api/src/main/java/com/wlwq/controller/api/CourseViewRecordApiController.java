package com.wlwq.controller.api;

import cn.hutool.core.bean.BeanUtil;
import com.wlwq.api.domain.ApiAccount;
import com.wlwq.api.domain.Course;
import com.wlwq.api.domain.CourseChapter;
import com.wlwq.api.domain.CourseViewRecord;
import com.wlwq.api.service.IApiAccountService;
import com.wlwq.api.service.ICourseChapterService;
import com.wlwq.api.service.ICourseService;
import com.wlwq.api.service.ICourseViewRecordService;
import com.wlwq.common.apiResult.ApiCode;
import com.wlwq.common.apiResult.ApiController;
import com.wlwq.common.apiResult.ApiResult;
import com.wlwq.common.exception.ApiException;
import com.wlwq.params.courseExam.CourseViewRecordParam;
import com.wlwq.service.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * @author wwb
 * @课程章节观看记录
 */
@RestController
@RequestMapping(value = "/api/courseViewRecord")
@AllArgsConstructor
public class CourseViewRecordApiController extends ApiController {

    private final ICourseViewRecordService courseViewRecordService;

    private final TokenService tokenService;

    private final IApiAccountService accountService;

    private final ICourseChapterService courseChapterService;

    private final ICourseService courseService;


    /**
     * pc@Description
     * params 添加更新章节观看记录
     */
    @Transactional(rollbackFor = Exception.class)
    @PostMapping(value = "/addOrUpdate")
    public ApiResult addOrUpdate(HttpServletRequest request, @RequestBody CourseViewRecordParam params, BindingResult bindingResult) {
        HashMap map = new HashMap(2);
        if (bindingResult.hasErrors()) {
            return ApiResult.fail(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        if (params.getCourseId() == null) {
            return fail("请传入课程ID");
        }
        if (params.getChapterId() == null) {
            return fail("请传入章节ID");
        }
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return fail(ApiCode.DONT_LOGIN.getMsg());
        }

        Course course = courseService.selectCourseById(params.getCourseId());
        CourseChapter courseChapter = courseChapterService.selectCourseChapterById(params.getChapterId());

        if (course == null) {
            return fail("系统没有此课程");
        }
        if (courseChapter == null) {
            return fail("系统没有此章节");
        }
        List<CourseViewRecord> list = courseViewRecordService.selectCourseViewRecordList(CourseViewRecord
                .builder()
                .accountId(account.getAccountId())
                .chapterId(params.getChapterId())
                .courseId(params.getCourseId())
                .build());

        if (list == null || list.size() <= 0) {
            //没有观看记录，添加观看记录
            CourseViewRecord courseViewRecord = CourseViewRecord.builder().build();
            BeanUtil.copyProperties(params, courseViewRecord);

            courseViewRecord.setAccountId(account.getAccountId());
            courseViewRecord.setNickName(account.getNickName());
            courseViewRecord.setHeadPortrait(account.getHeadPortrait());
            courseViewRecord.setDeptId(account.getDeptId());
            courseViewRecord.setDelStatus(0);
            courseViewRecord.setParentId(courseChapter.getParentId());

            int num = courseViewRecordService.insertCourseViewRecord(courseViewRecord);
            if (num <= 0) {
                throw new ApiException("添加章节观看记录失败。");
            }
            map.put("result", courseViewRecord);
            map.put("state", 200);
        } else {
            //有观看记录，进行数据更新；
            CourseViewRecord courseViewRecord = list.get(0);
            if (courseViewRecord.getWatchStatus() == 1) {
                return ok(map);
            }
            int num = courseViewRecordService.updateCourseViewRecord(CourseViewRecord
                    .builder()
                    .courseViewRecordId(courseViewRecord.getCourseViewRecordId())
                    .watchStatus(params.getWatchStatus())
                    .watchTime(params.getWatchTime())
                    .build());
            if (num <= 0) {
                throw new ApiException("更新章节观看记录失败。");
            }
            map.put("result", courseViewRecord);
            map.put("state", 200);
        }
        return ok(map);
    }
}
