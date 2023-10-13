package com.wlwq.setting.api;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wlwq.common.apiResult.ApiController;
import com.wlwq.common.apiResult.ApiResult;
import com.wlwq.common.apiResult.PageParam;
import com.wlwq.common.utils.StringUtils;
import com.wlwq.setting.result.ProblemListResult;
import com.wlwq.setting.service.ISettingProblemService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName ProblemController
 * @Description 常见问题
 * @Date 2021/6/7 17:21
 * @Author Rick wlwq
 */
@RestController
@RequestMapping(value = "/set/problem")
@AllArgsConstructor
public class ProblemController extends ApiController {

    /**
    *  @Description 常见问题答案
    *  @author Rick wlwq
    *  @Date   2021/6/7 18:25
    */
    @RequestMapping(value = "/problemAnswer",method = RequestMethod.GET)
    private ApiResult problemAnswer(Long problemId){
        if (StringUtils.isNull(problemId)){
            return fail("常见问题标识为空！");
        }
        return ok(problemService.selectProblemContentById(problemId));
    }

    /**
    *  @Description 常见问题列表
    *  @author Rick wlwq
    *  @Date   2021/6/7 17:23
    */
    @RequestMapping(value = "/problemList",method = RequestMethod.GET)
    public ApiResult problemList(PageParam pageParam,String problemTitle){
        PageHelper.startPage(pageParam.getPageNo(),pageParam.getPageSize());
        List<ProblemListResult> list = problemService.selectProblemList
                (problemTitle);
        PageInfo<ProblemListResult> pageInfo = new PageInfo<>(list);
        return ApiResult.ok(pageInfo);
    }

    private final ISettingProblemService problemService;

}
