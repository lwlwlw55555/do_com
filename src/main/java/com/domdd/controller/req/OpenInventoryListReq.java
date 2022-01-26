package com.domdd.controller.req;

import io.swagger.annotations.ApiParam;
import lombok.Data;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;

@Data
public class OpenInventoryListReq extends OpenBaseReq {

    @Digits(integer = Long.SIZE, fraction = 0, message = "页面大小必须是正整数")
    @ApiParam(value = "每页记录数", required = true, example = "10")
    @Max(100)
    private Integer pageSize = 20;
}
