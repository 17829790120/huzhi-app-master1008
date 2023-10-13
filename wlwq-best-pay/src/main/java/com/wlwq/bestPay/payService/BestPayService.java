package com.wlwq.bestPay.payService;

import com.wlwq.bestPay.params.PayRequest;
import com.wlwq.bestPay.params.QueryRequest;
import com.wlwq.bestPay.params.RefundRequest;
import com.wlwq.bestPay.result.QueryOrderResult;
import com.wlwq.bestPay.result.RefundResult;

public interface BestPayService {

    /**
     * @Description 统一下单 拉起支付
     * @author Rick wlwq
     * @Date 2021/7/1 16:36
     */
    String placeOrder(PayRequest payRequest);

    /**
     * @Description 退款
     * @author Rick wlwq
     * @Date 2021/7/6 11:11
     */
    RefundResult refundOrder(RefundRequest refundRequest);

    /**
     * @Description 查询订单
     * @author Rick wlwq
     * @Date 2021/7/6 14:59
     */
    QueryOrderResult queryOrder(QueryRequest queryRequest);
}
