package com.zhen.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.Assert;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: Bean 复制工具类
 * @author:lidetian
 * @date: 2018/3/21
 * @system name:
 * @copyright:
 */
public class BeanUtils extends org.springframework.beans.BeanUtils {

    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(BeanUtils.class);

    /**
     * description :JavaBean之间的属性复制，属性值非null才进行复制（推荐）
     * author : wuhengzhen
     * date : 2018-10-25 11:35
     */
    public static void copyProperties(Object source, Object target) throws BeansException {
        Assert.notNull(source, "Source must not be null");
        Assert.notNull(target, "Target must not be null");
        Class<?> actualEditable = target.getClass();
        PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);
        for (PropertyDescriptor targetPd : targetPds) {
            if (targetPd.getWriteMethod() != null) {
                PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(), targetPd.getName());
                if (sourcePd != null && sourcePd.getReadMethod() != null) {
                    try {
                        Method readMethod = sourcePd.getReadMethod();
                        if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                            readMethod.setAccessible(true);
                        }
                        Object value = readMethod.invoke(source);
                        // 这里判断以下value是否为空 当然这里也能进行一些特殊要求的处理 例如绑定时格式转换等等
                        if (value != null) {
                            Method writeMethod = targetPd.getWriteMethod();
                            if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                                writeMethod.setAccessible(true);
                            }
                            writeMethod.invoke(target, value);
                        }
                    } catch (Throwable ex) {
                        throw new FatalBeanException("Could not copy properties from source to target", ex);
                    }
                }
            }
        }
    }

    /**
     * Map --> Bean: 利用org.apache.commons.beanutils 工具类实现 Map --> Bean
     *
     * @param map  要转换的Map
     * @param type JavaBean的类型
     * @return JavaBean
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws InvocationTargetException
     */
    public static Object transMap2Bean(Map map, Class type) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        if (map == null) {
            return null;
        }
        // 创建 JavaBean 对象
        Object obj = type.newInstance();
        org.apache.commons.beanutils.BeanUtils.populate(obj, map);
        return obj;
    }

    /**
     * Map --> Bean：利用Introspector,PropertyDescriptor实现 Map --> Bean
     *
     * @param type 要转化的类型
     * @param map  包含属性值的 map
     * @return 转化出来的 JavaBean 对象
     * @throws IntrospectionException    如果分析类属性失败
     * @throws IllegalAccessException    如果实例化 JavaBean 失败
     * @throws InstantiationException    如果实例化 JavaBean 失败
     * @throws InvocationTargetException 如果调用属性的 setter 方法失败
     * @author: wuhengzhen
     * @date: 2018-7-4 09:40:46
     */
    @SuppressWarnings("rawtypes")
    public static Object transMap2Bean2(Map map, Class type) throws IntrospectionException, IllegalAccessException, InstantiationException, InvocationTargetException {
        if (map == null) {
            return null;
        }
        // 获取类属性
        BeanInfo beanInfo = Introspector.getBeanInfo(type);
        // 创建 JavaBean 对象
        Object obj = type.newInstance();

        // 给 JavaBean 对象的属性赋值
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor descriptor : propertyDescriptors) {
            //获取属性名
            String propertyName = descriptor.getName();
            //获取属性类型
            String propertyType = descriptor.getPropertyType().getTypeName();
            if (map.containsKey(propertyName)) {
                System.out.println("propertyName：" + propertyName + " propertyValue：" + map.get(propertyName));
                // 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。
                Object[] args = new Object[1];
                // 如果数据库类型是BigDecimal，传来不可以是非BigDecimal类型或者为空和null，会产生参数类型不匹配异常，
                // 这里将需要类型为BigDecimal的前台数据做类型转换
                if ("java.math.BigDecimal".equals(propertyType)) {
                    if (map.get(propertyName) == null || "".equals(map.get(propertyName))) {
                        continue;
                    }
                    BigDecimal value = new BigDecimal(map.get(propertyName).toString());
                    args[0] = value;
                } else {
                    Object value = map.get(propertyName);
                    args[0] = value != null ? value.toString() : "";
                }
                descriptor.getWriteMethod().invoke(obj, args);
            }
        }
        return obj;
    }

    /**
     * JavaBean -> Map
     *
     * @param object 要转换的Javabean
     * @return Map
     * @throws IllegalAccessException    异常
     * @throws NoSuchMethodException     异常
     * @throws InvocationTargetException 异常
     */
    public static Map transObject2Map(Object object) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (object == null) {
            return null;
        }
        return org.apache.commons.beanutils.BeanUtils.describe(object);
    }

    /**
     * JavaBean -> Map (忽略serialVersionUID)
     *
     * @param obj JavaBean
     * @return Map
     */
    public static Map<String, Object> transObject2Map2(Object obj) {
        Map<String, Object> map = new HashMap<>();
        if (obj != null) {
            //查询出对象所有的属性
            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                //不检查 直接取值
                field.setAccessible(true);
                System.out.println("field.getName()=" + field.getName());
                try {
                    if (!"serialVersionUID".equals(field.getName())) {
                        //不为空
                        System.out.println("field.get(obj)=" + field.get(obj));
                        map.put(field.getName(), field.get(obj));
                    }
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return map;
    }

    /**
     * <p>Discription:[返回指定的字段名fileName的File对象]</p>
     * Created on 2018年12月6日 下午3:48:03
     *
     * @param fieldName 指定的字段名
     * @param object    当前dubbo调用的参数
     * @return Field 返回指定的字段名fileName的File对象
     * @author: wuhengzhen
     */
    public static Field getFieldByClass(String fieldName, Object object) {
        Field field = null;
        Class<?> clazz = object.getClass();
        // getSuperclass获取普通函数的父类Class对象
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                // getDeclaredField获取指定name的类里属性
                field = clazz.getDeclaredField(fieldName);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        return field;
    }

    /**
     * 判断对象是否为空，包括全部字段为空
     *
     * @param obj 对象名
     * @return 是否不为空
     */
    public static boolean isNotEmpty(Object obj) {
        if (null == obj) {
            return false;
        }
        //查询出对象所有的属性
        Field[] fields = obj.getClass().getDeclaredFields();
        //用于判断所有属性是否为空,如果参数为空则不查询
        boolean flag = false;
        for (Field field : fields) {
            //不检查 直接取值
            field.setAccessible(true);
            System.out.println("field.getName()=" + field.getName());
            try {
                if (StringUtils.isNotBlank(field.get(obj)) && !"serialVersionUID".equals(field.getName())) {
                    //不为空
                    flag = true;
                    //当有任何一个参数不为空的时候则跳出判断直接查询
                    break;
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return flag;
    }

    /**
     * 判断对象为空
     *
     * @param obj 对象名
     * @return 是否不为空
     */
    public static boolean isEmpty(Object obj) {
        return !isNotEmpty(obj);

    }
}
