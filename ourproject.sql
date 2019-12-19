/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50701
Source Host           : localhost:3306
Source Database       : ourproject

Target Server Type    : MYSQL
Target Server Version : 50701
File Encoding         : 65001

Date: 2019-12-19 10:13:57
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `buyer`
-- ----------------------------
DROP TABLE IF EXISTS `buyer`;
CREATE TABLE `buyer` (
  `buyerId` varchar(11) NOT NULL,
  `buyerPassword` varchar(20) DEFAULT NULL,
  `uname` varchar(20) DEFAULT NULL,
  `buyerHeadImg` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`buyerId`)
) ENGINE=MyISAM DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Records of buyer
-- ----------------------------
INSERT INTO `buyer` VALUES ('1', '1', 'IT精英', 'D:/upload/1.jpg');
INSERT INTO `buyer` VALUES ('3', '3', '我是小仙女', 'D:/upload/3.jpg');
INSERT INTO `buyer` VALUES ('15231180689', '123456', '哈喽沃德', 'D:/upload/touxiang.jpg');
INSERT INTO `buyer` VALUES ('13073190601', '123456', '小可爱', 'D:/upload/13073190601.jpg');
INSERT INTO `buyer` VALUES ('15227857577', '123456', '子宇组长', 'D:/upload/15227857577.jpg');
INSERT INTO `buyer` VALUES ('18830931925', '123456', '参谋长', 'D:/upload/18830931925.jpg');

-- ----------------------------
-- Table structure for `collection`
-- ----------------------------
DROP TABLE IF EXISTS `collection`;
CREATE TABLE `collection` (
  `collectionId` smallint(6) NOT NULL AUTO_INCREMENT,
  `uname` varchar(20) NOT NULL,
  `vname` varchar(20) NOT NULL,
  PRIMARY KEY (`collectionId`)
) ENGINE=MyISAM AUTO_INCREMENT=17 DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Records of collection
-- ----------------------------
INSERT INTO `collection` VALUES ('2', '1', '炸鸡翅');
INSERT INTO `collection` VALUES ('3', '1', '荷兰豆炒腊肠。');
INSERT INTO `collection` VALUES ('4', '3', '鸡扒减肥餐');
INSERT INTO `collection` VALUES ('5', '3', '糖醋豆腐');
INSERT INTO `collection` VALUES ('6', '3', '粤式经典--白灼虾，酱汁');
INSERT INTO `collection` VALUES ('7', '3', '小猪蛋羹');
INSERT INTO `collection` VALUES ('8', '3', '蒜苔肉丝');
INSERT INTO `collection` VALUES ('9', '1', '家常烧豆腐');
INSERT INTO `collection` VALUES ('10', '1', '减肥餐_什锦锅贴');
INSERT INTO `collection` VALUES ('14', '15227857577', '麻辣烫');
INSERT INTO `collection` VALUES ('13', '13073190601', '红焖猪蹄');
INSERT INTO `collection` VALUES ('15', '13073190601', '正宗三杯鸡');
INSERT INTO `collection` VALUES ('16', '18830931925', '减肥餐～芒果虾仁');

