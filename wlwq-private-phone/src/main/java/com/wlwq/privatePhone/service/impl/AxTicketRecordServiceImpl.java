package com.wlwq.privatePhone.service.impl;

import java.util.List;
import com.wlwq.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.privatePhone.mapper.AxTicketRecordMapper;
import com.wlwq.privatePhone.domain.AxTicketRecord;
import com.wlwq.privatePhone.service.IAxTicketRecordService;
import com.wlwq.common.core.text.Convert;

/**
 * AX话单记录Service业务层处理
 * 
 * @author Rick wlwq
 * @date 2021-07-12
 */
@Service
public class AxTicketRecordServiceImpl implements IAxTicketRecordService 
{
    @Autowired
    private AxTicketRecordMapper axTicketRecordMapper;

    /**
     * 查询AX话单记录
     * 
     * @param axTicketRecordId AX话单记录ID
     * @return AX话单记录
     */
    @Override
    public AxTicketRecord selectAxTicketRecordById(Long axTicketRecordId)
    {
        return axTicketRecordMapper.selectAxTicketRecordById(axTicketRecordId);
    }

    /**
     * 查询AX话单记录列表
     * 
     * @param axTicketRecord AX话单记录
     * @return AX话单记录
     */
    @Override
    public List<AxTicketRecord> selectAxTicketRecordList(AxTicketRecord axTicketRecord)
    {
        return axTicketRecordMapper.selectAxTicketRecordList(axTicketRecord);
    }

    /**
     * 新增AX话单记录
     * 
     * @param axTicketRecord AX话单记录
     * @return 结果
     */
    @Override
    public int insertAxTicketRecord(AxTicketRecord axTicketRecord)
    {
        axTicketRecord.setCreateTime(DateUtils.getNowDate());
        return axTicketRecordMapper.insertAxTicketRecord(axTicketRecord);
    }

    /**
     * 修改AX话单记录
     * 
     * @param axTicketRecord AX话单记录
     * @return 结果
     */
    @Override
    public int updateAxTicketRecord(AxTicketRecord axTicketRecord)
    {
        return axTicketRecordMapper.updateAxTicketRecord(axTicketRecord);
    }

    /**
     * 删除AX话单记录对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteAxTicketRecordByIds(String ids)
    {
        return axTicketRecordMapper.deleteAxTicketRecordByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除AX话单记录信息
     * 
     * @param axTicketRecordId AX话单记录ID
     * @return 结果
     */
    @Override
    public int deleteAxTicketRecordById(Long axTicketRecordId)
    {
        return axTicketRecordMapper.deleteAxTicketRecordById(axTicketRecordId);
    }
}
