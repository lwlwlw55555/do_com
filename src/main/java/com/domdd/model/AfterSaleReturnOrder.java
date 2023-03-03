package com.domdd.model;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.*;
import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Data
@TableName(value = "after_sale_return_order")
public class AfterSaleReturnOrder implements Serializable {
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "订单id 主键")
    private Long orderId;

    @ApiModelProperty(value = "平台订单号")
    private String platformOrderSn;

    @ApiModelProperty(value = "商品编码")
    private String platformOrderGoodsSn;

    @ApiModelProperty(value = "退货原因")
    private String afterSaleReason;

    @ApiModelProperty(value = "店铺名称")
    private String shopName;

    @ApiModelProperty(value = "快递名称")
    private String shippingName;

    @ApiModelProperty(value = "面单号")
    private String trackingNumber;

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

    @ApiModelProperty(value = "商品类型 正常/残损 COMMON/DEFECTIVE")
    private String goodsSubType;

    @ApiModelProperty(value = "售后类型 1：全部 2：仅退款 3：退货退款 4：换货 5：缺货补寄")
    private Integer afterSalesType;

    @ApiModelProperty(value = "售后状态 1：全部 2：买家申请退款，待商家处理 3：退货退款，待商家处理 4：商家同意退款，退款中 5：平台同意退款，退款中 6：驳回退款， 待买家处理 7：已同意退货退款,待用户发货 8：平台处理中 9：平台拒 绝退款，退款关闭 10：退款成功 11：买家撤销 12：买家逾期未处 理，退款失败 13：买家逾期，超过有效期 14 : 换货补寄待商家处理 15:换货补寄待用户处理 16:换货补寄成功 17:换货补寄失败 18:换货补寄待用户确认完成")
    private Integer afterSalesStatus;

    @ApiModelProperty(value = "仓库id")
    private Integer warehouseId;

    @ApiModelProperty(value = "仓库名称")
    private String warehouseName;

    @ApiModelProperty(value = "商品数量")
    private Integer goodsNumber;

    @ApiModelProperty(value = "sku数量")
    private Integer quantity;

    @ApiModelProperty(value = "售后创建时间")
    private Date refundCreatedTime;

    @ApiModelProperty(value = "售后单创建时间")
    private Date returnOrderCreatedTime;

    /**
     * refundId 要先写好
     */
    @ApiModelProperty(value = "平台售后id")
    private Long refundId;

    @ApiModelProperty(value = "创建时间")
    private Date createdTime;

    @ApiModelProperty(value = "更新时间")
    private Date lastUpdatedTime;

    @ApiModelProperty(value = "主键")
    private Long orderGoodsId;

    @ApiModelProperty(value = "系统订单号")
    private String orderSn;

    @ApiModelProperty(value = "售后更新时间")
    private Date refundUpdatedTime;

    @JSONField(serialize = false)
    private Boolean isManual;

    private String returnStatus;

    @JSONField(serialize = false)
    private Long preRefundId;

    @JSONField(serialize = false)
    private Integer preQuantity;

    @ApiModelProperty(value = "订单类型 RETURN 退货， REJECT 拒收，CHANGE 换货，RESHIP 补发")
    @JSONField(serialize = false)
    private String orderType;

    @JSONField(serialize = false)
    private Integer preAfterSalesType;

    public static void checkParams(List<AfterSaleReturnOrder> afterSaleReturnOrderList) {
//        afterSaleReturnOrderList.forEach(afterSaleReturnOrder -> {
//            // TODO: 2023/2/3 refundId 要先写好
//            if (ObjectUtil.isNull(afterSaleReturnOrder.refundId)) {
//                if (StrUtil.isNotBlank(afterSaleReturnOrder.orderSn)) {
//                    List<String> parseGroup = ReUtil.findAllGroup0("\\d+", afterSaleReturnOrder.orderSn);
//                    if (CollectionUtil.isNotEmpty(parseGroup)) {
//                        afterSaleReturnOrder.setRefundId(Long.parseLong(parseGroup.get(0)));
//                    }
//                } else {
//                    afterSaleReturnOrder.setRefundId(RandomUtil.randomLong());
//                }
//            }
//        });
    }

    public Integer getQuantity() {
        if (ObjectUtil.isNull(quantity) || Objects.equals(quantity, 0)) {
            return goodsNumber;
        }
        return quantity;
    }
}