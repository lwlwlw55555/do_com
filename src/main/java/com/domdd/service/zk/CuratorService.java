//package com.domdd.service.zk;
//
//import org.apache.curator.RetryPolicy;
//import org.apache.curator.framework.CuratorFramework;
//import org.apache.curator.framework.CuratorFrameworkFactory;
//import org.apache.curator.framework.recipes.locks.InterProcessMutex;
//import org.apache.curator.retry.ExponentialBackoffRetry;
//import org.apache.zookeeper.CreateMode;
//import org.apache.zookeeper.data.Stat;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.concurrent.Executor;
//import java.util.concurrent.Executors;
//
///**
// * @author lw
// * @date 2022/2/24 3:46 下午
// */
//@Service
//public class CuratorService {
//
//    @Autowired
//    public void testCurator() throws Exception {
//        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
////        CuratorFramework client = CuratorFrameworkFactory.newClient("121.40.113.153:2181",
////                5000, 5000, retryPolicy);
//        CuratorFramework client = CuratorFrameworkFactory.builder().connectString("121.40.113.153:2181").sessionTimeoutMs(5000)  // 会话超时时间
//                .connectionTimeoutMs(5000) // 连接超时时间
//                .retryPolicy(retryPolicy)
//                .namespace("lw") // 包含隔离名称
//                .build();
//        client.start();
//
////        client.create().creatingParentContainersIfNeeded() // 递归创建所需父节点
////                .withMode(CreateMode.PERSISTENT) // 创建类型为持久节点
////                .forPath("/nodeA", "init".getBytes()); // 目录及内容
//
////        byte[] bytes = client.getData().forPath("/nodeA");
////        System.out.println(new String(bytes));
////
////        Stat stat = new Stat();
////        //ls -s
////        client.getData()
////                .storingStatIn(stat)
////                .forPath("/nodeA");
//
////        client.setData()
////                .withVersion(1) // 指定版本修改
////                .forPath("/nodeA", "data".getBytes());
//
//        //事务
////        client.inTransaction().check().forPath("/nodeA")
////                .and()
////                .create().withMode(CreateMode.EPHEMERAL).forPath("/nodeB", "init".getBytes())
////                .and()
////                .create().withMode(CreateMode.EPHEMERAL).forPath("/nodeC", "init".getBytes())
////                .and()
////                .commit();
//
////        Stat stat1 = client.checkExists() // 检查是否存在
////                .forPath("/nodeA");
////        List<String> strings = client.getChildren().forPath("/nodeA");// 获取子节点的路径
////
////        Executor executor = Executors.newFixedThreadPool(2);
////        client.create()
////                .creatingParentsIfNeeded()
////                .withMode(CreateMode.EPHEMERAL)
////                .inBackground((curatorFramework, curatorEvent) -> {
////                    System.out.println(String.format("eventType:%s,resultCode:%s", curatorEvent.getType(), curatorEvent.getResultCode()));
////                }, executor)
////                .forPath("/lw");
//
//        //分布式锁
//        InterProcessMutex interProcessMutex = new InterProcessMutex(client, "/anyLock");
//        interProcessMutex.acquire();
//        interProcessMutex.release();
//
//    }
//}
