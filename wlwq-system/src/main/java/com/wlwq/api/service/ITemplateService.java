package com.wlwq.api.service;

import java.util.List;
import com.wlwq.api.domain.Template;

/**
 * 自定义模版Service接口
 * 
 * @author gaoce
 * @date 2023-06-05
 */
public interface ITemplateService {
    /**
     * 查询自定义模版
     * 
     * @param templateId 自定义模版ID
     * @return 自定义模版
     */
    public Template selectTemplateById(String templateId);

    /**
     * 查询自定义模版列表
     * 
     * @param template 自定义模版
     * @return 自定义模版集合
     */
    public List<Template> selectTemplateList(Template template);

    /**
     * 新增自定义模版
     * 
     * @param template 自定义模版
     * @return 结果
     */
    public int insertTemplate(Template template);

    /**
     * 修改自定义模版
     * 
     * @param template 自定义模版
     * @return 结果
     */
    public int updateTemplate(Template template);

    /**
     * 批量删除自定义模版
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTemplateByIds(String ids);

    /**
     * 删除自定义模版信息
     * 
     * @param templateId 自定义模版ID
     * @return 结果
     */
    public int deleteTemplateById(String templateId);

    /**
     * 只查询最新的一条数据
     * @param template
     * @return
     */
    public Template selectTemplate(Template template);
}
