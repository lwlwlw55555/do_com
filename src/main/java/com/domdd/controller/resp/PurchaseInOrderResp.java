package com.domdd.controller.resp;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Date;

@Data
public class PurchaseInOrderResp {
    private String inventoryOrderSn;
    private String orderSn;
    private Date purchaseDate;
    private String supplier;
    private String warehouseName;
    private String shopName;
    private String orderStatus;
    private Integer goodsNumber;
    private String goodsSubType;
    @JSONField(serialize = false)
    private Integer supplierId;
    @JSONField(serialize = false)
    private Integer warehouseId;
    private Integer quantity;
    private String outerId;
    private String barcode;
    private String goodsName;
    private String skuName;
    private String productBatchCode;
    private Date productDate;
    private Date expireDate;
    private Date inventoryInTime;
}
