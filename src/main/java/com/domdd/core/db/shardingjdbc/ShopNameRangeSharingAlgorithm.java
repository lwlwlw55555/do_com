package com.domdd.core.db.shardingjdbc;

import io.shardingsphere.api.algorithm.sharding.PreciseShardingValue;
import io.shardingsphere.api.algorithm.sharding.RangeShardingValue;
import io.shardingsphere.api.algorithm.sharding.ShardingValue;
import io.shardingsphere.api.algorithm.sharding.standard.RangeShardingAlgorithm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author lw
 * @date 2022/3/7 1:58 下午
 */
public class ShopNameRangeSharingAlgorithm implements RangeShardingAlgorithm {

    @Override
    public Collection<String> doSharding(Collection availableTargetNames, RangeShardingValue shardingValue) {
        List<String> result = new ArrayList<>();
        long index = getIndex(shardingValue);
        availableTargetNames.forEach(
                name -> {
                    if (name.equals(String.valueOf(index))) {
                        result.add((String) name);
                        return;
                    }
                }
        );
        return result;
    }

    private long getIndex(ShardingValue value) {
        if ("诺优能官方旗舰店".equals(value.getColumnName())) {
            return 1;
        }
        return 0;
    }
}
