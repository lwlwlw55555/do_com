package com.domdd.dao.shardingjdbc;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.domdd.model.PurchaseInOrder;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface SjPurchaseInOrderMapper extends BaseMapper<PurchaseInOrder> {
    default IPage<PurchaseInOrder> selectByPage(IPage<PurchaseInOrder> page, Date startTime, Date endTime) {
        LambdaQueryWrapper<PurchaseInOrder> wrapper = new LambdaQueryWrapper<>();
        wrapper.ge(PurchaseInOrder::getLastUpdatedTime, startTime)
                .lt(PurchaseInOrder::getLastUpdatedTime, endTime);
        return this.selectPage(page, wrapper);
    }

    void replaceBatch(@Param("records") List<PurchaseInOrder> records);
}