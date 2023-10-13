package com.wlwq.api.mapper;

import com.wlwq.api.domain.Banners;

import java.util.List;

/**
 * banner列表Mapper接口
 *
 * @author gaoce
 * @date 2022-11-24
 */
public interface BannersMapper {
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
     * 删除banner列表
     *
     * @param bannerId banner列表ID
     * @return 结果
     */
    public int deleteBannersById(String bannerId);

    /**
     * 批量删除banner列表
     *
     * @param bannerIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteBannersByIds(String[] bannerIds);

    /**
     * Api查询banner列表列表
     *
     * @param banners banner列表
     * @return banner列表集合
     */
    public List<Banners> selectApiBannersList(Banners banners);

}
