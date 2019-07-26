/*
Navicat MySQL Data Transfer

Source Server         : 192.168.1.199_mysql
Source Server Version : 50723
Source Host           : 192.168.1.199:3306
Source Database       : esweb

Target Server Type    : MYSQL
Target Server Version : 50723
File Encoding         : 65001

Date: 2019-07-26 17:51:32
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for wechat_payrecord
-- ----------------------------
DROP TABLE IF EXISTS `wechat_payrecord`;
CREATE TABLE `wechat_payrecord` (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `orderNo` varchar(20) NOT NULL COMMENT '订单号',
  `phoneNum` varchar(16) NOT NULL COMMENT '手机号',
  `productType` varchar(30) NOT NULL COMMENT '专业',
  `dayOrder` int(11) NOT NULL COMMENT '题目属于某一天的序号',
  `createTime` datetime DEFAULT NULL COMMENT '订单创建时间',
  `payTime` datetime DEFAULT NULL COMMENT '支付成功时间',
  `status` smallint(6) NOT NULL DEFAULT '1' COMMENT '支付状态1：未支付，2：已支付',
  `orderType` smallint(6) NOT NULL DEFAULT '0' COMMENT '订单类别1：知识点，2：练习题',
  `price` double NOT NULL DEFAULT '0' COMMENT '价格金额（元）',
  `totalFee` double NOT NULL DEFAULT '0' COMMENT '价格金额（元）',
  PRIMARY KEY (`Id`),
  UNIQUE KEY `phone_product_day` (`phoneNum`,`productType`,`dayOrder`,`orderType`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of wechat_payrecord
-- ----------------------------
