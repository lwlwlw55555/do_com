package com.domdd.controller.req;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
public class OpenBaseReq {
    @Min(value = 1, message = "页码必须是正整数")
    @Digits(integer = Long.SIZE, fraction = 0, message = "页码必须是正整数")
    @ApiParam(value = "页码", required = true, example = "1")
    @ApiModelProperty(value = "页码")
    private Integer page = 1;

    @Digits(integer = Long.SIZE, fraction = 0, message = "页面大小必须是正整数")
    @ApiParam(value = "每页记录数", required = true, example = "10")
    @ApiModelProperty(value = "每页记录数")
    @Max(100)
    private Integer pageSize = 100;

    @NotBlank(message = "appkey is null")
    @ApiModelProperty("appkey")
    private String appkey;
    @NotBlank(message = "sign is null")
    @ApiModelProperty("签名")
    private String sign;
    @NotBlank(message = "timestamp is null")
    @ApiModelProperty("时间戳")
    private String timestamp;
    @NotBlank(message = "method is null")
    @ApiModelProperty("接口类型 order.list purchaseInOrder.list afterSaleOrder.list afterSaleReturnOrder.list inventory.list")
    private String method;
}
