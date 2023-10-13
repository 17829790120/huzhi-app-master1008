package com.wlwq.api.resultVO.course;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName ChapterDetailResultVO
 * @Description 章节详情返回值封装
 * @Date 2021/1/26 11:33
 * @Author Rick Jen
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ChapterDetailResultVO implements Serializable {

    /** 章节ID. */
    private Long chapterId;

    /** 章节视频封面图. */
    private String chapterVideoImage;

    /** 章节名字. */
    private String chapterName;

    /** 章节视频地址. */
    private String chapterVideoUrl;

    /** 是否可观看 1可以看0不可以看2课程已购买但是过期了. */
    private Integer watchStatus;

}
