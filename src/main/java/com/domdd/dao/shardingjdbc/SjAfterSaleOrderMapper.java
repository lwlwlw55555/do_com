package com.domdd.dao.shardingjdbc;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.domdd.model.AfterSaleOrder;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface SjAfterSaleOrderMapper extends BaseMapper<AfterSaleOrder> {
    default IPage<AfterSaleOrder> selectByPage(IPage<AfterSaleOrder> page, Date startTime, Date endTime, String shopName) {
        LambdaQueryWrapper<AfterSaleOrder> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AfterSaleOrder::getShopName, shopName);
        wrapper.ge(AfterSaleOrder::getLastUpdatedTime, startTime)
                .lt(AfterSaleOrder::getLastUpdatedTime, endTime)
                .ge(AfterSaleOrder::getLastUpdatedTime, DateUtil.parseDate("2021-10-01 00:00:00"));
        return this.selectPage(page, wrapper);
    }

    void replaceBatch(@Param("records") List<AfterSaleOrder> records);
}