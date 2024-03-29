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

public interface AfterSaleOrderMapper extends BaseMapper<AfterSaleOrder> {
    default IPage<AfterSaleOrder> selectByPage(IPage<AfterSaleOrder> page, Date startTime, Date endTime, String shopName) {
        LambdaQueryWrapper<AfterSaleOrder> wrapper = new LambdaQueryWrapper<>();
//        wrapper.eq(AfterSaleOrder::getShopName, shopName);
        wrapper.ge(AfterSaleOrder::getLastUpdatedTime, startTime)
                .lt(AfterSaleOrder::getLastUpdatedTime, endTime)
                .eq(AfterSaleOrder::getAfterSalesStatus, 10)
                .eq(AfterSaleOrder::getReturnStatus, "DONE")
                // todo 废弃退款单
                .eq(AfterSaleOrder::getShopName, "LW_TEST")
//                .ge(AfterSaleOrder::getLastUpdatedTime, DateUtil.parseDate("2023-02-01 00:00:00"))
//                .last(" and 1=0")
//                .isNotNull(AfterSaleOrder::getRefundId);
        ;
        return this.selectPage(page, wrapper);
    }

    void replaceBatch(@Param("records") List<AfterSaleOrder> records);
}