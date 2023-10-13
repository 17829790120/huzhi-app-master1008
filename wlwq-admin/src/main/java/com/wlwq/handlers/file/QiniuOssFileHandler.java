package com.wlwq.handlers.file;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.persistent.FileRecorder;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.wlwq.api.domain.UploadResult;
import com.wlwq.api.service.IOptionsService;
import com.wlwq.common.config.WlwqConfig;
import com.wlwq.common.enums.AttachmentType;
import com.wlwq.common.exception.FileOperationException;
import com.wlwq.common.utils.FilenameUtils;
import com.wlwq.common.utils.ImageUtils;
import com.wlwq.common.utils.JsonUtils;
import com.wlwq.handlers.properties.QiniuOssProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;


/**
 *  Create By Renbowen
 *  @Date: 2020/9/29 19:23
 *  @Description: Qiniu oss file handler.
 */
@Slf4j
@Component
public class QiniuOssFileHandler implements FileHandler {
    public final static String URL_SEPARATOR = "/";
    private final IOptionsService optionService;

    public QiniuOssFileHandler(IOptionsService optionService) {
        this.optionService = optionService;
    }

    @Override
    public @NotNull UploadResult upload(MultipartFile file) {
        Assert.notNull(file, "Multipart file must not be null");

        String accessKey = optionService.selectValueByKey(QiniuOssProperties.OSS_ACCESS_KEY.getValue());
        String secretKey = optionService.selectValueByKey(QiniuOssProperties.OSS_SECRET_KEY.getValue());
        String bucket = optionService.selectValueByKey(QiniuOssProperties.OSS_BUCKET.getValue());
        String protocol = optionService.selectValueByKey(QiniuOssProperties.OSS_PROTOCOL.getValue());
        String domain = optionService.selectValueByKey(QiniuOssProperties.OSS_DOMAIN.getValue());
        String source = optionService.selectValueByKey(QiniuOssProperties.OSS_SOURCE.getValue());
        String styleRule = optionService.selectValueByKey(QiniuOssProperties.OSS_STYLE_RULE.getValue());
        String thumbnailStyleRule = optionService.selectValueByKey(QiniuOssProperties.OSS_THUMBNAIL_STYLE_RULE.getValue());
        String zone = optionService.selectValueByKey(QiniuOssProperties.OSS_ZONE.getValue());
        // z0 华东  z1 华北  z2 华南  na0 北美  as0 东南亚
        Region region;
        if ("z0".equals(zone)){
            region = Region.region0();
        }else if ("z1".equals(zone)){
            region = Region.region1();
        }else if ("z2".equals(zone)){
            region = Region.region2();
        }else if ("na0".equals(zone)){
            region = Region.regionNa0();
        }else if ("as0".equals(zone)){
            region = Region.regionAs0();
        }else {
            region = Region.autoRegion();
        }

        // Create configuration
        Configuration configuration = new Configuration(region);

        // Create auth
        Auth auth = Auth.create(accessKey, secretKey);
        // Build put plicy
        StringMap putPolicy = new StringMap();
        putPolicy.put("returnBody", "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"size\":$(fsize),\"width\":$(imageInfo.width),\"height\":$(imageInfo.height)}");
        // Get upload token
        String uploadToken = auth.uploadToken(bucket, null, 60 * 60, putPolicy);

        // Create temp path
        Path tmpPath = Paths.get(ensureSuffix(WlwqConfig.getAvatarPath(), FILE_SEPARATOR), bucket);

        StringBuilder basePath = new StringBuilder(protocol)
                .append(domain)
                .append(URL_SEPARATOR);

        try {
            String basename = FilenameUtils.getBasename(Objects.requireNonNull(file.getOriginalFilename()));
            String extension = FilenameUtils.getExtension(file.getOriginalFilename());
            String timestamp = String.valueOf(System.currentTimeMillis());
            StringBuilder upFilePath = new StringBuilder();
            if (StringUtils.isNotEmpty(source)) {
                upFilePath.append(source)
                        .append(URL_SEPARATOR);
            }
            upFilePath.append(basename)
                    .append("_")
                    .append(timestamp)
                    .append(".")
                    .append(extension);

            // Get file recorder for temp directory
            FileRecorder fileRecorder = new FileRecorder(tmpPath.toFile());
            // Get upload manager
            UploadManager uploadManager = new UploadManager(configuration, fileRecorder);
            // Put the file
            Response response = uploadManager.put(file.getInputStream(), upFilePath.toString(), uploadToken, null, null);

            if (log.isDebugEnabled()) {
                log.debug("Qiniu oss response: [{}]", response.toString());
                log.debug("Qiniu oss response body: [{}]", response.bodyString());
            }

            // Convert response
            PutSet putSet = JsonUtils.jsonToObject(response.bodyString(), PutSet.class);

            // Get file full path
            String filePath = StringUtils.join(basePath.toString(), upFilePath.toString());

            // Build upload result
            UploadResult result = new UploadResult();
            result.setFilename(basename);
            result.setFilePath(StringUtils.isBlank(styleRule) ? filePath : filePath + styleRule);
            result.setKey(upFilePath.toString());
            result.setSuffix(extension);
            result.setWidth(putSet.getWidth());
            result.setHeight(putSet.getHeight());
            result.setMediaType(MediaType.valueOf(Objects.requireNonNull(file.getContentType())));
            result.setSize(file.getSize());

            if (FileHandler.isImageType(result.getMediaType())) {
                if (ImageUtils.EXTENSION_ICO.equals(extension)) {
                    result.setThumbPath(filePath);
                } else {
                    result.setThumbPath(StringUtils.isBlank(thumbnailStyleRule) ? filePath : filePath + thumbnailStyleRule);
                }
            }

            return result;
        } catch (IOException e) {
            if (e instanceof QiniuException) {
                log.error("Qiniu oss error response: [{}]", ((QiniuException) e).response);
            }

            throw new FileOperationException("上传附件 " + file.getOriginalFilename() + " 到七牛云失败", e);
        }
    }

    @Override
    public void delete(String key) {
        Assert.notNull(key, "File key must not be blank");


        String accessKey = optionService.selectValueByKey(QiniuOssProperties.OSS_ACCESS_KEY.getValue());
        String secretKey = optionService.selectValueByKey(QiniuOssProperties.OSS_SECRET_KEY.getValue());
        String bucket = optionService.selectValueByKey(QiniuOssProperties.OSS_BUCKET.getValue());

        // Create configuration
        Configuration configuration = new Configuration();

        // Create auth
        Auth auth = Auth.create(accessKey, secretKey);

        BucketManager bucketManager = new BucketManager(auth, configuration);

        try {
            Response response = bucketManager.delete(bucket, key);
            if (!response.isOK()) {
                log.warn("附件 " + key + " 从七牛云删除失败");
            }
        } catch (QiniuException e) {
            log.error("Qiniu oss error response: [{}]", e.response);
            throw new FileOperationException("附件 " + key + " 从七牛云删除失败", e);
        }
    }

    @Override
    public AttachmentType getAttachmentType() {
        return AttachmentType.QINIUOSS;
    }

    @NonNull
    public static String ensureSuffix(@NonNull String string, @NonNull String suffix) {
        Assert.hasText(string, "String must not be blank");
        Assert.hasText(suffix, "Suffix must not be blank");

        return StringUtils.removeEnd(string, suffix) + suffix;
    }

    @Data
    @NoArgsConstructor
    private static class PutSet {

        public String hash;

        public String key;

        private Long size;

        private Integer width;

        private Integer height;
    }
}

