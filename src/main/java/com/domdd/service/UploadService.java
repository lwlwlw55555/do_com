package com.domdd.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import com.domdd.dao.common.*;
import com.domdd.enums.upload.UploadShopNameEnum;
import com.domdd.enums.upload.UploadTypeEnum;
import com.domdd.enums.upload.sos.*;
import com.domdd.model.*;
import com.domdd.util.ObjectFieldHandler;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
@AllArgsConstructor
public class UploadService {
    private final OrderInfoMapper orderInfoMapper;
    private final AfterSaleOrderMapper afterSaleOrderMapper;
    private final AfterSaleReturnOrderMapper afterSaleReturnOrderMapper;
    private final InventoryMapper inventoryMapper;
    private final PurchaseInOrderMapper purchaseInOrderMapper;
    private final PlatformSkuOuterIdMappingMapper mappingMapper;

    public Integer upload(MultipartFile file, UploadTypeEnum uploadType, UploadShopNameEnum uploadShopName) {
        List<Row> rows = null;
        Sheet sheet = null;

        try {
            sheet = ObjectFieldHandler.getSheet0ByFile(file);
            rows = ObjectFieldHandler.getRowsBySheet(sheet, 0);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        if (ObjectUtil.isNull(uploadType)) {
            uploadType = UploadTypeEnum.getBySheetName(sheet.getSheetName());
        }
        switch (uploadType) {
            case ORDER:
                uploadOrder(rows, uploadShopName);
                break;
            case PURCHASE:
                uploadPurchase(rows);
                break;
            case INVENTORY:
                uploadInventory(rows);
                break;
            case AFTER_SALE:
                uploadAfterSale(rows, uploadShopName);
                break;
            case AFTER_SALE_RETURN:
                uploadAfterSaleReturn(rows, uploadShopName);
                break;
            default:
                throw new RuntimeException("暂不支持该类型的导入哦~");
        }
        return rows.size();
    }

    private void uploadOrder(List<Row> rows, UploadShopNameEnum uploadShopName) {
        List<List<Row>> rowsList = ObjectFieldHandler.split(rows, 2000);
        rowsList.forEach(rowList -> {
            List<OrderInfo> orderInfos = ObjectFieldHandler.generateListByObj(rowList, row -> {
                Map<String, Object> map = new HashMap<>();
                Arrays.stream(OrderUploadEnum.values()).forEach(e -> {
                    map.put(e.name(), ObjectFieldHandler.getValueByIndex(row, e.ordinal(), e.name()));
                });
                OrderInfo orderInfo = Convert.convert(OrderInfo.class, map);
//                orderInfo.setShopName(uploadShopName.getDesc());
                orderInfo.setShopName(getCurrentShopName(orderInfo.getShopName()));

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
                return orderInfo;
            });
            orderInfoMapper.replaceBatch(orderInfos);
        });
    }

    private void uploadPurchase(List<Row> rows) {
        List<List<Row>> rowsList = ObjectFieldHandler.split(rows, 2000);
        rowsList.forEach(rowList -> {
            List<PurchaseInOrder> orderInfos = ObjectFieldHandler.generateListByObj(rowList, row -> {
                Map<String, Object> map = new HashMap<>();
                Arrays.stream(PurchaseUploadEnum.values()).forEach(e -> {
                    map.put(e.name(), ObjectFieldHandler.getValueByIndex(row, e.ordinal(), e.name()));
                });
                return Convert.convert(PurchaseInOrder.class, map);
            });
            purchaseInOrderMapper.replaceBatch(orderInfos);
        });
    }

    private void uploadInventory(List<Row> rows) {
        List<List<Row>> rowsList = ObjectFieldHandler.split(rows, 2000);
        rowsList.forEach(rowList -> {
            List<Inventory> inventorys = ObjectFieldHandler.generateListByObj(rowList, row -> {
                Map<String, Object> map = new HashMap<>();
                Arrays.stream(InventoryUploadEnum.values()).forEach(e -> {
                    map.put(e.name(), ObjectFieldHandler.getValueByIndex(row, e.ordinal(), e.name()));
                });
                return Convert.convert(Inventory.class, map);
            });
            inventoryMapper.replaceBatch(inventorys);
        });
    }

    private void uploadAfterSale(List<Row> rows, UploadShopNameEnum uploadShopName) {
        List<List<Row>> rowsList = ObjectFieldHandler.split(rows, 2000);
        rowsList.forEach(rowList -> {
            List<AfterSaleOrder> afterSales = ObjectFieldHandler.generateListByObj(rowList, row -> {
                Map<String, Object> map = new HashMap<>();
                Arrays.stream(AfterSaleUploadEnum.values()).forEach(e -> {
                    map.put(e.name(), ObjectFieldHandler.getValueByIndex(row, e.ordinal(), e.name()));
                });
                AfterSaleOrder orderInfo = Convert.convert(AfterSaleOrder.class, map);
//                orderInfo.setShopName(uploadShopName.getDesc());
                orderInfo.setShopName(getCurrentShopName(orderInfo.getShopName()));

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
                return orderInfo;
            });
            afterSaleOrderMapper.replaceBatch(afterSales);
        });
    }


    private void uploadAfterSaleReturn(List<Row> rows, UploadShopNameEnum uploadShopName) {
        List<List<Row>> rowsList = ObjectFieldHandler.split(rows, 2000);
        rowsList.forEach(rowList -> {
            List<AfterSaleReturnOrder> afterSaleReturns = ObjectFieldHandler.generateListByObj(rowList, row -> {
                Map<String, Object> map = new HashMap<>();
                Arrays.stream(AfterSaleReturnUploadEnum.values()).forEach(e -> {
                    map.put(e.name(), ObjectFieldHandler.getValueByIndex(row, e.ordinal(), e.name()));
                });
                AfterSaleReturnOrder orderInfo = Convert.convert(AfterSaleReturnOrder.class, map);
//                orderInfo.setShopName(uploadShopName.getDesc());
                orderInfo.setShopName(getCurrentShopName(orderInfo.getShopName()));

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
                return orderInfo;
            });

            afterSaleReturnOrderMapper.replaceBatch(afterSaleReturns);
        });
    }

    public List<SelectVo> uploadType() {
        return ObjectFieldHandler.enumToSelectVoDesc(UploadTypeEnum.values());
    }

    public List<SelectVo> uploadShopNameType() {
        return ObjectFieldHandler.enumToSelectVoDesc(UploadShopNameEnum.values());
    }

    public static String getCurrentShopName(String shopName) {
        if (StrUtil.isBlank(shopName)) {
            throw new RuntimeException("店铺为空");
        }
        if (shopName.contains("爱他美")) {
            return "爱他美旗舰店";
        }
        if (shopName.contains("诺优能")) {
            return "诺优能官方旗舰店";
        }
        return null;
    }
}
