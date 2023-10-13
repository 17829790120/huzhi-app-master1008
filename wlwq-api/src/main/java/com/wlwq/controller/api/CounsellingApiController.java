package com.wlwq.controller.api;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wlwq.annotation.PassToken;
import com.wlwq.api.domain.CounsellingExecutionCategory;
import com.wlwq.api.domain.CounsellingExecutionPost;
import com.wlwq.api.domain.CounsellingTheoreticalCategory;
import com.wlwq.api.domain.CounsellingTheoreticalPost;
import com.wlwq.api.service.ICounsellingExecutionCategoryService;
import com.wlwq.api.service.ICounsellingExecutionPostService;
import com.wlwq.api.service.ICounsellingTheoreticalCategoryService;
import com.wlwq.api.service.ICounsellingTheoreticalPostService;
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
 * 辅导体系
 */
@RestController
@RequestMapping(value = "/api/counselling")
@AllArgsConstructor
public class CounsellingApiController extends ApiController {

    private final ICounsellingExecutionCategoryService counsellingExecutionCategoryService;

    private final ICounsellingTheoreticalCategoryService counsellingTheoreticalCategoryService;

    private final ICounsellingTheoreticalPostService counsellingTheoreticalPostService;

    private final ICounsellingExecutionPostService counsellingExecutionPostService;

    /**
     * 理论体系类别列表
     *
     * @return
     */
    //@PassToken
    @GetMapping(value = "/theoreticalCategoryList")
    public ApiResult theoreticalCategoryList() {
        List<CounsellingTheoreticalCategory> list = counsellingTheoreticalCategoryService.selectCounsellingTheoreticalCategoryList(CounsellingTheoreticalCategory
                .builder()
                .delStatus(0)
                .build());
        return ok(list);
    }

    /**
     * 辅导实施类目列表
     *
     * @return
     */
    //@PassToken
    @GetMapping(value = "/executionCategoryList")
    public ApiResult executionCategoryList() {
        List<CounsellingExecutionCategory> list = counsellingExecutionCategoryService.selectCounsellingExecutionCategoryList(CounsellingExecutionCategory
                .builder()
                .delStatus(0)
                .build());
        return ok(list);
    }

    /**
     * 理论体系咨询信息列表
     *
     * @param counsellingTheoreticalCategoryId 理论体系类别ID
     * @param keyword                          关键字搜索
     * @return
     */
    @PassToken
    @GetMapping(value = "/theoreticalInformationList")
    public ApiResult theoreticalInformationList(PageParam pageParam, String keyword, @RequestParam(defaultValue = "") String counsellingTheoreticalCategoryId) {
        if (pageParam == null) {
            pageParam = PageParam.builder().build();
        }
        PageHelper.startPage(pageParam.getPageNo(), pageParam.getPageSize());
        List<CounsellingTheoreticalPost> list = counsellingTheoreticalPostService.selectCounsellingTheoreticalPostList(CounsellingTheoreticalPost
                .builder()
                .counsellingTheoreticalCategoryId(counsellingTheoreticalCategoryId)
                .delStatus(0)
                .informationPostTitle(keyword)
                .build());
        PageInfo<CounsellingTheoreticalPost> pageInfo = new PageInfo<>(list);
        return ok(pageInfo);
    }

    /**
     * 理论体系咨询信息详情
     *
     * @param counsellingTheoreticalPostId 理论体系资讯ID
     * @return
     */
    @PassToken
    @GetMapping(value = "/counsellingTheoreticalPostDetail")
    public ApiResult counsellingTheoreticalPostDetail(@RequestParam(defaultValue = "0") String counsellingTheoreticalPostId) {
        CounsellingTheoreticalPost counsellingExecutionPost = counsellingTheoreticalPostService.selectCounsellingTheoreticalPostById(counsellingTheoreticalPostId);
        return ok(counsellingExecutionPost);
    }

    /**
     * 辅导实施资讯对象信息详情
     *
     * @param counsellingExecutionPostId 资讯信息ID
     * @return
     */
    @PassToken
    @GetMapping(value = "/counsellingExecutionPostDetail")
    public ApiResult counsellingExecutionPostDetail(@RequestParam(defaultValue = "0") String counsellingExecutionPostId) {
        CounsellingExecutionPost counsellingExecutionPost = counsellingExecutionPostService.selectCounsellingExecutionPostById(counsellingExecutionPostId);
        return ok(counsellingExecutionPost);
    }

    /**
     * 辅导实施咨询信息列表
     *
     * @param counsellingExecutionCategoryId 辅导实施类别ID
     * @param keyword                        关键字搜索
     * @return
     */
    @PassToken
    @GetMapping(value = "/executionInformationList")
    public ApiResult executionInformationList(PageParam pageParam, String keyword, @RequestParam(defaultValue = "") String counsellingExecutionCategoryId) {
        if (pageParam == null) {
            pageParam = PageParam.builder().build();
        }
        PageHelper.startPage(pageParam.getPageNo(), pageParam.getPageSize());
        List<CounsellingExecutionPost> list = counsellingExecutionPostService.selectCounsellingExecutionPostList(CounsellingExecutionPost
                .builder()
                .counsellingExecutionCategoryId(counsellingExecutionCategoryId)
                .delStatus(0)
                .informationPostTitle(keyword)
                .build());
        PageInfo<CounsellingExecutionPost> pageInfo = new PageInfo<>(list);
        return ok(pageInfo);
    }
}
