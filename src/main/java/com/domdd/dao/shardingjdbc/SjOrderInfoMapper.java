package com.domdd.dao.shardingjdbc;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.domdd.model.OrderInfo;
import com.domdd.service.OpenService;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public interface SjOrderInfoMapper extends BaseMapper<OrderInfo> {

    default IPage<OrderInfo> selectByPage(IPage<OrderInfo> page, String timeType, Date startTime, Date endTime, String shopName) {
        LambdaQueryWrapper<OrderInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OrderInfo::getShopName, shopName);
        if (Objects.equals(timeType, "pay_time")) {
            wrapper.ge(OrderInfo::getPayTime, startTime)
                    .lt(OrderInfo::getPayTime, endTime);
        } else if (Objects.equals(timeType, "shipping_time")) {
            wrapper.ge(OrderInfo::getShippingTime, startTime)
                    .lt(OrderInfo::getShippingTime, endTime)
                    .ge(OrderInfo::getShippingTime, DateUtil.parseDate("2021-10-01 00:00:00"));
        }
        wrapper.notIn(OrderInfo::getOuterId, OpenService.ignoreOuterIdList);
        return this.selectPage(page, wrapper);
    }

    default OrderInfo selectByIdAndName(Long id, String shopName) {
        LambdaQueryWrapper<OrderInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OrderInfo::getOrderId, id);
        wrapper.eq(OrderInfo::getShopName, shopName);
        return this.selectOne(wrapper);
    }

    @Select("SELECT * FROM users WHERE id = #{id} and shop_name = #{shopName}")
    OrderInfo selectByIdAndName2(@Param("id") Long id, @Param("shopName") String shopName);

    void replaceBatch(@Param("records") List<OrderInfo> records);

}