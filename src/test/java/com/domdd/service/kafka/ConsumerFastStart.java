package com.domdd.service.kafka;

/**
 * @author lw
 * @date 2022/2/26 2:17 下午
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * Kafka 消息消费者
 */
public class ConsumerFastStart {
    // Kafka集群地址，这里是虚拟机IP地址，前提是server.properties中配置了host.name,
    private static final String brokerList = "121.40.113.153:9092";
    // 主题名称-之前已经创建
    private static final String topic = "lw";
    // 消费组
    private static
    final String groupId = "group.demo";

    public static void main(String[] args) {
        Properties properties = new Properties();
        //key的反序列化
        properties.put("key.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");
        //value的反序列化
        properties.put("value.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("bootstrap.servers", brokerList);
        properties.put("group.id", groupId);

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Collections.singletonList(topic));


        //测试看每次取出500条数据
//        while (true) {
//            ConsumerRecords<String, String> records =
//                    consumer.poll(Duration.ofMillis(100000));
//            for (ConsumerRecord<String, String> record : records) {
//                System.out.println(record.value());
//            }
//        }

        //每次只取出一条数据
        Set<TopicPartition> assignment = consumer.assignment();
        while (assignment.size() == 0) {
            consumer.poll(Duration.ofMillis(100));
            assignment = consumer.assignment();
        }

        Map<TopicPartition, Long> endOffsets = consumer.endOffsets(assignment);
        long lastSysModifiedDate = 0L;
        String lastMessage = null;
        for (TopicPartition topicPartition : assignment) {
            Long offset = endOffsets.get(topicPartition);
            // endOffset为0，说明此分区没有消息，跳过循环
            if (offset == 0) {
                continue;
            }
            //指定消费分区最后一条消息
            consumer.seek(topicPartition, offset - 1);
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            while (records.isEmpty()) {
                records = consumer.poll(Duration.ofMillis(100));
            }
            String message = records.iterator().next().value();
//            JSONObject jsonMessage = JSONObject.parseObject(message);
            // 比较每个分区的最后一条消息的sysModifyDate，得到整个topic的最后一条消息
//            long sysModifyDate = (Long) jsonMessage.get("sysModifyDate");
//            if (sysModifyDate > lastSysModifiedDate){
//                lastSysModifiedDate = sysModifyDate;
//                lastMessage = message;
//            }
            System.out.println(lastMessage);
        }


    }
}

