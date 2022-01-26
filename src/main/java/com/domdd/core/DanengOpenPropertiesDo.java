package com.domdd.core;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Data
@Slf4j
public class DanengOpenPropertiesDo {
    public static String appkey = "daneng-mdd";
    public static String signSecret = "mdd-daneng";
    public static String url = "https://erpapi.pinshangyin.com/open/";
//    测试环境 https://omstestapi.pinshangyin.com/open/order/list
//    正式环境 https://erpapi.pinshangyin.com/open/order/list
    public void checkNull() {
        if (StringUtils.isBlank(DanengOpenPropertiesDo.appkey)) {
            DanengOpenPropertiesDo.appkey = "daneng-mdd";
            DanengOpenPropertiesDo.signSecret = "mdd-daneng";
            DanengOpenPropertiesDo.url = "https://erpapi.pinshangyin.com/open/";
        }
    }
}
