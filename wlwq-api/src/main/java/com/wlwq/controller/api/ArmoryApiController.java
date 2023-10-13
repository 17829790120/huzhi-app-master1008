package com.wlwq.controller.api;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wlwq.annotation.PassToken;
import com.wlwq.api.domain.ApiAccount;
import com.wlwq.api.domain.Armory;
import com.wlwq.api.service.IApiAccountService;
import com.wlwq.api.service.IArmoryService;
import com.wlwq.common.apiResult.ApiController;
import com.wlwq.common.apiResult.ApiResult;
import com.wlwq.common.apiResult.PageParam;
import com.wlwq.service.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author wwb
 * 英雄榜
 */
@RestController
@RequestMapping(value = "/api/armory")
@AllArgsConstructor
public class ArmoryApiController extends ApiController {
    private IArmoryService armoryService;

    private final TokenService tokenService;

    private final IApiAccountService accountService;

    /**
     * 英雄榜列表
     * @param deptId 部门ID，默认总公司（100）
     * @return
     */
    //@PassToken
    @GetMapping(value = "/list")
    public ApiResult list(@RequestParam(defaultValue = "0") Long deptId,PageParam pageParam, HttpServletRequest request){
        String accountId = tokenService.getAccountId(request);
        ApiAccount account = accountService.selectApiAccountById(accountId);
        if (account == null) {
            return fail("请登录系统！");
        }
        if (pageParam == null) {
            pageParam = PageParam.builder().build();
        }
        Armory armory = Armory.builder().build();
        armory.setDeptId(deptId);
        armory.setTag(0);
        PageHelper.startPage(pageParam.getPageNo(), pageParam.getPageSize());
        List<Armory> list = armoryService.selectApiArmoryList(armory);
        PageInfo<Armory> pageInfo = new PageInfo<>(list);
        return ok(pageInfo);
    }

    /**
     * 英雄榜详情
     *
     * @param armoryId  英雄榜id
     * @return
     */
    @PassToken
    @GetMapping(value = "/details")
    public ApiResult details(@RequestParam(defaultValue = "-1") String armoryId) {
        return ok(armoryService.selectArmoryById(armoryId));
    }
}
