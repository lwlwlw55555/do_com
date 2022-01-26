package com.domdd.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName(value = "purchase_in_order")
public class PurchaseInOrder implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long orderId;

    private String inventoryOrderSn;

    private String orderSn;

    private String supplier;

    private String warehouseName;

    private String orderStatus;

    private String goodsSubType;

    private String outerId;

    private String barcode;

    private String goodsName;

    private String skuName;

    private String productBatchCode;

    private Integer goodsNumber;

    private Integer quantity;

    private Date productDate;

    private Date expireDate;

    private Date inventoryInTime;

    private Date createdTime;

    private Date lastUpdatedTime;
}