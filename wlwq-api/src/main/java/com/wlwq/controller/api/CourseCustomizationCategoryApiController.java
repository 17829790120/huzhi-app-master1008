package com.wlwq.controller.api;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wlwq.annotation.PassToken;
import com.wlwq.api.domain.CourseCustomizationCategory;
import com.wlwq.api.domain.CourseCustomizationPost;
import com.wlwq.api.domain.CourseCustomizationPostRecord;
import com.wlwq.api.service.ICourseCustomizationCategoryService;
import com.wlwq.api.service.ICourseCustomizationPostRecordService;
import com.wlwq.api.service.ICourseCustomizationPostService;
import com.wlwq.common.apiResult.ApiController;
import com.wlwq.common.apiResult.ApiResult;
import com.wlwq.common.apiResult.PageParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author wwb
 * 课程定制类别
 */
@RestController
@RequestMapping(value = "/api/courseCustomizationCategory")
@AllArgsConstructor
public class CourseCustomizationCategoryApiController extends ApiController {

    private final ICourseCustomizationCategoryService courseCustomizationCategoryService;

    private final ICourseCustomizationPostService courseCustomizationPostService;

    private final ICourseCustomizationPostRecordService courseCustomizationPostRecordService;

    /**
     * 课程定制类别列表
     *
     * @param parentId 父级ID
     * @return
     */
    @PassToken
    @GetMapping(value = "/categoryList")
    public ApiResult categoryList(@RequestParam(defaultValue = "-1") Long parentId) {
        //查询1及导航
        if (parentId == -1) {
            List<CourseCustomizationCategory> categoryList = courseCustomizationCategoryService.selectCourseCustomizationCategoryList(CourseCustomizationCategory
                    .builder()
                    .parentId(0L)
                    .delStatus(0)
                    .showStatus(1)
                    .build());
            if (categoryList != null && categoryList.size() > 0) {
                parentId = categoryList.get(0).getCourseCustomizationCategoryId();
            }
        }
        List<CourseCustomizationCategory> list = courseCustomizationCategoryService.selectCourseCustomizationCategoryList(CourseCustomizationCategory
                .builder()
                .parentId(parentId)
                .delStatus(0)
                .showStatus(1)
                .build());
        return ok(list);
    }

    /**
     * 获取置顶课程定制咨询列表
     *
     * @return
     */
    @PassToken
    @GetMapping(value = "/topStatusPostList")
    public ApiResult topStatusPostList(PageParam pageParam) {
        if (pageParam == null) {
            pageParam = PageParam.builder().build();
        }
        PageHelper.startPage(pageParam.getPageNo(), pageParam.getPageSize());
        List<CourseCustomizationPost> list = courseCustomizationPostService.selectCourseCustomizationPostList(CourseCustomizationPost
                .builder()
                .topStatus(1)
                .delStatus(0)
                .build());
        PageInfo<CourseCustomizationPost> pageInfo = new PageInfo<>(list);
        list.forEach(
                obj -> {
                    List<CourseCustomizationPostRecord> postRecordList = courseCustomizationPostRecordService.selectCourseCustomizationPostRecordList(CourseCustomizationPostRecord
                            .builder()
                            .courseCustomizationPostId(obj.getCourseCustomizationPostId())
                            .build());
                    if(postRecordList == null || postRecordList.size() == 0){
                        obj.setCount(0);
                    }else{
                        obj.setCount(postRecordList.size());
                    }
                }
        );
        return ok(pageInfo);
    }

    /**
     * 课程定制咨询列表
     *
     * @param courseCustomizationCategoryId 部门ID，默认总公司（100）
     * @return
     */
    @PassToken
    @GetMapping(value = "/courseCustomizationPostList")
    public ApiResult courseCustomizationPostList(PageParam pageParam,
                                                 @RequestParam(defaultValue = "") Long courseCustomizationCategoryId,
                                                 @RequestParam(required=false) String keyword) {
        if (pageParam == null) {
            pageParam = PageParam.builder().build();
        }
        PageHelper.startPage(pageParam.getPageNo(), pageParam.getPageSize());
        List<CourseCustomizationPost> list = courseCustomizationPostService.selectCourseCustomizationPostList(CourseCustomizationPost
                .builder()
                .informationCategoryId(courseCustomizationCategoryId)
                .informationPostTitle(keyword)
                .delStatus(0)
                .build());
        PageInfo<CourseCustomizationPost> pageInfo = new PageInfo<>(list);
        list.forEach(
                obj -> {
                    List<CourseCustomizationPostRecord> postRecordList = courseCustomizationPostRecordService.selectCourseCustomizationPostRecordList(CourseCustomizationPostRecord
                            .builder()
                            .courseCustomizationPostId(obj.getCourseCustomizationPostId())
                            .build());
                    if(postRecordList == null || postRecordList.size() == 0){
                        obj.setCount(0);
                    }else{
                        obj.setCount(postRecordList.size());
                    }
                }
        );
        return ok(pageInfo);
    }

    /**
     * 课程定制咨询详情
     *
     * @param courseCustomizationPostId 资讯ID
     * @return
     */
    @PassToken
    @GetMapping(value = "/courseCustomization")
    public ApiResult courseCustomization(@RequestParam(defaultValue = "") String courseCustomizationPostId) {
        CourseCustomizationPost courseCustomizationPost = courseCustomizationPostService.selectCourseCustomizationPostById(courseCustomizationPostId);
        List<CourseCustomizationPostRecord> postRecordList = courseCustomizationPostRecordService.selectCourseCustomizationPostRecordList(CourseCustomizationPostRecord
                .builder()
                .courseCustomizationPostId(courseCustomizationPost.getCourseCustomizationPostId())
                .build());
        if(postRecordList == null || postRecordList.size() == 0){
            courseCustomizationPost.setCount(0);
        }else{
            courseCustomizationPost.setCount(postRecordList.size());
        }
        return ok(courseCustomizationPost);
    }

}
