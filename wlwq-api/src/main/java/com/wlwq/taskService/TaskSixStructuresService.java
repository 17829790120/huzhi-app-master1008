package com.wlwq.taskService;


import com.wlwq.api.domain.*;
import com.wlwq.api.service.IApiAccountService;
import com.wlwq.api.service.ISixStructuresReadRecordService;
import com.wlwq.common.apiResult.ApiController;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.TimerTask;

/**
 * @author wx
 */
@Component
@AllArgsConstructor
public class TaskSixStructuresService extends ApiController  {

    private final IApiAccountService accountService;

    private final ISixStructuresReadRecordService sixStructuresReadRecordService;


    /**
     * 六大架构已读未读记录
     *
     * @param sixStructures   六大架构
     * @param accountId 用户ID
     * @return
     */
    public TimerTask sixStructuresRecord(SixStructures sixStructures, String accountId) {
        return new TimerTask() {
            @Override
            public void run() {
                ApiAccount account = accountService.selectApiAccountById(accountId);
                if (account != null) {
                    SixStructuresReadRecord record = sixStructuresReadRecordService.selectSixStructuresReadRecord(SixStructuresReadRecord.builder()
                            .accountId(accountId)
                            .sixStructuresReadRecordId(sixStructures.getSixStructuresId())
                            .build());
                    if (record != null) {
                        sixStructuresReadRecordService.updateSixStructuresReadRecord(SixStructuresReadRecord.builder()
                                .sixStructuresReadRecordId(record.getSixStructuresReadRecordId())
                                .content(sixStructures.getContent())
                                .build());
                    } else {
                        sixStructuresReadRecordService.insertSixStructuresReadRecord(SixStructuresReadRecord.builder()
                                .sixStructuresId(sixStructures.getSixStructuresId())
                                .threeStoreClassId(sixStructures.getThreeStoreClassId())
                                .content(sixStructures.getContent())
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
