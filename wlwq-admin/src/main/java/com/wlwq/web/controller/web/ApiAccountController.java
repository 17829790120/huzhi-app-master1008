package com.wlwq.web.controller.web;

import java.util.List;

import cn.hutool.crypto.SmUtil;
import com.wlwq.api.domain.GroupInfor;
import com.wlwq.api.resultVO.AppPowerVO;
import com.wlwq.api.service.IGroupInforService;
import com.wlwq.common.core.domain.entity.SysDept;
import com.wlwq.common.core.domain.entity.SysDictData;
import com.wlwq.common.utils.ShiroUtils;
import com.wlwq.common.utils.StringUtils;
import com.wlwq.service.TokenService;
import com.wlwq.system.domain.SysPost;
import com.wlwq.system.service.ISysDeptService;
import com.wlwq.system.service.ISysDictDataService;
import com.wlwq.system.service.ISysPostService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.wlwq.common.annotation.Log;
import com.wlwq.common.enums.BusinessType;
import com.wlwq.api.domain.ApiAccount;
import com.wlwq.api.service.IApiAccountService;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.utils.poi.ExcelUtil;
import com.wlwq.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 用户列表Controller
 *
 * @author gaoce
 * @date 2023-02-22
 */
@Controller
@RequestMapping("/web/account")
public class ApiAccountController extends BaseController {

    private String prefix = "web/account";

    @Autowired
    private IApiAccountService apiAccountService;

    @Autowired
    private ISysDeptService deptService;

    @Autowired
    private ISysPostService postService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ISysDictDataService iSysDictDataService;

    @Autowired
    private IGroupInforService groupInforService;

    @RequiresPermissions("web:account:view")
    @GetMapping()
    public String account(ModelMap mmap) {
        // 查询公司信息
        SysDept dept = new SysDept();
        dept.setDeptLevel(-1);
        dept.setTag(1);
        List<SysDept> companyList = deptService.selectDeptList(dept);
        mmap.put("companyList", companyList);
        return prefix + "/account";
    }

    /**
     * 查询用户列表列表
     */
    @RequiresPermissions("web:account:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(ApiAccount apiAccount) {
        startPage();
        List<ApiAccount> list = apiAccountService.selectApiAccountList(apiAccount);
        return getDataTable(list);
    }

    /**
     * 查询用户列表列表
     */
    @RequiresPermissions("web:account:list")
    @PostMapping("/listByDept")
    @ResponseBody
    public TableDataInfo listByDept(ApiAccount apiAccount) {
        Long deptId = ShiroUtils.getSysUser().getDeptId();
        if (deptId != null) {
            SysDept dept = deptService.selectDeptByDeptId(deptId);
            if (dept != null) {
                apiAccount.setCompanyId(dept.getDeptId());
            }
        }
        startPage();
        List<ApiAccount> list = apiAccountService.selectListByDept(apiAccount);
        return getDataTable(list);
    }

    /**
     * 导出用户列表列表
     */
    @RequiresPermissions("web:account:export")
    @Log(title = "用户列表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ApiAccount apiAccount) {
        Long deptId = ShiroUtils.getSysUser().getDeptId();
        if (deptId != null) {
            SysDept dept = deptService.selectDeptByDeptId(deptId);
            if (dept != null) {
                apiAccount.setCompanyId(dept.getDeptId());
            }
        }
        List<ApiAccount> list = apiAccountService.selectApiAccountList(apiAccount);
        ExcelUtil<ApiAccount> util = new ExcelUtil<ApiAccount>(ApiAccount.class);
        return util.exportExcel(list, "account");
    }

    @Log(title = "用户管理", businessType = BusinessType.IMPORT)
    @RequiresPermissions("web:account:import")
    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception {
        ExcelUtil<ApiAccount> util = new ExcelUtil<ApiAccount>(ApiAccount.class);
        List<ApiAccount> accountList = util.importExcel(file.getInputStream());
        String operName = ShiroUtils.getSysUser().getLoginName();
        String message = apiAccountService.importUser(accountList, updateSupport, operName);
        return AjaxResult.success(message);
    }

    @RequiresPermissions("web:account:view")
    @GetMapping("/importTemplate")
    @ResponseBody
    public AjaxResult importTemplate() {
        ExcelUtil<ApiAccount> util = new ExcelUtil<ApiAccount>(ApiAccount.class);
        return util.importTemplateExcel("用户数据");
    }

