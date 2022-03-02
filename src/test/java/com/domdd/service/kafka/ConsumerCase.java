package com.domdd.service.kafka;

/**
 * @author lw
 * @date 2022/2/25 9:38 上午
 */

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

/**
 * kafka消费者示例
 */
public class ConsumerCase {
    private Properties props;
    private Consumer consumer;

    @Before
    public void before() {
        props = new Properties();
        props.put("bootstrap.servers", "121.40.113.153:9092");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.setProperty("group.id", "test");
    }

    @After
    public void after() throws Exception {
        consumer.close();
    }

    /**
     * 自动提交
     */
    @Test
    public void automatic() throws Exception {
        props.setProperty("enable.auto.commit", "true");
        props.setProperty("auto.commit.interval.ms", "1000");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList("test"));
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> record : records) {
                System.out.printf("offset=%d, key=%s, value=%s%n", record.offset(), record.key(), record.value());
            }
        }
    }

    /**
     * 手动提交
     * @throws Exception
     */
    @Test
    public void manual() throws Exception {
        props.setProperty("enable.auto.commit", "false");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList("test"));
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> record : records) {
                System.out.printf("offset=%d, key=%s, value=%s%n", record.offset(), record.key(), record.value());
            }
            //insertIntoDb(records);//具体业务处理
            consumer.commitSync();
        }
    }

}