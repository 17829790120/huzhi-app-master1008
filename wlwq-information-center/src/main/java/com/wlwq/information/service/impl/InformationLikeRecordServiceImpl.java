package com.wlwq.information.service.impl;

import java.util.List;
import com.wlwq.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.information.mapper.InformationLikeRecordMapper;
import com.wlwq.information.domain.InformationLikeRecord;
import com.wlwq.information.service.IInformationLikeRecordService;
import com.wlwq.common.core.text.Convert;

/**
 * 点赞记录Service业务层处理
 * 
 * @author Rick wlwq
 * @date 2021-04-20
 */
@Service
public class InformationLikeRecordServiceImpl implements IInformationLikeRecordService 
{
    @Autowired
    private InformationLikeRecordMapper informationLikeRecordMapper;

    /**
     * 查询点赞记录
     * 
     * @param informationLikeRecordId 点赞记录ID
     * @return 点赞记录
     */
    @Override
    public InformationLikeRecord selectInformationLikeRecordById(Long informationLikeRecordId)
    {
        return informationLikeRecordMapper.selectInformationLikeRecordById(informationLikeRecordId);
    }

    /**
     * 查询点赞记录列表
     * 
     * @param informationLikeRecord 点赞记录
     * @return 点赞记录
     */
    @Override
    public List<InformationLikeRecord> selectInformationLikeRecordList(InformationLikeRecord informationLikeRecord)
    {
        return informationLikeRecordMapper.selectInformationLikeRecordList(informationLikeRecord);
    }

    /**
     * 新增点赞记录
     * 
     * @param informationLikeRecord 点赞记录
     * @return 结果
     */
    @Override
    public int insertInformationLikeRecord(InformationLikeRecord informationLikeRecord)
    {
        informationLikeRecord.setCreateTime(DateUtils.getNowDate());
        return informationLikeRecordMapper.insertInformationLikeRecord(informationLikeRecord);
    }

    /**
     * 修改点赞记录
     * 
     * @param informationLikeRecord 点赞记录
     * @return 结果
     */
    @Override
    public int updateInformationLikeRecord(InformationLikeRecord informationLikeRecord)
    {
        return informationLikeRecordMapper.updateInformationLikeRecord(informationLikeRecord);
    }

    /**
     * 删除点赞记录对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteInformationLikeRecordByIds(String ids)
    {
        return informationLikeRecordMapper.deleteInformationLikeRecordByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除点赞记录信息
     * 
     * @param informationLikeRecordId 点赞记录ID
     * @return 结果
     */
    @Override
    public int deleteInformationLikeRecordById(Long informationLikeRecordId)
    {
        return informationLikeRecordMapper.deleteInformationLikeRecordById(informationLikeRecordId);
    }
    /**
     * 查询用户是否点赞过
     * @param primaryId
     * @param accountId
     * @return
     */
    @Override
    public InformationLikeRecord selectInformationLikeByPrimaryIdAndAccountId(Long primaryId, String accountId) {
        return informationLikeRecordMapper.selectInformationLikeByPrimaryIdAndAccountId(primaryId,accountId);
    }
}
