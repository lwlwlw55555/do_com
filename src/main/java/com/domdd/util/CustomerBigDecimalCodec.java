package com.domdd.util;

import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.DecimalFormat;

public class CustomerBigDecimalCodec implements ObjectSerializer {
    public static final CustomerBigDecimalCodec instance = new CustomerBigDecimalCodec();

    public CustomerBigDecimalCodec() {
    }

    public void write(com.alibaba.fastjson.serializer.JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
        SerializeWriter out = serializer.out;
        if (object == null) {
            out.writeNull();
        } else {
            DecimalFormat decimalFormat = new DecimalFormat("0.00#");
            out.writeString(decimalFormat.format(object));
        }
    }

}