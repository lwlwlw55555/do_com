package com.domdd.controller.base;

import cn.hutool.core.util.StrUtil;
import com.domdd.controller.base.resp.BaseResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.ValidationException;

@Slf4j
@ControllerAdvice
@RestController
public class BaseController {
    /**
     * 处理校验结果
     *
     * @param result BindingResult
     */
    public static void validate(BindingResult result) {
        if (result.hasErrors() && result.getFieldError() != null) {
            String errorMsg = result.getFieldError().getDefaultMessage();
            throw new ValidationException(errorMsg);
        }
    }

    @ExceptionHandler
    protected BaseResp<String> exceptionHandler(Exception ex) {
        if (ex instanceof HttpClientErrorException) {
            if (((HttpClientErrorException) ex).getStatusCode() == HttpStatus.NOT_FOUND) {
                throw (HttpClientErrorException) ex;
            }
            return BaseResp.reminderError(ex.getMessage());
        }else if (ex instanceof ValidationException || ex instanceof IllegalArgumentException) {
            String message = ex.getMessage();
            log.error(message);
            return BaseResp.invalidParam(StrUtil.split(message, "|")[0]);
        }

        log.error(" [BaseControllerError] ", ex);
        ex.printStackTrace();
        return BaseResp.exception(ex.getLocalizedMessage());
    }
}
