package com.zhen.common.rpc.provider;

import com.alibaba.dubbo.rpc.*;
import com.zhen.exception.BusinessException;
import com.zhen.utils.BeanUtils;
import com.zhen.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;

/**
 * Created with IntelliJ IDEA
 * <p>
 * Description：[dubbo提供者日志过滤器Filter]
 * Auth：wuhengzhen
 * Date：2018-12-06
 * Time：18:07
 */
@Slf4j
public class LogFilter implements Filter {
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        long l = System.currentTimeMillis();
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
        // 获取消费者的ip地址
        String clientIp = RpcContext.getContext().getRemoteHost();
        log.info(clientIp + " --->" + invocation.getArguments()[0]);

        // 调用服务的方法
        Result result = invoker.invoke(invocation);
        long cost = System.currentTimeMillis() - l;

        // 以下代码就是根据返回的数据 打印日志
        String backMessage = null;
        if (result != null) {
            if (result.hasException()) {
                if (!(result.getException() instanceof BusinessException)) {
                    result.getException().printStackTrace();
                    backMessage = "业务处理异常！" + result.getException().toString();
                } else {
                    backMessage = "errMsg = " + (result.getException()).getMessage();
                }
            } else {
                backMessage = result.getValue().toString();
                if (backMessage.length() > 500) {
                    backMessage = backMessage.substring(0, 500) + " ... ";
                }
            }
            log.info(clientIp + "<--" + backMessage + " cost :" + cost + "ms");

            if (cost > 2500) {
                log.info(clientIp + "<--" + backMessage + " cost :" + cost + " , cost long !");
            }
        } else {
            log.info(clientIp + "<--" + "[无返回]" + " cost :" + cost + "ms");
        }
        return result;
    }
}

