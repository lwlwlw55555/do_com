package com.domdd.core.db.shardingjdbc;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.domdd.core.db.DruidProperties;
import com.domdd.core.interceptors.MybatisLog;
import io.shardingsphere.api.config.rule.ShardingRuleConfiguration;
import io.shardingsphere.api.config.rule.TableRuleConfiguration;
import io.shardingsphere.api.config.strategy.HintShardingStrategyConfiguration;
import io.shardingsphere.api.config.strategy.StandardShardingStrategyConfiguration;
import io.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.LocalCacheScope;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.transaction.managed.ManagedTransactionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import tk.mybatis.spring.mapper.MapperScannerConfigurer;

import javax.sql.DataSource;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lw
 * @date 2022/3/4 11:39 上午
 */
@Configuration
@MapperScan(basePackages = {"com.domdd.dao.shardingjdbc"}, sqlSessionFactoryRef = "shardingSqlSessionFactory")
public class ShardingDbConfig {

    public static Integer DEFAULTDATASOURCE = -1;

    public static List<Integer> rdsDbList = new ArrayList<>();

    @Bean(name = "dataSourceMapList")
    public Map<String, DataSource> dataSourceMapList(@Qualifier("dbProperties") DruidProperties dbProperties) throws Exception {
        Map<String, DataSource> map = new HashMap<>();
//        partyDbProperties.setUseUnfairLock( true );
        DataSource dataSource = DruidDataSourceFactory.createDataSource(BeanUtil.toBean(dbProperties, Properties.class));
        map.put("0", dataSource);

        DruidProperties druidProperties = new DruidProperties();
        BeanUtils.copyProperties(dbProperties, druidProperties);
        String url = druidProperties.getUrl();
        String urlPre = url.substring(0, url.indexOf("?"));
        String db = urlPre.substring(urlPre.lastIndexOf("/") + 1);
        urlPre = urlPre.substring(0, urlPre.lastIndexOf("/") + 1);
        db = db.substring(0, db.indexOf("do_com") + 6) + "_0";
        String urlExtension = url.substring(url.indexOf("?"));
        url = urlPre + db + urlExtension;
        druidProperties.setUrl(url);
        DataSource dataSource0 = DruidDataSourceFactory.createDataSource(BeanUtil.toBean(druidProperties, Properties.class));
        map.put("1", dataSource0);
        return map;
    }


    @Bean("tableRule")
    public ShardingRuleConfiguration getTableRule() {
        // 配置Order表规则
//        TableRuleConfiguration orderTableRuleConfig = new TableRuleConfiguration();
//        orderTableRuleConfig.setLogicTable("order_info");
//        orderTableRuleConfig.setActualDataNodes("ds${0..1}.t_order${0..1}");

        // 配置分库 + 分表策略
        /**
         * @https://shardingsphere.apache.org/document/current/cn/user-manual/shardingsphere-jdbc/builtin-algorithm/sharding/
         * @https://shardingsphere.apache.org/document/current/cn/user-manual/shardingsphere-jdbc/java-api/rules/sharding/
         * @https://blog.csdn.net/qq_35270805/article/details/122733524
         * 实现 @StandardShardingAlgorithm
         */
//        orderTableRuleConfig.setDatabaseShardingStrategyConfig(new ShopNamePreciseSharingAlgorithm());
//        orderTableRuleConfig.setDatabaseShardingStrategyConfig(new StandardShardingStrategyConfiguration("shop_name", new ShopNamePreciseSharingAlgorithm(), new ShopNameRangeSharingAlgorithm()));
//        orderTableRuleConfig.setLogicTable("order_info");
//        orderTableRuleConfig.setTableShardingStrategyConfig(new InlineShardingStrategyConfiguration("order_id", "t_order${order_id % 2}"));


        // 配置分库 + 分表策略
        /**
         * @https://shardingsphere.apache.org/document/current/cn/user-manual/shardingsphere-jdbc/builtin-algorithm/sharding/
         * @https://shardingsphere.apache.org/document/current/cn/user-manual/shardingsphere-jdbc/java-api/rules/sharding/
         * @https://blog.csdn.net/qq_35270805/article/details/122733524
         * 实现 @StandardShardingAlgorithm
         */
        TableRuleConfiguration orderTableRuleConfig1 = new TableRuleConfiguration();
        orderTableRuleConfig1.setLogicTable("order_info");
        orderTableRuleConfig1.setDatabaseShardingStrategyConfig(new HintShardingStrategyConfiguration(new ShopNameHintShardingAlgorithm()));

        // 配置分片规则
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
        shardingRuleConfig.setDefaultDatabaseShardingStrategyConfig(new StandardShardingStrategyConfiguration("shop_name", new ShopNamePreciseSharingAlgorithm(), new ShopNameRangeSharingAlgorithm()));
//        shardingRuleConfig.setDefaultDatabaseShardingStrategyConfig(new HintShardingStrategyConfiguration(new ShopNameHintShardingAlgorithm()));
//        shardingRuleConfig.getTableRuleConfigs().add(orderTableRuleConfig);
        shardingRuleConfig.getTableRuleConfigs().add(orderTableRuleConfig1);

        return shardingRuleConfig;
    }

