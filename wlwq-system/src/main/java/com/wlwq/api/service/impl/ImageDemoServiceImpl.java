package com.wlwq.api.service.impl;

import com.wlwq.api.domain.ImageDemo;
import com.wlwq.api.mapper.ImageDemoMapper;
import com.wlwq.api.service.IImageDemoService;
import com.wlwq.common.core.text.Convert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 图片视频上传示例Service业务层处理
 *
 * @author Renbowen
 * @date 2020-10-09
 */
@Service
public class ImageDemoServiceImpl implements IImageDemoService {
    @Autowired
    private ImageDemoMapper imageDemoMapper;

    /**
     * 查询图片视频上传示例
     *
     * @param id 图片视频上传示例ID
     * @return 图片视频上传示例
     */
    @Override
    public ImageDemo selectImageDemoById(Long id) {
        return imageDemoMapper.selectImageDemoById(id);
    }

    /**
     * 查询图片视频上传示例列表
     *
     * @param imageDemo 图片视频上传示例
     * @return 图片视频上传示例
     */
    @Override
    public List<ImageDemo> selectImageDemoList(ImageDemo imageDemo) {
        return imageDemoMapper.selectImageDemoList(imageDemo);
    }

    /**
     * 新增图片视频上传示例
     *
     * @param imageDemo 图片视频上传示例
     * @return 结果
     */
    @Override
    public int insertImageDemo(ImageDemo imageDemo) {
        return imageDemoMapper.insertImageDemo(imageDemo);
    }

    /**
     * 修改图片视频上传示例
     *
     * @param imageDemo 图片视频上传示例
     * @return 结果
     */
    @Override
    public int updateImageDemo(ImageDemo imageDemo) {
        return imageDemoMapper.updateImageDemo(imageDemo);
    }

    /**
     * 删除图片视频上传示例对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteImageDemoByIds(String ids) {
        return imageDemoMapper.deleteImageDemoByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除图片视频上传示例信息
     *
     * @param id 图片视频上传示例ID
     * @return 结果
     */
    @Override
    public int deleteImageDemoById(Long id) {
        return imageDemoMapper.deleteImageDemoById(id);
    }
}
