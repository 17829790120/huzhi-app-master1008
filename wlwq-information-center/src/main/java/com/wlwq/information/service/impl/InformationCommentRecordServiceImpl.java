package com.wlwq.information.service.impl;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import com.wlwq.common.core.domain.Ztree;
import com.wlwq.common.utils.DateUtils;
import com.wlwq.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.information.mapper.InformationCommentRecordMapper;
import com.wlwq.information.domain.InformationCommentRecord;
import com.wlwq.information.service.IInformationCommentRecordService;
import com.wlwq.common.core.text.Convert;

/**
 * 资讯评论记录Service业务层处理
 * 
 * @author Rick wlwq
 * @date 2021-04-21
 */
@Service
public class InformationCommentRecordServiceImpl implements IInformationCommentRecordService 
{
    @Autowired
    private InformationCommentRecordMapper informationCommentRecordMapper;

    /**
     * 查询资讯评论记录
     * 
     * @param informationCommentId 资讯评论记录ID
     * @return 资讯评论记录
     */
    @Override
    public InformationCommentRecord selectInformationCommentRecordById(Long informationCommentId)
    {
        return informationCommentRecordMapper.selectInformationCommentRecordById(informationCommentId);
    }

    /**
     * 查询资讯评论记录列表
     * 
     * @param informationCommentRecord 资讯评论记录
     * @return 资讯评论记录
     */
    @Override
    public List<InformationCommentRecord> selectInformationCommentRecordList(InformationCommentRecord informationCommentRecord)
    {
        return informationCommentRecordMapper.selectInformationCommentRecordList(informationCommentRecord);
    }

    /**
     * 新增资讯评论记录
     * 
     * @param informationCommentRecord 资讯评论记录
     * @return 结果
     */
    @Override
    public int insertInformationCommentRecord(InformationCommentRecord informationCommentRecord)
    {
        informationCommentRecord.setCreateTime(DateUtils.getNowDate());
        return informationCommentRecordMapper.insertInformationCommentRecord(informationCommentRecord);
    }

    /**
     * 修改资讯评论记录
     * 
     * @param informationCommentRecord 资讯评论记录
     * @return 结果
     */
    @Override
    public int updateInformationCommentRecord(InformationCommentRecord informationCommentRecord)
    {
        return informationCommentRecordMapper.updateInformationCommentRecord(informationCommentRecord);
    }

    /**
     * 删除资讯评论记录对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteInformationCommentRecordByIds(String ids)
    {
        return informationCommentRecordMapper.deleteInformationCommentRecordByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除资讯评论记录信息
     * 
     * @param informationCommentId 资讯评论记录ID
     * @return 结果
     */
    @Override
    public int deleteInformationCommentRecordById(Long informationCommentId)
    {
        // 查询该评论下面的子集id
        String ids = informationCommentRecordMapper.selectIdsByParentId(informationCommentId);
        if (StringUtils.isNotBlank(ids)){
            // 删除子集
            informationCommentRecordMapper.deleteInformationCommentRecordByIds(ids.split(","));
        }
        return informationCommentRecordMapper.deleteInformationCommentRecordById(informationCommentId);
    }

    /**
     * 查询资讯评论记录树列表
     * 
     * @return 所有资讯评论记录信息
     */
    @Override
    public List<Ztree> selectInformationCommentRecordTree()
    {
        List<InformationCommentRecord> informationCommentRecordList = informationCommentRecordMapper.selectInformationCommentRecordList(new InformationCommentRecord());
        List<Ztree> ztrees = new ArrayList<Ztree>();
        for (InformationCommentRecord informationCommentRecord : informationCommentRecordList)
        {
            Ztree ztree = new Ztree();
            ztree.setId(informationCommentRecord.getInformationCommentId());
            ztree.setpId(informationCommentRecord.getParentId());
            ztree.setName(informationCommentRecord.getCommentContent());
            ztree.setTitle(informationCommentRecord.getCommentContent());
            ztrees.add(ztree);
        }
        return ztrees;
    }
    /**
     * 查询当前对象的评论列表
     * @param informationPostId
     * @param hostOrNewStatus
     * @return
     */
    @Override
    public List<Map<String, Object>> selectCommentListByPostId(Long informationPostId, String hostOrNewStatus) {
        return informationCommentRecordMapper.selectCommentListByPostId(informationPostId,hostOrNewStatus);
    }

    /**
     * 评论回复列表
     * @param informationCommentId
     * @return
     */
    @Override
    public List<Map<String, Object>> selectCommentReplyListByPrimaryId(Long informationCommentId) {
        return informationCommentRecordMapper.selectCommentReplyListByPrimaryId(informationCommentId);
    }
}
