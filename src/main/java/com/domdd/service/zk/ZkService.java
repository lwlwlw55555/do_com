//package com.domdd.service.zk;
//
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.zookeeper.*;
//import org.apache.zookeeper.data.Stat;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.stereotype.Service;
//
//import javax.websocket.Session;
//import java.io.IOException;
//import java.util.Arrays;
//import java.util.List;
//
//import static org.apache.zookeeper.Watcher.Event.*;
//
///**
// * @author lw
// * @date 2022/2/23 4:57 下午
// */
//@Slf4j
//@Service
//@AllArgsConstructor
//public class ZkService {
//    private static ZooKeeper zkClient;
//
//    @Autowired
//    public void setZkClient(@Qualifier("zooKeeper") ZooKeeper zooKeeper) {
//        ZkService.zkClient = zooKeeper;
//    }
//
//
////    @Autowired
////    public void testZkListener() {
////        new Thread(new zkThread()).start();
////    }
//
//
////    @Autowired
//    public void getData() throws Exception {
////        List<String> children = zkClient.getChildren("/dbconfig", true, null);
////        System.out.println(children);
//        zkClient.create("/lwtemp", "lwtemp".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
//
//
////        // 创建一个watcher
////        byte[] data = zkClient.getData("/LW", new Watcher() {
////
////            @Override
////            public void process(WatchedEvent event) {
////                log.info(event.getPath() + "数据发生了变化！====>" + event.getType());
////                try {
////
////                    byte[] data1 = zkClient.getData("/LW", true, null);
////
////                    log.info("新的结果是：" + String.valueOf(data1));
////                } catch (Exception e) {
////                    e.printStackTrace();
////                }
////
////            }
////        }, null);
////        return new String(data);
//    }
//
//
////    void getLock() throws KeeperException, InterruptedException {
////        List<String> list = zkClient.getChildren(lockRoot, false);
////        String[] nodes = list.toArray(new String[list.size()]);
////        Arrays.sort(nodes);
////        if (myZnode.equals(lockRoot + "/" + nodes[0])) {
//////            doAction();
////        } else {
////            waitForLock(nodes[0]);
////        }
////    }
////
////    void waitForLock(String lower) throws InterruptedException, KeeperException {
////        Stat stat = zkClient.exists(lockRoot + "/" + lower, true);
////        if (stat != null) {
////            mutex.wait();
////        } else {
////            getLock();
////        }
////    }
//
//    public static class zkThread implements Runnable {
//
//        public void run() {
//            /*
//             * 验证过程以下：
//             * 一、验证一个节点X上使用exist方式注册的多个监听器（ManyWatcherOne、ManyWatcherTwo），
//             *      在节点X发生create事件时的事件通知状况
//             * 二、验证一个节点Y上使用getDate方式注册的多个监听器（ManyWatcherOne、ManyWatcherTwo），
//             *      在节点X发生create事件时的事件通知状况
//             * */
//            //默认监听：注册默认监听是为了让None事件都由默认监听处理，
//            //不干扰ManyWatcherOne、ManyWatcherTwo的日志输出
////            ManyWatcherDefault watcherDefault = new ManyWatcherDefault();
////        ZooKeeper zkClient = null;
////        try {
////            zkClient = new ZooKeeper("192.168.61.129:2181", 120000, watcherDefault);
////        } catch (IOException e) {
////            log.error(e.getMessage(), e);
////            return;
////        }
//            //默认监听也可使用register方法注册
//            //zkClient.register(watcherDefault);
//
//            //一、========================================================
//            log.info("=================如下是第一个实验");
//            String path = "/LW";
//            ManyWatcherOne watcherOneX = new ManyWatcherOne(zkClient, path);
//            ManyWatcherTwo watcherTwoX = new ManyWatcherTwo(zkClient, path);
//            //注册监听，注意，这里两次exists方法的执行返回都是null，由于“X”节点还不存在
//            try {
//                zkClient.exists(path, watcherOneX);
//                zkClient.exists(path, watcherTwoX);
//                //建立"X"节点，为了简单起见，咱们忽略权限问题。
//                //而且建立一个临时节点，这样重复跑代码的时候，不用去server上手动删除)
////                zkClient.create(path, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
//            } catch (Exception e) {
//                log.error(e.getMessage(), e);
////                return;
//            }
//            //TODO 注意观察日志，根据原理咱们猜想理想状况下ManyWatcherTwo和ManyWatcherOne都会被通知。
//
//            //二、========================================================
//            log.info("=================如下是第二个实验");
//            path = "/Y";
//            ManyWatcherOne watcherOneY = new ManyWatcherOne(zkClient, path);
//            ManyWatcherTwo watcherTwoY = new ManyWatcherTwo(zkClient, path);
//            //注册监听，注意，这里使用两次getData方法注册监听，"Y"节点目前并不存在
//            try {
//                zkClient.getData(path, watcherOneY, null);
//                zkClient.getData(path, watcherTwoY, null);
//            } catch (Exception e) {
//                log.error(e.getMessage(), e);
//            }
//            //TODO 注意观察日志，由于"Y"节点不存在，因此getData就会出现异常。watcherOneY、watcherTwoY的注册都不起任何做用。
//            //而后咱们在报了异常的状况下，建立"Y"节点，根据原理，不会有任何watcher响应"Y"节点的create事件
//            try {
//                zkClient.create(path, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
//            } catch (Exception e) {
//                log.error(e.getMessage(), e);
//                return;
//            }
//
//            //下面这段代码能够忽略，是为了观察zk的原理。让守护线程保持不退出
//            synchronized (this) {
//                try {
//                    this.wait();
//                } catch (InterruptedException e) {
//                    log.error(e.getMessage(), e);
//                    System.exit(-1);
//                }
//            }
//        }
//    }
//
//    /**
//     * 这是默认的watcher实现。
//     *
//     * @author yinwenjie
//     */
//    public static class ManyWatcherDefault implements Watcher {
//        public void process(WatchedEvent event) {
//            KeeperState keeperState = event.getState();
//            EventType eventType = event.getType();
//            log.info("=========默认监听到None事件：keeperState = "
//                    + keeperState + "  :  eventType = " + eventType);
//        }
//    }
//
//    /**
//     * 这是第一种watcher
//     *
//     * @author yinwenjie
//     */
//    public static class ManyWatcherOne implements Watcher {
//        private ZooKeeper zkClient;
//
//        /**
//         * 被监控的znode地址
//         */
//        private String watcherPath;
//
//        public ManyWatcherOne(ZooKeeper zkClient, String watcherPath) {
//            zkClient = zkClient;
//            this.watcherPath = watcherPath;
//        }
//
//        public void process(WatchedEvent event) {
//            try {
//                zkClient.exists(this.watcherPath, this);
//            } catch (Exception e) {
//                log.error(e.getMessage(), e);
//            }
//            Event.KeeperState keeperState = event.getState();
//            EventType eventType = event.getType();
//            //这个属性是发生事件的path
//            String eventPath = event.getPath();
//
//            log.info("=========ManyWatcherOne监听到" + eventPath + "地址发生事件："
//                    + "keeperState = " + keeperState + "  :  eventType = " + eventType);
//        }
//    }
//
//    /**
//     * 这是第二种watcher
//     *
//     * @author yinwenjie
//     */
//    public static class ManyWatcherTwo implements Watcher {
//        private ZooKeeper zkClient;
//
//        /**
//         * 被监控的znode地址
//         */
//        private String watcherPath;
//
//        public ManyWatcherTwo(ZooKeeper zkClient, String watcherPath) {
//            zkClient = zkClient;
//            this.watcherPath = watcherPath;
//        }
//
//        public void process(WatchedEvent event) {
//            try {
//                zkClient.exists(this.watcherPath, this);
//            } catch (Exception e) {
//                log.error(e.getMessage(), e);
//            }
//            Event.KeeperState keeperState = event.getState();
//            Event.EventType eventType = event.getType();
//            //这个属性是发生事件的path
//            String eventPath = event.getPath();
//
//            log.info("=========ManyWatcherTwo监听到" + eventPath + "地址发生事件："
//                    + "keeperState = " + keeperState + "  :  eventType = " + eventType);
//        }
//    }
//
//
////    @Autowired
////    public void noneWatcher() {
////        TestEventNoneWatcher testEventNoneWatcher = new TestEventNoneWatcher();
////        new Thread(testEventNoneWatcher).start();
//////        zkClient.delete();
//////        zkClient.exists();
////    }
//
//    /**
//     * 这个测试类测试在指定了默认watcher，而且有不止一个watcher实例的状况下。zkClient对Event-NONE事件的响应机制。
//     * servers：192.168.61.129:2181，192.168.61.130:2181，192.168.61.132:2181<br>
//     * 咱们选择一个节点进行链接(192.168.61.129)，这样好在主动中止这个zk节点后，观察watcher的响应状况。
//     *
//     * @author yinwenjie
//     */
//    public class TestEventNoneWatcher implements Runnable {
//
//        public void run() {
//
////            zkClient.getData("/lw",true,null);
//            /*
//             * 验证过程以下：
//             * 一、链接zk后，并不进行进行默认的watcher的注册，而且使用exist方法注册一个监听节点"X"的监听器。
//             *      （完成后主线程进入等待状态）
//             * 二、关闭192.168.61.129:2181这个zk节点，让Disconnected事件发生。
//             *      观察究竟是哪一个watcher响应这些None事件。
//             * */
//            //一、========================================================
//            //注册默认监听
//            EventNodeWatcherDefault watcherDefault = new EventNodeWatcherDefault(this);
//            String path = "/X";
//            EventNodeWatcherOne eventNodeWatcherOne = new EventNodeWatcherOne(zkClient, path);
//            //注册监听，注意，这里两次exists方法的执行返回都是null，由于“X”节点还不存在
//            try {
//                zkClient.exists(path, eventNodeWatcherOne);
//                //建立"X"节点，为了简单起见，咱们忽略权限问题。
//                //而且建立一个临时节点，这样重复跑代码的时候，不用去server上手动删除)
//                zkClient.create(path, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
//            } catch (Exception e) {
//                log.error(e.getMessage(), e);
//                return;
//            }
//
//            //完成注册后，主线程等待。而后关闭192.168.61.129上的zk节点
//            synchronized (this) {
//                try {
//                    this.wait();
//                } catch (InterruptedException e) {
//                    log.error(e.getMessage(), e);
//                    System.exit(-1);
//                }
//            }
//        }
//
//        public ZooKeeper getZkClient() {
//            return zkClient;
//        }
//    }
//
//    /**
//     * 这是默认的watcher实现。
//     *
//     * @author yinwenjie
//     */
//    class EventNodeWatcherDefault implements Watcher {
//        private TestEventNoneWatcher eventNoneWatcherThead;
//
//        public EventNodeWatcherDefault(TestEventNoneWatcher eventNoneWatcherThead) {
//            this.eventNoneWatcherThead = eventNoneWatcherThead;
//        }
//
//        public void process(WatchedEvent event) {
//            //从新注册监听
//            this.eventNoneWatcherThead.getZkClient().register(this);
//
//            KeeperState keeperState = event.getState();
//            EventType eventType = event.getType();
//            log.info("=========默认EventNodeWatcher监听到None事件：keeperState = "
//                    + keeperState + "  :  eventType = " + eventType);
//        }
//    }
//
//    /**
//     * 这是第一种watcher
//     *
//     * @author yinwenjie
//     */
//    class EventNodeWatcherOne implements Watcher {
//        private ZooKeeper zkClient;
//
//        /**
//         * 被监控的znode地址
//         */
//        private String watcherPath;
//
//        public EventNodeWatcherOne(ZooKeeper zkClient, String watcherPath) {
//            zkClient = zkClient;
//            this.watcherPath = watcherPath;
//        }
//
//        public void process(WatchedEvent event) {
//            try {
//                zkClient.exists(this.watcherPath, this);
//            } catch (Exception e) {
//                log.error(e.getMessage(), e);
//            }
//            KeeperState keeperState = event.getState();
//            EventType eventType = event.getType();
//
//            log.info("=========EventNodeWatcherOne监听到事件：keeperState = "
//                    + keeperState + "  :  eventType = " + eventType);
//        }
//    }
//
//}
