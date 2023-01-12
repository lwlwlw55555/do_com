package com.domdd.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName(value = "inventory")
public class Inventory implements Serializable {

    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "库存id 主键")
    private Long id;

    @ApiModelProperty(value = "库存数量")
    private Integer quantity;

    @ApiModelProperty(value = "锁定的库存")
    private Integer lockQuantity;

    @ApiModelProperty(value = "可用库存")
    private Integer availableQuantity;

    @ApiModelProperty(value = "次品库存")
    private Integer substandardQuantity;

    @ApiModelProperty(value = "销退在途")
    private Integer saleInOnthewayQuantity;

    @ApiModelProperty(value = "条码")
    private String barcode;

    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    @ApiModelProperty(value = "sku名称")
    private String skuName;

    @ApiModelProperty(value = "品牌")
    private String brandName;

    @ApiModelProperty(value = "商品分类")
    private String categoryName;

    @ApiModelProperty(value = "创建时间")
    private Date createdTime;

    @ApiModelProperty(value = "更新时间")
    private Date lastUpdatedTime;
}