package com.wlwq.api.service.impl;

import java.util.List;

import cn.hutool.core.util.IdUtil;
import com.wlwq.common.utils.DateUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.api.mapper.MessageRemindMapper;
import com.wlwq.api.domain.MessageRemind;
import com.wlwq.api.service.IMessageRemindService;
import com.wlwq.common.core.text.Convert;

/**
 * 消息提醒Service业务层处理
 *
 * @author gaoce
 * @date 2023-06-10
 */
@Service
public class MessageRemindServiceImpl implements IMessageRemindService {

    @Autowired
    private MessageRemindMapper messageRemindMapper;

    /**
     * 查询消息提醒
     *
     * @param messageRemindId 消息提醒ID
     * @return 消息提醒
     */
    @Override
    public MessageRemind selectMessageRemindById(String messageRemindId) {
        return messageRemindMapper.selectMessageRemindById(messageRemindId);
    }

    /**
     * 查询消息提醒列表
     *
     * @param messageRemind 消息提醒
     * @return 消息提醒
     */
    @Override
    public List<MessageRemind> selectMessageRemindList(MessageRemind messageRemind) {
        return messageRemindMapper.selectMessageRemindList(messageRemind);
    }

    /**
     * 新增消息提醒
     *
     * @param messageRemind 消息提醒
     * @return 结果
     */
    @Override
    public int insertMessageRemind(MessageRemind messageRemind) {
        messageRemind.setMessageRemindId(IdUtil.getSnowflake(1, 1).nextIdStr());
        messageRemind.setCreateTime(DateUtils.getNowDate());
        return messageRemindMapper.insertMessageRemind(messageRemind);
    }

    /**
     * 修改消息提醒
     *
     * @param messageRemind 消息提醒
     * @return 结果
     */
    @Override
    public int updateMessageRemind(MessageRemind messageRemind) {
        return messageRemindMapper.updateMessageRemind(messageRemind);
    }

    /**
     * 删除消息提醒对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteMessageRemindByIds(String ids) {
        return messageRemindMapper.deleteMessageRemindByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除消息提醒信息
     *
     * @param messageRemindId 消息提醒ID
     * @return 结果
     */
    @Override
    public int deleteMessageRemindById(String messageRemindId) {
        return messageRemindMapper.deleteMessageRemindById(messageRemindId);
    }

    /**
     * 查询消息数量
     *
     * @param messageRemind
     * @return
     */
    @Override
    public int selectMessageRemindCount(MessageRemind messageRemind){
        return messageRemindMapper.selectMessageRemindCount(messageRemind);
    }

    /**
     * 标记某个用户的消息为已读
     *
     * @param accountId 用户ID
     * @return
     */
    @Override
    public int emptyNoReadStatus(@Param("accountId") String accountId){
        return messageRemindMapper.emptyNoReadStatus(accountId);
    }
}
