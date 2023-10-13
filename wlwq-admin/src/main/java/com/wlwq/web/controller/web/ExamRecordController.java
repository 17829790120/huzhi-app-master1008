package com.wlwq.web.controller.web;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import cn.hutool.core.util.IdUtil;
import com.wlwq.api.domain.*;
import com.wlwq.api.service.*;
import com.wlwq.bestPay.constant.ModuleConstant;
import com.wlwq.bestPay.mq.RabbitMQSendService;
import com.wlwq.common.core.text.Convert;
import com.wlwq.common.utils.DateUtils;
import com.wlwq.common.utils.StringUtils;
import com.wlwq.system.service.ISysConfigService;
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
 * 考试记录列表Controller
 *
 * @author wwb
 * @date 2023-04-23
 */
@Controller
@RequestMapping("/web/examRecord")
public class ExamRecordController extends BaseController {

    private String prefix = "web/examRecord";

    @Autowired
    private IExamRecordService examRecordService;

    @Autowired
    private IExamQuestionRecordService examQuestionRecordService;

    @Autowired
    private ISysConfigService configService;

    @Autowired
    private RabbitMQSendService rabbitMQSendService;

    @Autowired
    private IExamPaperRecordService examPaperRecordService;

    @Autowired
    private IApiAccountService accountService;

    @Autowired
    private ICourseChapterService courseChapterService;

    @Autowired
    private IAccountScoreService accountScoreService;

    @Autowired
    private IMessageRemindService messageRemindService;

    @RequiresPermissions("web:examRecord:view")
    @GetMapping()
    public String examRecord() {
        return prefix + "/examRecord";
    }

    /**
     * 查询考试记录列表列表
     */
    @RequiresPermissions("web:examRecord:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(ExamRecord examRecord) {
        startPage();
        List<ExamRecord> list = examRecordService.selectExamRecordList(examRecord);
        return getDataTable(list);
    }

    /**
     * 导出考试记录列表列表
     */
    @RequiresPermissions("web:examRecord:export")
    @Log(title = "考试记录列表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ExamRecord examRecord) {
        List<ExamRecord> list = examRecordService.selectExamRecordList(examRecord);
        ExcelUtil<ExamRecord> util = new ExcelUtil<ExamRecord>(ExamRecord.class);
        return util.exportExcel(list, "examRecord");
    }

