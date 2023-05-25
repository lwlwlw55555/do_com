package com.domdd.enums.upload;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UploadShopNameEnum {
    NYN("诺优能官方旗舰店", "诺优能"),
    ATM("爱他美旗舰店", "爱他美");

    private String desc;
    private String alias;
}
