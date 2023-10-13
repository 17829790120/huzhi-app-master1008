package com.wlwq.controller.api;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wlwq.annotation.PassToken;
import com.wlwq.api.domain.GoodsOrder;
import com.wlwq.api.domain.ResourceFilePost;
import com.wlwq.api.service.IGoodsOrderService;
import com.wlwq.api.service.IResourceFilePostService;
import com.wlwq.common.apiResult.ApiController;
import com.wlwq.common.apiResult.ApiResult;
import com.wlwq.common.apiResult.PageParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * @author wwb
 * 资源文件
 */
@RestController
@RequestMapping(value = "/api/resourceFilePost")
@AllArgsConstructor
public class ResourceFilePostApiController extends ApiController {

    private final IResourceFilePostService resourceFilePostService;

    private final IGoodsOrderService orderService;

    /**
     * 资源文件列表
     * @param resourceFileCategoryId  资源文件类别ID
     * @param companyId  公司ID
     * @param keyword                    关键字搜索
     * @return
     */
    @PassToken
    @GetMapping(value = "/resourceFilePostList")
    public ApiResult resourceFilePostList(PageParam pageParam,String keyword,@RequestParam(defaultValue = "") Long resourceFileCategoryId,
            @RequestParam(defaultValue = "") Long companyId) {
        if (pageParam == null) {
            pageParam = PageParam.builder().build();
        }
        PageHelper.startPage(pageParam.getPageNo(), pageParam.getPageSize());
        List<ResourceFilePost> list = resourceFilePostService.selectResourceFilePostList(ResourceFilePost
                .builder()
                .resourceFileCategoryId(resourceFileCategoryId)
                .tag(1)
                .companyId(companyId)
                .delStatus(0)
                .informationPostTitle(keyword)
                .build());
        PageInfo<ResourceFilePost> pageInfo = new PageInfo<>(list);
        list.forEach(
                obj->{
                    List<GoodsOrder> orderList = orderService.selectGoodsOrderList(GoodsOrder
                            .builder()
                            .goodsId(obj.getResourceFilePostId())
                            .orderStatus("3")
                            .build());
                   if(orderList == null){
                       obj.setRealDownloadNumber(0);
                   }else{
                       //查询此资源下载记录数据
                       obj.setRealDownloadNumber(orderList.size());
                   }
                }
        );
        return ok(pageInfo);
    }

    /**
     * 资源文件详情
     *
     * @param resourceFilePostId  资讯文件ID
     * @return
     */
    @PassToken
    @GetMapping(value = "/details")
    public ApiResult details(@RequestParam(defaultValue = "-1") String resourceFilePostId) {
        ResourceFilePost resourceFilePost = resourceFilePostService.selectResourceFilePostById(resourceFilePostId);
        if(resourceFilePost != null){
            List<GoodsOrder> orderList = orderService.selectGoodsOrderList(GoodsOrder
                    .builder()
                    .goodsId(resourceFilePost.getResourceFilePostId())
                    .orderStatus("3")
                    .build());
            if(orderList == null){
                resourceFilePost.setRealDownloadNumber(0);
            }else{
                //查询此资源下载记录数据
                resourceFilePost.setRealDownloadNumber(orderList.size());
            }
        }
        return ok(resourceFilePost);
    }


    /**
     * 置顶资源文件列表
     * @return
     */
    @PassToken
    @GetMapping(value = "/topStatusPostList")
    public ApiResult topStatusPostList(PageParam pageParam) {
        if (pageParam == null) {
            pageParam = PageParam.builder().build();
        }
        PageHelper.startPage(pageParam.getPageNo(), pageParam.getPageSize());
        List<ResourceFilePost> list = resourceFilePostService.selectResourceFilePostList(ResourceFilePost
                .builder()
                .topStatus(1)
                .tag(1)
                //.companyId(companyId)
                .delStatus(0)
                .build());
        PageInfo<ResourceFilePost> pageInfo = new PageInfo<>(list);
        list.forEach(
                obj->{
                    List<GoodsOrder> orderList = orderService.selectGoodsOrderList(GoodsOrder
                            .builder()
                            .goodsId(obj.getResourceFilePostId())
                            .orderStatus("3")
                            .build());
                    if(orderList == null){
                        obj.setRealDownloadNumber(0);
                    }else{
                        //查询此资源下载记录数据
                        obj.setRealDownloadNumber(orderList.size());
                    }
                }
        );
        return ok(pageInfo);
    }
}