    /**
     * 新增用户列表
     */
    @GetMapping("/add")
    public String add(ModelMap mmap) {
        // 查询公司信息
        SysDept dept = new SysDept();
        dept.setDeptLevel(-1);
        dept.setTag(1);
        List<SysDept> companyList = deptService.selectDeptList(dept);
        mmap.put("companyList", companyList);


        SysDictData sysDictData = new SysDictData();
        sysDictData.setDictType("app_power");
        List<SysDictData> sysDictData1 = iSysDictDataService.selectDictDataList(sysDictData);
        mmap.put("sysDictDataList", sysDictData1);

        SysDictData appDeptType = new SysDictData();
        appDeptType.setDictType("app_dept_type");
        List<SysDictData> appDeptType1 = iSysDictDataService.selectDictDataList(appDeptType);
        mmap.put("positionTypeList", appDeptType1);
        return prefix + "/add";
    }

    /**
     * 新增保存用户列表
     */
    @RequiresPermissions("web:account:add")
    @Log(title = "用户列表", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(ApiAccount apiAccount) {
        // 查询该账号是否存在
        ApiAccount account = apiAccountService.selectApiAccountByPhone(apiAccount.getPhone());
        if(account != null){
            return error("该账号已存在！");
        }
        // 查询领导岗位是否已用户是否已存在
        if(StringUtils.isNotBlank(apiAccount.getPostIds())){
            SysPost post = new SysPost();
            post.setPostIds(apiAccount.getPostIds());
            post.setStatus("0");
            post.setAccountId("0");
            List<SysPost> postList = postService.selectWebPostList(post);
            if(StringUtils.isNull(postList)){
                return error("该岗位不存在！");
            }
//            if(apiAccount.getPostIds().length() != postList.size()){
//                return error("您添加的岗位中有的岗位已占用，不能添加！");
//            }
        }
        return toAjax(apiAccountService.insertApiAccount(apiAccount));
    }

    /**
     * 修改用户列表
     */
    @GetMapping("/edit/{accountId}")
    public String edit(@PathVariable("accountId") String accountId, ModelMap mmap) {
        ApiAccount apiAccount = apiAccountService.selectApiAccountById(accountId);
        mmap.put("apiAccount", apiAccount);

        SysDept dept = new SysDept();
        dept.setDeptLevel(-1);
        dept.setTag(1);
        List<SysDept> companyList = deptService.selectDeptList(dept);
        mmap.put("companyList", companyList);


        SysPost sysPost = new SysPost();
        List<SysPost> sysPostList = postService.selectPostList(sysPost);
        mmap.put("sysPostList", sysPostList);

        SysDictData sysDictData = new SysDictData();
        sysDictData.setDictType("app_power");
        List<SysDictData> sysDictData1 = iSysDictDataService.selectDictDataList(sysDictData);
        mmap.put("sysDictDataList", sysDictData1);

        SysDictData appDeptType = new SysDictData();
        appDeptType.setDictType("app_dept_type");
        List<SysDictData> appDeptType1 = iSysDictDataService.selectDictDataList(appDeptType);
        mmap.put("positionTypeList", appDeptType1);
        return prefix + "/edit";
    }

    /**
     * 修改保存用户列表
     */
    @RequiresPermissions("web:account:edit")
    @Log(title = "用户列表", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ApiAccount apiAccount) {
        ApiAccount account = apiAccountService.selectApiAccountById(apiAccount.getAccountId());
        if (account == null) {
            AjaxResult.error("系统没有此用户。");
        }
        //用户被注销后，清除用户token信息
        if (apiAccount.getForbiddenStatus() == 1) {
            tokenService.delToken(account.getUuid());
        }
        // 查询领导岗位是否已用户是否已存在
        if(StringUtils.isNotBlank(apiAccount.getPostIds())){
            SysPost post = new SysPost();
            post.setPostIds(apiAccount.getPostIds());
            post.setStatus("0");
            post.setAccountId(apiAccount.getAccountId());
            List<SysPost> postList = postService.selectWebOtherPostList(post);
            if(StringUtils.isNull(postList)){
                return error("该岗位不存在！");
            }
//            if(apiAccount.getPostIds().split(",").length != postList.size()){
//                return error("您添加的岗位中有的岗位已占用，不能添加！");
//            }
        }
        return toAjax(apiAccountService.updateApiAccount(apiAccount));
    }

    /**
     * 删除用户列表
     */
    @RequiresPermissions("web:account:remove")
    @Log(title = "用户列表", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(apiAccountService.deleteApiAccountByIds(ids));
    }


    /**
     * 根据公司ID查询小组信息
     *
     * @return
     */
    @PostMapping("/selectGroupListById")
    @ResponseBody
    public List<GroupInfor> selectGroupListById(Long companyId) {
        // 查询小组
        List<GroupInfor> inforList = groupInforService.selectGroupInforList(GroupInfor
                .builder()
                .companyId(companyId)
                .delStatus(0)
                .build());
        return inforList;
    }
}
