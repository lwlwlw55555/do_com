package com.domdd.dao.common;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.domdd.model.PlatformSkuOuterIdMapping;

public interface PlatformSkuOuterIdMappingMapper extends BaseMapper<PlatformSkuOuterIdMapping> {

    default PlatformSkuOuterIdMapping selectByOuterId(String outerId) {
        LambdaQueryWrapper<PlatformSkuOuterIdMapping> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PlatformSkuOuterIdMapping::getOuterId, outerId)
                .orderByDesc(PlatformSkuOuterIdMapping::getIsSkuOnsale).last("limit 1");
        return this.selectOne(wrapper);
    }
}