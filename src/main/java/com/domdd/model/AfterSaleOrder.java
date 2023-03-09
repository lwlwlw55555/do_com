package com.domdd.model;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Pattern;

@Data
@TableName(value = "after_sale_order")
public class AfterSaleOrder implements Serializable {
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "订单id 主键")
    private Long orderId;

    @ApiModelProperty(value = "平台订单号")
    private String platformOrderSn;

    @ApiModelProperty(value = "店铺名称")
    private String shopName;

    @ApiModelProperty(value = "商品编码")
    private String platformOrderGoodsSn;

    @ApiModelProperty(value = "售后原因")
    private String afterSaleReason;

    @ApiModelProperty(value = "sku编码")
    private String outerId;

    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    @ApiModelProperty(value = "sku名称")
    private String skuName;

    @ApiModelProperty(value = "平台商品id")
    private String platformGoodsId;

    @ApiModelProperty(value = "平台商品名称")
    private String platformGoodsName;

    @ApiModelProperty(value = "sku规格")
    private String styleValue;

    @ApiModelProperty(value = "售后类型 1：全部 2：仅退款 3：退货退款 4：换货 5：缺货补寄")
    private Integer afterSalesType;

    @ApiModelProperty(value = "售后状态 1：全部 2：买家申请退款，待商家处理 3：退货退款，待商家处理 4：商家同意退款，退款中 5：平台同意退款，退款中 6：驳回退款， 待买家处理 7：已同意退货退款,待用户发货 8：平台处理中 9：平台拒 绝退款，退款关闭 10：退款成功 11：买家撤销 12：买家逾期未处 理，退款失败 13：买家逾期，超过有效期 14 : 换货补寄待商家处理 15:换货补寄待用户处理 16:换货补寄成功 17:换货补寄失败 18:换货补寄待用户确认完成")
    private Integer afterSalesStatus;

    @ApiModelProperty(value = "商品数量")
    private Integer goodsNumber;

    @ApiModelProperty(value = "售后金额")
    private BigDecimal refundAmount;

    @ApiModelProperty(value = "售后时间")
    private Date refundTime;

    @ApiModelProperty(value = "售后创建时间")
    private Date refundCreatedTime;

    @ApiModelProperty(value = "平台退款id")
    private Long refundId;

    @ApiModelProperty(value = "创建时间")
    private Date createdTime;

    @ApiModelProperty(value = "更新时间")
    private Date lastUpdatedTime;

    @JSONField(serialize = false)
    @ApiModelProperty(value = "主键")
    private Long orderGoodsId;

    @JSONField(serialize = false)
    @ApiModelProperty(value = "系统订单号")
    private String orderSn;

    @JSONField(serialize = false)
    @ApiModelProperty(value = "售后更新时间")
    private Date refundUpdatedTime;

    @JSONField(serialize = false)
    private Boolean isManual;

    @JSONField(serialize = false)
    private String returnStatus;

    @JSONField(serialize = false)
    private Long preRefundId;

    public static void checkParams(List<AfterSaleOrder> afterSaleOrderList) {
//        afterSaleOrderList.forEach(afterSaleOrder -> {
//            if (ObjectUtil.isNull(afterSaleOrder.refundId)) {
//                if (StrUtil.isNotBlank(afterSaleOrder.orderSn)) {
//                    List<String> parseGroup = ReUtil.findAllGroup0("\\d+", afterSaleOrder.orderSn);
//                    if (CollectionUtil.isNotEmpty(parseGroup)) {
//                        afterSaleOrder.setRefundId(Long.parseLong(parseGroup.get(0)));
//                    }
//                } else {
//                    afterSaleOrder.setRefundId(RandomUtil.randomLong());
//                }
//            }
//        });
    }
}