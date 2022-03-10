package com.domdd.service.rocketmq;

import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.consumer.PullResult;
import org.apache.rocketmq.client.consumer.PullStatus;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author lw
 * @date 2022/3/4 12:29 上午
 */
public class PullConsumer {
    /**
     * 消费者主动从服务器上拉取数据的方式。这种方式下面需要消费者主动的更新队列中的 offset 来使得下一次读取的位置得以更新。
     */
    public static final String NAME_SERVER_ADDR = "";

    public static void main(String[] args) throws Exception {
        DefaultMQPullConsumer consumer = new DefaultMQPullConsumer("GROUP_TEST");
        consumer.setNamesrvAddr(NAME_SERVER_ADDR);
        consumer.start();

        //获取到订阅的 Topic 上面所有的 Queue
        Set<MessageQueue> messageQueues = consumer.fetchSubscribeMessageQueues("TopicTest");
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            try {
                //遍历所有的 MessageQueue
                for (MessageQueue messageQueue : messageQueues) {
                    //抓取到当前 MessageQueue 的偏移量
                    long offset = consumer.fetchConsumeOffset(messageQueue, true);
                    //从当前的偏移量开始取 10 条数据
                    PullResult result = consumer.pullBlockIfNotFound(messageQueue, null, offset, 10);
                    System.out.println(result.getNextBeginOffset());
                    //取完数据后更新该 MessageQueue 的偏移量，以便于下次取消息
                    consumer.updateConsumeOffset(messageQueue, result.getNextBeginOffset());
                    //如果可以从 MessageQueue 取到消息
                    if (result.getPullStatus() == PullStatus.FOUND) {
                        List<MessageExt> messageExts = result.getMsgFoundList();
                        for (MessageExt messageExt : messageExts) {
                            System.out.printf("线程：%-25s 接收到新消息 %s --- %s %n", Thread.currentThread().getName(), messageExt.getTags(), new String(messageExt.getBody(), RemotingHelper.DEFAULT_CHARSET));
                        }
                    }
                }
            } catch (Exception e) {
                // TODO: handle exception
            }
        }, 1000L, 1000L, TimeUnit.MILLISECONDS);

    }

}