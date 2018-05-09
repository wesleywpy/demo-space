/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 50712
Source Host           : 127.0.0.1:3306
Source Database       : wesley

Target Server Type    : MYSQL
Target Server Version : 50712
File Encoding         : 65001

Date: 2018-04-30 11:51:05
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for shiro_roles_permissions
-- ----------------------------
DROP TABLE IF EXISTS `shiro_roles_permissions`;
CREATE TABLE `shiro_roles_permissions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(64) DEFAULT NULL,
  `permission` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of shiro_roles_permissions
-- ----------------------------
INSERT INTO `shiro_roles_permissions` VALUES ('1', 'admin', 'user:delete');
INSERT INTO `shiro_roles_permissions` VALUES ('2', 'admin', 'user:update');

-- ----------------------------
-- Table structure for shiro_user_roles
-- ----------------------------
DROP TABLE IF EXISTS `shiro_user_roles`;
CREATE TABLE `shiro_user_roles` (
  `id` int(11) NOT NULL,
  `role_name` varchar(64) NOT NULL,
  `username` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of shiro_user_roles
-- ----------------------------
INSERT INTO `shiro_user_roles` VALUES ('1', 'admin', 'wesley');

-- ----------------------------
-- Table structure for shiro_users
-- ----------------------------
DROP TABLE IF EXISTS `shiro_users`;
CREATE TABLE `shiro_users` (
  `id` int(11) NOT NULL,
  `username` varchar(64) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of shiro_users
-- ----------------------------
INSERT INTO `shiro_users` VALUES ('1', 'wesley', '123456');
