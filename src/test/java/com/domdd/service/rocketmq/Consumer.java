package com.domdd.service.rocketmq;

import com.alibaba.fastjson.JSON;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

import java.util.List;

/**
 * @author lw
 * @date 2022/3/2 4:04 下午
 */
public class Consumer {
    /**
     * 消费者有两种消费模式，一种是 Push，即服务器端将消息推送到客户端；另外一种是 Pull，即客户端主动去服务器端拉取消息。
     * 但其实在 RocketMQ 中，Push 模式的实现也是通过 Pull 模式来实现的，不过是帮我们屏蔽了对MQ上的消息队列 offset 的操作.
     * Push 模式下的消费者需要注册一个消息监听器来接受服务器发送过来的消息，同时也包括对消息的消费确认，
     * 即通过 MessageListenerConcurrently 接口的方法 consumeMessage 返回值 ConsumeConcurrentlyStatus。
     * ————————————————
     * 版权声明：本文为CSDN博主「championzgj」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
     * 原文链接：https://blog.csdn.net/championzgj/article/details/90726080
     * @param args
     * @throws InterruptedException
     * @throws MQClientException
     */

    public static void main(String[] args) throws InterruptedException, MQClientException {

        // Instantiate with specified consumer group name.
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("lw");

        // Specify name server addresses.
        consumer.setNamesrvAddr("121.40.113.153:9876");

        // Subscribe one more more topics to consume.
        consumer.subscribe("lw", "*");

        //广播模式
//        consumer.setMessageModel(MessageModel.BROADCASTING);
//        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

        //默认msgs里只有一条消息，可以通过设置consumeMessageBatchMaxSize参数来批量接收消息
        consumer.setConsumeMessageBatchMaxSize(1);

        // Register callback to execute on arrival of messages fetched from brokers.
        consumer.registerMessageListener(new MessageListenerConcurrently() {

            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                                                            ConsumeConcurrentlyContext context) {
//                System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), msgs);
//                System.out.println(JSON.toJSONString(msgs));
                msgs.forEach(m -> {
                    System.out.print(new String(m.getBody()));
                });
                System.out.println("----");
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        //如何保证顺序执行？

        //Launch the consumer instance.
        consumer.start();

        System.out.printf("Consumer Started.%n");
    }
}
