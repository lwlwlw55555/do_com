package com.domdd.controller.resp;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class InventoryListResp {
    private String barcode;
    private String goodsName;
    private String skuName;
    @JSONField(serialize = false)
    private Long skuId;
    private Integer quantity = 0;
    private Integer lockQuantity = 0;
    private Integer availableQuantity = 0;
    private Integer substandardQuantity = 0;
    private Integer saleInOnthewayQuantity = 0;
    private String brandName;
    private String categoryName;
}
