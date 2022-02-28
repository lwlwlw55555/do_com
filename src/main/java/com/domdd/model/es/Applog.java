package com.domdd.model.es;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * @author lw
 * @date 2022/2/16 4:21 下午
 */
@Data
@Document(indexName = "applog")
public class Applog {
    @Id
    private String id;
    private String host;
    private String level;
    private String loggerName;
    private String message;
    private String stackTrace;
    private String tags;
    private String threadName;
    private String port;
    private String levelValue;
}
