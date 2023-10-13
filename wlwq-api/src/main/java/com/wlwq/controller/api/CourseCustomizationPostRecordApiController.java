package com.wlwq.controller.api;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wlwq.api.domain.ApiAccount;
import com.wlwq.api.domain.CourseCustomizationPost;
import com.wlwq.api.domain.CourseCustomizationPostRecord;
import com.wlwq.api.service.IApiAccountService;
import com.wlwq.api.service.ICourseCustomizationPostRecordService;
import com.wlwq.api.service.ICourseCustomizationPostService;
import com.wlwq.common.apiResult.ApiCode;
import com.wlwq.common.apiResult.ApiController;
import com.wlwq.common.apiResult.ApiResult;
import com.wlwq.common.apiResult.PageParam;
import com.wlwq.common.exception.ApiException;
import com.wlwq.params.courseExam.CourseCustomizationPostRecordParam;
import com.wlwq.service.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

/**
 * @定制课程预约
 * @author wwb
 */
@RestController
@RequestMapping(value = "/api/courseCustomizationPostRecord")
@AllArgsConstructor
public class CourseCustomizationPostRecordApiController extends ApiController {

    private final TokenService tokenService;

    private final IApiAccountService accountService;

    private final ICourseCustomizationPostRecordService courseCustomizationPostRecordService;

    private final ICourseCustomizationPostService courseCustomizationPostService;

    /**
     * 预约定制课程
     * params 添加更新章节观看记录
     */
    @Transactional(rollbackFor = Exception.class)
    @PostMapping(value = "/add")
    public ApiResult add(HttpServletRequest request, @RequestBody CourseCustomizationPostRecordParam params, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ApiResult.fail(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        List<String> postLists = params.getCourseCustomizationPostIds();
        if(postLists == null || postLists.size() == 0){
            return fail("请选择需要预约的课程。");
        }
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return fail(ApiCode.DONT_LOGIN.getMsg());
        }
        postLists.forEach(
                obj -> {
                    CourseCustomizationPost courseCustomizationPost = courseCustomizationPostService.selectCourseCustomizationPostById(obj);
                    if(courseCustomizationPost == null){
                        throw new ApiException("预约课程不存在！");
                    }
                    CourseCustomizationPostRecord courseCustomizationPostRecord = CourseCustomizationPostRecord.builder().build();
                    courseCustomizationPostRecord.setAccountId(account.getAccountId());
                    courseCustomizationPostRecord.setNickName(account.getNickName());
                    courseCustomizationPostRecord.setHeadPortrait(account.getHeadPortrait());
                    courseCustomizationPostRecord.setPostIds(params.getPostIds());
                    courseCustomizationPostRecord.setPostNames(params.getPostNames());
                    courseCustomizationPostRecord.setCourseCustomizationPostId(courseCustomizationPost.getCourseCustomizationPostId());
                    courseCustomizationPostRecord.setInformationCategoryId(courseCustomizationPost.getInformationCategoryId());
                    courseCustomizationPostRecord.setInformationPostImages(courseCustomizationPost.getInformationPostImages());
                    courseCustomizationPostRecord.setInformationPostImagesType(courseCustomizationPost.getInformationPostImagesType());
                    courseCustomizationPostRecord.setSynopsis(courseCustomizationPost.getSynopsis());
                    courseCustomizationPostRecord.setInformationPostTitle(courseCustomizationPost.getInformationPostTitle());

                    courseCustomizationPostRecord.setCompanyId(params.getCompanyId());
                    courseCustomizationPostRecord.setCompanyName(params.getCompanyName());
                    courseCustomizationPostRecord.setCompanyAddress(params.getCompanyAddress());
                    courseCustomizationPostRecord.setReservationTime(params.getReservationTime());
                    courseCustomizationPostRecord.setTelephone(params.getTelephone());
                    courseCustomizationPostRecord.setContacts(params.getContacts());
                    courseCustomizationPostRecord.setIndustryId(params.getIndustryId());
                    courseCustomizationPostRecord.setIndustryName(params.getIndustryName());
                    int count = courseCustomizationPostRecordService.insertCourseCustomizationPostRecord(courseCustomizationPostRecord);
                    if(count < 0){
                        throw new ApiException("预约课程失败！");
                    }
                }
        );
        return ok("预约报名成功");
    }

    /**
     * 获取预约记录
     *
     * @return
     */
    @GetMapping("/getPostRecordList")
    public ApiResult getPostRecordList(PageParam pageParam, HttpServletRequest request,@RequestParam(required = false) String keyword) {
        if (pageParam == null) {
            pageParam = PageParam.builder().build();
        }
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return fail(ApiCode.DONT_LOGIN.getMsg());
        }
        PageHelper.startPage(pageParam.getPageNo(), pageParam.getPageSize());
        List<CourseCustomizationPostRecord> list = courseCustomizationPostRecordService.selectCourseCustomizationPostRecordList(CourseCustomizationPostRecord
                .builder()
                .accountId(account.getAccountId())
                .informationPostTitle(keyword)
                .build());
        PageInfo<CourseCustomizationPostRecord> pageInfo = new PageInfo<>(list);
        return ok(pageInfo);
    }
}
