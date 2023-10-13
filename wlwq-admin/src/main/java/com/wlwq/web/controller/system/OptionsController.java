package com.wlwq.web.controller.system;

import com.wlwq.api.domain.Options;
import com.wlwq.api.resultVO.OptionsVO;
import com.wlwq.api.service.IOptionsService;
import com.wlwq.common.annotation.Log;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.enums.BusinessType;
import com.wlwq.handlers.properties.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * 附件设置Controller
 *
 * @author Renbowen
 * @date 2020-09-26
 */
@Controller
@RequestMapping("/web/options")
public class OptionsController extends BaseController
{
    private String prefix = "system/options";

    private final IOptionsService optionsService;

    public OptionsController(IOptionsService optionsService) {
        this.optionsService = optionsService;
    }


    @RequiresPermissions("api:options:view")
    @GetMapping()
    public String options(ModelMap modelMap)
    {
        modelMap.put("optionVO", OptionsVO.builder()
                .attachmentType(optionsService.selectValueByKey("attachment_type"))
                // 阿里云
                .ossAliDomainProtocol(optionsService.selectValueByKey(AliOssProperties.OSS_PROTOCOL.getValue()))
                .ossAliDomain(optionsService.selectValueByKey(AliOssProperties.OSS_DOMAIN.getValue()))
                .ossAliEndpoint(optionsService.selectValueByKey(AliOssProperties.OSS_ENDPOINT.getValue()))
                .ossAliBucketName(optionsService.selectValueByKey(AliOssProperties.OSS_BUCKET_NAME.getValue()))
                .ossAliAccessKey(optionsService.selectValueByKey(AliOssProperties.OSS_ACCESS_KEY.getValue()))
                .ossAliAccessSecret(optionsService.selectValueByKey(AliOssProperties.OSS_ACCESS_SECRET.getValue()))
                .ossAliSource(optionsService.selectValueByKey(AliOssProperties.OSS_SOURCE.getValue()))
                .ossAliStyleRule(optionsService.selectValueByKey(AliOssProperties.OSS_STYLE_RULE.getValue()))
                .ossAliThumbnailStyleRule(optionsService.selectValueByKey(AliOssProperties.OSS_THUMBNAIL_STYLE_RULE.getValue()))
                // 百度云
                .bosBaiduDomainProtocol(optionsService.selectValueByKey(BaiduBosProperties.BOS_PROTOCOL.getValue()))
                .bosBaiduDomain(optionsService.selectValueByKey(BaiduBosProperties.BOS_DOMAIN.getValue()))
                .bosBaiduEndpoint(optionsService.selectValueByKey(BaiduBosProperties.BOS_ENDPOINT.getValue()))
                .bosBaiduBucketName(optionsService.selectValueByKey(BaiduBosProperties.BOS_BUCKET_NAME.getValue()))
                .bosBaiduAccessKey(optionsService.selectValueByKey(BaiduBosProperties.BOS_ACCESS_KEY.getValue()))
                .bosBaiduSecretKey(optionsService.selectValueByKey(BaiduBosProperties.BOS_SECRET_KEY.getValue()))
                .bosBaiduStyleRule(optionsService.selectValueByKey(BaiduBosProperties.BOS_STYLE_RULE.getValue()))
                .bosBaiduThumbnailStyleRule(optionsService.selectValueByKey(BaiduBosProperties.BOS_THUMBNAIL_STYLE_RULE.getValue()))
                // 华为云
                .obsHuaweiDomainProtocol(optionsService.selectValueByKey(HuaweiObsProperties.OSS_PROTOCOL.getValue()))
                .obsHuaweiDomain(optionsService.selectValueByKey(HuaweiObsProperties.OSS_DOMAIN.getValue()))
                .obsHuaweiEndpoint(optionsService.selectValueByKey(HuaweiObsProperties.OSS_ENDPOINT.getValue()))
                .obsHuaweiBucketName(optionsService.selectValueByKey(HuaweiObsProperties.OSS_BUCKET_NAME.getValue()))
                .obsHuaweiAccessKey(optionsService.selectValueByKey(HuaweiObsProperties.OSS_ACCESS_KEY.getValue()))
                .obsHuaweiAccessSecret(optionsService.selectValueByKey(HuaweiObsProperties.OSS_ACCESS_SECRET.getValue()))
                .obsHuaweiSource(optionsService.selectValueByKey(HuaweiObsProperties.OSS_SOURCE.getValue()))
                .obsHuaweiStyleRule(optionsService.selectValueByKey(HuaweiObsProperties.OSS_STYLE_RULE.getValue()))
                .obsHuaweiThumbnailStyleRule(optionsService.selectValueByKey(HuaweiObsProperties.OSS_THUMBNAIL_STYLE_RULE.getValue()))
                // 七牛云
                .ossQiniuZone(optionsService.selectValueByKey(QiniuOssProperties.OSS_ZONE.getValue()))
                .ossQiniuAccessKey(optionsService.selectValueByKey(QiniuOssProperties.OSS_ACCESS_KEY.getValue()))
                .ossQiniuSecretKey(optionsService.selectValueByKey(QiniuOssProperties.OSS_SECRET_KEY.getValue()))
                .ossQiniuSource(optionsService.selectValueByKey(QiniuOssProperties.OSS_SOURCE.getValue()))
                .ossQiniuDomainProtocol(optionsService.selectValueByKey(QiniuOssProperties.OSS_PROTOCOL.getValue()))
                .ossQiniuDomain(optionsService.selectValueByKey(QiniuOssProperties.OSS_DOMAIN.getValue()))
                .ossQiniuBucket(optionsService.selectValueByKey(QiniuOssProperties.OSS_BUCKET.getValue()))
                .ossQiniuStyleRule(optionsService.selectValueByKey(QiniuOssProperties.OSS_STYLE_RULE.getValue()))
                .ossQiniuThumbnailStyleRule(optionsService.selectValueByKey(QiniuOssProperties.OSS_THUMBNAIL_STYLE_RULE.getValue()))
                // Smms
                .smmsApiSecretToken(optionsService.selectValueByKey(SmmsProperties.SMMS_API_SECRET_TOKEN.getValue()))
                // 腾讯云
                .cosTencentDomainProtocol(optionsService.selectValueByKey(TencentCosProperties.COS_PROTOCOL.getValue()))
                .cosTencentDomain(optionsService.selectValueByKey(TencentCosProperties.COS_DOMAIN.getValue()))
                .cosTencentRegion(optionsService.selectValueByKey(TencentCosProperties.COS_REGION.getValue()))
                .cosTencentBucketName(optionsService.selectValueByKey(TencentCosProperties.COS_BUCKET_NAME.getValue()))
                .cosTencentSecretId(optionsService.selectValueByKey(TencentCosProperties.COS_SECRET_ID.getValue()))
                .cosTencentSecretKey(optionsService.selectValueByKey(TencentCosProperties.COS_SECRET_KEY.getValue()))
                .cosTencentSource(optionsService.selectValueByKey(TencentCosProperties.COS_SOURCE.getValue()))
                .cosTencentStyleRule(optionsService.selectValueByKey(TencentCosProperties.COS_STYLE_RULE.getValue()))
                .cosTencentThumbnailStyleRule(optionsService.selectValueByKey(TencentCosProperties.COS_THUMBNAIL_STYLE_RULE.getValue()))
                // 又拍云存储
                .ossUpyunSource(optionsService.selectValueByKey(UpOssProperties.OSS_SOURCE.getValue()))
                .ossUpyunPassword(optionsService.selectValueByKey(UpOssProperties.OSS_PASSWORD.getValue()))
                .ossUpyunBucket(optionsService.selectValueByKey(UpOssProperties.OSS_BUCKET.getValue()))
                .ossUpyunDomainProtocol(optionsService.selectValueByKey(UpOssProperties.OSS_PROTOCOL.getValue()))
                .ossUpyunDomain(optionsService.selectValueByKey(UpOssProperties.OSS_DOMAIN.getValue()))
                .ossUpyunOperator(optionsService.selectValueByKey(UpOssProperties.OSS_OPERATOR.getValue()))
                .ossUpyunStyleRule(optionsService.selectValueByKey(UpOssProperties.OSS_STYLE_RULE.getValue()))
                .ossUpyunThumbnailStyleRule(optionsService.selectValueByKey(UpOssProperties.OSS_THUMBNAIL_STYLE_RULE.getValue()))
                .build());
        return prefix + "/edit";
    }

