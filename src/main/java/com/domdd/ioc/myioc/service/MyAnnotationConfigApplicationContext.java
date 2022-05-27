package com.domdd.ioc.myioc.service;

import com.domdd.ioc.myioc.annotation.MyAutowired;
import com.domdd.ioc.myioc.annotation.MyComponent;
import com.domdd.ioc.myioc.annotation.MyQualifier;
import com.domdd.ioc.myioc.annotation.MyValue;
import com.domdd.ioc.myioc.utils.PackageUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author lw
 * @date 2022/5/27 2:26 下午
 * 1.自定义一个MyAnnotationConfigApplicationContext,构造器中传入要扫描的包名;
 * 2.获取这个包下的所有类;
 * 3.遍历这些类，找到@Component注解的类，获取它的Claa和对应的BeanName,封装为BeanDefinition，存入Set;
 * 4.遍历Set集合，利用反射机制创建对象，同时对Value注解下的属性进行赋值，对Autowired的注解完成自动注入完成赋值,将这些动态生成的对象以key-value的形式存入缓存区;
 * 5.提供getBean方法，通过BeanName方法拿到对应的Bean;
 */
public class MyAnnotationConfigApplicationContext {

    // 模拟IOC容器
    private Map<String, Object> MyIoc = new HashMap<>();
    //管理BeanNames
    private List<String> beanNames = new ArrayList<>();


    //定义构造器
    public MyAnnotationConfigApplicationContext(String packageName) {
        //遍历包名，拿到对应的BeanDefinition
        Set<BeanDefinition> beanDefinitions = getBeanDefinitions(packageName);

        //根据拿到的BeanDefinition，动态生成对象
        createTargetObject(beanDefinitions);

        //根据拿到的BeanDefinition,完成自动装配
        autowireObject(beanDefinitions);
    }

    /*
     将添加了注解的类的class与BeanName封装为BeanDefinition存入Set
     */
    public Set<BeanDefinition> getBeanDefinitions(String packageName) {

        Set<BeanDefinition> beanDefinitions = new HashSet<>();
        //获取包下所有的类
        Set<Class<?>> classes = PackageUtils.getClasses(packageName);

        //遍历Set集合，拿到添加了注解的类
        Iterator<Class<?>> iterator = classes.iterator();

        while (iterator.hasNext()) {
            Class<?> clazz = iterator.next();
            //判断是否加上注解
            MyComponent myComponent = clazz.getAnnotation(MyComponent.class);

            //通过myComponent来判断是否加上注解
            if (myComponent != null) {
                //获取注解标记对象在容器中的名字BeanName
                String beanName = myComponent.value();
                //如果用户没有写，默认为类名的首字母小写
                if ("".equals(beanName)) {
                    //clazz.getName(); 拿到类的全限定名称
                    // clazz.getPackage().getName() 拿到类的包名
                    //  String defaultName=clazz.getName().replaceAll(clazz.getPackage().getName()+".","");
                    String defaultName = clazz.getSimpleName();
                    defaultName = defaultName.substring(0, 1).toLowerCase() + defaultName.substring(1);
                    beanName = defaultName;
                }
                BeanDefinition beanDefinition = new BeanDefinition(beanName, clazz);
                beanDefinitions.add(beanDefinition);
            }
        }
        return beanDefinitions;
    }

