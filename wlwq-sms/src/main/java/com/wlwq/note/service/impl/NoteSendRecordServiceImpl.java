package com.wlwq.note.service.impl;

import com.wlwq.common.core.text.Convert;
import com.wlwq.common.utils.DateUtils;
import com.wlwq.note.domain.NoteSendRecord;
import com.wlwq.note.mapper.NoteSendRecordMapper;
import com.wlwq.note.service.INoteSendRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 短信发送记录Service业务层处理
 * 
 * @author Rick wlwq
 * @date 2021-07-07
 */
@Service
public class NoteSendRecordServiceImpl implements INoteSendRecordService 
{
    @Autowired
    private NoteSendRecordMapper noteSendRecordMapper;

    /**
     * 查询短信发送记录
     * 
     * @param noteSendRecordId 短信发送记录ID
     * @return 短信发送记录
     */
    @Override
    public NoteSendRecord selectNoteSendRecordById(Long noteSendRecordId)
    {
        return noteSendRecordMapper.selectNoteSendRecordById(noteSendRecordId);
    }

    /**
     * 查询短信发送记录列表
     * 
     * @param noteSendRecord 短信发送记录
     * @return 短信发送记录
     */
    @Override
    public List<NoteSendRecord> selectNoteSendRecordList(NoteSendRecord noteSendRecord)
    {
        return noteSendRecordMapper.selectNoteSendRecordList(noteSendRecord);
    }

    /**
     * 新增短信发送记录
     * 
     * @param noteSendRecord 短信发送记录
     * @return 结果
     */
    @Override
    public int insertNoteSendRecord(NoteSendRecord noteSendRecord)
    {
        noteSendRecord.setCreateTime(DateUtils.getNowDate());
        return noteSendRecordMapper.insertNoteSendRecord(noteSendRecord);
    }

    /**
     * 修改短信发送记录
     * 
     * @param noteSendRecord 短信发送记录
     * @return 结果
     */
    @Override
    public int updateNoteSendRecord(NoteSendRecord noteSendRecord)
    {
        return noteSendRecordMapper.updateNoteSendRecord(noteSendRecord);
    }

    /**
     * 删除短信发送记录对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteNoteSendRecordByIds(String ids)
    {
        return noteSendRecordMapper.deleteNoteSendRecordByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除短信发送记录信息
     * 
     * @param noteSendRecordId 短信发送记录ID
     * @return 结果
     */
    @Override
    public int deleteNoteSendRecordById(Long noteSendRecordId)
    {
        return noteSendRecordMapper.deleteNoteSendRecordById(noteSendRecordId);
    }
}
