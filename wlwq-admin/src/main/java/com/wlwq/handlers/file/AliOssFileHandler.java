package com.wlwq.handlers.file;


import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.DeleteObjectsRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.wlwq.api.domain.UploadResult;
import com.wlwq.api.service.IOptionsService;
import com.wlwq.common.enums.AttachmentType;
import com.wlwq.common.exception.FileOperationException;
import com.wlwq.common.utils.FilenameUtils;
import com.wlwq.common.utils.ImageUtils;
import com.wlwq.handlers.properties.AliOssProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageReader;
import java.util.Objects;


/**
 *  Create By Renbowen
 *  @Date: 2020/9/26 23:59
 *  @Description: 阿里云图床
 */
@Slf4j
@Component
public class AliOssFileHandler implements FileHandler {

    public final static String URL_SEPARATOR = "/";

    private final IOptionsService optionService;

    public AliOssFileHandler(IOptionsService optionService) {
        this.optionService = optionService;
    }

    @Override
    public @NotNull
    UploadResult upload(@NotNull MultipartFile file) {
        Assert.notNull(file, "Multipart file must not be null");

        // Get config
        String protocol = optionService.selectValueByKey(AliOssProperties.OSS_PROTOCOL.getValue());
        String domain = optionService.selectValueByKey(AliOssProperties.OSS_DOMAIN.getValue());
        String source = optionService.selectValueByKey(AliOssProperties.OSS_SOURCE.getValue());
        String endPoint = optionService.selectValueByKey(AliOssProperties.OSS_ENDPOINT.getValue());
        String accessKey = optionService.selectValueByKey(AliOssProperties.OSS_ACCESS_KEY.getValue());
        String accessSecret = optionService.selectValueByKey(AliOssProperties.OSS_ACCESS_SECRET.getValue());
        String bucketName = optionService.selectValueByKey(AliOssProperties.OSS_BUCKET_NAME.getValue());
        String styleRule = optionService.selectValueByKey(AliOssProperties.OSS_STYLE_RULE.getValue());
        String thumbnailStyleRule = optionService.selectValueByKey(AliOssProperties.OSS_THUMBNAIL_STYLE_RULE.getValue());

        // Init OSS client
        OSS ossClient = new OSSClientBuilder().build(endPoint, accessKey, accessSecret);

        StringBuilder basePath = new StringBuilder(protocol);

        if (StringUtils.isNotEmpty(domain)) {
            basePath.append(domain)
                    .append(URL_SEPARATOR);
        } else {
            basePath.append(bucketName)
                    .append(".")
                    .append(endPoint)
                    .append(URL_SEPARATOR);
        }

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

            String filePath = StringUtils.join(basePath.toString(), upFilePath.toString());

            log.info(basePath.toString());

            // Upload
            PutObjectResult putObjectResult = ossClient.putObject(bucketName, upFilePath.toString(), file.getInputStream());
            if (putObjectResult == null) {
                throw new FileOperationException("上传附件 " + file.getOriginalFilename() + " 到阿里云失败 ");
            }

            // Response result
            UploadResult uploadResult = new UploadResult();
            uploadResult.setFilename(basename);
            uploadResult.setFilePath(StringUtils.isBlank(styleRule) ? filePath : filePath + styleRule);
            uploadResult.setKey(upFilePath.toString());
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

            log.info("Uploaded file: [{}] successfully", file.getOriginalFilename());
            return uploadResult;
        } catch (Exception e) {
            throw new FileOperationException("上传附件 " + file.getOriginalFilename() + " 到阿里云失败 ", e).setErrorData(file.getOriginalFilename());
        } finally {
            ossClient.shutdown();
        }
    }

    @Override
    public void delete(@NotNull String key) {
        Assert.notNull(key, "File key must not be blank");

        // Get config
        String endPoint = optionService.selectValueByKey(AliOssProperties.OSS_ENDPOINT.getValue());
        String accessKey = optionService.selectValueByKey(AliOssProperties.OSS_ACCESS_KEY.getValue());
        String accessSecret = optionService.selectValueByKey(AliOssProperties.OSS_ACCESS_SECRET.getValue());
        String bucketName = optionService.selectValueByKey(AliOssProperties.OSS_BUCKET_NAME.getValue());

        // Init OSS client
        OSS ossClient = new OSSClientBuilder().build(endPoint, accessKey, accessSecret);

        try {
            ossClient.deleteObject(new DeleteObjectsRequest(bucketName).withKey(key));
        } catch (Exception e) {
            throw new FileOperationException("附件 " + key + " 从阿里云删除失败", e);
        } finally {
            ossClient.shutdown();
        }
    }

    @Override
    public AttachmentType getAttachmentType() {
        return AttachmentType.ALIOSS;
    }

}
