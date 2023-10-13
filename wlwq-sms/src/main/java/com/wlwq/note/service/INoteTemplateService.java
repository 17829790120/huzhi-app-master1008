package com.wlwq.note.service;

import com.wlwq.note.domain.NoteTemplate;

import java.util.List;

/**
 * 短信模板Service接口
 * 
 * @author Rick wlwq
 * @date 2021-07-07
 */
public interface INoteTemplateService 
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
     * 批量删除短信模板
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteNoteTemplateByIds(String ids);

    /**
     * 删除短信模板信息
     * 
     * @param noteTemplateId 短信模板ID
     * @return 结果
     */
    public int deleteNoteTemplateById(String noteTemplateId);
}
