package com.wlwq.controller.api;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wlwq.annotation.PassToken;
import com.wlwq.api.domain.WinningTrainingFollowUpRecord;
import com.wlwq.api.domain.WinningTrainingRecord;
import com.wlwq.api.service.IWinningTrainingFollowUpRecordService;
import com.wlwq.api.service.IWinningTrainingRecordService;
import com.wlwq.common.apiResult.ApiController;
import com.wlwq.common.apiResult.ApiResult;
import com.wlwq.common.apiResult.PageParam;
import com.wlwq.params.reportTraining.WinningTrainingRecordParamVO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * @author wwb
 * pk与对赌
 */
@RestController
@RequestMapping(value = "/api/winningTrainingRecord")
@AllArgsConstructor
public class WinningTrainingRecordApiController extends ApiController {

    private final IWinningTrainingRecordService winningTrainingRecordService;

    private final IWinningTrainingFollowUpRecordService winningTrainingFollowUpRecordService;

    /**
     * pk与对赌列表
     *
     * @return
     */
    @PassToken
    @GetMapping(value = "/list")
    public ApiResult list(PageParam pageParam,WinningTrainingRecordParamVO paramVO) {
        if (pageParam == null) {
            pageParam = PageParam.builder().build();
        }
        PageHelper.startPage(pageParam.getPageNo(), pageParam.getPageSize());
        List<WinningTrainingRecord> winningTrainingRecordList = winningTrainingRecordService.selectWinningTrainingRecordList(WinningTrainingRecord
                .builder()
                .companyId(paramVO.getCompanyId())
                .delStatus(0)
                .pkBettingType(paramVO.getPkBettingType())
                .pkStatus(paramVO.getPkStatus())
                .build());
        PageInfo<WinningTrainingRecord> pageInfo = new PageInfo<>(winningTrainingRecordList);
        return ok(pageInfo);
    }

    /**
     * pk与对赌详情信息
     * @param winningTrainingRecordId 训练记录主键id
     * @return
     */
    @PassToken
    @GetMapping(value = "/detail")
    public ApiResult detail(String winningTrainingRecordId) {
        WinningTrainingRecord winningTrainingRecord = winningTrainingRecordService.selectWinningTrainingRecordById(winningTrainingRecordId);
        List<WinningTrainingFollowUpRecord> list = winningTrainingFollowUpRecordService.selectWinningTrainingFollowUpRecordList(WinningTrainingFollowUpRecord
                .builder()
                .winningTrainingRecordId(winningTrainingRecordId)
                .delStatus(0)
                .build());
        winningTrainingRecord.setWinningTrainingFollowUpRecordList(list);
        return ok(winningTrainingRecord);
    }
}
