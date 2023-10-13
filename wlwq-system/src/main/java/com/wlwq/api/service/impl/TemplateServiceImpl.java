package com.wlwq.api.service.impl;

import java.util.List;
import cn.hutool.core.util.IdUtil;
import com.wlwq.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.api.mapper.TemplateMapper;
import com.wlwq.api.domain.Template;
import com.wlwq.api.service.ITemplateService;
import com.wlwq.common.core.text.Convert;

/**
 * 自定义模版Service业务层处理
 * 
 * @author gaoce
 * @date 2023-06-05
 */
@Service
public class TemplateServiceImpl implements ITemplateService {

    @Autowired
    private TemplateMapper templateMapper;

    /**
     * 查询自定义模版
     * 
     * @param templateId 自定义模版ID
     * @return 自定义模版
     */
    @Override
    public Template selectTemplateById(String templateId) {
        return templateMapper.selectTemplateById(templateId);
    }

    /**
     * 查询自定义模版列表
     * 
     * @param template 自定义模版
     * @return 自定义模版
     */
    @Override
    public List<Template> selectTemplateList(Template template) {
        return templateMapper.selectTemplateList(template);
    }

    /**
     * 新增自定义模版
     * 
     * @param template 自定义模版
     * @return 结果
     */
    @Override
    public int insertTemplate(Template template) {
        template.setTemplateId(IdUtil.getSnowflake(1,1).nextIdStr());
        template.setCreateTime(DateUtils.getNowDate());
        return templateMapper.insertTemplate(template);
    }

    /**
     * 修改自定义模版
     * 
     * @param template 自定义模版
     * @return 结果
     */
    @Override
    public int updateTemplate(Template template) {
        template.setUpdateTime(DateUtils.getNowDate());
        return templateMapper.updateTemplate(template);
    }

    /**
     * 删除自定义模版对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTemplateByIds(String ids) {
        return templateMapper.deleteTemplateByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除自定义模版信息
     * 
     * @param templateId 自定义模版ID
     * @return 结果
     */
    @Override
    public int deleteTemplateById(String templateId) {
        return templateMapper.deleteTemplateById(templateId);
    }

    /**
     * 只查询最新的一条数据
     * @param template
     * @return
     */
    @Override
    public Template selectTemplate(Template template){
        return templateMapper.selectTemplate(template);
    }
}
