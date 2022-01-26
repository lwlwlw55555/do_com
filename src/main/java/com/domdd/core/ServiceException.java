package com.domdd.core;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@EqualsAndHashCode(callSuper = true)
public class ServiceException extends RuntimeException {

    public ServiceException() {}

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(CharSequence template, Object... params) {
        super(StrUtil.format(template, params));
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
