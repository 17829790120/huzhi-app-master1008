package com.wlwq.controller.api;


import com.wlwq.annotation.PassToken;
import com.wlwq.api.domain.ApiAccount;
import com.wlwq.api.domain.FourRelationships;
import com.wlwq.api.domain.FourRelationshipsClass;
import com.wlwq.api.domain.FourRelationshipsReadRecord;
import com.wlwq.api.service.IApiAccountService;
import com.wlwq.api.service.IFourRelationshipsClassService;
import com.wlwq.api.service.IFourRelationshipsReadRecordService;
import com.wlwq.api.service.IFourRelationshipsService;
import com.wlwq.common.apiResult.ApiController;
import com.wlwq.common.apiResult.ApiResult;
import com.wlwq.service.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dxy
 * 四类关系
 */
@RestController
@RequestMapping(value = "/api/fourRelationshipsClass")
@AllArgsConstructor
public class FourRelationshipsClassApiController extends ApiController {

    private final TokenService tokenService;

    private final IApiAccountService accountService;

    private final IFourRelationshipsClassService fourRelationshipsClassService;

    private final IFourRelationshipsService fourRelationshipsService;

    private final IFourRelationshipsReadRecordService fourRelationshipsReadRecordService;


    /**
     * 四类关系分类列表
     *
     * @param deptId 部门ID，默认总公司（100）
     * @return
     */
    @PassToken
    @GetMapping(value = "/classList")
    public ApiResult classList(@RequestParam(defaultValue = "") Long deptId, String keyword, @RequestParam(defaultValue = "-1") Long parentId, Integer allStatus, HttpServletRequest request) {
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return ApiResult.fail("该用户不存在！");
        }
        //查询1及导航
        if (parentId == -1) {
            FourRelationshipsClass fourRelationshipsClass = fourRelationshipsClassService.selectApiFourRelationshipsClassList(FourRelationshipsClass
                    .builder()
                    .deptId(deptId)
                    .parentId(0L)
                    .build());
            if (fourRelationshipsClass != null) {
                parentId = fourRelationshipsClass.getFourRelationshipsClassId();
            }
        }
        List<FourRelationshipsClass> arrayList = fourRelationshipsClassService.selectFourRelationshipsClassVoList(FourRelationshipsClass
                .builder()
                .showStatus(1)
                .parentId(parentId)
                .className(keyword)
                .build());

        List<FourRelationshipsClass> tempList = new ArrayList<>();

        for (FourRelationshipsClass e : arrayList) {
            // 查询某个分类下的所有发布的信息
            int count = fourRelationshipsService.selectFourRelationshipsCount(FourRelationships.builder()
                    .companyId(account.getCompanyId())
                    .threeStoreClassId(e.getFourRelationshipsClassId().toString())
                    .build());
            // 查询已读数量
            int alreadyCount = fourRelationshipsReadRecordService.selectFourRelationshipsReadRecordCount(FourRelationshipsReadRecord.builder()
                    .accountId(account.getAccountId())
                    .companyId(account.getCompanyId())
                    .threeStoreClassId(e.getFourRelationshipsClassId().toString())
                    .build());
            e.setNoReadCount(count - alreadyCount < 0 ? 0 : count - alreadyCount);

            if (allStatus != null && allStatus == 1) {
                // 查询新的结果并添加到临时列表中
                List<FourRelationshipsClass> newResults = fourRelationshipsClassService.selectFourRelationshipsClassVoList(FourRelationshipsClass
                        .builder()
                        .showStatus(1)
                        .parentId(e.getFourRelationshipsClassId())
                        .className(keyword)
                        .build());
                tempList.addAll(newResults);
            }
        }

        if (allStatus != null && allStatus == 1) {
            return ok(tempList);
        }

        return ok(arrayList);
    }

}
