package com.wlwq.handlers.file;

import com.wlwq.api.domain.UploadResult;
import com.wlwq.api.service.IOptionsService;
import com.wlwq.common.enums.AttachmentType;
import com.wlwq.common.exception.FileOperationException;
import com.wlwq.common.utils.FilenameUtils;
import com.wlwq.common.utils.ImageUtils;
import com.wlwq.handlers.properties.UpOssProperties;
import com.upyun.RestManager;
import com.upyun.UpException;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 *  Create By Renbowen
 *  @Date: 2020/9/29 19:23
 *  @Description: Up oss file handler.
 */
@Slf4j
@Component
public class UpOssFileHandler implements FileHandler {

    private final IOptionsService optionService;

    public UpOssFileHandler(IOptionsService optionService) {
        this.optionService = optionService;
    }

    @Override
    public @NotNull UploadResult upload(MultipartFile file) {
        Assert.notNull(file, "Multipart file must not be null");

        String source = optionService.selectValueByKey(UpOssProperties.OSS_SOURCE.getValue());
        String password = optionService.selectValueByKey(UpOssProperties.OSS_PASSWORD.getValue());
        String bucket = optionService.selectValueByKey(UpOssProperties.OSS_BUCKET.getValue());
        String protocol = optionService.selectValueByKey(UpOssProperties.OSS_PROTOCOL.getValue());
        String domain = optionService.selectValueByKey(UpOssProperties.OSS_DOMAIN.getValue());
        String operator = optionService.selectValueByKey(UpOssProperties.OSS_OPERATOR.getValue());
        // style rule can be null
        String styleRule = optionService.selectValueByKey(UpOssProperties.OSS_STYLE_RULE.getValue());
        String thumbnailStyleRule = optionService.selectValueByKey(UpOssProperties.OSS_THUMBNAIL_STYLE_RULE.getValue());

        RestManager manager = new RestManager(bucket, operator, password);
        manager.setTimeout(60 * 10);
        manager.setApiDomain(RestManager.ED_AUTO);

        Map<String, String> params = new HashMap<>();

        try {
            // Get file basename
            String basename = FilenameUtils.getBasename(Objects.requireNonNull(file.getOriginalFilename()));
            // Get file extension
            String extension = FilenameUtils.getExtension(file.getOriginalFilename());
            // Get md5 value of the file
            String md5OfFile = DigestUtils.md5DigestAsHex(file.getInputStream());
            // Build file path
            String upFilePath = StringUtils.appendIfMissing(source, "/") + md5OfFile + '.' + extension;
            // Set md5Content
            params.put(RestManager.PARAMS.CONTENT_MD5.getValue(), md5OfFile);
            // Write file
            Response result = manager.writeFile(upFilePath, file.getInputStream(), params);
            if (!result.isSuccessful()) {
                throw new FileOperationException("上传附件 " + file.getOriginalFilename() + " 到又拍云失败" + upFilePath);
            }

            String filePath = protocol + StringUtils.removeEnd(domain, "/") + upFilePath;

            // Build upload result
            UploadResult uploadResult = new UploadResult();
            uploadResult.setFilename(basename);
            uploadResult.setFilePath(StringUtils.isBlank(styleRule) ? filePath : filePath + styleRule);
            uploadResult.setKey(upFilePath);
            uploadResult.setMediaType(MediaType.valueOf(Objects.requireNonNull(file.getContentType())));
            uploadResult.setSuffix(extension);
            uploadResult.setSize(file.getSize());

            // Handle thumbnail
            if (FileHandler.isImageType(uploadResult.getMediaType())) {
                ImageReader image = ImageUtils.getImageReaderFromFile(file.getInputStream(), extension);
                assert image != null;
                uploadResult.setWidth(image.getWidth(0));
                uploadResult.setHeight(image.getHeight(0));
                if (ImageUtils.EXTENSION_ICO.equals(extension)) {
                    uploadResult.setThumbPath(filePath);
                } else {
                    uploadResult.setThumbPath(StringUtils.isBlank(thumbnailStyleRule) ? filePath : filePath + thumbnailStyleRule);
                }
            }

            return uploadResult;
        } catch (Exception e) {
            throw new FileOperationException("上传附件 " + file.getOriginalFilename() + " 到又拍云失败", e);
        }
    }

    @Override
    public void delete(String key) {
        Assert.notNull(key, "File key must not be blank");

        // Get config
        String password = optionService.selectValueByKey(UpOssProperties.OSS_PASSWORD.getValue());
        String bucket = optionService.selectValueByKey(UpOssProperties.OSS_BUCKET.getValue());
        String operator = optionService.selectValueByKey(UpOssProperties.OSS_OPERATOR.getValue());

        RestManager manager = new RestManager(bucket, operator, password);
        manager.setTimeout(60 * 10);
        manager.setApiDomain(RestManager.ED_AUTO);

        try {
            Response result = manager.deleteFile(key, null);
            if (!result.isSuccessful()) {
                log.warn("附件 " + key + " 从又拍云删除失败");
            }
        } catch (IOException | UpException e) {
            e.printStackTrace();
            throw new FileOperationException("附件 " + key + " 从又拍云删除失败", e);
        }
    }

    @Override
    public AttachmentType getAttachmentType() {
        return AttachmentType.UPOSS;
    }
}
