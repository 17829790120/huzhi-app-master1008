package com.wlwq.service;

import cn.hutool.core.util.URLUtil;
import cn.hutool.http.HttpRequest;
import com.wlwq.common.exception.ApiException;
import com.wlwq.common.utils.StringUtils;
import com.wlwq.config.BaiDuAIConfig;
import com.wlwq.constant.IdCardConstant;
import com.wlwq.constant.IdCardNumberTypeConstant;
import com.wlwq.constant.IdCardRiskTypeConstant;
import com.wlwq.params.BusinessLicenseParams;
import com.wlwq.params.DrivingLicenseParams;
import com.wlwq.params.IdCardParams;
import com.wlwq.params.VehicleLicenseParams;
import com.wlwq.vo.BusinessLicenseInfo;
import com.wlwq.vo.DrivingLicenseInfo;
import com.wlwq.vo.IdCardInfo;
import com.wlwq.vo.VehicleLicenseInfo;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @ClassName TextRecognitionService
 * @Description 文字识别服务
 * @Date 2021/2/7 11:02
 * @Author Rick wlwq
 */
@Component
@Slf4j
public class TextRecognitionService {

    @Autowired
    private AuthService authService;

    /**
     * @Description 行驶证识别
     * @author Rick wlwq
     * @Date 2021/7/17 18:05
     */
    public VehicleLicenseInfo vehicleLicense(VehicleLicenseParams params) {
        String result = HttpRequest.post(BaiDuAIConfig.VEHICLE_LICENSE_URL)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("apikey", BaiDuAIConfig.API_KEY)
                .form(assembleRecognitionVehicleLicenseMap(params))
                //超时，毫秒
                .timeout(20000)
                .execute().body();
        log.info("行驶证{{}}识别返回参数：{{}}", params.getVehicleLicenseSide(), result);
        return analysisVehicleLicenseInfo(result, params.getVehicleLicenseSide());
    }

    /**
     * @Description 驾驶证识别
     * @author Rick wlwq
     * @Date 2021/7/17 17:40
     */
    public DrivingLicenseInfo drivingLicense(DrivingLicenseParams params) {
        String result = HttpRequest.post(BaiDuAIConfig.DRIVING_LICENSE_URL)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("apikey", BaiDuAIConfig.API_KEY)
                .form(assembleRecognitionDrivingLicenseMap(params))
                //超时，毫秒
                .timeout(20000)
                .execute().body();
        log.info("驾驶证{{}}识别返回参数：{{}}", params.getDrivingLicenseSide(), result);
        return analysisDrivingLicenseInfo(result, params.getDrivingLicenseSide());
    }

    /**
     * @Description 营业执照识别
     * @author Rick wlwq
     * @Date 2021/7/17 16:17
     */
    public BusinessLicenseInfo businessLicense(BusinessLicenseParams params) {
        String result = HttpRequest.post(BaiDuAIConfig.BUSINESS_LICENSE_URL)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("apikey", BaiDuAIConfig.API_KEY)
                .form(assembleRecognitionBusinessLicenseMap(params))
                //超时，毫秒
                .timeout(20000)
                .execute().body();
        log.info("营业执照识别返回参数：{{}}", result);
        return analysisBusinessLicenseInfo(result);
    }


    /**
     * @Description 身份证识别
     * @author Rick wlwq
     * @Date 2021/2/7 14:05
     */
    public IdCardInfo idCardInfo(IdCardParams params) {
        String result = HttpRequest.post(BaiDuAIConfig.ID_CARD_URL)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("apikey", BaiDuAIConfig.API_KEY)
                .form(assembleRecognitionIdCardMap(params))
                //超时，毫秒
                .timeout(20000)
                .execute().body();

        log.info("身份证{{}}识别返回参数：{{}}", params.getIdCardSide(), result);
        return analysisIdCardInfo(result, params.getIdCardSide());
    }


