package com.domdd.core.db;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.domdd.core.interceptors.MybatisLog;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.LocalCacheScope;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import tk.mybatis.spring.mapper.MapperScannerConfigurer;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * springboot集成mybatis的基本入口 1）创建数据源(如果采用的是默认的tomcat-jdbc数据源，则不需要)
 * 2）创建SqlSessionFactory 3）配置事务管理器，除非需要使用事务，否则不用配置
 */
@Configuration // 该注解类似于spring配置文件
@MapperScan(basePackages = {"com.domdd.dao.common"}, sqlSessionFactoryRef = "sqlSessionFactory")
public class MyBatisConfig implements EnvironmentAware {

    private Environment environment;

    @Override
    public void setEnvironment(final Environment environment) {
        this.environment = environment;
    }

    /**
     * @Primary 该注解表示在同一个接口有多个实现类可以注入的时候，默认选择哪一个，而不是让@autowire注解报错
     * @Qualifier 根据名称进行注入，通常是在具有相同的多个类型的实例的一个注入（例如有多个DataSource类型的实例）
     */
    @Bean
    @Primary
    public DataSource dataSource(@Qualifier("dbProperties") DruidProperties dbProperties) throws Exception {
//        Map<Object, Object> targetDataSources = new HashMap<>();
//        targetDataSources.put(DatabaseType.SyncExpress, syncExpressDataSource);
        DataSource datasource = DruidDataSourceFactory.createDataSource(BeanUtil.toBean(dbProperties, Properties.class));
//        datasource.setTimeBetweenEvictionRunsMillis(60000);
//        datasource.setMinEvictableIdleTimeMillis(300000);
//        datasource.setValidationQuery("SELECT 1 ");
//        datasource.setTestWhileIdle(true);
//        datasource.setTestOnBorrow(true);
//        datasource.setTestOnReturn(true);
//        datasource.setPoolPreparedStatements(true);
//        datasource.setMaxPoolPreparedStatementPerConnectionSize(20);
        return datasource;
    }

    /**
     * 根据数据源创建SqlSessionFactory
     */
    @Bean
    public MybatisSqlSessionFactoryBean sqlSessionFactory(@Qualifier("dataSource") DataSource ds, @Qualifier("paginationInterceptor") PaginationInterceptor paginationInterceptor, @Qualifier("mybatisLog") MybatisLog mybatisLog) throws Exception {
//        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//        SqlSessionFactoryBean fb = new SqlSessionFactoryBean();
//        fb.setPlugins(new Interceptor[]{(Interceptor) paginationInterceptor});
//        fb.setDataSource(ds);// 指定数据源(这个必须有，否则报错)
//        fb.setTypeAliasesPackage("com.domdd.dao");
//        // 下边两句仅仅用于*.xml文件，如果整个持久层操作不需要使用到xml文件的话（只用注解就可以搞定），则不加
//        fb.setMapperLocations(resolver.getResources("com/domdd/mapper/*.xml"));//
//        return fb.getObject();
        // TODO: 2021/12/27 当时遇到过这个问题的为什么现在想不起了呢？？？请做好记录啊！！！！
        // TODO: 2021/12/27 mybatis-plus 必须用MybatisSqlSessionFactoryBean不能用SqlSessionFactoryBean啊啊啊啊！
        //todo 从原理看MybatisSqlSessionFactoryBean是覆盖了之前的源码中的SqlSessionFactoryBean
        MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
        bean.setPlugins(new Interceptor[]{(Interceptor) paginationInterceptor, mybatisLog});
        bean.setDataSource(ds);
        bean.setTypeAliasesPackage("com.domdd.dao.common");
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("com/domdd/mapper/*.xml"));
        MybatisConfiguration mybatisConfiguration = new MybatisConfiguration();
        mybatisConfiguration.setLocalCacheScope(LocalCacheScope.STATEMENT);
        mybatisConfiguration.setCacheEnabled(false);
        bean.setConfiguration(mybatisConfiguration);
        return bean;
    }


    /**
     * 配置事务管理器
     */
    @Bean
    public DataSourceTransactionManager transactionManager(DataSource dataSource) throws Exception {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    @Primary
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer scannerConfigurer = new MapperScannerConfigurer();
        scannerConfigurer.setBasePackage("com.domdd.dao.common");
        scannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        Properties props = new Properties();
        props.setProperty("mappers", "tk.mybatis.mapper.common.Mapper");
        props.setProperty("IDENTITY", "MYSQL");
        props.setProperty("notEmpty", "true");
        scannerConfigurer.setProperties(props);
        return scannerConfigurer;
    }

}