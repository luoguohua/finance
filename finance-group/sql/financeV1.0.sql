/*
 Navicat Premium Data Transfer

 Source Server         : my_docker_mysql8
 Source Server Type    : MySQL
 Source Server Version : 80029
 Source Host           : 192.168.14.2:3306
 Source Schema         : finance

 Target Server Type    : MySQL
 Target Server Version : 80029
 File Encoding         : 65001

 Date: 05/07/2022 13:43:32
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for oauth_client_details
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details`  (
  `client_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `resource_ids` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `client_secret` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `scope` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `authorized_grant_types` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `web_server_redirect_uri` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `authorities` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `access_token_validity` int(0) NOT NULL,
  `refresh_token_validity` int(0) NULL DEFAULT NULL,
  `additional_information` varchar(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `autoapprove` tinyint(0) NULL DEFAULT NULL,
  `origin_secret` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`client_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '客户端配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oauth_client_details
-- ----------------------------
INSERT INTO `oauth_client_details` VALUES ('app', '', '$2a$10$8Qk/efslEpO1Af1kyw/rp.DdJGsdnET8UCp1vGDzpQEa.1qBklvua', 'all', 'refresh_token,password', '', NULL, 86400, 864000, NULL, NULL, '123456');
INSERT INTO `oauth_client_details` VALUES ('finance', ' ', '$2a$10$aSZTvMOtUAYUQ.75z2n3ceJd6dCIk9Vy3J/SKZUE4hBLd6sz7.6ge', 'all', 'password,refresh_token', NULL, NULL, 86400, 8640000, NULL, 0, '123456');

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`  (
  `DEPT_ID` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '部门ID',
  `PARENT_ID` bigint(0) NOT NULL COMMENT '上级部门ID',
  `DEPT_NAME` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '部门名称',
  `ORDER_NUM` double(20, 0) NULL DEFAULT NULL COMMENT '排序',
  `CREATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `MODIFY_TIME` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`DEPT_ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '部门表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES (1, 0, '开发部', 1, '2018-01-04 15:42:26', '2019-01-05 21:08:27');

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log`  (
  `ID` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '日志ID',
  `USERNAME` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '操作用户',
  `OPERATION` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '操作内容',
  `TIME` decimal(11, 0) NULL DEFAULT NULL COMMENT '耗时',
  `METHOD` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '操作方法',
  `PARAMS` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '方法参数',
  `IP` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '操作者IP',
  `CREATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `location` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '操作地点',
  PRIMARY KEY (`ID`) USING BTREE,
  INDEX `sys_log_create_time`(`CREATE_TIME`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户操作日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_log
-- ----------------------------
INSERT INTO `sys_log` VALUES (1, 'luoguohua', '删除菜单/按钮', 43, 'com.luoguohua.finance.system.controller.MenuController.deleteMenus()', ' menuIds: \"173,156,183,154,168\"', '172.20.0.32', '2022-07-04 16:42:42', '内网IP|0|0|内网IP|内网IP');

-- ----------------------------
-- Table structure for sys_login_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_login_log`;
CREATE TABLE `sys_login_log`  (
  `ID` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `USERNAME` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `LOGIN_TIME` datetime(0) NOT NULL COMMENT '登录时间',
  `LOCATION` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录地点',
  `IP` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'IP地址',
  `SYSTEM` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作系统',
  `BROWSER` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '浏览器',
  PRIMARY KEY (`ID`) USING BTREE,
  INDEX `sys_login_log_login_time`(`LOGIN_TIME`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '登录日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_login_log
-- ----------------------------
INSERT INTO `sys_login_log` VALUES (1, 'luoguohua', '2022-07-01 13:48:50', '内网IP|0|0|内网IP|内网IP', '172.20.0.32', 'Windows 10', 'Chrome 10');
INSERT INTO `sys_login_log` VALUES (2, 'luoguohua', '2022-07-02 14:42:20', '内网IP|0|0|内网IP|内网IP', '127.0.0.1', 'Windows 10', 'Chrome 10');
INSERT INTO `sys_login_log` VALUES (3, 'luoguohua', '2022-07-04 16:10:09', '内网IP|0|0|内网IP|内网IP', '172.20.0.32', 'Windows 10', 'Chrome 10');
INSERT INTO `sys_login_log` VALUES (4, 'luoguohua', '2022-07-04 16:35:37', '内网IP|0|0|内网IP|内网IP', '172.20.0.32', 'Windows 10', 'Chrome 10');
INSERT INTO `sys_login_log` VALUES (5, 'luoguohua', '2022-07-04 16:41:49', '内网IP|0|0|内网IP|内网IP', '172.20.0.32', 'Windows 10', 'Chrome 10');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `MENU_ID` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '菜单/按钮ID',
  `PARENT_ID` bigint(0) NOT NULL COMMENT '上级菜单ID',
  `MENU_NAME` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单/按钮名称',
  `PATH` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '对应路由path',
  `COMPONENT` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '对应路由组件component',
  `PERMS` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限标识',
  `ICON` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图标',
  `TYPE` char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '类型 0菜单 1按钮',
  `ORDER_NUM` double(20, 0) NULL DEFAULT NULL COMMENT '排序',
  `CREATE_TIME` datetime(0) NOT NULL COMMENT '创建时间',
  `MODIFY_TIME` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`MENU_ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 195 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '菜单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, 0, '系统管理', '/system', 'Layout', NULL, 'el-icon-set-up', '0', 1, '2017-12-27 16:39:07', '2019-07-20 16:19:04');
INSERT INTO `sys_menu` VALUES (2, 1, '用户管理', '/system/user', 'febs/system/user/Index', 'user:view', '', '0', 1, '2017-12-27 16:47:13', '2019-01-22 06:45:55');
INSERT INTO `sys_menu` VALUES (4, 1, '角色管理', '/system/role', 'febs/system/role/Index', 'role:view', '', '0', 2, '2017-12-27 16:48:09', '2018-04-25 09:01:12');
INSERT INTO `sys_menu` VALUES (5, 1, '菜单管理', '/system/menu', 'febs/system/menu/Index', 'menu:view', '', '0', 3, '2017-12-27 16:48:57', '2018-04-25 09:01:30');
INSERT INTO `sys_menu` VALUES (6, 1, '部门管理', '/system/dept', 'febs/system/dept/Index', 'dept:view', '', '0', 4, '2017-12-27 16:57:33', '2018-04-25 09:01:40');
INSERT INTO `sys_menu` VALUES (10, 1, '系统日志', '/monitor/systemlog', 'febs/monitor/systemlog/Index', 'log:view', '', '0', 2, '2017-12-27 17:00:50', '2022-07-01 14:15:06');
INSERT INTO `sys_menu` VALUES (11, 2, '新增用户', '', '', 'user:add', NULL, '1', NULL, '2017-12-27 17:02:58', NULL);
INSERT INTO `sys_menu` VALUES (12, 2, '修改用户', '', '', 'user:update', NULL, '1', NULL, '2017-12-27 17:04:07', NULL);
INSERT INTO `sys_menu` VALUES (13, 2, '删除用户', '', '', 'user:delete', NULL, '1', NULL, '2017-12-27 17:04:58', NULL);
INSERT INTO `sys_menu` VALUES (14, 4, '新增角色', '', '', 'role:add', NULL, '1', NULL, '2017-12-27 17:06:38', NULL);
INSERT INTO `sys_menu` VALUES (15, 4, '修改角色', '', '', 'role:update', NULL, '1', NULL, '2017-12-27 17:06:38', NULL);
INSERT INTO `sys_menu` VALUES (16, 4, '删除角色', '', '', 'role:delete', NULL, '1', NULL, '2017-12-27 17:06:38', NULL);
INSERT INTO `sys_menu` VALUES (17, 5, '新增菜单', '', '', 'menu:add', NULL, '1', NULL, '2017-12-27 17:08:02', NULL);
INSERT INTO `sys_menu` VALUES (18, 5, '修改菜单', '', '', 'menu:update', NULL, '1', NULL, '2017-12-27 17:08:02', NULL);
INSERT INTO `sys_menu` VALUES (19, 5, '删除菜单', '', '', 'menu:delete', NULL, '1', NULL, '2017-12-27 17:08:02', NULL);
INSERT INTO `sys_menu` VALUES (20, 6, '新增部门', '', '', 'dept:add', NULL, '1', NULL, '2017-12-27 17:09:24', NULL);
INSERT INTO `sys_menu` VALUES (21, 6, '修改部门', '', '', 'dept:update', NULL, '1', NULL, '2017-12-27 17:09:24', NULL);
INSERT INTO `sys_menu` VALUES (22, 6, '删除部门', '', '', 'dept:delete', NULL, '1', NULL, '2017-12-27 17:09:24', NULL);
INSERT INTO `sys_menu` VALUES (24, 10, '删除日志', '', '', 'log:delete', NULL, '1', NULL, '2017-12-27 17:11:45', NULL);
INSERT INTO `sys_menu` VALUES (130, 3, '导出Excel', NULL, NULL, 'user:export', NULL, '1', NULL, '2019-01-23 06:35:16', NULL);
INSERT INTO `sys_menu` VALUES (131, 4, '导出Excel', NULL, NULL, 'role:export', NULL, '1', NULL, '2019-01-23 06:35:36', NULL);
INSERT INTO `sys_menu` VALUES (132, 5, '导出Excel', NULL, NULL, 'menu:export', NULL, '1', NULL, '2019-01-23 06:36:05', NULL);
INSERT INTO `sys_menu` VALUES (133, 6, '导出Excel', NULL, NULL, 'dept:export', NULL, '1', NULL, '2019-01-23 06:36:25', NULL);
INSERT INTO `sys_menu` VALUES (135, 3, '密码重置', NULL, NULL, 'user:reset', NULL, '1', NULL, '2019-01-23 06:37:00', NULL);
INSERT INTO `sys_menu` VALUES (136, 10, '导出Excel', NULL, NULL, 'log:export', NULL, '1', NULL, '2019-01-23 06:37:27', NULL);
INSERT INTO `sys_menu` VALUES (150, 1, '登录日志', '/monitor/loginlog', 'febs/monitor/loginlog/Index', 'monitor:loginlog', '', '0', 3, '2019-07-22 13:41:17', '2022-07-01 14:15:17');
INSERT INTO `sys_menu` VALUES (151, 150, '删除日志', NULL, NULL, 'loginlog:delete', NULL, '1', NULL, '2019-07-22 13:43:04', NULL);
INSERT INTO `sys_menu` VALUES (152, 150, '导出Excel', NULL, NULL, 'loginlog:export', NULL, '1', NULL, '2019-07-22 13:43:30', NULL);
INSERT INTO `sys_menu` VALUES (163, 1, '客户端管理', '/client', 'febs/system/client/Index', 'client:view', '', '0', 5, '2019-09-26 22:58:09', NULL);
INSERT INTO `sys_menu` VALUES (164, 163, '新增', NULL, NULL, 'client:add', NULL, '1', NULL, '2019-09-26 22:58:21', NULL);
INSERT INTO `sys_menu` VALUES (165, 163, '修改', NULL, NULL, 'client:update', NULL, '1', NULL, '2019-09-26 22:58:43', NULL);
INSERT INTO `sys_menu` VALUES (166, 163, '删除', NULL, NULL, 'client:delete', NULL, '1', NULL, '2019-09-26 22:58:55', NULL);
INSERT INTO `sys_menu` VALUES (167, 163, '解密', NULL, NULL, 'client:decrypt', NULL, '1', NULL, '2019-09-26 22:59:08', NULL);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `ROLE_ID` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `ROLE_NAME` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名称',
  `REMARK` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色描述',
  `CREATE_TIME` datetime(0) NOT NULL COMMENT '创建时间',
  `MODIFY_TIME` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`ROLE_ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '管理员', '管理员', '2019-08-08 16:23:11', '2019-08-09 14:38:59');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `ROLE_ID` bigint(0) NOT NULL,
  `MENU_ID` bigint(0) NOT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色菜单关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (1, 1);
INSERT INTO `sys_role_menu` VALUES (1, 2);
INSERT INTO `sys_role_menu` VALUES (1, 3);
INSERT INTO `sys_role_menu` VALUES (1, 4);
INSERT INTO `sys_role_menu` VALUES (1, 5);
INSERT INTO `sys_role_menu` VALUES (1, 6);
INSERT INTO `sys_role_menu` VALUES (1, 10);
INSERT INTO `sys_role_menu` VALUES (1, 11);
INSERT INTO `sys_role_menu` VALUES (1, 12);
INSERT INTO `sys_role_menu` VALUES (1, 13);
INSERT INTO `sys_role_menu` VALUES (1, 14);
INSERT INTO `sys_role_menu` VALUES (1, 15);
INSERT INTO `sys_role_menu` VALUES (1, 16);
INSERT INTO `sys_role_menu` VALUES (1, 17);
INSERT INTO `sys_role_menu` VALUES (1, 18);
INSERT INTO `sys_role_menu` VALUES (1, 19);
INSERT INTO `sys_role_menu` VALUES (1, 20);
INSERT INTO `sys_role_menu` VALUES (1, 21);
INSERT INTO `sys_role_menu` VALUES (1, 22);
INSERT INTO `sys_role_menu` VALUES (1, 24);
INSERT INTO `sys_role_menu` VALUES (1, 130);
INSERT INTO `sys_role_menu` VALUES (1, 131);
INSERT INTO `sys_role_menu` VALUES (1, 132);
INSERT INTO `sys_role_menu` VALUES (1, 133);
INSERT INTO `sys_role_menu` VALUES (1, 135);
INSERT INTO `sys_role_menu` VALUES (1, 136);
INSERT INTO `sys_role_menu` VALUES (1, 150);
INSERT INTO `sys_role_menu` VALUES (1, 151);
INSERT INTO `sys_role_menu` VALUES (1, 152);
INSERT INTO `sys_role_menu` VALUES (1, 154);
INSERT INTO `sys_role_menu` VALUES (1, 155);
INSERT INTO `sys_role_menu` VALUES (1, 156);
INSERT INTO `sys_role_menu` VALUES (1, 157);
INSERT INTO `sys_role_menu` VALUES (1, 158);
INSERT INTO `sys_role_menu` VALUES (1, 159);
INSERT INTO `sys_role_menu` VALUES (1, 160);
INSERT INTO `sys_role_menu` VALUES (1, 163);
INSERT INTO `sys_role_menu` VALUES (1, 164);
INSERT INTO `sys_role_menu` VALUES (1, 165);
INSERT INTO `sys_role_menu` VALUES (1, 166);
INSERT INTO `sys_role_menu` VALUES (1, 167);
INSERT INTO `sys_role_menu` VALUES (1, 168);
INSERT INTO `sys_role_menu` VALUES (1, 169);
INSERT INTO `sys_role_menu` VALUES (1, 170);
INSERT INTO `sys_role_menu` VALUES (1, 171);
INSERT INTO `sys_role_menu` VALUES (1, 172);
INSERT INTO `sys_role_menu` VALUES (1, 173);
INSERT INTO `sys_role_menu` VALUES (1, 174);
INSERT INTO `sys_role_menu` VALUES (1, 175);
INSERT INTO `sys_role_menu` VALUES (1, 176);
INSERT INTO `sys_role_menu` VALUES (1, 177);
INSERT INTO `sys_role_menu` VALUES (1, 178);
INSERT INTO `sys_role_menu` VALUES (1, 179);
INSERT INTO `sys_role_menu` VALUES (1, 180);
INSERT INTO `sys_role_menu` VALUES (1, 181);
INSERT INTO `sys_role_menu` VALUES (1, 182);
INSERT INTO `sys_role_menu` VALUES (1, 183);
INSERT INTO `sys_role_menu` VALUES (1, 184);
INSERT INTO `sys_role_menu` VALUES (1, 185);
INSERT INTO `sys_role_menu` VALUES (1, 186);
INSERT INTO `sys_role_menu` VALUES (1, 187);
INSERT INTO `sys_role_menu` VALUES (1, 188);
INSERT INTO `sys_role_menu` VALUES (1, 189);
INSERT INTO `sys_role_menu` VALUES (1, 190);
INSERT INTO `sys_role_menu` VALUES (1, 191);
INSERT INTO `sys_role_menu` VALUES (1, 192);
INSERT INTO `sys_role_menu` VALUES (1, 193);
INSERT INTO `sys_role_menu` VALUES (1, 194);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `USER_ID` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `USERNAME` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `PASSWORD` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `DEPT_ID` bigint(0) NULL DEFAULT NULL COMMENT '部门ID',
  `EMAIL` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `MOBILE` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `STATUS` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '状态 0锁定 1有效',
  `CREATE_TIME` datetime(0) NOT NULL COMMENT '创建时间',
  `MODIFY_TIME` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `LAST_LOGIN_TIME` datetime(0) NULL DEFAULT NULL COMMENT '最近访问时间',
  `SSEX` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '性别 0男 1女 2保密',
  `IS_TAB` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'COMMENT \'是否开启tab，0关闭 1开启\'',
  `THEME` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '主题',
  `AVATAR` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像',
  `DESCRIPTION` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`USER_ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'luoguohua', '$2a$10$HBzeo/9EMTORRoX7JT9/We3Sz1xL7yNZNkhz57RMrJ2p.HIBj178e', 1, '503153198@qq.com', '17788888888', '1', '2019-06-14 20:39:22', '2019-07-19 10:18:36', '2022-07-04 16:41:49', '0', NULL, NULL, 'default.jpg', '我是帅比作者。');
INSERT INTO `sys_user` VALUES (2, '测试用户', '$2a$10$qnkX7Vk8UnK.dpzGve3XHu0h9IOUsgnHQDpZ075wudZ8FGZPvj8vW', 1, '503153198@qq.com', '13800138000', '1', '2022-07-02 15:43:44', NULL, NULL, '2', NULL, NULL, 'default.jpg', NULL);

-- ----------------------------
-- Table structure for sys_user_connection
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_connection`;
CREATE TABLE `sys_user_connection`  (
  `USER_NAME` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'FEBS系统用户名',
  `PROVIDER_NAME` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '第三方平台名称',
  `PROVIDER_USER_ID` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '第三方平台账户ID',
  `PROVIDER_USER_NAME` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '第三方平台用户名',
  `NICK_NAME` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '第三方平台昵称',
  `IMAGE_URL` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '第三方平台头像',
  `LOCATION` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地址',
  `REMARK` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`USER_NAME`, `PROVIDER_NAME`, `PROVIDER_USER_ID`) USING BTREE,
  UNIQUE INDEX `UserConnectionRank`(`USER_NAME`, `PROVIDER_NAME`, `PROVIDER_USER_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统用户社交账户关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_connection
-- ----------------------------

-- ----------------------------
-- Table structure for sys_user_data_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_data_permission`;
CREATE TABLE `sys_user_data_permission`  (
  `USER_ID` bigint(0) NOT NULL,
  `DEPT_ID` bigint(0) NOT NULL,
  PRIMARY KEY (`USER_ID`, `DEPT_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户数据权限关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_data_permission
-- ----------------------------

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `USER_ID` bigint(0) NOT NULL COMMENT '用户ID',
  `ROLE_ID` bigint(0) NOT NULL COMMENT '角色ID'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户角色关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 1);
INSERT INTO `sys_user_role` VALUES (2, 1);

-- ----------------------------
-- Table structure for t_ledger
-- ----------------------------
DROP TABLE IF EXISTS `t_ledger`;
CREATE TABLE `t_ledger`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `ledger_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '账本名称',
  `assessed_monthly_income` decimal(8, 2) NOT NULL DEFAULT 0.00 COMMENT '评估月收入',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `modify_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户操作日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_ledger
-- ----------------------------

-- ----------------------------
-- Table structure for t_ledger_budget_category
-- ----------------------------
DROP TABLE IF EXISTS `t_ledger_budget_category`;
CREATE TABLE `t_ledger_budget_category`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `ledger_id` bigint(0) NOT NULL COMMENT '账本id',
  `category_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '类别名称',
  `category_code` bigint(0) NOT NULL COMMENT '类别编码',
  `budget_proportion` decimal(5, 2) NOT NULL COMMENT '预算比例',
  `budget_amounts` decimal(8, 2) NOT NULL COMMENT '预算金额',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `modify_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '预算类目表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_ledger_budget_category
-- ----------------------------

-- ----------------------------
-- Table structure for t_ledger_category_label
-- ----------------------------
DROP TABLE IF EXISTS `t_ledger_category_label`;
CREATE TABLE `t_ledger_category_label`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `budget_category_id` bigint(0) NOT NULL COMMENT '预算类目id',
  `label_id` bigint(0) NOT NULL COMMENT '标签id',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `modify_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '预算类目标签关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_ledger_category_label
-- ----------------------------

-- ----------------------------
-- Table structure for t_ledger_label
-- ----------------------------
DROP TABLE IF EXISTS `t_ledger_label`;
CREATE TABLE `t_ledger_label`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `ledger_id` bigint(0) NOT NULL COMMENT '账本id',
  `label_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'O' COMMENT '标签类型：O,支出；I,收入',
  `label_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '标签名称',
  `label_code` bigint(0) NOT NULL COMMENT '标签编码',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `modify_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '账本标签表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_ledger_label
-- ----------------------------

-- ----------------------------
-- Table structure for t_ledger_record
-- ----------------------------
DROP TABLE IF EXISTS `t_ledger_record`;
CREATE TABLE `t_ledger_record`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `ledger_id` bigint(0) NOT NULL COMMENT '账本id',
  `label_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'O' COMMENT '标签类型：O,支出；I,收入',
  `label_code` bigint(0) NOT NULL COMMENT '标签编码',
  `amounts` decimal(10, 2) NOT NULL COMMENT '金额',
  `recorder` bigint(0) NOT NULL COMMENT '记录者',
  `record_time` datetime(0) NOT NULL COMMENT '记录时间',
  `create_user` bigint(0) NOT NULL COMMENT '创建人',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `modify_user` bigint(0) NOT NULL COMMENT '创建人',
  `modify_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '账目表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_ledger_record
-- ----------------------------

-- ----------------------------
-- Table structure for t_ledger_user
-- ----------------------------
DROP TABLE IF EXISTS `t_ledger_user`;
CREATE TABLE `t_ledger_user`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `ledger_id` bigint(0) NOT NULL COMMENT '账本id',
  `user_id` bigint(0) NOT NULL COMMENT '用户id',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '账本用户关联表' ROW_FORMAT = Fixed;

-- ----------------------------
-- Records of t_ledger_user
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
