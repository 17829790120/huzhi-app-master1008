package com.wlwq.controller.api;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wlwq.annotation.PassToken;
import com.wlwq.api.domain.ApiAccount;
import com.wlwq.api.domain.HouseTrainingCategory;
import com.wlwq.api.domain.HouseTrainingPost;
import com.wlwq.api.service.IApiAccountService;
import com.wlwq.api.service.IHouseTrainingCategoryService;
import com.wlwq.api.service.IHouseTrainingPostService;
import com.wlwq.common.apiResult.ApiController;
import com.wlwq.common.apiResult.ApiResult;
import com.wlwq.common.apiResult.PageParam;
import com.wlwq.common.utils.StringUtils;
import com.wlwq.information.domain.InformationLikeRecord;
import com.wlwq.information.service.IInformationLikeRecordService;
import com.wlwq.service.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author wwb
 * 家庭训练
 */
@RestController
@RequestMapping(value = "/api/houseTrainingCategory")
@AllArgsConstructor
public class HouseTrainingCategoryApiController extends ApiController {

    private final IInformationLikeRecordService informationLikeRecordService;

    private final TokenService tokenService;

    private final IApiAccountService accountService;

    private final IHouseTrainingCategoryService houseTrainingCategoryService;

    private final IHouseTrainingPostService houseTrainingPostService;

    /**
     * 文化展示列表
     *
     * @param deptId  部门ID，默认总公司（100）
     * @param keyword 关键字搜索
     * @return
     */
    @PassToken
    @GetMapping(value = "/list")
    public ApiResult list(@RequestParam(defaultValue = "0") Long deptId, @RequestParam(defaultValue = "-1") Long parentId, String keyword) {
        //查询1及导航
        if (parentId == -1) {
            List<HouseTrainingCategory> list = houseTrainingCategoryService.selectHouseTrainingCategoryVoList(HouseTrainingCategory
                    .builder()
                    .deptId(deptId)
                    .parentId(0L)
                    .delStatus(0)
                    .title(keyword)
                    .build());
            if (list != null && list.size() > 0) {
                parentId = list.get(0).getHouseTrainingCategoryId();
            }
        }
        List<HouseTrainingCategory> newList = houseTrainingCategoryService.selectHouseTrainingCategoryVoList(HouseTrainingCategory
                .builder()
                .parentId(parentId)
                .delStatus(0)
                .build());
        return ok(newList);
    }

    /**
     * 家庭训练资讯列表
     *
     * @param deptId  部门ID，默认总公司（100）
     * @param keyword 关键字搜索
     * @return
     */
    @PassToken
    @GetMapping(value = "/houseTrainingPostList")
    public ApiResult houseTrainingPostList(PageParam pageParam, @RequestParam(defaultValue = "") Long deptId,
                                           @RequestParam(defaultValue = "") Long informationCategoryId, String keyword, HttpServletRequest request) {
        if (pageParam == null) {
            pageParam = PageParam.builder().build();
        }
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        PageHelper.startPage(pageParam.getPageNo(), pageParam.getPageSize());
        List<HouseTrainingPost> list = houseTrainingPostService.selectHouseTrainingPostList(HouseTrainingPost
                .builder()
                .deptId(deptId)
                .informationCategoryId(informationCategoryId)
                .informationPostTitle(keyword)
                .build());
        PageInfo<HouseTrainingPost> pageInfo = new PageInfo<>(list);
        list.forEach(
                obj -> {
                    if (account == null) {
                        obj.setIsLike(0);
                    } else {
                        //查询用户是否点赞过
                        InformationLikeRecord commonLike = informationLikeRecordService.selectInformationLikeByPrimaryIdAndAccountId(obj.getHouseTrainingPostId(), account.getAccountId());
                        if (commonLike != null) {
                            obj.setIsLike(1);
                        } else {
                            obj.setIsLike(0);
                        }
                    }
                }
        );
        return ok(pageInfo);
    }

    /**
     * 家庭训练资讯详情
     *
     * @return
     */
    @PassToken
    @GetMapping(value = "/selectHouseTrainingPostById")
    public ApiResult selectHouseTrainingPostById(HttpServletRequest request,@RequestParam("houseTrainingPostId") String houseTrainingPostId) {
        if(StringUtils.isEmpty(houseTrainingPostId)){
            return fail("需要查看的数据不能为空。");
        }
        ApiAccount account = accountService.selectApiAccountById(tokenService.getNoAccountId(request));
        HouseTrainingPost houseTrainingPost = houseTrainingPostService.selectHouseTrainingPostById(Long.parseLong(houseTrainingPostId));
        if (account == null) {
            houseTrainingPost.setIsLike(0);
        } else {
            //查询用户是否点赞过
            InformationLikeRecord commonLike = informationLikeRecordService.selectInformationLikeByPrimaryIdAndAccountId(houseTrainingPost.getHouseTrainingPostId(), tokenService.getNoAccountId(request));
            if (commonLike != null) {
                houseTrainingPost.setIsLike(1);
            } else {
                houseTrainingPost.setIsLike(0);
            }
        }
        return ok(houseTrainingPost);
    }
}
