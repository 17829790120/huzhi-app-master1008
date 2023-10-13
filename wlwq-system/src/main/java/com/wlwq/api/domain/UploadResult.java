package com.wlwq.api.domain;

import lombok.Data;
import lombok.ToString;
import org.springframework.http.MediaType;

/**
 *  Create By Renbowen
 *  @Date: 2020/9/26 18:05
 *  @Description: 上传返回值封装
 */
@Data
@ToString
public class UploadResult {

    private String filename;

    private String filePath;

    private String key;

    private String thumbPath;

    private String suffix;

    private MediaType mediaType;

    private Long size;

    private Integer width;

    private Integer height;

}
