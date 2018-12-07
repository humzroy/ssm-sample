package com.zhen.base.interceptor;

import com.zhen.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA
 * <p>
 * Description：将当前线程设置一个唯一的Name，用于日志追踪
 * Auth：wuhengzhen
 * Date：2018-12-06
 * Time：17:09
 */
@Slf4j
public class ThreadInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        // 当前线程上下文唯一标示
        Thread.currentThread().setName(StringUtils.getStringRandom(12));
        log.info("当前线程唯一Name:" + Thread.currentThread().getName());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