    /**
     * 修改保存附件设置
     */
    @RequiresPermissions("api:options:edit")
    @Log(title = "附件设置", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    @Transactional
    public AjaxResult editSave(OptionsVO options)
    {
        // 根据key查询  若为空
        if (StringUtils.isBlank(optionsService.selectValueByKey("attachment_type"))){
            // 新增
            optionsService.insertOptions(Options.builder()
                    .optionKey("attachment_type")
                    .optionValue(options.getAttachmentType())
                    .createDate(new Date())
                    .updateDate(new Date())
                    .build());
        }else {
            // 修改
            optionsService.updateOptionsByKey("attachment_type",options.getAttachmentType());
        }
        if (options.getAttachmentType().equals("LOCAL")){
            return success();
        }

        if (options.getAttachmentType().equals("UPOSS")){
            if (StringUtils.isNotBlank(options.getOssUpyunSource())){
                // 根据key查询  若为空
                if (StringUtils.isBlank(optionsService.selectValueByKey(UpOssProperties.OSS_SOURCE.getValue()))){
                    // 新增
                    optionsService.insertOptions(Options.builder()
                            .optionKey(UpOssProperties.OSS_SOURCE.getValue())
                            .optionValue(options.getOssUpyunSource())
                            .createDate(new Date())
                            .updateDate(new Date())
                            .build());
                }else {
                    // 修改
                    optionsService.updateOptionsByKey(UpOssProperties.OSS_SOURCE.getValue(),options.getOssUpyunSource());
                }
            }


            if (StringUtils.isNotBlank(options.getOssUpyunPassword())){
                if (StringUtils.isBlank(optionsService.selectValueByKey(UpOssProperties.OSS_PASSWORD.getValue()))){
                    // 新增
                    optionsService.insertOptions(Options.builder()
                            .optionKey(UpOssProperties.OSS_PASSWORD.getValue())
                            .optionValue(options.getOssUpyunPassword())
                            .createDate(new Date())
                            .updateDate(new Date())
                            .build());
                }else {
                    // 修改
                    optionsService.updateOptionsByKey(UpOssProperties.OSS_PASSWORD.getValue(),options.getOssUpyunPassword());
                }
            }

            if (StringUtils.isNotBlank(options.getOssUpyunBucket())){
                if (StringUtils.isBlank(optionsService.selectValueByKey(UpOssProperties.OSS_BUCKET.getValue()))){
                    // 新增
                    optionsService.insertOptions(Options.builder()
                            .optionKey(UpOssProperties.OSS_BUCKET.getValue())
                            .optionValue(options.getOssUpyunBucket())
                            .createDate(new Date())
                            .updateDate(new Date())
                            .build());
                }else {
                    // 修改
                    optionsService.updateOptionsByKey(UpOssProperties.OSS_BUCKET.getValue(),options.getOssUpyunBucket());
                }
            }


            if (StringUtils.isNotBlank(options.getOssUpyunDomainProtocol())){
                if (StringUtils.isBlank(optionsService.selectValueByKey(UpOssProperties.OSS_PROTOCOL.getValue()))){
                    // 新增
                    optionsService.insertOptions(Options.builder()
                            .optionKey(UpOssProperties.OSS_PROTOCOL.getValue())
                            .optionValue(options.getOssUpyunDomainProtocol())
                            .createDate(new Date())
                            .updateDate(new Date())
                            .build());
                }else {
                    // 修改
                    optionsService.updateOptionsByKey(UpOssProperties.OSS_PROTOCOL.getValue(),options.getOssUpyunDomainProtocol());
                }

            }

            if (StringUtils.isNotBlank(options.getOssUpyunDomain())){
                if (StringUtils.isBlank(optionsService.selectValueByKey(UpOssProperties.OSS_DOMAIN.getValue()))){
                    // 新增
                    optionsService.insertOptions(Options.builder()
                            .optionKey(UpOssProperties.OSS_DOMAIN.getValue())
                            .optionValue(options.getOssUpyunDomain())
                            .createDate(new Date())
                            .updateDate(new Date())
                            .build());
                }else {
                    // 修改
                    optionsService.updateOptionsByKey(UpOssProperties.OSS_DOMAIN.getValue(),options.getOssUpyunDomain());
                }
            }


            if (StringUtils.isNotBlank(options.getOssUpyunOperator())){
                if (StringUtils.isBlank(optionsService.selectValueByKey(UpOssProperties.OSS_OPERATOR.getValue()))){
                    // 新增
                    optionsService.insertOptions(Options.builder()
                            .optionKey(UpOssProperties.OSS_OPERATOR.getValue())
                            .optionValue(options.getOssUpyunOperator())
                            .createDate(new Date())
                            .updateDate(new Date())
                            .build());
                }else {
                    // 修改
                    optionsService.updateOptionsByKey(UpOssProperties.OSS_OPERATOR.getValue(),options.getOssUpyunOperator());
                }
            }


            if (StringUtils.isNotBlank(options.getOssUpyunStyleRule())){
                if (StringUtils.isBlank(optionsService.selectValueByKey(UpOssProperties.OSS_STYLE_RULE.getValue()))){
                    // 新增
                    optionsService.insertOptions(Options.builder()
                            .optionKey(UpOssProperties.OSS_STYLE_RULE.getValue())
                            .optionValue(options.getOssUpyunStyleRule())
                            .createDate(new Date())
                            .updateDate(new Date())
                            .build());
                }else {
                    // 修改
                    optionsService.updateOptionsByKey(UpOssProperties.OSS_STYLE_RULE.getValue(),options.getOssUpyunStyleRule());
                }
            }


            if (StringUtils.isNotBlank(options.getOssUpyunThumbnailStyleRule())){
                if (StringUtils.isBlank(optionsService.selectValueByKey(UpOssProperties.OSS_THUMBNAIL_STYLE_RULE.getValue()))){
                    // 新增
                    optionsService.insertOptions(Options.builder()
                            .optionKey(UpOssProperties.OSS_THUMBNAIL_STYLE_RULE.getValue())
                            .optionValue(options.getOssUpyunThumbnailStyleRule())
                            .createDate(new Date())
                            .updateDate(new Date())
                            .build());
                }else {
                    // 修改
                    optionsService.updateOptionsByKey(UpOssProperties.OSS_THUMBNAIL_STYLE_RULE.getValue(),options.getOssUpyunThumbnailStyleRule());
                }
            }



        }

        if (options.getAttachmentType().equals("QINIUOSS")){

            if (StringUtils.isNotBlank(options.getOssQiniuZone())){
                if (StringUtils.isBlank(optionsService.selectValueByKey(QiniuOssProperties.OSS_ZONE.getValue()))){
                    // 新增
                    optionsService.insertOptions(Options.builder()
                            .optionKey(QiniuOssProperties.OSS_ZONE.getValue())
                            .optionValue(options.getOssQiniuZone())
                            .createDate(new Date())
                            .updateDate(new Date())
                            .build());
                }else {
                    // 修改
                    optionsService.updateOptionsByKey(QiniuOssProperties.OSS_ZONE.getValue(),options.getOssQiniuZone());
                }
            }


            if (StringUtils.isNotBlank(options.getOssQiniuAccessKey())){
                if (StringUtils.isBlank(optionsService.selectValueByKey(QiniuOssProperties.OSS_ACCESS_KEY.getValue()))){
                    // 新增
                    optionsService.insertOptions(Options.builder()
                            .optionKey(QiniuOssProperties.OSS_ACCESS_KEY.getValue())
                            .optionValue(options.getOssQiniuAccessKey())
                            .createDate(new Date())
                            .updateDate(new Date())
                            .build());
                }else {
                    // 修改
                    optionsService.updateOptionsByKey(QiniuOssProperties.OSS_ACCESS_KEY.getValue(),options.getOssQiniuAccessKey());
                }
            }


            if (StringUtils.isNotBlank(options.getOssQiniuSecretKey())){
                if (StringUtils.isBlank(optionsService.selectValueByKey(QiniuOssProperties.OSS_SECRET_KEY.getValue()))){
                    // 新增
                    optionsService.insertOptions(Options.builder()
                            .optionKey(QiniuOssProperties.OSS_SECRET_KEY.getValue())
                            .optionValue(options.getOssQiniuSecretKey())
                            .createDate(new Date())
                            .updateDate(new Date())
                            .build());
                }else {
                    // 修改
                    optionsService.updateOptionsByKey(QiniuOssProperties.OSS_SECRET_KEY.getValue(),options.getOssQiniuSecretKey());
                }
            }


            if (StringUtils.isNotBlank(options.getOssQiniuSource())){
                if (StringUtils.isBlank(optionsService.selectValueByKey(QiniuOssProperties.OSS_SOURCE.getValue()))){
                    // 新增
                    optionsService.insertOptions(Options.builder()
                            .optionKey(QiniuOssProperties.OSS_SOURCE.getValue())
                            .optionValue(options.getOssQiniuSource())
                            .createDate(new Date())
                            .updateDate(new Date())
                            .build());
                }else {
                    // 修改
                    optionsService.updateOptionsByKey(QiniuOssProperties.OSS_SOURCE.getValue(),options.getOssQiniuSource());
                }
            }


            if (StringUtils.isNotBlank(options.getOssQiniuDomainProtocol())){
                if (StringUtils.isBlank(optionsService.selectValueByKey(QiniuOssProperties.OSS_PROTOCOL.getValue()))){
                    // 新增
                    optionsService.insertOptions(Options.builder()
                            .optionKey(QiniuOssProperties.OSS_PROTOCOL.getValue())
                            .optionValue(options.getOssQiniuDomainProtocol())
                            .createDate(new Date())
                            .updateDate(new Date())
                            .build());
                }else {
                    // 修改
                    optionsService.updateOptionsByKey(QiniuOssProperties.OSS_PROTOCOL.getValue(),options.getOssQiniuDomainProtocol());
                }
            }


            if (StringUtils.isNotBlank(options.getOssQiniuDomain())){
                if (StringUtils.isBlank(optionsService.selectValueByKey(QiniuOssProperties.OSS_DOMAIN.getValue()))){
                    // 新增
                    optionsService.insertOptions(Options.builder()
                            .optionKey(QiniuOssProperties.OSS_DOMAIN.getValue())
                            .optionValue(options.getOssQiniuDomain())
                            .createDate(new Date())
                            .updateDate(new Date())
                            .build());
                }else {
                    // 修改
                    optionsService.updateOptionsByKey(QiniuOssProperties.OSS_DOMAIN.getValue(),options.getOssQiniuDomain());
                }
            }


            if (StringUtils.isNotBlank(options.getOssQiniuBucket())){
                if (StringUtils.isBlank(optionsService.selectValueByKey(QiniuOssProperties.OSS_BUCKET.getValue()))){
                    // 新增
                    optionsService.insertOptions(Options.builder()
                            .optionKey(QiniuOssProperties.OSS_BUCKET.getValue())
                            .optionValue(options.getOssQiniuBucket())
                            .createDate(new Date())
                            .updateDate(new Date())
                            .build());
                }else {
                    // 修改
                    optionsService.updateOptionsByKey(QiniuOssProperties.OSS_BUCKET.getValue(),options.getOssQiniuBucket());
                }
            }


            if (StringUtils.isNotBlank(options.getOssQiniuStyleRule())){
                if (StringUtils.isBlank(optionsService.selectValueByKey(QiniuOssProperties.OSS_STYLE_RULE.getValue()))){
                    // 新增
                    optionsService.insertOptions(Options.builder()
                            .optionKey(QiniuOssProperties.OSS_STYLE_RULE.getValue())
                            .optionValue(options.getOssQiniuStyleRule())
                            .createDate(new Date())
                            .updateDate(new Date())
                            .build());
                }else {
                    // 修改
                    optionsService.updateOptionsByKey(QiniuOssProperties.OSS_STYLE_RULE.getValue(),options.getOssQiniuStyleRule());
                }
            }


            if (StringUtils.isNotBlank(options.getOssQiniuThumbnailStyleRule())){
                if (StringUtils.isBlank(optionsService.selectValueByKey(QiniuOssProperties.OSS_THUMBNAIL_STYLE_RULE.getValue()))){
                    // 新增
                    optionsService.insertOptions(Options.builder()
                            .optionKey(QiniuOssProperties.OSS_THUMBNAIL_STYLE_RULE.getValue())
                            .optionValue(options.getOssQiniuThumbnailStyleRule())
                            .createDate(new Date())
                            .updateDate(new Date())
                            .build());
                }else {
                    // 修改
                    optionsService.updateOptionsByKey(QiniuOssProperties.OSS_THUMBNAIL_STYLE_RULE.getValue(),options.getOssQiniuThumbnailStyleRule());
                }
            }


        }
        if (options.getAttachmentType().equals("SMMS")){
            if (StringUtils.isNotBlank(options.getSmmsApiSecretToken())){
                if (StringUtils.isBlank(optionsService.selectValueByKey(SmmsProperties.SMMS_API_SECRET_TOKEN.getValue()))){
                    // 新增
                    optionsService.insertOptions(Options.builder()
                            .optionKey(SmmsProperties.SMMS_API_SECRET_TOKEN.getValue())
                            .optionValue(options.getSmmsApiSecretToken())
                            .createDate(new Date())
                            .updateDate(new Date())
                            .build());
                }else {
                    // 修改
                    optionsService.updateOptionsByKey(SmmsProperties.SMMS_API_SECRET_TOKEN.getValue(),options.getSmmsApiSecretToken());
                }
            }

        }
        if (options.getAttachmentType().equals("ALIOSS")){

            if (StringUtils.isNotBlank(options.getOssAliDomainProtocol())){
                if (StringUtils.isBlank(optionsService.selectValueByKey(AliOssProperties.OSS_PROTOCOL.getValue()))){
                    // 新增
                    optionsService.insertOptions(Options.builder()
                            .optionKey(AliOssProperties.OSS_PROTOCOL.getValue())
                            .optionValue(options.getOssAliDomainProtocol())
                            .createDate(new Date())
                            .updateDate(new Date())
                            .build());
                }else {
                    // 修改
                    optionsService.updateOptionsByKey(AliOssProperties.OSS_PROTOCOL.getValue(),options.getOssAliDomainProtocol());
                }
            }

            if (StringUtils.isNotBlank(options.getOssAliDomain())){
                if (StringUtils.isBlank(optionsService.selectValueByKey(AliOssProperties.OSS_DOMAIN.getValue()))){
                    // 新增
                    optionsService.insertOptions(Options.builder()
                            .optionKey(AliOssProperties.OSS_DOMAIN.getValue())
                            .optionValue(options.getOssAliDomain())
                            .createDate(new Date())
                            .updateDate(new Date())
                            .build());
                }else {
                    // 修改
                    optionsService.updateOptionsByKey(AliOssProperties.OSS_DOMAIN.getValue(),options.getOssAliDomain());
                }
            }

            if (StringUtils.isNotBlank(options.getOssAliEndpoint())){
                if (StringUtils.isBlank(optionsService.selectValueByKey(AliOssProperties.OSS_ENDPOINT.getValue()))){
                    // 新增
                    optionsService.insertOptions(Options.builder()
                            .optionKey(AliOssProperties.OSS_ENDPOINT.getValue())
                            .optionValue(options.getOssAliEndpoint())
                            .createDate(new Date())
                            .updateDate(new Date())
                            .build());
                }else {
                    // 修改
                    optionsService.updateOptionsByKey(AliOssProperties.OSS_ENDPOINT.getValue(),options.getOssAliEndpoint());
                }
            }

            if (StringUtils.isNotBlank(options.getOssAliBucketName())){
                if (StringUtils.isBlank(optionsService.selectValueByKey(AliOssProperties.OSS_BUCKET_NAME.getValue()))){
                    // 新增
                    optionsService.insertOptions(Options.builder()
                            .optionKey(AliOssProperties.OSS_BUCKET_NAME.getValue())
                            .optionValue(options.getOssAliBucketName())
                            .createDate(new Date())
                            .updateDate(new Date())
                            .build());
                }else {
                    // 修改
                    optionsService.updateOptionsByKey(AliOssProperties.OSS_BUCKET_NAME.getValue(),options.getOssAliBucketName());
                }
            }


            if (StringUtils.isNotBlank(options.getOssAliAccessKey())){
                if (StringUtils.isBlank(optionsService.selectValueByKey(AliOssProperties.OSS_ACCESS_KEY.getValue()))){
                    // 新增
                    optionsService.insertOptions(Options.builder()
                            .optionKey(AliOssProperties.OSS_ACCESS_KEY.getValue())
                            .optionValue(options.getOssAliAccessKey())
                            .createDate(new Date())
                            .updateDate(new Date())
                            .build());
                }else {
                    // 修改
                    optionsService.updateOptionsByKey(AliOssProperties.OSS_ACCESS_KEY.getValue(),options.getOssAliAccessKey());
                }
            }

            if (StringUtils.isNotBlank(options.getOssAliAccessSecret())){
                if (StringUtils.isBlank(optionsService.selectValueByKey(AliOssProperties.OSS_ACCESS_SECRET.getValue()))){
                    // 新增
                    optionsService.insertOptions(Options.builder()
                            .optionKey(AliOssProperties.OSS_ACCESS_SECRET.getValue())
                            .optionValue(options.getOssAliAccessSecret())
                            .createDate(new Date())
                            .updateDate(new Date())
                            .build());
                }else {
                    // 修改
                    optionsService.updateOptionsByKey(AliOssProperties.OSS_ACCESS_SECRET.getValue(),options.getOssAliAccessSecret());
                }
            }

            if (StringUtils.isNotBlank(options.getOssAliSource())){
                if (StringUtils.isBlank(optionsService.selectValueByKey(AliOssProperties.OSS_SOURCE.getValue()))){
                    // 新增
                    optionsService.insertOptions(Options.builder()
                            .optionKey(AliOssProperties.OSS_SOURCE.getValue())
                            .optionValue(options.getOssAliSource())
                            .createDate(new Date())
                            .updateDate(new Date())
                            .build());
                }else {
                    // 修改
                    optionsService.updateOptionsByKey(AliOssProperties.OSS_SOURCE.getValue(),options.getOssAliSource());
                }
            }

            if (StringUtils.isNotBlank(options.getOssAliStyleRule())){
                if (StringUtils.isBlank(optionsService.selectValueByKey(AliOssProperties.OSS_STYLE_RULE.getValue()))){
                    // 新增
                    optionsService.insertOptions(Options.builder()
                            .optionKey(AliOssProperties.OSS_STYLE_RULE.getValue())
                            .optionValue(options.getOssAliStyleRule())
                            .createDate(new Date())
                            .updateDate(new Date())
                            .build());
                }else {
                    // 修改
                    optionsService.updateOptionsByKey(AliOssProperties.OSS_STYLE_RULE.getValue(),options.getOssAliStyleRule());
                }
            }


            if (StringUtils.isNotBlank(options.getOssAliThumbnailStyleRule())){
                if (StringUtils.isBlank(optionsService.selectValueByKey(AliOssProperties.OSS_THUMBNAIL_STYLE_RULE.getValue()))){
                    // 新增
                    optionsService.insertOptions(Options.builder()
                            .optionKey(AliOssProperties.OSS_THUMBNAIL_STYLE_RULE.getValue())
                            .optionValue(options.getOssAliThumbnailStyleRule())
                            .createDate(new Date())
                            .updateDate(new Date())
                            .build());
                }else {
                    // 修改
                    optionsService.updateOptionsByKey(AliOssProperties.OSS_THUMBNAIL_STYLE_RULE.getValue(),options.getOssAliThumbnailStyleRule());
                }
            }



        }
        if (options.getAttachmentType().equals("BAIDUBOS")){

            if (StringUtils.isNotBlank(options.getBosBaiduDomainProtocol())){
                if (StringUtils.isBlank(optionsService.selectValueByKey(BaiduBosProperties.BOS_PROTOCOL.getValue()))){
                    // 新增
                    optionsService.insertOptions(Options.builder()
                            .optionKey(BaiduBosProperties.BOS_PROTOCOL.getValue())
                            .optionValue(options.getBosBaiduDomainProtocol())
                            .createDate(new Date())
                            .updateDate(new Date())
                            .build());
                }else {
                    // 修改
                    optionsService.updateOptionsByKey(BaiduBosProperties.BOS_PROTOCOL.getValue(),options.getBosBaiduDomainProtocol());
                }
            }

            if (StringUtils.isNotBlank(options.getBosBaiduDomain())){
                if (StringUtils.isBlank(optionsService.selectValueByKey(BaiduBosProperties.BOS_DOMAIN.getValue()))){
                    // 新增
                    optionsService.insertOptions(Options.builder()
                            .optionKey(BaiduBosProperties.BOS_DOMAIN.getValue())
                            .optionValue(options.getBosBaiduDomain())
                            .createDate(new Date())
                            .updateDate(new Date())
                            .build());
                }else {
                    // 修改
                    optionsService.updateOptionsByKey(BaiduBosProperties.BOS_DOMAIN.getValue(),options.getBosBaiduDomain());
                }

            }

            if (StringUtils.isNotBlank(options.getBosBaiduEndpoint())){
                if (StringUtils.isBlank(optionsService.selectValueByKey(BaiduBosProperties.BOS_ENDPOINT.getValue()))){
                    // 新增
                    optionsService.insertOptions(Options.builder()
                            .optionKey(BaiduBosProperties.BOS_ENDPOINT.getValue())
                            .optionValue(options.getBosBaiduEndpoint())
                            .createDate(new Date())
                            .updateDate(new Date())
                            .build());
                }else {
                    // 修改
                    optionsService.updateOptionsByKey(BaiduBosProperties.BOS_ENDPOINT.getValue(),options.getBosBaiduEndpoint());
                }
            }

            if (StringUtils.isNotBlank(options.getBosBaiduBucketName())){
                if (StringUtils.isBlank(optionsService.selectValueByKey(BaiduBosProperties.BOS_BUCKET_NAME.getValue()))){
                    // 新增
                    optionsService.insertOptions(Options.builder()
                            .optionKey(BaiduBosProperties.BOS_BUCKET_NAME.getValue())
                            .optionValue(options.getBosBaiduBucketName())
                            .createDate(new Date())
                            .updateDate(new Date())
                            .build());
                }else {
                    // 修改
                    optionsService.updateOptionsByKey(BaiduBosProperties.BOS_BUCKET_NAME.getValue(),options.getBosBaiduBucketName());
                }
            }

            if (StringUtils.isNotBlank(options.getBosBaiduAccessKey())){
                if (StringUtils.isBlank(optionsService.selectValueByKey(BaiduBosProperties.BOS_ACCESS_KEY.getValue()))){
                    // 新增
                    optionsService.insertOptions(Options.builder()
                            .optionKey(BaiduBosProperties.BOS_ACCESS_KEY.getValue())
                            .optionValue(options.getBosBaiduAccessKey())
                            .createDate(new Date())
                            .updateDate(new Date())
                            .build());
                }else {
                    // 修改
                    optionsService.updateOptionsByKey(BaiduBosProperties.BOS_ACCESS_KEY.getValue(),options.getBosBaiduAccessKey());
                }
            }

            if (StringUtils.isNotBlank(options.getBosBaiduSecretKey())){
                if (StringUtils.isBlank(optionsService.selectValueByKey(BaiduBosProperties.BOS_SECRET_KEY.getValue()))){
                    // 新增
                    optionsService.insertOptions(Options.builder()
                            .optionKey(BaiduBosProperties.BOS_SECRET_KEY.getValue())
                            .optionValue(options.getBosBaiduSecretKey())
                            .createDate(new Date())
                            .updateDate(new Date())
                            .build());
                }else {
                    // 修改
                    optionsService.updateOptionsByKey(BaiduBosProperties.BOS_SECRET_KEY.getValue(),options.getBosBaiduSecretKey());
                }
            }


            if (StringUtils.isNotBlank(options.getBosBaiduStyleRule())){
                if (StringUtils.isBlank(optionsService.selectValueByKey(BaiduBosProperties.BOS_STYLE_RULE.getValue()))){
                    // 新增
                    optionsService.insertOptions(Options.builder()
                            .optionKey(BaiduBosProperties.BOS_STYLE_RULE.getValue())
                            .optionValue(options.getBosBaiduStyleRule())
                            .createDate(new Date())
                            .updateDate(new Date())
                            .build());
                }else {
                    // 修改
                    optionsService.updateOptionsByKey(BaiduBosProperties.BOS_STYLE_RULE.getValue(),options.getBosBaiduStyleRule());
                }
            }


            if (StringUtils.isNotBlank(options.getBosBaiduThumbnailStyleRule())){
                if (StringUtils.isBlank(optionsService.selectValueByKey(BaiduBosProperties.BOS_THUMBNAIL_STYLE_RULE.getValue()))){
                    // 新增
                    optionsService.insertOptions(Options.builder()
                            .optionKey(BaiduBosProperties.BOS_THUMBNAIL_STYLE_RULE.getValue())
                            .optionValue(options.getBosBaiduThumbnailStyleRule())
                            .createDate(new Date())
                            .updateDate(new Date())
                            .build());
                }else {
                    // 修改
                    optionsService.updateOptionsByKey(BaiduBosProperties.BOS_THUMBNAIL_STYLE_RULE.getValue(),options.getBosBaiduThumbnailStyleRule());
                }
            }




        }
        if (options.getAttachmentType().equals("TENCENTCOS")){

            if (StringUtils.isNotBlank(options.getCosTencentDomainProtocol())){
                if (StringUtils.isBlank(optionsService.selectValueByKey(TencentCosProperties.COS_PROTOCOL.getValue()))){
                    // 新增
                    optionsService.insertOptions(Options.builder()
                            .optionKey(TencentCosProperties.COS_PROTOCOL.getValue())
                            .optionValue(options.getCosTencentDomainProtocol())
                            .createDate(new Date())
                            .updateDate(new Date())
                            .build());
                }else {
                    // 修改
                    optionsService.updateOptionsByKey(TencentCosProperties.COS_PROTOCOL.getValue(),options.getCosTencentDomainProtocol());
                }
            }

            if (StringUtils.isNotBlank(options.getCosTencentDomain())){
                if (StringUtils.isBlank(optionsService.selectValueByKey(TencentCosProperties.COS_DOMAIN.getValue()))){
                    // 新增
                    optionsService.insertOptions(Options.builder()
                            .optionKey(TencentCosProperties.COS_DOMAIN.getValue())
                            .optionValue(options.getCosTencentDomain())
                            .createDate(new Date())
                            .updateDate(new Date())
                            .build());
                }else {
                    // 修改
                    optionsService.updateOptionsByKey(TencentCosProperties.COS_DOMAIN.getValue(),options.getCosTencentDomain());
                }
            }

            if (StringUtils.isNotBlank(options.getCosTencentRegion())){
                if (StringUtils.isBlank(optionsService.selectValueByKey(TencentCosProperties.COS_REGION.getValue()))){
                    // 新增
                    optionsService.insertOptions(Options.builder()
                            .optionKey(TencentCosProperties.COS_REGION.getValue())
                            .optionValue(options.getCosTencentRegion())
                            .createDate(new Date())
                            .updateDate(new Date())
                            .build());
                }else {
                    // 修改
                    optionsService.updateOptionsByKey(TencentCosProperties.COS_REGION.getValue(),options.getCosTencentRegion());
                }
            }


            if (StringUtils.isNotBlank(options.getCosTencentBucketName())){
                if (StringUtils.isBlank(optionsService.selectValueByKey(TencentCosProperties.COS_BUCKET_NAME.getValue()))){
                    // 新增
                    optionsService.insertOptions(Options.builder()
                            .optionKey(TencentCosProperties.COS_BUCKET_NAME.getValue())
                            .optionValue(options.getCosTencentBucketName())
                            .createDate(new Date())
                            .updateDate(new Date())
                            .build());
                }else {
                    // 修改
                    optionsService.updateOptionsByKey(TencentCosProperties.COS_BUCKET_NAME.getValue(),options.getCosTencentBucketName());
                }

            }

            if (StringUtils.isNotBlank(options.getCosTencentSecretId())){
                if (StringUtils.isBlank(optionsService.selectValueByKey(TencentCosProperties.COS_SECRET_ID.getValue()))){
                    // 新增
                    optionsService.insertOptions(Options.builder()
                            .optionKey(TencentCosProperties.COS_SECRET_ID.getValue())
                            .optionValue(options.getCosTencentSecretId())
                            .createDate(new Date())
                            .updateDate(new Date())
                            .build());
                }else {
                    // 修改
                    optionsService.updateOptionsByKey(TencentCosProperties.COS_SECRET_ID.getValue(),options.getCosTencentSecretId());
                }
            }


            if (StringUtils.isNotBlank(options.getCosTencentSecretKey())){
                if (StringUtils.isBlank(optionsService.selectValueByKey(TencentCosProperties.COS_SECRET_KEY.getValue()))){
                    // 新增
                    optionsService.insertOptions(Options.builder()
                            .optionKey(TencentCosProperties.COS_SECRET_KEY.getValue())
                            .optionValue(options.getCosTencentSecretKey())
                            .createDate(new Date())
                            .updateDate(new Date())
                            .build());
                }else {
                    // 修改
                    optionsService.updateOptionsByKey(TencentCosProperties.COS_SECRET_KEY.getValue(),options.getCosTencentSecretKey());
                }
            }


            if (StringUtils.isNotBlank(options.getCosTencentSource())){
                if (StringUtils.isBlank(optionsService.selectValueByKey(TencentCosProperties.COS_SOURCE.getValue()))){
                    // 新增
                    optionsService.insertOptions(Options.builder()
                            .optionKey(TencentCosProperties.COS_SOURCE.getValue())
                            .optionValue(options.getCosTencentSource())
                            .createDate(new Date())
                            .updateDate(new Date())
                            .build());
                }else {
                    // 修改
                    optionsService.updateOptionsByKey(TencentCosProperties.COS_SOURCE.getValue(),options.getCosTencentSource());
                }
            }


            if (StringUtils.isNotBlank(options.getCosTencentStyleRule())){
                if (StringUtils.isBlank(optionsService.selectValueByKey(TencentCosProperties.COS_STYLE_RULE.getValue()))){
                    // 新增
                    optionsService.insertOptions(Options.builder()
                            .optionKey(TencentCosProperties.COS_STYLE_RULE.getValue())
                            .optionValue(options.getCosTencentStyleRule())
                            .createDate(new Date())
                            .updateDate(new Date())
                            .build());
                }else {
                    // 修改
                    optionsService.updateOptionsByKey(TencentCosProperties.COS_STYLE_RULE.getValue(),options.getCosTencentStyleRule());
                }
            }


            if (StringUtils.isNotBlank(options.getCosTencentThumbnailStyleRule())){
                if (StringUtils.isBlank(optionsService.selectValueByKey(TencentCosProperties.COS_THUMBNAIL_STYLE_RULE.getValue()))){
                    // 新增
                    optionsService.insertOptions(Options.builder()
                            .optionKey(TencentCosProperties.COS_THUMBNAIL_STYLE_RULE.getValue())
                            .optionValue(options.getCosTencentThumbnailStyleRule())
                            .createDate(new Date())
                            .updateDate(new Date())
                            .build());
                }else {
                    // 修改
                    optionsService.updateOptionsByKey(TencentCosProperties.COS_THUMBNAIL_STYLE_RULE.getValue(),options.getCosTencentThumbnailStyleRule());
                }
            }


        }
        if (options.getAttachmentType().equals("HUAWEIOBS")){
            if (StringUtils.isNotBlank(options.getObsHuaweiDomainProtocol())){
                if (StringUtils.isBlank(optionsService.selectValueByKey(HuaweiObsProperties.OSS_PROTOCOL.getValue()))){
                    // 新增
                    optionsService.insertOptions(Options.builder()
                            .optionKey(HuaweiObsProperties.OSS_PROTOCOL.getValue())
                            .optionValue(options.getObsHuaweiDomainProtocol())
                            .createDate(new Date())
                            .updateDate(new Date())
                            .build());
                }else {
                    // 修改
                    optionsService.updateOptionsByKey(HuaweiObsProperties.OSS_PROTOCOL.getValue(),options.getObsHuaweiDomainProtocol());
                }
            }


            if (StringUtils.isNotBlank(options.getObsHuaweiDomain())){
                if (StringUtils.isBlank(optionsService.selectValueByKey(HuaweiObsProperties.OSS_DOMAIN.getValue()))){
                    // 新增
                    optionsService.insertOptions(Options.builder()
                            .optionKey(HuaweiObsProperties.OSS_DOMAIN.getValue())
                            .optionValue(options.getObsHuaweiDomain())
                            .createDate(new Date())
                            .updateDate(new Date())
                            .build());
                }else {
                    // 修改
                    optionsService.updateOptionsByKey(HuaweiObsProperties.OSS_DOMAIN.getValue(),options.getObsHuaweiDomain());
                }
            }


            if (StringUtils.isNotBlank(options.getObsHuaweiEndpoint())){
                if (StringUtils.isBlank(optionsService.selectValueByKey(HuaweiObsProperties.OSS_ENDPOINT.getValue()))){
                    // 新增
                    optionsService.insertOptions(Options.builder()
                            .optionKey(HuaweiObsProperties.OSS_ENDPOINT.getValue())
                            .optionValue(options.getObsHuaweiEndpoint())
                            .createDate(new Date())
                            .updateDate(new Date())
                            .build());
                }else {
                    // 修改
                    optionsService.updateOptionsByKey(HuaweiObsProperties.OSS_ENDPOINT.getValue(),options.getObsHuaweiEndpoint());
                }
            }


            if (StringUtils.isNotBlank(options.getObsHuaweiBucketName())){
                if (StringUtils.isBlank(optionsService.selectValueByKey(HuaweiObsProperties.OSS_BUCKET_NAME.getValue()))){
                    // 新增
                    optionsService.insertOptions(Options.builder()
                            .optionKey(HuaweiObsProperties.OSS_BUCKET_NAME.getValue())
                            .optionValue(options.getObsHuaweiBucketName())
                            .createDate(new Date())
                            .updateDate(new Date())
                            .build());
                }else {
                    // 修改
                    optionsService.updateOptionsByKey(HuaweiObsProperties.OSS_BUCKET_NAME.getValue(),options.getObsHuaweiBucketName());
                }
            }


            if (StringUtils.isNotBlank(options.getObsHuaweiAccessKey())){
                if (StringUtils.isBlank(optionsService.selectValueByKey(HuaweiObsProperties.OSS_ACCESS_KEY.getValue()))){
                    // 新增
                    optionsService.insertOptions(Options.builder()
                            .optionKey(HuaweiObsProperties.OSS_ACCESS_KEY.getValue())
                            .optionValue(options.getObsHuaweiAccessKey())
                            .createDate(new Date())
                            .updateDate(new Date())
                            .build());
                }else {
                    // 修改
                    optionsService.updateOptionsByKey(HuaweiObsProperties.OSS_ACCESS_KEY.getValue(),options.getObsHuaweiAccessKey());
                }
            }


            if (StringUtils.isNotBlank(options.getObsHuaweiAccessSecret())){
                if (StringUtils.isBlank(optionsService.selectValueByKey(HuaweiObsProperties.OSS_ACCESS_SECRET.getValue()))){
                    // 新增
                    optionsService.insertOptions(Options.builder()
                            .optionKey(HuaweiObsProperties.OSS_ACCESS_SECRET.getValue())
                            .optionValue(options.getObsHuaweiAccessSecret())
                            .createDate(new Date())
                            .updateDate(new Date())
                            .build());
                }else {
                    // 修改
                    optionsService.updateOptionsByKey(HuaweiObsProperties.OSS_ACCESS_SECRET.getValue(),options.getObsHuaweiAccessSecret());
                }

            }

            if (StringUtils.isNotBlank(options.getObsHuaweiSource())){
                if (StringUtils.isBlank(optionsService.selectValueByKey(HuaweiObsProperties.OSS_SOURCE.getValue()))){
                    // 新增
                    optionsService.insertOptions(Options.builder()
                            .optionKey(HuaweiObsProperties.OSS_SOURCE.getValue())
                            .optionValue(options.getObsHuaweiSource())
                            .createDate(new Date())
                            .updateDate(new Date())
                            .build());
                }else {
                    // 修改
                    optionsService.updateOptionsByKey(HuaweiObsProperties.OSS_SOURCE.getValue(),options.getObsHuaweiSource());
                }
            }


            if (StringUtils.isNotBlank(options.getObsHuaweiStyleRule())){
                if (StringUtils.isBlank(optionsService.selectValueByKey(HuaweiObsProperties.OSS_STYLE_RULE.getValue()))){
                    // 新增
                    optionsService.insertOptions(Options.builder()
                            .optionKey(HuaweiObsProperties.OSS_STYLE_RULE.getValue())
                            .optionValue(options.getObsHuaweiStyleRule())
                            .createDate(new Date())
                            .updateDate(new Date())
                            .build());
                }else {
                    // 修改
                    optionsService.updateOptionsByKey(HuaweiObsProperties.OSS_STYLE_RULE.getValue(),options.getObsHuaweiStyleRule());
                }
            }


            if (StringUtils.isNotBlank(options.getObsHuaweiThumbnailStyleRule())){
                if (StringUtils.isBlank(optionsService.selectValueByKey(HuaweiObsProperties.OSS_THUMBNAIL_STYLE_RULE.getValue()))){
                    // 新增
                    optionsService.insertOptions(Options.builder()
                            .optionKey(HuaweiObsProperties.OSS_THUMBNAIL_STYLE_RULE.getValue())
                            .optionValue(options.getObsHuaweiThumbnailStyleRule())
                            .createDate(new Date())
                            .updateDate(new Date())
                            .build());
                }else {
                    // 修改
                    optionsService.updateOptionsByKey(HuaweiObsProperties.OSS_THUMBNAIL_STYLE_RULE.getValue(),options.getObsHuaweiThumbnailStyleRule());
                }
            }

        }
        return success();
    }
}
