package com.wlwq.controller.api;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wlwq.annotation.PassToken;
import com.wlwq.api.domain.ApiAccount;
import com.wlwq.api.domain.LiveVideo;
import com.wlwq.api.domain.WinningTrainingFollowUpRecord;
import com.wlwq.api.domain.WinningTrainingRecord;
import com.wlwq.api.service.IApiAccountService;
import com.wlwq.api.service.IWinningTrainingFollowUpRecordService;
import com.wlwq.api.service.IWinningTrainingRecordService;
import com.wlwq.common.apiResult.ApiCode;
import com.wlwq.common.apiResult.ApiController;
import com.wlwq.common.apiResult.ApiResult;
import com.wlwq.common.apiResult.PageParam;
import com.wlwq.common.exception.ApiException;
import com.wlwq.params.courseExam.WinningTrainingFollowUpRecordParam;
import com.wlwq.service.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

/**
 * @author wwb
 * 训练跟进记录
 */
@RestController
@RequestMapping(value = "/api/winningTrainingFollowUpRecord")
@AllArgsConstructor
public class WinningTrainingFollowUpRecordApiController extends ApiController {

    private final IWinningTrainingFollowUpRecordService winningTrainingFollowUpRecordService;

    private final IWinningTrainingRecordService winningTrainingRecordService;

    private final TokenService tokenService;

    private final IApiAccountService accountService;

    /**
     * pc@Description
     * params 添加训练跟进记录
     */
    @Transactional(rollbackFor = Exception.class)
    @PostMapping(value = "/add")
    public ApiResult add(HttpServletRequest request, @RequestBody WinningTrainingFollowUpRecordParam param, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ApiResult.fail(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return fail(ApiCode.DONT_LOGIN.getMsg());
        }
        WinningTrainingRecord winningTrainingRecord = winningTrainingRecordService.selectWinningTrainingRecordById(param.getWinningTrainingRecordId());
        if(winningTrainingRecord == null){
            return fail("没有此数据记录。");
        }
        WinningTrainingFollowUpRecord winningTrainingFollowUpRecord= WinningTrainingFollowUpRecord.builder().build();
        BeanUtil.copyProperties(param,winningTrainingFollowUpRecord);
        winningTrainingFollowUpRecord.setWinningTrainingFollowUpRecordId(IdUtil.getSnowflake(1, 1).nextIdStr());
        winningTrainingFollowUpRecord.setCompanyId(account.getCompanyId());
        winningTrainingFollowUpRecord.setDeptId(account.getDeptId());
        winningTrainingFollowUpRecord.setCreatorId(account.getAccountId());
        winningTrainingFollowUpRecord.setCreatorNickName(account.getNickName());
        winningTrainingFollowUpRecord.setCreatorHeadPortrait(account.getHeadPortrait());
        winningTrainingFollowUpRecord.setPostIds(account.getPostIds());
        winningTrainingFollowUpRecord.setPkBettingType(winningTrainingRecord.getPkBettingType());
        winningTrainingFollowUpRecord.setWinningTrainingRecordId(winningTrainingRecord.getWinningTrainingRecordId());
        winningTrainingFollowUpRecord.setWinningTrainingType(winningTrainingRecord.getWinningTrainingType());

        int num = winningTrainingFollowUpRecordService.insertWinningTrainingFollowUpRecord(winningTrainingFollowUpRecord);
        if (num <= 0) {
            throw new ApiException("添加失败。");
        }
        return ok(winningTrainingFollowUpRecord);
    }

    /**
     * 查询训练跟进记录列表
     * @param winningTrainingRecordId 训练记录主键id
     * @return
     */
    @PassToken
    @GetMapping(value = "/list")
    public ApiResult list(PageParam pageParam,@RequestParam(defaultValue = "")String winningTrainingRecordId ) {
        if (pageParam == null) {
            pageParam = PageParam.builder().build();
        }
        PageHelper.startPage(pageParam.getPageNo(), pageParam.getPageSize());
        List<WinningTrainingFollowUpRecord> list = winningTrainingFollowUpRecordService.selectWinningTrainingFollowUpRecordList(WinningTrainingFollowUpRecord
                .builder()
                .winningTrainingRecordId(winningTrainingRecordId)
                .delStatus(0)
                .build());
        PageInfo<WinningTrainingFollowUpRecord> pageInfo = new PageInfo<>(list);
        return ok(pageInfo);
    }

}
