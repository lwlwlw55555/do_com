package com.domdd.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class NormalOrderInfo implements Serializable {
    private Long orderId;

    private Long orderGoodsId;

    private String orderSn;

    private String platformOrderSn;

    private String goodsName;

    private String platformOrderGoodsSn;

    private String platformOrderStatus;

    private String provinceName;

    private String cityName;

    private String districtName;

    private String shippingAddress;

    private String mobile;

    private String receiveName;

    private String platformSkuId;

    private String styleValue;

    private String platformGoodsId;

    private String sellerNote;

    private String refundStatus;

    private String trackingNumber;

    private String shippingName;

    private String outerId;

    private Date shippingTime;

    private String shopName;

    private Date payTime;

    private BigDecimal goodsAmount;

    private BigDecimal payAmount;

    private BigDecimal orderAmount;

    private BigDecimal sellerDiscount;

    private BigDecimal platformDiscount;

    private Integer goodsNumber;

    private Integer skuNumber;

    private Date createdTime;

    private Date lastUpdatedTime;

    private static final long serialVersionUID = 1L;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getOrderGoodsId() {
        return orderGoodsId;
    }

    public void setOrderGoodsId(Long orderGoodsId) {
        this.orderGoodsId = orderGoodsId;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn == null ? null : orderSn.trim();
    }

    public String getPlatformOrderSn() {
        return platformOrderSn;
    }

    public void setPlatformOrderSn(String platformOrderSn) {
        this.platformOrderSn = platformOrderSn == null ? null : platformOrderSn.trim();
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName == null ? null : goodsName.trim();
    }

    public String getPlatformOrderGoodsSn() {
        return platformOrderGoodsSn;
    }

    public void setPlatformOrderGoodsSn(String platformOrderGoodsSn) {
        this.platformOrderGoodsSn = platformOrderGoodsSn == null ? null : platformOrderGoodsSn.trim();
    }

    public String getPlatformOrderStatus() {
        return platformOrderStatus;
    }

    public void setPlatformOrderStatus(String platformOrderStatus) {
        this.platformOrderStatus = platformOrderStatus == null ? null : platformOrderStatus.trim();
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName == null ? null : provinceName.trim();
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName == null ? null : cityName.trim();
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName == null ? null : districtName.trim();
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress == null ? null : shippingAddress.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName == null ? null : receiveName.trim();
    }

    public String getPlatformSkuId() {
        return platformSkuId;
    }

    public void setPlatformSkuId(String platformSkuId) {
        this.platformSkuId = platformSkuId == null ? null : platformSkuId.trim();
    }

    public String getStyleValue() {
        return styleValue;
    }

    public void setStyleValue(String styleValue) {
        this.styleValue = styleValue == null ? null : styleValue.trim();
    }

    public String getPlatformGoodsId() {
        return platformGoodsId;
    }

    public void setPlatformGoodsId(String platformGoodsId) {
        this.platformGoodsId = platformGoodsId == null ? null : platformGoodsId.trim();
    }

    public String getSellerNote() {
        return sellerNote;
    }

    public void setSellerNote(String sellerNote) {
        this.sellerNote = sellerNote == null ? null : sellerNote.trim();
    }

    public String getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(String refundStatus) {
        this.refundStatus = refundStatus == null ? null : refundStatus.trim();
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber == null ? null : trackingNumber.trim();
    }

    public String getShippingName() {
        return shippingName;
    }

    public void setShippingName(String shippingName) {
        this.shippingName = shippingName == null ? null : shippingName.trim();
    }

    public String getOuterId() {
        return outerId;
    }

    public void setOuterId(String outerId) {
        this.outerId = outerId == null ? null : outerId.trim();
    }

    public Date getShippingTime() {
        return shippingTime;
    }

    public void setShippingTime(Date shippingTime) {
        this.shippingTime = shippingTime;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName == null ? null : shopName.trim();
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public BigDecimal getGoodsAmount() {
        return goodsAmount;
    }

    public void setGoodsAmount(BigDecimal goodsAmount) {
        this.goodsAmount = goodsAmount;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public BigDecimal getSellerDiscount() {
        return sellerDiscount;
    }

    public void setSellerDiscount(BigDecimal sellerDiscount) {
        this.sellerDiscount = sellerDiscount;
    }

    public BigDecimal getPlatformDiscount() {
        return platformDiscount;
    }

    public void setPlatformDiscount(BigDecimal platformDiscount) {
        this.platformDiscount = platformDiscount;
    }

    public Integer getGoodsNumber() {
        return goodsNumber;
    }

    public void setGoodsNumber(Integer goodsNumber) {
        this.goodsNumber = goodsNumber;
    }

    public Integer getSkuNumber() {
        return skuNumber;
    }

    public void setSkuNumber(Integer skuNumber) {
        this.skuNumber = skuNumber;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public void setLastUpdatedTime(Date lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        NormalOrderInfo other = (NormalOrderInfo) that;
        return (this.getOrderId() == null ? other.getOrderId() == null : this.getOrderId().equals(other.getOrderId()))
            && (this.getOrderGoodsId() == null ? other.getOrderGoodsId() == null : this.getOrderGoodsId().equals(other.getOrderGoodsId()))
            && (this.getOrderSn() == null ? other.getOrderSn() == null : this.getOrderSn().equals(other.getOrderSn()))
            && (this.getPlatformOrderSn() == null ? other.getPlatformOrderSn() == null : this.getPlatformOrderSn().equals(other.getPlatformOrderSn()))
            && (this.getGoodsName() == null ? other.getGoodsName() == null : this.getGoodsName().equals(other.getGoodsName()))
            && (this.getPlatformOrderGoodsSn() == null ? other.getPlatformOrderGoodsSn() == null : this.getPlatformOrderGoodsSn().equals(other.getPlatformOrderGoodsSn()))
            && (this.getPlatformOrderStatus() == null ? other.getPlatformOrderStatus() == null : this.getPlatformOrderStatus().equals(other.getPlatformOrderStatus()))
            && (this.getProvinceName() == null ? other.getProvinceName() == null : this.getProvinceName().equals(other.getProvinceName()))
            && (this.getCityName() == null ? other.getCityName() == null : this.getCityName().equals(other.getCityName()))
            && (this.getDistrictName() == null ? other.getDistrictName() == null : this.getDistrictName().equals(other.getDistrictName()))
            && (this.getShippingAddress() == null ? other.getShippingAddress() == null : this.getShippingAddress().equals(other.getShippingAddress()))
            && (this.getMobile() == null ? other.getMobile() == null : this.getMobile().equals(other.getMobile()))
            && (this.getReceiveName() == null ? other.getReceiveName() == null : this.getReceiveName().equals(other.getReceiveName()))
            && (this.getPlatformSkuId() == null ? other.getPlatformSkuId() == null : this.getPlatformSkuId().equals(other.getPlatformSkuId()))
            && (this.getStyleValue() == null ? other.getStyleValue() == null : this.getStyleValue().equals(other.getStyleValue()))
            && (this.getPlatformGoodsId() == null ? other.getPlatformGoodsId() == null : this.getPlatformGoodsId().equals(other.getPlatformGoodsId()))
            && (this.getSellerNote() == null ? other.getSellerNote() == null : this.getSellerNote().equals(other.getSellerNote()))
            && (this.getRefundStatus() == null ? other.getRefundStatus() == null : this.getRefundStatus().equals(other.getRefundStatus()))
            && (this.getTrackingNumber() == null ? other.getTrackingNumber() == null : this.getTrackingNumber().equals(other.getTrackingNumber()))
            && (this.getShippingName() == null ? other.getShippingName() == null : this.getShippingName().equals(other.getShippingName()))
            && (this.getOuterId() == null ? other.getOuterId() == null : this.getOuterId().equals(other.getOuterId()))
            && (this.getShippingTime() == null ? other.getShippingTime() == null : this.getShippingTime().equals(other.getShippingTime()))
            && (this.getShopName() == null ? other.getShopName() == null : this.getShopName().equals(other.getShopName()))
            && (this.getPayTime() == null ? other.getPayTime() == null : this.getPayTime().equals(other.getPayTime()))
            && (this.getGoodsAmount() == null ? other.getGoodsAmount() == null : this.getGoodsAmount().equals(other.getGoodsAmount()))
            && (this.getPayAmount() == null ? other.getPayAmount() == null : this.getPayAmount().equals(other.getPayAmount()))
            && (this.getOrderAmount() == null ? other.getOrderAmount() == null : this.getOrderAmount().equals(other.getOrderAmount()))
            && (this.getSellerDiscount() == null ? other.getSellerDiscount() == null : this.getSellerDiscount().equals(other.getSellerDiscount()))
            && (this.getPlatformDiscount() == null ? other.getPlatformDiscount() == null : this.getPlatformDiscount().equals(other.getPlatformDiscount()))
            && (this.getGoodsNumber() == null ? other.getGoodsNumber() == null : this.getGoodsNumber().equals(other.getGoodsNumber()))
            && (this.getSkuNumber() == null ? other.getSkuNumber() == null : this.getSkuNumber().equals(other.getSkuNumber()))
            && (this.getCreatedTime() == null ? other.getCreatedTime() == null : this.getCreatedTime().equals(other.getCreatedTime()))
            && (this.getLastUpdatedTime() == null ? other.getLastUpdatedTime() == null : this.getLastUpdatedTime().equals(other.getLastUpdatedTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getOrderId() == null) ? 0 : getOrderId().hashCode());
        result = prime * result + ((getOrderGoodsId() == null) ? 0 : getOrderGoodsId().hashCode());
        result = prime * result + ((getOrderSn() == null) ? 0 : getOrderSn().hashCode());
        result = prime * result + ((getPlatformOrderSn() == null) ? 0 : getPlatformOrderSn().hashCode());
        result = prime * result + ((getGoodsName() == null) ? 0 : getGoodsName().hashCode());
        result = prime * result + ((getPlatformOrderGoodsSn() == null) ? 0 : getPlatformOrderGoodsSn().hashCode());
        result = prime * result + ((getPlatformOrderStatus() == null) ? 0 : getPlatformOrderStatus().hashCode());
        result = prime * result + ((getProvinceName() == null) ? 0 : getProvinceName().hashCode());
        result = prime * result + ((getCityName() == null) ? 0 : getCityName().hashCode());
        result = prime * result + ((getDistrictName() == null) ? 0 : getDistrictName().hashCode());
        result = prime * result + ((getShippingAddress() == null) ? 0 : getShippingAddress().hashCode());
        result = prime * result + ((getMobile() == null) ? 0 : getMobile().hashCode());
        result = prime * result + ((getReceiveName() == null) ? 0 : getReceiveName().hashCode());
        result = prime * result + ((getPlatformSkuId() == null) ? 0 : getPlatformSkuId().hashCode());
        result = prime * result + ((getStyleValue() == null) ? 0 : getStyleValue().hashCode());
        result = prime * result + ((getPlatformGoodsId() == null) ? 0 : getPlatformGoodsId().hashCode());
        result = prime * result + ((getSellerNote() == null) ? 0 : getSellerNote().hashCode());
        result = prime * result + ((getRefundStatus() == null) ? 0 : getRefundStatus().hashCode());
        result = prime * result + ((getTrackingNumber() == null) ? 0 : getTrackingNumber().hashCode());
        result = prime * result + ((getShippingName() == null) ? 0 : getShippingName().hashCode());
        result = prime * result + ((getOuterId() == null) ? 0 : getOuterId().hashCode());
        result = prime * result + ((getShippingTime() == null) ? 0 : getShippingTime().hashCode());
        result = prime * result + ((getShopName() == null) ? 0 : getShopName().hashCode());
        result = prime * result + ((getPayTime() == null) ? 0 : getPayTime().hashCode());
        result = prime * result + ((getGoodsAmount() == null) ? 0 : getGoodsAmount().hashCode());
        result = prime * result + ((getPayAmount() == null) ? 0 : getPayAmount().hashCode());
        result = prime * result + ((getOrderAmount() == null) ? 0 : getOrderAmount().hashCode());
        result = prime * result + ((getSellerDiscount() == null) ? 0 : getSellerDiscount().hashCode());
        result = prime * result + ((getPlatformDiscount() == null) ? 0 : getPlatformDiscount().hashCode());
        result = prime * result + ((getGoodsNumber() == null) ? 0 : getGoodsNumber().hashCode());
        result = prime * result + ((getSkuNumber() == null) ? 0 : getSkuNumber().hashCode());
        result = prime * result + ((getCreatedTime() == null) ? 0 : getCreatedTime().hashCode());
        result = prime * result + ((getLastUpdatedTime() == null) ? 0 : getLastUpdatedTime().hashCode());
        return result;
    }
}