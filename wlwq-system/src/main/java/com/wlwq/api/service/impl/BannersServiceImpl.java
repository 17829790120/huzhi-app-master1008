package com.wlwq.api.service.impl;

import cn.hutool.core.util.IdUtil;
import com.wlwq.api.domain.Banners;
import com.wlwq.api.mapper.BannersMapper;
import com.wlwq.api.service.IBannersService;
import com.wlwq.common.annotation.DataScope;
import com.wlwq.common.core.text.Convert;
import com.wlwq.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * banner列表Service业务层处理
 *
 * @author gaoce
 * @date 2022-11-24
 */
@Service
public class BannersServiceImpl implements IBannersService {
    @Autowired
    private BannersMapper bannersMapper;

    /**
     * 查询banner列表
     *
     * @param bannerId banner列表ID
     * @return banner列表
     */
    @Override
    public Banners selectBannersById(String bannerId) {
        return bannersMapper.selectBannersById(bannerId);
    }

    /**
     * 查询banner列表列表
     *
     * @param banners banner列表
     * @return banner列表
     */
    @DataScope(deptAlias = "d")
    @Override
    public List<Banners> selectBannersList(Banners banners) {
        return bannersMapper.selectBannersList(banners);
    }

    /**
     * 新增banner列表
     *
     * @param banners banner列表
     * @return 结果
     */
    @Override
    public int insertBanners(Banners banners) {
        banners.setBannerId(IdUtil.getSnowflake(1,1).nextIdStr());
        banners.setCreateTime(DateUtils.getNowDate());
        return bannersMapper.insertBanners(banners);
    }

    /**
     * 修改banner列表
     *
     * @param banners banner列表
     * @return 结果
     */
    @Override
    public int updateBanners(Banners banners) {
        banners.setUpdateTime(DateUtils.getNowDate());
        return bannersMapper.updateBanners(banners);
    }

    /**
     * 删除banner列表对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteBannersByIds(String ids) {
        return bannersMapper.deleteBannersByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除banner列表信息
     *
     * @param bannerId banner列表ID
     * @return 结果
     */
    @Override
    public int deleteBannersById(String bannerId) {
        return bannersMapper.deleteBannersById(bannerId);
    }

    /**
     * Api查询banner列表列表
     *
     * @param banners banner列表
     * @return banner列表集合
     */
    @Override
    public List<Banners> selectApiBannersList(Banners banners){
        return bannersMapper.selectApiBannersList(banners);
    }
}
