package com.domdd.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class OpenTimeReq extends OpenBaseReq {
    @NotNull(message = "startTime is null")
    @ApiModelProperty(value = "开始时间")
    private Date startTime;
    @NotNull(message = "endTime is null")
    @ApiModelProperty(value = "结束时间")
    private Date endTime;
}
