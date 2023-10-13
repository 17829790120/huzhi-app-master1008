package com.wlwq.controller.api;

import com.wlwq.annotation.PassToken;
import com.wlwq.api.domain.Industry;
import com.wlwq.api.service.IIndustryService;
import com.wlwq.common.apiResult.ApiController;
import com.wlwq.common.apiResult.ApiResult;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * 行业信息Controller
 *
 * @author jxh
 * @date 2021-11-10
 */
@RestController
@RequestMapping(value = "/api/industry")
@AllArgsConstructor
public class IndustryApiController extends ApiController {

    private final IIndustryService industryService;

    /**
     * 企业所属行业
     * @return
     */
    @PassToken
    @RequestMapping(value = "/getEnterpriseIndustry", method = RequestMethod.GET)
    public ApiResult getEnterpriseIndustry(@RequestParam(defaultValue = "") Long id){
        Industry industry = industryService.selectIndustryById(id);
        return ApiResult.ok(industry);
    }

    /**
     * 所属行业列表
     * @return
     */
    @PassToken
    @RequestMapping(value = "/findIndustryList", method = RequestMethod.GET)
    public ApiResult findIndustryList(){
        List<Industry> industries = industryService.selectIndustryList(Industry.builder().parentId(0L).build());
        if(industries.size() > 0){
            for(int i = 0 ; i < industries.size(); i++){
                Industry tempIndustry = industries.get(i);
                List<Industry> tempIndustries = industryService.selectIndustryList(Industry.builder().parentId(tempIndustry.getId()).build());
                tempIndustry.setIndustryList(tempIndustries);
            }
        }
        return ApiResult.ok(industries);
    }
}
