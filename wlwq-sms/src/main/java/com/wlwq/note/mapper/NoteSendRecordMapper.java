package com.wlwq.note.mapper;

import com.wlwq.note.domain.NoteSendRecord;

import java.util.List;

/**
 * 短信发送记录Mapper接口
 * 
 * @author Rick wlwq
 * @date 2021-07-07
 */
public interface NoteSendRecordMapper 
{
    /**
     * 查询短信发送记录
     * 
     * @param noteSendRecordId 短信发送记录ID
     * @return 短信发送记录
     */
    public NoteSendRecord selectNoteSendRecordById(Long noteSendRecordId);

    /**
     * 查询短信发送记录列表
     * 
     * @param noteSendRecord 短信发送记录
     * @return 短信发送记录集合
     */
    public List<NoteSendRecord> selectNoteSendRecordList(NoteSendRecord noteSendRecord);

    /**
     * 新增短信发送记录
     * 
     * @param noteSendRecord 短信发送记录
     * @return 结果
     */
    public int insertNoteSendRecord(NoteSendRecord noteSendRecord);

    /**
     * 修改短信发送记录
     * 
     * @param noteSendRecord 短信发送记录
     * @return 结果
     */
    public int updateNoteSendRecord(NoteSendRecord noteSendRecord);

    /**
     * 删除短信发送记录
     * 
     * @param noteSendRecordId 短信发送记录ID
     * @return 结果
     */
    public int deleteNoteSendRecordById(Long noteSendRecordId);

    /**
     * 批量删除短信发送记录
     * 
     * @param noteSendRecordIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteNoteSendRecordByIds(String[] noteSendRecordIds);
}
