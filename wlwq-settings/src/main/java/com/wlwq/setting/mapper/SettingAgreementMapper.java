package com.wlwq.setting.mapper;

import com.wlwq.setting.domain.SettingAgreement;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 协议Mapper接口
 *
 * @author Rick wlwq
 * @date 2021-06-07
 */
public interface SettingAgreementMapper {
    /**
     * 查询协议
     *
     * @param agreementId 协议ID
     * @return 协议
     */
    public SettingAgreement selectSettingAgreementById(String agreementId);

    /**
     * 查询协议列表
     *
     * @param settingAgreement 协议
     * @return 协议集合
     */
    public List<SettingAgreement> selectSettingAgreementList(SettingAgreement settingAgreement);

    /**
     * 新增协议
     *
     * @param settingAgreement 协议
     * @return 结果
     */
    public int insertSettingAgreement(SettingAgreement settingAgreement);

    /**
     * 修改协议
     *
     * @param settingAgreement 协议
     * @return 结果
     */
    public int updateSettingAgreement(SettingAgreement settingAgreement);

    /**
     * 删除协议
     *
     * @param agreementId 协议ID
     * @return 结果
     */
    public int deleteSettingAgreementById(String agreementId);

    /**
     * 批量删除协议
     *
     * @param agreementIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSettingAgreementByIds(String[] agreementIds);

    /**
     * 根据ID获取协议内容
     *
     * @param agreementId
     * @return
     */
    String selectAgreementContentById(@Param("agreementId") String agreementId);
}
