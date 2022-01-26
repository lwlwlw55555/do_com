package com.domdd.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.domdd.model.Inventory;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface InventoryMapper extends BaseMapper<Inventory> {
    default IPage<Inventory> selectByPage(IPage<Inventory> page) {
        LambdaQueryWrapper<Inventory> wrapper = new LambdaQueryWrapper<>();
        return this.selectPage(page, wrapper);
    }

    void replaceBatch(@Param("records") List<Inventory> records);
}