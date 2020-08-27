/*
Navicat MySQL Data Transfer

Source Server         : docker
Source Server Version : 50730
Source Host           : 192.168.7.80:3306
Source Database       : es_dep

Target Server Type    : MYSQL
Target Server Version : 50730
File Encoding         : 65001

Date: 2020-08-27 09:03:27
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for dep_applyinfo
-- ----------------------------
DROP TABLE IF EXISTS `dep_applyinfo`;
CREATE TABLE `dep_applyinfo` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `equip_id` bigint(20) NOT NULL COMMENT '设备id',
  `apply_time` datetime DEFAULT NULL COMMENT '申请时间',
  `worker_id` varchar(100) DEFAULT '0' COMMENT '工人id',
  `worker_name` varchar(100) DEFAULT '0' COMMENT '工人名称',
  `repair_orderid` char(32) DEFAULT NULL COMMENT '抢修单编号',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='设备申请表';

-- ----------------------------
-- Records of dep_applyinfo
-- ----------------------------

-- ----------------------------
-- Table structure for dep_equipments
-- ----------------------------
DROP TABLE IF EXISTS `dep_equipments`;
CREATE TABLE `dep_equipments` (
  `equip_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '设备id',
  `store_id` bigint(20) NOT NULL COMMENT '仓库id',
  `type_id` int(11) DEFAULT NULL COMMENT '分类ID',
  `equip_name` varchar(100) DEFAULT NULL COMMENT '设备名称',
  `equip_totalmount` int(100) DEFAULT '0' COMMENT '设备总数量',
  `equip_currentmount` int(100) DEFAULT '0' COMMENT '设备当前数量',
  `equip_unit` varchar(10) DEFAULT NULL COMMENT '设备单位',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`equip_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='设备资源表';

-- ----------------------------
-- Records of dep_equipments
-- ----------------------------
INSERT INTO `dep_equipments` VALUES ('1', '1', '1', '风火轮1', '99', '50', '台', '');
INSERT INTO `dep_equipments` VALUES ('2', '2', '2', '风火轮2', '100', '66', '辆', '');

-- ----------------------------
-- Table structure for dep_equipments_type
-- ----------------------------
DROP TABLE IF EXISTS `dep_equipments_type`;
CREATE TABLE `dep_equipments_type` (
  `type_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '分类ID',
  `type_name` varchar(128) DEFAULT NULL COMMENT '分类名称',
  `type_remark` varchar(255) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dep_equipments_type
-- ----------------------------
INSERT INTO `dep_equipments_type` VALUES ('1', '电缆', '电缆分类描述，电缆分类描述，电缆分类描述，电缆分类描述，电缆分类描述，电缆分类描述，电缆分类描述，电缆分类描述，电缆分类描述，电缆分类描述，电缆分类描述，电缆分类描述，电缆分类描述，电缆分类描述，电缆分类描述，');
INSERT INTO `dep_equipments_type` VALUES ('2', '架空导线', '啊啊啊啊啊啊啊啊啊啊啊啊啊');

-- ----------------------------
-- Table structure for dep_resource
-- ----------------------------
DROP TABLE IF EXISTS `dep_resource`;
CREATE TABLE `dep_resource` (
  `resource_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '资源id',
  `resource_name` varchar(100) NOT NULL COMMENT '资源名称',
  `resource_addr` varchar(200) DEFAULT NULL COMMENT '资源地址',
  `resource_phone` varchar(20) DEFAULT NULL COMMENT '资源联系方式',
  `remark` text COMMENT '资源备注',
  PRIMARY KEY (`resource_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COMMENT='部门资源';

-- ----------------------------
-- Records of dep_resource
-- ----------------------------
INSERT INTO `dep_resource` VALUES ('4', '于集供电所', '安徽省阜阳市阜南县XX路XX号', '18360004236', '于集供电所备注于集供电所备注于集供电所备注于集供电所备注于集供电所备注于集供电所备注于集供电所备注于集供电所备注于集供电所备注于集供电所备注于集供电所备注于集供电所备注于集供电所备注于集供电所备注于集供电所备注于集供电所备注于集供电所备注于集供电所备注于集供电所备注于集供电所备注于集供电所备注于集供电所备注于集供电所备注于集供电所备注于集供电所备注于集供电所备注于集供电所备注于集供电所备注');
INSERT INTO `dep_resource` VALUES ('5', '苗集供电所', '安徽省阜阳市阜南县XX路XX号', '13695627823', '苗集供电所苗集供电所苗集供电所');

-- ----------------------------
-- Table structure for dep_store
-- ----------------------------
DROP TABLE IF EXISTS `dep_store`;
CREATE TABLE `dep_store` (
  `store_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '仓库id',
  `resource_id` bigint(20) NOT NULL COMMENT '资源id',
  `store_name` varchar(100) DEFAULT NULL COMMENT '仓库名称',
  `store_manager` int(100) DEFAULT '0' COMMENT '仓库负责人',
  `manager_phone` varchar(100) DEFAULT NULL COMMENT '仓库联系电话',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`store_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='资源所属仓库';

-- ----------------------------
-- Records of dep_store
-- ----------------------------
INSERT INTO `dep_store` VALUES ('1', '4', '于集仓库', '2', '18360004001', '于集仓库备注，于集仓库备注，于集仓库备注，于集仓库备注，于集仓库备注，于集仓库备注\n');
INSERT INTO `dep_store` VALUES ('2', '5', '苗集仓库', '2', '18360004001', '');

-- ----------------------------
-- Table structure for dep_workers
-- ----------------------------
DROP TABLE IF EXISTS `dep_workers`;
CREATE TABLE `dep_workers` (
  `worker_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '工人id',
  `resource_id` bigint(20) DEFAULT NULL COMMENT '资源id',
  `worker_name` varchar(20) DEFAULT NULL COMMENT '工人姓名',
  `worker_birth` varchar(32) DEFAULT NULL COMMENT '工人出生日期',
  `worker_position` varchar(100) DEFAULT NULL COMMENT '工人职位',
  `orders_received` bigint(20) DEFAULT NULL COMMENT '接单数量',
  `orders_finished` bigint(20) DEFAULT NULL COMMENT '结单数量',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `work_pic` varchar(255) DEFAULT NULL COMMENT '头像',
  `work_nickname` varchar(20) DEFAULT NULL COMMENT '昵称/用户名',
  `work_mobile` char(11) DEFAULT NULL COMMENT '联系方式',
  `work_password` varchar(32) DEFAULT NULL COMMENT '加密后密码',
  PRIMARY KEY (`worker_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='抢修工人';

-- ----------------------------
-- Records of dep_workers
-- ----------------------------
INSERT INTO `dep_workers` VALUES ('1', '4', '于辉', '1991-08-22', '普通员工', '0', '0', null, 'file\\images\\1598489377722于辉341221199107130837.jpg', 'yuhui3170', '18360004009', '5dad40678f0d14118e3fef87979f94ac');
INSERT INTO `dep_workers` VALUES ('2', '5', '郭子豪', '1990-03-07', '', '0', '0', null, 'file\\images\\1598489466393郭子豪341204199003070810.jpg', 'guozihao123', '18767101041', 'efc2471934f4ab684cf7657d7755b50c');

-- ----------------------------
-- Table structure for dep_workers_code
-- ----------------------------
DROP TABLE IF EXISTS `dep_workers_code`;
CREATE TABLE `dep_workers_code` (
  `cord_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '验证码ID',
  `mobile` char(11) DEFAULT NULL COMMENT '手机号码',
  `code` char(6) DEFAULT NULL COMMENT '验证码',
  `expire_time` datetime DEFAULT NULL COMMENT '过期时间',
  PRIMARY KEY (`cord_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dep_workers_code
-- ----------------------------
INSERT INTO `dep_workers_code` VALUES ('1', '18360004009', '3333', '2020-05-27 14:53:05');
INSERT INTO `dep_workers_code` VALUES ('2', '18360004008', '2389', '2020-05-26 18:54:43');
INSERT INTO `dep_workers_code` VALUES ('3', '18360004009', '8131', '2020-05-28 02:40:59');
INSERT INTO `dep_workers_code` VALUES ('4', '18360004009', '7383', '2020-05-28 04:42:35');
INSERT INTO `dep_workers_code` VALUES ('5', '18360004009', '0953', '2020-08-15 19:55:01');
INSERT INTO `dep_workers_code` VALUES ('6', '18360004009', '6388', '2020-08-16 02:47:07');
INSERT INTO `dep_workers_code` VALUES ('7', '18360004009', '3098', '2020-08-16 03:46:51');
INSERT INTO `dep_workers_code` VALUES ('8', '18360004009', '3302', '2020-08-20 03:57:28');
INSERT INTO `dep_workers_code` VALUES ('9', '18360004009', '8489', '2020-08-20 05:07:50');
