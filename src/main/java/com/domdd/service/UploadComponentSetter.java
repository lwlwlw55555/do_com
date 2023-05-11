package com.domdd.service;

import com.domdd.dao.common.AfterSaleReturnOrderMapper;
import com.domdd.dao.common.OrderInfoMapper;
import com.domdd.model.AfterSaleReturnOrder;
import com.domdd.util.ObjectFieldHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UploadComponentSetter {

    @Autowired
    public void setOrderMapperAndAfterSaleReturnMapper(OrderInfoMapper orderInfoMapper, AfterSaleReturnOrderMapper afterSaleReturnOrderMapper) {
        ObjectFieldHandler.orderInfoMapper = orderInfoMapper;
        ObjectFieldHandler.afterSaleReturnOrderMapper = afterSaleReturnOrderMapper;
    }
}
