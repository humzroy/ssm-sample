package com.zhen.aspect;

import com.zhen.datasource.DataSource;
import com.zhen.datasource.DataSourceConstant;
import com.zhen.datasource.DataSourceContextHolder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA
 *
 * @Description：
 * @Author：wuhengzhen
 * @Date：2019-05-16 16:48
 */
public class DataSourceAspect {
    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(DataSourceAspect.class);

    /**
     * 拦截目标方法，获取由@DataSource指定的数据源标识，设置到线程存储中以便切换数据源
     *
     * @param point
     * @throws Exception
     */
    public void before(JoinPoint point) throws Exception {
        logger.info("before......");

        // Object target = point.getTarget();
        // String method = point.getSignature().getName();
        // Class<?>[] classz = target.getClass().getInterfaces();
        // Class<?>[] parameterTypes = ((MethodSignature) point.getSignature()).getMethod().getParameterTypes();
        // try {
        //     Method m = classz[0].getMethod(method, parameterTypes);
        //     if (m != null && m.isAnnotationPresent(DataSource.class)) {
        //         // 访问mapper中的注解
        //         DataSource data = m.getAnnotation(DataSource.class);
        //         switch (data.value()) {
        //             case "MYSQL":
        //                 DataSourceContextHolder.setDataSourceType(DataSourceConstant.MYSQL);
        //                 logger.info("using dataSource:{}", DataSourceConstant.MYSQL);
        //                 break;
        //             case "ORACLE":
        //                 DataSourceContextHolder.setDataSourceType(DataSourceConstant.ORACLE);
        //                 logger.info("using dataSource:{}", DataSourceConstant.MYSQL);
        //                 break;
        //         }
        //     }
        // } catch (Exception e) {
        //     logger.error("dataSource annotation error:{}", e.getMessage());
        //     // 若出现异常，手动设为主库
        //     DataSourceContextHolder.setDataSourceType(DataSourceConstant.MYSQL);
        // }


        Class<?> target = point.getTarget().getClass();
        MethodSignature signature = (MethodSignature) point.getSignature();
        // 默认使用目标类型的注解，如果没有则使用其实现接口的注解
        for (Class<?> clazz : target.getInterfaces()) {
            resolveDataSource(clazz, signature.getMethod());
        }
        resolveDataSource(target, signature.getMethod());
        logger.info("using dataSource:{}", DataSourceContextHolder.getDataSourceType());
    }


    /**
     * 执行后将数据源置为空
     */
    public void after() {
        logger.info("after......");
        DataSourceContextHolder.clearDataSourceType();
    }

    /**
     * 提取目标对象方法注解和类型注解中的数据源标识
     *
     * @param clazz
     * @param method
     */
    private void resolveDataSource(Class<?> clazz, Method method) {
        try {
            Class<?>[] types = method.getParameterTypes();
            // 默认使用类型注解
            if (clazz.isAnnotationPresent(DataSource.class)) {
                DataSource source = clazz.getAnnotation(DataSource.class);
                DataSourceContextHolder.setDataSourceType(source.value());
            }
            // 方法注解可以覆盖类型注解
            Method m = clazz.getMethod(method.getName(), types);
            if (m != null && m.isAnnotationPresent(DataSource.class)) {
                DataSource source = m.getAnnotation(DataSource.class);
                DataSourceContextHolder.setDataSourceType(source.value());
            }
        } catch (Exception e) {
            logger.error("dataSource annotation error:{}", e.getMessage());
            // 若出现异常，手动设为MYSQL库
            DataSourceContextHolder.setDataSourceType(DataSourceConstant.MYSQL);
        }
    }

}
