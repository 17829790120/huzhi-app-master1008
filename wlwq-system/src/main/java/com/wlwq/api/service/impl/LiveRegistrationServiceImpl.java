package com.wlwq.api.service.impl;

import java.util.List;

import cn.hutool.core.util.IdUtil;
import com.wlwq.common.annotation.DataScope;
import com.wlwq.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.api.mapper.LiveRegistrationMapper;
import com.wlwq.api.domain.LiveRegistration;
import com.wlwq.api.service.ILiveRegistrationService;
import com.wlwq.common.core.text.Convert;

/**
 * 直播报名Service业务层处理
 *
 * @author wwb
 * @date 2023-05-15
 */
@Service
public class LiveRegistrationServiceImpl implements ILiveRegistrationService {

    @Autowired
    private LiveRegistrationMapper liveRegistrationMapper;

    /**
     * 查询直播报名
     *
     * @param liveRegistrationId 直播报名ID
     * @return 直播报名
     */
    @Override
    public LiveRegistration selectLiveRegistrationById(String liveRegistrationId) {
        return liveRegistrationMapper.selectLiveRegistrationById(liveRegistrationId);
    }

    /**
     * 查询直播报名列表
     *
     * @param liveRegistration 直播报名
     * @return 直播报名
     */
    @DataScope(deptAlias = "d")
    @Override
    public List<LiveRegistration> selectLiveRegistrationList(LiveRegistration liveRegistration) {
        return liveRegistrationMapper.selectLiveRegistrationList(liveRegistration);
    }

    /**
     * 新增直播报名
     *
     * @param liveRegistration 直播报名
     * @return 结果
     */
    @Override
    public int insertLiveRegistration(LiveRegistration liveRegistration) {
        liveRegistration.setLiveRegistrationId(IdUtil.getSnowflake(1, 1).nextIdStr());
        liveRegistration.setCreateTime(DateUtils.getNowDate());
        return liveRegistrationMapper.insertLiveRegistration(liveRegistration);
    }

    /**
     * 修改直播报名
     *
     * @param liveRegistration 直播报名
     * @return 结果
     */
    @Override
    public int updateLiveRegistration(LiveRegistration liveRegistration) {
        liveRegistration.setUpdateTime(DateUtils.getNowDate());
        return liveRegistrationMapper.updateLiveRegistration(liveRegistration);
    }

    /**
     * 删除直播报名对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteLiveRegistrationByIds(String ids) {
        return liveRegistrationMapper.deleteLiveRegistrationByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除直播报名信息
     *
     * @param liveRegistrationId 直播报名ID
     * @return 结果
     */
    @Override
    public int deleteLiveRegistrationById(String liveRegistrationId) {
        return liveRegistrationMapper.deleteLiveRegistrationById(liveRegistrationId);
    }
}
