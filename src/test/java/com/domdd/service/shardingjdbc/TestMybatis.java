package com.domdd.service.shardingjdbc;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.IdUtil;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.alibaba.druid.pool.DruidPooledResultSet;
import com.alibaba.druid.pool.DruidPooledStatement;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.domdd.DoMddApplication;
import com.domdd.core.aop.PreNormalAopAbstract;
import com.domdd.dao.common.OrderInfoMapper;
import com.domdd.dao.normal.NormalOrderInfoMapper;
import com.domdd.dao.shardingjdbc.SjOrderInfoMapper;
import com.domdd.model.NormalOrderInfo;
import com.domdd.model.OrderInfo;
import com.domdd.service.lw.NormalTransaction;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

/**
 * @author lw
 * @date 2022/3/18 4:50 下午
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DoMddApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestMybatis {
    @Resource
    OrderInfoMapper orderInfoMapper;

    @Resource
    NormalOrderInfoMapper normalOrderInfoMapper;

    @Resource(name = "normalDataSource")
    DruidDataSource dataSource;

//    @Resource
//    OrderInfo orderInfo;

    @Test
    public void testJdbc() {
        OrderInfo orderInfo = orderInfoMapper.selectById(924002L);
        System.out.println(orderInfo);
    }

    @Test
    public void testNormalJdbc() {
        NormalOrderInfo normalOrderInfo = normalOrderInfoMapper.selectByPrimaryKey(924002L);
        System.out.println(normalOrderInfo);
    }


    @Test
    public void testInsertNormalJdbc() {
        NormalOrderInfo normalOrderInfo = createOrder(generateUUID());
        normalOrderInfoMapper.insertSelective(normalOrderInfo);
        System.out.println(normalOrderInfo);
        System.out.println(JSON.toJSONString(normalOrderInfo));
    }

//    com.baomidou.mybatisplus.core.override.MybatisMapperProxy@4298e4b4

    @Test
    public void testBatchInsertNormalJdbc() {

        NormalOrderInfo order0 = createOrder(generateUUID());
        NormalOrderInfo order1 = createOrder(generateUUID());
        NormalOrderInfo order2 = createOrder(generateUUID());
        ArrayList<NormalOrderInfo> orderList = new ArrayList<>(Arrays.asList(order0, order1, order2));
        normalOrderInfoMapper.batchInsertSelective(orderList);
        System.out.println(orderList);
        System.out.println(JSON.toJSONString(orderList));
    }

    //    (((long) (new Random().nextDouble() * (max - min)))) + min;
    public synchronized static Long generateUUID() {
        return new Random().nextLong() * 10 + 8000;
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


    @Test
    public void testJdbc3() {

        DruidPooledStatement stmt = null;
        ResultSet rs = null;
        DruidPooledResultSet resultSet = null;
        try {
            DruidPooledConnection conn = dataSource.getConnection();
            stmt = (DruidPooledStatement) conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY,
                    java.sql.ResultSet.CONCUR_UPDATABLE);
            // ...
            // 省略若干行（如上例般创建demo表）
            // ...
            stmt.executeUpdate(
                    "INSERT INTO common_config_index (config_key,config_value) "
                            + "values ('lw_key','lw_value')",
                    Statement.RETURN_GENERATED_KEYS);                      // 向驱动指明需要自动获取generatedKeys！
            int autoIncKeyFromApi = -1;
            rs = stmt.getGeneratedKeys();// 获取自增主键！
            if (rs.next()) {
                autoIncKeyFromApi = rs.getInt(1);
            } else {
                // throw an exception from here
            }
            rs.close();
            rs = null;
            System.out.println("Key returned from getGeneratedKeys():"
                    + autoIncKeyFromApi);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }


    //JDK动态代理
//    @Autowired
//    PreNormalAopAbstract preNormalAopAbstract;
//
//    @Test
//    public void testNormalTransaction() {
//        preNormalAopAbstract.testNormalTransaction();
//    }

    //Cglib
    @Autowired
    NormalTransaction normalTransaction;

    @Test
    public void testNormalTransaction() {
        normalTransaction.testNormalTransaction();
    }

    @Test
    public void testMybatisLog() {
        IPage<OrderInfo> p = new Page<>(0, 100);
        IPage<OrderInfo> orderInfoIPage = orderInfoMapper.selectByPage(p,
                "shipping_type", DateUtil.parseDateTime("2022-05-08 14:29:00"),
                DateUtil.parseDateTime("2022-05-08 14:30:00"), "爱他美旗舰店",
                false, "PRESALE");
        System.out.println(orderInfoIPage);
    }


}