    /**
     * @Description 银行卡识别
     * @author Rick wlwq
     * @Date 2021/2/7 18:02
     * @Param imageBase64 base64编码的银行卡照片
     */
    public String bankCardInfo(String imageBase64) {
        Map<String, Object> params = new HashMap<>(3);
        params.put("access_token", authService.getAuth());
        params.put(URLUtil.encode("image"), URLUtil.encode(imageBase64, "UTF-8"));
        params.put(URLUtil.encode("detect_direction"), URLUtil.encode("true"));
        String result = HttpRequest.post(BaiDuAIConfig.BANK_URL)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("apikey", BaiDuAIConfig.API_KEY)
                .form(params)
                //超时，毫秒
                .timeout(20000)
                .execute().body();
        return result;
    }

    /**
     * 组装行驶证识别参数
     *
     * @param params
     * @return
     */
    private Map<String, Object> assembleRecognitionVehicleLicenseMap(VehicleLicenseParams params) {
        Map<String, Object> map = new HashMap<>(5);
        map.put("access_token", authService.getAuth());
        map.put("image", params.getImageBase64());
        map.put("url", params.getImageUrl());
        map.put("detect_direction", params.getDetectDirection());
        map.put("vehicle_license_side", params.getVehicleLicenseSide());
        return map;
    }

    /**
     * 组装驾驶证识别参数
     *
     * @param params
     * @return
     */
    private Map<String, Object> assembleRecognitionDrivingLicenseMap(DrivingLicenseParams params) {
        Map<String, Object> map = new HashMap<>(5);
        map.put("access_token", authService.getAuth());
        map.put("image", params.getImageBase64());
        map.put("url", params.getImageUrl());
        map.put("detect_direction", params.getDetectDirection());
        map.put("driving_license_side", params.getDrivingLicenseSide());
        return map;
    }

    /**
     * 组装身份证识别参数
     *
     * @return
     */
    public Map<String, Object> assembleRecognitionIdCardMap(IdCardParams params) {
        Map<String, Object> map = new HashMap<>(6);
        map.put("access_token", authService.getAuth());
        map.put("image", params.getImageBase64());
        map.put("url", params.getImageUrl());
        map.put("id_card_side", params.getIdCardSide());
        map.put("detect_risk", params.getDetectRisk());
        map.put("detect_photo", params.getDetectPhoto());
        return map;
    }

    /**
     * 解析行驶证识别数据
     *
     * @param jsonStr
     * @param side
     * @return
     */
    private VehicleLicenseInfo analysisVehicleLicenseInfo(String jsonStr, String side) {
        JSONObject jsonObject = new JSONObject(jsonStr);
        if (jsonObject.has("error_code")) {
            throw new ApiException(jsonObject.getInt("error_code") + "-" + jsonObject.getString("error_msg"));
        }
        VehicleLicenseInfo info = new VehicleLicenseInfo();
        Iterator<String> it = jsonObject.keys();
        while (it.hasNext()) {
            String key = it.next();
            if (key.equals("words_result")) {
                JSONObject result = jsonObject.getJSONObject(key);
                Iterator<String> ite = result.keys();
                while (ite.hasNext()) {
                    String k = ite.next();
                    JSONObject r = result.getJSONObject(k);
                    String value = r.getString("words");
                    if (side.equals(IdCardConstant.ID_CARD_SIDE_FONT)) {
                        switch (k) {
                            case "车辆识别代号":
                                info.setVehicleNumber(value);
                                break;
                            case "住址":
                                info.setAddress(value);
                                break;
                            case "发证日期":
                                info.setIssueDate(value);
                                break;
                            case "发证单位":
                                info.setIssueUnit(value);
                                break;
                            case "品牌型号":
                                info.setVehicleBrand(value);
                                break;
                            case "车辆类型":
                                info.setVehicleType(value);
                                break;
                            case "所有人":
                                info.setOwner(value);
                                break;
                            case "使用性质":
                                info.setUseNature(value);
                                break;
                            case "发动机号码":
                                info.setEngineNumber(value);
                                break;
                            case "号牌号码":
                                info.setVehicleLicenseNumber(value);
                                break;
                            case "注册日期":
                                info.setRegisterDate(value);
                                break;
                            default:
                        }
                    } else {
                        switch (k) {
                            case "档案编号":
                                info.setDocumentNumber(value);
                                break;
                            case "核定载人数":
                                info.setLoadNumber(value);
                                break;
                            case "总质量":
                                info.setTotalWeight(value);
                                break;
                            case "整备质量":
                                info.setCurbWeight(value);
                                break;
                            case "核定载质量":
                                info.setLoadWeight(value);
                                break;
                            case "外廓尺寸":
                                info.setGabarite(value);
                                break;
                            case "准牵引总质量":
                                info.setTractionMass(value);
                                break;
                            case "备注":
                                info.setRemark(value);
                                break;
                            case "检验记录":
                                info.setInspectionRecord(value);
                                break;
                            default:
                        }
                    }

                }
            }

        }
        return info;
    }

