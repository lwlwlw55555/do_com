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

    public static List<String> shuadanOrders = CollectionUtil.newArrayList("230713-518924573452384", "230713-514368490290947",
            "230713-452125018901795", "230713-188208967263731", "230713-191417568140435", "230713-454389974442617", "230713-576307916320937",
            "230713-137290106470590", "230713-140954900031767", "230713-111238194101671", "230713-632988712383422", "230713-385026672060374",
            "230713-602223482501314", "230713-622634003923435", "230713-205248295811454", "230713-285978183282197", "230713-176291879782101",
            "230713-368432935773551", "230713-354114620212171", "230713-446446979922989", "230713-041261526642816", "230713-229522871733464",
            "230713-581760480021605", "230713-340965518851830", "230713-233486457713839", "230713-175494931271353", "230713-402600836700216",
            "230713-384858930670842", "230713-504690144000293", "230713-512502087243487", "230713-165077349131156", "230713-011639244260487",
            "230713-177151691643826", "230713-390704721301942", "230713-203014828820546", "230713-504365085641681", "230713-521184244780893",
            "230713-574231683963874", "230713-011974798823806", "230713-064120431422001", "230713-053802453893421", "230713-499929650792999",
            "230713-038000403331175", "230713-403832893070868", "230713-497051247633055", "230713-509068000810112", "230713-031918714342331",
            "230713-374236835353696", "230713-200246588371051", "230713-529373602760653", "230713-139030700803515", "230713-390175138583179",
            "230713-505838314290794", "230713-128219882341299", "230713-088153824303096", "230713-510499296954028", "230713-139953489541309",
            "230713-482722498210196", "230713-385215384383482", "230713-403497358773271", "230713-248030196550597", "230713-444774491130237", "230713-179369440111606", "230713-409117705800220", "230713-508019362752349", "230713-102309621202565", "230713-159152916111052", "230713-026911774273696", "230713-483550862333825", "230713-441602620800102", "230713-353003191893887", "230713-190547260361269", "230713-371620638093126", "230713-255039937442776", "230713-358707414660778", "230713-652298219022306", "230713-039017522132877", "230713-627583261522152", "230713-590039018972345", "230713-480179701173266", "230713-437717647021410", "230713-311201678840713", "230713-029527981650861", "230713-242493705061460", "230713-086098605020111", "230713-582672751453362", "230713-584193156021097", "230713-637544765030405", "230713-522133195831277", "230713-369035888082176", "230713-074642953861635", "230713-213259416511220", "230713-669400441652748", "230713-394710271672510", "230713-586007223301058", "230713-127360060393159", "230713-082051122671544", "230713-140656045563400", "230713-506095277492612", "230713-634603509320466", "230713-152263740442097", "230713-346134977513914", "230713-182567586680001", "230713-269803898391764", "230713-213453403163093", "230713-267439317860687", "230713-031520276032610", "230713-539072951331827", "230713-202102588980888", "230713-191585340383748", "230713-493402244581666", "230713-360122981271458", "230713-659806023083528", "230713-279760096053758", "230713-491289363910650", "230713-375841156611409", "230713-551236484531951", "230713-027745371691005", "230713-393556848072699", "230713-404850011800025", "230713-034964786210263", "230713-301025269332683", "230713-061687766040197", "230713-484363550312920", "230713-033816595251156", "230713-307825264121479", "230713-580019875431004", "230713-214648779582269", "230713-343749508621113", "230713-170603344883798", "230713-433654384072444", "230713-193640538973029", "230713-000235990551331", "230713-392555468253174", "230713-578331678263834", "230713-276682525661569", "230713-644622591013412", "230713-245555598740619", "230713-025595811312844", "230713-424002200512973", "230713-460749546323161", "230713-282302924353663", "230713-281941175731194", "230713-477023466542477", "230713-649393622052421", "230713-319060714453207", "230713-560851895841829", "230713-544661851193319", "230713-357297048360625", "230713-529761617313263", "230713-241675815820115", "230713-099431290341186", "230713-098671041492784", "230713-203874702952621", "230713-134637188130765", "230713-139067431970074", "230713-209657589282770", "230713-171699148450279", "230713-067842928292976", "230713-327894967150469", "230713-440601200112829", "230713-462296195940156", "230713-052995070853532", "230713-225134591440631", "230713-172516996033831", "230713-440265634820702", "230713-571615528543610", "230713-511306658591039", "230713-167043408883070", "230713-115762841153635", "230713-281705214943369", "230713-551236474410674", "230713-180963307043062", "230713-005048954403102", "230713-309476771402519", "230713-105025411990378", "230713-390468739983337", "230713-000985742994015", "230713-645173155251932", "230713-142380921763623", "230713-432915138223027", "230713-678229493151970", "230713-343974880122873", "230713-499709429142985", "230713-034188901871617", "230713-049754960760884", "230713-640312963953608", "230713-034582055682641", "230713-292930231103316", "230713-301801173890221", "230713-480945182332696", "230713-122484192101380", "230713-566954576720936", "230713-296799508113907", "230713-042430636852970", "230713-446242497544063", "230713-334039664251674", "230713-346753616922433", "230713-681962412992629", "230713-534574601723729", "230713-367892898642053", "230713-654799072800947", "230713-266527098523440", "230713-047998575642312", "230713-446216293143728", "230713-381817998221752", "230713-556856810391721", "230713-123952208832220", "230713-026801621971823", "230713-129216029672327", "230713-521352068522406", "230713-142774147913630", "230713-424767723191132", "230713-222151351343809", "230713-537442456984032", "230713-052114308660290", "230713-522536907833396", "230713-257058467320859", "230713-577498049971258", "230713-107190711260489", "230713-009217064521052", "230713-103017399782486", "230713-654426848680839", "230713-535382015663865", "230713-277652458401126", "230713-430083971920675", "230713-654799062713632", "230713-292500325892367", "230713-217600521003821", "230713-221889228240171",
            "230713-555341628802477", "230713-357155542573650", "230713-653294356031928", "230713-156594411131750", "230713-087267746720035", "230713-357962935820712", "230713-018082774462949", "230713-336073870120171", "230713-097003847322343", "230713-323490958143169");

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
                    map.put(e.name(), ObjectFieldHandler.getValueByIndex(row, e.ordinal(), e.name(), UploadTypeEnum.ORDER));
                });
                OrderInfo orderInfo = Convert.convert(OrderInfo.class, map);
//                orderInfo.setShopName(uploadShopName.getDesc());
                orderInfo.setShopName(getCurrentShopName(orderInfo.getShopName()));
                if (CollectionUtil.contains(shuadanOrders, orderInfo.getPlatformOrderSn())) {
                    orderInfo.setSellerNote("不回传");
                }

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
                    map.put(e.name(), ObjectFieldHandler.getValueByIndex(row, e.ordinal(), e.name(), UploadTypeEnum.AFTER_SALE_RETURN));
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
