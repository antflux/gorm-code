/*
 * Copyright (c) 2020. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package org.fruip.helper.sql;

import org.junit.Test;

import static org.junit.Assert.*;

public class SqlParserTest {

    @Test
    public void TestExecute() {
        String sql = "CREATE TABLE IF NOT EXISTS `orderForm` (\n" +
                     "  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,\n" +
                     "  `userId` int(11) DEFAULT NULL COMMENT '用户id',\n" +
                     "  `totalPrice` decimal(15,2) unsigned DEFAULT NULL COMMENT '总价',\n" +
                     "  `payTime` int(11) DEFAULT '0' COMMENT '支付时间',\n" +
                     "  `payStatus` enum('支付成功','取消','未支付') CHARACTER SET utf8 DEFAULT '未支付' COMMENT '支付状态',\n" +
                     "  `createTime` int(11) DEFAULT NULL COMMENT '生成时间',\n" +
                     "  `cashierOid` varchar(60) CHARACTER SET utf8 DEFAULT NULL COMMENT '收银员oid',\n" +
                     "  `couponId` int(11) NOT NULL DEFAULT '0' COMMENT '优惠券赠送id',\n" +
                     "  `orderNum` varchar(60) CHARACTER SET utf8 DEFAULT NULL COMMENT '备用纸质订单id',\n" +
                     "  `memo` varchar(1024) CHARACTER SET utf8 DEFAULT NULL COMMENT '备注',\n" +
                     "  `actualPrice` decimal(15,2) unsigned DEFAULT '0.00' COMMENT '应收',\n" +
                     "  `orderStatus` enum('取消','订单完成','备货','已支付','待支付','已收货','保证金不足','退货申请中','已导出') CHARACTER SET utf8 DEFAULT NULL COMMENT '订单状态',\n" +
                     "  `orderType` enum('购物商城','福利商城','会员商城','线下订单','线上订单','线上服务订单','线下服务订单','团购订单') CHARACTER SET utf8 DEFAULT '线下订单' COMMENT '订单类型',\n" +
                     "  `corpId` int(11) DEFAULT NULL COMMENT '机构id',\n" +
                     "  `payMode` enum('现金','会员卡','线上账户','现金and会员卡','现金and线上账户','会员卡and线上账户','现金and会员卡and线上账户','积分and其他','积分and现金','商圈备付金','会员卡and商圈备付金','线上账户and商圈备付金','会员卡and线上账户and商圈备付金','机构代付','代付','积分','汇仓券','线上直付') CHARACTER SET utf8 DEFAULT '线上账户' COMMENT '付款方式',\n" +
                     "  `payMemo` varchar(128) CHARACTER SET utf8 DEFAULT NULL COMMENT '付款备注',\n" +
                     "  `corpAuditStatus` enum('已清算','未清算') CHARACTER SET utf8 DEFAULT '未清算' COMMENT '店间清算状态',\n" +
                     "  `membAuditStatus` enum('已清算','未清算') CHARACTER SET utf8 DEFAULT '未清算' COMMENT '会员清算状态',\n" +
                     "  `custResource` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '顾客来源',\n" +
                     "  `receivId` int(11) DEFAULT NULL COMMENT '收货人id',\n" +
                     "  `addrId` int(11) DEFAULT NULL COMMENT '收货地址id',\n" +
                     "  `deliverTime` int(11) DEFAULT '0' COMMENT '发货时间',\n" +
                     "  `advertId` varchar(128) CHARACTER SET utf8 DEFAULT NULL COMMENT '推文id,以逗号分隔的本订单推文id列表',\n" +
                     "  `bond` decimal(12,2) DEFAULT '0.00' COMMENT '保证金,继承商品时,给上级店的保证金',\n" +
                     "  `unionId` int(11) DEFAULT '0' COMMENT '商圈id,如果是商圈订单,这里标注商圈id',\n" +
                     "  `unionDiscount` decimal(11,2) DEFAULT '0.00' COMMENT '商圈折扣,本笔订单执行的商圈折扣',\n" +
                     "  `groupId` int(11) unsigned DEFAULT '0' COMMENT '团购id',\n" +
                     "  `freight` decimal(9,2) DEFAULT '0.00' COMMENT '运费',\n" +
                     "  `payId` int(11) DEFAULT '0',\n" +
                     "  `payM` int(1) DEFAULT '0' COMMENT '0 - 普通 1 - 分账',\n" +
                     "  `splitOpenId` varchar(64) COLLATE utf8mb4_bin DEFAULT '0' COMMENT '分账id',\n" +
                     "  `splitAmount` decimal(12,2) DEFAULT '0.00' COMMENT '分账金额',\n" +
                     "  `splitRun` int(1) DEFAULT '0' COMMENT '1 - 执行分账',\n" +
                     "  `outputFlag` int(1) DEFAULT '0' COMMENT '0 - 未导出 1 - 导出',\n" +
                     "  PRIMARY KEY (`id`),\n" +
                     "  KEY `corpId` (`corpId`),\n" +
                     "  KEY `groupId` (`groupId`),\n" +
                     "  KEY `userId` (`userId`)\n" +
                     ") ENGINE=InnoDB AUTO_INCREMENT=2608589 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin";
        SqlParser sqlParser = new SqlParser();
        String expect = "package model\n" +
                        "\n" +
                        "type OrderForm struct {\n" +
                        "\tId              uint32  `gorm:\"type:INT(11) UNSIGNED;column:id;AUTO_INCREMENT;NOT NULL\"`\n" +
                        "\tUserId          int32   `gorm:\"type:INT(11);column:userId;\"`\n" +
                        "\tTotalPrice      float64 `gorm:\"type:DECIMAL(15, 2) UNSIGNED;column:totalPrice;\"`\n" +
                        "\tPayTime         int32   `gorm:\"type:INT(11);column:payTime;\"`\n" +
                        "\tPayStatus       string  `gorm:\"type:ENUM('支付成功', '取消', '未支付');column:payStatus;\"`\n" +
                        "\tCreateTime      int32   `gorm:\"type:INT(11);column:createTime;\"`\n" +
                        "\tCashierOid      string  `gorm:\"type:VARCHAR(60);column:cashierOid;\"`\n" +
                        "\tCouponId        int32   `gorm:\"type:INT(11);column:couponId;NOT NULL\"`\n" +
                        "\tOrderNum        string  `gorm:\"type:VARCHAR(60);column:orderNum;\"`\n" +
                        "\tMemo            string  `gorm:\"type:VARCHAR(1024);column:memo;\"`\n" +
                        "\tActualPrice     float64 `gorm:\"type:DECIMAL(15, 2) UNSIGNED;column:actualPrice;\"`\n" +
                        "\tOrderStatus     string  `gorm:\"type:ENUM('取消', '订单完成', '备货', '已支付', '待支付', '已收货', '保证金不足', '退货申请中', '已导出');column:orderStatus;\"`\n" +
                        "\tOrderType       string  `gorm:\"type:ENUM('购物商城', '福利商城', '会员商城', '线下订单', '线上订单', '线上服务订单', '线下服务订单', '团购订单');column:orderType;\"`\n" +
                        "\tCorpId          int32   `gorm:\"type:INT(11);column:corpId;\"`\n" +
                        "\tPayMode         string  `gorm:\"type:ENUM('现金', '会员卡', '线上账户', '现金AND会员卡', '现金AND线上账户', '会员卡AND线上账户', '现金AND会员卡AND线上账户', '积分AND其他', '积分AND现金', '商圈备付金', '会员卡AND商圈备付金', '线上账户AND商圈备付金', '会员卡AND线上账户AND商圈备付金', '机构代付', '代付', '积分', '汇仓券', '线上直付');column:payMode;\"`\n" +
                        "\tPayMemo         string  `gorm:\"type:VARCHAR(128);column:payMemo;\"`\n" +
                        "\tCorpAuditStatus string  `gorm:\"type:ENUM('已清算', '未清算');column:corpAuditStatus;\"`\n" +
                        "\tMembAuditStatus string  `gorm:\"type:ENUM('已清算', '未清算');column:membAuditStatus;\"`\n" +
                        "\tCustResource    uint32  `gorm:\"type:INT(11) UNSIGNED;column:custResource;NOT NULL\"`\n" +
                        "\tReceivId        int32   `gorm:\"type:INT(11);column:receivId;\"`\n" +
                        "\tAddrId          int32   `gorm:\"type:INT(11);column:addrId;\"`\n" +
                        "\tDeliverTime     int32   `gorm:\"type:INT(11);column:deliverTime;\"`\n" +
                        "\tAdvertId        string  `gorm:\"type:VARCHAR(128);column:advertId;\"`\n" +
                        "\tBond            float64 `gorm:\"type:DECIMAL(12, 2);column:bond;\"`\n" +
                        "\tUnionId         int32   `gorm:\"type:INT(11);column:unionId;\"`\n" +
                        "\tUnionDiscount   float64 `gorm:\"type:DECIMAL(11, 2);column:unionDiscount;\"`\n" +
                        "\tGroupId         uint32  `gorm:\"type:INT(11) UNSIGNED;column:groupId;\"`\n" +
                        "\tFreight         float64 `gorm:\"type:DECIMAL(9, 2);column:freight;\"`\n" +
                        "\tPayId           int32   `gorm:\"type:INT(11);column:payId;\"`\n" +
                        "\tPayM            int32   `gorm:\"type:INT(1);column:payM;\"`\n" +
                        "\tSplitOpenId     string  `gorm:\"type:VARCHAR(64);column:splitOpenId;\"`\n" +
                        "\tSplitAmount     float64 `gorm:\"type:DECIMAL(12, 2);column:splitAmount;\"`\n" +
                        "\tSplitRun        int32   `gorm:\"type:INT(1);column:splitRun;\"`\n" +
                        "\tOutputFlag      int32   `gorm:\"type:INT(1);column:outputFlag;\"`\n" +
                        "}\n";
        String actual = sqlParser.Execute(sql);
        assertEquals(expect, actual);
    }

    @Test
    public void TestIssue16_Can_not_work_with_goland_windows_version() {
        String sql = "CREATE TABLE group_job (\n" +
                "g_id int(10) unsigned NOT NULL AUTO_INCREMENT,\n" +
                "g_type tinyint(4) NOT NULL DEFAULT '1' COMMENT '1:TYPEA; 2:TYPEB',\n" +
                "g_valid_start timestamp NOT NULL DEFAULT '2001-01-01 00:00:00',\n" +
                "g_message text NOT NULL COMMENT 'Template, json format',\n" +
                "g_job_status tinyint(4) NOT NULL DEFAULT '1' COMMENT '0:invalid,1:valid',\n" +
                "g_a_id varchar(20) NOT NULL DEFAULT '' COMMENT 'account_id',\n" +
                "g_operator_id int(10) unsigned NOT NULL DEFAULT '0',\n" +
                "g_version tinyint(255) unsigned NOT NULL DEFAULT '1',\n" +
                "g_created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,\n" +
                "g_updated_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,\n" +
                "PRIMARY KEY (g_id) USING BTREE,\n" +
                "KEY g_type (g_type) USING BTREE\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;";
        SqlParser sqlParser = new SqlParser();
        String actual = sqlParser.Execute(sql);
        String expect = "package model\n" +
                "\n" +
                "type GroupJob struct {\n" +
                "\tGId         uint32    `gorm:\"type:INT(10) UNSIGNED;PRIMARY_KEY;AUTO_INCREMENT;NOT NULL\"`\n" +
                "\tGType       int8      `gorm:\"type:TINYINT(4);NOT NULL\"`\n" +
                "\tGValidStart time.Time `gorm:\"type:TIMESTAMP;NOT NULL\"`\n" +
                "\tGMessage    string    `gorm:\"type:TEXT;NOT NULL\"`\n" +
                "\tGJobStatus  int8      `gorm:\"type:TINYINT(4);NOT NULL\"`\n" +
                "\tGAId        string    `gorm:\"type:VARCHAR(20);NOT NULL\"`\n" +
                "\tGOperatorId uint32    `gorm:\"type:INT(10) UNSIGNED;NOT NULL\"`\n" +
                "\tGVersion    uint8     `gorm:\"type:TINYINT(255) UNSIGNED;NOT NULL\"`\n" +
                "\tGCreatedAt  time.Time `gorm:\"type:TIMESTAMP;NOT NULL\"`\n" +
                "\tGUpdatedAt  time.Time `gorm:\"type:TIMESTAMP;NOT NULL\"`\n" +
                "}\n";
        //assertEquals(expect, actual);
    }
}