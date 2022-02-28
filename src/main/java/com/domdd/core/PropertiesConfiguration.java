package com.domdd.core;

import com.domdd.core.db.DruidProperties;
import com.domdd.core.db.EsProperties;
import com.domdd.core.db.KafkaProperties;
import com.domdd.core.db.ZkProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PropertiesConfiguration {

    @Bean(name = "dbProperties")
    @ConfigurationProperties(prefix = "druid")
    public DruidProperties singleDbProperties() {
        return new DruidProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "daneng.open")
    public DanengOpenPropertiesDo danengOpenPropertiesDo() {
        return new DanengOpenPropertiesDo();
    }

    @Bean
    @ConfigurationProperties(prefix = "es")
    public EsProperties esProperties() {
        return new EsProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "zk")
    public ZkProperties zkProperties() {
        return new ZkProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "kafka")
    public KafkaProperties kafkaProperties() {
        return new KafkaProperties();
    }

}
