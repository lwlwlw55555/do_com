package com.domdd.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class OpenOrderShopReq extends OpenTimeReq{
    @NotBlank(message = "shopName is null")
    @ApiModelProperty(value = "店铺名称")
    private String shopName;
}
