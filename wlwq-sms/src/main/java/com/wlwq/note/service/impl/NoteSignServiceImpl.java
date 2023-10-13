package com.wlwq.note.service.impl;

import com.wlwq.common.core.text.Convert;
import com.wlwq.common.utils.DateUtils;
import com.wlwq.note.domain.NoteSign;
import com.wlwq.note.mapper.NoteSignMapper;
import com.wlwq.note.service.INoteSignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 短信签名Service业务层处理
 * 
 * @author Rick wlwq
 * @date 2021-07-07
 */
@Service
public class NoteSignServiceImpl implements INoteSignService 
{
    @Autowired
    private NoteSignMapper noteSignMapper;

    /**
     * 查询短信签名
     * 
     * @param noteSignId 短信签名ID
     * @return 短信签名
     */
    @Override
    public NoteSign selectNoteSignById(String noteSignId)
    {
        return noteSignMapper.selectNoteSignById(noteSignId);
    }

    /**
     * 查询短信签名列表
     * 
     * @param noteSign 短信签名
     * @return 短信签名
     */
    @Override
    public List<NoteSign> selectNoteSignList(NoteSign noteSign)
    {
        return noteSignMapper.selectNoteSignList(noteSign);
    }

    /**
     * 新增短信签名
     * 
     * @param noteSign 短信签名
     * @return 结果
     */
    @Override
    public int insertNoteSign(NoteSign noteSign)
    {
        noteSign.setCreateTime(DateUtils.getNowDate());
        return noteSignMapper.insertNoteSign(noteSign);
    }

    /**
     * 修改短信签名
     * 
     * @param noteSign 短信签名
     * @return 结果
     */
    @Override
    public int updateNoteSign(NoteSign noteSign)
    {
        noteSign.setUpdateTime(DateUtils.getNowDate());
        return noteSignMapper.updateNoteSign(noteSign);
    }

    /**
     * 删除短信签名对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteNoteSignByIds(String ids)
    {
        return noteSignMapper.deleteNoteSignByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除短信签名信息
     * 
     * @param noteSignId 短信签名ID
     * @return 结果
     */
    @Override
    public int deleteNoteSignById(String noteSignId)
    {
        return noteSignMapper.deleteNoteSignById(noteSignId);
    }
}
