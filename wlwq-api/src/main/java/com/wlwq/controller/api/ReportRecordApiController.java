//package com.wlwq.controller.api;
//
//import cn.hutool.core.bean.BeanUtil;
//import com.github.pagehelper.PageHelper;
//import com.github.pagehelper.PageInfo;
//import com.wlwq.annotation.PassToken;
//import com.wlwq.api.domain.ApiAccount;
//import com.wlwq.api.domain.ReportRecord;
//import com.wlwq.api.service.IApiAccountService;
//import com.wlwq.api.service.ICircleDynamicsService;
//import com.wlwq.api.service.IReportRecordService;
//import com.wlwq.api.service.IReportTypeService;
//import com.wlwq.common.apiResult.ApiCode;
//import com.wlwq.common.apiResult.ApiController;
//import com.wlwq.common.apiResult.ApiResult;
//import com.wlwq.common.apiResult.PageParam;
//import com.wlwq.common.exception.ApiException;
//import com.wlwq.params.web.ReportRecordVo;
//import com.wlwq.service.TokenService;
//import lombok.AllArgsConstructor;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.validation.BindingResult;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.List;
//import java.util.Objects;
//
///**
// * 举报类
// *
// * @author EDZ
// */
//@RestController
//@RequestMapping(value = "/api/reportRecord")
//@AllArgsConstructor
//public class ReportRecordApiController extends ApiController {
//    private final TokenService tokenService;
//
//    private final IApiAccountService accountService;
//
//    private final IReportTypeService reportTypeService;
//
//    private final IReportRecordService reportRecordService;
//
//    private final ICircleDynamicsService circleDynamicsService;
//
//    /**
//     * @Description 查询举报类型
//     */
//    @PassToken
//    @GetMapping(value = "/reportTypeList")
//    public ApiResult reportTypeList(PageParam pageParam) {
//        if (pageParam == null) {
//            pageParam = PageParam.builder().build();
//        }
//        PageHelper.startPage(pageParam.getPageNo(), pageParam.getPageSize());
//        List<ReportType> reportTypeList = reportTypeService.selectReportTypeList(ReportType.builder().build());
//        PageInfo<ReportType> pageInfo = new PageInfo<>(reportTypeList);
//        return ok(pageInfo);
//    }
//
//    /**
//     * @Description 查询我举报的记录
//     */
//    @PassToken
//    @GetMapping(value = "/reportList")
//    public ApiResult reportList(PageParam pageParam, HttpServletRequest request) {
//        if (pageParam == null) {
//            pageParam = PageParam.builder().build();
//        }
//        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
//        if(account == null){
//            return ApiResult.fail(ApiCode.DONT_LOGIN.getMsg());
//        }
//        PageHelper.startPage(pageParam.getPageNo(), pageParam.getPageSize());
//        List<ReportRecord> list = reportRecordService.selectReportRecordList(ReportRecord.builder().accountId(account.getAccountId()).build());
//        PageInfo<ReportRecord> pageInfo = new PageInfo<>(list);
//        return ok(pageInfo);
//    }
//
//    /**
//     * 添加举报
//     * @param params
//     * @param bindingResult
//     * @param request
//     * @return
//     */
//    @Transactional(rollbackFor = Exception.class)
//    @PostMapping(value = "/add")
//    public ApiResult add(@Validated ReportRecordVo params, BindingResult bindingResult, HttpServletRequest request){
//        if (bindingResult.hasErrors()){
//            return ApiResult.fail(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
//        }
//
//        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
//        if(account == null){
//            return ApiResult.fail(ApiCode.DONT_LOGIN.getMsg());
//        }
//
//        CircleDynamics circleDynamics = circleDynamicsService.selectCircleDynamicsById(params.getTargetId());
//        if(circleDynamics==null){
//            return fail("被举报的动态不存在");
//        }
//        ReportRecord reportRecord = ReportRecord.builder().build();
//        BeanUtil.copyProperties(params,reportRecord);
//        reportRecord.setAccountId(account.getAccountId());
//        reportRecord.setAccountHead(account.getHeadPortrait());
//        reportRecord.setAccountName(account.getNickName());
//        reportRecord.setAccountPhone(account.getPhone());
//        int flag = reportRecordService.insertReportRecord(reportRecord);
//        if (flag < 1){
//            throw new ApiException("举报过程发生异常！");
//        }
//        return ApiResult.ok(reportRecord);
//    }
//
//}
