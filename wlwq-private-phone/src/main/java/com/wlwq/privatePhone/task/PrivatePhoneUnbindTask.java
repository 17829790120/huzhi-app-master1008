package com.wlwq.privatePhone.task;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.wlwq.privatePhone.axService.AXService;
import com.wlwq.privatePhone.domain.VirtualNumber;
import com.wlwq.privatePhone.service.IVirtualNumberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @ClassName PrivatePhoneUnbindTask
 * @Description 隐私通话定时解绑
 * @Date 2021/4/12 11:19
 * @Author Rick wlwq
 */
@Component(value = "PrivatePhoneUnbindTask")
@Slf4j
public class PrivatePhoneUnbindTask {

    @Autowired
    private IVirtualNumberService numberService;
    @Autowired
    private AXService axService;

    /**
    *  @Description 通话超时十分钟解绑隐私号
    *  @author Rick wlwq
    *  @Date   2021/4/12 11:20
    */
    public void timeOutTenMinuteUnbind(){
        List<VirtualNumber> list = numberService.selectVirtualNumberList(VirtualNumber.builder()
                .status(1)
                .build());
        for (VirtualNumber number : list) {
            // 如果绑定时间超过10分钟  解绑
            if (DateUtil.between(number.getUpdateTime(),new Date(), DateUnit.MINUTE) >= 10){
                axService.axUnbindNumber(number.getVirtualNumber(),null);
            }
        }
    }

}
