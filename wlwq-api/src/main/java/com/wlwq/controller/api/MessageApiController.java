package com.wlwq.controller.api;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wlwq.annotation.PassToken;
import com.wlwq.common.apiResult.ApiController;
import com.wlwq.common.apiResult.ApiResult;
import com.wlwq.common.apiResult.PageParam;
import com.wlwq.system.domain.SysNotice;
import com.wlwq.system.service.ISysNoticeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 消息相关接口
 *
 * @author wwb
 * @date 2023-3-1
 */
@RestController
@RequestMapping("/api/message")
@AllArgsConstructor
public class MessageApiController extends ApiController {

    private final ISysNoticeService noticeService;

    /**
     * 消息的列表
     * deptId 公司ID
     * @return
     */
    @PassToken
    @GetMapping("/list")
    public ApiResult index(@RequestParam(defaultValue = "100") Long deptId,
                           PageParam pageParam) {
        if (pageParam == null) {
            pageParam = PageParam.builder().build();
        }
        SysNotice notice = new SysNotice();
        notice.setStatus("0");
        notice.setDeptId(deptId);
        PageHelper.startPage(pageParam.getPageNo(), pageParam.getPageSize());
        List<SysNotice> list = noticeService.selectApiNoticeList(notice);
        PageInfo<SysNotice> pageInfo = new PageInfo<>(list);
        return ok(pageInfo);
    }

    /**
     * 查看消息详情
     *
     * @param noticeId 消息ID
     * @return
     */
    @PassToken
    @GetMapping(value = "/detail")
    public ApiResult detail(@RequestParam(defaultValue = "0") String noticeId) {
        SysNotice notice = noticeService.selectNoticeById(noticeId);
        if(notice == null){
            return fail("该详情不存在！");
        }
        return ok(notice);
    }

}
