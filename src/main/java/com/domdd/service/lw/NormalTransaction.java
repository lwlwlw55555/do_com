package com.domdd.service.lw;

import com.alibaba.fastjson.JSON;
import com.domdd.core.aop.PreNormalAopAbstract;
import com.domdd.dao.normal.NormalOrderInfoMapper;
import com.domdd.dao.shardingjdbc.SjOrderInfoMapper;
import com.domdd.model.NormalOrderInfo;
import com.domdd.model.OrderInfo;
import io.shardingsphere.api.HintManager;
import lombok.AllArgsConstructor;
import org.apache.ibatis.session.SqlSessionFactory;
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

import java.util.*;


/**
 * @author lw
 * @date 2022/3/7 5:14 下午
 */
@Service
@AllArgsConstructor
//加上以下就是JDK动态代理
//implements PreNormalAopAbstract
public class NormalTransaction {
    private final NormalOrderInfoMapper normalOrderInfoMapper;

    private final OrderInfo orderInfo;

//    @Override
    @Transactional("normalDataSourceTransactionManager")
    public void testNormalTransaction() {
        NormalOrderInfo order0 = createOrder(generateUUID());
        NormalOrderInfo order1 = createOrder(generateUUID());
        NormalOrderInfo order2 = createOrder(generateUUID());
        ArrayList<NormalOrderInfo> orderList = new ArrayList<>(Arrays.asList(order0, order1, order2));
        normalOrderInfoMapper.batchInsertSelective(orderList);
        int i = 1/0;
        System.out.println(orderList);
        System.out.println(JSON.toJSONString(orderList));
    }

    public static NormalOrderInfo createOrder(Long orderGoodsId) {
        NormalOrderInfo normalOrderInfo = new NormalOrderInfo();
        normalOrderInfo.setOrderGoodsId(orderGoodsId);
        normalOrderInfo.setOrderSn("lw_test_insert");
        normalOrderInfo.setPlatformOrderSn("lw_test_insert");
        normalOrderInfo.setGoodsName("lw_test_name");
        normalOrderInfo.setPlatformOrderStatus("WAIT_SELLER_SEND_GOODS");
        normalOrderInfo.setProvinceName("province");
        normalOrderInfo.setCityName("city");
        normalOrderInfo.setDistrictName("district");
        normalOrderInfo.setShippingAddress("lw_test_address");
        normalOrderInfo.setReceiveName("lw");
        normalOrderInfo.setRefundStatus("NONE");
        normalOrderInfo.setStyleValue("lw_test_style_value");
        normalOrderInfo.setMobile("111122221");
        normalOrderInfo.setGoodsNumber(1);
        normalOrderInfo.setSkuNumber(1);
        normalOrderInfo.setLastUpdatedTime(new Date());
        return normalOrderInfo;
    }

    public synchronized static Long generateUUID() {
        return new Random().nextLong() * 10 + 8000;
    }

}
