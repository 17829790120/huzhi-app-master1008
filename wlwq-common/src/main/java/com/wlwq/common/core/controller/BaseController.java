package com.wlwq.common.core.controller;

import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.internal.SignUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.core.domain.AjaxResult.Type;
import com.wlwq.common.core.page.PageDomain;
import com.wlwq.common.core.page.TableDataInfo;
import com.wlwq.common.core.page.TableSupport;
import com.wlwq.common.utils.DateUtils;
import com.wlwq.common.utils.ServletUtils;
import com.wlwq.common.utils.StringUtils;
import com.wlwq.common.utils.sql.SqlUtil;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * web层通用数据处理
 * 
 * @author wlwq
 */
public class BaseController
{
    protected final Logger logger = LoggerFactory.getLogger(BaseController.class);

    private final static RestTemplate restTemplate=new RestTemplate();

    /**
     * 将前台传递过来的日期格式的字符串，自动转化为Date类型
     */
    @InitBinder
    public void initBinder(WebDataBinder binder)
    {
        // Date 类型转换
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport()
        {
            @Override
            public void setAsText(String text)
            {
                setValue(DateUtils.parseDate(text));
            }
        });
    }

    /**
     * 设置请求分页数据
     */
    protected void startPage()
    {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        if (StringUtils.isNotNull(pageNum) && StringUtils.isNotNull(pageSize))
        {
            String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
            PageHelper.startPage(pageNum, pageSize, orderBy);
        }
    }

    /**
     * 设置请求排序数据
     */
    protected void startOrderBy()
    {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        if (StringUtils.isNotEmpty(pageDomain.getOrderBy()))
        {
            String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
            PageHelper.orderBy(orderBy);
        }
    }

    /**
     * 获取request
     */
    public HttpServletRequest getRequest()
    {
        return ServletUtils.getRequest();
    }

    /**
     * 获取response
     */
    public HttpServletResponse getResponse()
    {
        return ServletUtils.getResponse();
    }

    /**
     * 获取session
     */
    public HttpSession getSession()
    {
        return getRequest().getSession();
    }

    /**
     * 响应请求分页数据
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected TableDataInfo getDataTable(List<?> list)
    {
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(0);
        rspData.setRows(list);
        rspData.setTotal(new PageInfo(list).getTotal());
        return rspData;
    }

    /**
     * 响应返回结果
     * 
     * @param rows 影响行数
     * @return 操作结果
     */
    protected AjaxResult toAjax(int rows)
    {
        return rows > 0 ? success() : error();
    }

    /**
     * 响应返回结果
     * 
     * @param result 结果
     * @return 操作结果
     */
    protected AjaxResult toAjax(boolean result)
    {
        return result ? success() : error();
    }

    /**
     * 返回成功
     */
    public AjaxResult success()
    {
        return AjaxResult.success();
    }

    /**
     * 返回失败消息
     */
    public AjaxResult error()
    {
        return AjaxResult.error();
    }

    /**
     * 返回成功消息
     */
    public AjaxResult success(String message)
    {
        return AjaxResult.success(message);
    }

    /**
     * 返回失败消息
     */
    public AjaxResult error(String message)
    {
        return AjaxResult.error(message);
    }

    /**
     * 返回错误码消息
     */
    public AjaxResult error(Type type, String message)
    {
        return new AjaxResult(type, message);
    }

    /**
     * 页面跳转
     */
    public String redirect(String url)
    {
        return StringUtils.format("redirect:{}", url);
    }


    /**
     * 获取远程文件大小
     *
     * @param fileUrl 云端文件地址
     * @return
     */
    public static  Map<String,Object> fileSize(String fileUrl) throws IOException {
        Map<String,Object> map = new HashMap<>(2);
        int size = 0;
        URL url = new URL(fileUrl);
        URLConnection conn = url.openConnection();
        size = conn.getContentLength();
        String type =conn.getContentType();
        conn.getInputStream().close();
        map.put("size",size);
        map.put("type",type);
        return map;
    }

    /**
     *  获取七牛云视频的详细信息（时长，大小）
     * @return
     */
    public static JSONObject getFileInfor(String url) {
        JSONObject jsonObject = null;
        try {
            Map resultMap = restTemplate.getForEntity(url, Map.class).getBody();
            if (resultMap.get("format")!=null) {
                String data = JSON.toJSONString(resultMap.get("format"));
                jsonObject = JSONObject.parseObject(data);
            }
        } catch (RestClientException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

}
