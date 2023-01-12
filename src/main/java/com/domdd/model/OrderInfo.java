package com.domdd.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@TableName(value = "order_info")
@Slf4j
public class OrderInfo implements Serializable {
    @TableId(type = IdType.AUTO)
    @JSONField(serialize = false)
    @ApiModelProperty(value = "订单id 主键")
    private Long orderId;

    @ApiModelProperty(value = "系统订单号")
    private String orderSn;

    @ApiModelProperty(value = "系统订单商品id 唯一键")
    private Long orderGoodsId;

    @ApiModelProperty(value = "平台订单号")
    private String platformOrderSn;

    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    @ApiModelProperty(value = "商品编码")
    private String platformOrderGoodsSn;

    @ApiModelProperty(value = "平台订单状态 平台订单状态,WAIT_BUYER_PAY(未付款) WAIT_SELLER_SEND_GOODS(待发货) TRADE_FINISHED(交易结束) WAIT_BUYER_CONFIRM_GOODS(已发货) TRADE_CLOSED(平台关闭) TRADE_CLOSED_BY_PLATFORM(平台关闭，未付款)")
    private String platformOrderStatus;

    @ApiModelProperty(value = "收件地址省")
    private String provinceName;

    @ApiModelProperty(value = "收件地址市")
    private String cityName;

    @ApiModelProperty(value = "收件地址区")
    private String districtName;

    @ApiModelProperty(value = "收件地址")
    private String shippingAddress;

    @ApiModelProperty(value = "收件手机号")
    private String mobile;

    @ApiModelProperty(value = "收件人姓名")
    private String receiveName;

    @ApiModelProperty(value = "平台skuId")
    private String platformSkuId;

    @ApiModelProperty(value = "sku规格")
    private String styleValue;

    @ApiModelProperty(value = "平台商品Id")
    private String platformGoodsId;

    @ApiModelProperty(value = "卖家备注")
    private String sellerNote;

    @ApiModelProperty(value = "退货状态 NONE APPLIED RETURNED")
    private String refundStatus;

    @ApiModelProperty(value = "面单号")
    private String trackingNumber;

    @ApiModelProperty(value = "快递名称")
    private String shippingName;

    @ApiModelProperty(value = "sku编码")
    private String outerId;

    @JSONField(serialize = false)
    @ApiModelProperty(value = "系统sku编码")
    private String sysOuterId;

    @ApiModelProperty(value = "发货时间")
    private Date shippingTime;

    @ApiModelProperty(value = "付款时间")
    private Date payTime;

    @ApiModelProperty(value = "店铺名称")
    private String shopName;

    @ApiModelProperty(value = "商品金额")
    private BigDecimal goodsAmount;

    @ApiModelProperty(value = "卖家优惠")
    private BigDecimal sellerDiscount;

    @ApiModelProperty(value = "订单金额")
    private BigDecimal orderAmount;

    @ApiModelProperty(value = "付款金额")
    private BigDecimal payAmount;

    @ApiModelProperty(value = "平台优惠")
    private BigDecimal platformDiscount;

    @ApiModelProperty(value = "商品数量")
    private Integer goodsNumber;

    @ApiModelProperty(value = "sku数量")
    private Integer skuNumber;

    @JSONField(serialize = false)
    @ApiModelProperty(value = "订单类型 SALE销售，CHANGE 换货，RESHIP 补发")
    private String orderType;

    @JSONField(serialize = false)
    @ApiModelProperty(value = "创建时间")
    private Date createdTime;

    @JSONField(serialize = false)
    @ApiModelProperty(value = "更新时间")
    private Date lastUpdatedTime;

    public static void checkParams(List<OrderInfo> records) {
        records.forEach(orderInfo -> {
            if (StringUtils.isNotBlank(orderInfo.getSysOuterId())) {
                orderInfo.setOuterId(orderInfo.getSysOuterId());
            }
        });
    }
}