    /**
     * @Primary 该注解表示在同一个接口有多个实现类可以注入的时候，默认选择哪一个，而不是让@autowire注解报错
     * @Qualifier 根据名称进行注入，通常是在具有相同的多个类型的实例的一个注入（例如有多个DataSource类型的实例）
     */
    @Bean("shardingDb")
    public DataSource dataSource(@Qualifier("dataSourceMapList") Map<String, DataSource> dataSourceMapList, @Qualifier("tableRule") ShardingRuleConfiguration tableRule) throws Exception {
        // 获取数据源对象
        DataSource dataSource = ShardingDataSourceFactory.createDataSource(dataSourceMapList, tableRule, new ConcurrentHashMap(), new Properties());
        return dataSource;
    }

    /**
     * 配置事务管理器
     */
    @Bean("shardingTractionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("shardingDb") DataSource dataSource) throws Exception {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "shardingSqlSessionFactory")
    public MybatisSqlSessionFactoryBean shardingSqlSessionFactory(@Qualifier("shardingDb") DataSource dataSource, @Qualifier("paginationInterceptor") PaginationInterceptor paginationInterceptor, @Qualifier("mybatisLog") MybatisLog mybatisLog) throws Exception {
        MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
        bean.setPlugins(new Interceptor[]{(Interceptor) paginationInterceptor, mybatisLog});
        bean.setDataSource(dataSource);
        bean.setTypeAliasesPackage("com.domdd.shardingjdbcdao");
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("com/domdd/mapper/*.xml"));
        MybatisConfiguration mybatisConfiguration = new MybatisConfiguration();
        mybatisConfiguration.setLocalCacheScope(LocalCacheScope.STATEMENT);
        mybatisConfiguration.setCacheEnabled(false);
        bean.setConfiguration(mybatisConfiguration);
        return bean;
    }

    @Bean("shardingMapperScannerConfigurer")
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer scannerConfigurer = new MapperScannerConfigurer();
        scannerConfigurer.setBasePackage("com.domdd.shardingjdbcdao");
        scannerConfigurer.setSqlSessionFactoryBeanName("shardingSqlSessionFactory");
        Properties props = new Properties();
        props.setProperty("mappers", "tk.mybatis.mapper.common.Mapper");
        props.setProperty("IDENTITY", "MYSQL");
        props.setProperty("notEmpty", "true");
        scannerConfigurer.setProperties(props);
        return scannerConfigurer;
    }

    @Bean
    public SqlSessionTemplate sqlSession(@Qualifier("shardingSqlSessionFactory") SqlSessionFactory factory) throws Exception {
        return new SqlSessionTemplate(factory, ExecutorType.BATCH);
    }
}
