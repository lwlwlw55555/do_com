package com.domdd.util;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.math.BigDecimal.ROUND_DOWN;
import static java.math.BigDecimal.ROUND_HALF_UP;

public class ObjectFieldHandler {
    public static final String[] IGNORE_COPY_FIELDS = new String[]{"createdTime", "lastUpdatedTime"};
    public static String FH = "FH";
    public static String DATE_PATTEN = "yyMMddHHmmssSSS";

    public static void copyProperties(Object source, Object target) {
        BeanUtils.copyProperties(source, target, IGNORE_COPY_FIELDS);
    }

    public static void copyProperties(Object source, Object target, String[] ignoreArr) {
        BeanUtils.copyProperties(source, target, ignoreArr);
    }

    public static BigDecimal getDecimal(BigDecimal decimal) {
        if (decimal == null) return new BigDecimal(0);
        return decimal.setScale(2, BigDecimal.ROUND_UP);
    }

    public static BigDecimal getPercentDecimal(BigDecimal decimal, double percent) {
        if (decimal == null) return new BigDecimal(0);
        return getDecimal(decimal.multiply((new BigDecimal(percent))));
    }

    public static BigDecimal getPercentDecimal(int total, int part) {
        return getDecimal(new BigDecimal(part).divide(new BigDecimal(total), 2, ROUND_HALF_UP));
    }

    public static BigDecimal getPercentDecimal(Long total, Long part) {
        if (total == 0 && part == 0){
            return new BigDecimal(1);
        }
        return getDecimal(new BigDecimal(part).divide(new BigDecimal(total), 2, ROUND_HALF_UP));
    }

    public static BigDecimal getPercent4Decimal(int total, int part) {
        return new BigDecimal(part).divide(new BigDecimal(total), 4, ROUND_DOWN);
    }

    public static BigDecimal getPercentDecimal(BigDecimal decimal, int total, int part) {
        if (decimal == null) return new BigDecimal(0);
        return decimal.multiply(new BigDecimal(part)).divide(new BigDecimal(total), 2, ROUND_HALF_UP);
    }

    public static BigDecimal getLastPartDecimal(BigDecimal decimal, int total, int otherPart) {
        if (decimal == null) return new BigDecimal(0);
        return decimal.subtract(decimal.multiply(new BigDecimal(otherPart)).divide(new BigDecimal(total), 2, ROUND_HALF_UP));
    }

    public static String joinString(String preStr, String addStr) {
        try {
            ArrayList<String> list = new ArrayList<>(Arrays.asList(preStr.split(",")));
            list.add(addStr);
            return String.join(",", generateSortListByList(generateFindAllOptional(list, StringUtils::isNotBlank)));
        } catch (Exception ignore) {
        }
        return !StringUtils.isBlank(preStr) ? preStr + "," + addStr : addStr;
    }

    public static <V, T> Map<V, T> generateMapByObj(List<T> objList, Function<? super T, V> keyExtractor) {
        if (objList == null || objList.size() == 0) {
            return new HashMap<>();
        }
        return objList.stream().collect(Collectors.toMap(keyExtractor, x -> x, (x, y) -> x));
    }

    public static <T, V> List<V> generateDistinctListByObj(List<T> objList, Function<? super T, V> keyExtractor) {
        if (objList == null || objList.size() == 0) {
            return new ArrayList<>();
        }
        return objList.stream().map(keyExtractor).collect(Collectors.toList()).stream().distinct().collect(Collectors.toList());
    }

    public static <T, V> List<V> generateListByObj(List<T> objList, Function<? super T, V> keyExtractor) {
        if (objList == null || objList.size() == 0) {
            return new ArrayList<>();
        }
        return objList.stream().map(keyExtractor).collect(Collectors.toList());
    }

    public static <T> Date generateMinDateByObj(List<T> objList, Function<? super T, Date> keyExtractor) {
        try {
            return objList.stream().map(keyExtractor).distinct().min(ObjectFieldHandler::compareDate).get();
        } catch (Exception ignore) {
            return DateUtil.date();
        }
    }

    public static <T> List<T> generateSortListByList(List<T> objList) {
        return objList.stream().sorted().collect(Collectors.toList());
    }

    public static Date getMinDate(Date confirmTime, Date confirmTime0) {
        return compareDate(confirmTime, confirmTime0) > 0 ? confirmTime0 : confirmTime;
    }

    public static <T> Date generateMaxDateByObj(List<T> objList, Function<? super T, Date> keyExtractor) {
        try {
            return objList.stream().map(keyExtractor).distinct().max(ObjectFieldHandler::compareDate).get();
        } catch (Exception ignore) {
            return DateUtil.date();
        }
    }

    public static int compareDate(Date first, Date second) {
        return first != null && second != null ? first.compareTo(second) : 0;
    }

    public static int compareInteger(Integer t1, Integer t2) {
        return t1.compareTo(t2);
    }

    public static <T> T generateFindAnyOptional(Collection<T> objList, Predicate<? super T> predicate) {
        if (CollectionUtils.isEmpty(objList)) {
            return null;
        }
        Optional<T> optional = objList.stream().filter(predicate).findAny();
        return optional.orElse(null);
    }

    public static <T> List<T> generateFindAllOptional(List<T> objList, Predicate<? super T> predicate) {
        return objList.stream().filter(predicate).collect(Collectors.toList());
    }

    public static <T> String generateJoinString(List<T> objList, Function<? super T, String> keyExtractor) {
        return generateListByObj(objList, keyExtractor).stream().filter(s -> !StringUtils.isBlank(s)).collect(Collectors.joining(","));
    }

    public static <T> BigDecimal generateSumDecimal(List<T> objList, Function<? super T, BigDecimal> keyExtractor) {
        return objList.stream().map(keyExtractor).reduce(getDecimal(null), (sum, obj) -> {
            sum = sum.add(getDecimal(obj));
            return sum;
        });
    }

    public static <T> List<List<T>> split(List<T> list, int size) {
        if (list == null || list.size() == 0) {
            return null;
        }
        int count = list.size();
        int pageCount = (count / size) + (count % size == 0 ? 0 : 1);
        List<List<T>> temp = new ArrayList<>(pageCount);
        for (int i = 0, from = 0, to = 0; i < pageCount; i++) {
            from = i * size;
            to = from + size;
            to = Math.min(to, count);
            List<T> list1 = list.subList(from, to);
            temp.add(list1);
        }
        return temp;
    }

    public static int getNum(String originStr, String targetStr){
        String result = originStr.replaceAll(targetStr, "");

        int i = originStr.length() - result.length();

        return i;
    }

    public static Map getMapByJson(ImmutableMap immutableMap){
        return JSONObject.parseObject(JSONObject.toJSONString(immutableMap)).toJavaObject(Map.class);
    }
}
