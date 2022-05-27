package com.domdd.ioc.myioc.dto;

import com.domdd.ioc.myioc.annotation.MyComponent;
import com.domdd.ioc.myioc.annotation.MyValue;
import lombok.Data;

/**
 * @author lw
 * @date 2022/5/27 2:17 下午
 */
@Data
@MyComponent("account")
public class Account {
    @MyValue("1")
    private Integer Id;
    @MyValue("Taoger")
    private String name;
    @MyValue("20")
    private Integer age;
}

