package com.wlwq.api.mapper;


import com.wlwq.api.domain.Attachments;

import java.util.List;

/**
 * 附件Mapper接口
 *
 * @author Renbowen
 * @date 2020-09-26
 */
public interface AttachmentsMapper
{
    /**
     * 查询附件
     *
     * @param id 附件ID
     * @return 附件
     */
    public Attachments selectAttachmentsById(Long id);

    /**
     * 查询附件列表
     *
     * @param attachments 附件
     * @return 附件集合
     */
    public List<Attachments> selectAttachmentsList(Attachments attachments);

    /**
     * 新增附件
     *
     * @param attachments 附件
     * @return 结果
     */
    public int insertAttachments(Attachments attachments);

    /**
     * 修改附件
     *
     * @param attachments 附件
     * @return 结果
     */
    public int updateAttachments(Attachments attachments);

    /**
     * 删除附件
     *
     * @param id 附件ID
     * @return 结果
     */
    public int deleteAttachmentsById(Long id);

    /**
     * 批量删除附件
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteAttachmentsByIds(String[] ids);
}
