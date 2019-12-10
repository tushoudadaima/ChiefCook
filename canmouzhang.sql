/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50506
Source Host           : localhost:3306
Source Database       : canmouzhang

Target Server Type    : MYSQL
Target Server Version : 50506
File Encoding         : 65001

Date: 2019-12-10 08:32:41
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `buyer`
-- ----------------------------
DROP TABLE IF EXISTS `buyer`;
CREATE TABLE `buyer` (
  `buyerId` char(11) NOT NULL,
  `buyerPassword` varchar(20) DEFAULT NULL,
  `buyerName` varchar(20) DEFAULT NULL,
  `buyerHeadImg` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`buyerId`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

-- ----------------------------
-- Records of buyer
-- ----------------------------
INSERT INTO `buyer` VALUES ('1', '1', null, null);
INSERT INTO `buyer` VALUES ('11111111111', '111111', null, null);
INSERT INTO `buyer` VALUES ('13073190601', '111111', null, null);
INSERT INTO `buyer` VALUES ('13784015962', '123456', null, null);
INSERT INTO `buyer` VALUES ('15227857577', '123456', null, null);
INSERT INTO `buyer` VALUES ('15231180689', '123456', null, null);
INSERT INTO `buyer` VALUES ('18830931925', '123456', null, null);
INSERT INTO `buyer` VALUES ('2', '2', null, null);
INSERT INTO `buyer` VALUES ('3', '111111', null, null);

-- ----------------------------
-- Table structure for `buyerdetail`
-- ----------------------------
DROP TABLE IF EXISTS `buyerdetail`;
CREATE TABLE `buyerdetail` (
  `buyerName` varchar(50) NOT NULL DEFAULT '',
  `buyerHeadImg` varchar(20) NOT NULL DEFAULT '',
  `buyerId` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`buyerName`,`buyerHeadImg`,`buyerId`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

-- ----------------------------
-- Records of buyerdetail
-- ----------------------------

-- ----------------------------
-- Table structure for `seller`
-- ----------------------------
DROP TABLE IF EXISTS `seller`;
CREATE TABLE `seller` (
  `sellerId` char(11) NOT NULL,
  `sellerPassword` varchar(20) DEFAULT NULL,
  `sellerName` varchar(20) DEFAULT NULL,
  `sellerHeadImg` varchar(20) DEFAULT NULL,
  `sellerAddr` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`sellerId`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

-- ----------------------------
-- Records of seller
-- ----------------------------
INSERT INTO `seller` VALUES ('13073190601', '1111111', null, null, null);
INSERT INTO `seller` VALUES ('18830931925', '123456', null, null, null);
INSERT INTO `seller` VALUES ('2', '2', null, null, null);
