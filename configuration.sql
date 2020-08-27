/*
Navicat MySQL Data Transfer

Source Server         : docker
Source Server Version : 50730
Source Host           : 192.168.7.80:3306
Source Database       : gulimall_admin

Target Server Type    : MYSQL
Target Server Version : 50730
File Encoding         : 65001

Date: 2020-08-27 09:09:51
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for configuration
-- ----------------------------
DROP TABLE IF EXISTS `configuration`;
CREATE TABLE `configuration` (
  `type` varchar(32) NOT NULL COMMENT '类别',
  `category` varchar(255) NOT NULL COMMENT '类别',
  `data` varchar(255) DEFAULT NULL COMMENT '配置描述',
  `describation` varchar(255) DEFAULT NULL,
  `isvalid` char(1) DEFAULT NULL COMMENT '是否有效：1有效  0无效 其他-永久有效'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of configuration
-- ----------------------------
INSERT INTO `configuration` VALUES ('role', 'storekeeper', '1', '仓管员角色配置，不可修改', '9');
