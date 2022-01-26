package com.domdd.controller.req;

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
    private Integer page = 1;

    @Digits(integer = Long.SIZE, fraction = 0, message = "页面大小必须是正整数")
    @ApiParam(value = "每页记录数", required = true, example = "10")
    @Max(100)
    private Integer pageSize = 100;

    @NotBlank(message = "appkey is null")
    private String appkey;
    @NotBlank(message = "sign is null")
    private String sign;
    @NotBlank(message = "timestamp is null")
    private String timestamp;
    @NotBlank(message = "method is null")
    private String method;
}
