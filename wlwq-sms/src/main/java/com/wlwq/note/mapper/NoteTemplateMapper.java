package com.wlwq.note.mapper;

import com.wlwq.note.domain.NoteTemplate;

import java.util.List;

/**
 * 短信模板Mapper接口
 * 
 * @author Rick wlwq
 * @date 2021-07-07
 */
public interface NoteTemplateMapper 
{
    /**
     * 查询短信模板
     * 
     * @param noteTemplateId 短信模板ID
     * @return 短信模板
     */
    public NoteTemplate selectNoteTemplateById(String noteTemplateId);

    /**
     * 查询短信模板列表
     * 
     * @param noteTemplate 短信模板
     * @return 短信模板集合
     */
    public List<NoteTemplate> selectNoteTemplateList(NoteTemplate noteTemplate);

    /**
     * 新增短信模板
     * 
     * @param noteTemplate 短信模板
     * @return 结果
     */
    public int insertNoteTemplate(NoteTemplate noteTemplate);

    /**
     * 修改短信模板
     * 
     * @param noteTemplate 短信模板
     * @return 结果
     */
    public int updateNoteTemplate(NoteTemplate noteTemplate);

    /**
     * 删除短信模板
     * 
     * @param noteTemplateId 短信模板ID
     * @return 结果
     */
    public int deleteNoteTemplateById(String noteTemplateId);

    /**
     * 批量删除短信模板
     * 
     * @param noteTemplateIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteNoteTemplateByIds(String[] noteTemplateIds);
}
