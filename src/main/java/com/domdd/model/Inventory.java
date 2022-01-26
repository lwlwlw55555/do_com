package com.domdd.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName(value = "inventory")
public class Inventory implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Integer quantity;

    private Integer lockQuantity;

    private Integer availableQuantity;

    private Integer substandardQuantity;

    private Integer saleInOnthewayQuantity;

    private String barcode;

    private String goodsName;

    private String skuName;

    private String brandName;

    private String categoryName;

    private Date createdTime;

    private Date lastUpdatedTime;
}