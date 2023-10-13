package com.wlwq.api.service.impl;
import com.wlwq.api.domain.Attachments;
import com.wlwq.api.mapper.AttachmentsMapper;
import com.wlwq.api.service.IAttachmentsService;
import com.wlwq.common.core.text.Convert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 附件Service业务层处理
 *
 * @author Renbowen
 * @date 2020-09-26
 */
@Service
@Slf4j
public class AttachmentsServiceImpl implements IAttachmentsService
{
    @Autowired
    private AttachmentsMapper attachmentsMapper;

    /**
     * 查询附件
     *
     * @param id 附件ID
     * @return 附件
     */
    @Override
    public Attachments selectAttachmentsById(Long id)
    {
        return attachmentsMapper.selectAttachmentsById(id);
    }

    /**
     * 查询附件列表
     *
     * @param attachments 附件
     * @return 附件
     */
    @Override
    public List<Attachments> selectAttachmentsList(Attachments attachments)
    {
        return attachmentsMapper.selectAttachmentsList(attachments);
    }

    /**
     * 新增附件
     *
     * @param attachments 附件
     * @return 结果
     */
    @Override
    public int insertAttachments(Attachments attachments)
    {
        return attachmentsMapper.insertAttachments(attachments);
    }

    /**
     * 修改附件
     *
     * @param attachments 附件
     * @return 结果
     */
    @Override
    public int updateAttachments(Attachments attachments)
    {
        return attachmentsMapper.updateAttachments(attachments);
    }

    /**
     * 删除附件对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteAttachmentsByIds(String ids)
    {
        return attachmentsMapper.deleteAttachmentsByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除附件信息
     *
     * @param id 附件ID
     * @return 结果
     */
    @Override
    public int deleteAttachmentsById(Long id)
    {
        return attachmentsMapper.deleteAttachmentsById(id);
    }
}
