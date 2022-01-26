package com.domdd.controller.resp;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class AfterSaleOrderResp {
    private String platformOrderSn;
    private String platformOrderGoodsSn;
    private Integer afterSalesType;
    private Integer afterSalesStatus;
    private String afterSaleReason;
    private String outerId;
    private String goodsName;
    private String skuName;
    private String platformGoodsId;
    private String platformGoodsName;
    private String styleValue;
    private String goodsNumber;
    private BigDecimal refundAmount;
    private Date refundCreatedTime;
    private Date refundTime;
    private Long refundId;
}
