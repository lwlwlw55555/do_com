package com.domdd.core.db;

import lombok.Data;

@Data
public class DruidProperties {

    private String driverClassName;

    private String url;

    private String username;

    private String password;

    private String initialSize;

    private String minIdle;

    private String maxActive;

    private String maxWait;

    private Boolean useUnfairLock = false;

    private String minEvictableIdleTimeMillis;

    private String timeBetweenEvictionRunsMillis;

    private String keepAlive;

    private String removeAbandoned;

    private String testWhileIdle;

    private String testOnBorrow;

    private String testOnReturn;

//    private Integer count;
}
