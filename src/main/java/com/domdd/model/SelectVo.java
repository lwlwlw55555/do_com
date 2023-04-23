package com.domdd.model;

import lombok.Data;

@Data
public class SelectVo {
    private String key;
    private String name;
    private Long id;

    public SelectVo() {
    }

    public SelectVo(String key, String name, Long id) {
        this.key = key;
        this.name = name;
        this.id = id;
    }
}
