package com.wlwq.note.mapper;

import com.wlwq.note.domain.NoteSign;

import java.util.List;

/**
 * 短信签名Mapper接口
 * 
 * @author Rick wlwq
 * @date 2021-07-07
 */
public interface NoteSignMapper 
{
    /**
     * 查询短信签名
     * 
     * @param noteSignId 短信签名ID
     * @return 短信签名
     */
    public NoteSign selectNoteSignById(String noteSignId);

    /**
     * 查询短信签名列表
     * 
     * @param noteSign 短信签名
     * @return 短信签名集合
     */
    public List<NoteSign> selectNoteSignList(NoteSign noteSign);

    /**
     * 新增短信签名
     * 
     * @param noteSign 短信签名
     * @return 结果
     */
    public int insertNoteSign(NoteSign noteSign);

    /**
     * 修改短信签名
     * 
     * @param noteSign 短信签名
     * @return 结果
     */
    public int updateNoteSign(NoteSign noteSign);

    /**
     * 删除短信签名
     * 
     * @param noteSignId 短信签名ID
     * @return 结果
     */
    public int deleteNoteSignById(String noteSignId);

    /**
     * 批量删除短信签名
     * 
     * @param noteSignIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteNoteSignByIds(String[] noteSignIds);
}
