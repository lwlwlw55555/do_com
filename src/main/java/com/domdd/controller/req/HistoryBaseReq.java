package com.domdd.controller.req;

import lombok.Data;

import java.util.Date;

@Data
public class HistoryBaseReq {
    private Date startDate;
    private Date endDate;
}
