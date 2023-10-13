package com.wlwq.information.service;

import java.util.List;
import com.wlwq.information.domain.InformationLikeRecord;

/**
 * 点赞记录Service接口
 * 
 * @author Rick wlwq
 * @date 2021-04-20
 */
public interface IInformationLikeRecordService 
{
    /**
     * 查询点赞记录
     * 
     * @param informationLikeRecordId 点赞记录ID
     * @return 点赞记录
     */
    public InformationLikeRecord selectInformationLikeRecordById(Long informationLikeRecordId);

    /**
     * 查询点赞记录列表
     * 
     * @param informationLikeRecord 点赞记录
     * @return 点赞记录集合
     */
    public List<InformationLikeRecord> selectInformationLikeRecordList(InformationLikeRecord informationLikeRecord);

    /**
     * 新增点赞记录
     * 
     * @param informationLikeRecord 点赞记录
     * @return 结果
     */
    public int insertInformationLikeRecord(InformationLikeRecord informationLikeRecord);

    /**
     * 修改点赞记录
     * 
     * @param informationLikeRecord 点赞记录
     * @return 结果
     */
    public int updateInformationLikeRecord(InformationLikeRecord informationLikeRecord);

    /**
     * 批量删除点赞记录
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteInformationLikeRecordByIds(String ids);

    /**
     * 删除点赞记录信息
     * 
     * @param informationLikeRecordId 点赞记录ID
     * @return 结果
     */
    public int deleteInformationLikeRecordById(Long informationLikeRecordId);

    /**
     * 查询用户是否点赞过
     * @param primaryId
     * @param accountId
     * @return
     */
    InformationLikeRecord selectInformationLikeByPrimaryIdAndAccountId(Long primaryId, String accountId);
}
