package com.wlwq.controller.api;

import com.wlwq.annotation.PassToken;
import com.wlwq.api.domain.ResourceFileCategory;
import com.wlwq.api.service.IResourceFileCategoryService;
import com.wlwq.common.apiResult.ApiController;
import com.wlwq.common.apiResult.ApiResult;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * @author wwb
 * 资源文件类别
 */
@RestController
@RequestMapping(value = "/api/resourceFileCategory")
@AllArgsConstructor
public class ResourceFileCategoryApiController extends ApiController {

    private IResourceFileCategoryService resourceFileCategoryService;

    /**
     * 资源文件类别列表
     *
     * @param parentId 父级ID
     * @return
     */
    @PassToken
    @GetMapping(value = "/categoryList_old")
    public ApiResult categoryList_old(@RequestParam(defaultValue = "-1") Long parentId) {
        //查询1及导航
        if (parentId == -1) {
            List<ResourceFileCategory> categoryList = resourceFileCategoryService.selectResourceFileCategoryList(ResourceFileCategory
                    .builder()
                    .parentId(0L)
                    .delStatus(0)
                    .build());
            if (categoryList != null && categoryList.size() > 0) {
                parentId = categoryList.get(0).getResourceFileCategoryId();
            }
        }
        List<ResourceFileCategory> list = resourceFileCategoryService.selectResourceFileCategoryList(ResourceFileCategory
                .builder()
                .parentId(parentId)
                .delStatus(0)
                .build());
        return ok(list);
    }

    /**
     * 资源文件类别列表
     *
     * @param parentId 父级ID
     * @return
     */
    @PassToken
    @GetMapping(value = "/categoryList")
    public ApiResult categoryList(@RequestParam(defaultValue = "0") Long parentId, @RequestParam(defaultValue = "") Long companyId) {
        List<ResourceFileCategory> categoryList = resourceFileCategoryService.selectResourceFileCategoryList(ResourceFileCategory
                .builder()
                .parentId(parentId)
                .companyId(companyId)
                .delStatus(0)
                .build());
        return ok(categoryList);
    }
}
