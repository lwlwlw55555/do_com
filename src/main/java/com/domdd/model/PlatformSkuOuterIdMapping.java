package com.domdd.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

@Data
@TableName(value = "platform_sku_outer_id_mapping")
@Slf4j
public class PlatformSkuOuterIdMapping implements Serializable {
    private Integer id;

    private String platformGoodsId;

    private String platformSkuId;

    private String platformOuterId;

    private String platformGoodsOuterId;

    private String platformGoodsName;

    private String platformSkuName;

    private Integer isGoodsOnsale;

    private Integer isSkuOnsale;

    private String outerId;

    private String skuName;

    private String barcode;

    private String goodsName;

    private String goodsOuterId;
}