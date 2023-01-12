package com.domdd.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
public class OpenOrderListReq extends OpenOrderShopReq {
    @Pattern(regexp = "^pay_time|shipping_time$", message = "timeType error")
    @ApiModelProperty(value = "订单状态 付款/发货")
    private String timeType = "pay_time";

    @ApiModelProperty(value = "订单类型 暂未用")
    private String orderType;
}
