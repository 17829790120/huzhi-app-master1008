package com.wlwq.api.resultVO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName OptionsVO
 * @Description 操作返回值封装
 * @Date 2020/9/26 17:40
 * Create By Renbowen
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OptionsVO implements Serializable {

    /** 上传附件位置类型 .*/
    private String attachmentType;
//-----------阿里云----------------
    /** 绑定域名协议 .*/
    private String ossAliDomainProtocol;

    /** 绑定域名 .*/
    private String ossAliDomain;

    /** 地域节点 .*/
    private String ossAliEndpoint;

    /** 存储空间 .*/
    private String ossAliBucketName;

    /** access_key .*/
    private String ossAliAccessKey;

    /** access_secret .*/
    private String ossAliAccessSecret;

    /** 文件目录 .*/
    private String ossAliSource;

    /** 图片处理策略 .*/
    private String ossAliStyleRule;

    /** 缩略图处理策略 .*/
    private String ossAliThumbnailStyleRule;
    //--------------------阿里云结束---------------------------

    //-------------------百度云----------------------------------
    /** 绑定域名协议 .*/
    private String bosBaiduDomainProtocol;

    /** 绑定域名 .*/
    private String bosBaiduDomain;

    /** 节点 .*/
    private String bosBaiduEndpoint;

    /** 存储桶名字 .*/
    private String bosBaiduBucketName;

    /** access_key .*/
    private String bosBaiduAccessKey;

    /** secret_key .*/
    private String bosBaiduSecretKey;

    /** 图片处理策略 .*/
    private String bosBaiduStyleRule;

    /** 缩略图处理策略 .*/
    private String bosBaiduThumbnailStyleRule;
    //-----------------------------百度云结束--------------------------------------
    //-----------------------------华为云--------------------------------------
    /** 绑定域名协议 .*/
    private String obsHuaweiDomainProtocol;

    /** 绑定域名 .*/
    private String obsHuaweiDomain;

    /** 节点 .*/
    private String obsHuaweiEndpoint;

    /** 存储桶名字 .*/
    private String obsHuaweiBucketName;

    /** access_key .*/
    private String obsHuaweiAccessKey;

    /** access_secret .*/
    private String obsHuaweiAccessSecret;

    /** 文件目录 .*/
    private String obsHuaweiSource;

    /** 图片处理策略 .*/
    private String obsHuaweiStyleRule;

    /** 缩略图处理策略 .*/
    private String obsHuaweiThumbnailStyleRule;

    //---------------------华为云结束-----------------------------------
    //---------------------七牛云-----------------------------------
    /** 区域（代码） .*/
    private String ossQiniuZone;

    /** access_key .*/
    private String ossQiniuAccessKey;

    /** secret_key .*/
    private String ossQiniuSecretKey;

    /** 文件目录 .*/
    private String ossQiniuSource;

    /** 绑定域名协议 .*/
    private String ossQiniuDomainProtocol;

    /** 绑定域名 .*/
    private String ossQiniuDomain;

    /** 桶 .*/
    private String ossQiniuBucket;

    /** 图片处理策略 .*/
    private String ossQiniuStyleRule;

    /** 缩略图处理策略 .*/
    private String ossQiniuThumbnailStyleRule;
    //----------------------------七牛云结束------------------------------------------

    //----------------------------Smms------------------------------------------
    /** secret_token .*/
    private String smmsApiSecretToken;
    //----------------------------Smms结束------------------------------------------
    //----------------------------腾讯云存储------------------------------------------
    /** 绑定域名协议 .*/
    private String cosTencentDomainProtocol;

    /** 绑定域名 .*/
    private String cosTencentDomain;

    /** 区域（代码） .*/
    private String cosTencentRegion;

    /** 桶 .*/
    private String cosTencentBucketName;

    /** secret_id .*/
    private String cosTencentSecretId;

    /** secret_key .*/
    private String cosTencentSecretKey;

    /** 文件目录 .*/
    private String cosTencentSource;

    /** 图片处理策略 .*/
    private String cosTencentStyleRule;

    /** 缩略图处理策略 .*/
    private String cosTencentThumbnailStyleRule;

    //----------------------------腾讯云存储结束------------------------------------------

    //----------------------------又拍云存储------------------------------------------

    /** 文件目录 .*/
    private String ossUpyunSource;

    /** 操作员密码 .*/
    private String ossUpyunPassword;

    /** 桶 .*/
    private String ossUpyunBucket;

    /** 域名绑定协议 .*/
    private String ossUpyunDomainProtocol;

    /** 绑定域名 .*/
    private String ossUpyunDomain;

    /** 操作员 .*/
    private String ossUpyunOperator;

    /** 图片处理策略 .*/
    private String ossUpyunStyleRule;

    /** 缩略图处理策略 .*/
    private String ossUpyunThumbnailStyleRule;

}
