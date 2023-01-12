package com.domdd.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName(value = "purchase_in_order")
public class PurchaseInOrder implements Serializable {
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "入库单id 主键")
    private Long orderId;

    @ApiModelProperty(value = "出库订单号")
    private String inventoryOrderSn;

    @ApiModelProperty(value = "系统订单号")
    private String orderSn;

    @ApiModelProperty(value = "供应商")
    private String supplier;

    @ApiModelProperty(value = "仓库名称")
    private String warehouseName;

    @ApiModelProperty(value = "订单状态 WAIT_CHECK CHECKED CLOSED_MANUAL DONE")
    private String orderStatus;

    @ApiModelProperty(value = "商品类型 正常/残损 COMMON/DEFECTIVE")
    private String goodsSubType;

    @ApiModelProperty(value = "sku编码")
    private String outerId;

    @ApiModelProperty(value = "条码")
    private String barcode;

    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    @ApiModelProperty(value = "sku名称")
    private String skuName;

    @ApiModelProperty(value = "入库批次，不等于生产批次")
    private String productBatchCode;

    @ApiModelProperty(value = "商品数量")
    private Integer goodsNumber;

    @ApiModelProperty(value = "sku数量")
    private Integer quantity;

    @ApiModelProperty(value = "生产日期")
    private Date productDate;

    @ApiModelProperty(value = "过期日期")
    private Date expireDate;

    @ApiModelProperty(value = "入库时间")
    private Date inventoryInTime;

    @ApiModelProperty(value = "创建时间")
    private Date createdTime;

    @ApiModelProperty(value = "更新时间")
    private Date lastUpdatedTime;
}