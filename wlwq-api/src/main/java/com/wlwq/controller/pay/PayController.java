package com.wlwq.controller.pay;

import cn.hutool.core.util.IdUtil;
import com.wlwq.annotation.PassToken;
import com.wlwq.bestPay.enums.BestPayTypeEnum;
import com.wlwq.bestPay.params.PayRequest;
import com.wlwq.bestPay.params.QueryRequest;
import com.wlwq.bestPay.params.RefundRequest;
import com.wlwq.bestPay.payService.BestPayService;
import com.wlwq.common.apiResult.ApiController;
import com.wlwq.common.apiResult.ApiResult;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * @ClassName PayController
 * @Description 支付
 * @Date 2021/4/14 18:17
 * @Author Rick wlwq
 */
@RequestMapping(value = "/api/pay")
@RestController
@AllArgsConstructor
public class PayController extends ApiController {

    /**
     * 支付中心服务
     */
    private final BestPayService payService;

    @RequestMapping(value = "/toPay", method = RequestMethod.POST)
    @PassToken
    public ApiResult toPay(BestPayTypeEnum bestPayTypeEnum) {
        return ok(payService.placeOrder(PayRequest.builder()
//                .appId("wx76f6d5ecd6056697")
                .appId("2021002148659928")
//                .payTypeEnum(BestPayTypeEnum.getByCode(params.getPayType()))
                .payTypeEnum(bestPayTypeEnum)
                .timeOutValue(10)
                .orderId(IdUtil.getSnowflake(0, 0).nextIdStr())
                .orderAmount(BigDecimal.valueOf(0.01))
                .orderName("商品购买")
                .moduleName("goods")
                .build()));
    }


    @RequestMapping(value = "/refund", method = RequestMethod.POST)
    @PassToken
    public ApiResult refund(String tranId) {
        return ok(payService.refundOrder(RefundRequest.builder()
//                .appId("wx76f6d5ecd6056697")
                .appId("2021002148659928")
//                .payTypeEnum(BestPayTypeEnum.WXPAY_APP)
                .payTypeEnum(BestPayTypeEnum.ALIPAY_APP)
                .tradeNo(tranId)
                .refundOrderId(IdUtil.getSnowflake(0, 0).nextIdStr())
                .totalAmount(BigDecimal.valueOf(0.01))
                .refundAmount(BigDecimal.valueOf(0.01))
                .refundReason("不想要了")
                .opUserId("admin")
                .build()));
    }


    @RequestMapping(value = "/query", method = RequestMethod.POST)
    @PassToken
    public ApiResult query(String tranId) {
        return ok(payService.queryOrder(QueryRequest.builder()
                //                .appId("wx76f6d5ecd6056697")
                .appId("2021002148659928")
//                .payTypeEnum(BestPayTypeEnum.WXPAY_APP)
                .payTypeEnum(BestPayTypeEnum.ALIPAY_APP)
                .tradeNo(tranId)
                .build()));
    }


}
