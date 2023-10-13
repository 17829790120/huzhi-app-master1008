package com.wlwq.api.mapper;

import java.util.List;

import com.wlwq.api.domain.ExperienceSharing;

/**
 * 心得分享Mapper接口
 *
 * @author wwb
 * @date 2023-05-05
 */
public interface ExperienceSharingMapper {
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
     * 删除心得分享
     *
     * @param experienceSharingId 心得分享ID
     * @return 结果
     */
    public int deleteExperienceSharingById(String experienceSharingId);

    /**
     * 批量删除心得分享
     *
     * @param experienceSharingIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteExperienceSharingByIds(String[] experienceSharingIds);
}
