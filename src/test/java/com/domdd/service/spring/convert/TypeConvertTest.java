package com.domdd.service.spring.convert;

import cn.hutool.core.convert.impl.ArrayConverter;
import com.domdd.service.fit.Builder;
import com.domdd.util.ObjectFieldHandler;
import lombok.Data;
import org.springframework.beans.SimpleTypeConverter;
import org.springframework.beans.TypeConverter;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import javax.lang.model.type.ArrayType;
import java.util.Arrays;

/**
 * @see TypeConverter
 * @see "https://blog.csdn.net/qq_17821591/article/details/117400617"
 */

public class TypeConvertTest {


    public static void main(String[] args) {
        TypeConverter converter = new SimpleTypeConverter();
//        TypeConverterDelegate typeConverterDelegate = new TypeConverterDelegate();


        String s = converter.convertIfNecessary("1", String.class);

        System.out.println(s);

        // 这样会报错！ClassUtils.isAssignableValue 会校验value和type是否为一个类型 继承/实现接口等关系可
        // if (!ClassUtils.isAssignableValue(requiredType, convertedValue)) {
        // A a = Builder.of(A::new).with(A::setName, "1").build();
        // B b = Builder.of(B::new).with(B::setName, "1").build();

//        Object a = Builder.of(A::new).with(A::setName, "1").build();
        Object b = Builder.of(B::new).with(B::setName, "1").build();


        System.out.println(ClassUtils.isAssignableValue(A.class, b));

        System.out.println(ClassUtils.isAssignableValue(B.class, b));


//        B b1 = converter.convertIfNecessary(a, B.class);
        A a1 = converter.convertIfNecessary(b, A.class);

        System.out.println(a1);

        String s1 = StringUtils.collectionToCommaDelimitedString(Arrays.asList("1", "2", 3));
        System.out.println(s1);

//        ConvertHandler


    }

    @Data
    public static class A {
        String name;
    }

    @Data
    public static class B extends A {
        String name;
    }


//    public static TypeConverter getTypeConverter() {
//        TypeConverter customConverter = new SimpleTypeConverter();
//        if (customConverter != null) {
//            return customConverter;
//        } else {
//            // Build default TypeConverter, registering custom editors.
//            SimpleTypeConverter typeConverter = new SimpleTypeConverter();
//            typeConverter.setConversionService(getConversionService());
//            registerCustomEditors(typeConverter);
//            return typeConverter;
//        }
//    }
}
