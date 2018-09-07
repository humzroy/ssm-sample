package com.test.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
public class CopyBeanUtils {
    /**
     * JavaBean属性值复制
     *
     * @param source
     * @param to
     * @throws Exception
     */
    public static void copy(Object source, Object to) throws Exception {
        // 获取属性
        BeanInfo sourceBean = Introspector.getBeanInfo(source.getClass(), Object.class);
        PropertyDescriptor[] sourceProperty = sourceBean.getPropertyDescriptors();

        BeanInfo destBean = Introspector.getBeanInfo(to.getClass(), Object.class);
        PropertyDescriptor[] destProperty = destBean.getPropertyDescriptors();
        try {
            for (PropertyDescriptor aSourceProperty : sourceProperty) {
                for (PropertyDescriptor aDestProperty : destProperty) {

                    if (aSourceProperty.getName().equals(aDestProperty.getName())) {
                        // 调用source的getter方法和dest的setter方法
                        aDestProperty.getWriteMethod().invoke(to, aSourceProperty.getReadMethod().invoke(source));
                        break;
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception("属性复制失败:" + e.getMessage());
        }
    }


    /**
     * 公共方法：将一个 Map对象转化为一个JavaBean
     *
     * @param type 要转化的类型
     * @param map  包含属性值的 map
     * @return 转化出来的 JavaBean 对象
     * @throws IntrospectionException    如果分析类属性失败
     * @throws IllegalAccessException    如果实例化 JavaBean 失败
     * @throws InstantiationException    如果实例化 JavaBean 失败
     * @throws InvocationTargetException 如果调用属性的 setter 方法失败
     * @Author: wuhengzhen
     * @Date: 2018-7-4 09:40:46
     */
    @SuppressWarnings("rawtypes")
    public static Object convertMapToBean(Class type, Map map)
            throws IntrospectionException, IllegalAccessException,
            InstantiationException, InvocationTargetException {
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
     * 公共方法：将一个 JavaBean对象转化为一个Map
     *
     * @param bean 要转化的JavaBean 对象
     * @return 转化出来的  Map 对象
     * @throws IntrospectionException    如果分析类属性失败
     * @throws IllegalAccessException    如果实例化 JavaBean 失败
     * @throws InvocationTargetException 如果调用属性的 setter 方法失败
     * @Author: wuhengzhen
     * @Date: 2018-7-4 09:40:46
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static Map<String, Object> convertBeanToMap(Object bean)
            throws IntrospectionException, IllegalAccessException, InvocationTargetException {
        Class type = bean.getClass();
        Map<String, Object> returnMap = new HashMap<>();
        BeanInfo beanInfo = Introspector.getBeanInfo(type);

        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor descriptor : propertyDescriptors) {
            String propertyName = descriptor.getName();
            if (!"class".equals(propertyName)) {
                Method readMethod = descriptor.getReadMethod();
                Object result = readMethod.invoke(bean);
                if (result != null) {
                    returnMap.put(propertyName, result);
                } else {
                    returnMap.put(propertyName, "");
                }
            }
        }
        return returnMap;
    }

}
