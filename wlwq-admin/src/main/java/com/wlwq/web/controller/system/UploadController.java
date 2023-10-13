package com.wlwq.web.controller.system;

import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.wlwq.annotation.PassToken;
import com.wlwq.api.domain.Attachments;
import com.wlwq.api.domain.UploadResult;
import com.wlwq.api.service.IAttachmentsService;
import com.wlwq.api.service.IOptionsService;
import com.wlwq.common.apiResult.ApiResult;
import com.wlwq.common.constant.Constants;
import com.wlwq.common.enums.AttachmentType;
import com.wlwq.handlers.file.FileHandler;
import com.wlwq.handlers.file.FileHandlers;
import com.wlwq.handlers.properties.QiniuOssProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName UploadController
 * @Description 上传
 * @Date 2020/9/27 1:40
 * Create By Renbowen
 */
@RestController
@RequestMapping(value = "/app")
@Slf4j
public class UploadController {

    @Autowired
    private IAttachmentsService attachmentService;
    @Autowired
    private FileHandlers fileHandlers;

    public static final String FILE_SEPARATOR = File.separator;

    /**
     * 上传图片
     * @param files
     * @return
     */
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Map<String, Object> upload(@RequestPart("file") MultipartFile[] files) {
        Map<String, Object> map = new HashMap<>();
        StringBuilder pathUrls = new StringBuilder();
        try {
            for (MultipartFile file : files) {
                // Upload single file
                AttachmentType attachmentType = getAttachmentType();

                log.debug("Starting uploading... type: [{}], file: [{}]", attachmentType, file.getOriginalFilename());

                // Upload file
                UploadResult uploadResult = fileHandlers.upload(file, attachmentType);

                log.debug("Attachment type: [{}]", attachmentType);
                log.debug("Upload result: [{}]", uploadResult);
                if (pathUrls.length() <= 0){
                    pathUrls.append(uploadResult.getFilePath());
                }else {
                    pathUrls.append(",").append(uploadResult.getFilePath());
                }
                //本地使用
                if (attachmentType.equals(AttachmentType.LOCAL)) {
                    pathUrls.insert(0, Constants.RESOURCE_PREFIX + FILE_SEPARATOR);
                }
                //服务器使用
/*                if (attachmentType.equals(AttachmentType.LOCAL)) {
                    pathUrls.insert(0, ONLINE_ADDRESS + FILE_SEPARATOR);
                }*/
            }
            log.info("上传后的图片路径：{{}}",pathUrls.toString());
            map.put("message", pathUrls.toString());
            map.put("status", "success");
        }catch (Exception e){
            map.put("message", e.getMessage());
            map.put("status", "error");
        }
        return map;
    }

    @Autowired
    private IOptionsService optionService;
    /**
     * 获取上传Token
     * @return
     */
    @PassToken
    @GetMapping("/uploadToken")
    public ApiResult uploadToken(){
        String accessKey = optionService.selectValueByKey(QiniuOssProperties.OSS_ACCESS_KEY.getValue());
        String secretKey = optionService.selectValueByKey(QiniuOssProperties.OSS_SECRET_KEY.getValue());
        String bucket = optionService.selectValueByKey(QiniuOssProperties.OSS_BUCKET.getValue());
        Auth auth = Auth.create(accessKey, secretKey);
        // Build put plicy
        StringMap putPolicy = new StringMap();
        putPolicy.put("returnBody", "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"size\":$(fsize),\"width\":$(imageInfo.width),\"height\":$(imageInfo.height)}");

        String uploadToken = auth.uploadToken(bucket, null, 60 * 60, putPolicy);

        return ApiResult.ok(uploadToken);
    }

    @PostMapping(value = "/uploads", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<Attachments> uploadAttachments(@RequestPart("files") MultipartFile[] files) {
        List<Attachments> result = new LinkedList<>();

        for (MultipartFile file : files) {
            // Upload single file
            Attachments attachment = upload(file);
            result.add(attachment);
        }

        return result;
    }

    public Attachments upload(MultipartFile file) {
        Assert.notNull(file, "Multipart file must not be null");

        AttachmentType attachmentType = getAttachmentType();

        log.debug("Starting uploading... type: [{}], file: [{}]", attachmentType, file.getOriginalFilename());

        // Upload file
        UploadResult uploadResult = fileHandlers.upload(file, attachmentType);

        log.debug("Attachment type: [{}]", attachmentType);
        log.debug("Upload result: [{}]", uploadResult);

        // Build attachment
        Attachments attachment = new Attachments();
        attachment.setName(uploadResult.getFilename());
        // Convert separator
        attachment.setPath(changeFileSeparatorToUrlSeparator(uploadResult.getFilePath()));
        attachment.setFileKey(uploadResult.getKey());
        attachment.setThumbPath(uploadResult.getThumbPath());
        attachment.setMediaType(uploadResult.getMediaType().toString());
        attachment.setSuffix(uploadResult.getSuffix());
        attachment.setWidth(uploadResult.getWidth());
        attachment.setHeight(uploadResult.getHeight());
        attachment.setSize(uploadResult.getSize());
        attachment.setType(attachmentType.getValue());

        log.debug("Creating attachment: [{}]", attachment);
        // Create and return
        attachmentService.insertAttachments(attachment);
        return attachment;
    }

    @Autowired
    private IOptionsService optionsService;

//    @RequestMapping(value = "/getA")
    public AttachmentType getAttachmentType() {
        String attachmentTypeValue =optionsService.selectValueByKey("attachment_type");
        if ("LOCAL".equals(attachmentTypeValue)){
            return AttachmentType.LOCAL;
        }
        if ("UPOSS".equals(attachmentTypeValue)){
            return AttachmentType.UPOSS;
        }
        if ("QINIUOSS".equals(attachmentTypeValue)){
            return AttachmentType.QINIUOSS;
        }
        if ("SMMS".equals(attachmentTypeValue)){
            return AttachmentType.SMMS;
        }
        if ("ALIOSS".equals(attachmentTypeValue)){
            return AttachmentType.ALIOSS;
        }
        if ("BAIDUBOS".equals(attachmentTypeValue)){
            return AttachmentType.BAIDUBOS;
        }
        if ("TENCENTCOS".equals(attachmentTypeValue)){
            return AttachmentType.TENCENTCOS;
        }
        return AttachmentType.HUAWEIOBS;
    }


    public static String changeFileSeparatorToUrlSeparator(@NonNull String pathname) {
        Assert.hasText(pathname, "Path name must not be blank");

        return pathname.replace(FileHandler.FILE_SEPARATOR, "/");
    }
}