    /**
     * 新增考试记录列表
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存考试记录列表
     */
    @RequiresPermissions("web:examRecord:add")
    @Log(title = "考试记录列表", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(ExamRecord examRecord) {
        return toAjax(examRecordService.insertExamRecord(examRecord));
    }

    /**
     * 修改考试记录列表
     */
    @GetMapping("/edit/{examRecordId}")
    public String edit(@PathVariable("examRecordId") String examRecordId, ModelMap mmap) {
        ExamRecord examRecord = examRecordService.selectExamRecordById(examRecordId);
        mmap.put("examRecord", examRecord);
        return prefix + "/edit";
    }

    /**
     * 修改保存考试记录列表
     */
    @RequiresPermissions("web:examRecord:edit")
    @Log(title = "考试记录列表", businessType = BusinessType.UPDATE)
    @PostMapping("/editRecord")
    @ResponseBody
    public AjaxResult editRecord(ExamRecord examRecord) {
        ExamRecord examRecordOld = examRecordService.selectExamRecordById(examRecord.getExamRecordId());
        if (examRecordOld == null) {
            return AjaxResult.error("没有此考试信息。");
        }
        if (examRecordOld.getScoreStatus() == 1) {
            return AjaxResult.error("已经评过分，不能重复评分。");
        }
        Double score = examQuestionRecordService.getScore(examRecord.getExamRecordId());
        examRecord.setScore(score);
        examRecord.setScoreStatus(1);

        int num = examRecordService.updateExamRecord(examRecord);
        if(num <= 0){
            return AjaxResult.error("考试评分失败。");
        }

        ApiAccount account = accountService.selectApiAccountById(examRecordOld.getAccountId());
        if (account != null) {
            examRecordOld.setScore(score);
            examRecordOld.setScoreStatus(1);
            setIntegral(examRecordOld,account);
        }
        return AjaxResult.success("考试评分成功。");
    }

    /**
     * 获得积分，徽章逻辑处理
     */
    private AjaxResult setIntegral(ExamRecord examRecord, ApiAccount account) {
        //更新考试得分信息,如果全是非客观题，直接给总分。
        Double score = examQuestionRecordService.getScore(examRecord.getExamRecordId());
        Integer integral = 0;
        String message = "";
        Double passingScore = 0.0;
        //考试及格后获得积分
        // 异步操作，送积分
        //试题类型（0：章节课程；1：测试训练）
        if(score == null){
            return AjaxResult.error("得分为0，不进行操作。");
        }
        if (examRecord.getQuestionType() == 0) {
            CourseChapter courseChapter = courseChapterService.selectCourseChapterById(examRecord.getChapterId());
            if (courseChapter != null) {
                //判断分数所对应的级别（及格，优秀，满分等）
                if (new BigDecimal(courseChapter.getFullScore()).compareTo(new BigDecimal(score)) == 0 && courseChapter.getFullScore() > 0.0) {
                    //满分获得的积分
                    integral = courseChapter.getFullIntegralScore();
                    message = "章节考试满分获得积分";
                } else if (new BigDecimal(courseChapter.getExcellentScore()).compareTo(new BigDecimal(score)) < 0 && courseChapter.getExcellentScore() > 0.0) {
                    //优秀分数获得的积分
                    integral = courseChapter.getExcellentIntegralScore();
                    message = "章节考试优秀获得积分";
                } else if (new BigDecimal(courseChapter.getGoodScore()).compareTo(new BigDecimal(score)) < 0 && courseChapter.getGoodScore() > 0.0) {
                    //良好分数获得的积分
                    integral = courseChapter.getGoodIntegralScore();
                    message = "章节考试良好获得积分";
                } else if (new BigDecimal(courseChapter.getPassingScore()).compareTo(new BigDecimal(score)) < 0 && courseChapter.getPassingScore() > 0.0) {
                    //及格分数获得的积分
                    integral = courseChapter.getPassingIntegralScore();
                    message = "章节考试及格获得积分";
                }

                passingScore = courseChapter.getPassingScore();
            }
        } else if (examRecord.getQuestionType() == 1) {
            //测试训练考试得积分逻辑
            //测试训练考试记录
            ExamPaperRecord examPaperRecord = examPaperRecordService.selectExamPaperRecordById(examRecord.getExamPaperRecordId());
            if (examPaperRecord != null) {
                //判断分数所对应的级别（及格，优秀，满分等）
                if (new BigDecimal(examPaperRecord.getFullScore()).compareTo(new BigDecimal(score)) == 0 && examPaperRecord.getFullScore() > 0.0) {
                    //满分获得的积分
                    integral = examPaperRecord.getFullIntegralScore();
                    message = "测试训练考试满分获得积分";
                } else if (new BigDecimal(examPaperRecord.getExcellentScore()).compareTo(new BigDecimal(score)) < 0 && examPaperRecord.getExcellentScore() > 0.0) {
                    //优秀分数获得的积分
                    integral = examPaperRecord.getExcellentIntegralScore();
                    message = "测试训练考试优秀获得积分";
                } else if (new BigDecimal(examPaperRecord.getGoodScore()).compareTo(new BigDecimal(score)) < 0 && examPaperRecord.getGoodScore() > 0.0) {
                    //良好分数获得的积分
                    integral = examPaperRecord.getGoodIntegralScore();
                    message = "测试训练考试良好获得积分";
                } else if (new BigDecimal(examPaperRecord.getPassingScore()).compareTo(new BigDecimal(score)) < 0 && examPaperRecord.getPassingScore() > 0.0) {
                    //及格分数获得的积分
                    integral = examPaperRecord.getPassingIntegralScore();
                    message = "测试训练考试及格获得积分";
                }
                passingScore = examPaperRecord.getPassingScore();
            }
        }

        if (examRecord.getScore() != null && new BigDecimal(examRecord.getScore()).compareTo(new BigDecimal(passingScore)) > 0) {
            if (examRecord.getQuestionType() == 0) {
                // 将心得分享发送到延时队列，7天后还未分享心得则标注为未分享心得，心得分享失效
                String automaticReceiptTime = Optional.ofNullable(configService.selectConfigByKey("experience_sharing_automatic_expiration_time")).orElse("7");
                Long expiration = new BigDecimal(automaticReceiptTime).multiply(BigDecimal.valueOf(24L)).multiply(BigDecimal.valueOf(60L)).multiply(BigDecimal.valueOf(60L)).multiply(BigDecimal.valueOf(1000L)).longValue();
                com.alibaba.fastjson.JSONObject jsonObject = new com.alibaba.fastjson.JSONObject();
                jsonObject.put("moduleName", ModuleConstant.EXPERIENCE_SHARING_TAKE_DELIVERY_TIMEOUT);
                jsonObject.put("messageContent", examRecord.getExamRecordId());
                rabbitMQSendService.sendDelayMessage(expiration, jsonObject.toString());
            }
            if(integral == 0){
                return AjaxResult.success("积分为0，不进行操作。");
            }
            // 更新用户信息
            accountService.updateApiAccount(ApiAccount.builder()
                    .accountId(account.getAccountId())
                    .surplusScore(account.getSurplusScore() + integral)
                    .totalScore(account.getTotalScore() + integral)
                    .build());
            // 查看是否满足勋章条件并更新勋章
            //accountMedalRecordService.updateMedalRecord(accountService.selectApiAccountById(account.getAccountId()), integral, message);
            // 用户积分存入记录
            // 赠送用户积分
            accountScoreService.insertAccountScore(AccountScore.builder()
                    .accountId(account.getAccountId())
                    .targetId(examRecord.getExamRecordId())
                    .scoreType(-2)
                    .accountName(account.getNickName())
                    .accountPhone(account.getPhone())
                    .accountHead(account.getHeadPortrait())
                    .scoreSource(message)
                    .scoreStatus(1)
                    .score(integral)
                    .courseId(examRecord.getCourseId())
                    .build());
            // 发送系统消息
            // 查询消息是否存在
            messageRemindService.insertMessageRemind(MessageRemind.builder()
                    .title("积分变动")
                    .brief(message + ",获得" + integral + "积分,点击查看")
                    .modelStatus(2)
                    .jumpType(-2)
                    .modelId("")
                    .accountId(account.getAccountId())
                    .build());
        }
        return AjaxResult.error("得分操作成功。");
    }


    /**
     * 修改保存考试记录列表
     */
    @RequiresPermissions("web:examRecord:edit")
    @Log(title = "考试记录列表", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ExamRecord examRecord) {
        return toAjax(examRecordService.updateExamRecord(examRecord));
    }

    /**
     * 删除考试记录列表
     */
    @RequiresPermissions("web:examRecord:remove")
    @Log(title = "考试记录列表", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(examRecordService.deleteExamRecordByIds(ids));
    }

    /**
     * 查询考试记录列表
     * chapterId 章节ID
     * courseId  课程ID
     */
    @GetMapping("/examRecordList")
    public String examRecordList(ModelMap modelMap, Long chapterId, Long courseId) {
        modelMap.put("chapterId", chapterId);
        modelMap.put("courseId", courseId);
        return prefix + "/examRecordNew";
    }

    /**
     * 查询测试训练考试记录列表
     * examPaperRecordId 考试试卷记录表id
     */
    @GetMapping("/examRecordTestTrainingList")
    public String examRecordTestTrainingList(ModelMap modelMap, String examPaperRecordId) {
        modelMap.put("examPaperRecordId", examPaperRecordId);
        return prefix + "/examRecordTestTraining";
    }

    /**
     * 新增保存考试记录列表
     */
    //@RequiresPermissions("web:examRecord:add")
    @Log(title = "考试记录列表", businessType = BusinessType.INSERT)
    @PostMapping("/addNew")
    @ResponseBody
    public AjaxResult addNew(ExamRecord examRecord) {
        ExamPaperRecord examPaperRecord = examPaperRecordService.selectExamPaperRecordById(examRecord.getExamPaperRecordId());
        if (examPaperRecord == null) {
            return AjaxResult.error("没有此试卷信息。");
        }
        if (DateUtils.compareDays(examPaperRecord.getEndTime(), new Date()) > 0) {
            return AjaxResult.error("考试时间已结束，不能发放试卷。");
        }
        ApiAccount account = accountService.selectApiAccountById(examRecord.getAccountId());
        if (account == null) {
            return AjaxResult.error("没有此用户。");
        }
        List<ExamRecord> list = examRecordService.selectExamRecordList(ExamRecord
                .builder()
                .accountId(account.getAccountId())
                .examPaperRecordId(examRecord.getExamPaperRecordId())
                .build());

        if (list != null && list.size() > 0) {
            return AjaxResult.error("用户已发放此试卷。");
        }
        examRecord.setExamRecordId(IdUtil.getSnowflake(1, 1).nextIdStr());
        examRecord.setHeadPortrait(account.getHeadPortrait());
        examRecord.setNickName(account.getNickName());
        examRecord.setExamPaperTitle(examPaperRecord.getExamPaperTitle());
        examRecord.setQuestionType(1);
        examRecord.setDeptId(account.getDeptId());
        examRecord.setBeginTime(examPaperRecord.getBeginTime());
        examRecord.setEndTime(examPaperRecord.getEndTime());
        return toAjax(examRecordService.insertExamRecord(examRecord));
    }

    /**
     * 新增保存考试记录列表
     */
    //@RequiresPermissions("web:examRecord:add")
    @Log(title = "考试记录列表", businessType = BusinessType.INSERT)
    @PostMapping("/addAllNew")
    @ResponseBody
    public AjaxResult addAllNew(ExamRecord examRecord) {
        ExamPaperRecord examPaperRecord = examPaperRecordService.selectExamPaperRecordById(examRecord.getExamPaperRecordId());
        if (examPaperRecord == null) {
            return AjaxResult.error("没有此试卷信息。");
        }
        if (DateUtils.compareDays(examPaperRecord.getEndTime(), new Date()) > 0) {
            return AjaxResult.error("考试时间已结束，不能发放试卷。");
        }
        String ids = examRecord.getIds();
        if (StringUtils.isEmpty(ids)) {
            return AjaxResult.error("请选择需要给发放试卷的人。");
        }
        String[] idsList = Convert.toStrArray(ids);
        for (int i = 0; i < idsList.length; i++) {
            ApiAccount account = accountService.selectApiAccountById(idsList[i]);
            if (account != null) {
                List<ExamRecord> list = examRecordService.selectExamRecordList(ExamRecord
                        .builder()
                        .accountId(account.getAccountId())
                        .examPaperRecordId(examRecord.getExamPaperRecordId())
                        .build());

                if (list != null && list.size() > 0) {
                    continue;
                }
                examRecord.setExamRecordId(IdUtil.getSnowflake(1, 1).nextIdStr());
                examRecord.setAccountId(account.getAccountId());
                examRecord.setHeadPortrait(account.getHeadPortrait());
                examRecord.setNickName(account.getNickName());
                examRecord.setExamPaperTitle(examPaperRecord.getExamPaperTitle());
                examRecord.setQuestionType(1);
                examRecord.setDeptId(account.getDeptId());
                examRecord.setBeginTime(examPaperRecord.getBeginTime());
                examRecord.setEndTime(examPaperRecord.getEndTime());
                examRecordService.insertExamRecord(examRecord);
            }
        }
        return AjaxResult.success("试卷发放成功。");
    }


}
