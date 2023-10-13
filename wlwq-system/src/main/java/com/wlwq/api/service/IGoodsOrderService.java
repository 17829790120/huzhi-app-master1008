package com.wlwq.api.service;

import java.util.List;

import com.wlwq.api.domain.GoodsOrder;

/**
 * 订单信息Service接口
 *
 * @author wwb
 * @date 2023-05-25
 */
public interface IGoodsOrderService {
    /**
     * 查询订单信息
     *
     * @param orderId 订单信息ID
     * @return 订单信息
     */
    public GoodsOrder selectGoodsOrderById(String orderId);

    /**
     * 查询订单信息列表
     *
     * @param goodsOrder 订单信息
     * @return 订单信息集合
     */
    public List<GoodsOrder> selectGoodsOrderList(GoodsOrder goodsOrder);

    /**
     * 新增订单信息
     *
     * @param goodsOrder 订单信息
     * @return 结果
     */
    public int insertGoodsOrder(GoodsOrder goodsOrder);

    /**
     * 修改订单信息
     *
     * @param goodsOrder 订单信息
     * @return 结果
     */
    public int updateGoodsOrder(GoodsOrder goodsOrder);

    /**
     * 批量删除订单信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteGoodsOrderByIds(String ids);

    /**
     * 删除订单信息信息
     *
     * @param orderId 订单信息ID
     * @return 结果
     */
    public int deleteGoodsOrderById(String orderId);
}
