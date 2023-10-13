-- 数据字典
INSERT INTO `wlwq-product-data`.`sys_dict_type`(`dict_id`, `dict_name`, `dict_type`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (12, '支付平台', 'pay_platform', '0', 'admin', '2021-07-02 17:30:39', '', NULL, NULL);
INSERT INTO `wlwq-product-data`.`sys_dict_type`(`dict_id`, `dict_name`, `dict_type`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (13, '支付方式', 'pay_type', '0', 'admin', '2021-07-02 17:31:52', '', NULL, NULL);
INSERT INTO `wlwq-product-data`.`sys_dict_type`(`dict_id`, `dict_name`, `dict_type`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (14, '启用开关', 'start_status', '0', 'admin', '2021-07-02 17:57:36', '', NULL, NULL);

INSERT INTO `wlwq-product-data`.`sys_dict_data`(`dict_code`, `dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (105, 1, '支付宝', 'alipay', 'pay_platform', NULL, 'success', 'Y', '0', 'admin', '2021-07-02 17:31:15', '', NULL, NULL);
INSERT INTO `wlwq-product-data`.`sys_dict_data`(`dict_code`, `dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (106, 2, '微信', 'wx', 'pay_platform', NULL, 'primary', 'Y', '0', 'admin', '2021-07-02 17:31:38', '', NULL, NULL);
INSERT INTO `wlwq-product-data`.`sys_dict_data`(`dict_code`, `dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (107, 1, '支付宝APP支付', 'alipay_app', 'pay_type', '', 'primary', 'Y', '0', 'admin', '2021-07-02 17:32:27', 'admin', '2021-07-02 17:33:06', '');
INSERT INTO `wlwq-product-data`.`sys_dict_data`(`dict_code`, `dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (108, 2, '支付宝pc支付', 'alipay_pc', 'pay_type', NULL, 'success', 'Y', '0', 'admin', '2021-07-02 17:33:01', '', NULL, NULL);
INSERT INTO `wlwq-product-data`.`sys_dict_data`(`dict_code`, `dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (109, 3, '支付宝wap支付', 'alipay_wap', 'pay_type', NULL, 'info', 'Y', '0', 'admin', '2021-07-02 17:33:28', '', NULL, NULL);
INSERT INTO `wlwq-product-data`.`sys_dict_data`(`dict_code`, `dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (110, 4, '微信公众号小程序支付', 'JSAPI', 'pay_type', NULL, 'warning', 'Y', '0', 'admin', '2021-07-02 17:33:59', '', NULL, NULL);
INSERT INTO `wlwq-product-data`.`sys_dict_data`(`dict_code`, `dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (111, 5, '微信H5支付', 'MWEB', 'pay_type', NULL, 'success', 'Y', '0', 'admin', '2021-07-02 17:34:23', '', NULL, NULL);
INSERT INTO `wlwq-product-data`.`sys_dict_data`(`dict_code`, `dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (112, 6, '微信Native支付', 'NATIVE', 'pay_type', NULL, 'danger', 'Y', '0', 'admin', '2021-07-02 17:34:44', '', NULL, NULL);
INSERT INTO `wlwq-product-data`.`sys_dict_data`(`dict_code`, `dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (113, 7, '微信APP支付', 'APP', 'pay_type', NULL, 'primary', 'Y', '0', 'admin', '2021-07-02 17:35:10', '', NULL, NULL);
INSERT INTO `wlwq-product-data`.`sys_dict_data`(`dict_code`, `dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (114, 1, '启用', '1', 'start_status', NULL, 'primary', 'Y', '0', 'admin', '2021-07-02 17:57:49', '', NULL, NULL);
INSERT INTO `wlwq-product-data`.`sys_dict_data`(`dict_code`, `dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (115, 2, '停用', '0', 'start_status', NULL, 'danger', 'Y', '0', 'admin', '2021-07-02 17:58:10', '', NULL, NULL);

-- 菜单

-- 主菜单
INSERT INTO `wlwq-product-data`.`sys_menu`(`menu_id`, `menu_name`, `parent_id`, `order_num`, `url`, `target`, `menu_type`, `visible`, `is_refresh`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (20000, '开发配置', 0, 0, '#', 'menuItem', 'M', '0', '1', NULL, 'fa fa-coffee', 'admin', '2021-07-02 17:25:29', '', NULL, '');

-- 开发配置表
-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('支付配置2.0版', '20000', '1', '/pay/payConfig', 'C', '0', 'pay:payConfig:view', '#', 'admin', sysdate(), '', null, '支付配置2.0版菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('支付配置2.0版查询', @parentId, '1',  '#',  'F', '0', 'pay:payConfig:list',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('支付配置2.0版新增', @parentId, '2',  '#',  'F', '0', 'pay:payConfig:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('支付配置2.0版修改', @parentId, '3',  '#',  'F', '0', 'pay:payConfig:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('支付配置2.0版删除', @parentId, '4',  '#',  'F', '0', 'pay:payConfig:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('支付配置2.0版导出', @parentId, '5',  '#',  'F', '0', 'pay:payConfig:export',       '#', 'admin', sysdate(), '', null, '');
