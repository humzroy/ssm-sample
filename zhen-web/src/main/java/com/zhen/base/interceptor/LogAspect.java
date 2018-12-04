package com.zhen.base.interceptor;

import com.alibaba.fastjson.JSON;
import com.zhen.base.domain.system.RequestLog;
import com.zhen.base.service.demo.IDemoService;
import com.zhen.utils.RequestUtil;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.ObjectUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * 日志记录AOP实现
 * Created by wuhengzhen on 2018/11/14.
 */
@Aspect
public class LogAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogAspect.class);

    /**
     * 开始时间
     */
    private long startTime = 0L;
    /**
     * 结束时间
     */
    private long endTime = 0L;

    @Autowired
    private IDemoService demoService;

    @Before("execution(* *..controller..*.*(..))")
    public void doBeforeInServiceLayer(JoinPoint joinPoint) {
        LOGGER.debug("doBeforeInServiceLayer");
        startTime = System.currentTimeMillis();
    }

    @After("execution(* *..controller..*.*(..))")
    public void doAfterInServiceLayer(JoinPoint joinPoint) {
        LOGGER.debug("doAfterInServiceLayer");
    }

    @Around("execution(* *..controller..*.*(..))")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        // 获取request
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
        HttpServletRequest request = servletRequestAttributes.getRequest();

        RequestLog reqlog = new RequestLog();
        // 从注解中获取操作名称、获取响应结果
        Object result = pjp.proceed();
        Signature signature = pjp.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if (method.isAnnotationPresent(ApiOperation.class)) {
            ApiOperation log = method.getAnnotation(ApiOperation.class);
            reqlog.setDescription(log.value());
        }
        if (method.isAnnotationPresent(RequiresPermissions.class)) {
            RequiresPermissions requiresPermissions = method.getAnnotation(RequiresPermissions.class);
            String[] permissions = requiresPermissions.value();
            if (permissions.length > 0) {
                reqlog.setPermissions(permissions[0]);
            }
        }
        endTime = System.currentTimeMillis();
        LOGGER.debug("doAround>>>result={},耗时：{}", result, endTime - startTime);

        reqlog.setBasePath(RequestUtil.getBasePath(request));
        reqlog.setIp(RequestUtil.getIpAddr(request));
        reqlog.setMethod(request.getMethod());
        if ("GET".equalsIgnoreCase(request.getMethod())) {
            reqlog.setParameter(request.getQueryString());
        } else {
            reqlog.setParameter(ObjectUtils.toString(request.getParameterMap()));
        }
        reqlog.setResult(JSON.toJSONString(result));
        reqlog.setSpendTime((int) (endTime - startTime));
        reqlog.setStartTime(startTime);
        reqlog.setUri(request.getRequestURI());
        reqlog.setUrl(ObjectUtils.toString(request.getRequestURL()));
        reqlog.setUserAgent(request.getHeader("User-Agent"));
        reqlog.setUsername(ObjectUtils.toString(request.getUserPrincipal()));
        demoService.insertUpmsLogSelective(reqlog);
        return result;
    }

}
