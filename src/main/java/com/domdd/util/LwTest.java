package com.domdd.util;

import cn.hutool.core.map.MapUtil;
import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * @author lw
 * @date 2022/3/30 4:15 下午
 */
public class LwTest {

    public static void main(String[] args) {
        ImmutableMap<String, Object> lw = ImmutableMap.of("lw", 1);
//        lw.put("ww", 2);
        lw.builder().put("www", 2).build();
        System.out.println(lw);

        String s = "111";

        Map<Object, Object> build = MapUtil.builder().put("lw", "jj").put("11", 11).put(14, "ww").build();
        build.put(11,4444);
        System.out.println(build);
    }
}
