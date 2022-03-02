package com.domdd.service.kafka;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.admin.*;
import org.apache.kafka.common.KafkaFuture;
import org.apache.kafka.common.TopicPartitionReplica;
import org.apache.kafka.common.config.ConfigResource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Properties;

public class AdminCase {
    private AdminClient adminClient;

    @Before
    public void before() {
        Properties props = new Properties();
        props.put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, "121.40.113.153:9092");
        adminClient = AdminClient.create(props);
    }

    @After
    public void after() {
        adminClient.close();
    }

    /**
     * 创建主题
     */
    @Test
    public void createTopics() {
        NewTopic topic = new NewTopic("admin-test", 4, (short) 1);//分区为4，副本为1
        Collection<NewTopic> topicList = new ArrayList<>();
        topicList.add(topic);
        adminClient.createTopics(topicList);
    }

    /**
     * 列出主题
     *
     * @throws Exception
     */
    @Test
    public void listTopics() throws Exception {
        ListTopicsOptions listTopicsOptions = new ListTopicsOptions();
        listTopicsOptions.listInternal(true);//是否罗列内部主题
        ListTopicsResult result = adminClient.listTopics(listTopicsOptions);
        Collection<TopicListing> list = result.listings().get();
        System.out.println(list);
    }

    /**
     * 查看主题详情
     *
     * @throws Exception
     */
    @Test
    public void describeTopics() throws Exception {
        DescribeTopicsResult result = adminClient.describeTopics(Arrays.asList("admin-test"));
        System.out.println(result.all().get());
    }

    /**
     * 删除主题
     *
     * @throws Exception
     */
    @Test
    public void deleteTopics() throws Exception {
        adminClient.deleteTopics(Arrays.asList("admin-test"));
    }

    /**
     * 查询集群信息
     *
     * @throws Exception
     */
    @Test
    public void describeCluster() throws Exception {
        DescribeClusterResult result = adminClient.describeCluster();
        System.out.println(result.nodes().get());
    }

    /**
     * 查询配置信息
     *
     * @throws Exception
     */
    @Test
    public void describeConfigs() throws Exception {
        DescribeConfigsResult result = adminClient.describeConfigs(Arrays.asList(new ConfigResource(ConfigResource.Type.TOPIC, "admin-test")));
        System.out.println(result.all().get());
    }

    /**
     * 查询节点的日志目录信息
     *
     * @throws Exception
     */
    @Test
    public void describeLogDirs() throws Exception {
        DescribeLogDirsResult result = adminClient.describeLogDirs(Arrays.asList(0));//查询broker.id为0的节点
        System.out.println(result.all().get());
    }

    /**
     * 查询副本的日志目录信息
     *
     * @throws Exception
     */
    @Test
    public void describeReplicaLogDirs() throws Exception {
        DescribeReplicaLogDirsResult result = adminClient.describeReplicaLogDirs(Arrays.asList(new TopicPartitionReplica("admin-test", 0, 0)));
        System.out.println(result.all().get());
    }
}