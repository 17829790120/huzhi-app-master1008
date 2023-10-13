package com.wlwq.api.mapper;


import com.wlwq.api.domain.ImageDemo;

import java.util.List;

/**
 * 图片视频上传示例Mapper接口
 *
 * @author Renbowen
 * @date 2020-10-09
 */
public interface ImageDemoMapper {
    /**
     * 查询图片视频上传示例
     *
     * @param id 图片视频上传示例ID
     * @return 图片视频上传示例
     */
    public ImageDemo selectImageDemoById(Long id);

    /**
     * 查询图片视频上传示例列表
     *
     * @param imageDemo 图片视频上传示例
     * @return 图片视频上传示例集合
     */
    public List<ImageDemo> selectImageDemoList(ImageDemo imageDemo);

    /**
     * 新增图片视频上传示例
     *
     * @param imageDemo 图片视频上传示例
     * @return 结果
     */
    public int insertImageDemo(ImageDemo imageDemo);

    /**
     * 修改图片视频上传示例
     *
     * @param imageDemo 图片视频上传示例
     * @return 结果
     */
    public int updateImageDemo(ImageDemo imageDemo);

    /**
     * 删除图片视频上传示例
     *
     * @param id 图片视频上传示例ID
     * @return 结果
     */
    public int deleteImageDemoById(Long id);

    /**
     * 批量删除图片视频上传示例
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteImageDemoByIds(String[] ids);
}
