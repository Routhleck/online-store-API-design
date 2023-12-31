/*
 Navicat Premium Data Transfer

 Source Server         : tencent
 Source Server Type    : MySQL
 Source Server Version : 50718 (5.7.18-cynos-log)
 Source Host           : bj-cynosdbmysql-grp-og8zp5ro.sql.tencentcdb.com:26956
 Source Schema         : api_design

 Target Server Type    : MySQL
 Target Server Version : 50718 (5.7.18-cynos-log)
 File Encoding         : 65001

 Date: 24/06/2023 22:19:51
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for cart
-- ----------------------------
DROP TABLE IF EXISTS `cart`;
CREATE TABLE `cart`  (
  `id` int(11) NOT NULL,
  `user_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FKl70asp4l4w0jmbm1tqyofho4o`(`user_id`) USING BTREE,
  CONSTRAINT `FKl70asp4l4w0jmbm1tqyofho4o` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for cart_item
-- ----------------------------
DROP TABLE IF EXISTS `cart_item`;
CREATE TABLE `cart_item`  (
  `id` int(11) NOT NULL,
  `quantity` int(11) NULL DEFAULT 1 COMMENT '商品数量',
  `cart_id` int(11) NULL DEFAULT NULL,
  `product_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK1uobyhgl1wvgt1jpccia8xxs3`(`cart_id`) USING BTREE,
  INDEX `FKjcyd5wv4igqnw413rgxbfu4nv`(`product_id`) USING BTREE,
  CONSTRAINT `FK1uobyhgl1wvgt1jpccia8xxs3` FOREIGN KEY (`cart_id`) REFERENCES `cart` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKjcyd5wv4igqnw413rgxbfu4nv` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for cart_item_seq
-- ----------------------------
DROP TABLE IF EXISTS `cart_item_seq`;
CREATE TABLE `cart_item_seq`  (
  `next_val` bigint(20) NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for cart_seq
-- ----------------------------
DROP TABLE IF EXISTS `cart_seq`;
CREATE TABLE `cart_seq`  (
  `next_val` bigint(20) NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `id` int(11) NOT NULL,
  `category_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'test' COMMENT '分类名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for category_seq
-- ----------------------------
DROP TABLE IF EXISTS `category_seq`;
CREATE TABLE `category_seq`  (
  `next_val` bigint(20) NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
  `id` int(11) NOT NULL,
  `is_delivered` tinyint(1) NULL DEFAULT 0 COMMENT '是否发货',
  `is_finished` tinyint(1) NULL DEFAULT 0 COMMENT '是否完成',
  `is_payed` tinyint(1) NULL DEFAULT 0 COMMENT '是否支付',
  `user_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FKel9kyl84ego2otj2accfd8mr7`(`user_id`) USING BTREE,
  CONSTRAINT `FKel9kyl84ego2otj2accfd8mr7` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for orders_item
-- ----------------------------
DROP TABLE IF EXISTS `orders_item`;
CREATE TABLE `orders_item`  (
  `id` int(11) NOT NULL,
  `quantity` int(11) NULL DEFAULT 1 COMMENT '商品数量',
  `orders_id` int(11) NULL DEFAULT NULL,
  `product_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FKk58cvrmhry3ibomsp6204wjw`(`orders_id`) USING BTREE,
  INDEX `FKlrqyo8q92lfie02g03gp8l89x`(`product_id`) USING BTREE,
  CONSTRAINT `FKk58cvrmhry3ibomsp6204wjw` FOREIGN KEY (`orders_id`) REFERENCES `orders` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKlrqyo8q92lfie02g03gp8l89x` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for orders_item_seq
-- ----------------------------
DROP TABLE IF EXISTS `orders_item_seq`;
CREATE TABLE `orders_item_seq`  (
  `next_val` bigint(20) NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for orders_seq
-- ----------------------------
DROP TABLE IF EXISTS `orders_seq`;
CREATE TABLE `orders_seq`  (
  `next_val` bigint(20) NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product`  (
  `id` int(11) NOT NULL,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'test' COMMENT '商品描述',
  `is_list` tinyint(1) NULL DEFAULT 0 COMMENT '是否上架',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'test' COMMENT '商品名称',
  `price` decimal(10, 0) NULL DEFAULT 0 COMMENT '商品价格',
  `category_id` int(11) NULL DEFAULT NULL,
  `shop_id` int(11) NULL DEFAULT NULL,
  `rate` decimal(10, 0) NULL DEFAULT 0 COMMENT '商品平均评分',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UK_jmivyxk9rmgysrmsqw15lqr5b`(`name`) USING BTREE,
  INDEX `FK1mtsbur82frn64de7balymq9s`(`category_id`) USING BTREE,
  INDEX `FK94hgg8hlqfqfnt3dag950vm7n`(`shop_id`) USING BTREE,
  CONSTRAINT `FK1mtsbur82frn64de7balymq9s` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK94hgg8hlqfqfnt3dag950vm7n` FOREIGN KEY (`shop_id`) REFERENCES `shop` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for product_seq
-- ----------------------------
DROP TABLE IF EXISTS `product_seq`;
CREATE TABLE `product_seq`  (
  `next_val` bigint(20) NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for rate
-- ----------------------------
DROP TABLE IF EXISTS `rate`;
CREATE TABLE `rate`  (
  `id` int(11) NOT NULL,
  `rate` decimal(10, 0) NULL DEFAULT 0 COMMENT '商品评分',
  `product_id` int(11) NULL DEFAULT NULL,
  `user_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FKrwqxo7w4ksxh4g3qgjdqdl4w5`(`product_id`) USING BTREE,
  INDEX `FKqa3bu60wco5ipgfi8rhmxr6aq`(`user_id`) USING BTREE,
  CONSTRAINT `FKqa3bu60wco5ipgfi8rhmxr6aq` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKrwqxo7w4ksxh4g3qgjdqdl4w5` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for rate_seq
-- ----------------------------
DROP TABLE IF EXISTS `rate_seq`;
CREATE TABLE `rate_seq`  (
  `next_val` bigint(20) NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for shop
-- ----------------------------
DROP TABLE IF EXISTS `shop`;
CREATE TABLE `shop`  (
  `id` int(11) NOT NULL,
  `shop_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'test' COMMENT '店铺名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for shop_seq
-- ----------------------------
DROP TABLE IF EXISTS `shop_seq`;
CREATE TABLE `shop_seq`  (
  `next_val` bigint(20) NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL,
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'test' COMMENT '地址',
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'test@qq.com' COMMENT '邮箱',
  `gender` int(1) NOT NULL COMMENT '性别',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'test' COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'test' COMMENT '密码',
  `phone` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '111111111111' COMMENT '手机号',
  `token` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT 'Token',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UK_gj2fy3dcix7ph7k8684gka40c`(`name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_seq
-- ----------------------------
DROP TABLE IF EXISTS `user_seq`;
CREATE TABLE `user_seq`  (
  `next_val` bigint(20) NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
