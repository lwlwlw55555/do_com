package com.domdd.service.shardingjdbc;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.domdd.DoMddApplication;
import com.domdd.dao.shardingjdbc.SjOrderInfoMapper;
import com.domdd.model.OrderInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author lw
 * @date 2022/3/4 4:45 下午
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DoMddApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestShardingjdbc {
    @Resource
    SjOrderInfoMapper sjOrderInfoMapper;

    @Resource
    ShardingjdbcService shardingjdbcService;

    @Test
    public void testShardingjdbc() {
//        IPage<OrderInfo> p = new Page<>(0, 1000);
//        IPage<OrderInfo> result = sjOrderInfoMapper.selectByPage(p,
//                "shipping_time", DateUtil.parseDateTime("2022-03-03 00:49:50"),
//                DateUtil.parseDateTime("2022-03-03 23:59:50"), "诺优能官方旗舰店");
        OrderInfo result = sjOrderInfoMapper.selectByIdAndName(1045360L, "诺优能官方旗舰店");
        System.out.println(JSON.toJSONString(result));
    }

    @Test
    public void testTransactional() {
        shardingjdbcService.testTransactional();
    }

    @Test
    public void testTransactionalTemplate1() {
        shardingjdbcService.testTransactionalTemplate1();
    }
}
