package com.domdd.controller.req;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
public class OpenOrderListReq extends OpenOrderShopReq {
    @Pattern(regexp = "^pay_time|shipping_time$", message = "timeType error")
    private String timeType = "pay_time";

    private String orderType;
}
