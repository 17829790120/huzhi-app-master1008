package com.wlwq.information.service;

import java.util.List;
import java.util.Map;

import com.wlwq.information.domain.InformationCommentRecord;
import com.wlwq.common.core.domain.Ztree;

/**
 * 资讯评论记录Service接口
 * 
 * @author Rick wlwq
 * @date 2021-04-21
 */
public interface IInformationCommentRecordService 
{
    /**
     * 查询资讯评论记录
     * 
     * @param informationCommentId 资讯评论记录ID
     * @return 资讯评论记录
     */
    public InformationCommentRecord selectInformationCommentRecordById(Long informationCommentId);

    /**
     * 查询资讯评论记录列表
     * 
     * @param informationCommentRecord 资讯评论记录
     * @return 资讯评论记录集合
     */
    public List<InformationCommentRecord> selectInformationCommentRecordList(InformationCommentRecord informationCommentRecord);

    /**
     * 新增资讯评论记录
     * 
     * @param informationCommentRecord 资讯评论记录
     * @return 结果
     */
    public int insertInformationCommentRecord(InformationCommentRecord informationCommentRecord);

    /**
     * 修改资讯评论记录
     * 
     * @param informationCommentRecord 资讯评论记录
     * @return 结果
     */
    public int updateInformationCommentRecord(InformationCommentRecord informationCommentRecord);

    /**
     * 批量删除资讯评论记录
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteInformationCommentRecordByIds(String ids);

    /**
     * 删除资讯评论记录信息
     * 
     * @param informationCommentId 资讯评论记录ID
     * @return 结果
     */
    public int deleteInformationCommentRecordById(Long informationCommentId);

    /**
     * 查询资讯评论记录树列表
     * 
     * @return 所有资讯评论记录信息
     */
    public List<Ztree> selectInformationCommentRecordTree();
    /**
     * 查询当前对象的评论列表
     * @param informationPostId
     * @param hostOrNewStatus
     * @return
     */
    List<Map<String, Object>> selectCommentListByPostId(Long informationPostId, String hostOrNewStatus);
    /**
     * 评论回复列表
     * @param informationCommentId
     * @return
     */
    List<Map<String, Object>> selectCommentReplyListByPrimaryId(Long informationCommentId);
}
