package com.wlwq.api.service.impl;

import java.util.List;

import cn.hutool.core.util.IdUtil;
import com.wlwq.api.domain.*;
import com.wlwq.api.resultVO.score.AccountScoreResultVO;
import com.wlwq.api.service.IAccountAppellationService;
import com.wlwq.api.service.IAccountMedalService;
import com.wlwq.api.service.IMessageRemindService;
import com.wlwq.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.api.mapper.AccountMedalRecordMapper;
import com.wlwq.api.service.IAccountMedalRecordService;
import com.wlwq.common.core.text.Convert;

/**
 * 用户勋章记录Service业务层处理
 *
 * @author gaoce
 * @date 2023-06-09
 */
@Service
public class AccountMedalRecordServiceImpl implements IAccountMedalRecordService {

    @Autowired
    private AccountMedalRecordMapper accountMedalRecordMapper;
    @Autowired
    private IAccountMedalService accountMedalService;
    @Autowired
    private IAccountMedalRecordService accountMedalRecordService;
    @Autowired
    private IMessageRemindService messageRemindService;
    @Autowired
    private IAccountAppellationService accountAppellationService;

    /**
     * 查询用户勋章记录
     *
     * @param accountMedalRecordId 用户勋章记录ID
     * @return 用户勋章记录
     */
    @Override
    public AccountMedalRecord selectAccountMedalRecordById(String accountMedalRecordId) {
        return accountMedalRecordMapper.selectAccountMedalRecordById(accountMedalRecordId);
    }

    /**
     * 查询用户勋章记录列表
     *
     * @param accountMedalRecord 用户勋章记录
     * @return 用户勋章记录
     */
    @Override
    public List<AccountMedalRecord> selectAccountMedalRecordList(AccountMedalRecord accountMedalRecord) {
        return accountMedalRecordMapper.selectAccountMedalRecordList(accountMedalRecord);
    }

    /**
     * 新增用户勋章记录
     *
     * @param accountMedalRecord 用户勋章记录
     * @return 结果
     */
    @Override
    public int insertAccountMedalRecord(AccountMedalRecord accountMedalRecord) {
        accountMedalRecord.setAccountMedalRecordId(IdUtil.getSnowflake(1, 1).nextIdStr());
        accountMedalRecord.setCreateTime(DateUtils.getNowDate());
        return accountMedalRecordMapper.insertAccountMedalRecord(accountMedalRecord);
    }

    /**
     * 修改用户勋章记录
     *
     * @param accountMedalRecord 用户勋章记录
     * @return 结果
     */
    @Override
    public int updateAccountMedalRecord(AccountMedalRecord accountMedalRecord) {
        accountMedalRecord.setUpdateTime(DateUtils.getNowDate());
        return accountMedalRecordMapper.updateAccountMedalRecord(accountMedalRecord);
    }

    /**
     * 删除用户勋章记录对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteAccountMedalRecordByIds(String ids) {
        return accountMedalRecordMapper.deleteAccountMedalRecordByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除用户勋章记录信息
     *
     * @param accountMedalRecordId 用户勋章记录ID
     * @return 结果
     */
    @Override
    public int deleteAccountMedalRecordById(String accountMedalRecordId) {
        return accountMedalRecordMapper.deleteAccountMedalRecordById(accountMedalRecordId);
    }


    /**
     * 根据条件查询数量
     *
     * @param accountMedalRecord
     * @return
     */
    @Override
    public int selectAccountMedalRecordCountByParentId(AccountMedalRecord accountMedalRecord) {
        return accountMedalRecordMapper.selectAccountMedalRecordCountByParentId(accountMedalRecord);
    }

    /**
     *  根据条件查询二级勋章数量（去重）
     * @param accountMedalRecord
     * @return
     */
    @Override
    public int selectDistinctAccountMedalRecordCountByParentId(AccountMedalRecord accountMedalRecord){
        return accountMedalRecordMapper.selectDistinctAccountMedalRecordCountByParentId(accountMedalRecord);
    }

