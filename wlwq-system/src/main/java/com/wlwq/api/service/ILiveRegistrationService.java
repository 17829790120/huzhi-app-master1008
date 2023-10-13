package com.wlwq.api.service;

import java.util.List;
import com.wlwq.api.domain.LiveRegistration;

/**
 * 直播报名Service接口
 * 
 * @author wwb
 * @date 2023-05-15
 */
public interface ILiveRegistrationService {
    /**
     * 查询直播报名
     * 
     * @param liveRegistrationId 直播报名ID
     * @return 直播报名
     */
    public LiveRegistration selectLiveRegistrationById(String liveRegistrationId);

    /**
     * 查询直播报名列表
     * 
     * @param liveRegistration 直播报名
     * @return 直播报名集合
     */
    public List<LiveRegistration> selectLiveRegistrationList(LiveRegistration liveRegistration);

    /**
     * 新增直播报名
     * 
     * @param liveRegistration 直播报名
     * @return 结果
     */
    public int insertLiveRegistration(LiveRegistration liveRegistration);

    /**
     * 修改直播报名
     * 
     * @param liveRegistration 直播报名
     * @return 结果
     */
    public int updateLiveRegistration(LiveRegistration liveRegistration);

    /**
     * 批量删除直播报名
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteLiveRegistrationByIds(String ids);

    /**
     * 删除直播报名信息
     * 
     * @param liveRegistrationId 直播报名ID
     * @return 结果
     */
    public int deleteLiveRegistrationById(String liveRegistrationId);
}
