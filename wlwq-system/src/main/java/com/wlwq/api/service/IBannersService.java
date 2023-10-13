package com.wlwq.api.service;

import com.wlwq.api.domain.Banners;

import java.util.List;

/**
 * banner列表Service接口
 *
 * @author gaoce
 * @date 2022-11-24
 */
public interface IBannersService {
    /**
     * 查询banner列表
     *
     * @param bannerId banner列表ID
     * @return banner列表
     */
    public Banners selectBannersById(String bannerId);

    /**
     * 查询banner列表列表
     *
     * @param banners banner列表
     * @return banner列表集合
     */
    public List<Banners> selectBannersList(Banners banners);

    /**
     * 新增banner列表
     *
     * @param banners banner列表
     * @return 结果
     */
    public int insertBanners(Banners banners);

    /**
     * 修改banner列表
     *
     * @param banners banner列表
     * @return 结果
     */
    public int updateBanners(Banners banners);

    /**
     * 批量删除banner列表
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBannersByIds(String ids);

    /**
     * 删除banner列表信息
     *
     * @param bannerId banner列表ID
     * @return 结果
     */
    public int deleteBannersById(String bannerId);

    /**
     * Api查询banner列表列表
     *
     * @param banners banner列表
     * @return banner列表集合
     */
    public List<Banners> selectApiBannersList(Banners banners);
}