    /**
     * 更新勋章
     * account 用户信息
     * score 本次更新积分
     * remark 备注
     *
     * @return
     */
    @Override
    public void updateMedalRecord(ApiAccount account, Integer score, String remark,AccountMedal accountMedal) {
        // 将勋章二级更新到勋章记录表中
        accountMedalRecordService.insertAccountMedalRecord(AccountMedalRecord.builder()
                .accountMedalId(accountMedal.getAccountMedalId())
                .medalName(accountMedal.getMedalName())
                .accountId(account.getAccountId())
                .accountName(account.getNickName())
                .accountPhone(account.getPhone())
                .accountHead(account.getHeadPortrait())
                .alreadyIcon(accountMedal.getAlreadyIcon())
                .score(score)
                .deptId(account.getDeptId())
                .companyId(account.getCompanyId())
                .postIds(account.getPostIds())
                .remark(remark)
                .medalParentId(accountMedal.getParentId())
                .build());
        // 发送系统消息
        // 查询消息是否存在
        messageRemindService.insertMessageRemind(MessageRemind.builder()
                .title("勋章消息")
                .brief("恭喜您获得一枚勋章【" + accountMedal.getMedalName() + "】,同时获得" + score + "积分,点击查看")
                .modelStatus(1)
                .jumpType(-1)
                .modelId("0")
                .accountId(account.getAccountId())
                .build());
        // 查询未领取的一级勋章列表
        List<AccountMedal> medalList = accountMedalService.selectApiAccountMedalList(AccountMedal.builder().showStatus(1).accountId(account.getAccountId()).parentId(0L).build());
        for (AccountMedal record : medalList){
            //查询一级分类下面的二级勋章数量
            int allCount = accountMedalService.selectAccountMedalCount(AccountMedal.builder().showStatus(1).parentId(record.getAccountMedalId()).build());
            // 查询记录表里边的一级下面的二级勋章数量
            int alreadyCount = accountMedalRecordService.selectDistinctAccountMedalRecordCountByParentId(AccountMedalRecord.builder().accountId(account.getAccountId()).medalParentId(record.getAccountMedalId()).build());
            if (allCount != 0 && alreadyCount == allCount) {
                // 存入一级勋章
                // 将勋章一级更新到勋章记录表中
                accountMedalRecordService.insertAccountMedalRecord(AccountMedalRecord.builder()
                        .accountMedalId(record.getAccountMedalId())
                        .medalName(record.getMedalName())
                        .accountId(account.getAccountId())
                        .accountName(account.getNickName())
                        .accountPhone(account.getPhone())
                        .accountHead(account.getHeadPortrait())
                        .alreadyIcon(record.getAlreadyIcon())
                        .score(score)
                        .deptId(account.getDeptId())
                        .companyId(account.getCompanyId())
                        .postIds(account.getPostIds())
                        .remark(remark)
                        .medalParentId(0L)
                        .build());
                // 发送系统消息
                // 查询消息是否存在
                messageRemindService.insertMessageRemind(MessageRemind.builder()
                        .title("勋章消息")
                        .brief("恭喜您获得一枚勋章【" + record.getMedalName() + "】,同时获得" + score + "积分,点击查看")
                        .modelStatus(1)
                        .jumpType(-1)
                        .modelId("0")
                        .accountId(account.getAccountId())
                        .build());
            }
        }

    }


    /**
     * api查询勋章列表
     *
     * @param accountMedalRecord
     * @return
     */
    @Override
    public List<AccountMedalRecord> selectApiAccountMedalRecordList(AccountMedalRecord accountMedalRecord) {
        return accountMedalRecordMapper.selectApiAccountMedalRecordList(accountMedalRecord);
    }

    /**
     * 查询勋章排行榜
     * @param account
     * @return
     */
    @Override
    public List<AccountScoreResultVO> selectApiMedalAccountList(ApiAccount account){
        List<AccountScoreResultVO> resultVOList = accountMedalRecordMapper.selectApiMedalAccountList(account);
        resultVOList.forEach(e -> {
            // 查询积分称谓
            String appellationName = accountAppellationService.selectAccountAppellationName(AccountAppellation.builder()
                    .minScore(e.getTotalScore())
                    .maxScore(e.getTotalScore())
                    .appellationType(account.getAppellationType())
                    .build());
            e.setAppellationName(appellationName);
        });
        return resultVOList;
    }
}
