package com.wlwq.api.service.impl;

import java.util.List;

import cn.hutool.core.util.IdUtil;
import com.wlwq.common.annotation.DataScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.api.mapper.LiveVideoMapper;
import com.wlwq.api.domain.LiveVideo;
import com.wlwq.api.service.ILiveVideoService;
import com.wlwq.common.core.text.Convert;

/**
 * 直播列表Service业务层处理
 *
 * @author wwb
 * @date 2023-05-13
 */
@Service
public class LiveVideoServiceImpl implements ILiveVideoService {

    @Autowired
    private LiveVideoMapper liveVideoMapper;

    /**
     * 查询直播列表
     *
     * @param liveVideoId 直播列表ID
     * @return 直播列表
     */
    @Override
    public LiveVideo selectLiveVideoById(String liveVideoId) {
        return liveVideoMapper.selectLiveVideoById(liveVideoId);
    }

    /**
     * 查询直播列表列表
     *
     * @param liveVideo 直播列表
     * @return 直播列表
     */
    @DataScope(deptAlias = "d")
    @Override
    public List<LiveVideo> selectLiveVideoList(LiveVideo liveVideo) {
        return liveVideoMapper.selectLiveVideoList(liveVideo);
    }

    /**
     * 新增直播列表
     *
     * @param liveVideo 直播列表
     * @return 结果
     */
    @Override
    public int insertLiveVideo(LiveVideo liveVideo) {
        liveVideo.setLiveVideoId(IdUtil.getSnowflake(1, 1).nextIdStr());
        return liveVideoMapper.insertLiveVideo(liveVideo);
    }

    /**
     * 修改直播列表
     *
     * @param liveVideo 直播列表
     * @return 结果
     */
    @Override
    public int updateLiveVideo(LiveVideo liveVideo) {
        return liveVideoMapper.updateLiveVideo(liveVideo);
    }

    /**
     * 删除直播列表对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteLiveVideoByIds(String ids) {
        return liveVideoMapper.deleteLiveVideoByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除直播列表信息
     *
     * @param liveVideoId 直播列表ID
     * @return 结果
     */
    @Override
    public int deleteLiveVideoById(String liveVideoId) {
        return liveVideoMapper.deleteLiveVideoById(liveVideoId);
    }
}
