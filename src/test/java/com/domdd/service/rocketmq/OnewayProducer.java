package com.domdd.service.rocketmq;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * @author lw
 * @date 2022/3/2 4:03 下午
 */
public class OnewayProducer {
    public static void main(String[] args) throws Exception{
        /***
         * 原理：单向（Oneway）发送特点为只负责发送消息，不等待服务器回应且没有回调函数触发，即只发送请求不等待应答。此方式发送消息的过程耗时非常短，一般在微秒级别。
         * @https://blog.csdn.net/yangjiachang1203/article/details/52195258
         * 应用场景：适用于某些耗时非常短，但对可靠性要求并不高的场景，例如日志收集。
         */
        //Instantiate with a producer group name.
        DefaultMQProducer producer = new DefaultMQProducer("lw");
        // Specify name server addresses.
        producer.setNamesrvAddr("121.40.113.153:9876");
        //Launch the instance.
        producer.start();
        for (int i = 0; i < 100; i++) {
            //Create a message instance, specifying topic, tag and message body.
            Message msg = new Message("lw" /* Topic */,
                    "TagA" /* Tag */,
                    ("Hello RocketMQ " +
                            i).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
            );
            //Call send message to deliver message to one of brokers.
            producer.sendOneway(msg);
        }
        //Wait for sending to complete
        Thread.sleep(5000);
        producer.shutdown();
    }
}

