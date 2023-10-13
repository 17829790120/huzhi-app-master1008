package com.wlwq.web.controller.web;

import cn.hutool.core.date.DateUtil;
import com.wlwq.api.domain.ImageDemo;
import com.wlwq.api.service.IImageDemoService;
import com.wlwq.common.annotation.Log;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.core.page.TableDataInfo;
import com.wlwq.common.enums.BusinessType;
import com.wlwq.common.utils.poi.ExcelUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 图片视频上传示例Controller
 *
 * @author Renbowen
 * @date 2020-10-09
 */
@Controller
@RequestMapping("/web/imageDemo")
public class ImageDemoController extends BaseController
{
    private String prefix = "web/imageDemo";

    @Autowired
    private IImageDemoService imageDemoService;

    @RequiresPermissions("web:imageDemo:view")
    @GetMapping()
    public String imageDemo()
    {
        return prefix + "/imageDemo";
    }

    /**
     * 查询图片视频上传示例列表
     */
    @RequiresPermissions("web:imageDemo:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(ImageDemo imageDemo)
    {
        startPage();
        List<ImageDemo> list = imageDemoService.selectImageDemoList(imageDemo);
        for (ImageDemo h: list){
            h.setImagesStr(StringUtils.isBlank(h.getImages()) ? null : h.getImages().split(","));
            h.setVideosStr(StringUtils.isBlank(h.getVideos()) ? null : h.getVideos().split(","));
        }
        return getDataTable(list);
    }

    /**
     * 导出图片视频上传示例列表
     */
    @RequiresPermissions("web:imageDemo:export")
    @Log(title = "图片视频上传示例", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ImageDemo imageDemo)
    {
        List<ImageDemo> list = imageDemoService.selectImageDemoList(imageDemo);
        ExcelUtil<ImageDemo> util = new ExcelUtil<ImageDemo>(ImageDemo.class);
        return util.exportExcel(list, "imageDemo");
    }

    /**
     * 新增图片视频上传示例
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存图片视频上传示例
     */
    @RequiresPermissions("web:imageDemo:add")
    @Log(title = "图片视频上传示例", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(ImageDemo imageDemo)
    {
        imageDemo.setCreateDate(DateUtil.date());
        imageDemo.setLastDate(DateUtil.date());
        return toAjax(imageDemoService.insertImageDemo(imageDemo));
    }

    /**
     * 修改图片视频上传示例
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        ImageDemo imageDemo = imageDemoService.selectImageDemoById(id);
        mmap.put("imageDemo", imageDemo);
        return prefix + "/edit";
    }

    /**
     * 修改保存图片视频上传示例
     */
    @RequiresPermissions("web:imageDemo:edit")
    @Log(title = "图片视频上传示例", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ImageDemo imageDemo)
    {
        imageDemo.setLastDate(DateUtil.date());
        return toAjax(imageDemoService.updateImageDemo(imageDemo));
    }

    /**
     * 删除图片视频上传示例
     */
    @RequiresPermissions("web:imageDemo:remove")
    @Log(title = "图片视频上传示例", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(imageDemoService.deleteImageDemoByIds(ids));
    }
}
