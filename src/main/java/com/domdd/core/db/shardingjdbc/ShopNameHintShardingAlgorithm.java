package com.domdd.core.db.shardingjdbc;

import cn.hutool.core.collection.CollectionUtil;
import io.shardingsphere.api.algorithm.sharding.ListShardingValue;
import io.shardingsphere.api.algorithm.sharding.PreciseShardingValue;
import io.shardingsphere.api.algorithm.sharding.ShardingValue;
import io.shardingsphere.api.algorithm.sharding.hint.HintShardingAlgorithm;
import org.apache.commons.collections.CollectionUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author lw
 * @date 2022/3/8 3:06 下午
 */
public class ShopNameHintShardingAlgorithm implements HintShardingAlgorithm {

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, ShardingValue shardingValue) {
        ListShardingValue value = (ListShardingValue) shardingValue;
        return value.getValues();
    }

//    private long getIndex(ShardingValue value) {
//        if ("诺优能官方旗舰店".equals(value.getColumnName())) {
//            return 1;
//        }
//        return 0;
//    }
}
