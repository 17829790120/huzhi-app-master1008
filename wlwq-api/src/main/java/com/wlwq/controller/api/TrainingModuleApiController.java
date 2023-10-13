package com.wlwq.controller.api;

import com.wlwq.annotation.PassToken;
import com.wlwq.api.domain.TrainingModule;
import com.wlwq.api.service.ITrainingModuleService;
import com.wlwq.common.apiResult.ApiController;
import com.wlwq.common.apiResult.ApiResult;
import com.wlwq.common.apiResult.PageParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author gaoce
 */
@RestController
@RequestMapping(value = "/api/trainingModule")
@AllArgsConstructor
public class TrainingModuleApiController extends ApiController {

    private final ITrainingModuleService moduleService;

    /**
     * 人员训练模块列表
     *
     * @return
     */
    @PassToken
    @GetMapping("/list")
    public ApiResult list() {
        return ok(moduleService.selectApiTrainingModuleList(TrainingModule.builder().showStatus(1).build()));
    }
}
