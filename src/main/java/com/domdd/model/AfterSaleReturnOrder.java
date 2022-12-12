package com.domdd.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName(value = "after_sale_return_order")
public class AfterSaleReturnOrder implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long orderId;

    private String platformOrderSn;

    private String platformOrderGoodsSn;

    private String afterSaleReason;

    private String shopName;

    private String shippingName;

    private String trackingNumber;

    private String outerId;

    private String goodsName;

    private String skuName;

    private String platformGoodsId;

    private String platformGoodsName;

    private String styleValue;

    private String goodsSubType;

    private Integer afterSalesType;

    private Integer afterSalesStatus;

    private Integer warehouseId;

    private String warehouseName;

    private Integer goodsNumber;

    private Integer quantity;

    private Date refundCreatedTime;

    private Date returnOrderCreatedTime;

    private Long refundId;

    private Date createdTime;

    private Date lastUpdatedTime;
}