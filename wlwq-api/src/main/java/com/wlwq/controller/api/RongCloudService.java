package com.wlwq.controller.api;

import com.wlwq.api.domain.ApiAccount;
import com.wlwq.api.domain.ImGroup;
import io.rong.RongCloud;
import io.rong.methods.group.Group;
import io.rong.methods.user.User;
import io.rong.methods.user.blacklist.Blacklist;
import io.rong.models.Result;
import io.rong.models.group.GroupMember;
import io.rong.models.group.GroupModel;
import io.rong.models.response.*;
import io.rong.models.user.UserModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @Author:gaoce
 * @Date:2022/3/28 0:35
 * 融云即时通讯
 */
@Service
@AllArgsConstructor
public class RongCloudService {

    /**
     * 此处替换成您的appKey
     */
    private static final String appKey = "tdr3vipks1tv9p5";
    /**
     * 此处替换成您的appSecret
     */
    private static final String appSecret = "x1Cni1CzsUe2KB";

    public String appKey(){
        return appKey;
    }

    public String appSecret(){
        return appSecret;
    }

    /**
     * 融云用户注册
     *
     * @return
     */
    public TokenResult register(ApiAccount account) throws Exception {
        RongCloud rongCloud = RongCloud.getInstance(appKey, appSecret);
        User User = rongCloud.user;
        /**
         * API 文档: http://www.rongcloud.cn/docs/server_sdk_api/user/user.html#register
         *
         * 注册用户，生成用户在融云的唯一身份标识 Token
         */
        UserModel user = new UserModel()
                .setId(account.getAccountId())
                .setName(account.getNickName())
                .setPortrait(account.getHeadPortrait());
        TokenResult result = User.register(user);
        System.out.println("getToken:  " + result.toString());
        return result;

    }


    /**
     * 获取某用户的黑名单列表方法
     *
     * @return
     */
    public BlackListResult blackList(String accountId) throws Exception {
        /**
         *
         * API 文档: http://www.rongcloud.cn/docs/server_sdk_api/user/black.html#getList
         * 获取某用户的黑名单列表方法
         */
        RongCloud rongCloud = RongCloud.getInstance(appKey, appSecret);
        Blacklist BlackList = rongCloud.user.blackList;
        UserModel user2 = new UserModel().setId(accountId);
        BlackListResult result = BlackList.getList(user2);
        System.out.println("查询黑名单列表:  " + result.toString());
        return result;

    }

    /**
     * 融云创建群组
     *
     * @return
     */
    public Result createGroup(ApiAccount account, ImGroup imGroup) throws Exception {
        RongCloud rongCloud = RongCloud.getInstance(appKey, appSecret);

        GroupMember[] members = {new GroupMember().setId(account.getAccountId())};
        Group Group = rongCloud.group;
        GroupModel group = new GroupModel()
                .setId(imGroup.getImGroupId())
                .setName(imGroup.getGroupName())
                .setMembers(members);
        Result createGroup = Group.create(group);
        System.out.println("createGroup:  " + createGroup);
        return createGroup;

    }

    /**
     * 融云更新群组
     *
     * @return
     */
    public Result updateGroup(ImGroup imGroup) throws Exception {
        RongCloud rongCloud = RongCloud.getInstance(appKey, appSecret);
        Group Group = rongCloud.group;
        GroupModel group = new GroupModel()
                .setId(imGroup.getImGroupId())
                .setName(imGroup.getGroupName());
        Result updateGroup = Group.update(group);
        System.out.println("updateGroup:  " + updateGroup);
        return updateGroup;

    }

    /**
     * 融云加入群组
     *
     * @return
     */
    public Result addGroup(ApiAccount account, ImGroup imGroup) throws Exception {
        RongCloud rongCloud = RongCloud.getInstance(appKey, appSecret);
        Group Group = rongCloud.group;
        GroupMember[] members = {new GroupMember().setId(account.getAccountId())};
        GroupModel group = new GroupModel()
                .setId(imGroup.getImGroupId())
                .setName(imGroup.getGroupName())
                .setMembers(members);
        Result addGroup = Group.join(group);
        System.out.println("addGroup:  " + addGroup);
        return addGroup;
    }


