package com.wlwq.privatePhone.controller;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpUtil;
import com.wlwq.common.annotation.Log;
import com.wlwq.common.config.ServerConfig;
import com.wlwq.common.config.WlwqConfig;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.core.page.TableDataInfo;
import com.wlwq.common.enums.BusinessType;
import com.wlwq.common.exception.BusinessException;
import com.wlwq.common.utils.StringUtils;
import com.wlwq.common.utils.file.FileUploadUtils;
import com.wlwq.common.utils.poi.ExcelUtil;
import com.wlwq.privatePhone.axService.AXService;
import com.wlwq.privatePhone.domain.AxTicketRecord;
import com.wlwq.privatePhone.service.IAxTicketRecordService;
import com.wlwq.privatePhone.utils.MultipartFileUtil;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * AX话单记录Controller
 *
 * @author Rick wlwq
 * @date 2021-07-12
 */
@Controller
@RequestMapping("/privatePhone/axTicketRecord")
public class AxTicketRecordController extends BaseController {
    private String prefix = "privatePhone/axTicketRecord";

    @Autowired
    private IAxTicketRecordService axTicketRecordService;
    @Autowired
    private AXService axService;

    @RequiresPermissions("privatePhone:axTicketRecord:view")
    @GetMapping()
    public String axTicketRecord() {
        return prefix + "/axTicketRecord";
    }

    /**
     * 查询AX话单记录列表
     */
    @RequiresPermissions("privatePhone:axTicketRecord:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(AxTicketRecord axTicketRecord) {
        startPage();
        List<AxTicketRecord> list = axTicketRecordService.selectAxTicketRecordList(axTicketRecord);
        return getDataTable(list);
    }

    /**
     * 导出AX话单记录列表
     */
    @RequiresPermissions("privatePhone:axTicketRecord:export")
    @Log(title = "AX话单记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(AxTicketRecord axTicketRecord) {
        List<AxTicketRecord> list = axTicketRecordService.selectAxTicketRecordList(axTicketRecord);
        ExcelUtil<AxTicketRecord> util = new ExcelUtil<AxTicketRecord>(AxTicketRecord.class);
        return util.exportExcel(list, "axTicketRecord");
    }

    /**
     * 新增AX话单记录
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存AX话单记录
     */
    @RequiresPermissions("privatePhone:axTicketRecord:add")
    @Log(title = "AX话单记录", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(AxTicketRecord axTicketRecord) {
        return toAjax(axTicketRecordService.insertAxTicketRecord(axTicketRecord));
    }

    /**
     * 修改AX话单记录
     */
    @GetMapping("/edit/{axTicketRecordId}")
    public String edit(@PathVariable("axTicketRecordId") Long axTicketRecordId, ModelMap mmap) {
        AxTicketRecord axTicketRecord = axTicketRecordService.selectAxTicketRecordById(axTicketRecordId);
        mmap.put("axTicketRecord", axTicketRecord);
        return prefix + "/edit";
    }

    /**
     * 修改保存AX话单记录
     */
    @RequiresPermissions("privatePhone:axTicketRecord:edit")
    @Log(title = "AX话单记录", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(AxTicketRecord axTicketRecord) {
        return toAjax(axTicketRecordService.updateAxTicketRecord(axTicketRecord));
    }

    /**
     * 删除AX话单记录
     */
    @RequiresPermissions("privatePhone:axTicketRecord:remove")
    @Log(title = "AX话单记录", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(axTicketRecordService.deleteAxTicketRecordByIds(ids));
    }

    @RequiresPermissions("privatePhone:axTicketRecord:voice")
    @Log(title = "AX话单记录", businessType = BusinessType.UPDATE)
    @PostMapping("/getVoice")
    @ResponseBody
    public AjaxResult getVoice(Long axTicketRecordId) {
        AxTicketRecord axTicketRecord = axTicketRecordService.selectAxTicketRecordById(axTicketRecordId);
        if (StringUtils.isNull(axTicketRecord)) {
            return error("未获取到话单记录信息！");
        }
        // 获取相差多久
        long minute = DateUtil.between(axTicketRecord.getCreateTime(), new Date(), DateUnit.MINUTE);
        if (minute < 10) {
            return error("请十分钟后获取录音文件！");
        }
        long day = DateUtil.between(axTicketRecord.getCreateTime(), new Date(), DateUnit.DAY);
        if (day > 7) {
            return error("录音文件在华为云仅保存7天！当前已" + day + "天");
        }
        String huaweiVoiceUrl = axService.axGetRecordDownloadLink(axTicketRecord.getRecordDomain(), axTicketRecord.getRecordObjectName());
        String localFilePath = uploadLocal(huaweiVoiceUrl, axTicketRecord.getRecordObjectName());
        return toAjax(axTicketRecordService.updateAxTicketRecord(AxTicketRecord.builder()
                .axTicketRecordId(axTicketRecordId)
                .recordFileUrl(huaweiVoiceUrl)
                .recordFileUrlLocal(localFilePath)
                .build()));
    }

    @Autowired
    private ServerConfig serverConfig;

    private String uploadLocal(String huaweiVoiceUrl, String fileName) {
        byte[] bytes = HttpUtil.downloadBytes(huaweiVoiceUrl);
        String fullFilePath = WlwqConfig.getUploadPath() + "/" + fileName;
        File file = FileUtil.touch(fullFilePath);
        FileUtil.writeBytes(bytes, file);
        // 保存文件到本地
        MultipartFile multipartFile = MultipartFileUtil.getMulFileByPath(fullFilePath);
        // 上传文件路径
        String filePath = WlwqConfig.getUploadPath();
        // 上传并返回新文件名称
        String newFileName = null;
        try {
            newFileName = FileUploadUtils.upload(filePath, multipartFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return serverConfig.getUrl() + newFileName;
    }


}
