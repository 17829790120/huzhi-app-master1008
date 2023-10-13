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

 Date: 07/06/2021 18:19:45
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for setting_agreement
-- ----------------------------
DROP TABLE IF EXISTS `setting_agreement`;
CREATE TABLE `setting_agreement`
(
    `agreement_id`      varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '协议ID',
    `agreement_title`   varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '协议标题',
    `agreement_content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci     NOT NULL COMMENT '协议内容',
    `del_status`        tinyint(5)                                                    NOT NULL DEFAULT 0 COMMENT '删除状态（0正常 1已删除）',
    `create_time`       datetime(0)                                                   NOT NULL COMMENT '创建时间',
    `update_time`       datetime(0)                                                            DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`agreement_id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '协议表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for setting_company
-- ----------------------------
DROP TABLE IF EXISTS `setting_company`;
CREATE TABLE `setting_company`
(
    `company_id`      bigint(20)                                                   NOT NULL AUTO_INCREMENT COMMENT '公司ID',
    `company_name`    varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '公司名字',
    `company_logo`    longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci    NOT NULL COMMENT '公司LOGO',
    `company_phone`   varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '公司电话',
    `company_content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci    NOT NULL COMMENT '公司简介',
    `version_number`  varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '当前版本号',
    `create_time`     datetime(0)                                                  NOT NULL COMMENT '创建时间',
    `update_time`     datetime(0) DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`company_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '公司信息表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for setting_feedback
-- ----------------------------
DROP TABLE IF EXISTS `setting_feedback`;
CREATE TABLE `setting_feedback`
(
    `feedback_id`      bigint(20)                                                   NOT NULL AUTO_INCREMENT COMMENT '反馈ID',
    `account_id`       varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户ID',
    `account_head`     longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci    NOT NULL COMMENT '用户头像',
    `account_name`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名字',
    `account_phone`    varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户手机号',
    `feedback_type`    longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci    NOT NULL COMMENT '反馈类型',
    `feedback_content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci    NOT NULL COMMENT '反馈内容',
    `feedback_images`  longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '反馈图片',
    `read_status`      tinyint(2)                                                   NOT NULL DEFAULT 0 COMMENT '是否已读（0未读 1已读）',
    `create_time`      datetime(0)                                                  NOT NULL COMMENT '创建时间',
    `update_time`      datetime(0)                                                           DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`feedback_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '意见反馈表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for setting_feedback_type
-- ----------------------------
DROP TABLE IF EXISTS `setting_feedback_type`;
CREATE TABLE `setting_feedback_type`
(
    `feedback_type_id` bigint(20)                                                   NOT NULL AUTO_INCREMENT COMMENT '反馈类型ID',
    `title`            varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '标题',
    `sort_num`         int(10)                                                      NOT NULL COMMENT '排序',
    `del_status`       tinyint(2)                                                   NOT NULL DEFAULT 0 COMMENT '删除状态（0正常 1已删除）',
    `create_time`      datetime(0)                                                  NOT NULL COMMENT '创建时间',
    `update_time`      datetime(0)                                                           DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`feedback_type_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 9
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '意见反馈类型表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for setting_problem
-- ----------------------------
DROP TABLE IF EXISTS `setting_problem`;
CREATE TABLE `setting_problem`
(
    `problem_id`     bigint(20)                                                   NOT NULL AUTO_INCREMENT COMMENT '常见问题ID',
    `problem_title`  varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '标题',
    `problem_answer` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci    NOT NULL COMMENT '内容',
    `sort_num`       int(10)                                                      NOT NULL COMMENT '排序',
    `show_status`    tinyint(2)                                                   NOT NULL COMMENT '展示状态（0隐藏 1展示）',
    `del_status`     tinyint(2)                                                   NOT NULL DEFAULT 0 COMMENT '删除状态（0正常 1删除）',
    `create_time`    datetime(0)                                                  NOT NULL COMMENT '创建时间',
    `update_time`    datetime(0)                                                           DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`problem_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '常见问题表'
  ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
