package com.wlwq.taskService;

import com.wlwq.api.domain.AccountFlowerRecord;
import com.wlwq.api.domain.ApiAccount;
import com.wlwq.api.domain.MessageRemind;
import com.wlwq.api.service.IAccountFlowerRecordService;
import com.wlwq.api.service.IApiAccountService;
import com.wlwq.api.service.IMessageRemindService;
import com.wlwq.common.apiResult.ApiController;
import com.wlwq.common.exception.ApiException;
import com.wlwq.common.utils.StringUtils;
import com.wlwq.params.examine.ContractParamVO;
import com.wlwq.params.flower.AccountParamVO;
import com.wlwq.params.flower.FlowerParamVO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.TimerTask;

/**
 * @author gaoce
 */
@Component
@AllArgsConstructor
public class TaskFlowerService extends ApiController {


    private final IApiAccountService accountService;

    private final IAccountFlowerRecordService flowerRecordService;

    private final IMessageRemindService messageRemindService;

    /**
     * App更新红花奖励记录
     *
     * @param params
     * @return
     */
    public TimerTask flower(FlowerParamVO params,ApiAccount account) {
        return new TimerTask() {
            @Override
            public void run() {
                for (AccountParamVO e : params.getFlowerList()) {
                    // 插入记录
                    flowerRecordService.insertAccountFlowerRecord(AccountFlowerRecord.builder()
                            .recordName(params.getRemark())
                            .recordNum(params.getNum() < 0 ? -params.getNum() : params.getNum())
                            .giveAccountId(account.getAccountId())
                            .giveAccountName(account.getNickName())
                            .giveAccountPhone(account.getPhone())
                            .giveAccountHead(account.getHeadPortrait())
                            .givePostNames(account.getPostNames())
                            .accountId(e.getAccountId())
                            .accountName(e.getAccountName())
                            .accountPhone(e.getAccountPhone())
                            .accountHead(e.getAccountHead())
                            .postNames(e.getPostNames())
                            .build());
                    // 更新接收者的余额
                    ApiAccount apiAccount = accountService.selectApiAccountById(e.getAccountId());
                    if (apiAccount != null) {
                        accountService.updateApiAccount(ApiAccount.builder()
                                .accountId(apiAccount.getAccountId())
                                .totalFlowerNum(apiAccount.getTotalFlowerNum() + params.getNum())
                                .surplusFlowerNum(apiAccount.getSurplusFlowerNum() + params.getNum())
                                .build());
                    }
//                    // 发送系统消息
//                    messageRemindService.insertMessageRemind(MessageRemind.builder()
//                            .title("红花奖励")
//                            .brief("恭喜！"+  e.getAccountName() +"-"+ e.getPostNames()+" 获得 "+account.getNickName() +"-"+ account.getPostNames()+" 赠送的"+params.getNum()+"个小红花，理由："+params.getRemark())
//                            .modelStatus(1)
//                            .jumpType(-4)
//                            .modelId("0")
//                            .accountId(e.getAccountId())
//                            .build());
                }
                // 更新消息记录
                List<ApiAccount> accountList = accountService.selectAccountListByCompanyId(ApiAccount.builder().companyId(account.getCompanyId()).build());
                for (ApiAccount account1 : accountList){
                    for (AccountParamVO e : params.getFlowerList()) {
                        // 发送系统消息
                        messageRemindService.insertMessageRemind(MessageRemind.builder()
                                .title("红花奖励")
                                .brief("恭喜！"+  e.getAccountName() +"-"+ e.getPostNames()+" 获得 "+account.getNickName() +"-"+ account.getPostNames()+" 赠送的"+params.getNum()+"个小红花，理由："+params.getRemark())
                                .modelStatus(1)
                                .jumpType(-4)
                                .modelId("0")
                                .accountId(account1.getAccountId())
                                .build());
                    }
                }
            }
        };
    }


