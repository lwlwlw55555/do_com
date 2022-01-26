package com.domdd.controller.resp;

import lombok.Data;

import java.util.Date;

@Data
public class AfterSaleReturnOrderResp {
    private String platformOrderSn;
    private String platformOrderGoodsSn;
    private Integer afterSalesType;
    private Integer afterSalesStatus;
    private String afterSaleReason;
    private String shippingName;
    private String trackingNumber;
    private Integer warehouseId;
    private String outerId;
    private String goodsName;
    private String skuName;
    private String platformGoodsId;
    private String platformGoodsName;
    private String styleValue;
    private String goodsSubType;
    private Integer goodsNumber;
    private Integer quantity;
    private Date refundCreatedTime;
    private Date createdTime;
    private Long refundId;
}
