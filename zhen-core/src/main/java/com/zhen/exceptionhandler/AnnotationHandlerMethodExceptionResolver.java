package com.zhen.exceptionhandler;

import com.zhen.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.*;
import java.util.Map.Entry;


/**
 * 不必在Controller中对异常进行处理，抛出即可，由此异常解析器统一控制。<br>
 * ajax请求（有@ResponseBody的Controller）发生错误，输出JSON。<br>
 * 页面请求（无@ResponseBody的Controller）发生错误，输出错误页面。<br>
 * 需要与AnnotationMethodHandlerAdapter使用同一个messageConverters<br>
 * Controller中需要有专门处理异常的方法。
 **/
public class AnnotationHandlerMethodExceptionResolver extends ExceptionHandlerExceptionResolver {

    private static Logger logger = LoggerFactory.getLogger(AnnotationHandlerMethodExceptionResolver.class);

    private String defaultErrorView;
    private Properties exceptionMappings;

    public String getDefaultErrorView() {
        return this.defaultErrorView;
    }

    public void setDefaultErrorView(String defaultErrorView) {
        this.defaultErrorView = defaultErrorView;
    }

    public Properties getExceptionMappings() {
        return this.exceptionMappings;
    }

    public void setExceptionMappings(Properties exceptionMappings) {
        this.exceptionMappings = exceptionMappings;
    }

    /**
     * 异常后跳转到页面
     *
     * @param request
     * @param response
     * @param handlerMethod
     * @param exception
     * @return
     */
    @Override
    protected ModelAndView doResolveHandlerMethodException(HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod, Exception exception) {

        exception.printStackTrace();

        if (handlerMethod == null) {
            return null;
        }

        Method method = handlerMethod.getMethod();

        if (method == null) {
            return null;
        }
        // 如果定义了ExceptionHandler则返回相应的Map中的数据
        ModelAndView returnValue = super.doResolveHandlerMethodException(request, response, handlerMethod, exception);

        ResponseBody responseBodyAnn = (ResponseBody) AnnotationUtils.findAnnotation(method, ResponseBody.class);
        if (("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) || (responseBodyAnn != null)) {
            try {
                ModelAndView jsonView = new ModelAndView(new MappingJackson2JsonView());

                if ((exception instanceof BusinessException)) {
                    logger.error("PanCloud捕获BusinessException:" + exception.getMessage());
                    jsonView.addObject(exception.getMessage());
                } else {
                    logger.error("PanCloud捕获非BusinessException:" + exception.getMessage());
                    jsonView.addObject("系统异常！");
                }
                response.setStatus(500);
                return jsonView;
            } catch (Exception e) {
                return null;
            }
        }
        if (this.exceptionMappings != null) {
            Set<Entry<Object, Object>> eSet = this.exceptionMappings.entrySet();
            for (Entry entry : eSet) {
                if (exception.getClass().getName().equals(entry.getKey())) {
                    Map param = new HashMap();
                    String errorMsg = exception.getMessage();

                    if (StringUtils.isEmpty(errorMsg)) {
                        errorMsg = "系统异常！";
                    }
                    try {
                        param.put("errorMsg", URLEncoder.encode(errorMsg, "UTF-8"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    logger.error("PanCloud捕获" + entry.getKey() + ":" + errorMsg);
                    returnValue = new ModelAndView(entry.getValue().toString(), param);
                    break;
                }
            }
        }

        if (returnValue == null) {
            Map param = new HashMap();
            try {
                param.put("errorMsg", URLEncoder.encode("系统异常！", "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            response.reset();
            returnValue = new ModelAndView(this.defaultErrorView, param);
        }

        return returnValue;
    }

    /**
     * 异常后 返回json
     *
     * @param returnValue
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    private ModelAndView handleResponseBody(ModelAndView returnValue, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map value = returnValue.getModelMap();
        HttpInputMessage inputMessage = new ServletServerHttpRequest(request);
        List<MediaType> acceptedMediaTypes = inputMessage.getHeaders().getAccept();
        if (acceptedMediaTypes.isEmpty()) {
            acceptedMediaTypes = Collections.singletonList(MediaType.ALL);
        }
        MediaType.sortByQualityValue(acceptedMediaTypes);
        HttpOutputMessage outputMessage = new ServletServerHttpResponse(response);
        Class<?> returnValueType = value.getClass();
        List<HttpMessageConverter<?>> messageConverters = super.getMessageConverters();
        if (messageConverters != null) {
            for (MediaType acceptedMediaType : acceptedMediaTypes) {
                for (HttpMessageConverter messageConverter : messageConverters) {
                    if (messageConverter.canWrite(returnValueType, acceptedMediaType)) {
                        messageConverter.write(value, acceptedMediaType, outputMessage);
                        return new ModelAndView();
                    }
                }
            }
        }
        if (logger.isWarnEnabled()) {
            logger.warn("Could not find HttpMessageConverter that supports return type [" + returnValueType + "] and " + acceptedMediaTypes);
        }
        return null;
    }

}
