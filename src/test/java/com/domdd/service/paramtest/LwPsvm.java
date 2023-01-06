package com.domdd.service.paramtest;

import cn.hutool.core.collection.CollectionUtil;

public class LwPsvm {
    public static void main(String[] args) {
        LwUtil lwUtil = new LwUtilImp();
        LwTest1 lwTest1 = new LwTest1();
        LwTest2 lwTest2 = new LwTest2();
        lwUtil.testParam(lwTest2);
        lwUtil.testParam(CollectionUtil.newArrayList(lwTest2));
    }
}
