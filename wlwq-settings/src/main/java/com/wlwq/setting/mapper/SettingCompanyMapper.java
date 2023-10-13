package com.wlwq.setting.mapper;

import com.wlwq.setting.domain.SettingCompany;
import com.wlwq.setting.result.CompanyResult;

import java.util.List;

/**
 * 公司信息Mapper接口
 * 
 * @author Rick wlwq
 * @date 2021-06-07
 */
public interface SettingCompanyMapper 
{
    /**
     * 查询公司信息
     * 
     * @param companyId 公司信息ID
     * @return 公司信息
     */
    public SettingCompany selectSettingCompanyById(Long companyId);

    /**
     * 查询公司信息列表
     * 
     * @param settingCompany 公司信息
     * @return 公司信息集合
     */
    public List<SettingCompany> selectSettingCompanyList(SettingCompany settingCompany);

    /**
     * 新增公司信息
     * 
     * @param settingCompany 公司信息
     * @return 结果
     */
    public int insertSettingCompany(SettingCompany settingCompany);

    /**
     * 修改公司信息
     * 
     * @param settingCompany 公司信息
     * @return 结果
     */
    public int updateSettingCompany(SettingCompany settingCompany);

    /**
     * 删除公司信息
     * 
     * @param companyId 公司信息ID
     * @return 结果
     */
    public int deleteSettingCompanyById(Long companyId);

    /**
     * 批量删除公司信息
     * 
     * @param companyIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSettingCompanyByIds(String[] companyIds);

    /**
     * 获取公司详情
     * @return
     */
    CompanyResult selectCompanyDetail();

    /**
     * 获取版本号
     * @return
     */
    String selectVersion();
}
