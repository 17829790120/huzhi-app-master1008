package com.wlwq.handlers.file;

import com.obs.services.ObsClient;
import com.obs.services.model.PutObjectResult;
import com.wlwq.api.domain.UploadResult;
import com.wlwq.api.service.IOptionsService;
import com.wlwq.common.enums.AttachmentType;
import com.wlwq.common.exception.FileOperationException;
import com.wlwq.common.utils.FilenameUtils;
import com.wlwq.common.utils.ImageUtils;
import com.wlwq.handlers.properties.HuaweiObsProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageReader;
import java.io.IOException;
import java.util.Objects;


/**
 *  Create By Renbowen
 *  @Date: 2020/9/26 23:59
 *  @Description: 华为图床
 */
@Slf4j
@Component
public class HuaweiObsFileHandler implements FileHandler {

    public final static String URL_SEPARATOR = "/";
    private final IOptionsService optionService;

    public HuaweiObsFileHandler(IOptionsService optionService) {
        this.optionService = optionService;
    }

    @Override
    public @NotNull
    UploadResult upload(@NotNull MultipartFile file) {
        Assert.notNull(file, "Multipart file must not be null");

        // Get config
        String protocol = optionService.selectValueByKey(HuaweiObsProperties.OSS_PROTOCOL.getValue());
        String domain = optionService.selectValueByKey(HuaweiObsProperties.OSS_DOMAIN.getValue());
        String source = optionService.selectValueByKey(HuaweiObsProperties.OSS_SOURCE.getValue());
        String endPoint = optionService.selectValueByKey(HuaweiObsProperties.OSS_ENDPOINT.getValue());
        String accessKey = optionService.selectValueByKey(HuaweiObsProperties.OSS_ACCESS_KEY.getValue());
        String accessSecret = optionService.selectValueByKey(HuaweiObsProperties.OSS_ACCESS_SECRET.getValue());
        String bucketName = optionService.selectValueByKey(HuaweiObsProperties.OSS_BUCKET_NAME.getValue());
        String styleRule = optionService.selectValueByKey(HuaweiObsProperties.OSS_STYLE_RULE.getValue());
        String thumbnailStyleRule = optionService.selectValueByKey(HuaweiObsProperties.OSS_THUMBNAIL_STYLE_RULE.getValue());

        // Init OSS client
        final ObsClient obsClient = new ObsClient(accessKey, accessSecret, endPoint);

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
            PutObjectResult putObjectResult = obsClient.putObject(bucketName, upFilePath.toString(), file.getInputStream());
            if (putObjectResult == null) {
                throw new FileOperationException("上传附件 " + file.getOriginalFilename() + " 到华为云失败 ");
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
            throw new FileOperationException("上传附件 " + file.getOriginalFilename() + " 到华为云失败 ", e).setErrorData(file.getOriginalFilename());
        } finally {
            try {
                obsClient.close();
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
    }

    @Override
    public void delete(@NotNull String key) {
        Assert.notNull(key, "File key must not be blank");

        // Get config
        String endPoint = optionService.selectValueByKey(HuaweiObsProperties.OSS_ENDPOINT.getValue());
        String accessKey = optionService.selectValueByKey(HuaweiObsProperties.OSS_ACCESS_KEY.getValue());
        String accessSecret = optionService.selectValueByKey(HuaweiObsProperties.OSS_ACCESS_SECRET.getValue());
        String bucketName = optionService.selectValueByKey(HuaweiObsProperties.OSS_BUCKET_NAME.getValue());

        // Init OSS client
        final ObsClient obsClient = new ObsClient(accessKey, accessSecret, endPoint);

        try {
            obsClient.deleteObject(bucketName, key);
        } catch (Exception e) {
            throw new FileOperationException("附件 " + key + " 从华为云删除失败", e);
        } finally {
            try {
                obsClient.close();
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
    }

    @Override
    public AttachmentType getAttachmentType() {
        return AttachmentType.HUAWEIOBS;
    }

}
