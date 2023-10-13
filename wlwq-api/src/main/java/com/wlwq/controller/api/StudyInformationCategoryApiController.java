package com.wlwq.controller.api;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wlwq.annotation.PassToken;
import com.wlwq.api.domain.StudyInformationCategory;
import com.wlwq.api.domain.StudyInformationPost;
import com.wlwq.api.service.IStudyInformationCategoryService;
import com.wlwq.api.service.IStudyInformationPostService;
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
 * 学习模块--自己与组织的关系--资讯分类
 */
@RestController
@RequestMapping(value = "/api/studyInformationCategory")
@AllArgsConstructor
public class StudyInformationCategoryApiController extends ApiController {

    private final IStudyInformationCategoryService studyInformationCategoryService;

    private final IStudyInformationPostService studyInformationPostService;

    /**
     * 咨询分类展示列表
     *
     * @param deptId 部门ID，默认总公司（100）
     * @return
     */
    @PassToken
    @GetMapping(value = "/list")
    public ApiResult list(@RequestParam(defaultValue = "") Long deptId, @RequestParam(defaultValue = "-1") Long parentId) {
        //查询1及导航
        if (parentId == -1) {
            List<StudyInformationCategory> informationCategoryList = studyInformationCategoryService.selectInformationCategoryVoList(StudyInformationCategory
                    .builder()
                    .deptId(deptId)
                    .parentId(0L)
                    .delStatus(0)
                    .build());
            if (informationCategoryList != null && informationCategoryList.size() > 0) {
                parentId = informationCategoryList.get(0).getStudyInformationCategoryId();
            }
        }
        List<StudyInformationCategory> informationCategoryNewList = studyInformationCategoryService.selectInformationCategoryVoList(StudyInformationCategory
                .builder()
                .parentId(parentId)
                .delStatus(0)
                .build());
        return ok(informationCategoryNewList);
    }

    /**
     * 文化展示列表
     *
     * @param studyInformationCategoryId
     * @param keyword                    关键字搜索
     * @return
     */
    @PassToken
    @GetMapping(value = "/informationList")
    public ApiResult informationList(PageParam pageParam, String keyword,
                                     @RequestParam(defaultValue = "") Long studyInformationCategoryId) {
        if (pageParam == null) {
            pageParam = PageParam.builder().build();
        }
        PageHelper.startPage(pageParam.getPageNo(), pageParam.getPageSize());
        List<StudyInformationPost> list = studyInformationPostService.selectStudyInformationPostList(StudyInformationPost
                .builder()
                .studyInformationCategoryId(studyInformationCategoryId)
                .delStatus(0)
                .informationPostTitle(keyword)
                .build());
        PageInfo<StudyInformationPost> pageInfo = new PageInfo<>(list);
        return ok(pageInfo);
    }

    /**
     * 学习模块--自己与组织的关系--文化展示详情
     *
     * @param studyInformationPostId  资讯ID
     * @return
     */
    @PassToken
    @GetMapping(value = "/details")
    public ApiResult details(@RequestParam(defaultValue = "-1") Long studyInformationPostId) {
        return ok(studyInformationPostService.selectStudyInformationPostById(studyInformationPostId));
    }
}
