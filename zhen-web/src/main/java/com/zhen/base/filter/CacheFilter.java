/**
 * 软件著作权：长安新生（深圳）金融投资有限公司
 * <p>
 * 系统名称：马达贷
 */
package com.zhen.base.filter;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 缓存过滤器
 *
 * @author wuhengzhen
 */
public class CacheFilter extends OncePerRequestFilter {

    private static final int MAX_AGE = 30 * 24 * 60 * 60;

    @Override
    protected void doFilterInternal(final HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String curUrl = request.getRequestURL().toString();

        if (curUrl.indexOf("?") > 0) {
            curUrl = curUrl.substring(0, curUrl.indexOf("?"));
        }

        if (curUrl.endsWith(".js") || curUrl.endsWith(".css")) {
            response.setHeader("Cache-Control", "max-age=" + String.valueOf(MAX_AGE));
        } else if (curUrl.endsWith(".jpg") || curUrl.endsWith(".png")) {

            response.setHeader("Cache-Control", "max-age=" + String.valueOf(MAX_AGE));
        } else {
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
        }

        chain.doFilter(request, response);

    }
}
