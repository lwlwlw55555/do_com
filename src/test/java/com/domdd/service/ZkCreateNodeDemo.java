package com.domdd.service;

/**
 * @author lw
 * @date 2022/2/23 4:59 下午
 */

import org.apache.zookeeper.*;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.Session;
import java.io.IOException;

/*
   java操作zookeeper对象

 */
public class ZkCreateNodeDemo {

    private static final Logger logger = LoggerFactory.getLogger(ZkCreateNodeDemo.class);
    private Session session;

    String url = "121.40.113.153:2181";
//    String url = "121.40.113.153/zk";
    ZooKeeper zk;

    //初始化
    @Before
    public void test01() throws IOException {
        zk = new ZooKeeper(url, 3000, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                String path = event.getPath();                //负责监听的路径
                Event.KeeperState state = event.getState();   //负责监听的状态
                Event.EventType type = event.getType();       //负责监听类型
                logger.info("路径为=" + path);
                logger.info("状态为=" + state);
                logger.info("监听类型=" + type);
            }
        });
    }

    //创建节点
    @Test
    public void createNode() throws Exception {
        try {
//            zk.create("/", "lw0".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            zk.create("/a1/lw1/lw12", "lw".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            zk.create("/b2/lw2/lw22", "lw12".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            zk.create("/c3/lw3/lw33", "lw123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
//            zk.create("/lw123/lw3/lw3", "lw123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
//            zk.addWatch();
        } catch (Exception ex) {
            logger.info("zookeeper创建失败！");
            ex.printStackTrace();
        }
    }


    //删除节点
    @Test
    public void deleteNode() throws Exception {
        zk.delete("/a1", -1);
    }


    //修改节点
    @Test
    public void updateNode() throws Exception {
        zk.setData("/b1", "testUpdate".getBytes(), -1);
    }

    //查询节点
    @Test
    public void getNode() throws Exception {
        byte[] data = zk.getData("/c1", true, null);
        System.out.println(new String(data));
    }
}