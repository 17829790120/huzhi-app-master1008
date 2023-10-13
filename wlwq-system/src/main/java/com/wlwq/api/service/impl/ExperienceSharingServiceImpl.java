package com.wlwq.api.service.impl;

import java.util.List;

import cn.hutool.core.util.IdUtil;
import com.wlwq.common.annotation.DataScope;
import com.wlwq.common.utils.DateUtils;
import com.wlwq.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.api.mapper.ExperienceSharingMapper;
import com.wlwq.api.domain.ExperienceSharing;
import com.wlwq.api.service.IExperienceSharingService;
import com.wlwq.common.core.text.Convert;

/**
 * 心得分享Service业务层处理
 *
 * @author wwb
 * @date 2023-05-05
 */
@Service
public class ExperienceSharingServiceImpl implements IExperienceSharingService {

    @Autowired
    private ExperienceSharingMapper experienceSharingMapper;

    /**
     * 查询心得分享
     *
     * @param experienceSharingId 心得分享ID
     * @return 心得分享
     */
    @Override
    public ExperienceSharing selectExperienceSharingById(String experienceSharingId) {
        return experienceSharingMapper.selectExperienceSharingById(experienceSharingId);
    }

    /**
     * 查询心得分享列表
     *
     * @param experienceSharing 心得分享
     * @return 心得分享
     */
    @DataScope(deptAlias = "d")
    @Override
    public List<ExperienceSharing> selectExperienceSharingList(ExperienceSharing experienceSharing) {
        return experienceSharingMapper.selectExperienceSharingList(experienceSharing);
    }

    /**
     * 新增心得分享
     *
     * @param experienceSharing 心得分享
     * @return 结果
     */
    @Override
    public int insertExperienceSharing(ExperienceSharing experienceSharing) {
        if(StringUtils.isEmpty(experienceSharing.getExperienceSharingId())){
            experienceSharing.setExperienceSharingId(IdUtil.getSnowflake(1, 1).nextIdStr());
        }
        experienceSharing.setCreateTime(DateUtils.getNowDate());
        return experienceSharingMapper.insertExperienceSharing(experienceSharing);
    }

    /**
     * 修改心得分享
     *
     * @param experienceSharing 心得分享
     * @return 结果
     */
    @Override
    public int updateExperienceSharing(ExperienceSharing experienceSharing) {
        return experienceSharingMapper.updateExperienceSharing(experienceSharing);
    }

    /**
     * 删除心得分享对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteExperienceSharingByIds(String ids) {
        return experienceSharingMapper.deleteExperienceSharingByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除心得分享信息
     *
     * @param experienceSharingId 心得分享ID
     * @return 结果
     */
    @Override
    public int deleteExperienceSharingById(String experienceSharingId) {
        return experienceSharingMapper.deleteExperienceSharingById(experienceSharingId);
    }
}
