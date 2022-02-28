package com.domdd.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName(value = "order_info")
@Slf4j
public class OrderInfo implements Serializable {
    @TableId(type = IdType.AUTO)
    @JSONField(serialize = false)
    private Long orderId;

    private String orderSn;

    private Long orderGoodsId;

    private String platformOrderSn;

    private String goodsName;

    private String platformOrderGoodsSn;

    private String platformOrderStatus;

    private String provinceName;

    private String cityName;

    private String districtName;

    private String shippingAddress;

    private String mobile;

    private String receiveName;

    private String platformSkuId;

    private String styleValue;

    private String platformGoodsId;

    private String sellerNote;

    private String refundStatus;

    private String trackingNumber;

    private String shippingName;

    private String outerId;

    private Date shippingTime;

    private Date payTime;

    private String shopName;

    private BigDecimal goodsAmount;

    private BigDecimal sellerDiscount;

    private BigDecimal orderAmount;

    private BigDecimal payAmount;

    private BigDecimal platformDiscount;

    private Integer goodsNumber;

    private Integer skuNumber;

    @JSONField(serialize = false)
    private Date createdTime;

    @JSONField(serialize = false)
    private Date lastUpdatedTime;
}