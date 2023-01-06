package com.domdd.service.paramtest;

import cn.hutool.core.collection.CollectionUtil;

import java.util.Collection;

public class LwTest1 {
//    @Override
    public void saveOrder() {
        LwUtil lwUtil = new LwUtilImp();
        LwTest1 lwTest1 = new LwTest1();
        LwTest2 lwTest2 = new LwTest2();
        lwUtil.testParam(lwTest2);
    }


}
