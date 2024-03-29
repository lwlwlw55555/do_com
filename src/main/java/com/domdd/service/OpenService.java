package com.domdd.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.*;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.domdd.controller.resp.MddResp;
import com.domdd.core.DanengOpenPropertiesDo;
import com.domdd.core.aop.OpenAspect;
import com.domdd.dao.common.*;
import com.domdd.enums.upload.UploadShopNameEnum;
import com.domdd.enums.upload.UploadTypeEnum;
import com.domdd.model.*;
import com.domdd.util.ObjectFieldHandler;
import com.google.common.collect.ImmutableMap;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotEmpty;
import java.util.*;

@Service
@AllArgsConstructor
@Slf4j
//@SuppressWarnings("all")
public class OpenService {
    private final OrderInfoMapper orderInfoMapper;
    private final AfterSaleOrderMapper afterSaleOrderMapper;
    private final AfterSaleReturnOrderMapper afterSaleReturnOrderMapper;
    private final InventoryMapper inventoryMapper;
    private final PurchaseInOrderMapper purchaseInOrderMapper;
    private final DanengOpenPropertiesDo danengOpenPropertiesDo;
    private final PlatformSkuOuterIdMappingMapper mappingMapper;
    /**
     * @see org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration#stringRedisTemplate(RedisConnectionFactory)
     */
    private static StringRedisTemplate stringRedisTemplate;