    /*
      根据拿到的BeanDefinitions根据反射来创建目标对象
     */
    public void createTargetObject(Set<BeanDefinition> beanDefinitions) {
        Iterator<BeanDefinition> iterator = beanDefinitions.iterator();
        while (iterator.hasNext()) {
            BeanDefinition beanDefinition = iterator.next();
            //拿到Class
            Class beanClass = beanDefinition.getBeanClass();
            //拿到BeanName
            String beanName = beanDefinition.getBeanName();
            try {
                //根据对象的模板来生产对象，利用反射机制，底层采用无参构造机制
                Object object = beanClass.getConstructor().newInstance();
                //根据注解MyValue来自动装配，完成属性的赋值
                Field[] declaredFields = beanClass.getDeclaredFields();
                for (Field declaredField : declaredFields) {
                    //判断成员变量是否被MyValue注解标识
                    MyValue myValue = declaredField.getAnnotation(MyValue.class);
                    if (myValue != null) {
                        //拿到注解中的赋值,这里用String来接受可能会产生类型不匹配
                        String value = myValue.value();
                        //拿到方法名：
                        String fieldName = declaredField.getName();
                        String methodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                        // 通过Set方法来赋值
                        Method method = beanClass.getMethod(methodName, declaredField.getType());

                        //完成类型转换,这里只是做了简单的类型处理，真正的IOC需要更完善的类型转换
                        Object val = null;
                        switch (declaredField.getType().getName()) {
                            case "java.lang.Integer":
                                val = Integer.parseInt(value);
                                break;
                            case "java.lang.String":
                                val = value;
                                break;
                            case "java.lang.Float":
                                val = Float.parseFloat(value);
                                break;
                        }
                        method.invoke(object, val);
                    }
                }
                //将创建的对象存入IOC容器中
                MyIoc.put(beanName, object);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }

    /*
     根据拿到的原材料进行自动装载
    */
    public void autowireObject(Set<BeanDefinition> beanDefinitions) {
        Iterator<BeanDefinition> iterator = beanDefinitions.iterator();
        while (iterator.hasNext()) {
            BeanDefinition beanDefinition = iterator.next();
            //拿到Class,遍历当前Class每一个属性，判断是否需要自动装载
            Class beanClass = beanDefinition.getBeanClass();
            Field[] declaredFields = beanClass.getDeclaredFields();
            for (Field declaredField : declaredFields) {
                MyAutowired myAutowired = declaredField.getAnnotation(MyAutowired.class);
                //该属性需要自动装配
                if (myAutowired != null) {
                    //判断是否通过名字注入
                    MyQualifier myQualifier = declaredField.getAnnotation(MyQualifier.class);
                    //通过名称注入，byName
                    if (myQualifier != null) {
                        //   System.out.println("需要自动装配的属性:"+declaredField);
                        try {
                            String beanName = myQualifier.value();

                            Object autowiredValue = getBean(beanName);
                            //拿到方法名：
                            String fieldName = declaredField.getName();
                            String methodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                            Method method = beanClass.getMethod(methodName, declaredField.getType());

                            //拿到需要赋值的对象
                            Object object = getBean(beanDefinition.getBeanName());
                            System.out.println("通过ByNameXSx");
                            method.invoke(object, autowiredValue);
                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                    // 通过类型注入,ByType
                    else {
                        //遍历容器中的所有对象
                        for (String beanName : MyIoc.keySet()) {
                            //如果类型与filed的属性一样
                            if (getBean(beanName).getClass() == declaredField.getType()) {
                                //拿到方法名：
                                try {
                                    String fieldName = declaredField.getName();
                                    String methodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                                    Method method = beanClass.getMethod(methodName, declaredField.getType());
                                    //拿到需要赋值的对象
                                    Object object = getBean(beanDefinition.getBeanName());
                                    System.out.println("通过ByType");
                                    method.invoke(object, getBean(beanName));
                                } catch (NoSuchMethodException e) {
                                    e.printStackTrace();
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                } catch (InvocationTargetException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /*
    提供getBean方法，根据在容器中的标记值拿到对应的对象
    */
    public Object getBean(String beanName) {
        return MyIoc.get(beanName);
    }

    /*
       提供getBeanDefinitionNames，返回容器中管理对象的BeanName
     */
    public String[] getBeanDefinitionNames() {
        return beanNames.toArray(new String[0]);
    }

    /*
       提供getBeanDefinitionCount，返回容器中管理对象的数量
     */
    public Integer getBeanDefinitionCount() {
        return beanNames.size();
    }


//    public static void main(String[] args) {
//            MyAnnotationConfigApplicationContext applicationContext = new
//                    MyAnnotationConfigApplicationContext("com.domdd.ioc.myioc.dto");
//            Object persion = applicationContext.getBean("persion");
//            System.out.println(persion);
//            //Account account= (Account) applicationContext.getBean("Account");
//    }
}




