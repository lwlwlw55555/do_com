package com.domdd.core.interceptors;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Properties;

//@Signature(type = ParameterHandler.class, method = "setParameters", args = {PreparedStatement.class}),
@Intercepts({
        @Signature(type = StatementHandler.class, method = "query", args = {Statement.class, ResultHandler.class}),
        @Signature(type = StatementHandler.class, method = "update", args = {Statement.class}),
        @Signature(type = StatementHandler.class, method = "batch", args = {Statement.class})
})
@Slf4j
@Component("mybatisLog")
public class MybatisLog implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = invocation.proceed();
        long end = System.currentTimeMillis();
        PreparedStatement statement = (PreparedStatement) invocation.getArgs()[0];
        if(Proxy.isProxyClass(statement.getClass())){
            InvocationHandler preparedStatementLogger = Proxy.getInvocationHandler(statement);
            if(preparedStatementLogger.getClass().getName().endsWith(".PreparedStatementLogger")){
                Field field = preparedStatementLogger.getClass().getDeclaredField("statement");
                field.setAccessible(true);
                PreparedStatement clientPreparedStatement = null;
                PreparedStatement tempPreparedStatement = (PreparedStatement) field.get(preparedStatementLogger);

                if (tempPreparedStatement.getClass().getName().endsWith(".DruidPooledPreparedStatement")) {
                    field = tempPreparedStatement.getClass().getDeclaredField("stmt");
                    field.setAccessible(true);
                    tempPreparedStatement = (PreparedStatement) field.get(tempPreparedStatement);

                    if (tempPreparedStatement.getClass().getName().endsWith(".ClientPreparedStatement")) {
                        clientPreparedStatement = tempPreparedStatement;
                    } else if (tempPreparedStatement.getClass().getName().endsWith(".PreparedStatementProxyImpl")){
                        field = tempPreparedStatement.getClass().getDeclaredField("statement");
                        field.setAccessible(true);
                        tempPreparedStatement = (PreparedStatement) field.get(tempPreparedStatement);
                        if (tempPreparedStatement.getClass().getName().endsWith(".ClientPreparedStatement")) {
                            clientPreparedStatement = tempPreparedStatement;
                        }
                    }

                }  else {
                    clientPreparedStatement = tempPreparedStatement;
                }
                Long cost = end - start;
                log.debug(" customMybatisLog cost:{} ms, costFirst-{}, costLevel-{},  Sql:{} ", cost, String.valueOf(cost).charAt(0), String.valueOf(cost).length(), clientPreparedStatement.toString().replaceAll("[\\s\n ]+"," ").replaceFirst("com.mysql.cj.jdbc.ClientPreparedStatement:", " "));
            }
        }

        return result;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
