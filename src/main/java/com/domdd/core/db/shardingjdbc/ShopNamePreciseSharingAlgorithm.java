package com.domdd.core.db.shardingjdbc;

import io.shardingsphere.api.algorithm.sharding.PreciseShardingValue;
import io.shardingsphere.api.algorithm.sharding.ShardingValue;
import io.shardingsphere.api.algorithm.sharding.standard.PreciseShardingAlgorithm;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author lw
 * @date 2022/3/5 4:51 下午
 */
@Slf4j
public class ShopNamePreciseSharingAlgorithm implements PreciseShardingAlgorithm {

    @Override
    public String doSharding(Collection availableTargetNames, PreciseShardingValue shardingValue) {
        AtomicReference<String> result = new AtomicReference<>();
        long index = getIndex(shardingValue);
        availableTargetNames.forEach(
                name -> {
                    if (name.equals(String.valueOf(index))) {
                        result.set((String) name);
                        return;
                    }
                }
        );
        return result.get();
    }

    private long getIndex(PreciseShardingValue value) {
        if ("诺优能官方旗舰店".equals(value.getValue())) {
            return 1;
        }
        return 0;
    }
}
