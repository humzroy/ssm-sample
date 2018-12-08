package com.zhen.common.rpc.provider;

import com.alibaba.dubbo.common.utils.ReflectUtils;
import com.alibaba.dubbo.rpc.*;
import com.alibaba.dubbo.rpc.service.GenericService;
import com.zhen.common.rpc.domain.FilterDesc;
import com.zhen.exception.BusinessException;
import com.zhen.utils.BeanUtils;
import com.zhen.utils.JsonUtils;
import com.zhen.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA
 * <p>
 * 功能：
 * <ol>
 * <li>[dubbo提供者日志过滤器Filter]<br>
 * <li>不期望的异常打ERROR日志（Provider端）<br>
 * 不期望的日志即是，没有的接口上声明的Unchecked异常。
 * <li>异常不在API包中，则Wrap一层RuntimeException。<br>
 * RPC对于第一层异常会直接序列化传输(Cause异常会String化)，避免异常在Client出不能反序列化问题。
 * </ol>
 * Author：wuhengzhen
 * Date：2018-12-06
 * Time：18:07
 */
@Slf4j
public class LogFilter implements Filter {
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        // 先给当前线程赋个初始随机数，预防调用当前dubbo项目的消费者没有传traceId字段
        Thread.currentThread().setName(StringUtils.getStringRandom(8));

        // 通过以下for循环，拿到消费者传过来的traceId字段，即 当前请求的线程名称
        for (Object object : invocation.getArguments()) {
            // 字符串traceId是此次dubbo请求参数对象里的一个字段属性
            Field field = BeanUtils.getFieldByClass("traceId", object);
            if (field != null) {
                // 设置成true的作用就是让我们在用反射时可以对私有变量操作
                field.setAccessible(true);
                try {
                    Object traceId = field.get(object);
                    if (traceId != null) {
                        // 将当前线程的名称设置成消费者传过来的名称
                        Thread.currentThread().setName((String) traceId);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        FilterDesc filterReq = new FilterDesc();
        filterReq.setInterfaceName(invocation.getInvoker().getInterface().getName());
        filterReq.setMethodName(invocation.getMethodName());
        filterReq.setArgs(invocation.getArguments());
        // 获取消费者的ip地址
        String clientIp = RpcContext.getContext().getRemoteHost();
        log.info(clientIp + " --->" + JsonUtils.obj2Json(filterReq));
        long start = System.currentTimeMillis();
        // 调用服务的方法
        Result result = invoker.invoke(invocation);
        long cost = System.currentTimeMillis() - start;

        // 以下代码就是根据返回的数据 打印日志
        String backMessage = null;
        if (result.hasException() && GenericService.class != invoker.getInterface()) {
            log.info("dubbo执行异常!");
            try {
                Throwable exception = result.getException();
                // 如果是checked异常，直接抛出
                if (!(exception instanceof RuntimeException) && (exception instanceof Exception)) {
                    return result;
                }
                // 在方法签名上有声明，直接抛出
                try {
                    Method method = invoker.getInterface().getMethod(invocation.getMethodName(), invocation.getParameterTypes());
                    Class<?>[] exceptionClassses = method.getExceptionTypes();
                    for (Class<?> exceptionClass : exceptionClassses) {
                        if (exception.getClass().equals(exceptionClass)) {
                            return result;
                        }
                    }
                } catch (NoSuchMethodException e) {
                    return result;
                }
                // 在服务器端打印ERROR日志
                log.error("Got unchecked and undeclared exception which called by " + RpcContext.getContext().getRemoteHost()
                        + ". service: " + invoker.getInterface().getName() + ", method: " + invocation.getMethodName()
                        + ", exception: " + exception.getClass().getName() + ": " + exception.getMessage() + ", Exception StackTrace:", exception);

                // 异常类和接口类在同一jar包里，直接抛出
                String serviceFile = ReflectUtils.getCodeBase(invoker.getInterface());
                String exceptionFile = ReflectUtils.getCodeBase(exception.getClass());
                if (serviceFile == null || exceptionFile == null || serviceFile.equals(exceptionFile)) {
                    return result;
                }
                // 是JDK自带的异常，直接抛出
                String className = exception.getClass().getName();
                if (className.startsWith("java.") || className.startsWith("javax.")) {
                    return result;
                }
                // 是Dubbo本身的异常，直接抛出
                if (exception instanceof RpcException) {
                    return result;
                }
                if (result.getException() instanceof BusinessException) {
                    backMessage = "业务处理异常！errMsg = " + (result.getException()).getMessage();
                }
            } catch (Throwable e) {
                log.warn("Fail to LogFilter when called by " + RpcContext.getContext().getRemoteHost()
                        + ". service: " + invoker.getInterface().getName() + ", method: " + invocation.getMethodName()
                        + ", exception: " + e.getClass().getName() + ": " + e.getMessage(), e);
                return result;
            }
        } else {
            log.info("dubbo执行成功!");
            if (result.getValue() != null) {
                backMessage = JsonUtils.obj2Json(result.getValue());
                if (backMessage.length() > 500) {
                    backMessage = backMessage.substring(0, 500) + " ... ";
                }
            } else {
                log.info(clientIp + "<--" + "[无返回Msg]" + " cost :" + cost + "ms");
            }
        }
        if (StringUtils.isNotEmpty(backMessage)) {
            log.info(clientIp + "<--" + backMessage + " cost :" + cost + "ms");
        }
        if (cost > 2500) {
            log.info(clientIp + "<--" + backMessage + " cost :" + cost + " , cost long !");
        }
        return result;
    }
}

