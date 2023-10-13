package com.wlwq.service;

import com.wlwq.PrinterConstant;
import com.wlwq.common.exception.BusinessException;
import com.wlwq.config.PrinterConfig;
import com.wlwq.params.PrinterAddParams;
import com.wlwq.params.PrinterParams;
import com.wlwq.params.PrinterPlayVoiceParams;
import com.wlwq.params.Product;
import com.wlwq.utils.NoteFormatter;
import net.xpyun.platform.opensdk.service.PrintService;
import net.xpyun.platform.opensdk.util.HashSignUtil;
import net.xpyun.platform.opensdk.vo.*;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @ClassName PrinterService
 * @Description 打印机服务封装
 * @Date 2021/1/16 11:54
 * @Author Rick wlwq
 */
public class PrinterService {

    /**
     * 打印服务对象实例化
     */
    public static PrintService service = new PrintService();


    /**
    *  @Description 添加打印机
    *  @author Rick wlwq
    *  @Date   2021/1/16 11:56
    *  @Param  printerSn 打印机编号
    *  @Param  printerName 打印机名字（填商户名字即可）
    */
    public static void addPrinter(List<PrinterAddParams> list) {
        AddPrinterRequest request = new AddPrinterRequest();
        // 添加公共参数
        PrinterConfig.createRequestHeader(request);
        String s =request.getSign();
        //打印机列表
        List<AddPrinterRequestItem> itemList = new ArrayList<>();
        for (PrinterAddParams params : list) {
            AddPrinterRequestItem item = new AddPrinterRequestItem();
            // 打印机编号，必须是真实的打印机编号，否在会导致后续api无法打印
            item.setSn(params.getPrinterSn());
            //打印机名称
            item.setName(params.getPrinterName());
            itemList.add(item);
        }
        //*必填*：items:数组元素为 json 对象：
        //{"name":"打印机名称","sn":"打印机编号"}
        //其中打印机编号 sn 和名称 name 字段为必填项，每次最多添加50台
        AddPrinterRequestItem[] items = new AddPrinterRequestItem[itemList.size()];
        itemList.toArray(items);
        request.setItems(items);
        try {
            ObjectRestResponse<PrinterResult> resp = service.addPrinters(request);
            //resp.data:返回1个 json 对象，包含成功和失败的信息，详看https://www.xpyun.net/open/index.html示例
            // resp 返回值
            // ObjectRestResponse(code=0, msg=ok, data=PrinterResult(success=[00D5E1X8D2A0B4A], fail=[]), serverExecutedTime=38)ObjectRestResponse(code=0, msg=ok, data=PrinterResult(success=[00D5E1X8D2A0B4A], fail=[]), serverExecutedTime=38)
            // ObjectRestResponse(code=0, msg=ok, data=PrinterResult(success=[00D5E1X8D2A0B4A], fail=[]), serverExecutedTime=35)
            if (resp.getCode() != PrinterConstant.SUCCESS_CODE){
                throw new BusinessException("打印机绑定失败！");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    /**
    *  @Description 设置打印机语音类型
    *  @author Rick wlwq
    *  @Date   2021/1/16 14:55
    *  @Param   声音类型： 0真人语音（大） 1真人语音（中） 2真人语音（小） 3 嘀嘀声  4 静音
    */
    public static void setVoiceType(String printerSn,Integer voiceType) {
        SetVoiceTypeRequest request = new SetVoiceTypeRequest();
        PrinterConfig.createRequestHeader(request);
        //*必填*：打印机编号
        request.setSn(printerSn);
        //*必填*：声音类型： 0真人语音（大） 1真人语音（中） 2真人语音（小） 3 嘀嘀声  4 静音
        request.setVoiceType(voiceType);
        ObjectRestResponse<Boolean> resp = service.setPrinterVoiceType(request);
        //resp.data:返回布尔类型：true 表示设置成功 false 表示设置失败
        // resp值  ObjectRestResponse(code=0, msg=ok, data=true, serverExecutedTime=30)ObjectRestResponse(code=0, msg=ok, data=true, serverExecutedTime=30)
        if (!resp.getData()){
            throw new BusinessException("设置打印机语音类型失败！");
        }
    }

    /**
    *  @Description 批量删除打印机
    *  @author Rick wlwq
    *  @Date   2021/1/16 15:28
    *  @Param  printerSns 打印机编号集合，类型为字符串数组
    */
    public static void delPrinters(String[] printerSns) {
        ObjectRestResponse<PrinterResult> resp = service.delPrinters(setRequest(printerSns));
        //resp.data:返回1个 json 对象，包含成功和失败的信息，详看https://www.xpyun.net/open/index.html示例
        System.out.println(resp);
        // resp返回值
        // ObjectRestResponse(code=0, msg=ok, data=PrinterResult(success=[00D5E1X8D2A0B4A], fail=[]), serverExecutedTime=62)ObjectRestResponse(code=0, msg=ok, data=PrinterResult(success=[00D5E1X8D2A0B4A], fail=[]), serverExecutedTime=62)
        if (resp.getCode() != PrinterConstant.SUCCESS_CODE){
            throw new BusinessException("删除打印机失败！");
        }
    }


    /**
     *  @Description 单个删除打印机
     *  @author Rick wlwq
     *  @Date   2021/1/16 15:28
     *  @Param  printerSns 打印机编号集合，类型为字符串数组
     */
    public static void delPrinter(String printerSn) {
        ObjectRestResponse<PrinterResult> resp = service.delPrinters(setRequest(printerSn.split(",")));
        //resp.data:返回1个 json 对象，包含成功和失败的信息，详看https://www.xpyun.net/open/index.html示例
        System.out.println(resp);
        // resp返回值
        // ObjectRestResponse(code=0, msg=ok, data=PrinterResult(success=[00D5E1X8D2A0B4A], fail=[]), serverExecutedTime=62)ObjectRestResponse(code=0, msg=ok, data=PrinterResult(success=[00D5E1X8D2A0B4A], fail=[]), serverExecutedTime=62)
        if (resp.getCode() != PrinterConstant.SUCCESS_CODE){
            throw new BusinessException("删除打印机失败！");
        }
    }

    /**
     * 设置请求的打印机信息
     * @param printerSn
     * @return
     */
    public static DelPrinterRequest setRequest(String[] printerSn) {
        DelPrinterRequest request = new DelPrinterRequest();
        PrinterConfig.createRequestHeader(request);
        request.setUser(PrinterConfig.USER_NAME);
        request.setTimestamp(System.currentTimeMillis() + "");
        //*必填*：对参数 user + UserKEY + timestamp 拼接后（+号表示连接符）进行SHA1加密得到签名，值为40位小写字符串，其中 UserKEY 为用户开发者密钥
        request.setSign(HashSignUtil.sign(request.getUser() + PrinterConfig.USER_KEY + request.getTimestamp()));
        request.setSnlist(printerSn);
        return request;
    }

    /**
    *  @Description 修改打印机信息
    *  @author Rick wlwq
    *  @Date   2021/1/16 16:48
    */
    public static void updPrinter(PrinterAddParams params) {
        UpdPrinterRequest request = new UpdPrinterRequest();
        PrinterConfig.createRequestHeader(request);
        //*必填*：打印机编号
        request.setSn(params.getPrinterSn());
        //*必填*：打印机名称
        request.setName(params.getPrinterName());
        ObjectRestResponse<Boolean> resp = service.updPrinter(request);
        //resp.data:返回布尔类型：true 表示成功 false 表示失败
        // resp返回值
        // ObjectRestResponse(code=0, msg=ok, data=true, serverExecutedTime=29)ObjectRestResponse(code=0, msg=ok, data=true, serverExecutedTime=29)
        if (!resp.getData()){
            throw new BusinessException("修改打印机失败！");
        }
    }


    /**
    *  @Description 清空待打印队列
    *  @author Rick wlwq
    *  @Date   2021/1/16 15:43
    *  @Param
    */
    public static void delPrinterQueue(String printerSn) {
        PrinterRequest request = new PrinterRequest();
        PrinterConfig.createRequestHeader(request);
        //*必填*：打印机编号
        request.setSn(printerSn);
        ObjectRestResponse<Boolean> resp = service.delPrinterQueue(request);
        //resp.data:返回布尔类型：true 表示成功 false 表示失败
        // resp返回值
        // ObjectRestResponse(code=0, msg=ok, data=true, serverExecutedTime=37)ObjectRestResponse(code=0, msg=ok, data=true, serverExecutedTime=37)
        if (!resp.getData()){
            throw new BusinessException("清空待打印队列失败！");
        }
    }

    /**
    *  @Description 查询订单是否打印成功
    *  @author Rick wlwq
    *  @Date   2021/1/16 15:50
    *  @Param  orderId 订单编号，由“打印订单”接口返回
    *  @Return 已打印返回true,未打印返回false
    */
    public static boolean queryOrderState(String orderId) {
        QueryOrderStateRequest request = new QueryOrderStateRequest();
        PrinterConfig.createRequestHeader(request);
        request.setOrderId(orderId);
        ObjectRestResponse<Boolean> resp = service.queryOrderState(request);
        //resp.data:返回布尔类型,已打印返回true,未打印返回false
        return resp.getData();
    }


    /**
    *  @Description 查询打印机状态
    *  @author Rick wlwq
    *  @Date   2021/1/16 15:52
    *  @Param  printerSn 打印机编号
    *  @Return     0 表示离线  1 表示在线正常 2 表示在线缺纸   离线的判断是打印机与服务器失去联系超过 30 秒
    */
    public static Integer queryPrinterStatus(String printerSn) {
        PrinterRequest request = new PrinterRequest();
        PrinterConfig.createRequestHeader(request);
        //*必填*：打印机编号
        request.setSn(printerSn);
        ObjectRestResponse<Integer> resp = service.queryPrinterStatus(request);
        return resp.getData();
    }


    /**
    *  @Description 金额播报
    *  @author Rick wlwq
    *  @Date   2021/1/16 15:55
    *  @Param
    */
    public static void playVoice(PrinterPlayVoiceParams params) {
        VoiceRequest request = new VoiceRequest();
        PrinterConfig.createRequestHeader(request);
        //*必填*：打印机编号
        request.setSn(params.getPrinterSn());
        //支付方式：
        //取值范围41~55：
        //支付宝 41、微信 42、云支付 43、银联刷卡 44、银联支付 45、会员卡消费 46、会员卡充值 47、翼支付 48、成功收款 49、嘉联支付 50、壹钱包 51、京东支付 52、快钱支付 53、威支付 54、享钱支付 55
        request.setPayType(params.getPayType());
        //支付与否：
        //取值范围59~61：
        //退款 59 到账 60 消费 61。
        request.setPayMode(params.getPayMode());
        //支付金额：
        //最多允许保留2位小数。
        request.setMoney(params.getMoney());
        ObjectRestResponse<String> resp = service.playVoice(request);
        //resp.data:正确返回0
        System.out.println(resp);
        if (!resp.getData().equals(PrinterConstant.SUCCESS_VALUE)){
            throw new BusinessException("金额播报失败！");
        }
    }


    /**
    *  @Description 不播报金额打印小票
    *  @author Rick wlwq
    *  @Date   2021/1/16 16:47
    */
    public static String printComplexReceipt(PrinterParams params) throws Exception{
        StringBuilder printContent = new StringBuilder();
        printContent.append("<C>").append("<B>").append(params.getTitle()).append("</B>").append("<BR></C>");
        printContent.append("<BR>");

        printContent.append("商品").append(StringUtils.repeat(" ", 16))
                .append("数量").append(StringUtils.repeat(" ", 2))
                .append("单价").append(StringUtils.repeat(" ", 2))
                .append("<BR>");
        printContent.append(StringUtils.repeat("-", 32)).append("<BR>");
        for (Product product : params.getProductList()) {
            printContent.append(NoteFormatter.formatPrintOrderItem(product.getProductName(), product.getProductNumber(), product.getProductPrice()));
        }
        printContent.append(StringUtils.repeat("-", 32)).append("<BR>");
        printContent.append("<R>").append("合计：").append(params.getTotalPrice()).append("元").append("<BR></R>");
        printContent.append("<R>").append("优惠：").append(params.getDiscountPrice()).append("元").append("<BR></R>");
        printContent.append("<R>").append("实付：").append(params.getRealPrice()).append("元").append("<BR></R>");
        printContent.append("<BR>");
        printContent.append("<L>")
                .append("收货人姓名：").append(params.getAccountName()).append("<BR>")
                .append("收货人电话：").append(params.getAccountPhone()).append("<BR>")
                .append("收货人地址：").append(params.getAccountAddress()).append("<BR>")
                .append("下单时间：").append(params.getOrderTime()).append("<BR>")
                .append("备注：").append(params.getAccountRemark()).append("<BR>");
        PrintRequest request = new PrintRequest();
        PrinterConfig.createRequestHeader(request);
        //*必填*：打印机编号
        request.setSn(params.getPrinterSn());
        //*必填*：打印内容,不能超过12K
        request.setContent(printContent.toString());
        //打印份数，默认为1
        request.setCopies(params.getCopies());
        //打印模式：
        //值为 0 或不指定则会检查打印机是否在线，如果不在线 则不生成打印订单，直接返回设备不在线状态码；如果在线则生成打印订单，并返回打印订单号。
        //值为 1不检查打印机是否在线，直接生成打印订单，并返回打印订单号。如果打印机不在线，订单将缓存在打印队列中，打印机正常在线时会自动打印。
        request.setMode(1);
        ObjectRestResponse<String> resp = service.print(request);
        //resp.data:正确返回订单编号
        return resp.getData();
    }

    public static void main(String[] args) {
//        List<PrinterAddParams> list = new LinkedList<>();
//        list.add(PrinterAddParams.builder()
//                .printerName("Rick wlwq的蛋糕店")
//                .printerSn("00D5E1X8D2A0B4A")
//                .build());
//        addPrinter(list);
//        updPrinter(PrinterAddParams.builder()
//                .printerName("Rick wlwq的蛋糕店")
//                .printerSn("00D5E1X8D2A0B4A")
//                .build());
//        delPrinterQueue("00D5E1X8D2A0B4A");
//        System.out.println(queryPrinterStatus("00D5E1X8D2A0B4A"));
//        playVoice(PrinterPlayVoiceParams.builder()
//                .printerSn("00D5E1X8D2A0B4A")
//                .payType(42)
//                .payMode(60)
//                .money(100000000.00)
//                .build());
        try {
            List<Product> productList = new LinkedList<>();
            productList.add(Product.builder()
                    .productName("黄瓜")
                    .productNumber(1)
                    .productPrice(10.00)
                    .build());
            productList.add(Product.builder()
                    .productName("西红柿")
                    .productNumber(10)
                    .productPrice(100.00)
                    .build());
            productList.add(Product.builder()
                    .productName("西伯利亚矿业生产员基地生产的梨")
                    .productNumber(1)
                    .productPrice(1000.00)
                    .build());
            productList.add(Product.builder()
                    .productName("哈密瓜")
                    .productNumber(1)
                    .productPrice(19.99)
                    .build());
            productList.add(Product.builder()
                    .productName("柚子")
                    .productNumber(5)
                    .productPrice(99.00)
                    .build());
            productList.add(Product.builder()
                    .productName("茄子（五斤装）")
                    .productNumber(1)
                    .productPrice(8.00)
                    .build());
            productList.add(Product.builder()
                    .productName("橘子（1框）")
                    .productNumber(1)
                    .productPrice(59.50)
                    .build());
            productList.add(Product.builder()
                    .productName("不知道叫啥的商品但是名字想起的长一点试试能不能打印格式")
                    .productNumber(1)
                    .productPrice(10.00)
                    .build());
            productList.add(Product.builder()
                    .productName("优乐美奶茶（原味）-1件4个")
                    .productNumber(1)
                    .productPrice(15.00)
                    .build());
            productList.add(Product.builder()
                    .productName("哈密瓜")
                    .productNumber(1)
                    .productPrice(19.99)
                    .build());
            productList.add(Product.builder()
                    .productName("哈密瓜")
                    .productNumber(1)
                    .productPrice(19.99)
                    .build());
            productList.add(Product.builder()
                    .productName("哈密瓜")
                    .productNumber(1)
                    .productPrice(19.99)
                    .build());
            productList.add(Product.builder()
                    .productName("哈密瓜")
                    .productNumber(1)
                    .productPrice(19.99)
                    .build());
            productList.add(Product.builder()
                    .productName("哈密瓜")
                    .productNumber(1)
                    .productPrice(19.99)
                    .build());
            productList.add(Product.builder()
                    .productName("哈密瓜")
                    .productNumber(1)
                    .productPrice(19.99)
                    .build());
            printComplexReceipt(PrinterParams.builder()
                    .printerSn("00D5E1X8D2A0B4A")
                    .copies(5)
                    .title("红洋葱总店购物单")
                    .totalPrice("478.65")
                    .discountPrice("158.58")
                    .realPrice("289.78")
                    .accountAddress("陕西省西安市雁塔区唐延路禾盛京广C座1403室")
                    .accountPhone("199****5893")
                    .accountName("Rick wlwq")
                    .orderTime("2021-01-16 18:58:00")
                    .accountRemark("不要葱 不要葱 不要葱 不要葱 不要葱 不要葱 不要葱 不要葱 不要葱 不要葱 不要葱 不要葱")
                    .productList(productList)
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
