package com.domdd.ioc.myioc.service;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author lw
 * @date 2022/5/27 2:19 下午
 */
@Data
@AllArgsConstructor
public class BeanDefinition {
    private String beanName;
    private Class beanClass;
}


