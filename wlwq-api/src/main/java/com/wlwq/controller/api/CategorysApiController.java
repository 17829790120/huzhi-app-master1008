package com.wlwq.controller.api;

import com.wlwq.api.domain.ApiAccount;
import com.wlwq.api.domain.Categorys;
import com.wlwq.api.service.IApiAccountService;
import com.wlwq.api.service.ICategoryService;
import com.wlwq.api.service.ICourseService;
import com.wlwq.common.apiResult.ApiCode;
import com.wlwq.common.apiResult.ApiController;
import com.wlwq.common.apiResult.ApiResult;
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
 * 自己与自己的关系
 */
@RestController
@RequestMapping(value = "/api/categorys")
@AllArgsConstructor
public class CategorysApiController extends ApiController {

    private final ICategoryService categoryService;

    private final ICourseService courseService;

    private final IApiAccountService accountService;

    private final TokenService tokenService;

    /**
     * 自己与自己的关系列表
     * trainingModuleId  模块ID
     * @return
     */
    //@PassToken
    @GetMapping(value = "/list")
    public ApiResult list(HttpServletRequest request, @RequestParam(defaultValue = "0") String trainingModuleId, @RequestParam(defaultValue = "0") Long parentId) {

        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        String accountId = (account == null ? "" : account.getAccountId());
        List<Categorys> list = categoryService.selectApiCategoryList(Categorys
                .builder()
                .parentId(parentId)
                .trainingModuleId(trainingModuleId)
                .build());
        list.forEach(
                obj -> {
                    Map<String, Object> map = courseService.selectWatchFinishCount(obj.getCategoryId(), accountId);
                    obj.setAllCount(Integer.valueOf(map.get("allCount").toString()));
                    obj.setFinishLern(Integer.valueOf(map.get("finishLern").toString()));
                }
        );
        return ok(list);
    }
}
