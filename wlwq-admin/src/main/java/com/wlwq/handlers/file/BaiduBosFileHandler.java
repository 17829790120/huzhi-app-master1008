package com.wlwq.handlers.file;


import com.baidubce.auth.DefaultBceCredentials;
import com.baidubce.services.bos.BosClient;
import com.baidubce.services.bos.BosClientConfiguration;
import com.baidubce.services.bos.model.PutObjectResponse;
import com.wlwq.api.domain.UploadResult;
import com.wlwq.api.service.IOptionsService;
import com.wlwq.common.enums.AttachmentType;
import com.wlwq.common.exception.FileOperationException;
import com.wlwq.common.utils.FilenameUtils;
import com.wlwq.common.utils.ImageUtils;
import com.wlwq.handlers.properties.BaiduBosProperties;
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
 *  @Date: 2020/9/27 0:00
 *  @Description: 百度云图床
 */
@Slf4j
@Component
public class BaiduBosFileHandler implements FileHandler {

    private final IOptionsService optionService;

    public BaiduBosFileHandler(IOptionsService optionService) {
        this.optionService = optionService;
    }

    @Override
    public @NotNull UploadResult upload(MultipartFile file) {
        Assert.notNull(file, "Multipart file must not be null");

        // Get config
        Object protocol = optionService.selectValueByKey(BaiduBosProperties.BOS_PROTOCOL.getValue());
        String domain = optionService.selectValueByKey(BaiduBosProperties.BOS_DOMAIN.getValue());
        String endPoint = optionService.selectValueByKey(BaiduBosProperties.BOS_ENDPOINT.getValue());
        String accessKey = optionService.selectValueByKey(BaiduBosProperties.BOS_ACCESS_KEY.getValue());
        String secretKey = optionService.selectValueByKey(BaiduBosProperties.BOS_SECRET_KEY.getValue());
        String bucketName = optionService.selectValueByKey(BaiduBosProperties.BOS_BUCKET_NAME.getValue());
        String styleRule = optionService.selectValueByKey(BaiduBosProperties.BOS_STYLE_RULE.getValue());
        String thumbnailStyleRule = optionService.selectValueByKey(BaiduBosProperties.BOS_THUMBNAIL_STYLE_RULE.getValue());
        String source = StringUtils.join(protocol, bucketName, "." + endPoint);

        BosClientConfiguration config = new BosClientConfiguration();
        config.setCredentials(new DefaultBceCredentials(accessKey, secretKey));
        config.setEndpoint(endPoint);

        // Init OSS client
        BosClient client = new BosClient(config);

        domain = protocol + domain;

        try {
            String basename = FilenameUtils.getBasename(Objects.requireNonNull(file.getOriginalFilename()));
            String extension = FilenameUtils.getExtension(file.getOriginalFilename());
            String timestamp = String.valueOf(System.currentTimeMillis());
            String upFilePath = StringUtils.join(basename, "_", timestamp, ".", extension);
            String filePath = StringUtils.join(StringUtils.appendIfMissing(StringUtils.isNotBlank(domain) ? domain : source, "/"), upFilePath);

            // Upload
            PutObjectResponse putObjectResponseFromInputStream = client.putObject(bucketName, upFilePath, file.getInputStream());
            if (putObjectResponseFromInputStream == null) {
                throw new FileOperationException("上传附件 " + file.getOriginalFilename() + " 到百度云失败 ");
            }

            // Response result
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
            throw new FileOperationException("附件 " + file.getOriginalFilename() + " 上传失败(百度云)", e);
        } finally {
            client.shutdown();
        }
    }

    @Override
    public void delete(String key) {
        Assert.notNull(key, "File key must not be blank");

        // Get config
        String endPoint = optionService.selectValueByKey(BaiduBosProperties.BOS_ENDPOINT.getValue());
        String accessKey = optionService.selectValueByKey(BaiduBosProperties.BOS_ACCESS_KEY.getValue());
        String secretKey = optionService.selectValueByKey(BaiduBosProperties.BOS_SECRET_KEY.getValue());
        String bucketName = optionService.selectValueByKey(BaiduBosProperties.BOS_BUCKET_NAME.getValue());

        BosClientConfiguration config = new BosClientConfiguration();
        config.setCredentials(new DefaultBceCredentials(accessKey, secretKey));
        config.setEndpoint(endPoint);

        // Init OSS client
        BosClient client = new BosClient(config);

        try {
            client.deleteObject(bucketName, key);
        } catch (Exception e) {
            throw new FileOperationException("附件 " + key + " 从百度云删除失败", e);
        } finally {
            client.shutdown();
        }
    }

    @Override
    public AttachmentType getAttachmentType() {
        return AttachmentType.BAIDUBOS;
    }
}
