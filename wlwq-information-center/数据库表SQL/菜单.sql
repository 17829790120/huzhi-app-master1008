INSERT INTO `sys_menu`(`menu_id`, `menu_name`, `parent_id`, `order_num`, `url`, `target`, `menu_type`, `visible`, `is_refresh`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (10000, '资讯管理', 0, 0, '#', 'menuItem', 'M', '0', '1', NULL, 'fa fa-bars', 'admin', '2021-04-19 16:04:17', '', NULL, '');

-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('资讯设置', '10000', '1', '/information/informationSetting', 'C', '0', 'information:informationSetting:view', '#', 'admin', sysdate(), '', null, '资讯设置菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('资讯设置查询', @parentId, '1',  '#',  'F', '0', 'information:informationSetting:list',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('资讯设置修改', @parentId, '3',  '#',  'F', '0', 'information:informationSetting:edit',         '#', 'admin', sysdate(), '', null, '');

-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('资讯分类', '10000', '1', '/information/informationCategory', 'C', '0', 'information:informationCategory:view', '#', 'admin', sysdate(), '', null, '资讯分类菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('资讯分类查询', @parentId, '1',  '#',  'F', '0', 'information:informationCategory:list',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('资讯分类新增', @parentId, '2',  '#',  'F', '0', 'information:informationCategory:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('资讯分类修改', @parentId, '3',  '#',  'F', '0', 'information:informationCategory:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('资讯分类删除', @parentId, '4',  '#',  'F', '0', 'information:informationCategory:remove',       '#', 'admin', sysdate(), '', null, '');


-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('资讯文章', '10000', '1', '/information/informationPost', 'C', '0', 'information:informationPost:view', '#', 'admin', sysdate(), '', null, '资讯文章菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('资讯文章查询', @parentId, '1',  '#',  'F', '0', 'information:informationPost:list',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('资讯文章新增', @parentId, '2',  '#',  'F', '0', 'information:informationPost:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('资讯文章修改', @parentId, '3',  '#',  'F', '0', 'information:informationPost:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('资讯文章删除', @parentId, '4',  '#',  'F', '0', 'information:informationPost:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('资讯文章导出', @parentId, '5',  '#',  'F', '0', 'information:informationPost:export',       '#', 'admin', sysdate(), '', null, '');


-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('浏览记录', '10000', '1', '/information/informationBrowseRecord', 'C', '0', 'information:informationBrowseRecord:view', '#', 'admin', sysdate(), '', null, '资讯浏览记录菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('资讯浏览记录查询', @parentId, '1',  '#',  'F', '0', 'information:informationBrowseRecord:list',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('资讯浏览记录新增', @parentId, '2',  '#',  'F', '0', 'information:informationBrowseRecord:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('资讯浏览记录修改', @parentId, '3',  '#',  'F', '0', 'information:informationBrowseRecord:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('资讯浏览记录删除', @parentId, '4',  '#',  'F', '0', 'information:informationBrowseRecord:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('资讯浏览记录导出', @parentId, '5',  '#',  'F', '0', 'information:informationBrowseRecord:export',       '#', 'admin', sysdate(), '', null, '');


-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('点赞记录', '10000', '1', '/information/informationLikeRecord', 'C', '0', 'information:informationLikeRecord:view', '#', 'admin', sysdate(), '', null, '点赞记录菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('点赞记录查询', @parentId, '1',  '#',  'F', '0', 'information:informationLikeRecord:list',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('点赞记录新增', @parentId, '2',  '#',  'F', '0', 'information:informationLikeRecord:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('点赞记录修改', @parentId, '3',  '#',  'F', '0', 'information:informationLikeRecord:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('点赞记录删除', @parentId, '4',  '#',  'F', '0', 'information:informationLikeRecord:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('点赞记录导出', @parentId, '5',  '#',  'F', '0', 'information:informationLikeRecord:export',       '#', 'admin', sysdate(), '', null, '');


-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('评论记录', '10000', '1', '/information/informationCommentRecord', 'C', '0', 'information:informationCommentRecord:view', '#', 'admin', sysdate(), '', null, '资讯评论记录菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('资讯评论记录查询', @parentId, '1',  '#',  'F', '0', 'information:informationCommentRecord:list',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('资讯评论记录新增', @parentId, '2',  '#',  'F', '0', 'information:informationCommentRecord:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('资讯评论记录修改', @parentId, '3',  '#',  'F', '0', 'information:informationCommentRecord:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('资讯评论记录删除', @parentId, '4',  '#',  'F', '0', 'information:informationCommentRecord:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('资讯评论记录导出', @parentId, '5',  '#',  'F', '0', 'information:informationCommentRecord:export',       '#', 'admin', sysdate(), '', null, '');
