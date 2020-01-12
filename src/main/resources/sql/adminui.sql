/*
 Navicat Premium Data Transfer

 Source Server         : window-mysql
 Source Server Type    : MySQL
 Source Server Version : 50720
 Source Host           : localhost:3306
 Source Schema         : adminui

 Target Server Type    : MySQL
 Target Server Version : 50720
 File Encoding         : 65001

 Date: 04/10/2019 11:17:11
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
  `createtime` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updatetime` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
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
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `state` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'T' COMMENT '帐号状态',
  `createtime` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updatetime` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_person` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作人',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (3, 'admin', 'admin', 'T', '2019-09-04 15:12:35', NULL, NULL);
INSERT INTO `sys_user` VALUES (4, 'zhangsan', 'zhangsan', 'T', '2019-09-26 15:12:40', NULL, NULL);
INSERT INTO `sys_user` VALUES (5, 'lisi', 'lisi', 'T', '2019-09-05 15:12:42', '2019-09-21 15:07:41', 'admin');
INSERT INTO `sys_user` VALUES (6, 'wangwu', 'wangwu', 'T', '2019-09-04 15:12:45', '2019-09-21 15:09:55', 'admin');
INSERT INTO `sys_user` VALUES (7, 'zhaoliu', 'zhaoliu', 'T', '2019-09-03 15:12:47', '2019-09-21 15:43:56', '11');
INSERT INTO `sys_user` VALUES (8, 'user1', '1', 'F', '2019-09-21 15:12:51', '2019-09-21 15:20:43', '11');
INSERT INTO `sys_user` VALUES (9, 'user2', '2', 'F', '2019-09-03 16:09:15', '2019-09-21 15:20:45', '11');
INSERT INTO `sys_user` VALUES (10, 'user3', '2', 'F', '2019-09-04 16:09:20', '2019-09-21 15:20:48', '11');
INSERT INTO `sys_user` VALUES (11, '1', '1', 'F', '2019-08-29 16:09:28', '2019-09-21 15:49:50', '1');
INSERT INTO `sys_user` VALUES (12, '2', '2', 'T', '2019-08-28 16:09:32', NULL, NULL);
INSERT INTO `sys_user` VALUES (13, '11', '11', 'F', '2019-09-21 15:20:19', '2019-09-21 15:20:35', '11');
INSERT INTO `sys_user` VALUES (14, 'a', 'a', 'F', '2019-09-21 15:50:03', '2019-09-21 15:50:19', 'a');

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
) ENGINE = InnoDB AUTO_INCREMENT = 80 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户与角色关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 3, 1);
INSERT INTO `sys_user_role` VALUES (2, 4, 2);
INSERT INTO `sys_user_role` VALUES (9, 8, 3);
INSERT INTO `sys_user_role` VALUES (10, 9, 3);
INSERT INTO `sys_user_role` VALUES (11, 10, 3);
INSERT INTO `sys_user_role` VALUES (14, 12, 3);
INSERT INTO `sys_user_role` VALUES (15, 11, 3);
INSERT INTO `sys_user_role` VALUES (21, 10, 2);
INSERT INTO `sys_user_role` VALUES (24, 9, 2);
INSERT INTO `sys_user_role` VALUES (57, 5, 2);
INSERT INTO `sys_user_role` VALUES (58, 5, 3);
INSERT INTO `sys_user_role` VALUES (72, 7, 1);
INSERT INTO `sys_user_role` VALUES (73, 7, 2);
INSERT INTO `sys_user_role` VALUES (74, 7, 3);
INSERT INTO `sys_user_role` VALUES (76, 6, 2);
INSERT INTO `sys_user_role` VALUES (77, 6, 3);
INSERT INTO `sys_user_role` VALUES (78, 5, 1);
INSERT INTO `sys_user_role` VALUES (79, 6, 1);

SET FOREIGN_KEY_CHECKS = 1;
