package com.domdd.controller.resp;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MddResp<T> {
    private Boolean hasNextPage;
    private Integer page;
    private Integer pageSize;
    private Integer current;
    private List<T> records = new ArrayList<>();
}
