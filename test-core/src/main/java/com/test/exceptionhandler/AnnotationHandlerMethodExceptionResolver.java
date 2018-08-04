package com.test.exceptionhandler; /**
 * 软件著作权：长安新生（深圳）金融投资有限公司
 * 
 * 系统名称：马达贷
 * 
 */


import com.test.exception.BusinessException;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

/**
 *
 *
 * @author 郑翔
 */
public class AnnotationHandlerMethodExceptionResolver extends ExceptionHandlerExceptionResolver{

	//private static Logger logger = LoggerFactory.getLogger(AnnotationHandlerMethodExceptionResolver.class);
	  private String defaultErrorView;
	  private Properties exceptionMappings;

	  public String getDefaultErrorView()
	  {
	    return this.defaultErrorView;
	  }

	  public void setDefaultErrorView(String defaultErrorView) {
	    this.defaultErrorView = defaultErrorView;
	  }

	  protected ModelAndView doResolveHandlerMethodException(HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod, Exception exception)
	  {
		 
		  exception.printStackTrace();
		  
	    if (handlerMethod == null) {
	      return null;
	    }

	    Method method = handlerMethod.getMethod();

	    if (method == null) {
	      return null;
	    }

	    ModelAndView returnValue = super.doResolveHandlerMethodException(request, response, handlerMethod, exception);

	    ResponseBody responseBodyAnn = (ResponseBody)AnnotationUtils.findAnnotation(method, ResponseBody.class);
	    if (("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) || (responseBodyAnn != null)) {
	      try
	      {
	        ModelAndView jsonView = new ModelAndView(new MappingJackson2JsonView());

	        if ((exception instanceof BusinessException)) {
	         // logger.error("PanCloud捕获BusinessException:" + exception.getMessage());
	          jsonView.addObject(exception.getMessage());
	        } else {
	         // logger.error("PanCloud捕获非BusinessException:" + exception.getMessage());
	          jsonView.addObject("系统异常！");
	        }
	        response.setStatus(500);
	        return jsonView;
	      }
	      catch (Exception e) {
	        return null;
	      }
	    }
	    if (this.exceptionMappings != null) {
	      Set<Entry<Object,Object>> eSet = this.exceptionMappings.entrySet();
	      for (Entry entry : eSet) {
	        if (exception.getClass().getName().equals(entry.getKey())) {
	          Map param = new HashMap();
	          String errorMsg = exception.getMessage();
	          
	          if (StringUtils.isEmpty(errorMsg)) {
	            errorMsg = "系统异常！";
	          }
	          try {
				param.put("errorMsg", URLEncoder.encode(errorMsg,"UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
	          //logger.error("PanCloud捕获" + entry.getKey() + ":" + errorMsg);
	          returnValue = new ModelAndView(entry.getValue().toString(), param);
	          break;
	        }
	      }
	    }

	    if (returnValue == null) {
	      Map param = new HashMap();
	      try {
			param.put("errorMsg",   URLEncoder.encode("系统异常！","UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	      response.reset();
	      returnValue = new ModelAndView(this.defaultErrorView, param);
	    }

	    return returnValue;
	  }

	  public Properties getExceptionMappings()
	  {
	    return this.exceptionMappings;
	  }

	  public void setExceptionMappings(Properties exceptionMappings) {
	    this.exceptionMappings = exceptionMappings;
	  }
	
}
