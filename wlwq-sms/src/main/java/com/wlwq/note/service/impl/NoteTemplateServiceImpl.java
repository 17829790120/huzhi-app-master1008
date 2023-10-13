package com.wlwq.note.service.impl;

import com.wlwq.common.core.text.Convert;
import com.wlwq.common.utils.DateUtils;
import com.wlwq.note.domain.NoteTemplate;
import com.wlwq.note.mapper.NoteTemplateMapper;
import com.wlwq.note.service.INoteTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 短信模板Service业务层处理
 * 
 * @author Rick wlwq
 * @date 2021-07-07
 */
@Service
public class NoteTemplateServiceImpl implements INoteTemplateService 
{
    @Autowired
    private NoteTemplateMapper noteTemplateMapper;

    /**
     * 查询短信模板
     * 
     * @param noteTemplateId 短信模板ID
     * @return 短信模板
     */
    @Override
    public NoteTemplate selectNoteTemplateById(String noteTemplateId)
    {
        return noteTemplateMapper.selectNoteTemplateById(noteTemplateId);
    }

    /**
     * 查询短信模板列表
     * 
     * @param noteTemplate 短信模板
     * @return 短信模板
     */
    @Override
    public List<NoteTemplate> selectNoteTemplateList(NoteTemplate noteTemplate)
    {
        return noteTemplateMapper.selectNoteTemplateList(noteTemplate);
    }

    /**
     * 新增短信模板
     * 
     * @param noteTemplate 短信模板
     * @return 结果
     */
    @Override
    public int insertNoteTemplate(NoteTemplate noteTemplate)
    {
        noteTemplate.setCreateTime(DateUtils.getNowDate());
        return noteTemplateMapper.insertNoteTemplate(noteTemplate);
    }

    /**
     * 修改短信模板
     * 
     * @param noteTemplate 短信模板
     * @return 结果
     */
    @Override
    public int updateNoteTemplate(NoteTemplate noteTemplate)
    {
        noteTemplate.setUpdateTime(DateUtils.getNowDate());
        return noteTemplateMapper.updateNoteTemplate(noteTemplate);
    }

    /**
     * 删除短信模板对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteNoteTemplateByIds(String ids)
    {
        return noteTemplateMapper.deleteNoteTemplateByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除短信模板信息
     * 
     * @param noteTemplateId 短信模板ID
     * @return 结果
     */
    @Override
    public int deleteNoteTemplateById(String noteTemplateId)
    {
        return noteTemplateMapper.deleteNoteTemplateById(noteTemplateId);
    }
}