    /**
     * 组装营业执照识别参数
     *
     * @return
     */
    public Map<String, Object> assembleRecognitionBusinessLicenseMap(BusinessLicenseParams params) {
        Map<String, Object> map = new HashMap<>(6);
        map.put("access_token", authService.getAuth());
        map.put("image", params.getImageBase64());
        map.put("url", params.getImageUrl());
        return map;
    }

    /**
     * 解析营业执照识别数据
     *
     * @param jsonStr
     * @return
     */
    private BusinessLicenseInfo analysisBusinessLicenseInfo(String jsonStr) {
        JSONObject jsonObject = new JSONObject(jsonStr);
        if (jsonObject.has("error_code")) {
            throw new ApiException(jsonObject.getInt("error_code") + "-" + jsonObject.getString("error_msg"));
        }
        BusinessLicenseInfo info = new BusinessLicenseInfo();
        Iterator<String> it = jsonObject.keys();
        while (it.hasNext()) {
            String key = it.next();
            if (key.equals("words_result")) {
                JSONObject result = jsonObject.getJSONObject(key);
                Iterator<String> ite = result.keys();
                while (ite.hasNext()) {
                    String k = ite.next();
                    JSONObject r = result.getJSONObject(k);
                    String value = r.getString("words");
                    switch (k) {
                        case "社会信用代码":
                            info.setSocialCreditCode(value);
                            break;
                        case "组成形式":
                            info.setOrganizationType(value);
                            break;
                        case "经营范围":
                            info.setBusinessScope(value);
                            break;
                        case "成立日期":
                            info.setEstablishmentDate(value);
                            break;
                        case "法人":
                            info.setLegalPerson(value);
                            break;
                        case "注册资本":
                            info.setRegisteredCapital(value);
                            break;
                        case "证件编号":
                            info.setBusinessLicenseCode(value);
                            break;
                        case "地址":
                            info.setSite(value);
                            break;
                        case "单位名称":
                            info.setOrganizationName(value);
                            break;
                        case "有效期":
                            info.setValidityDate(value);
                            break;
                        case "类型":
                            info.setType(value);
                            break;
                        default:
                    }
                }
            }

        }
        return info;
    }

