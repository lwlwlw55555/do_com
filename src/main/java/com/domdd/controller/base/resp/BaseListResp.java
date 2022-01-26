package com.domdd.controller.base.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("列表返回结果")
public class BaseListResp<T> {

    @ApiModelProperty("列表")
    private List<T> records;

    @ApiModelProperty("结果总数")
    private Integer total;

    public static <T> BaseListResp<T> result(List<T> records) {
        BaseListResp<T> result = new BaseListResp<>();
        result.records = records;
        result.total = records.size();
        return result;
    }
}
