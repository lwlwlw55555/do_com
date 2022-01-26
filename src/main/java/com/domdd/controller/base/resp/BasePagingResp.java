package com.domdd.controller.base.resp;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@ApiModel("分页列表返回结果")
@NoArgsConstructor
public class BasePagingResp<T> {

    @ApiModelProperty(value = "总记录数", example = "100")
    private Long total;

    @ApiModelProperty(value = "总页数", example = "10")
    private Integer totalPage;

    @ApiModelProperty(value = "页码", example = "1")
    private Integer page;

    @ApiModelProperty(value = "每页记录数", example = "10")
    private Integer pageSize;

    @ApiModelProperty(value = "是否有下一页", example = "true")
    private Boolean hasNextPage;

    @ApiModelProperty("列表")
    private List<T> records;

    public BasePagingResp(IPage<T> iPage) {
        this.total =  iPage.getTotal();
        this.totalPage = (int) iPage.getPages();
        this.page = (int) iPage.getCurrent();
        this.pageSize = (int) iPage.getSize();
        this.records = iPage.getRecords();

        this.hasNextPage = (page - 1) * pageSize + records.size() < total;
    }

    public BasePagingResp(Long total, List<T> records) {
        this.total =  total;
        this.records = records;
    }

}