-- ----------------------------
-- Table structure for `discuss`
-- ----------------------------
DROP TABLE IF EXISTS `discuss`;
CREATE TABLE `discuss` (
  `discussId` smallint(6) NOT NULL AUTO_INCREMENT,
  `uname` varchar(20) NOT NULL,
  `vname` varchar(20) NOT NULL,
  `time` varchar(20) NOT NULL,
  `content` varchar(100) NOT NULL,
  `buyerId` varchar(11) DEFAULT NULL,
  PRIMARY KEY (`discussId`)
) ENGINE=MyISAM AUTO_INCREMENT=69 DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Records of discuss
-- ----------------------------
INSERT INTO `discuss` VALUES ('66', '子宇组长', '麻辣烫', '2019-12-19 09:24:25', '评论麻辣烫', '15227857577');
INSERT INTO `discuss` VALUES ('65', 'IT精英', '家常烧豆腐', '2019-12-19 08:59:41', '真的不一样', '1');
INSERT INTO `discuss` VALUES ('64', 'IT精英', '家常烧豆腐', '2019-12-19 08:55:13', '很不一样', '1');
INSERT INTO `discuss` VALUES ('63', 'IT精英', '家常烧豆腐', '2019-12-19 08:54:22', '不一样的', '1');
INSERT INTO `discuss` VALUES ('62', 'IT精英', '糖醋藕片', '2019-12-19 08:53:06', '啊', '1');
INSERT INTO `discuss` VALUES ('60', 'IT精英', '梅菜扣肉', '2019-12-19 08:48:45', '啊啊啊', '1');
INSERT INTO `discuss` VALUES ('12', '哈喽沃德', '红烧日本豆腐', '2019-12-17 16:21:22', '好开心啊，每次都能吃到这么美味的食物，漂亮极了', '15231180689');
INSERT INTO `discuss` VALUES ('13', '哈喽沃德', '梅菜扣肉', '2019-12-17 16:29:33', '真好吃，每次都能和组员一起吃饭。666', '15231180689');
INSERT INTO `discuss` VALUES ('14', '哈喽沃德', '锅塌豆腐', '2019-12-17 16:36:29', '真美味', '15231180689');
INSERT INTO `discuss` VALUES ('15', '哈喽沃德', '回锅肉', '2019-12-17 16:44:01', '太喜欢这道菜了', '15231180689');
INSERT INTO `discuss` VALUES ('61', 'IT精英', '梅菜扣肉', '2019-12-19 08:50:37', '蹦蹦蹦', '1');
INSERT INTO `discuss` VALUES ('18', '哈喽沃德', '梅菜扣肉', '2019-12-17 17:22:32', '今天天气不好', '15231180689');
INSERT INTO `discuss` VALUES ('53', 'IT精英', '梅菜扣肉', '2019-12-19 08:46:26', '挺不错哦', '1');
INSERT INTO `discuss` VALUES ('52', '小可爱', '红焖猪蹄', '2019-12-19 00:06:10', '红烧猪蹄', '13073190601');
INSERT INTO `discuss` VALUES ('22', '哈喽沃德', '家常烧豆腐', '2019-12-17 18:28:49', '刚哈更会哈更刚哈更会哈更刚哈', '15231180689');
INSERT INTO `discuss` VALUES ('23', '哈喽沃德', '家常烧豆腐', '2019-12-17 20:24:42', '一起来吃吧', '15231180689');
INSERT INTO `discuss` VALUES ('24', '哈喽沃德', '家常烧豆腐', '2019-12-17 20:26:41', '我也想啊吃', '15231180689');
INSERT INTO `discuss` VALUES ('25', 'IT精英', '家常烧豆腐', '2019-12-17 20:29:08', '说两句话呗', '1');
INSERT INTO `discuss` VALUES ('26', '我是小仙女', '家常烧豆腐', '2019-12-17 20:36:53', '我特别喜欢这个', '3');
INSERT INTO `discuss` VALUES ('28', '哈喽沃德', '家常烧豆腐', '2019-12-17 20:39:48', '特别香', '15231180689');
INSERT INTO `discuss` VALUES ('51', '小可爱', '红焖猪蹄', '2019-12-19 00:06:08', '红烧猪蹄', '13073190601');
INSERT INTO `discuss` VALUES ('50', 'IT精英', '减肥餐-什锦锅贴', '2019-12-18 20:24:54', '拱嘴', '1');
INSERT INTO `discuss` VALUES ('33', '哈喽沃德', '家常烧豆腐', '2019-12-17 21:55:50', '感觉达到了巅峰', '15231180689');
INSERT INTO `discuss` VALUES ('34', '我是小仙女', '家常烧豆腐', '2019-12-18 08:12:07', '怎么形容呢？', '3');
INSERT INTO `discuss` VALUES ('35', 'IT精英', '家常烧豆腐', '2019-12-18 08:59:52', 'IT评论', '1');
INSERT INTO `discuss` VALUES ('49', 'IT精英', '减肥餐-什锦锅贴', '2019-12-18 20:24:53', '拱嘴', '1');
INSERT INTO `discuss` VALUES ('48', 'IT精英', '红烧牛肉面', '2019-12-18 18:47:34', '我想和你唱', '1');
INSERT INTO `discuss` VALUES ('47', 'IT精英', '家常烧豆腐', '2019-12-18 16:31:26', '我系渣渣辉', '1');
INSERT INTO `discuss` VALUES ('46', 'IT精英', '皮蛋豆腐', '2019-12-18 15:12:17', '我们一起学猫叫吧', '1');
INSERT INTO `discuss` VALUES ('44', '我是小仙女', '梅菜扣肉', '2019-12-18 10:55:55', '梅菜扣肉真好吃', '3');
INSERT INTO `discuss` VALUES ('45', '我是小仙女', '梅菜扣肉', '2019-12-18 10:56:37', '梅菜扣肉贼好吃', '3');
INSERT INTO `discuss` VALUES ('67', '小可爱', '正宗三杯鸡', '2019-12-19 09:54:15', '评论', '13073190601');
INSERT INTO `discuss` VALUES ('68', '参谋长', '减肥餐～芒果虾仁', '2019-12-19 10:03:03', '评论', '18830931925');

-- ----------------------------
-- Table structure for `seller`
-- ----------------------------
DROP TABLE IF EXISTS `seller`;
CREATE TABLE `seller` (
  `sellerId` char(11) NOT NULL,
  `sellerPassword` varchar(20) DEFAULT NULL,
  `sellerName` varchar(20) DEFAULT NULL,
  `sellerHeadImg` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`sellerId`)
) ENGINE=MyISAM DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Records of seller
-- ----------------------------

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` smallint(11) NOT NULL,
  `uname` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL,
  `headingImage` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Records of user
-- ----------------------------
