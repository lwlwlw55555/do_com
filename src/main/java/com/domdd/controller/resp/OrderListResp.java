package com.domdd.controller.resp;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderListResp {
    private Long orderGoodsId;
    private String orderSn;
    private String platformOrderSn;
    private String goodsName;
    private String platformOrderGoodsSn;
    private String platformOrderStatus;
    private BigDecimal goodsAmount;
    private BigDecimal sellerDiscount;
    private BigDecimal platformDiscount;
    private BigDecimal payAmount;
    private BigDecimal orderAmount;
    private String provinceName;
    private String cityName;
    private String districtName;
    private String shippingAddress;
    private String mobile;
    private String receiveName;
    private Integer goodsNumber;
    private Date payTime;
    private Date shippingTime;
    private String platformSkuId;
    private String styleValue;
    private String platformGoodsId;
    private String sellerNote;
    private String refundStatus;
    private String trackingNumber;
    private String shippingName;
    private String outerId;
    private Integer skuNumber;
}
