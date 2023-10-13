package com.wlwq.service;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.wlwq.config.BaiDuAIConfig;
import com.wlwq.constant.ContentTypeConstant;
import com.wlwq.constant.FileTypeConstant;
import com.wlwq.params.ContentAuthParams;
import com.wlwq.vo.ContentAuditResult;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName ContentAuditService
 * @Description 内容审核服务
 * @Date 2021/3/13 10:17
 * @Author Rick wlwq
 */
@Component
public class ContentAuditService {

    @Autowired
    private AuthService authService;

    /**
     * 内容审核
     * @param params
     */
    public ContentAuditResult audit(ContentAuthParams params){
        if (params.getContentType().equals(ContentTypeConstant.TEXT)){
            return textAudit(params.getContent());
        }
        if (params.getContentType().equals(ContentTypeConstant.IMAGE)){
            return imageAudit(params.getContent(),params.getFileType());
        }
        if (params.getContentType().equals(ContentTypeConstant.VIDEO)){
            return videoAudit(params.getContent(),params.getVideoName());
        }
        if (params.getContentType().equals(ContentTypeConstant.VOICE)){
            return voiceAudit(params.getContent(),params.getFileType(),params.getVoiceFmt());
        }
        return ContentAuditResult.builder()
                .conclusionType(0)
                .conclusion("暂未发现该识别类型！")
                .build();
    }


    /**
    *  @Description 音频审核
    *  @author Rick wlwq
    *  @Date   2021/3/13 12:11
    */
    public ContentAuditResult voiceAudit(String voiceUrl,String fileType,String voiceFmt){
        Map<String,Object> paramsMap = new HashMap<>(3);
        paramsMap.put("access_token", authService.getAuth());
        paramsMap.put("rawText", true);
        paramsMap.put("split", true);
        paramsMap.put("fmt", voiceFmt);
        if (fileType.equals(FileTypeConstant.IMAGE_URL)) {
            paramsMap.put("url", voiceUrl);
        }
        if (fileType.equals(FileTypeConstant.IMAGE_BASE64)) {
            paramsMap.put("base64",voiceUrl);
        }
        String result = HttpRequest.post(BaiDuAIConfig.AUDIT_VOICE_URL)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("apikey", BaiDuAIConfig.API_KEY)
                .form(paramsMap)
                //超时，毫秒
                .timeout(20000)
                .execute().body();
        System.out.println(result);
        JSONObject jsonObject = new JSONObject(result);
        return ContentAuditResult.builder()
                .conclusion(jsonObject.getString("conclusion"))
                .conclusionType(jsonObject.getInt("conclusionType"))
                .rawText(jsonObject.getString("rawText"))
                .build();
    }


    /**
    *  @Description 短视频审核
    *  @author Rick wlwq
    *  @Date   2021/3/13 11:52
    */
    public ContentAuditResult videoAudit(String videoUrl,String videoName){
        Map<String,Object> paramsMap = new HashMap<>(3);
        paramsMap.put("access_token", authService.getAuth());
        paramsMap.put("name", videoName);
        paramsMap.put("videoUrl", videoUrl);
        paramsMap.put("extId", videoUrl);
        String result = HttpRequest.post(BaiDuAIConfig.AUDIT_VIDEO_URL)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("apikey", BaiDuAIConfig.API_KEY)
                .form(paramsMap)
                //超时，毫秒
                .timeout(20000)
                .execute().body();
        System.out.println(result);
        JSONObject jsonObject = new JSONObject(result);
        return ContentAuditResult.builder()
                .conclusion(jsonObject.getString("conclusion"))
                .conclusionType(jsonObject.getInt("conclusionType"))
                .build();
    }

    /**
    *  @Description 图片审核
    *  @author Rick wlwq
    *  @Date   2021/3/13 11:25
    */
    public ContentAuditResult imageAudit(String image,String imageType){
        Map<String,Object> paramsMap = new HashMap<>(2);
        paramsMap.put("access_token", authService.getAuth());
        if (imageType.equals(FileTypeConstant.IMAGE_URL)) {
            paramsMap.put("imgUrl",image);
        }
        if (imageType.equals(FileTypeConstant.IMAGE_BASE64)) {
            paramsMap.put("image",image);
        }
        String result = HttpRequest.post(BaiDuAIConfig.AUDIT_IMAGE_URL)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("apikey", BaiDuAIConfig.API_KEY)
                .form(paramsMap)
                //超时，毫秒
                .timeout(20000)
                .execute().body();
        JSONObject jsonObject = new JSONObject(result);
        return ContentAuditResult.builder()
                .conclusion(jsonObject.getString("conclusion"))
                .conclusionType(jsonObject.getInt("conclusionType"))
                .build();
    }

    /**
    *  @Description 文本审核
    *  @author Rick wlwq
    *  @Date   2021/3/13 11:02
    */
    public ContentAuditResult textAudit(String text){
        Map<String,Object> paramsMap = new HashMap<>(2);
        paramsMap.put("access_token", authService.getAuth());
        paramsMap.put("text",text);
        String result = HttpRequest.post(BaiDuAIConfig.AUDIT_TEXT_URL)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("apikey", BaiDuAIConfig.API_KEY)
                .form(paramsMap)
                //超时，毫秒
                .timeout(20000)
                .execute().body();
        JSONObject jsonObject = new JSONObject(result);
        return ContentAuditResult.builder()
                .conclusion(jsonObject.getString("conclusion"))
                .conclusionType(jsonObject.getInt("conclusionType"))
                .build();
    }
}
