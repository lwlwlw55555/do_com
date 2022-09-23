package com.domdd.core;

import com.domdd.core.db.ZkProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * @author lw
 * @date 2022/2/23 6:37 下午
 */
@Slf4j
@Configuration
public class ZkConfig {

    @Bean
    public ZooKeeper zooKeeper(ZkProperties zkProperties) throws IOException {
        if (zkProperties.getUrl() == null) {
            return null;
        }
        return new ZooKeeper(zkProperties.getUrl(), 3000, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                String path = event.getPath();                //负责监听的路径
                Event.KeeperState state = event.getState();   //负责监听的状态
                Event.EventType type = event.getType();       //负责监听类型
                log.info("路径为=" + path);
                log.info("状态为=" + state);
                log.info("监听类型=" + type);
            }
        });
    }
}
