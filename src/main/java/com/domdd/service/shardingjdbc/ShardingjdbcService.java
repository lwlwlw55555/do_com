package com.domdd.service.shardingjdbc;

import com.alibaba.fastjson.JSON;
import com.domdd.dao.shardingjdbc.SjOrderInfoMapper;
import com.domdd.model.OrderInfo;
import io.shardingsphere.api.HintManager;
import io.shardingsphere.core.hint.HintManagerHolder;
import lombok.AllArgsConstructor;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * @author lw
 * @date 2022/3/7 5:14 下午
 */
@Service
@AllArgsConstructor
public class ShardingjdbcService extends SqlSessionDaoSupport {
    private final SjOrderInfoMapper sjOrderInfoMapper;

//    private final TransactionTemplate transactionDefinition;

    @Qualifier("shardingSqlSessionFactory")
    private final SqlSessionFactory sqlSessionFactory;

    private final DataSourceTransactionManager transactionManager;

    @Autowired
    public void setFactory() {
        this.setSqlSessionFactory(sqlSessionFactory);
    }
//    private final SqlSessionTemplate sqlSessionTemplate;

    @Transactional("shardingTractionManager")
    public void testTransactional() {
        System.out.println(transactionManager);
//        System.out.println(transactionDefinition);
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
//        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW); // 事物隔离级别，开启新事务
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition()); // 获得事务状态

        HintManager instance = HintManager.getInstance();
        instance.addDatabaseShardingValue("order_info", "1");
//        instance.setDatabaseShardingValue("order_info",);
//        instance.addDatabaseShardingValue("order_info","shop_name");
//        instance.addDatabaseShardingValue("order_info","shop_name");
//        instance.addDatabaseShardingValue("order_info","shop_name");
//        instance.setMasterRouteOnly();
//        instance.setDatabaseShardingValue("诺优能官方旗舰店");

        OrderInfo result = sjOrderInfoMapper.selectByIdAndName(1045360L, "诺优能官方旗舰店");
        OrderInfo result1 = sjOrderInfoMapper.selectByIdAndName(1059846L, "爱他美旗舰店");
        System.out.println(JSON.toJSONString(result));
        System.out.println(JSON.toJSONString(result1));
    }

    public void testTransactionalTemplate() {
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.execute(txStatus -> {
            sjOrderInfoMapper.insert(new OrderInfo());
            return null;
        });
    }

    //    public void testTransactionalTemplate1() {
////        sqlSessionTemplate
    List<Object> params = Arrays.asList(1045360L, "诺优能官方旗舰店");
//        ResultHandler<Object> res = new ResultHandler<Object>() {
//            @Override
//            public void handleResult(ResultContext resultContext) {
//                System.out.println(JSON.toJSONString(resultContext));
//            }
//        };
//        sqlSessionTemplate.select("com.domdd.dao.shardingjdbc.SjOrderInfoMapper.selectById",
//                1045360L, res);
//    }

    public void testTransactionalTemplate1() {
//        sqlSessionTemplate
//        List<Object> params = Arrays.asList(1045360L, "诺优能官方旗舰店");
//        ResultHandler<Object> res = new ResultHandler<Object>() {
//            @Override
//            public void handleResult(ResultContext resultContext) {
//                System.out.println(JSON.toJSONString(resultContext));
//            }
//        };
//        sqlSessionTemplate.select("com.domdd.dao.shardingjdbc.SjOrderInfoMapper.selectById",
//                1045360L, res);
        //项目起不来,不知道为什么...
//        setSqlSessionTemplate(sqlSessionTemplate);
        Object o = getSqlSession().selectOne("com.domdd.dao.shardingjdbc.SjOrderInfoMapper.selectById", 1045360L);
        System.out.println(JSON.toJSONString(o));
    }
}
