package com.wlwq.privatePhone.service;

import java.util.List;
import com.wlwq.privatePhone.domain.AxTicketRecord;

/**
 * AX话单记录Service接口
 * 
 * @author Rick wlwq
 * @date 2021-07-12
 */
public interface IAxTicketRecordService 
{
    /**
     * 查询AX话单记录
     * 
     * @param axTicketRecordId AX话单记录ID
     * @return AX话单记录
     */
    public AxTicketRecord selectAxTicketRecordById(Long axTicketRecordId);

    /**
     * 查询AX话单记录列表
     * 
     * @param axTicketRecord AX话单记录
     * @return AX话单记录集合
     */
    public List<AxTicketRecord> selectAxTicketRecordList(AxTicketRecord axTicketRecord);

    /**
     * 新增AX话单记录
     * 
     * @param axTicketRecord AX话单记录
     * @return 结果
     */
    public int insertAxTicketRecord(AxTicketRecord axTicketRecord);

    /**
     * 修改AX话单记录
     * 
     * @param axTicketRecord AX话单记录
     * @return 结果
     */
    public int updateAxTicketRecord(AxTicketRecord axTicketRecord);

    /**
     * 批量删除AX话单记录
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteAxTicketRecordByIds(String ids);

    /**
     * 删除AX话单记录信息
     * 
     * @param axTicketRecordId AX话单记录ID
     * @return 结果
     */
    public int deleteAxTicketRecordById(Long axTicketRecordId);
}
