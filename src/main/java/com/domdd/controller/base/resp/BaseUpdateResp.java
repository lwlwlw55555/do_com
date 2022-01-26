package com.domdd.controller.base.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BaseUpdateResp {

    @ApiModelProperty("成功创建的条数")
    private int insertCount;

    @ApiModelProperty("成功修改的条数")
    private int updateCount;


}
