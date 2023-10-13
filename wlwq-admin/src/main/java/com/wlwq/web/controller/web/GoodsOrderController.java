package com.wlwq.web.controller.web;

import java.util.Date;
import java.util.List;

import com.wlwq.common.core.domain.entity.SysDept;
import com.wlwq.common.exception.ApiException;
import com.wlwq.common.utils.ShiroUtils;
import com.wlwq.system.service.ISysDeptService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.wlwq.common.annotation.Log;
import com.wlwq.common.enums.BusinessType;
import com.wlwq.api.domain.GoodsOrder;
import com.wlwq.api.service.IGoodsOrderService;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.utils.poi.ExcelUtil;
import com.wlwq.common.core.page.TableDataInfo;

/**
 * 订单信息Controller
 *
 * @author wwb
 * @date 2023-05-25
 */
@Controller
@RequestMapping("/web/goodsOrder")
public class GoodsOrderController extends BaseController {

    private String prefix = "web/goodsOrder";

    @Autowired
    private IGoodsOrderService goodsOrderService;

    @Autowired
    private IGoodsOrderService orderService;

    @Autowired
    private ISysDeptService deptService;

    @RequiresPermissions("web:goodsOrder:view")
    @GetMapping()
    public String goodsOrder() {
        return prefix + "/goodsOrder";
    }

    /**
     * 查询订单信息列表
     */
    @RequiresPermissions("web:goodsOrder:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(GoodsOrder goodsOrder) {
        Long deptId = ShiroUtils.getSysUser().getDeptId();
        if(deptId != null){
            SysDept dept = deptService.selectDeptByDeptId(deptId);
            if(dept != null){
                goodsOrder.setCompanyId(dept.getDeptId());
            }
        }
        startPage();
        List<GoodsOrder> list = goodsOrderService.selectGoodsOrderList(goodsOrder);
        return getDataTable(list);
    }

    /**
     * 导出订单信息列表
     */
    @RequiresPermissions("web:goodsOrder:export")
    @Log(title = "订单信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(GoodsOrder goodsOrder) {
        List<GoodsOrder> list = goodsOrderService.selectGoodsOrderList(goodsOrder);
        ExcelUtil<GoodsOrder> util = new ExcelUtil<GoodsOrder>(GoodsOrder.class);
        return util.exportExcel(list, "goodsOrder");
    }

    /**
     * 新增订单信息
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存订单信息
     */
    @RequiresPermissions("web:goodsOrder:add")
    @Log(title = "订单信息", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(GoodsOrder goodsOrder) {
        return toAjax(goodsOrderService.insertGoodsOrder(goodsOrder));
    }

    /**
     * 修改订单信息
     */
    @GetMapping("/edit/{orderId}")
    public String edit(@PathVariable("orderId") String orderId, ModelMap mmap) {
        GoodsOrder goodsOrder = goodsOrderService.selectGoodsOrderById(orderId);
        mmap.put("goodsOrder", goodsOrder);
        return prefix + "/edit";
    }

    /**
     * 修改保存订单信息
     */
    @RequiresPermissions("web:goodsOrder:edit")
    @Log(title = "订单信息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(GoodsOrder goodsOrder) {
        return toAjax(goodsOrderService.updateGoodsOrder(goodsOrder));
    }

    /**
     * 删除订单信息
     */
    @RequiresPermissions("web:goodsOrder:remove")
    @Log(title = "订单信息", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(goodsOrderService.deleteGoodsOrderByIds(ids));
    }

    /**
     * 确认支付
     */
    @Transactional(rollbackFor = Exception.class)
    @Log(title = "订单信息", businessType = BusinessType.UPDATE)
    @PostMapping("/toPay")
    @ResponseBody
    public AjaxResult toPay(String orderId) {
        GoodsOrder order = orderService.selectGoodsOrderById(orderId);
        if(order == null){
            return error("该订单不存在！");
        }
        if(!"0".equals(order.getOrderStatus())){
            return error("只有待付款的订单才能点击支付！");
        }
        int count = orderService.updateGoodsOrder(GoodsOrder.builder()
                .orderId(orderId)
                .payStatus(1)
                .orderStatus("3")
                .payType("platform")
                .payTime(new Date())
                .build());
        if(count <= 0){
            throw new ApiException("修改失败！");
        }
        return toAjax(1);
    }
}
