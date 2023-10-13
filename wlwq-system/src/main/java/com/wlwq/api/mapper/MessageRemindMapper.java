package com.wlwq.api.mapper;

import java.util.List;

import com.wlwq.api.domain.MessageRemind;
import org.apache.ibatis.annotations.Param;

/**
 * 消息提醒Mapper接口
 *
 * @author gaoce
 * @date 2023-06-10
 */
public interface MessageRemindMapper {
    /**
     * 查询消息提醒
     *
     * @param messageRemindId 消息提醒ID
     * @return 消息提醒
     */
    public MessageRemind selectMessageRemindById(String messageRemindId);

    /**
     * 查询消息提醒列表
     *
     * @param messageRemind 消息提醒
     * @return 消息提醒集合
     */
    public List<MessageRemind> selectMessageRemindList(MessageRemind messageRemind);

    /**
     * 新增消息提醒
     *
     * @param messageRemind 消息提醒
     * @return 结果
     */
    public int insertMessageRemind(MessageRemind messageRemind);

    /**
     * 修改消息提醒
     *
     * @param messageRemind 消息提醒
     * @return 结果
     */
    public int updateMessageRemind(MessageRemind messageRemind);

    /**
     * 删除消息提醒
     *
     * @param messageRemindId 消息提醒ID
     * @return 结果
     */
    public int deleteMessageRemindById(String messageRemindId);

    /**
     * 批量删除消息提醒
     *
     * @param messageRemindIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteMessageRemindByIds(String[] messageRemindIds);

    /**
     * 查询消息数量
     *
     * @param messageRemind
     * @return
     */
    int selectMessageRemindCount(MessageRemind messageRemind);

    /**
     * 标记某个用户的消息为已读
     *
     * @param accountId 用户ID
     * @return
     */
    int emptyNoReadStatus(@Param("accountId") String accountId);

}
