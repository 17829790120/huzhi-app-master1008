package com.wlwq.api.service;

import java.util.List;

import com.wlwq.api.domain.CounsellingTheoreticalPost;

/**
 * 理论体系资讯Service接口
 *
 * @author wwb
 * @date 2023-05-18
 */
public interface ICounsellingTheoreticalPostService {
    /**
     * 查询理论体系资讯
     *
     * @param counsellingTheoreticalPostId 理论体系资讯ID
     * @return 理论体系资讯
     */
    public CounsellingTheoreticalPost selectCounsellingTheoreticalPostById(String counsellingTheoreticalPostId);

    /**
     * 查询理论体系资讯列表
     *
     * @param counsellingTheoreticalPost 理论体系资讯
     * @return 理论体系资讯集合
     */
    public List<CounsellingTheoreticalPost> selectCounsellingTheoreticalPostList(CounsellingTheoreticalPost counsellingTheoreticalPost);

    /**
     * 新增理论体系资讯
     *
     * @param counsellingTheoreticalPost 理论体系资讯
     * @return 结果
     */
    public int insertCounsellingTheoreticalPost(CounsellingTheoreticalPost counsellingTheoreticalPost);

    /**
     * 修改理论体系资讯
     *
     * @param counsellingTheoreticalPost 理论体系资讯
     * @return 结果
     */
    public int updateCounsellingTheoreticalPost(CounsellingTheoreticalPost counsellingTheoreticalPost);

    /**
     * 批量删除理论体系资讯
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteCounsellingTheoreticalPostByIds(String ids);

    /**
     * 删除理论体系资讯信息
     *
     * @param counsellingTheoreticalPostId 理论体系资讯ID
     * @return 结果
     */
    public int deleteCounsellingTheoreticalPostById(String counsellingTheoreticalPostId);
}
