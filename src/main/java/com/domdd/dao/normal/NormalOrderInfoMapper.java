package com.domdd.dao.normal;

import com.domdd.model.NormalOrderInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NormalOrderInfoMapper {
    int deleteByPrimaryKey(Long orderId);

    int insert(NormalOrderInfo record);

    int insertSelective(NormalOrderInfo record);

    NormalOrderInfo selectByPrimaryKey(Long orderId);

    int updateByPrimaryKeySelective(NormalOrderInfo record);

    int updateByPrimaryKey(NormalOrderInfo record);

    int batchInsertSelective(@Param("orderList") List<NormalOrderInfo> orderList);

}