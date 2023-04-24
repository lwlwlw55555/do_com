package com.domdd.enums.upload;

import com.domdd.util.ObjectFieldHandler;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@AllArgsConstructor
@Getter
public enum UploadTypeEnum {
    PURCHASE("采购单", "采购入库单查询"),
    ORDER("销售单(出库单)", "销售订单查询"),
    AFTER_SALE("售后单(仅退款)", "退款单查询"),
    AFTER_SALE_RETURN("退货单(退回)", "退货单查询"),
    INVENTORY("库存", "库存表查询");

    private String desc;
    private String erpName;

    public static UploadTypeEnum getBySheetName(String sheetName) {
        return ObjectFieldHandler.generateFindAnyOptional(UploadTypeEnum.values(), e -> Objects.equals(e.erpName, sheetName));
    }
}
