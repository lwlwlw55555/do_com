package com.domdd.controller.req;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class OpenTimeReq extends OpenBaseReq {
    @NotNull(message = "startTime is null")
    private Date startTime;
    @NotNull(message = "endTime is null")
    private Date endTime;
}
