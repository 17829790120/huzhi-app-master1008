package com.wlwq.api.resultVO.course;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName ChapterSonResultVO
 * @Description 章节子类返回值封装
 * @Date 2021/1/26 9:29
 * @Author Rick Jen
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ChapterSonResultVO implements Serializable {

    /** 子章节ID. */
    private Long chapterSonId;

    /** 子章节名字. */
    private String chapterSonName;

    /** 总分钟数. */
    private String totalMinuteNumber;

    /** 是否试看 1可试看0不可试看. */
    private Integer tryStatus;

    /** 章节视频封面图. */
    private String chapterVideoImage;

    /** 章节视频地址. */
    private String chapterVideoUrl;

    /** 是否可观看 1可以看0不可以看2课程已购买但是过期了. */
    private Integer watchStatus;
}
