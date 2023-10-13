package com.wlwq.taskService;

import com.wlwq.api.domain.ApiAccount;
import com.wlwq.api.domain.ReportTraining;
import com.wlwq.api.domain.ReportTrainingReadRecord;
import com.wlwq.api.service.IApiAccountService;
import com.wlwq.api.service.IReportTrainingReadRecordService;
import com.wlwq.common.apiResult.ApiController;
import com.wlwq.params.examine.ContractParamVO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.TimerTask;

/**
 * @author gaoce
 */
@Component
@AllArgsConstructor
public class TaskReportTrainingServe extends ApiController {

    private final IApiAccountService accountService;

    private final IReportTrainingReadRecordService readRecordService;

    /**
     * 汇报训练处理已读未读记录
     *
     * @param params    汇报训练
     * @param accountId 用户ID
     * @return
     */
    public TimerTask record(ReportTraining params, String accountId) {
        return new TimerTask() {
            @Override
            public void run() {
                ApiAccount account = accountService.selectApiAccountById(accountId);
                if (account != null) {
                    ReportTrainingReadRecord record = readRecordService.selectReportTrainingReadRecord(ReportTrainingReadRecord.builder()
                            .accountId(accountId)
                            .reportTrainingId(params.getReportTrainingId())
                            .build());
                    if (record != null) {
                        readRecordService.updateReportTrainingReadRecord(ReportTrainingReadRecord.builder()
                                .reportTrainingReadRecordId(record.getReportTrainingReadRecordId())
                                .content(params.getContent())
                                .build());
                    } else {
                        readRecordService.insertReportTrainingReadRecord(ReportTrainingReadRecord.builder()
                                .reportTrainingId(params.getReportTrainingId())
                                .templateType(params.getTemplateType())
                                .content(params.getContent())
                                .accountId(account.getAccountId())
                                .postIds(account.getPostIds())
                                .deptId(account.getDeptId())
                                .accountName(account.getNickName())
                                .accountPhone(account.getPhone())
                                .accountHead(account.getHeadPortrait())
                                .companyId(account.getCompanyId())
                                .build());
                    }
                }

            }
        };
    }
}
