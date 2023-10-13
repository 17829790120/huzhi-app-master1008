package com.wlwq.controller.api;

import cn.hutool.core.date.DateUtil;
import com.wlwq.annotation.PassToken;
import com.wlwq.api.domain.ApiAccount;
import com.wlwq.api.domain.ReportTraining;
import com.wlwq.api.domain.ReportTrainingClassify;
import com.wlwq.api.domain.ReportTrainingReadRecord;
import com.wlwq.api.service.IApiAccountService;
import com.wlwq.api.service.IReportTrainingClassifyService;
import com.wlwq.api.service.IReportTrainingReadRecordService;
import com.wlwq.api.service.IReportTrainingService;
import com.wlwq.common.apiResult.ApiController;
import com.wlwq.common.apiResult.ApiResult;
import com.wlwq.service.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 汇报训练
 *
 * @author gaoce
 */
@RestController
@RequestMapping(value = "/api/reportTraining")
@AllArgsConstructor
public class ReportTrainingClassifyApiController extends ApiController {

    private final IReportTrainingClassifyService trainingClassifyService;

    private final IReportTrainingService reportTrainingService;

    private final IReportTrainingReadRecordService readRecordService;

    private final TokenService tokenService;

    private final IApiAccountService accountService;

    /**
     * 分类列表
     *
     * @return
     */
    @GetMapping("/classifyList")
    public ApiResult classifyList(HttpServletRequest request) {
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return ApiResult.fail("该用户不存在！");
        }
        List<ReportTrainingClassify> classifyList = trainingClassifyService.selectReportTrainingClassifyList(ReportTrainingClassify.builder().showStatus(1).build());
        classifyList.forEach(e -> {
            String date = "";
            String month = "";
            if (e.getReportTrainingClassifyId() == 1) {
                date = DateUtil.today();
            } else {
                month = DateUtil.format(DateUtil.date(), "yyyy-MM");
            }
            // 查询某个分类下的所有发布的信息
            int count = reportTrainingService.selectApiReportTrainingCount(ReportTraining.builder()
                    .companyId(account.getCompanyId())
                    .templateType(e.getReportTrainingClassifyId())
                    .date(date)
                    .month(month)
                    .build());
            // 查询已读数量
            int alreadyCount = readRecordService.selectReportTrainingReadRecordCount(ReportTrainingReadRecord.builder()
                    .accountId(account.getAccountId())
                    .companyId(account.getCompanyId())
                    .templateType(e.getReportTrainingClassifyId())
                    .date(date)
                    .month(month)
                    .build());
            e.setNoReadCount(count - alreadyCount < 0 ? 0 : count - alreadyCount);
        });
        return ok(classifyList);
    }
}