    /**
     * 融云退出群组
     *
     * @return
     */
    public Result quitGroup(ApiAccount account, ImGroup imGroup) throws Exception {
        RongCloud rongCloud = RongCloud.getInstance(appKey, appSecret);
        GroupMember[] members = {new GroupMember().setId(account.getAccountId())};
        Group Group = rongCloud.group;
        GroupModel group = new GroupModel()
                .setId(imGroup.getImGroupId())
                .setName(imGroup.getGroupName())
                .setMembers(members);
        Result quitGroup = Group.quit(group);
        System.out.println("quitGroup:  " + quitGroup);
        return quitGroup;

    }


    /**
     * 融云解散群组
     *
     * @return
     */
    public Result dismissGroup(ApiAccount account, ImGroup imGroup) throws Exception {
        RongCloud rongCloud = RongCloud.getInstance(appKey, appSecret);
        GroupMember[] members = {new GroupMember().setId(account.getAccountId())};
        Group Group = rongCloud.group;
        GroupModel group = new GroupModel()
                .setId(imGroup.getImGroupId())
                .setMembers(members);
        Result dismissGroup = Group.dismiss(group);
        System.out.println("dismissGroup:  " + dismissGroup);
        return dismissGroup;

    }


    public static void main(String[] args) throws Exception {

        RongCloud rongCloud = RongCloud.getInstance(appKey, appSecret);
//        History history = rongCloud.message.history;
//        HistoryMessageResult historyMessageResult = (HistoryMessageResult) history.get("20220703");
//        System.err.println("historyMessageResult:"+historyMessageResult.getUrl());
        // 自定义 api 地址方式
        // RongCloud rongCloud = RongCloud.getInstance(appKey, appSecret,api);
        // 使用 百度 HTTPDNS 获取最快的 IP 地址进行连接
        // BaiduHttpDNSUtil.setHostTypeIp("account_id", "secret", rongCloud.getApiHostType());

        // 设置连接超时时间
        // rongCloud.getApiHostType().setConnectTimeout(10000);
        // 设置读取超时时间
        // rongCloud.getApiHostType().setReadTimeout(10000);
        // 获取备用域名List
        // List<HostType> hosttypes = rongCloud.getApiHostListBackUp();
        // 设置连接、读取超时时间
        // for (HostType hosttype : hosttypes) {
        //     hosttype.setConnectTimeout(10000);
        //     hosttype.setReadTimeout(10000);
        // }

        User User = rongCloud.user;

        /**
         * API 文档: http://www.rongcloud.cn/docs/server_sdk_api/user/user.html#register
         *
         * 注册用户，生成用户在融云的唯一身份标识 Token
         */
        UserModel user = new UserModel()
                .setId("userxx1d2")
                .setName("username")
                .setPortrait("http://www.rongcloud.cn/images/logo.png");
        TokenResult result = User.register(user);
        System.out.println("getToken:  " + result.toString());

//        /**
//         *
//         * API 文档: http://www.rongcloud.cn/docs/server_sdk_api/user/user.html#refresh
//         *
//         * 刷新用户信息方法
//         */
//        Result refreshResult = User.update(user);
//        System.out.println("refresh:  " + refreshResult.toString());
//
//        /**
//         *
//         * API 文档: http://www.rongcloud.cn/docs/server_sdk_api/user/user.html#get
//         *
//         * 查询用户信息方法
//         */
//        UserResult userResult = User.get(user);
//        System.out.println("getUserInfo:  " + userResult.toString());
//
//        /**
//         *
//         * API 文档: http://www.rongcloud.cn/docs/server_sdk_api/user/user.html#get
//         *
//         * 查询用户所在群组
//         */
//        UserGroupQueryResult userGroupResult = User.getGroups(user);
//        System.out.println("getGroups:  " + userGroupResult.toString());
//
//
//        ExpireModel expireModel = new ExpireModel()
//                .setUserId(new String[]{"CHIQ1", "CHIQ2"})
//                .setTime(1623123911000L);
//        /**
//         *
//         * API 文档: https://docs.rongcloud.cn/v4/5X/views/im/server/user/expire.html
//         *
//         * Token 失效
//         */
//        refreshResult = User.expire(expireModel);
//        System.out.println("expire:  " + refreshResult.toString());


    }

}
