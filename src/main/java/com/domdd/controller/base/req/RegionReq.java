package com.domdd.controller.base.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RegionReq {

    @ApiModelProperty(value ="省份id", example = "1")
    protected Integer provinceId;

    @ApiModelProperty(hidden=true,value ="省份")
    protected String provinceName;

    @ApiModelProperty(value ="市id",  example = "2")
    protected Integer cityId;

    @ApiModelProperty(hidden=true,value ="市")
    protected String cityName;

    @ApiModelProperty(value ="区id", example = "3")
    protected Integer districtId;

    @ApiModelProperty(hidden=true,value ="区")
    protected String districtName;

    @ApiModelProperty(value ="详细地址", example = "九鼎大厦")
    protected String shippingAddress;

    @ApiModelProperty(hidden=true,value ="市")
    protected Integer pddCityId;

    @ApiModelProperty(hidden=true,value ="市")
    protected Integer pddProvinceId;

    @ApiModelProperty(hidden=true,value ="市")
    protected Integer pddDistrictId;

}
