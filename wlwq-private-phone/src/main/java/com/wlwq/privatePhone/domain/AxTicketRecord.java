package com.wlwq.privatePhone.domain;

import com.wlwq.common.annotation.Excel;
import com.wlwq.common.core.domain.BaseEntity;
import lombok.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * AX话单记录对象 ax_ticket_record
 * 
 * @author Rick wlwq
 * @date 2021-07-12
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AxTicketRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** AX话单记录ID */
    private Long axTicketRecordId;

    /** 通话呼叫方向（0其他用户呼叫A 1A呼叫其他用户 2异常场景） */
    @Excel(name = "通话呼叫方向", readConverterExp = "0=其他用户呼叫A,1=A呼叫其他用户,2=异常场景")
    private Integer direction;

    /** 客户的云服务账号 */
    @Excel(name = "客户的云服务账号")
    private String spId;

    /** 隐私保护通话应用的app_key */
    @Excel(name = "隐私保护通话应用的app_key")
    private String appKey;

    /** 呼叫记录的唯一标识 */
    @Excel(name = "呼叫记录的唯一标识")
    private String icId;

    /** 隐私保护号码 */
    @Excel(name = "隐私保护号码")
    private String bindNum;

    /** 通话链路的唯一标识 */
    @Excel(name = "通话链路的唯一标识")
    private String sessionId;

    /** 主叫号码 */
    @Excel(name = "主叫号码")
    private String callerNum;

    /** 被叫号码 */
    @Excel(name = "被叫号码")
    private String calleeNum;

    /** 呼入的开始时间 */
    @Excel(name = "呼入的开始时间")
    private String callinTime;

    /** 呼叫结束时间 */
    @Excel(name = "呼叫结束时间")
    private String callEndTime;

    /** 录音标识（0表示未录音 1表示有录音） */
    @Excel(name = "录音标识", readConverterExp = "0=表示未录音,1=表示有录音")
    private Integer recordFlag;

    /** 录音开始时间 */
    @Excel(name = "录音开始时间")
    private String recordStartTime;

    /** 录音文件名 */
    @Excel(name = "录音文件名")
    private String recordObjectName;

    /** 录音文件名所在的目录名 */
    @Excel(name = "录音文件名所在的目录名")
    private String recordBucketName;

    /** 存放录音文件的域名 */
    @Excel(name = "存放录音文件的域名")
    private String recordDomain;

    /** 录音文件地址 */
    @Excel(name = "录音文件地址")
    private String recordFileUrl;

    /** 录音文件存储在本地地址 */
    @Excel(name = "录音文件存储在本地地址")
    private String recordFileUrlLocal;

    /** 呼叫的业务类型（003：AX模式） */
    @Excel(name = "呼叫的业务类型", readConverterExp = "003=AX模式")
    private String serviceType;

    /** 话单生成的服务器设备对应的主机名 */
    @Excel(name = "话单生成的服务器设备对应的主机名")
    private String hostName;

    /** 绑定ID */
    @Excel(name = "绑定ID")
    private String subscriptionId;

    /** 隐私保护号码（X号码）的城市码 */
    @Excel(name = "隐私保护号码", readConverterExp = "X=号码")
    private String areaCode;

    /** 呼叫的通话时长，单位为秒。 */
    @Excel(name = "呼叫的通话时长，单位为秒。")
    private Long callDuration;
}
