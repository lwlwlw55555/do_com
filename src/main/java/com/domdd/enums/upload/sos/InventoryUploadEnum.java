package com.domdd.enums.upload.sos;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum InventoryUploadEnum {
    barcode,
    goodsName,
    skuName,
    skuId,
    quantity,
    lockQuantity,
    availableQuantity,
    substandardQuantity,
    saleInOnthewayQuantity,
    brandName,
    categoryName,
}
