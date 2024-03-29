package com.domdd.dao.common;

import com.domdd.model.ShuadanOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShuadanOrderMapper {
    int deleteByPrimaryKey(String orderSn);

    int insert(ShuadanOrder record);

    int insertSelective(ShuadanOrder record);

    ShuadanOrder selectByPrimaryKey(String orderSn);

    int updateByPrimaryKeySelective(ShuadanOrder record);

    int updateByPrimaryKey(ShuadanOrder record);

    List<String> selectAll();

    void replace(@Param("records") List<ShuadanOrder> shuadanOrders);
}