    /**
     * 解析驾驶证识别数据
     *
     * @param jsonStr
     * @param side
     * @return
     */
    private DrivingLicenseInfo analysisDrivingLicenseInfo(String jsonStr, String side) {
        JSONObject jsonObject = new JSONObject(jsonStr);
        if (jsonObject.has("error_code")) {
            throw new ApiException(jsonObject.getInt("error_code") + "-" + jsonObject.getString("error_msg"));
        }
        DrivingLicenseInfo info = new DrivingLicenseInfo();
        Iterator<String> it = jsonObject.keys();
        while (it.hasNext()) {
            String key = it.next();
            if (key.equals("words_result")) {
                JSONObject result = jsonObject.getJSONObject(key);
                Iterator<String> ite = result.keys();
                while (ite.hasNext()) {
                    String k = ite.next();
                    JSONObject r = result.getJSONObject(k);
                    String value = r.getString("words");
                    if (side.equals(IdCardConstant.ID_CARD_SIDE_FONT)) {
                        switch (k) {
                            case "姓名":
                                info.setName(value);
                                break;
                            case "至":
                                info.setOverDate(value);
                                break;
                            case "住址":
                                info.setAddress(value);
                                break;
                            case "出生日期":
                                info.setBirthday(value);
                                break;
                            case "证号":
                                info.setIdCardNumber(value);
                                break;
                            case "性别":
                                info.setSex(value);
                                break;
                            case "初次领证日期":
                                info.setFirstGetDate(value);
                                break;
                            case "国籍":
                                info.setNationality(value);
                                break;
                            case "准驾车型":
                                info.setCanDrivingCarType(value);
                                break;
                            case "有效期限":
                                info.setStartDate(value);
                                break;
                            case "发证单位":
                                info.setIssueUnit(value);
                                break;
                            default:
                        }
                    } else {
                        switch (k) {
                            case "记录":
                                info.setRecord(value);
                                break;
                            case "档案编号":
                                info.setFileNumber(value);
                                break;
                            default:
                        }
                    }

                }
            }

        }
        return info;
    }

    /**
     * @Description 解析识别结果返回自定义封装对象-身份证
     * @author Rick wlwq
     * @Date 2021/2/7 14:53
     */
    public IdCardInfo analysisIdCardInfo(String jsonStr, String side) {
        JSONObject jsonObject = new JSONObject(jsonStr);
        if (jsonObject.has("error_code")) {
            throw new ApiException(jsonObject.getInt("error_code") + "-" + jsonObject.getString("error_msg"));
        }
        IdCardInfo info = new IdCardInfo();
        if (side.equals(IdCardConstant.ID_CARD_SIDE_FONT)) {
            if (StringUtils.isNotBlank(jsonObject.getString("risk_type"))) {
                if (jsonObject.getString("risk_type").equals(IdCardRiskTypeConstant.NORMAL)) {
                    info.setRiskType(jsonObject.getString("risk_type"));
                } else {
                    throw new ApiException(IdCardRiskTypeConstant.getMsg(jsonObject.getString("risk_type")));
                }
            }
            jsonObject.getInt("idcard_number_type");
            if (jsonObject.getInt("idcard_number_type") == IdCardNumberTypeConstant.NO_DIFFERENCE) {
                info.setIdCardNumberType(jsonObject.getInt("idcard_number_type"));
            } else {
                throw new ApiException(IdCardNumberTypeConstant.getMsg(jsonObject.getInt("idcard_number_type")));
            }
            if (StringUtils.isNotBlank(jsonObject.getString("photo"))) {
                info.setHeadPhoto("data:image/png;base64," + jsonObject.getString("photo"));
            }
        }
        Iterator<String> it = jsonObject.keys();
        while (it.hasNext()) {
            String key = it.next();
            if (key.equals("words_result")) {
                JSONObject result = jsonObject.getJSONObject(key);
                Iterator<String> ite = result.keys();
                while (ite.hasNext()) {
                    String k = ite.next();
                    JSONObject r = result.getJSONObject(k);
                    String value = r.getString("words");
                    switch (k) {
                        case "姓名":
                            info.setRealName(value);
                            break;
                        case "民族":
                            info.setNation(value);
                            break;
                        case "住址":
                            info.setAddress(value);
                            break;
                        case "公民身份号码":
                            info.setIdCardNumber(value);
                            break;
                        case "出生":
                            info.setBirthday(value);
                            break;
                        case "性别":
                            info.setSex(value);
                            break;
                        case "失效日期":
                            info.setPastDueDate(value);
                            break;
                        case "签发日期":
                            info.setSignDate(value);
                            break;
                        case "签发机关":
                            info.setSignDept(value);
                            break;
                        default:
                    }
                }
            }

        }
        return info;
    }


}
