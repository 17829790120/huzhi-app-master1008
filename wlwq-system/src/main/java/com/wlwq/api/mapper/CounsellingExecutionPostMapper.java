package com.wlwq.api.mapper;

import java.util.List;

import com.wlwq.api.domain.CounsellingExecutionPost;

/**
 * 辅导实施资讯Mapper接口
 *
 * @author wwb
 * @date 2023-05-18
 */
public interface CounsellingExecutionPostMapper {
    /**
     * 查询辅导实施资讯
     *
     * @param counsellingExecutionPostId 辅导实施资讯ID
     * @return 辅导实施资讯
     */
    public CounsellingExecutionPost selectCounsellingExecutionPostById(String counsellingExecutionPostId);

    /**
     * 查询辅导实施资讯列表
     *
     * @param counsellingExecutionPost 辅导实施资讯
     * @return 辅导实施资讯集合
     */
    public List<CounsellingExecutionPost> selectCounsellingExecutionPostList(CounsellingExecutionPost counsellingExecutionPost);

    /**
     * 新增辅导实施资讯
     *
     * @param counsellingExecutionPost 辅导实施资讯
     * @return 结果
     */
    public int insertCounsellingExecutionPost(CounsellingExecutionPost counsellingExecutionPost);

    /**
     * 修改辅导实施资讯
     *
     * @param counsellingExecutionPost 辅导实施资讯
     * @return 结果
     */
    public int updateCounsellingExecutionPost(CounsellingExecutionPost counsellingExecutionPost);

    /**
     * 删除辅导实施资讯
     *
     * @param counsellingExecutionPostId 辅导实施资讯ID
     * @return 结果
     */
    public int deleteCounsellingExecutionPostById(String counsellingExecutionPostId);

    /**
     * 批量删除辅导实施资讯
     *
     * @param counsellingExecutionPostIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteCounsellingExecutionPostByIds(String[] counsellingExecutionPostIds);
}
