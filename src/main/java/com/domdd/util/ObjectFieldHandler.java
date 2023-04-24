package com.domdd.util;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.comparator.CompareUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.domdd.enums.upload.sos.OrderUploadEnum;
import com.domdd.model.SelectVo;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.framework.AopProxy;
import org.springframework.aop.support.AopUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.math.BigDecimal.ROUND_DOWN;
import static java.math.BigDecimal.ROUND_HALF_UP;

public class ObjectFieldHandler {
    public static volatile LoadingCache<String, Object> COMMON_CACHES;
    public static volatile LoadingCache<String, LoadingCache<String, Object>> COMMON_LOCKS;

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
        if (total == 0 && part == 0) {
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

    public static <V, T, K> Map<V, K> generateMapByObj(List<T> objList, Function<? super T, V> keyExtractor, Function<? super T, K> valueExtractor) {
        if (objList == null || objList.size() == 0) {
            return new HashMap<>();
        }
        return objList.stream().collect(Collectors.toMap(keyExtractor, valueExtractor));
    }

    public static <V, T, K> Map<V, K> generateMapByObj(T[] array, Function<? super T, V> keyExtractor, Function<? super T, K> valueExtractor) {
        if (ArrayUtil.isEmpty(array)) {
            return new HashMap<>();
        }
        return Arrays.stream(array).collect(Collectors.toMap(keyExtractor, valueExtractor));
    }

    public static <V, T, K> Map<V, K> generateFindAllOptionMap(Collection<T> objList, Predicate<? super T> predicate, Function<? super T, V> keyExtractor, Function<? super T, K> valueExtractor) {
        if (CollectionUtil.isEmpty(objList)) {
            return new HashMap<>();
        }
        return objList.stream().filter(predicate).collect(Collectors.toMap(keyExtractor, valueExtractor));
    }

    public static <V, K> Map<V, K> generateFindAllOptionMap(Collection<Map.Entry<V, K>> objList, Predicate<? super Map.Entry<V, K>> predicate) {
        return generateFindAllOptionMap(objList, predicate, Map.Entry::getKey, Map.Entry::getValue);
    }

    public static <T, V> List<V> generateDistinctListByObj(Collection<T> objList, Function<? super T, V> keyExtractor) {
        if (objList == null || objList.size() == 0) {
            return new ArrayList<>();
        }
        return objList.stream().map(keyExtractor).collect(Collectors.toList()).stream().distinct().collect(Collectors.toList());
    }

    public static <T, V> List<V> generateDistinctListNonNullByObj(Collection<T> objList, Function<? super T, V> keyExtractor) {
        if (objList == null || objList.size() == 0) {
            return new ArrayList<>();
        }
        return generateFindAllOptional(objList.stream().map(keyExtractor).collect(Collectors.toList()).stream().distinct().collect(Collectors.toList()), ObjectUtil::isNotNull);
    }

    public static <T, V> List<V> generateListByObj(Collection<T> objList, Function<? super T, V> keyExtractor) {
        if (objList == null || objList.size() == 0) {
            return new ArrayList<>();
        }
        return objList.stream().map(keyExtractor).collect(Collectors.toList());
    }

    public static <T, V> List<V> generateListByObj(T[] objList, Function<? super T, V> keyExtractor) {
        if (objList == null || objList.length == 0) {
            return new ArrayList<>();
        }
        return Arrays.stream(objList).map(keyExtractor).collect(Collectors.toList());
    }

    public static <T, V> List<V> generateListByObj(Iterable<T> objList, Function<? super T, V> keyExtractor) {
        if (objList == null || !objList.iterator().hasNext()) {
            return new ArrayList<>();
        }
        return StreamSupport.stream(objList.spliterator(), false).map(keyExtractor).collect(Collectors.toList());
    }


    public static <T> Date generateMinDateByObj(List<T> objList, Function<? super T, Date> keyExtractor) {
        try {
            return objList.stream().map(keyExtractor).distinct().min(ObjectFieldHandler::compareDate).get();
        } catch (Exception ignore) {
            return DateUtil.date();
        }
    }

    public static <T> List<T> generateSortListByList(List<T> objList, Comparator<? super T> comparator) {
        return objList.stream().sorted(comparator).collect(Collectors.toList());
    }

    public static <T> List<T> generateSortListByList(List<T> objList) {
        return objList.stream().sorted().collect(Collectors.toList());
    }

    public static <T> List<T> generateSortDateListByList(List<T> objList, Function<? super T, Date> keyExtractor) {
        if (CollectionUtils.isEmpty(objList)) {
            return objList;
        }
        return objList.stream().sorted(Comparator.comparing(keyExtractor,
                Comparator.nullsLast(ObjectFieldHandler::compareDate))).collect(Collectors.toList());
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

    public static <T, V extends Comparable<? super V>> V generateMaxObjByObj(Collection<T> objList, Function<? super T, V> keyExtractor) {
        try {
            return objList.stream().map(keyExtractor).distinct().max((v1, v2) -> CompareUtil.compare(v1, v2)).get();
        } catch (Exception ignore) {
            return null;
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


    public static <T> T generateFindAnyOptional(T[] objList, Predicate<? super T> predicate) {
        if (ArrayUtil.isEmpty(objList)) {
            return null;
        }
        Optional<T> optional = Arrays.stream(objList).filter(predicate).findAny();
        return optional.orElse(null);
    }

    public static <T> T generateFindAnyOptional(Iterable<T> iterable, Predicate<? super T> predicate) {
        if (ArrayUtil.isEmpty(iterable)) {
            return null;
        }
        Optional<T> optional = StreamSupport.stream(iterable.spliterator(), false).filter(predicate).findAny();
        return optional.orElse(null);
    }

    public static <T> T generateFindAnyOptional(Iterator<T> iterator, Predicate<? super T> predicate) {
        if (iterator == null || !iterator.hasNext()) {
            return null;
        }
        Optional<T> optional = StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, 0), false).filter(predicate).findAny();
        return optional.orElse(null);
    }

    public static <T> List<T> generateFindAllOptional(List<T> objList, Predicate<? super T> predicate) {
        if (CollectionUtil.isEmpty(objList)) {
            return CollectionUtil.newArrayList();
        }
        return objList.stream().filter(predicate).collect(Collectors.toList());
    }

    public static <T> List<T> generateFindAllOptional(Iterable<T> objList, Predicate<? super T> predicate) {
        return StreamSupport.stream(objList.spliterator(), false).filter(predicate).collect(Collectors.toList());
    }

    public static <T> List<T> generateFindAllOptional(T[] objList, Predicate<? super T> predicate) {
        return Arrays.stream(objList).filter(predicate).collect(Collectors.toList());
    }

    public static <T> String generateJoinString(List<T> objList, Function<? super T, String> keyExtractor) {
        return generateListByObj(objList, keyExtractor).stream().filter(s -> !StringUtils.isBlank(s)).collect(Collectors.joining(","));
    }

    public static <T, V extends Annotation> Map generateMapByAnnotation(T obj, Class<V> vClass) {
        Map<String, Object> map = CollectionUtil.newHashMap();
        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        while (Object.class != clazz.getSuperclass()) {
            clazz = clazz.getSuperclass();
            fields = ArrayUtil.addAll(fields, clazz.getDeclaredFields());
        }
        fields = ArrayUtil.distinct(fields);
        for (Field f : fields) {
            f.setAccessible(true);
            boolean isNeedSerializer = f.isAnnotationPresent(vClass);
            if (isNeedSerializer) {
                Object o = null;
                try {
                    o = f.get(obj);
                } catch (Exception ignored) {

                }
                String name = f.getName();
                if (f.isAnnotationPresent(JSONField.class)) {
                    name = f.getAnnotation(JSONField.class).name();
                }
                if (f.isAnnotationPresent(JsonAlias.class)) {
                    name = f.getAnnotation(JsonAlias.class).value()[0];
                }
                map.put(name, o);
            } else {
                if (f.getType().getName().equals("java.util.List")) {
                    String name = f.getName();
                    try {
                        if (f.get(obj) != null) {
                            map.put(name, generateListByObj((List) f.get(obj), o1 -> generateMapByAnnotation(o1, vClass)));
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
        return map;
    }

    public static <T, V extends Annotation> String generateJsonByAnnotation(List<T> objList, Class<V> vClass) {
        return JSON.toJSONString(generateListByObj(objList, obj -> generateMapByAnnotation(obj, vClass)), SerializerFeature.WriteMapNullValue);
    }

    public static <T, V extends Annotation> String generateJsonByAnnotation(T obj, Class<V> vClass) {
        return JSON.toJSONString(generateMapByAnnotation(obj, vClass), SerializerFeature.WriteMapNullValue);
    }

    public static <T> BigDecimal generateSumDecimal(List<T> objList, Function<? super T, BigDecimal> keyExtractor) {
        return objList.stream().map(keyExtractor).reduce(getDecimal(null), (sum, obj) -> {
            sum = sum.add(getDecimal(obj));
            return sum;
        });
    }

    public static <T> Integer generateSumInteger(List<T> objList, Function<? super T, Integer> keyExtractor) {
        return objList.stream().map(keyExtractor).reduce(0, (sum, obj) -> {
            sum = sum + obj;
            return sum;
        });
    }

    public static <T, V> List<V> generateSumCollection(Collection<T> objList, Function<? super T, List<V>> keyExtractor) {
        return objList.stream().map(keyExtractor).reduce(CollectionUtil.newArrayList(), CollectionUtil::addAllIfNotContains);
    }

    public static <T, V> List<V> generateSumAllCollection(Collection<T> objList, Function<? super T, List<V>> keyExtractor) {
        return objList.stream().map(keyExtractor).reduce(CollectionUtil.newArrayList(), ObjectFieldHandler::addAll);
    }

    public static <T> List<T> generateSumCollection(List<T>... objList) {
        return Arrays.stream(objList).reduce(CollectionUtil.newArrayList(), CollectionUtil::addAllIfNotContains);
    }

    public static Boolean isDescBySortType(String sortType) {
        return StrUtil.equalsIgnoreCase(sortType, "DESC");
    }

    public static <T> List<T> addAll(List<T> list, List<T> otherList) {
        Iterator var2 = otherList.iterator();
        while (var2.hasNext()) {
            T t = (T) var2.next();
            list.add(t);
        }
        return list;
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

    public static int getNum(String originStr, String targetStr) {
        String result = originStr.replaceAll(targetStr, "");

        int i = originStr.length() - result.length();

        return i;
    }

    public static Map<String, Object> getMapByJson(Map<String, Object> map) {
        return JSONObject.parseObject(JSONObject.toJSONString(map)).toJavaObject(Map.class);
    }

    public static Map getMapByJson(Object o) {
        return JSONObject.parseObject(JSONObject.toJSONString(o)).toJavaObject(Map.class);
    }

    public static <T> T generateClassJson(Object preObj, Class<T> targetClass) {
        return JSON.parseObject(JSON.toJSONString(preObj)).toJavaObject(targetClass);
    }

    public static <T> List<T> generateClassJson(Collection preObj, Class<T> targetClass) {
        return JSON.parseArray(JSON.toJSONString(preObj)).toJavaList(targetClass);
    }

    public static <T, V> Map<V, List<T>> generateGroupByObj(List<T> preList, Function<? super T, V> keyExtractor) {
        return preList.stream().collect(Collectors.groupingBy(keyExtractor, Collectors.toList()));
    }

    public static <T, V> Map<V, List<T>> generateGroupByObj(Collection<T> preList, Function<? super T, V> keyExtractor) {
        return preList.stream().collect(Collectors.groupingBy(keyExtractor, Collectors.toList()));
    }

    public static <T, V, S> Map<V, List<S>> generateGroupByObj(List<T> preList, Function<? super T, V> keyExtractor, Function<? super T, S> keyExtractor0) {
        if (CollectionUtil.isEmpty(preList)) {
            return MapUtil.newHashMap();
        }
        return preList.stream().collect(Collectors.groupingBy(keyExtractor, Collectors.mapping(keyExtractor0, Collectors.toList())));
    }

    public static <T> List<T> split(String str, Class<T> clazz) {
        return split(str, ',', clazz);
    }

    public static <T> List<T> split(String str, char splitChar, Class<T> clazz) {
        if (StrUtil.isBlank(str)) {
            return ListUtil.empty();
        }
        return generateListByObj(StrUtil.split(str, splitChar), s -> Convert.convert(clazz, s));
    }

    public static Object getProxyTargetObject(Object proxy) {
        if (!AopUtils.isAopProxy(proxy)) {
            return proxy;
        }
        try {
            if (AopUtils.isJdkDynamicProxy(proxy)) {
                return getJdkDynamicProxyTargetObject(proxy);
            } else {
                return getCglibProxyTargetObject(proxy);
            }
        } catch (Exception ignored) {
            return proxy;
        }
    }

    public static Object getCglibProxyTargetObject(Object proxy) throws Exception {
        Field h = proxy.getClass().getDeclaredField("CGLIB$CALLBACK_0");
        h.setAccessible(true);
        Object dynamicAdvisedInterceptor = h.get(proxy);

        Field advised = dynamicAdvisedInterceptor.getClass().getDeclaredField("advised");
        advised.setAccessible(true);

        Object target = ((AdvisedSupport) advised.get(dynamicAdvisedInterceptor)).getTargetSource().getTarget();

        return target;
    }

    public static Object getJdkDynamicProxyTargetObject(Object proxy) throws Exception {
        Field h = proxy.getClass().getSuperclass().getDeclaredField("h");
        h.setAccessible(true);
        AopProxy aopProxy = (AopProxy) h.get(proxy);

        Field advised = aopProxy.getClass().getDeclaredField("advised");
        advised.setAccessible(true);

        Object target = ((AdvisedSupport) advised.get(aopProxy)).getTargetSource().getTarget();

        return target;
    }

    public static <T> List<T> refreshNull(List<T> list, Class<T> clazz) {
        try {
            if (CollectionUtil.isNotEmpty(list)) {
                for (int i = 0; i < list.size(); i++) {
                    if (ObjectUtil.isNull(list.get(i))) {
                        list.set(i, clazz.newInstance());
                    }
                }
            }
        } catch (Exception ignored) {
        }
        return list;
    }

    public static <T> List<T> refreshDailyCount(Date startDate, Date endDate, List<T> list, Function<? super T, Date> keyExtractor, Class<T> clazz, String column) {
        List<T> result = new ArrayList<>();
        try {
            List<DateTime> dateTimeList = DateUtil.rangeToList(startDate, endDate, DateField.DAY_OF_YEAR);
            Map<Date, List<T>> listGroup = ObjectFieldHandler.generateGroupByObj(list, keyExtractor);
            for (Date date : dateTimeList) {
                if (listGroup.containsKey(date)) {
                    result.add(listGroup.get(date).get(0));
                } else {
                    T t = clazz.newInstance();
                    Field[] fields = clazz.getDeclaredFields();
                    for (Field f : fields) {
                        f.setAccessible(true);
                        if (Objects.equals(f.getName(), column)) {
                            if (f.getType() == String.class) {
                                f.set(t, DateUtil.formatDate(date));
                            } else if (f.getType() == Date.class) {
                                f.set(t, date);
                            }
                        }
                    }
                    result.add(t);
                }
            }
        } catch (Exception ignored) {
            result = list;
        }
        return result;
    }

    public static String getBeginOfDate(String date) {
        try {
            return DateUtil.formatDateTime(DateUtil.beginOfDay(DateUtil.parseDate(date)));
        } catch (Exception ignored) {

        }
        return date;
    }

    public static String getEndOfDate(String date) {
        try {
            return DateUtil.formatDateTime(DateUtil.endOfDay(DateUtil.parseDate(date)));
        } catch (Exception ignored) {
        }
        return date;
    }

    public static boolean isNotBlank(Object value) {
        if (value instanceof CharSequence) {
            return StrUtil.isNotBlank((CharSequence) value);
        } else {
            return ObjectUtil.isNotEmpty(value);
        }
    }

    public static Boolean toBoolean(Integer booleanInt) {
        if (ObjectUtil.isNull(booleanInt) || !Arrays.asList(0, 1).contains(booleanInt)) {
            return null;
        }
        return toBoolean(booleanInt.toString());
    }

    public static Boolean toBoolean(String booleanStr) {
        if (StrUtil.isBlank(booleanStr)) {
            return null;
        }
        return BooleanUtils.toBooleanObject(booleanStr);
    }

    public static <T> T getEnumByName(Enum[] values, String name, Class<T> tClass) {
        return (T) ObjectFieldHandler.generateFindAnyOptional(values, v -> Objects.equals(v.name(), name));
    }

    public static List<SelectVo> enumToSelectVo(Enum[] values) {
        return enumToSelectVo(values, null);
    }

    public static List<SelectVo> enumToSelectVoDesc(Enum[] values) {
        return enumToSelectVo(values, "desc");
    }

    public static List<SelectVo> enumToSelectVo(Enum[] values, String fieldName) {
        return generateListByObj(values, v -> {
            Object name = null;
            if (StrUtil.isNotBlank(fieldName)) {
                Class<? extends Enum> aClass = v.getClass();
                Field[] declaredFields = aClass.getDeclaredFields();
                Field field = ObjectFieldHandler.generateFindAnyOptional(declaredFields, f -> Objects.equals(f.getName(), fieldName));
                if (field != null) {
                    field.setAccessible(true);
                    try {
                        name = field.get(v);
                    } catch (IllegalAccessException ignored) {

                    }
                }
            }
            return Builder.of(SelectVo::new).with(SelectVo::setKey, v.name()).with(SelectVo::setName, ObjectUtil.isNull(name) ? v.name() : name.toString()).build();
        });
    }

    public static String join(String joinStr, String... strArr) {
        return StrUtil.join(joinStr, ObjectFieldHandler.generateFindAllOptional(strArr, StrUtil::isNotBlank));
    }

    public static String join(String joinStr, List<String> strArr) {
        if (CollectionUtil.isEmpty(strArr)) {
            return "";
        }
        return StrUtil.join(joinStr, ObjectFieldHandler.generateFindAllOptional(strArr, StrUtil::isNotBlank));
    }

    public static <T> List<T> generateMergeDistinctList(Collection<T>... collections) {
        return ObjectFieldHandler.generateDistinctListByObj(Arrays.stream(collections).reduce(CollectionUtil.newArrayList(), (init, obj) -> {
            init.addAll(obj);
            return init;
        }), o -> o);
    }

    public static <T> List<T> generateDiffCollection(List<T> preDiffList, List<T>... objList) {
        if (ArrayUtil.isEmpty(objList)) {
            return CollectionUtil.newArrayList();
        }
        return Arrays.stream(objList).reduce(CollectionUtil.newArrayList(preDiffList), CollectionUtil::subtractToList);
    }

    public static String diffStr(String str1, String str2) {
        List<String> str1List = new ArrayList<>();
        for (char c : str1.toCharArray()) {
            str1List.add(CharUtil.toString(c));
        }

        List<String> str2List = new ArrayList<>();
        for (char c : str2.toCharArray()) {
            str2List.add(CharUtil.toString(c));
        }
        return generateSumString(generateDiffCollection(str1List, str2List), s -> s);
    }

    public static <T> String generateSumString(Collection<T> objList, Function<? super T, String> keyExtractor) {
        return objList.stream().map(keyExtractor).reduce("", (init, str) -> init += str);
    }

    public static Object getLock(String lockName, Object key1, Object key2) {
        try {
            if (ObjectUtil.isAllEmpty(key1, key2)) {
                return new Object();
            }
            synchronized (ObjectFieldHandler.class) {
                if (ObjectUtil.isNull(COMMON_LOCKS)) {
                    COMMON_LOCKS = CacheBuilder
                            .newBuilder()
                            .expireAfterWrite(2, TimeUnit.MINUTES)
                            .build(new CacheLoader<String, LoadingCache<String, Object>>() {
                                @Override
                                public LoadingCache<String, Object> load(String key) {
                                    return CacheBuilder
                                            .newBuilder()
                                            .expireAfterWrite(1, TimeUnit.MINUTES)
                                            .build(new CacheLoader<String, Object>() {
                                                @Override
                                                public Object load(String key) {
                                                    return new Object();
                                                }
                                            });
                                }
                            });
                }
            }
            return COMMON_LOCKS.get(lockName).get(isNotBlank(key1) ? key1.toString() : key2.toString());
        } catch (Exception ignored) {

        }
        return new Object();
    }

    public static Object getCache(String key) {
        try {
            if (ObjectUtil.isEmpty(key)) {
                return null;
            }
            return COMMON_CACHES.get(key);
        } catch (Exception ignored) {

        }
        return null;
    }

    public static <T> T getCache(String key, Class<T> clazz) {
        try {
            if (ObjectUtil.isEmpty(key)) {
                return null;
            }
            Object cache = COMMON_CACHES.get(key);
            if (ObjectUtil.isNotNull(cache)) {
                return JSON.parseObject(JSON.toJSONString(cache), clazz);
            }
        } catch (Exception ignored) {

        }
        return null;
    }

    public static <T> List<T> getCacheList(String key, Class<T> clazz) {
        try {
            if (ObjectUtil.isEmpty(key)) {
                return null;
            }
            Object cache = COMMON_CACHES.get(key);
            if (ObjectUtil.isNotNull(cache)) {
                return JSON.parseArray(JSON.toJSONString(cache), clazz);
            }
        } catch (Exception ignored) {

        }
        return null;
    }


    public static void putCache(String key, Object obj) {
        putCache(key, obj, 3);
    }

    public static void putCache(String key, Object obj, Integer cacheTime) {
        putCache(key, obj, cacheTime, TimeUnit.MINUTES);
    }

    public static void putCache(String key, Object obj, Integer cacheTime, TimeUnit timeUnit) {
        try {
            if (ObjectUtil.isNull(key) || ObjectUtil.isNull(obj)) {
                return;
            }
            synchronized (ObjectFieldHandler.class) {
                if (ObjectUtil.isNull(COMMON_CACHES)) {
                    COMMON_CACHES = CacheBuilder
                            .newBuilder()
                            .expireAfterWrite(cacheTime, timeUnit)
                            .build(new CacheLoader<String, Object>() {
                                @Override
                                public Object load(String key) {
                                    return null;
                                }
                            });
                } else {
                    COMMON_CACHES.put(key, obj);
                }
            }
        } catch (Exception ignored) {

        }
    }

    public static String findAllGroup0(String pattern, String str) {
        try {
            return ReUtil.findAllGroup0(pattern, str).get(0);
        } catch (Exception ignored) {
            return "";
        }
    }

    public static class Builder<T> {

        private final Supplier<T> instanceProvider;

        private final List<Consumer<T>> modifiers = new ArrayList<>();

        public Builder(Supplier<T> instanceProvider) {
            this.instanceProvider = instanceProvider;
        }

        public static <T> Builder<T> of(Supplier<T> instanceProvider) {
            return new Builder<>(instanceProvider);
        }

        public <P1> Builder<T> with(BiConsumer<T, P1> consumer1, P1 p1) {
            Consumer<T> c = instance -> consumer1.accept(instance, p1);
            modifiers.add(c);
            return this;
        }

        public T build() {
            T value = instanceProvider.get();
            modifiers.forEach(modifier -> modifier.accept(value));
            modifiers.clear();
            return value;
        }
    }

    public static void copyPropertiesIgr(Object source, Object target, String... arg) {
        CopyOptions copyOptions = CopyOptions.create().ignoreNullValue();
        if (ArrayUtil.isNotEmpty(arg)) {
            copyOptions.setIgnoreProperties(arg);
        }
        BeanUtil.copyProperties(source, target, copyOptions);
    }

    public static String getValueByIndex(Row r, Integer i) {
        if (r == null || r.getCell(i) == null) {
            return "";
        }
        try {
            if (HSSFDateUtil.isCellDateFormatted(r.getCell(i))) {
                Date dateCellValue = r.getCell(i).getDateCellValue();
                return DateUtil.formatDateTime(dateCellValue);
            }
        } catch (Exception ignored) {

        }
        setCellStrType(r.getCell(i));
        return r.getCell(i).getStringCellValue().trim();
    }

    public static String getValueByIndex(Row r, Integer i, String field) {
        if (r == null || r.getCell(i) == null) {
            return "";
        }
        if (Objects.equals(field, OrderUploadEnum.orderGoodsId.name())) {
            Double numericCellValue = r.getCell(i).getNumericCellValue();
            String orderGoodsStr = StrUtil.toString(numericCellValue).replaceAll("\\.", "");
            return orderGoodsStr.substring(0, orderGoodsStr.indexOf("E"));
        }
        try {
            if (HSSFDateUtil.isCellDateFormatted(r.getCell(i))) {
                Date dateCellValue = r.getCell(i).getDateCellValue();
                return DateUtil.formatDateTime(dateCellValue);
            }
        } catch (Exception ignored) {

        }
        setCellStrType(r.getCell(i));
        return r.getCell(i).getStringCellValue().trim();
    }

    public static Boolean checkIsNotEmptyRow(Row r) {
        return ObjectFieldHandler.generateFindAnyOptional(r.cellIterator(), cell -> {
            setCellStrType(cell);
            return ObjectFieldHandler.isNotBlank(cell.getStringCellValue());
        }) != null;
    }

    public static void setCellStrType(Cell cell) {
        if (!Objects.equals(cell.getCellType(), CellType.STRING)) {
            cell.setCellType(CellType.STRING);
        }
    }

    public static List<Row> getRowsByFileAndRowIndex(MultipartFile file, Integer rowIndex) throws Exception {
        byte[] b = file.getBytes();
        Workbook wb = null;
        try {
            wb = new XSSFWorkbook(new ByteArrayInputStream(b));
        } catch (Exception ignored) {
            wb = new HSSFWorkbook(new ByteArrayInputStream(b));
        }
        Sheet sheet = wb.getSheetAt(0);
        List<Row> rows = ObjectFieldHandler.generateFindAllOptional(sheet, r -> r.getRowNum() > rowIndex && checkIsNotEmptyRow(r));

        if (CollectionUtil.isEmpty(rows)) {
            throw new Exception("文件为空，无法解析");
        }
        return rows;
    }

}
