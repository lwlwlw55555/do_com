package com.domdd.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName(value = "after_sale_order")
public class AfterSaleOrder implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long orderId;

    private String platformOrderSn;

    private String shopName;

    private String platformOrderGoodsSn;

    private String afterSaleReason;

    private String outerId;

    private String goodsName;

    private String skuName;

    private String platformGoodsId;

    private String platformGoodsName;

    private String styleValue;

    private Integer afterSalesType;

    private Integer afterSalesStatus;

    private Integer goodsNumber;

    private BigDecimal refundAmount;

    private Date refundTime;

    private Date refundCreatedTime;

    private Long refundId;

    private Date createdTime;

    private Date lastUpdatedTime;
}