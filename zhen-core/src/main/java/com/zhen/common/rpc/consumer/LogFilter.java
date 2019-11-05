package com.zhen.common.rpc.consumer;

import com.alibaba.dubbo.rpc.*;
import com.zhen.exception.BusinessException;
import com.zhen.utils.BeanUtil;
import com.zhen.utils.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;

import java.lang.reflect.Field;

/**
 * Created with IntelliJ IDEA
 * <p>
 * Description：[dubbo消费者日志过滤器Filter]
 * Author：wuhengzhen
 * Date：2018-12-06
 * Time：15:57
 */
@Slf4j
public class LogFilter implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        /*
         * 这个for循环的作用：将消费者里此次请求的线程名称赋值到调用dubbo的参数属性里，由此让dubbo服务里的线程名和消费者统一
         */
        for (Object object : invocation.getArguments()) {
            // 字符串traceId是此次dubbo请求参数对象里的一个字段属性
            Field field = BeanUtil.getFieldByClass("traceId", object);
            if (field != null) {
                // 设置成true的作用就是让我们在用反射时可以对私有变量操作
                field.setAccessible(true);
                try {
                    // field的set方法两个参数：第一个参数是对象，第二个参数是将第一个参数里field字段的终值(终值就是要赋的值)
                    field.set(object, Thread.currentThread().getName());
                } catch (IllegalAccessException e) {
                    log.error(ExceptionUtil.getStackTrace(e));
                }
            }
        }
        /*
         * 拼接dubbo服务的参数成字符串
         */
        StringBuilder sb = new StringBuilder();
        int index = 1;
        // 请求参数的循环拼接
        for (Object object : invocation.getArguments()) {
            // isPrimitive()为true则当前object的类类型是基本类型，比如：byte,char,short,int,long,float,double,boolean和void
            if (object.getClass().isPrimitive() ||
                    "class java.lang.String".equals(object.getClass().toString()) ||
                    "class java.lang.Boolean".equals(object.getClass().toString())) {
                sb.append("参数").append(index).append(":").append(String.valueOf(object));
                if (index != invocation.getArguments().length) {
                    sb.append("\r\n");
                }
            } else {
                // isPrimitive()为false则当前object的类类型不是基本类型的，是Object，是对象
                try {
                    sb.append("参数").append(index).append(":").append(JSONObject.fromObject(object).toString());
                    if (index != invocation.getArguments().length) {
                        sb.append("\r\n");
                    }
                } catch (Exception e) {
                    sb.append("参数").append(index).append(":").append(object.toString());
                    if (index != invocation.getArguments().length) {
                        sb.append("\r\n");
                    }
                }
            }
            index++;
        }

        /*
         * 调取dubbo服务打印调取日志
         */
        long start = System.currentTimeMillis();
        log.info("RPC调用开始, 接口:{}, 方法:{}, 参数:{}", invoker.getInterface(), invocation.getMethodName(), sb.toString());
        // 继续调用dubbo服务，dubbo服务的响应结果存放在了Result中
        Result result = invoker.invoke(invocation);
        // 获取调用的提供者IP地址
        String serverIp = RpcContext.getContext().getRemoteHost();
        if (result.hasException()) {
            if (result.getException() instanceof BusinessException) {
                log.error("RPC({}) 调用发生异常, errMsg = {}", serverIp, (result.getException()), (result.getException()).getMessage());
            } else {
                log.error("RPC({}) 调用发生异常, {}", serverIp, result.getException());
            }
        } else {
            long elapsed = System.currentTimeMillis() - start;
            try {
                log.info("RPC({}) 调用结束, 返回值:{}, 耗时:{}ms", serverIp, JSONObject.fromObject(result.getValue()).toString(), elapsed);
            } catch (Exception e) {
                log.info("RPC({}) 调用结束, 返回值:{}, 耗时:{}ms", serverIp, result.getValue().toString(), elapsed);
            }
        }
        return result;
    }

}
