package com.wlwq.information.mapper;

import java.util.List;
import java.util.Map;

import com.wlwq.information.domain.InformationCommentRecord;
import org.apache.ibatis.annotations.Param;

/**
 * 资讯评论记录Mapper接口
 *
 * @author Rick wlwq
 * @date 2021-04-21
 */
public interface InformationCommentRecordMapper {
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
     * 删除资讯评论记录
     *
     * @param informationCommentId 资讯评论记录ID
     * @return 结果
     */
    public int deleteInformationCommentRecordById(Long informationCommentId);

    /**
     * 批量删除资讯评论记录
     *
     * @param informationCommentIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteInformationCommentRecordByIds(String[] informationCommentIds);

    /**
     * 查询子集ID
     *
     * @param parentId
     * @return
     */
    String selectIdsByParentId(@Param("parentId") Long parentId);
    /**
     * 查询当前对象的评论列表
     * @param informationPostId
     * @param hostOrNewStatus
     * @return
     */
    List<Map<String, Object>> selectCommentListByPostId(@Param("informationPostId")Long informationPostId,@Param("hostOrNewStatus") String hostOrNewStatus);
    /**
     * 评论回复列表
     * @param informationCommentId
     * @return
     */
    List<Map<String, Object>> selectCommentReplyListByPrimaryId(Long informationCommentId);
}
