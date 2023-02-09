package com.domdd.dao.common;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.BooleanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.domdd.model.OrderInfo;
import com.domdd.service.OpenService;
import org.apache.ibatis.annotations.Param;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public interface OrderInfoMapper extends BaseMapper<OrderInfo> {

    default IPage<OrderInfo> selectByPage(IPage<OrderInfo> page, String timeType, Date startTime, Date endTime, String shopName, Boolean isRefund, String orderType, List<String> latestIgnoreOuterIdList) {
        LambdaQueryWrapper<OrderInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OrderInfo::getShopName, shopName);
//                .eq(OrderInfo::getShippingUserType, "SYSTEM");
        if (Objects.equals(timeType, "pay_time")) {
            wrapper.ge(OrderInfo::getPayTime, startTime)
                    .lt(OrderInfo::getPayTime, endTime);
        } else if (Objects.equals(timeType, "shipping_time")) {
            wrapper.ge(OrderInfo::getShippingTime, startTime)
                    .lt(OrderInfo::getShippingTime, endTime)
//                    .ge(OrderInfo::getShippingTime, DateUtil.parseDate("2023-02-01 00:00:00"))
            ;
        }
//
//        if (StringUtils.isNotBlank(orderType)) {
//            wrapper.eq(OrderInfo::getOrderType, orderType);
//        } else {
//            wrapper.eq(OrderInfo::getOrderType, "SALE");
//        }

        wrapper.notIn(OrderInfo::getOuterId, latestIgnoreOuterIdList);

        wrapper.and(w -> w.isNull(OrderInfo::getSysOuterId).
                or(w1 -> w1.notIn(OrderInfo::getSysOuterId, latestIgnoreOuterIdList)));

        wrapper.notLike(OrderInfo::getSellerNote, "不回传");
        if (BooleanUtil.isTrue(isRefund)) {
            wrapper.in(OrderInfo::getRefundStatus, Arrays.asList("APPLIED", "RETURNED"));
        }
        return this.selectPage(page, wrapper);
    }

    void replaceBatch(@Param("records") List<OrderInfo> records);
}