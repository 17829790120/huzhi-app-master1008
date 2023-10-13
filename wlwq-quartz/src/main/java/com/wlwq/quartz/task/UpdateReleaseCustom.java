package com.wlwq.quartz.task;

import cn.hutool.core.util.ObjectUtil;
import com.wlwq.api.mapper.CustomCustomInfoMapper;
import com.wlwq.api.mapper.CustomLevelDaysMapper;
import com.wlwq.api.mapper.CustomUserClaimMapper;
import com.wlwq.api.resultVO.CustomThrowVO;
import com.wlwq.api.service.ICustomCustomInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 定时任务 释放未跟进客户至公海
 *
 * @author wlwq
 */
@Component("releaseCustom")
public class UpdateReleaseCustom {
    @Autowired
    private ICustomCustomInfoService iCustomCustomInfoService;
    @Autowired
    private CustomCustomInfoMapper customCustomInfoMapper;
    @Autowired
    private CustomUserClaimMapper customUserClaimMapper;
    @Autowired
    private CustomLevelDaysMapper customLevelDaysMapper;

    /**
     * 每天00:00点
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateReleaseCustom() {
        //查询下一个轮回需要延长的 type=1
        List<CustomThrowVO> customThrowVO = iCustomCustomInfoService.findCustomThrowVO(1);
        if (ObjectUtil.isNotEmpty(customThrowVO) && customThrowVO.size() > 0) {
            //延长
            List<String> customClaimIds = customThrowVO.stream().map(CustomThrowVO::getCustomClaimId).collect(Collectors.toList());
            customLevelDaysMapper.updateCustomFollowTime(customClaimIds);
        }
        //查询已过期的type=2
        List<CustomThrowVO> customThrowVO2 = iCustomCustomInfoService.findCustomThrowVO(2);
        if (ObjectUtil.isNotEmpty(customThrowVO2) && customThrowVO2.size() > 0) {
            List<String> customIds = customThrowVO2.stream().map(CustomThrowVO::getCustomId).collect(Collectors.toList());
            List<String> customClaimIds = customThrowVO2.stream().map(CustomThrowVO::getCustomClaimId).collect(Collectors.toList());
            customCustomInfoMapper.updateClaimStatuses(0L, customIds);
            customUserClaimMapper.updateCustomUsers(1L, customClaimIds);
        }
    }
}
