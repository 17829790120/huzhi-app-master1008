package com.wlwq.web.controller.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.IdUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wlwq.api.domain.*;
import com.wlwq.api.paramsVO.TemplateFieldParamVO;
import com.wlwq.api.service.*;
import com.wlwq.common.core.domain.entity.SysDept;
import com.wlwq.common.utils.ShiroUtils;
import com.wlwq.common.utils.StringUtils;
import com.wlwq.system.domain.SysPost;
import com.wlwq.system.service.ISysDeptService;
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
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.utils.poi.ExcelUtil;
import com.wlwq.common.core.page.TableDataInfo;

/**
 * pk与对赌Controller
 *
 * @author wwb
 * @date 2023-06-07
 */
@Controller
@RequestMapping("/web/winningTrainingRecord")
public class WinningTrainingRecordController extends BaseController {

    private String prefix = "web/winningTrainingRecord";

    @Autowired
    private IWinningTrainingRecordService winningTrainingRecordService;

    @Autowired
    private ITemplateService templateService;

    @Autowired
    private ISysDeptService deptService;

    @Autowired
    private IApiAccountService apiAccountService;

    @Autowired
    private IGroupInforService groupInforService;

    @Autowired
    private ISysPostService postService;

    @Autowired
    private IAccountScoreService accountScoreService;

    @Autowired
    private IMessageRemindService messageRemindService;

    @Autowired
    private IApiAccountService accountService;

    /**
     * templateType
     * @param modelMap
     * @param templateType
     * @return
     */
    @RequiresPermissions("web:winningTrainingRecord:view")
    @GetMapping()
    public String winningTrainingRecord(ModelMap modelMap,Integer templateType,Integer pkBettingType) {
        modelMap.put("templateType",templateType);
        modelMap.put("pkBettingType",pkBettingType);
        return prefix + "/winningTrainingRecord";
    }

    /**
     * 查询用户
     * @return
     */
    @GetMapping("/allAccount")
    public String allAccount() {
        return prefix + "/winningAccountDept";
    }

    /**
     * 查询小组
     * @return
     */
    @GetMapping("/winningGroupInfor")
    public String winningGroupInfor() {
        return prefix + "/winningGroupInfor";
    }

    /**
     * 查询汇报训练列表
     */
    @RequiresPermissions("web:winningTrainingRecord:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(WinningTrainingRecord winningTrainingRecord) {
        Long deptId = ShiroUtils.getSysUser().getDeptId();
        if(deptId != null){
            SysDept dept = deptService.selectDeptByDeptId(deptId);
            if(dept != null){
                winningTrainingRecord.setCompanyId(dept.getDeptId());
            }
        }

        Template template = templateService.selectTemplate(Template.builder().templateType(winningTrainingRecord.getTemplateType()).build());
        List<TemplateFieldParamVO> paramVOList = new Gson().fromJson(template == null ? "" : template.getTemplateContent(), new TypeToken<List<TemplateFieldParamVO>>() {

        }.getType());
        startPage();
        List<Map<String,Object>> list = winningTrainingRecordService.selectWebWinningTrainingList(winningTrainingRecord);
        list.forEach(param-> {
            List<TemplateFieldParamVO> trainingList = new Gson().fromJson(Convert.toStr(param.get("content")), new TypeToken<List<TemplateFieldParamVO>>() {

            }.getType());
            trainingList.forEach(training-> {
                paramVOList.forEach(e-> {
                    if(training.getFieldEnglishName().equals(e.getFieldEnglishName())){
                        param.put(e.getFieldEnglishName(),training.getValue());
                    }
                });
            });
        });
        return getDataTable(list);
    }


