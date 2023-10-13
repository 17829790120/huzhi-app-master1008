package com.wlwq.controller.api;


import com.wlwq.annotation.PassToken;
import com.wlwq.api.domain.*;
import com.wlwq.api.service.IApiAccountService;
import com.wlwq.api.service.ISixStructuresClassService;
import com.wlwq.api.service.ISixStructuresReadRecordService;
import com.wlwq.api.service.ISixStructuresService;
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
 * @author wx
 * 六大架构
 */
@RestController
@RequestMapping(value = "/api/sixStructuresClass")
@AllArgsConstructor
public class SixStructuresClassApiController extends ApiController {

    private final TokenService tokenService;

    private final IApiAccountService accountService;

    private final ISixStructuresClassService sixStructuresClassService;

    private final ISixStructuresService sixStructuresService;

    private final ISixStructuresReadRecordService sixStructuresReadRecordService;


    /**
     * 六大架构分类列表
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
            SixStructuresClass sixStructuresClass = sixStructuresClassService.selectApiSixStructuresClassList(SixStructuresClass
                    .builder()
                    .deptId(deptId)
                    .parentId(0L)
                    .build());
            if (sixStructuresClass != null) {
                parentId = sixStructuresClass.getSixStructuresClassId();
            }
        }
        List<SixStructuresClass> arrayList = sixStructuresClassService.selectSixStructuresClassVoList(SixStructuresClass
                .builder()
                .showStatus(1)
                .parentId(parentId)
                .className(keyword)
                .build());

        List<SixStructuresClass> tempList = new ArrayList<>();

        for (SixStructuresClass e : arrayList) {
            // 查询某个分类下的所有发布的信息
            int count = sixStructuresService.selectSixStructuresCount(SixStructures.builder()
                    .companyId(account.getCompanyId())
                    .threeStoreClassId(e.getSixStructuresClassId().toString())
                    .build());
            // 查询已读数量
            int alreadyCount = sixStructuresReadRecordService.selectSixStructuresReadRecordCount(SixStructuresReadRecord.builder()
                    .accountId(account.getAccountId())
                    .companyId(account.getCompanyId())
                    .threeStoreClassId(e.getSixStructuresClassId().toString())
                    .build());
            e.setNoReadCount(count - alreadyCount < 0 ? 0 : count - alreadyCount);

            if (allStatus != null && allStatus == 1) {
                // 查询新的结果并添加到临时列表中
                List<SixStructuresClass> newResults = sixStructuresClassService.selectSixStructuresClassVoList(SixStructuresClass
                        .builder()
                        .showStatus(1)
                        .parentId(e.getSixStructuresClassId())
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
