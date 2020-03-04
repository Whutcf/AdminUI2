/*
 Navicat Premium Data Transfer

 Source Server         : windows-mysql
 Source Server Type    : MySQL
 Source Server Version : 80018
 Source Host           : localhost:3306
 Source Schema         : adminui

 Target Server Type    : MySQL
 Target Server Version : 80018
 File Encoding         : 65001

 Date: 16/01/2020 00:19:25
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '权限ID',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限名称',
  `permission` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限标识',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `role_id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名称',
  `create_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_person` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作人',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, 'admin', '2019-09-12 15:12:21', NULL, NULL);
INSERT INTO `sys_role` VALUES (2, 'manager', '2019-09-12 16:09:53', NULL, NULL);
INSERT INTO `sys_role` VALUES (3, 'employee', '2019-09-17 16:10:10', NULL, NULL);

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `role_id` bigint(11) NULL DEFAULT NULL COMMENT '角色ID',
  `menu_id` bigint(11) NULL DEFAULT NULL COMMENT '权限ID',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `role_id`(`role_id`) USING BTREE,
  INDEX `menu_id`(`menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色与权限关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `user_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `state` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'T' COMMENT '帐号状态',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_person` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作人',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `username`(`user_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (3, 'admin', 'admin', 'T', '2019-09-04 15:12:35', NULL, NULL);
INSERT INTO `sys_user` VALUES (4, 'zhangsan1', '11', 'T', '2019-09-26 15:12:40', '2020-01-15 23:54:49', 'admin');
INSERT INTO `sys_user` VALUES (5, 'lisi', 'lisi', 'F', '2019-09-05 15:12:42', '2020-01-12 22:07:17', 'admin');
INSERT INTO `sys_user` VALUES (6, 'wangwu', 'wangwu', 'T', '2019-09-04 15:12:45', '2019-09-21 15:09:55', 'admin');
INSERT INTO `sys_user` VALUES (7, 'zhaoliu', 'zhaoliu', 'T', '2019-09-03 15:12:47', '2019-09-21 15:43:56', '11');
INSERT INTO `sys_user` VALUES (8, 'user1', '1', 'T', '2019-09-21 15:12:51', '2020-01-15 23:55:43', '1');
INSERT INTO `sys_user` VALUES (10, 'user3', '2', 'F', '2019-09-04 16:09:20', '2019-09-21 15:20:48', '11');
INSERT INTO `sys_user` VALUES (11, '1', '1', 'T', '2019-08-29 16:09:28', '2020-01-12 20:15:23', 'admin');
INSERT INTO `sys_user` VALUES (12, '2', '2', 'T', '2019-08-28 16:09:32', NULL, NULL);
INSERT INTO `sys_user` VALUES (14, 'a', 'a', 'T', '2019-09-21 15:50:03', '2020-01-12 19:01:56', 'admin');
INSERT INTO `sys_user` VALUES (16, 'ad', 'ad', 'F', '2020-01-12 18:57:02', '2020-01-12 19:02:01', 'admin');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint(11) NULL DEFAULT NULL COMMENT '用户ID',
  `role_id` bigint(11) NULL DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `role_id`(`role_id`) USING BTREE,
  CONSTRAINT `sys_user_role_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `sys_user_role_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`role_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 89 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户与角色关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 3, 1);
INSERT INTO `sys_user_role` VALUES (2, 4, 2);
INSERT INTO `sys_user_role` VALUES (9, 8, 3);
INSERT INTO `sys_user_role` VALUES (11, 10, 3);
INSERT INTO `sys_user_role` VALUES (14, 12, 3);
INSERT INTO `sys_user_role` VALUES (21, 10, 2);
INSERT INTO `sys_user_role` VALUES (57, 5, 2);
INSERT INTO `sys_user_role` VALUES (58, 5, 3);
INSERT INTO `sys_user_role` VALUES (72, 7, 1);
INSERT INTO `sys_user_role` VALUES (73, 7, 2);
INSERT INTO `sys_user_role` VALUES (74, 7, 3);
INSERT INTO `sys_user_role` VALUES (76, 6, 2);
INSERT INTO `sys_user_role` VALUES (77, 6, 3);
INSERT INTO `sys_user_role` VALUES (78, 5, 1);
INSERT INTO `sys_user_role` VALUES (79, 6, 1);
INSERT INTO `sys_user_role` VALUES (90, 11, 2);
INSERT INTO `sys_user_role` VALUES (91, 11, 3);
INSERT INTO `sys_user_role` VALUES (99, 4, 3);
INSERT INTO `sys_user_role` VALUES (100, 8, 1);
INSERT INTO `sys_user_role` VALUES (101, 8, 2);

SET FOREIGN_KEY_CHECKS = 1;
