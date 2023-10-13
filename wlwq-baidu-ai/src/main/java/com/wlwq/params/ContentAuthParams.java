package com.wlwq.params;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName ContentAuthParams
 * @Description 文本审核参数封装
 * @Date 2021/3/13 10:24
 * @Author Rick wlwq
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ContentAuthParams implements Serializable {

    /** 审核内容类型 1文字2图片3视频4音频 参考常量类ContentTypeConstant. */
    private Integer contentType;


    /** 审核内容
     * 文本审核请求的长度限制为：2w字节（约等于6666汉字）
     * . */
    private String content;

    /**
     * 图片类型 参考常量FileTypeConstant
     */
    private String fileType;

    /** 视频名字. */
    private String videoName;

    /** 音频文件类型. */
    private String voiceFmt;
}
