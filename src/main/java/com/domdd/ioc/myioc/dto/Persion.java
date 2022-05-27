package com.domdd.ioc.myioc.dto;

import com.domdd.ioc.myioc.annotation.MyAutowired;
import com.domdd.ioc.myioc.annotation.MyComponent;
import lombok.Data;

/**
 * @author lw
 * @date 2022/5/27 2:17 下午
 */
@Data
@MyComponent()
public class Persion {
    @MyAutowired
    //@MyQualifier("account")
    private Account account;
}