    public static void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
        OpenService.stringRedisTemplate = stringRedisTemplate;
    }

    public static String DEFAULT_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static String DEFAULT_DATE_PATTERN_0 = "yyyy-MM-dd 00:00:00";
    public static String DEFAULT_DATE_PATTERN_1 = "yyyy-MM-dd 23:59:59";
    public static String OnlineStatus = "OnlineStatus";
    public static Integer pageSize = 100;
    public static List<String> shopNameList = Arrays.asList("诺优能官方旗舰店", "爱他美旗舰店");
    public static Map<String, String> shopNameMapping = ImmutableMap.of("爱他美旗舰店", "爱他美官方旗舰店");
    public static List<String> ignoreOuterIdList = CollectionUtil.newArrayList("lyf-sbyj", "qmsd-3", "qmsd-xd",
            "ysgb", "hx-yzbs", "tc-qslsb", "dsn-rt", "ld-xhyb", "dsn-lh", "qmsd-1", "ksjta", "ksj-2", "ksj-3", "ksj-4",
            "myb", "dsnsb", "ksj-12", "9e00094", "qmsd-7", "tsx-wd", "qmsd-4", "dsn-xhj", "dl-yhb", "lyf-rbt", "xhr-xlx",
            "fx-yywj", "fx-rtfb", "NDXY-DXGZ", "DUCK-DDL", "DSN-gjjwj", "DSN-yhxb", "SHARK-xcq", "fx-mmb", "hrl-cslb", "ds-xcq", "dsn-rmj", "ht-hxc", "zxp", "bfr-mnjt", "mtj-ktkd", "fx-cnt", "xtk-sh", "qw-mkfty", "ds-cfj", "bf-yqz",
            "HPK-atm", "dsn-rfm", "lyf-ddtrbt", "mq-lyyx", "nadle-slc", "sbe-kqzg", "qmsd-5", "atm-cl", "20221228",
            "DN-AC1*2",
            "DN-AC1*3",
            "DN-AC2*2",
            "DN-AC2*3",
            "DN-AC3*2",
            "DN-AC3*3",
            "DN-AC3*6",
            "DN-AC4*2",
            "DN-AC4*6",
            "DN-MF6+*2",
            "DN-MF6+*3",
            "DN-MF7+*2",
            "DN-MF7+*3",
            "DN-MF8+*2",
            "DN-MF8+*3",
            "DN-DTN*2",
            "DN-NGB3*2",
            "DN-NGB3*3",
            "DN-NGB3*6",
            "DN-NGB4*3",
            "DN-NGB4*6",
            "DN-LY3*3",
            "DN-LY3*6",
            "DN-ZY3*2",
            "DN-ZY3*3",
            "DN-ZY3*6",
            "DN-NC1*2",
            "DN-NC1*3",
            "DN-NC2*2",
            "DN-NC2*3",
            "DN-NC3*2",
            "DN-NC3*3",
            "DN-NC3*6",
            "DN-NC4*2",
            "DN-NC4*3",
            "DN-NC4*6",
            "DN-NCT1*3",
            "DN-NCT2*3",
            "DN-NCT3*2",
            "DN-NCT3*3",
            "DN-NCT3*6",
            "DN-NCT4*2",
            "DN-NCT4*3",
            "DN-NCT4*6",
            "DN-AC1",
            "DN-AC2",
            "DN-AC3",
            "DN-AC4",
            "DN-MF-6+",
            "DN-MF-7+",
            "DN-MF-8+",
            "DN-APKM",
            "DN-LY1",
            "DN-LY2",
            "DN-LY3",
            "DN-LY1-300",
            "DN-LY2-300",
            "DN-LY3-300",
            "DN-NGB1",
            "DN-NGB2",
            "DN-NGB3",
            "DN-NGB4",
            "DN-NGB1-300",
            "DN-NGB2-300",
            "DN-NGB3-300",
            "DN-ZY1",
            "DN-ZY2",
            "DN-ZY3",
            "DN-ZY1-400",
            "DN-ZY2-400",
            "DN-ZY3-400",
            "DN-NC1",
            "DN-NC2",
            "DN-NC3",
            "DN-NC4",
            "DN-NCT1",
            "DN-NCT2",
            "DN-NCT3",
            "DN-NCT4"
    );
    public static String ignoreOuterIdRedisKey = "outerIdList";

    public static String daNengIgnoreOuterIdRedisKey = "daNengOuterIdList";

    public List<SelectVo> orderType() {
        return ObjectFieldHandler.enumToSelectVoDesc(
                ArrayUtil.toArray(ObjectFieldHandler.generateFindAllOptional(UploadTypeEnum.values(), e ->
                        CollectionUtil.newArrayList(UploadTypeEnum.ORDER, UploadTypeEnum.AFTER_SALE_RETURN)
                                .contains(e)), UploadTypeEnum.class));
    }

    public List<SelectVo> orderShopNameType() {
        return ObjectFieldHandler.enumToSelectVoDesc(UploadShopNameEnum.values());
    }

    public enum OnlineStatusEnum {
        NORMAL,
        REFUND,
        OFFLINE
    }

    public static String getStringByMapping(String shopName) {
        if (shopNameMapping.containsKey(shopName)) {
            return shopNameMapping.get(shopName);
        }
        return shopName;
    }

    public static boolean checkTimeInNight() {
//        int hour = DateUtil.hour(DateUtil.date(), true);
//        return hour <= 6;
        return false;
    }

    public static List<String> getIgnoreOuterIdListByRedis() {
        List<String> latestIgnoreOuterIdList = ignoreOuterIdList;

        String ignoreOuterIdStr = stringRedisTemplate.opsForValue().get(ignoreOuterIdRedisKey);
//        log.info("[OpenService/getIgnoreOuterIdListByRedis] Latest ignoreOuterIdList :{}", ignoreOuterIdStr);
        if (CollectionUtils.isNotEmpty(JSONObject.parseArray(ignoreOuterIdStr, String.class))) {
            List<String> tempIgnoreOuterIdList = JSONObject.parseArray(ignoreOuterIdStr, String.class);
            latestIgnoreOuterIdList.addAll(tempIgnoreOuterIdList);
            latestIgnoreOuterIdList = ObjectFieldHandler.generateDistinctListByObj(latestIgnoreOuterIdList, v -> v);
        }
        String daNengIgnoreOuterIdStr = stringRedisTemplate.opsForValue().get(daNengIgnoreOuterIdRedisKey);
//        log.info("[OpenService/getIgnoreOuterIdListByRedis] Latest daNengIgnoreOuterIdStr :{}", daNengIgnoreOuterIdStr);
        if (CollectionUtils.isNotEmpty(JSONObject.parseArray(daNengIgnoreOuterIdStr, String.class))) {
            List<String> tempIgnoreOuterIdList = JSONObject.parseArray(daNengIgnoreOuterIdStr, String.class);
            latestIgnoreOuterIdList.addAll(tempIgnoreOuterIdList);
            latestIgnoreOuterIdList = ObjectFieldHandler.generateDistinctListByObj(latestIgnoreOuterIdList, v -> v);
        }
        log.info("[OpenService/getIgnoreOuterIdListByRedis] Latest latestIgnoreOuterIdList :{}", JSONObject.toJSONString(latestIgnoreOuterIdList));
        return latestIgnoreOuterIdList;
    }

    public IPage<OrderInfo> orderList(String shopName, Date startTime, Date endTime, String timeType, Integer page, Integer pageSize, String orderType) {
        String statusVal = stringRedisTemplate.opsForValue().get(OnlineStatus);
        OnlineStatusEnum onlineStatus = OnlineStatusEnum.NORMAL;
        try {
            onlineStatus = Convert.convert(OnlineStatusEnum.class, statusVal);
        } catch (Exception ignored) {

        }
        IPage<OrderInfo> orderInfoRes = orderList(shopName, startTime, endTime, timeType, page, pageSize, onlineStatus, orderType);
        if (Objects.equals(onlineStatus, OnlineStatusEnum.OFFLINE)) {
            orderInfoRes.setRecords(CollectionUtil.newArrayList());
            orderInfoRes.setTotal(0);
        } else if (Objects.equals(onlineStatus, OnlineStatusEnum.REFUND)) {
            List<OrderInfo> records = orderInfoRes.getRecords();
            records = ObjectFieldHandler.generateFindAllOptional(records, orderInfo -> !("NONE".equals(orderInfo.getRefundStatus())));
            orderInfoRes.setRecords(records);
        }
        return orderInfoRes;
    }

    public IPage<OrderInfo> orderById(String shopName, String platformOrderSn) {
        return orderInfoMapper.selectByPage(new Page<>(1, 100), "shipping_time", null, null, shopName, false, null, getIgnoreOuterIdListByRedis(), CollectionUtil.newArrayList(platformOrderSn));
    }

    public IPage<OrderInfo> orderList(String shopName, Date startTime, Date endTime, String timeType, Integer page, Integer pageSize, OnlineStatusEnum onlineStatusEnum, String orderType) {
        if (checkTimeInNight()) {
            MddResp<OrderInfo> orderInfoList = getOrderInfoList(shopName, startTime, endTime, timeType, page, pageSize);

            IPage<OrderInfo> p = new Page<>();
            orderInfoList.setRecords(ObjectFieldHandler.generateFindAllOptional(orderInfoList.getRecords(), order -> {
                return !ignoreOuterIdList.contains(order.getOuterId()) && !ignoreOuterIdList.contains(order.getSysOuterId());
            }));
            BeanUtils.copyProperties(orderInfoList, p);
            return p;
//            return Convert.convert(IPage.class, orderInfoList);
        }
        IPage<OrderInfo> p = new Page<>(page, pageSize);

//        return orderInfoMapper.selectByPage(p, timeType, startTime, endTime, shopName, Objects.equals(onlineStatusEnum, OnlineStatusEnum.REFUND), orderType, getIgnoreOuterIdListByRedis());
        return orderInfoMapper.selectByPage(p, timeType, startTime, endTime, shopName, Objects.equals(onlineStatusEnum, OnlineStatusEnum.REFUND), orderType, getIgnoreOuterIdListByRedis(), null);
    }

    public IPage<AfterSaleOrder> afterSaleOrderList(String shopName, Date startTime, Date endTime, Integer page, Integer pageSize) {
        if (checkTimeInNight()) {
            MddResp<AfterSaleOrder> orderInfoList = getAfterSaleOrderList(shopName, startTime, endTime, page, pageSize);
            IPage<AfterSaleOrder> p = new Page<>();
            BeanUtils.copyProperties(orderInfoList, p);
            return p;
//            return Convert.convert(IPage.class, orderInfoList);
        }
        IPage<AfterSaleOrder> p = new Page<>(page, pageSize);
        return afterSaleOrderMapper.selectByPage(p, startTime, endTime, shopName);
    }

    public IPage<AfterSaleReturnOrder> afterSaleReturnOrderList(String shopName, Date startTime, Date endTime, Integer page, Integer pageSize) {
        if (checkTimeInNight()) {
            MddResp<AfterSaleReturnOrder> orderInfoList = getAfterSaleReturnOrderList(shopName, startTime, endTime, page, pageSize);
            IPage<AfterSaleReturnOrder> p = new Page<>();
            BeanUtils.copyProperties(orderInfoList, p);
            return p;
//            return Convert.convert(IPage.class, orderInfoList);
        }
        IPage<AfterSaleReturnOrder> p = new Page<>(page, pageSize);
        return afterSaleReturnOrderMapper.selectByPage(p, startTime, endTime, shopName, getIgnoreOuterIdListByRedis(), null);
    }

    public IPage<AfterSaleReturnOrder> afterSaleReturnOrderById(String shopName, String platformOrderSn) {
        return afterSaleReturnOrderMapper.selectByPage(new Page<>(1, 100), null, null, shopName, getIgnoreOuterIdListByRedis(), platformOrderSn);
    }


    public IPage<Inventory> inventoryList(Integer page, Integer pageSize) {
        if (checkTimeInNight()) {
            MddResp<Inventory> inventoryList = getInventoryList(page, pageSize);
            IPage<Inventory> p = new Page<>();
            BeanUtils.copyProperties(inventoryList, p);
            return p;
//            return Convert.convert(IPage.class, inventoryList);
        }
        IPage<Inventory> p = new Page<>(page, pageSize);
        return inventoryMapper.selectByPage(p);
    }

    public IPage<PurchaseInOrder> purchaseInOrderList(Date startTime, Date endTime, Integer page, Integer pageSize) {
        if (checkTimeInNight()) {
            MddResp<PurchaseInOrder> purchaseInOrderList = getPurchaseInOrderList(startTime, endTime, page, pageSize);
            IPage<PurchaseInOrder> p = new Page<>();
            BeanUtils.copyProperties(purchaseInOrderList, p);
            return p;
//            return Convert.convert(IPage.class, purchaseInOrderList);
        }
        IPage<PurchaseInOrder> p = new Page<>(page, pageSize);
        return purchaseInOrderMapper.selectByPage(p, startTime, endTime);
    }


    public static Map getBaseMap(String method, Integer page, Integer pageSize) {
        return ObjectFieldHandler.getMapByJson(ImmutableMap.of("appkey", DanengOpenPropertiesDo.appkey, "timestamp", System.currentTimeMillis(), "method", method, "page", page, "pageSize", pageSize));
    }

    private static String parseRespFormat(String rspStr) {
        try {
            if (!StringUtils.isBlank(rspStr)) {
                return JSONObject.toJSONString(JSONObject.parseObject(rspStr));
            }
        } catch (Exception ignore) {
        }
        return rspStr;
    }

    public static MddResp<OrderInfo> getOrderInfoList(String shopName, Date startTime, Date endTime, String timeType, Integer page, Integer pageSize) {
        Map<String, Object> paramsMap = getBaseMap("order.list", page, pageSize);
        paramsMap.putAll(ObjectFieldHandler.getMapByJson(ImmutableMap.of("timeType", timeType, "shopName", shopName, "endTime", endTime, "startTime", startTime)));
        String sign = OpenAspect.generateSign(paramsMap);
        paramsMap.put("sign", sign);
        String params = JSONObject.toJSONString(paramsMap);
        String url = DanengOpenPropertiesDo.url + "order/list";
        String resp = HttpRequest.post(url).
                body(params).header("Content-Type", "application/json").execute().body();
        log.info("[OpenService/getOrderInfoList-url] url:{} params:{} resp:{}", url, params, parseRespFormat(resp));
        return getMddRespByRespStr(resp, OrderInfo.class);
//        JSONObject jsonObject = JSONObject.parseObject(resp);
//        return jsonObject.getJSONObject("data").getJSONArray("records").toJavaList(OrderInfo.class);
    }

    public static MddResp<AfterSaleOrder> getAfterSaleOrderList(String shopName, Date startTime, Date endTime, Integer page, Integer pageSize) {
        Map<String, Object> paramsMap = getBaseMap("afterSaleOrder.list", page, pageSize);
        paramsMap.putAll(ObjectFieldHandler.getMapByJson(ImmutableMap.of("shopName", shopName, "endTime", endTime, "startTime", startTime)));
        String sign = OpenAspect.generateSign(paramsMap);
        paramsMap.put("sign", sign);
        String params = JSONObject.toJSONString(paramsMap);
        String url = DanengOpenPropertiesDo.url + "afterSaleOrder/list";
        String resp = HttpRequest.post(url).
                body(params).header("Content-Type", "application/json").execute().body();
        log.info("[OpenService/getAfterSaleOrderList-url] url:{} params:{} resp:{}", url, params, parseRespFormat(resp));
        return getMddRespByRespStr(resp, AfterSaleOrder.class);
//        JSONObject jsonObject = JSONObject.parseObject(resp);
//        return jsonObject.getJSONObject("data").getJSONArray("records").toJavaList(AfterSaleOrder.class);
    }

    public static MddResp<AfterSaleReturnOrder> getAfterSaleReturnOrderList(String shopName, Date startTime, Date endTime, Integer page, Integer pageSize) {
        Map<String, Object> paramsMap = getBaseMap("afterSaleReturnOrder.list", page, pageSize);
        paramsMap.putAll(ObjectFieldHandler.getMapByJson(ImmutableMap.of("shopName", shopName, "endTime", endTime, "startTime", startTime)));
        String sign = OpenAspect.generateSign(paramsMap);
        paramsMap.put("sign", sign);
        String params = JSONObject.toJSONString(paramsMap);
        String url = DanengOpenPropertiesDo.url + "afterSaleReturnOrder/list";
        String resp = HttpRequest.post(url).
                body(params).header("Content-Type", "application/json").execute().body();
        log.info("[OpenService/getAfterSaleReturnOrderList-url] url:{} params:{} resp:{}", url, params, parseRespFormat(resp));
        return getMddRespByRespStr(resp, AfterSaleReturnOrder.class);
//        JSONObject jsonObject = JSONObject.parseObject(resp);
//        return jsonObject.getJSONObject("data").getJSONArray("records").toJavaList(AfterSaleReturnOrder.class);
    }

    public static MddResp<PurchaseInOrder> getPurchaseInOrderList(Date startTime, Date endTime, Integer page, Integer pageSize) {
        Map<String, Object> paramsMap = getBaseMap("purchaseInOrder.list", page, pageSize);
        paramsMap.putAll(ObjectFieldHandler.getMapByJson(ImmutableMap.of("endTime", endTime, "startTime", startTime)));
        String sign = OpenAspect.generateSign(paramsMap);
        paramsMap.put("sign", sign);
        String params = JSONObject.toJSONString(paramsMap);
        String url = DanengOpenPropertiesDo.url + "purchaseInOrder/list";
        String resp = HttpRequest.post(url).
                body(params).header("Content-Type", "application/json").execute().body();
        log.info("[OpenService/getPurchaseInOrderList-url] url:{} params:{} resp:{}", url, params, parseRespFormat(resp));
        return getMddRespByRespStr(resp, PurchaseInOrder.class);
//        return jsonObject.getJSONObject("data").getJSONArray("records").toJavaList(PurchaseInOrder.class);
    }

    public static MddResp<Inventory> getInventoryList(Integer page, Integer pageSize) {
        Map<String, Object> paramsMap = getBaseMap("inventory.list", page, pageSize);
        String sign = OpenAspect.generateSign(paramsMap);
        paramsMap.put("sign", sign);
        String params = JSONObject.toJSONString(paramsMap);
        String url = DanengOpenPropertiesDo.url + "inventory/list";
        String resp = HttpRequest.post(url).
                body(params).header("Content-Type", "application/json").execute().body();
        log.info("[OpenService/getInventoryList-url] url:{} params:{} resp:{}", url, params, parseRespFormat(resp));
        return getMddRespByRespStr(resp, Inventory.class);
//        JSONObject jsonObject = JSONObject.parseObject(resp).getJSONObject("data");
//        MddResp mddResp = jsonObject.toJavaObject(MddResp.class);
//        mddResp.setRecords(JSONObject.parseArray(JSONObject.toJSONString(mddResp.getRecords())).toJavaList(Inventory.class));
//        return mddResp;
    }

    private static MddResp getMddRespByRespStr(String resp, Class tClass) {
        JSONObject jsonObject = JSONObject.parseObject(resp).getJSONObject("data");
        MddResp mddResp = jsonObject.toJavaObject(MddResp.class);
        mddResp.setRecords(JSONObject.parseArray(JSONObject.toJSONString(mddResp.getRecords())).toJavaList(tClass));
        return mddResp;
    }

    public void startRefreshOrderList() {
        startRefreshOrderList(null, null);
    }

    public void startRefreshOrderList(Date startDate, Date endDate) {
        if (startDate == null || endDate == null) {
            DateTime yesterday = DateUtil.offsetDay(DateUtil.date(), -1);
            startDate = DateUtil.offsetDay(DateUtil.parseDateTime(DateUtil.format(yesterday, DEFAULT_DATE_PATTERN_0)), -2);
            endDate = DateUtil.parseDateTime(DateUtil.format(yesterday, DEFAULT_DATE_PATTERN_1));
        }
        Date tempEndDate = startDate;
        while (tempEndDate.before(endDate)) {
            tempEndDate = DateUtil.offsetDay(startDate, 1);
            if (tempEndDate.after(endDate)) {
                tempEndDate = endDate;
            }
            for (String shopName : shopNameList) {
                int page = 0;
                MddResp<OrderInfo> resp = null;
                while (resp == null || resp.getHasNextPage()) {
                    page++;
                    resp = getOrderInfoList(getStringByMapping(shopName), startDate, tempEndDate, "shipping_time", page, pageSize);
                    if (CollectionUtils.isNotEmpty(resp.getRecords())) {
                        resp.getRecords().forEach(orderInfo -> {
                            orderInfo.setShopName(shopName);
                            if (StringUtils.isAnyBlank(orderInfo.getPlatformGoodsId(), orderInfo.getPlatformSkuId())
                                    || Objects.equals(orderInfo.getPlatformGoodsId(), "0") || Objects.equals(orderInfo.getPlatformSkuId(), "0")
                            ) {
                                if (!StrUtil.isAllBlank(orderInfo.getOuterId(), orderInfo.getSysOuterId())) {
                                    PlatformSkuOuterIdMapping mapping = mappingMapper.selectByOuterId(StrUtil.isNotBlank(orderInfo.getSysOuterId()) ? orderInfo.getSysOuterId() : orderInfo.getOuterId());
                                    if (ObjectUtil.isNotNull(mapping)) {
                                        orderInfo.setPlatformGoodsId(mapping.getPlatformGoodsId());
                                        orderInfo.setPlatformSkuId(mapping.getPlatformSkuId());
                                        if (StrUtil.isBlank(orderInfo.getGoodsName()) || orderInfo.getGoodsName().contains("自定义")) {
                                            orderInfo.setGoodsName(mapping.getPlatformGoodsName());
                                        }
                                        if (StrUtil.isBlank(orderInfo.getStyleValue()) || orderInfo.getStyleValue().contains("自定义")) {
                                            orderInfo.setStyleValue(mapping.getPlatformSkuName());
                                        }
                                        if (StrUtil.isBlank(orderInfo.getPlatformOrderGoodsSn())) {
                                            orderInfo.setPlatformOrderGoodsSn(mapping.getPlatformGoodsOuterId());
                                        }
                                    }
                                }
                            }
                        });
                        orderInfoMapper.replaceBatch(resp.getRecords());
//                resp.getRecords().forEach(orderInfo -> {
//                    orderInfo.setShopName("爱他美旗舰店");
//                    orderInfoMapper.insert(orderInfo);
//                });
                    }
                }
            }
            startDate = tempEndDate;
        }
    }

    public void startRefreshAfterSaleList() {
        startRefreshAfterSaleList(null, null);
    }

    public void startRefreshAfterSaleList(Date startDate, Date endDate) {
        if (startDate == null || endDate == null) {
            DateTime yesterday = DateUtil.offsetDay(DateUtil.date(), -1);
            startDate = DateUtil.offsetDay(DateUtil.parseDateTime(DateUtil.format(yesterday, DEFAULT_DATE_PATTERN_0)), -2);
            endDate = DateUtil.parseDateTime(DateUtil.format(yesterday, DEFAULT_DATE_PATTERN_1));
        }
        Date tempEndDate = startDate;
        while (tempEndDate.before(endDate)) {
            tempEndDate = DateUtil.offsetDay(startDate, 1);
            if (tempEndDate.after(endDate)) {
                tempEndDate = endDate;
            }
            for (String shopName : shopNameList) {
                int page = 0;
                MddResp<AfterSaleOrder> resp = null;
                while (resp == null || resp.getHasNextPage()) {
                    page++;
                    resp = getAfterSaleOrderList(getStringByMapping(shopName), startDate, tempEndDate, page, pageSize);
                    if (CollectionUtils.isNotEmpty(resp.getRecords())) {
                        resp.getRecords().forEach(orderInfo -> {
                            orderInfo.setShopName(shopName);

                            if (StringUtils.isAnyBlank(orderInfo.getPlatformGoodsId())
                                    || Objects.equals(orderInfo.getPlatformGoodsId(), "0")) {
                                if (!StrUtil.isAllBlank(orderInfo.getOuterId())) {
                                    PlatformSkuOuterIdMapping mapping = mappingMapper.selectByOuterId(orderInfo.getOuterId());
                                    if (ObjectUtil.isNotNull(mapping)) {
                                        orderInfo.setPlatformGoodsId(mapping.getPlatformGoodsId());
                                        if (StrUtil.isBlank(orderInfo.getGoodsName()) || orderInfo.getGoodsName().contains("自定义")) {
                                            orderInfo.setGoodsName(mapping.getPlatformGoodsName());
                                        }
                                        if (StrUtil.isBlank(orderInfo.getStyleValue()) || orderInfo.getStyleValue().contains("自定义")) {
                                            orderInfo.setStyleValue(mapping.getPlatformSkuName());
                                        }
                                        if (StrUtil.isBlank(orderInfo.getSkuName())) {
                                            orderInfo.setSkuName(mapping.getPlatformSkuName());
                                        }
                                        if (StrUtil.isBlank(orderInfo.getPlatformOrderGoodsSn())) {
                                            orderInfo.setPlatformOrderGoodsSn(mapping.getPlatformGoodsOuterId());
                                        }
                                    }
                                }
                            }

                            if (ObjectUtil.isNull(orderInfo.getRefundId())) {
                                orderInfo.setRefundId(RandomUtil.randomLong());
                                orderInfo.setIsManual(true);
                                if (StrUtil.isNotBlank(orderInfo.getOrderSn())) {
                                    List<String> parseGroup = ReUtil.findAllGroup0("\\d+", orderInfo.getOrderSn());
                                    if (CollectionUtil.isNotEmpty(parseGroup)) {
                                        orderInfo.setRefundId(Long.parseLong(parseGroup.get(0)));
                                    }
                                }
                            }

                            orderInfo.setPreRefundId(orderInfo.getRefundId());
                            orderInfo.setRefundId(1000000 + orderInfo.getOrderGoodsId());
                        });
                        afterSaleOrderMapper.replaceBatch(resp.getRecords());
                    }
                }
            }
            startDate = tempEndDate;
        }
    }

    public void startRefreshAfterSaleRefundList() {
        startRefreshAfterSaleRefundList(null, null);
    }

    public void startRefreshAfterSaleRefundList(Date startDate, Date endDate) {
        if (startDate == null || endDate == null) {
            DateTime yesterday = DateUtil.offsetDay(DateUtil.date(), -1);
            startDate = DateUtil.offsetDay(DateUtil.parseDateTime(DateUtil.format(yesterday, DEFAULT_DATE_PATTERN_0)), -2);
            endDate = DateUtil.parseDateTime(DateUtil.format(yesterday, DEFAULT_DATE_PATTERN_1));
        }
        Date tempEndDate = startDate;
        while (tempEndDate.before(endDate)) {
            tempEndDate = DateUtil.offsetDay(startDate, 1);
            if (tempEndDate.after(endDate)) {
                tempEndDate = endDate;
            }
            for (String shopName : shopNameList) {
                int page = 0;
                MddResp<AfterSaleReturnOrder> resp = null;
                while (resp == null || resp.getHasNextPage()) {
                    page++;
                    resp = getAfterSaleReturnOrderList(getStringByMapping(shopName), startDate, tempEndDate, page, pageSize);
                    if (CollectionUtils.isNotEmpty(resp.getRecords())) {
                        resp.getRecords().forEach(orderInfo -> {
                            orderInfo.setShopName(shopName);

                            if (StringUtils.isAnyBlank(orderInfo.getPlatformGoodsId())
                                    || Objects.equals(orderInfo.getPlatformGoodsId(), "0")) {
                                if (!StrUtil.isAllBlank(orderInfo.getOuterId())) {
                                    PlatformSkuOuterIdMapping mapping = mappingMapper.selectByOuterId(orderInfo.getOuterId());
                                    if (ObjectUtil.isNotNull(mapping)) {
                                        orderInfo.setPlatformGoodsId(mapping.getPlatformGoodsId());
                                        if (StrUtil.isBlank(orderInfo.getGoodsName()) || orderInfo.getGoodsName().contains("自定义")) {
                                            orderInfo.setGoodsName(mapping.getPlatformGoodsName());
                                        }
                                        if (StrUtil.isBlank(orderInfo.getSkuName()) || orderInfo.getSkuName().contains("自定义")) {
                                            orderInfo.setSkuName(mapping.getPlatformSkuName());
                                        }
                                        if (StrUtil.isBlank(orderInfo.getPlatformOrderGoodsSn())) {
                                            orderInfo.setPlatformOrderGoodsSn(mapping.getPlatformGoodsOuterId());
                                        }
                                    }
                                }
                            }
                            orderInfo.setIsManual(false);
                            if (ObjectUtil.isNull(orderInfo.getRefundId())) {
                                orderInfo.setRefundId(RandomUtil.randomLong());
                                orderInfo.setIsManual(true);
                                if (StrUtil.isNotBlank(orderInfo.getOrderSn())) {
                                    List<String> parseGroup = ReUtil.findAllGroup0("\\d+", orderInfo.getOrderSn());
                                    if (CollectionUtil.isNotEmpty(parseGroup)) {
                                        orderInfo.setRefundId(Long.parseLong(parseGroup.get(0)));
                                    }
                                }
                            }
                            //处理erp的bug
                            orderInfo.setPreQuantity(orderInfo.getQuantity());
                            orderInfo.setQuantity(orderInfo.getGoodsNumber());
                            orderInfo.setPreRefundId(orderInfo.getRefundId());
                            orderInfo.setRefundId(orderInfo.getOrderGoodsId());
                        });

                        afterSaleReturnOrderMapper.replaceBatch(resp.getRecords());
                    }
                }
            }
            startDate = tempEndDate;
        }
    }

    public void startRefreshInventoryList() {
        startRefreshInventoryList(null, null);
    }

    public void startRefreshInventoryList(Date startDate, Date endDate) {
//        if (startDate == null || endDate == null) {
//            DateTime yesterday = DateUtil.offsetDay(DateUtil.date(), -1);
//            startDate = DateUtil.parseDateTime(DateUtil.format(yesterday, DEFAULT_DATE_PATTERN_0));
//            endDate = DateUtil.parseDateTime(DateUtil.format(yesterday, DEFAULT_DATE_PATTERN_1));
//        }
//        Date tempEndDate = startDate;
//        while (tempEndDate.before(endDate)) {
//            tempEndDate = DateUtil.offsetDay(startDate, 1);
//            if (tempEndDate.after(endDate)) {
//                tempEndDate = endDate;
//            }
        int page = 0;
        MddResp<Inventory> resp = null;
        while (resp == null || resp.getHasNextPage()) {
            page++;
            resp = getInventoryList(page, pageSize);
            if (CollectionUtils.isNotEmpty(resp.getRecords())) {
//                resp.getRecords().forEach(inventory -> {
//                    inventoryMapper.insert(inventory);
//                });
                inventoryMapper.replaceBatch(resp.getRecords());
            }
        }
//        }
//        }
    }

    public void startRefreshPurchaseInOrder() {
        startRefreshPurchaseInOrder(null, null);
    }

    public void startRefreshPurchaseInOrder(Date startDate, Date endDate) {
        if (startDate == null || endDate == null) {
            DateTime yesterday = DateUtil.offsetDay(DateUtil.date(), -1);
            startDate = DateUtil.offsetDay(DateUtil.parseDateTime(DateUtil.format(yesterday, DEFAULT_DATE_PATTERN_0)), -2);
            endDate = DateUtil.parseDateTime(DateUtil.format(yesterday, DEFAULT_DATE_PATTERN_1));
        }
        Date tempEndDate = startDate;
        while (tempEndDate.before(endDate)) {
            tempEndDate = DateUtil.offsetDay(startDate, 1);
            if (tempEndDate.after(endDate)) {
                tempEndDate = endDate;
            }
            int page = 0;
            MddResp<PurchaseInOrder> resp = null;
            while (resp == null || resp.getHasNextPage()) {
                page++;
                resp = getPurchaseInOrderList(startDate, tempEndDate, page, pageSize);
                if (CollectionUtils.isNotEmpty(resp.getRecords())) {
//                resp.getRecords().forEach(purchaseInOrder -> {
//                    purchaseInOrderMapper.insert(purchaseInOrder);
//                });
                    purchaseInOrderMapper.replaceBatch(resp.getRecords());
                }
            }
            startDate = tempEndDate;
        }
    }

//    public static void main(String[] args) {
////        List<OrderInfo> orderInfos = getOrderInfoList("爱他美旗舰店", DateUtil.date(), DateUtil.offsetMinute(DateUtil.date(), -5), "shipping_time", 1, 100);
////        System.out.println(orderInfos);
////        MddResp<Inventory> inventoryList = getInventoryList(1, 100);
////        System.out.println(inventoryList);
//        startRefreshOrderList();
//    }

}
