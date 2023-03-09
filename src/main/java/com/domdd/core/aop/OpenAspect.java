package com.domdd.core.aop;

import cn.hutool.crypto.SecureUtil;
import com.alibaba.fastjson.JSON;
import com.domdd.controller.req.OpenBaseReq;
import com.domdd.core.DanengOpenPropertiesDo;
import com.domdd.core.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import javax.annotation.Resource;
import java.util.*;

import static cn.hutool.extra.validation.ValidationUtil.validate;


@Aspect
@Slf4j
@Component
@Order(1)
public class OpenAspect {
    public static String ValidateName = "bindingResult";

    @Resource
    private DanengOpenPropertiesDo danengOpenPropertiesDo;

    @Pointcut("execution(* com.domdd.controller.open.OpenController.*(..))")
    private void openPut() {

    }

    @Before("openPut()")
    public void openPut(JoinPoint point) {
        Object[] args = point.getArgs();
        Signature signature = point.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        String[] parameterNames = methodSignature.getParameterNames();
        OpenBaseReq arg = (OpenBaseReq) args[0];
        String sign = arg.getSign();
        arg.setSign(null);

        if (Objects.equals(methodSignature.getMethod().getName(), "afterSaleReturnOrderById")) {
            return;
        }

//        int hour = DateUtil.hour(DateUtil.date(), true);
//        boolean isProd = Objects.equals(profile, "prod") || Objects.equals(profile, "job") || Objects.equals(profile, "fuckdev");
//        if (DateUtil.date().isAfter(limitDate) && isProd && hour >= 6) {
//            throw new Exception("time not in 0~6 !");
//        }

        for (int i = 0; i < parameterNames.length; i++) {
            if (ValidateName.equals(parameterNames[i]) && args[i] instanceof BindingResult) {
                validate((BindingResult) args[i]);
                break;
            }
        }

        if (StringUtils.isBlank(DanengOpenPropertiesDo.appkey)) {
            danengOpenPropertiesDo.checkNull();
        }

        if (!Objects.equals(arg.getAppkey(), DanengOpenPropertiesDo.appkey)) {
            throw new ServiceException("appkey error");
        }

        if (!Objects.equals(getMethod(arg.getMethod()), signature.getName())) {
            throw new ServiceException("method error");
        }

        Map<String, Object> map = sortMap(JSON.parseObject(JSON.toJSONString(arg)));
        String computeSign = getSign((TreeMap<String, Object>) map);
        if (!Objects.equals(computeSign, sign)) {
            log.error("method:{} params:{} sign error current is:{}", methodSignature.getMethod(), JSON.toJSONString(arg), computeSign);
            throw new ServiceException("sign error");
        }
    }

    public static String getMethod(String method) {
        String begin = method.substring(0, method.indexOf("."));
        String substring = method.substring(method.indexOf(".") + 1);
        String substring1 = substring.substring(0, 1).toUpperCase();
        return begin + substring1 + substring.substring(1);
    }

    public static String generateSign(Map arg) {
        Map<String, Object> map = sortMap(JSON.parseObject(JSON.toJSONString(arg)));
        return getSign((TreeMap<String, Object>) map);
    }

    @SuppressWarnings("all")
    public static String getSign(TreeMap<String, Object> map) {
        StringBuilder preSign = new StringBuilder();
        preSign.append(DanengOpenPropertiesDo.signSecret);
        for (String key : map.keySet()) {
            preSign.append(key).append(map.get(key));
        }
        preSign.append(DanengOpenPropertiesDo.signSecret);
        return SecureUtil.md5(preSign.toString());
    }

    @SuppressWarnings("all")
    public static Set<String> sort(Map map) {
        List<String> list = new ArrayList<String>(map.keySet());
        Collections.sort(list, new Comparator<String>() {
            @Override
            public int compare(String a, String b) {
                return a.indexOf(0) - b.indexOf(0);
            }
        });

        return new TreeSet<String>(list);
    }

    @SuppressWarnings("all")
    public static Map<String, Object> sortMap(Map<String, Object> map) {
        List<Map.Entry<String, Object>> list = new ArrayList<Map.Entry<String, Object>>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Object>>() {
            @Override
            public int compare(Map.Entry<String, Object> o1,
                               Map.Entry<String, Object> o2) {
                return o1.getKey().indexOf(0) - o2.getKey().indexOf(0);
            }
        });
        Map<String, Object> result = new TreeMap<>();
        for (Map.Entry<String, Object> entry : list) {
            result.put(entry.getKey(), entry.getValue());

        }
        return result;
    }
}
