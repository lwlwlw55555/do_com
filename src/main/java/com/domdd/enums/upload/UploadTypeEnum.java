package com.domdd.enums.upload;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UploadTypeEnum {
    PURCHASE("采购单"),
    ORDER("销售单"),
    AFTER_SALE("售后单"),
    AFTER_SALE_RETURN("退货单"),
    INVENTORY("库存单");

    private String desc;
}
