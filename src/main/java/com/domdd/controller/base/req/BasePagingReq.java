package com.domdd.controller.base.req;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasePagingReq extends BaseReq {

    @NotNull(message = "page 不能为空")
    @Min(value = 1, message = "页码必须是正整数")
    @Digits(integer = Long.SIZE, fraction = 0, message = "页码必须是正整数")
    @ApiParam(value = "页码", required = true, example = "1")
    private Integer page = 1;

    @NotNull(message = "pageSize 不能为空")
    @Digits(integer = Long.SIZE, fraction = 0, message = "页面大小必须是正整数")
    @ApiParam(value = "每页记录数", required = true, example = "10")
    private Integer pageSize = 1000;

    @ApiModelProperty(hidden = true)
    @JSONField(serialize = false)
    private Integer getOffset(){
        return (this.page -1) * pageSize;
    }
}
