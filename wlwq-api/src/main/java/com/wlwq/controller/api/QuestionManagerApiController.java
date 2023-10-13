package com.wlwq.controller.api;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wlwq.api.domain.ExamQuestionManager;
import com.wlwq.api.domain.QuestionManager;
import com.wlwq.api.service.IExamPaperRecordService;
import com.wlwq.api.service.IExamQuestionManagerService;
import com.wlwq.api.service.IQuestionManagerService;
import com.wlwq.common.apiResult.ApiController;
import com.wlwq.common.apiResult.ApiResult;
import com.wlwq.common.apiResult.PageParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * @试题列表
 * @author wwb
 */
@RestController
@RequestMapping(value = "/api/questionManager")
@AllArgsConstructor
public class QuestionManagerApiController extends ApiController {

    private final IQuestionManagerService questionManagerService;

    private final IExamPaperRecordService examPaperRecordService;

    private final IExamQuestionManagerService examQuestionManagerService;

    /**
     * 课程章节对应的考试题列表
     * @return
     */
    //@PassToken
    @GetMapping(value = "/list")
    public ApiResult list(PageParam pageParam, @RequestParam(defaultValue = "") Long chapterId,
                          @RequestParam(defaultValue = "")Long courseId){
        if(chapterId == null ){
            return  fail("请选择章节");
        }
        if(courseId == null){
            return  fail("请选择课程");
        }
        if (pageParam == null) {
            pageParam = PageParam.builder().build();
        }
        PageHelper.startPage(pageParam.getPageNo(), pageParam.getPageSize());
        List<QuestionManager> list = questionManagerService.selectQuestionManagerList(QuestionManager
                .builder()
                .courseId(courseId)
                .chapterId(chapterId)
                .build());
        PageInfo<QuestionManager> pageInfo = new PageInfo<>(list);
        return ok(pageInfo);
    }

    /**
     * 测试训练考试题
     * @param examPaperRecordId 考试试卷记录表id
     * @return
     */
    //@PassToken
    @GetMapping(value = "/getExamPaperRecord")
    public ApiResult getExamPaperRecord(@RequestParam(defaultValue = "")String examPaperRecordId){
        return ok(examPaperRecordService.selectExamPaperRecordById(examPaperRecordId));
    }

    /**
     * 测试训练考试题
     * @param examPaperRecordId 考试试卷记录表id
     * @return
     */
    //@PassToken
    @GetMapping(value = "/getExamQuestionManagerList")
    public ApiResult getExamQuestionManagerList(@RequestParam(defaultValue = "")String examPaperRecordId){
        List<ExamQuestionManager> list = examQuestionManagerService.selectExamQuestionManagerList(ExamQuestionManager
                .builder()
                .examPaperRecordId(examPaperRecordId)
                .build());
        return ok(list);
    }
}
