package com.domdd.controller.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class OpenOrderShopReq extends OpenTimeReq{
    @NotBlank(message = "shopName is null")
    private String shopName;
}
