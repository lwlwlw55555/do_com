package com.domdd.controller.base.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BaseResp<T> {

    @ApiModelProperty(value = "状态码", position = 1)
    private Integer code;

    @ApiModelProperty(value = "返回消息", position = 2)
    private String message;

    @ApiModelProperty(value = "数据", position = 3)
    private T data;

    /**
     * 成功
     */
    private static final int SUCCESS = 0;
    /**
     * 系统异常
     */
    private static final int EXCEPTION = -1;
    /**
     * 未登录
     */
    private static final int NOT_LOGIN = 1;
    /**
     * 参数错误
     */
    private static final int INVALID_PARAM = 2;
    /**
     * 业务出错
     */
    public static final int SERVICE_ERROR = 3;
    /**
     * 未选择店铺
     */
    public static final int NO_SHOP = 4;
    /** 
     * 需要提醒的错误 
     */
    public static final int REMINDER_ERROR = 5;
    /**
     * 有过期店铺
     */
    public static final int SHOP_NOT_ENABLED = 6;

    public static BaseResp<?> success() {
        return new BaseResp<>(0, "SUCCESS", null);
    }

    public static <T> BaseResp<T> success(T data) {
        return new BaseResp<>(0, "SUCCESS", data);
    }

    public static <T> BaseResp<T> exception(T data) {
        return new BaseResp<>(BaseResp.EXCEPTION, "系统繁忙，请稍后再试", data);
    }

    public static <T> BaseResp<T> notLogin() {
        return new BaseResp<>(BaseResp.NOT_LOGIN, "您尚未登录", null);
    }

    public static <T> BaseResp<T> invalidParam(String message) {
        return new BaseResp<>(BaseResp.INVALID_PARAM, message, null);
    }

    public static <T> BaseResp<T> reminderError(String message) {
        return new BaseResp<>(BaseResp.REMINDER_ERROR, message, null);
    }
    
    public static <T> BaseResp<T> serviceError(String message) {
        return new BaseResp<>(BaseResp.SERVICE_ERROR, message, null);
    }

    public static <T> BaseResp<T> serviceError(String message, T data) {  
        return new BaseResp<>(BaseResp.SERVICE_ERROR, message, data);
    }

    public static <T> BaseResp<T> result(int code, String message) {
        return new BaseResp<>(code, message, null);
    }

    public static <T> BaseResp<T> result(int code, String message, T data) {
        return new BaseResp<>(code, message, data);
    }
}
