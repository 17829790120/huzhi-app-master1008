/*
 Navicat Premium Data Transfer

 Source Server         : 本地数据库
 Source Server Type    : MySQL
 Source Server Version : 50731
 Source Host           : 127.0.0.1:3306
 Source Schema         : ry

 Target Server Type    : MySQL
 Target Server Version : 50731
 File Encoding         : 65001

 Date: 06/04/2021 20:41:47
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for ax_service_log
-- ----------------------------
DROP TABLE IF EXISTS `ax_service_log`;
CREATE TABLE `ax_service_log`
(
    `ax_service_log_id` bigint(11)                                             NOT NULL AUTO_INCREMENT COMMENT 'AX绑定日志ID',
    `virtual_number`    varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '虚拟号码',
    `real_number`       varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '真实号码',
    `sub_id`            longtext CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '华为云绑定ID',
    `remarks`           varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci         DEFAULT NULL COMMENT '备注',
    `status`            bit(1)                                                 NOT NULL DEFAULT b'1' COMMENT '状态： 0失败  1成功',
    `create_time`       datetime(0)                                            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`ax_service_log_id`) USING BTREE
) ENGINE = MyISAM
  AUTO_INCREMENT = 9
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = 'X号码绑定日志'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for virtual_number
-- ----------------------------
DROP TABLE IF EXISTS `virtual_number`;
CREATE TABLE `virtual_number`
(
    `virtual_number_id` bigint(20)                                                   NOT NULL AUTO_INCREMENT COMMENT '虚拟号码ID',
    `virtual_number`    varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '虚拟号码',
    `real_number`       varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci          DEFAULT NULL COMMENT '真实号码',
    `sub_id`            longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '华为云绑定ID',
    `status`            tinyint(5)                                                   NOT NULL DEFAULT 0 COMMENT '状态（0空闲 1绑定）',
    `create_time`       datetime(0)                                                           DEFAULT NULL COMMENT '创建时间',
    `update_time`       datetime(0)                                                           DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`virtual_number_id`, `virtual_number`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '虚拟号码表'
  ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `ax_ticket_record`;
CREATE TABLE `ax_ticket_record`
(
    `ax_ticket_record_id`   bigint(20)                                                    NOT NULL AUTO_INCREMENT COMMENT 'AX话单记录ID',
    `direction`             tinyint(5)                                                    NOT NULL COMMENT '通话呼叫方向（0其他用户呼叫A 1A呼叫其他用户 2异常场景）',
    `sp_id`                 varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '客户的云服务账号',
    `app_key`               varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '隐私保护通话应用的app_key',
    `ic_id`                 varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '呼叫记录的唯一标识',
    `bind_num`              varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '隐私保护号码',
    `session_id`            varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '通话链路的唯一标识',
    `caller_num`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '主叫号码',
    `callee_num`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '被叫号码',
    `callIn_time`           varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '呼入的开始时间',
    `call_end_time`         varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '呼叫结束时间',
    `record_flag`           tinyint(5)                                                    DEFAULT NULL COMMENT '录音标识（0表示未录音 1表示有录音）',
    `record_start_time`     varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '录音开始时间',
    `record_object_name`    varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '录音文件名',
    `record_bucket_name`    varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '录音文件名所在的目录名',
    `record_domain`         varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '存放录音文件的域名',
    `record_file_url`       longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '录音文件地址',
    `record_file_url_local` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '录音文件存储在本地地址',
    `service_type`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '呼叫的业务类型（003：AX模式）',
    `host_name`             varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '话单生成的服务器设备对应的主机名',
    `subscription_id`       varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '绑定ID',
    `area_code`             varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '隐私保护号码（X号码）的城市码',
    `call_duration`         int(20)                                                       DEFAULT NULL COMMENT '呼叫的通话时长，单位为秒。',
    `create_time`           datetime(0)                                                   NOT NULL COMMENT '通知事件',
    PRIMARY KEY (`ax_ticket_record_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = 'AX话单记录表'
  ROW_FORMAT = Dynamic;


SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------
-- 菜单sql
-- ----------------------------
INSERT INTO `sys_menu`(`menu_id`, `menu_name`, `parent_id`, `order_num`, `url`, `target`, `menu_type`, `visible`,
                       `is_refresh`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES (2014, '华为云隐私通话设置', 0, 1, '#', 'menuItem', 'M', '0', '1', NULL, 'fa fa-gear', 'admin', '2021-04-01 15:40:53', '',
        NULL, '');
INSERT INTO `sys_menu`(`menu_id`, `menu_name`, `parent_id`, `order_num`, `url`, `target`, `menu_type`, `visible`,
                       `is_refresh`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES (2015, '虚拟号码', 2014, 1, '/privatePhone/virtualNumber', '', 'C', '0', '1', 'privatePhone:virtualNumber:view', '#',
        'admin', '2021-04-01 15:43:35', '', NULL, '虚拟号码菜单');
INSERT INTO `sys_menu`(`menu_id`, `menu_name`, `parent_id`, `order_num`, `url`, `target`, `menu_type`, `visible`,
                       `is_refresh`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES (2016, '虚拟号码查询', 2015, 1, '#', '', 'F', '0', '1', 'privatePhone:virtualNumber:list', '#', 'admin',
        '2021-04-01 15:43:35', '', NULL, '');
INSERT INTO `sys_menu`(`menu_id`, `menu_name`, `parent_id`, `order_num`, `url`, `target`, `menu_type`, `visible`,
                       `is_refresh`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES (2017, '虚拟号码新增', 2015, 2, '#', '', 'F', '0', '1', 'privatePhone:virtualNumber:add', '#', 'admin',
        '2021-04-01 15:43:35', '', NULL, '');
INSERT INTO `sys_menu`(`menu_id`, `menu_name`, `parent_id`, `order_num`, `url`, `target`, `menu_type`, `visible`,
                       `is_refresh`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES (2018, '虚拟号码修改', 2015, 3, '#', '', 'F', '0', '1', 'privatePhone:virtualNumber:edit', '#', 'admin',
        '2021-04-01 15:43:35', '', NULL, '');
INSERT INTO `sys_menu`(`menu_id`, `menu_name`, `parent_id`, `order_num`, `url`, `target`, `menu_type`, `visible`,
                       `is_refresh`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES (2019, '虚拟号码删除', 2015, 4, '#', '', 'F', '0', '1', 'privatePhone:virtualNumber:remove', '#', 'admin',
        '2021-04-01 15:43:35', '', NULL, '');
INSERT INTO `sys_menu`(`menu_id`, `menu_name`, `parent_id`, `order_num`, `url`, `target`, `menu_type`, `visible`,
                       `is_refresh`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES (2020, '虚拟号码导出', 2015, 5, '#', '', 'F', '0', '1', 'privatePhone:virtualNumber:export', '#', 'admin',
        '2021-04-01 15:43:35', '', NULL, '');
INSERT INTO `sys_menu`(`menu_id`, `menu_name`, `parent_id`, `order_num`, `url`, `target`, `menu_type`, `visible`,
                       `is_refresh`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES (2027, 'AX号码绑定日志', 2014, 1, '/privatePhone/axServiceLog', '', 'C', '0', '1', 'privatePhone:axServiceLog:view',
        '#', 'admin', '2021-04-02 10:45:32', '', NULL, 'AX号码绑定日志菜单');
INSERT INTO `sys_menu`(`menu_id`, `menu_name`, `parent_id`, `order_num`, `url`, `target`, `menu_type`, `visible`,
                       `is_refresh`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES (2028, 'AX号码绑定日志查询', 2027, 1, '#', '', 'F', '0', '1', 'privatePhone:axServiceLog:list', '#', 'admin',
        '2021-04-02 10:45:32', '', NULL, '');
INSERT INTO `sys_menu`(`menu_id`, `menu_name`, `parent_id`, `order_num`, `url`, `target`, `menu_type`, `visible`,
                       `is_refresh`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES (2029, 'AX号码绑定日志新增', 2027, 2, '#', '', 'F', '0', '1', 'privatePhone:axServiceLog:add', '#', 'admin',
        '2021-04-02 10:45:32', '', NULL, '');
INSERT INTO `sys_menu`(`menu_id`, `menu_name`, `parent_id`, `order_num`, `url`, `target`, `menu_type`, `visible`,
                       `is_refresh`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES (2030, 'AX号码绑定日志修改', 2027, 3, '#', '', 'F', '0', '1', 'privatePhone:axServiceLog:edit', '#', 'admin',
        '2021-04-02 10:45:32', '', NULL, '');
INSERT INTO `sys_menu`(`menu_id`, `menu_name`, `parent_id`, `order_num`, `url`, `target`, `menu_type`, `visible`,
                       `is_refresh`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES (2031, 'AX号码绑定日志删除', 2027, 4, '#', '', 'F', '0', '1', 'privatePhone:axServiceLog:remove', '#', 'admin',
        '2021-04-02 10:45:32', '', NULL, '');
INSERT INTO `sys_menu`(`menu_id`, `menu_name`, `parent_id`, `order_num`, `url`, `target`, `menu_type`, `visible`,
                       `is_refresh`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES (2032, 'AX号码绑定日志导出', 2027, 5, '#', '', 'F', '0', '1', 'privatePhone:axServiceLog:export', '#', 'admin',
        '2021-04-02 10:45:32', '', NULL, '');
-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time,
                      update_by, update_time, remark)
values ('AX话单记录', '2014', '1', '/privatePhone/axTicketRecord', 'C', '0', 'privatePhone:axTicketRecord:view', '#',
        'admin', sysdate(), '', null, 'AX话单记录菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time,
                      update_by, update_time, remark)
values ('AX话单记录查询', @parentId, '1', '#', 'F', '0', 'privatePhone:axTicketRecord:list', '#', 'admin', sysdate(), '',
        null, '');

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time,
                      update_by, update_time, remark)
values ('AX话单记录获取视频', @parentId, '1', '#', 'F', '0', 'privatePhone:axTicketRecord:voice', '#', 'admin', sysdate(), '',
        null, '');

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time,
                      update_by, update_time, remark)
values ('AX话单记录下载', @parentId, '1', '#', 'F', '0', 'privatePhone:axTicketRecord:down', '#', 'admin', sysdate(), '',
        null, '');


insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time,
                      update_by, update_time, remark)
values ('AX话单记录删除', @parentId, '4', '#', 'F', '0', 'privatePhone:axTicketRecord:remove', '#', 'admin', sysdate(), '',
        null, '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time,
                      update_by, update_time, remark)
values ('AX话单记录导出', @parentId, '5', '#', 'F', '0', 'privatePhone:axTicketRecord:export', '#', 'admin', sysdate(), '',
        null, '');


