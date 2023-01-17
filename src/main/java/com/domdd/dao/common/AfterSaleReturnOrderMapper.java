package com.domdd.dao.common;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.domdd.model.AfterSaleOrder;
import com.domdd.model.AfterSaleReturnOrder;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface AfterSaleReturnOrderMapper extends BaseMapper<AfterSaleReturnOrder> {
    default IPage<AfterSaleReturnOrder> selectByPage(IPage<AfterSaleReturnOrder> page, Date startTime, Date endTime, String shopName) {
        LambdaQueryWrapper<AfterSaleReturnOrder> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AfterSaleReturnOrder::getShopName, shopName);
        wrapper.ge(AfterSaleReturnOrder::getLastUpdatedTime, startTime)
                .lt(AfterSaleReturnOrder::getLastUpdatedTime, endTime)
                .eq(AfterSaleReturnOrder::getAfterSalesStatus, 10)
                .in(AfterSaleReturnOrder::getWarehouseId, 111, 112, 113, 1084, 1085, 1086)
                .ge(AfterSaleReturnOrder::getLastUpdatedTime, DateUtil.parseDate("2021-10-01 00:00:00"))
                .isNotNull(AfterSaleReturnOrder::getRefundId);;
        return this.selectPage(page, wrapper);
    }

    void replaceBatch(@Param("records") List<AfterSaleReturnOrder> records);
}