package com.domdd.core;

import com.domdd.core.db.EsProperties;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;

/**
 * @author lw
 * @date 2022/2/15 5:05 下午
 */
@Configuration
public class EsConfig {

    @Bean
    RestHighLevelClient client(EsProperties esProperties) {
        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo(esProperties.getUrl())
                .build();
        return RestClients.create(clientConfiguration).rest();
    }
}
