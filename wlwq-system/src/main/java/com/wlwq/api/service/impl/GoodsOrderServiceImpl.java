package com.wlwq.api.service.impl;

import java.util.List;
import cn.hutool.core.util.IdUtil;
import com.wlwq.common.utils.DateUtils;
import com.wlwq.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.api.mapper.GoodsOrderMapper;
import com.wlwq.api.domain.GoodsOrder;
import com.wlwq.api.service.IGoodsOrderService;
import com.wlwq.common.core.text.Convert;

/**
 * 订单信息Service业务层处理
 * 
 * @author wwb
 * @date 2023-05-25
 */
@Service
public class GoodsOrderServiceImpl implements IGoodsOrderService {

    @Autowired
    private GoodsOrderMapper goodsOrderMapper;

    /**
     * 查询订单信息
     * 
     * @param orderId 订单信息ID
     * @return 订单信息
     */
    @Override
    public GoodsOrder selectGoodsOrderById(String orderId) {
        return goodsOrderMapper.selectGoodsOrderById(orderId);
    }

    /**
     * 查询订单信息列表
     * 
     * @param goodsOrder 订单信息
     * @return 订单信息
     */
    @Override
    public List<GoodsOrder> selectGoodsOrderList(GoodsOrder goodsOrder) {
        return goodsOrderMapper.selectGoodsOrderList(goodsOrder);
    }

    /**
     * 新增订单信息
     * 
     * @param goodsOrder 订单信息
     * @return 结果
     */
    @Override
    public int insertGoodsOrder(GoodsOrder goodsOrder) {
        if(StringUtils.isBlank(goodsOrder.getOrderId())){
            goodsOrder.setOrderId(IdUtil.getSnowflake(1,1).nextIdStr());
        }
        goodsOrder.setCreateTime(DateUtils.getNowDate());
        return goodsOrderMapper.insertGoodsOrder(goodsOrder);
    }

    /**
     * 修改订单信息
     * 
     * @param goodsOrder 订单信息
     * @return 结果
     */
    @Override
    public int updateGoodsOrder(GoodsOrder goodsOrder) {
        return goodsOrderMapper.updateGoodsOrder(goodsOrder);
    }

    /**
     * 删除订单信息对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteGoodsOrderByIds(String ids) {
        return goodsOrderMapper.deleteGoodsOrderByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除订单信息信息
     * 
     * @param orderId 订单信息ID
     * @return 结果
     */
    @Override
    public int deleteGoodsOrderById(String orderId) {
        return goodsOrderMapper.deleteGoodsOrderById(orderId);
    }
}
