package com.wlwq.taskService;


import com.wlwq.api.domain.*;
import com.wlwq.api.domain.FourRelationshipsReadRecord;
import com.wlwq.api.service.IApiAccountService;
import com.wlwq.api.service.IFourRelationshipsReadRecordService;
import com.wlwq.common.apiResult.ApiController;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.TimerTask;

/**
 * @author dxy
 */
@Component
@AllArgsConstructor
public class TaskFourRelationshipsService extends ApiController  {

    private final IApiAccountService accountService;

    private final IFourRelationshipsReadRecordService fourRelationshipsReadRecordService;

    /**
     * 四类关系已读未读记录
     *
     * @param fourRelationships   四类关系
     * @param accountId 用户ID
     * @return
     */
    public TimerTask fourRelationshipsRecord(FourRelationships fourRelationships, String accountId) {
        return new TimerTask() {
            @Override
            public void run() {
                ApiAccount account = accountService.selectApiAccountById(accountId);
                if (account != null) {
                    FourRelationshipsReadRecord record = fourRelationshipsReadRecordService.selectFourRelationshipsReadRecord(FourRelationshipsReadRecord.builder()
                            .accountId(accountId)
                            .fourRelationshipsReadRecordId(fourRelationships.getFourRelationshipsId())
                            .build());
                    if (record != null) {
                        fourRelationshipsReadRecordService.updateFourRelationshipsReadRecord(FourRelationshipsReadRecord.builder()
                                .fourRelationshipsReadRecordId(record.getFourRelationshipsReadRecordId())
                                .content(fourRelationships.getContent())
                                .build());
                    } else {
                        fourRelationshipsReadRecordService.insertFourRelationshipsReadRecord(FourRelationshipsReadRecord.builder()
                                .fourRelationshipsId(fourRelationships.getFourRelationshipsId())
                                .threeStoreClassId(fourRelationships.getThreeStoreClassId())
                                .content(fourRelationships.getContent())
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
