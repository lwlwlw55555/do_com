package com.domdd.service.kafka;

/**
 * @author lw
 * @date 2022/2/26 2:16 下午
 */

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

/**
 * Kafka 消息生产者
 */
public class ProducerFastStart {
    // Kafka集群地址
    private static final String brokerList = "121.40.113.153:9092";

    // 主题名称-之前已经创建
    private static final String topic = "lw";

    public static void main(String[] args) {
        Properties properties = new Properties();
        // 设置key序列化器
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        //另外一种写法
        //properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // 设置重试次数
        properties.put(ProducerConfig.RETRIES_CONFIG, 10);
        // 设置值序列化器
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        // 设置集群地址
        properties.put("bootstrap.servers", brokerList);
        // KafkaProducer 线程安全
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);
        try {
            int index = 0;
            while (true) {
                index++;
                ProducerRecord<String, String> record = new ProducerRecord<>(topic, "lw_key_" + index, "lw_value_" + index);
                try {
                    producer.send(record);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } finally {
            producer.close();
        }
    }
}

