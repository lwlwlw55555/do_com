package com.domdd.controller.base.req;

import lombok.Data;

@Data
public class SignReq<T> {

    private String appKey;

    private Long timestamp;

    private String sign;

    private T param;
}
