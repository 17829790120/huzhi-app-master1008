package com.wlwq.controller.api;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wlwq.annotation.PassToken;
import com.wlwq.common.apiResult.ApiController;
import com.wlwq.common.apiResult.ApiResult;
import com.wlwq.common.apiResult.PageParam;
import com.wlwq.information.domain.InformationCategory;
import com.wlwq.information.domain.InformationPost;
import com.wlwq.information.service.IInformationCategoryService;
import com.wlwq.information.service.IInformationPostService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * @author wwb
 * 文化展示
 */
@RestController
@RequestMapping(value = "/api/informationCategory")
@AllArgsConstructor
public class InformationCategoryApiController extends ApiController {

    private final IInformationCategoryService informationCategoryService;

    private final IInformationPostService informationPostService;

    /**
     * 文化展示列表
     *
     * @param deptId 部门ID，默认总公司（100）
     * @return
     */
    @PassToken
    @GetMapping(value = "/list")
    public ApiResult list(@RequestParam(defaultValue = "0") Long deptId, @RequestParam(defaultValue = "-1") Long parentId) {
        //查询1及导航
        if (parentId == -1) {
            List<InformationCategory> informationCategoryList = informationCategoryService.selectInformationCategoryVoList(InformationCategory
                    .builder()
                    .deptId(deptId)
                    .parentId(0L)
                    .delStatus(0)
                    .build());
            if (informationCategoryList != null && informationCategoryList.size() > 0) {
                parentId = informationCategoryList.get(0).getInformationCategoryId();
            }
        }
        List<InformationCategory> informationCategoryNewList = informationCategoryService.selectInformationCategoryVoList(InformationCategory
                .builder()
                .parentId(parentId)
                .delStatus(0)
                .build());
        return ok(informationCategoryNewList);
    }

    /**
     * 文化展示列表
     *
     * @param deptId 部门ID，默认总公司（100）
     * @param keyword 关键字搜索
     * @return
     */
    @PassToken
    @GetMapping(value = "/informationList")
    public ApiResult informationList(PageParam pageParam, @RequestParam(defaultValue = "") Long deptId,
                                     @RequestParam(defaultValue = "") Long informationCategoryId, String keyword) {
        if (pageParam == null) {
            pageParam = PageParam.builder().build();
        }
        PageHelper.startPage(pageParam.getPageNo(), pageParam.getPageSize());
        List<InformationPost> list = informationPostService.selectApiInformationPostList(InformationPost
                .builder()
                .deptId(deptId)
                .informationCategoryId(informationCategoryId)
                .informationPostTitle(keyword)
                .build());
        PageInfo<InformationPost> pageInfo = new PageInfo<>(list);
        return ok(pageInfo);
    }

    /**
     * 文化展示详情
     *
     * @param informationPostId  资讯ID
     * @return
     */
    @PassToken
    @GetMapping(value = "/details")
    public ApiResult details(@RequestParam(defaultValue = "-1") Long informationPostId) {
        return ok(informationPostService.selectInformationPostById(informationPostId));
    }
}
