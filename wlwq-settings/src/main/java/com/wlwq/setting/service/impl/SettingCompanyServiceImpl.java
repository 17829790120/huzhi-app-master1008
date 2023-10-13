package com.wlwq.setting.service.impl;

import com.wlwq.common.core.text.Convert;
import com.wlwq.common.utils.DateUtils;
import com.wlwq.setting.domain.SettingCompany;
import com.wlwq.setting.mapper.SettingCompanyMapper;
import com.wlwq.setting.result.CompanyResult;
import com.wlwq.setting.service.ISettingCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 公司信息Service业务层处理
 * 
 * @author Rick wlwq
 * @date 2021-06-07
 */
@Service
public class SettingCompanyServiceImpl implements ISettingCompanyService 
{
    @Autowired
    private SettingCompanyMapper settingCompanyMapper;

    /**
     * 查询公司信息
     * 
     * @param companyId 公司信息ID
     * @return 公司信息
     */
    @Override
    public SettingCompany selectSettingCompanyById(Long companyId)
    {
        return settingCompanyMapper.selectSettingCompanyById(companyId);
    }

    /**
     * 查询公司信息列表
     * 
     * @param settingCompany 公司信息
     * @return 公司信息
     */
    @Override
    public List<SettingCompany> selectSettingCompanyList(SettingCompany settingCompany)
    {
        return settingCompanyMapper.selectSettingCompanyList(settingCompany);
    }

    /**
     * 新增公司信息
     * 
     * @param settingCompany 公司信息
     * @return 结果
     */
    @Override
    public int insertSettingCompany(SettingCompany settingCompany)
    {
        settingCompany.setCreateTime(DateUtils.getNowDate());
        return settingCompanyMapper.insertSettingCompany(settingCompany);
    }

    /**
     * 修改公司信息
     * 
     * @param settingCompany 公司信息
     * @return 结果
     */
    @Override
    public int updateSettingCompany(SettingCompany settingCompany)
    {
        settingCompany.setUpdateTime(DateUtils.getNowDate());
        return settingCompanyMapper.updateSettingCompany(settingCompany);
    }

    /**
     * 删除公司信息对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSettingCompanyByIds(String ids)
    {
        return settingCompanyMapper.deleteSettingCompanyByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除公司信息信息
     * 
     * @param companyId 公司信息ID
     * @return 结果
     */
    @Override
    public int deleteSettingCompanyById(Long companyId)
    {
        return settingCompanyMapper.deleteSettingCompanyById(companyId);
    }

    /**
     * 获取公司详情
     * CompanyResult
     * @return
     */
    @Override
    public CompanyResult selectCompanyDetail() {
        return settingCompanyMapper.selectCompanyDetail();
    }

    /**
     * 获取版本号
     * @return
     */
    @Override
    public String selectVersion() {
        return settingCompanyMapper.selectVersion();
    }
}
