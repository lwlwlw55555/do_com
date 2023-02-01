package com.domdd.core;

import com.domdd.service.OpenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class BeanSetter {

    @Autowired
    public void setOrderInfoOpenSdkHandler(StringRedisTemplate stringRedisTemplate) {
        OpenService.setStringRedisTemplate(stringRedisTemplate);
    }
}
