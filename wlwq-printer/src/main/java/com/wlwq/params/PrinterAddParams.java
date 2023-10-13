package com.wlwq.params;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName PrinterAddParams
 * @Description 添加打印机参数
 * @Date 2021/1/16 15:01
 * @Author Rick wlwq
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PrinterAddParams implements Serializable {

    /** 打印机编号. */
    private String printerSn;

    /** 给打印机起名字（可传商铺名字）. */
    private String printerName;
}
