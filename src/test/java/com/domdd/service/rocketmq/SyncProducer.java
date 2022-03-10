package com.domdd.service.rocketmq;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * @author lw
 * @date 2022/3/2 3:45 下午
 */
public class SyncProducer {
    /**
     * 发送同步消息，可靠的同步传输用于广泛的场景，如重要的通知消息，短信通知，短信营销系统等。
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        //Instantiate with a producer group name.
        DefaultMQProducer producer = new
                DefaultMQProducer("lw");
        // Specify name server addresses.
        producer.setNamesrvAddr("121.40.113.153:9876");
//        producer.setVipChannelEnabled(false);
        //Launch the instance.
        producer.start();
        for (int i = 0; i < 1; i++) {
            //Create a message instance, specifying topic, tag and message body.
            Message msg = new Message("lw" /* Topic */,
                    "TagA" /* Tag */,
                    ("Hello RocketMQ " +
                            i).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
            );
            //Call send message to deliver message to one of brokers.
            SendResult sendResult = producer.send(msg);
//            System.out.printf("%s%n", sendResult);
            System.out.println(new String(msg.getBody()));
            System.out.println(sendResult.getSendStatus().name());
        }
        //Shut down once the producer instance is not longer in use.
        producer.shutdown();
    }
}
