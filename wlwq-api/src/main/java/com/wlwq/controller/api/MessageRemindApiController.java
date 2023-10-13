package com.wlwq.controller.api;

import cn.hutool.core.convert.Convert;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wlwq.annotation.PassToken;
import com.wlwq.api.domain.ApiAccount;
import com.wlwq.api.domain.MessageRemind;
import com.wlwq.api.service.IApiAccountService;
import com.wlwq.api.service.IMessageRemindService;
import com.wlwq.common.apiResult.ApiController;
import com.wlwq.common.apiResult.ApiResult;
import com.wlwq.common.apiResult.PageParam;
import com.wlwq.service.TokenService;
import com.wlwq.system.domain.SysNotice;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 消息记录
 *
 * @author gaoce
 */
@RestController
@RequestMapping(value = "/api/messageRemind")
@AllArgsConstructor
public class MessageRemindApiController extends ApiController {

    private final TokenService tokenService;

    private final IMessageRemindService messageRemindService;



    /**
     * 未读消息的数量
     *
     * @param request
     * @return
     */
    @PassToken
    @GetMapping("/noReadyCount")
    public ApiResult noReadyCount(HttpServletRequest request) {
        String accountId = tokenService.getNoAccountId(request);
        Map<String, Object> map = new HashMap<>(3);
        // 消息表里的消息
        int totalMessageCount = messageRemindService.selectMessageRemindCount(MessageRemind.builder().accountId(accountId).readStatus(0).build());
        // 未读消息数量
        map.put("allNoReadyCount", totalMessageCount);
        // 系统未读消息
        map.put("systemNoReadyCount",messageRemindService.selectMessageRemindCount(MessageRemind.builder().accountId(accountId).modelStatus(1).readStatus(0).build()));
        // 积分未读消息
        map.put("scoreNoReadyCount",messageRemindService.selectMessageRemindCount(MessageRemind.builder().accountId(accountId).modelStatus(2).readStatus(0).build()));
        return ok(map);
    }

    /**
     * 消息的列表
     * status 消息类型（1系统消息 2积分消息）
     * @param request
     * @return
     */
    @PassToken
    @GetMapping("/index")
    public ApiResult index(HttpServletRequest request,
                           PageParam pageParam,
                           @RequestParam(defaultValue = "2") Integer status) {
        String accountId = tokenService.getNoAccountId(request);
        if (pageParam == null) {
            pageParam = PageParam.builder().build();
        }
        PageHelper.startPage(pageParam.getPageNo(), pageParam.getPageSize());
        List<MessageRemind> remindList = messageRemindService.selectMessageRemindList(MessageRemind.builder()
                .accountId(accountId)
                .modelStatus(status)
                .build());
        PageInfo<MessageRemind> pageInfo = new PageInfo<>(remindList);
        return ok(pageInfo);
    }

    /**
     * 将某个消息置为已读
     * messageId 消息表id
     *
     * @return
     */
    @PostMapping(value = "/messageRemindingReadStatus")
    public ApiResult messageRemindingReadStatus(@RequestParam(defaultValue = "0") String messageId) {
        int count = messageRemindService.updateMessageRemind(MessageRemind.builder().messageRemindId(messageId).readStatus(1).build());
        if (count <= 0) {
            return fail("更新失败！");
        }
        return ok("成功");
    }

    /**
     * 标记所有为已读
     *
     * @param request
     * @return
     */
    @PostMapping(value = "/emptyNoReadStatus")
    public ApiResult emptyNoReadStatus(HttpServletRequest request) {
        // 账号id
        String accountId = tokenService.getAccountId(request);
        // 标记某个用户的消息为已读
        int count = messageRemindService.emptyNoReadStatus(accountId);
        if (count <= 0) {
            return fail("标记已读失败 或者 没有未读的消息！");
        }
        return ok("成功");
    }

    /**
     * 将某个消息删除
     * messageId 消息表id
     *
     * @return
     */
    @PostMapping(value = "/del")
    public ApiResult del(@RequestParam(defaultValue = "0") String messageId) {
        int count = messageRemindService.deleteMessageRemindById(messageId);
        if (count <= 0) {
            return fail("删除失败！");
        }
        return ok("已删除");
    }
}