    /**
     * 导出人与人pk列表
     */
    @RequiresPermissions("web:winningTrainingRecord:export")
    @Log(title = "人与人pk", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(WinningTrainingRecord winningTrainingRecord) {
        List<WinningTrainingRecord> list = winningTrainingRecordService.selectWinningTrainingRecordList(winningTrainingRecord);
        ExcelUtil<WinningTrainingRecord> util = new ExcelUtil<WinningTrainingRecord>(WinningTrainingRecord.class);
        return util.exportExcel(list, "winningTrainingRecord");
    }

    /**
     * 新增人与人pk
     */
    @GetMapping("/add")
    public String add(ModelMap mmap,Integer templateType) {
        Template template = templateService.selectTemplate(Template.builder().templateType(templateType).build());
        List<TemplateFieldParamVO> paramVOList = new Gson().fromJson(template.getTemplateContent(), new TypeToken<List<TemplateFieldParamVO>>() {
        }.getType());
        mmap.put("content",paramVOList);
        mmap.put("templateType",templateType);

        SysPost sysPost=new SysPost();
        Long deptId = ShiroUtils.getSysUser().getDeptId();
        if(deptId != null){
            SysDept dept = deptService.selectDeptByDeptId(deptId);
            if(dept != null){
                sysPost.setCompanyId(dept.getDeptId());
            }
        }
        List<SysPost> sysPostList = postService.selectPostList(sysPost);
        mmap.put("sysPostList",sysPostList);
        return prefix + "/add";
    }

    /**
     * 新增保存人与人pk
     */
    @RequiresPermissions("web:winningTrainingRecord:add")
    @Log(title = "人与人pk", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(WinningTrainingRecord winningTrainingRecord) {
        List<TemplateFieldParamVO> templateFieldList = winningTrainingRecord.getTemplateFieldList();
        if (templateFieldList == null || templateFieldList.size() == 0) {
            return error("请添加字段！");
        }
        // 对list进行正序排列
//        templateFieldList = templateFieldList.stream().sorted(Comparator.comparing(TemplateFieldParamVO::getSortNum))
//                .collect(Collectors.toList());
        winningTrainingRecord.setContent(new Gson().toJson(templateFieldList));
        winningTrainingRecord.setCreatorHeadPortrait(ShiroUtils.getSysUser().getAvatar());
        winningTrainingRecord.setCreatorNickName(ShiroUtils.getSysUser().getUserName());
        winningTrainingRecord.setCreatorId(ShiroUtils.getUserId());
        winningTrainingRecord.setWinningTrainingRecordId(IdUtil.getSnowflake(1, 1).nextIdStr());
        SysDept sysDept = deptService.selectDeptByDeptId(ShiroUtils.getSysUser().getDeptId());
        if(sysDept != null){
            winningTrainingRecord.setCompanyId(sysDept.getDeptId());
        }
        setPartyInforNew(winningTrainingRecord,"A",winningTrainingRecord.getPartyAAccountId());
        setPartyInforNew(winningTrainingRecord,"B",winningTrainingRecord.getPartyBAccountId());
        return toAjax(winningTrainingRecordService.insertWinningTrainingRecord(winningTrainingRecord));
    }

    /**
     * 修改人与人pk
     */
    @GetMapping("/edit/{winningTrainingRecordId}")
    public String edit(@PathVariable("winningTrainingRecordId") String winningTrainingRecordId, ModelMap mmap) {
        WinningTrainingRecord winningTrainingRecord = winningTrainingRecordService.selectWinningTrainingRecordById(winningTrainingRecordId);
        mmap.put("winningTrainingRecord", winningTrainingRecord);
        winningTrainingRecord.setTemplateFieldList(new Gson().fromJson(winningTrainingRecord.getContent(), new TypeToken<List<TemplateFieldParamVO>>() {
        }.getType()));

        SysPost sysPost=new SysPost();
        Long deptId = ShiroUtils.getSysUser().getDeptId();
        if(deptId != null){
            SysDept dept = deptService.selectDeptByDeptId(deptId);
            if(dept != null){
                sysPost.setCompanyId(dept.getDeptId());
            }
        }
        List<SysPost> sysPostList = postService.selectPostList(sysPost);
        mmap.put("sysPostList",sysPostList);
        return prefix + "/edit";
    }

    /**
     * 修改保存人与人pk
     */
    @RequiresPermissions("web:winningTrainingRecord:edit")
    @Log(title = "人与人pk", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(WinningTrainingRecord winningTrainingRecord) {
        WinningTrainingRecord winningTrainingRecordOld = winningTrainingRecordService.selectWinningTrainingRecordById(winningTrainingRecord.getWinningTrainingRecordId());
        if(winningTrainingRecordOld == null){
            return AjaxResult.error("没有此数据。");
        }
        if(winningTrainingRecordOld.getPkStatus() == 2){
            return AjaxResult.error("已结束的PK/对赌数据不可以修改。");
        }
        winningTrainingRecord.setCreatorHeadPortrait(ShiroUtils.getSysUser().getAvatar());
        winningTrainingRecord.setCreatorNickName(ShiroUtils.getSysUser().getUserName());
        winningTrainingRecord.setCreatorId(ShiroUtils.getUserId());
        SysDept sysDept = deptService.selectDeptByDeptId(ShiroUtils.getSysUser().getDeptId());
        if(sysDept != null){
            winningTrainingRecord.setCompanyId(sysDept.getDeptId());
        }
        List<TemplateFieldParamVO> templateFieldList = winningTrainingRecord.getTemplateFieldList();
        if (templateFieldList == null || templateFieldList.size() == 0) {
            return error("请添加字段！");
        }
        // 对list进行正序排列
//        templateFieldList = templateFieldList.stream().sorted(Comparator.comparing(TemplateFieldParamVO::getSortNum))
//                .collect(Collectors.toList());
        winningTrainingRecord.setContent(new Gson().toJson(templateFieldList));

        setPartyInforNew(winningTrainingRecord,"A",winningTrainingRecord.getPartyAAccountId());
        setPartyInforNew(winningTrainingRecord,"B",winningTrainingRecord.getPartyBAccountId());

        return toAjax(winningTrainingRecordService.updateWinningTrainingRecord(winningTrainingRecord));
    }

    private void setPartyInforNew(WinningTrainingRecord winningTrainingRecord,String type,String partyAccountId){
        ApiAccount account = apiAccountService.selectApiAccountById(partyAccountId);
        if(!StringUtils.isEmpty(partyAccountId)){
            if(account != null){
                //查询人
                SysDept dept = deptService.selectDeptById(account.getDeptId());
                if("A".equals(type)){
                    winningTrainingRecord.setPartyADeptName(dept != null ? dept.getDeptName() : "");
                }else if("B".equals(type)){
                    winningTrainingRecord.setPartyBDeptName(dept != null ? dept.getDeptName() : "");
                }
                setIntegral(winningTrainingRecord,account);
            }else{
                List<HashMap<String,Object>> accountList = null;
                //查询部门
                if(winningTrainingRecord.getPkBettingType() == 3){
                    //小组
                    GroupInfor groupInfor = groupInforService.selectGroupInforById(Long.valueOf(partyAccountId));
                    if(groupInfor != null){
                        //按照公司与小组查询人员信息
                        accountList = apiAccountService.selectAccountMapByGroup(groupInfor.getGroupInforId(),
                                groupInfor.getCompanyId());
                    }
                }else{
                    //部门，不查询公司
                    SysDept dept = deptService.selectDeptById(Long.valueOf(partyAccountId));
                    if(dept != null){
                        if(dept.getParentId() != 100 && dept.getParentId() != 0){
                            accountList = apiAccountService.selectAccountMap(Long.valueOf(partyAccountId),
                                    dept.getParentId(),null);
                        }
                    }
                }
                if(accountList != null && accountList.size() > 0){
                    String accountIds = StringUtils.join(accountList.stream()
                            .map(obj -> obj.get("accountId").toString()).collect(Collectors.toList()), ",");
                    String nickNames = StringUtils.join(accountList.stream()
                            .map(obj -> obj.get("nickName").toString()).collect(Collectors.toList()), ",");
                    if("A".equals(type)){
                        winningTrainingRecord.setPartyADeptAccountIds(accountIds);
                        winningTrainingRecord.setPartyADeptAccountNames(nickNames);
                        winningTrainingRecord.setPartyADeptAccountCount(accountList.size());
                    }else if("B".equals(type)){
                        winningTrainingRecord.setPartyBDeptAccountIds(accountIds);
                        winningTrainingRecord.setPartyBDeptAccountNames(nickNames);
                        winningTrainingRecord.setPartyBDeptAccountCount(accountList.size());
                    }
                    accountList.forEach(
                            obj -> {
                                ApiAccount accountNew = apiAccountService.selectApiAccountById(obj.get("accountId").toString());
                                if(accountNew != null){
                                    setIntegral(winningTrainingRecord,accountNew);
                                }
                            }
                    );
                }
            }
        }
    }

    /**
     * 获得积分，徽章逻辑处理
     */
    private AjaxResult setIntegral(WinningTrainingRecord winningTrainingRecord, ApiAccount account) {
        if(winningTrainingRecord.getRewardScore() == null || winningTrainingRecord.getRewardScore() == 0){
            return AjaxResult.error("得分为0，不进行操作。");
        }
        //已结束状态才可以赠送积分
        if(winningTrainingRecord.getPkStatus() == 2){
            Integer score = winningTrainingRecord.getRewardScore();
            String title = winningTrainingRecord.getTitle();
            // 更新用户信息
            accountService.updateApiAccount(ApiAccount.builder()
                    .accountId(account.getAccountId())
                    .surplusScore(account.getSurplusScore()+score)
                    .totalScore(account.getTotalScore() + score)
                    .build());
            // 查看是否满足勋章条件并更新勋章
            //accountMedalRecordService.updateMedalRecord(accountService.selectApiAccountById(account.getAccountId()), score, title);
            // 用户积分存入记录
            // 赠送用户积分
            accountScoreService.insertAccountScore(AccountScore.builder()
                    .accountId(account.getAccountId())
                    .targetId(winningTrainingRecord.getWinnerId())
                    .scoreType(-2)
                    .accountName(account.getNickName())
                    .accountPhone(account.getPhone())
                    .accountHead(account.getHeadPortrait())
                    .scoreSource(title)
                    .scoreStatus(1)
                    .score(score)
                    .build());
            // 发送系统消息
            // 查询消息是否存在
            messageRemindService.insertMessageRemind(MessageRemind.builder()
                    .title("积分变动")
                    .brief(title + ",获得" + score + "积分,点击查看")
                    .modelStatus(2)
                    .jumpType(-2)
                    .modelId("0")
                    .accountId(account.getAccountId())
                    .build());
        }
        return AjaxResult.success("赠送积分成功。");
    }

    /**
     * 删除人与人pk
     */
    @RequiresPermissions("web:winningTrainingRecord:remove")
    @Log(title = "人与人pk", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(winningTrainingRecordService.deleteWinningTrainingRecordByIds(ids));
    }

    /**
     * 动态获取列(周汇报)
     * templateType 1:日精进 2:周汇报 3:月总结 4：季度工作报告 5：半年工作报告 6：年度工作报告 7：述职报告 8：其他汇报
     */
    @PostMapping("/ajaxColumns")
    @ResponseBody
    public AjaxResult ajaxColumns(Integer templateType) {
        Template template = templateService.selectTemplate(Template.builder().templateType(templateType).build());
        if(template == null){
            return error("暂未查询到模板！");
        }
        List<TemplateFieldParamVO> paramVOList = new Gson().fromJson(template.getTemplateContent(), new TypeToken<List<TemplateFieldParamVO>>() {
        }.getType());
        return AjaxResult.success(paramVOList);
    }
}
