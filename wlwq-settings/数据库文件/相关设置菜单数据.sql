INSERT INTO `sys_menu`(`menu_id`, `menu_name`, `parent_id`, `order_num`, `url`, `target`, `menu_type`, `visible`, `is_refresh`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (20000, '设置管理', 0, 2, '#', 'menuItem', 'M', '0', '1', NULL, 'fa fa-gear', 'admin', '2021-02-20 16:15:15', '', NULL, '');

-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('协议', '20000', '1', '/setting/settingAgreement', 'C', '0', 'setting:settingAgreement:view', '#', 'admin', sysdate(), '', null, '协议菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('协议查询', @parentId, '1',  '#',  'F', '0', 'setting:settingAgreement:list',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('协议新增', @parentId, '2',  '#',  'F', '0', 'setting:settingAgreement:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('协议修改', @parentId, '3',  '#',  'F', '0', 'setting:settingAgreement:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('协议删除', @parentId, '4',  '#',  'F', '0', 'setting:settingAgreement:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('协议导出', @parentId, '5',  '#',  'F', '0', 'setting:settingAgreement:export',       '#', 'admin', sysdate(), '', null, '');


-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('常见问题', '20000', '1', '/setting/problem', 'C', '0', 'setting:problem:view', '#', 'admin', sysdate(), '', null, '常见问题菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('常见问题查询', @parentId, '1',  '#',  'F', '0', 'setting:problem:list',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('常见问题新增', @parentId, '2',  '#',  'F', '0', 'setting:problem:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('常见问题修改', @parentId, '3',  '#',  'F', '0', 'setting:problem:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('常见问题删除', @parentId, '4',  '#',  'F', '0', 'setting:problem:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('常见问题导出', @parentId, '5',  '#',  'F', '0', 'setting:problem:export',       '#', 'admin', sysdate(), '', null, '');


-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('意见反馈类型', '20000', '1', '/setting/settingFeedbackType', 'C', '0', 'setting:settingFeedbackType:view', '#', 'admin', sysdate(), '', null, '意见反馈类型菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('意见反馈类型查询', @parentId, '1',  '#',  'F', '0', 'setting:settingFeedbackType:list',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('意见反馈类型新增', @parentId, '2',  '#',  'F', '0', 'setting:settingFeedbackType:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('意见反馈类型修改', @parentId, '3',  '#',  'F', '0', 'setting:settingFeedbackType:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('意见反馈类型删除', @parentId, '4',  '#',  'F', '0', 'setting:settingFeedbackType:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('意见反馈类型导出', @parentId, '5',  '#',  'F', '0', 'setting:settingFeedbackType:export',       '#', 'admin', sysdate(), '', null, '');


-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('意见反馈', '20000', '1', '/setting/feedback', 'C', '0', 'setting:feedback:view', '#', 'admin', sysdate(), '', null, '意见反馈菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('意见反馈查询', @parentId, '1',  '#',  'F', '0', 'setting:feedback:list',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('意见反馈修改', @parentId, '3',  '#',  'F', '0', 'setting:feedback:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('意见反馈删除', @parentId, '4',  '#',  'F', '0', 'setting:feedback:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('意见反馈导出', @parentId, '5',  '#',  'F', '0', 'setting:feedback:export',       '#', 'admin', sysdate(), '', null, '');


-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('公司信息', '20000', '1', '/setting/settingCompany', 'C', '0', 'setting:settingCompany:view', '#', 'admin', sysdate(), '', null, '公司信息菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('公司信息查询', @parentId, '1',  '#',  'F', '0', 'setting:settingCompany:list',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('公司信息修改', @parentId, '3',  '#',  'F', '0', 'setting:settingCompany:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('公司信息删除', @parentId, '4',  '#',  'F', '0', 'setting:settingCompany:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('公司信息导出', @parentId, '5',  '#',  'F', '0', 'setting:settingCompany:export',       '#', 'admin', sysdate(), '', null, '');

