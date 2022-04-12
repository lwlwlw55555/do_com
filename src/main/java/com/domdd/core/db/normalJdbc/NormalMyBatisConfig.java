package com.domdd.core.db.normalJdbc;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.domdd.core.db.DruidProperties;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
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
@MapperScan(basePackages = {"com.domdd.dao.normal"}, sqlSessionFactoryRef = "normalSqlSessionFactory")
public class NormalMyBatisConfig {
    /**
     * @Primary 该注解表示在同一个接口有多个实现类可以注入的时候，默认选择哪一个，而不是让@autowire注解报错
     * @Qualifier 根据名称进行注入，通常是在具有相同的多个类型的实例的一个注入（例如有多个DataSource类型的实例）
     */
    @Bean(name = "normalDataSource")
    public DruidDataSource normalDataSource(@Qualifier("dbProperties") DruidProperties dbProperties) throws Exception {
        DataSource datasource = DruidDataSourceFactory.createDataSource(BeanUtil.toBean(dbProperties, Properties.class));
        return (DruidDataSource) datasource;
    }

    @Bean(name = "normalMapperScannerConfigurer")
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer scannerConfigurer = new MapperScannerConfigurer();
        scannerConfigurer.setBasePackage("com.domdd.dao.normal");
        scannerConfigurer.setSqlSessionFactoryBeanName("normalSqlSessionFactory");
        Properties props = new Properties();
        props.setProperty("mappers", "tk.mybatis.mapper.common.Mapper");
        props.setProperty("IDENTITY", "MYSQL");
        props.setProperty("notEmpty", "true");
        scannerConfigurer.setProperties(props);
        return scannerConfigurer;
    }

    /**
     * 根据数据源创建SqlSessionFactory
     */
    @Bean(name = "normalSqlSessionFactory")
    public SqlSessionFactory normalSqlSessionFactory(@Qualifier("normalDataSource") DruidDataSource ds) throws Exception {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

        SqlSessionFactoryBean fb = new SqlSessionFactoryBean();
        fb.setDataSource(ds);// 指定数据源(这个必须有，否则报错)
        // 下边两句仅仅用于*.xml文件，如果整个持久层操作不需要使用到xml文件的话（只用注解就可以搞定），则不加
        fb.setTypeAliasesPackage("com.dao.normal");// 指定基包
        fb.setMapperLocations(resolver.getResources("com/domdd/mapper/*.xml"));//

        return fb.getObject();
    }


    /**
     * 配置事务管理器
     */
    @Bean(name = "normalDataSourceTransactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("normalDataSource") DruidDataSource normalDataSource) throws Exception {
        return new DataSourceTransactionManager(normalDataSource);
    }


}