    /**
     * 后台更新红花奖励记录
     *
     * @param accountFlowerRecord 鲜花记录
     * @return
     */
    public TimerTask managerFlower(AccountFlowerRecord accountFlowerRecord,Long deptId) {
        return new TimerTask() {
            @Override
            public void run() {
                String accountId = accountFlowerRecord.getAccountId();
                if(StringUtils.isNotBlank(accountId)){
                    String[] arr = accountId.split(",");
                    for (int i = 0;i<arr.length;i++) {
                        // 更新接收者的余额
                        ApiAccount apiAccount = accountService.selectApiAccountById(arr[i]);
                        if (apiAccount != null) {
                            if(accountFlowerRecord.getRecordNum() > 0){
                                int count = accountService.updateApiAccount(ApiAccount.builder()
                                        .accountId(arr[i])
                                        .totalFlowerNum(apiAccount.getTotalFlowerNum() + accountFlowerRecord.getRecordNum())
                                        .surplusFlowerNum(apiAccount.getSurplusFlowerNum() + accountFlowerRecord.getRecordNum())
                                        .build());
                                if(count <= 0){
                                    throw new ApiException("更新记录失败！");
                                }
//                                // 发送系统消息
//                                messageRemindService.insertMessageRemind(MessageRemind.builder()
//                                        .title("红花奖励")
//                                        .brief("恭喜！"+  apiAccount.getNickName() +"-"+ apiAccount.getPostNames()+" 获得平台赠送的"+accountFlowerRecord.getRecordNum()+"个小红花，理由："+accountFlowerRecord.getRecordName())
//                                        .modelStatus(1)
//                                        .jumpType(-4)
//                                        .modelId("0")
//                                        .accountId(apiAccount.getAccountId())
//                                        .build());
                            }
                            if(accountFlowerRecord.getRecordNum() < 0){
                                int count = accountService.updateApiAccount(ApiAccount.builder()
                                        .accountId(arr[i])
                                        .surplusFlowerNum(apiAccount.getSurplusFlowerNum() + accountFlowerRecord.getRecordNum())
                                        .build());
                                if(count <= 0){
                                    throw new ApiException("更新记录失败！");
                                }
//                                // 发送系统消息
//                                messageRemindService.insertMessageRemind(MessageRemind.builder()
//                                        .title("红花扣除")
//                                        .brief("很遗憾！"+  apiAccount.getNickName() +"-"+ apiAccount.getPostNames()+" 平台"+accountFlowerRecord.getRecordNum()+"个小红花，理由："+accountFlowerRecord.getRecordName())
//                                        .modelStatus(1)
//                                        .jumpType(-4)
//                                        .modelId("0")
//                                        .accountId(apiAccount.getAccountId())
//                                        .build());
                            }
                            if(accountFlowerRecord.getRecordNum() != 0){
                                // 插入记录
                                flowerRecordService.insertAccountFlowerRecord(AccountFlowerRecord.builder()
                                        .recordName(accountFlowerRecord.getRecordName())
                                        .recordNum(accountFlowerRecord.getRecordNum())
                                        .giveAccountId("0")
                                        .giveAccountName("平台")
                                        .giveAccountPhone("")
                                        .giveAccountHead("")
                                        .accountId(apiAccount.getAccountId())
                                        .accountName(apiAccount.getAccountName())
                                        .accountPhone(apiAccount.getPhone())
                                        .accountHead(apiAccount.getHeadPortrait())
                                        .build());

                            }

                        }

                    }
                    List<ApiAccount> accountList = accountService.selectAccountListByCompanyId(ApiAccount.builder().companyId(deptId).build());
                    for (ApiAccount account1 : accountList){
                        for (int i = 0;i<arr.length;i++) {
                            // 更新接收者的余额
                            ApiAccount apiAccount = accountService.selectApiAccountById(arr[i]);
                            if (apiAccount != null) {
                                if (accountFlowerRecord.getRecordNum() > 0) {
                                    // 发送系统消息
                                    messageRemindService.insertMessageRemind(MessageRemind.builder()
                                            .title("红花奖励")
                                            .brief("恭喜！"+  apiAccount.getNickName() +"-"+ apiAccount.getPostNames()+" 获得平台赠送的"+accountFlowerRecord.getRecordNum()+"个小红花，理由："+accountFlowerRecord.getRecordName())
                                            .modelStatus(1)
                                            .jumpType(-4)
                                            .modelId("0")
                                            .accountId(account1.getAccountId())
                                            .build());
                                }
                                if(accountFlowerRecord.getRecordNum() < 0){
                                    // 发送系统消息
                                    messageRemindService.insertMessageRemind(MessageRemind.builder()
                                            .title("红花扣除")
                                            .brief("很遗憾！"+  apiAccount.getNickName() +"-"+ apiAccount.getPostNames()+" 平台"+accountFlowerRecord.getRecordNum()+"个小红花，理由："+accountFlowerRecord.getRecordName())
                                            .modelStatus(1)
                                            .jumpType(-4)
                                            .modelId("0")
                                            .accountId(account1.getAccountId())
                                            .build());
                                }
                            }
                        }
                    }
                }
            }
        };
    }
}
