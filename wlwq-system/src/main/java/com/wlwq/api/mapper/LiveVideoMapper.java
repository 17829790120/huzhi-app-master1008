package com.wlwq.api.mapper;

import java.util.List;

import com.wlwq.api.domain.LiveVideo;

/**
 * 直播列表Mapper接口
 *
 * @author wwb
 * @date 2023-05-13
 */
public interface LiveVideoMapper {
    /**
     * 查询直播列表
     *
     * @param liveVideoId 直播列表ID
     * @return 直播列表
     */
    public LiveVideo selectLiveVideoById(String liveVideoId);

    /**
     * 查询直播列表列表
     *
     * @param liveVideo 直播列表
     * @return 直播列表集合
     */
    public List<LiveVideo> selectLiveVideoList(LiveVideo liveVideo);

    /**
     * 新增直播列表
     *
     * @param liveVideo 直播列表
     * @return 结果
     */
    public int insertLiveVideo(LiveVideo liveVideo);

    /**
     * 修改直播列表
     *
     * @param liveVideo 直播列表
     * @return 结果
     */
    public int updateLiveVideo(LiveVideo liveVideo);

    /**
     * 删除直播列表
     *
     * @param liveVideoId 直播列表ID
     * @return 结果
     */
    public int deleteLiveVideoById(String liveVideoId);

    /**
     * 批量删除直播列表
     *
     * @param liveVideoIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteLiveVideoByIds(String[] liveVideoIds);
}
