package com.wlwq.privatePhone.axService;

/**
*  @Description AX模式接口
*  @author Rick wlwq
*  @Date   2021/4/6 9:51
*/
public interface IAXInterface {
    /**
     * Set the X number to the user's privacy number | 隐私号码AX绑定
     * 
     * @param origNum          用户号码
     * @param privateNum       隐私号码
     * @param calleeNumDisplay 是否显示用户号码  设置非A用户呼叫X时，A接到呼叫时的主显号码。 取值：  0：显示X号码； 1：显示真实主叫号码。  注：由于运营商管控，当前平台要求该参数必须设置为0，否则呼叫会被运营商拦截。
     * @param callDirection 呼叫方向控制0：允许双向呼叫。 1：只允许A呼叫X号码。 2：只允许其他号码呼叫X号码。
     */
    String axBindNumber(String origNum, String privateNum, String calleeNumDisplay,String callDirection);

    /**
     * Modify sms function of the user's privacy number | 隐私号码AX绑定信息修改
     * 
     * @param subscriptionId 绑定关系ID
     * @param origNum        用户号码,如果需要修改绑定关系中的用户号码,请指定新的用户号码.不携带时表示不修改该参数值
     * @param privateNum     隐私号码
     * @param privateSms     是否支持短信
     *                       subscriptionId和privateNum二选一即可,当都传入时,优先选用subscriptionId
     */
    void axModifyNumber(String subscriptionId, String origNum, String privateNum, boolean privateSms);

    /**
     * Unbind the privacy number from number a | 隐私号码AX解绑
     * 
     * @param subscriptionId 绑定关系ID
     * @param privateNum     隐私号码
     *                       subscriptionId和privateNum二选一即可,当都传入时,优先选用subscriptionId
     */
    String axUnbindNumber(String subscriptionId, String privateNum);

    /**
     * Query the privacy binding numbers on the X number | 查询AX绑定信息
     * 
     * @param subscriptionId 绑定关系ID
     * @param origNum        用户号码
     * @param privateNum     隐私号码
     *                       subscriptionId,origNum和privateNum三选一即可,当都传入时,subscriptionId > origNum > privateNum
     */
    void axQueryBindRelation(String subscriptionId, String origNum, String privateNum);

    /**
     * Set the callee number to the ax bind relation | 设置AX临时被叫
     * 
     * @param subscriptionId 绑定关系ID
     * @param privateNum     隐私号码
     * @param calleeNum      被叫号码
     *                       subscriptionId和privateNum二选一即可,当都传入时,优先选用subscriptionId
     */
    void axSetCalleeNumber(String subscriptionId, String privateNum, String calleeNum);

    /**
     * Get download link of the record file created in call | 获取录音文件下载地址
     * 
     * @param recordDomain 录音文件存储的服务器域名
     * @param fileName     录音文件名
     */
    String axGetRecordDownloadLink(String recordDomain, String fileName);

    /**
     * Stop the call on the X number assigned by sessionid | 终止呼叫
     * 
     * @param sessionid 呼叫会话ID 通过"呼叫事件通知接口"获取
     */
    void axStopCall(String sessionid);
}