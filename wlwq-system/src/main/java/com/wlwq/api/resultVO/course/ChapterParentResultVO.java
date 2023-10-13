package com.wlwq.api.resultVO.course;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName ChapterParentResultVO
 * @Description 章节父级返回值封装
 * @Date 2021/1/26 9:26
 * @Author Rick Jen
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ChapterParentResultVO implements Serializable {

    /** 章节父类ID. */
    private Long chapterParentId;

    /** 章节父类名字. */
    private String chapterParentName;

    /** 子章节数量. */
    private Integer totalChapterSonNumber;

    /** 章节总时长. */
    private String totalMinuteNumber;

    /** 子章节集合. */
    private List<ChapterSonResultVO> chapterSonResultVOList;

}
