
package com.wlwq.common.apiResult;

import cn.hutool.core.convert.ConverterRegistry;
import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Random;


@Slf4j
public class ApiController {

    /**
     * 七牛云图片缩略图后缀
     */
    public static final String QI_NIU_IMAGE_THUMBANIL = "?imageView/1/w/240/h/240";

    /**
     * 七牛云视频缩略图后缀
     */
    public static final String QI_NIU_VIDEO_THUMBANIL = "?vframe/jpg/offset/1";


    /**
     * HuTool类型转换器
     */
    public static final ConverterRegistry CONVERTER_REGISTRY = ConverterRegistry.getInstance();

    /**
     * <p>
     * 请求成功
     * </p>
     *
     * @param data 数据内容
     * @param <T>  对象泛型
     * @return
     */
    protected <T> ApiResult<T> ok(T data) {
        return  ApiResult.ok(data);
    }

    /**
     * <p>
     * 请求失败
     * </p>
     *
     * @param msg 提示内容
     * @return
     */
    protected ApiResult<Object> fail(String msg) {
        return  ApiResult.fail(msg);
    }

    /**
     * <p>
     * 请求失败
     * </p>
     *
     * @param apiCode 请求错误码
     * @return
     */
    protected ApiResult<Object> fail(ApiCode apiCode) {
        return  ApiResult.fail(apiCode);
    }


    /**
     * 生成订单流水号
     * @param accountId
     * @return
     */
    public static String generateOrderSerialNumber(Long accountId) {
        return DateUtil.format(DateUtil.date(),"yyyyMMddHHmmss") + accountId + new Random().nextInt(1000);
    }

    /**
     * 判断Object对象为空或空字符串
     *
     * @param obj
     * @return
     */
    public static Boolean isObjectNotEmpty(Object obj) {
        String str = ObjectUtils.toString(obj, "");
        Boolean flag = StringUtils.isNotBlank(str);
        return flag;
    }

    /**
     * 获取远程文件大小
     *
     * @param fileUrl 云端文件地址
     * @return
     */
    public static Integer fileSize(String fileUrl) throws IOException {
        int size = 0;
        URL url = new URL(fileUrl);
        URLConnection conn = url.openConnection();
        size = conn.getContentLength();
//        if (size < 0)
//            System.out.println("无法获取文件大小。");
//        else
//            System.out.println("文件大小为：" + size + " bytes");
        conn.getInputStream().close();
        return size;
    }
}
