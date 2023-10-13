package com.wlwq.api.service;

import java.util.List;

import com.wlwq.api.domain.ExperienceSharing;

/**
 * 心得分享Service接口
 *
 * @author wwb
 * @date 2023-05-05
 */
public interface IExperienceSharingService {
    /**
     * 查询心得分享
     *
     * @param experienceSharingId 心得分享ID
     * @return 心得分享
     */
    public ExperienceSharing selectExperienceSharingById(String experienceSharingId);

    /**
     * 查询心得分享列表
     *
     * @param experienceSharing 心得分享
     * @return 心得分享集合
     */
    public List<ExperienceSharing> selectExperienceSharingList(ExperienceSharing experienceSharing);

    /**
     * 新增心得分享
     *
     * @param experienceSharing 心得分享
     * @return 结果
     */
    public int insertExperienceSharing(ExperienceSharing experienceSharing);

    /**
     * 修改心得分享
     *
     * @param experienceSharing 心得分享
     * @return 结果
     */
    public int updateExperienceSharing(ExperienceSharing experienceSharing);

    /**
     * 批量删除心得分享
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteExperienceSharingByIds(String ids);

    /**
     * 删除心得分享信息
     *
     * @param experienceSharingId 心得分享ID
     * @return 结果
     */
    public int deleteExperienceSharingById(String experienceSharingId);
}
