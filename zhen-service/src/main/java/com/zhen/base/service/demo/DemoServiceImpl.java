package com.zhen.base.service.demo;

import com.zhen.base.domain.system.RequestLog;
import com.zhen.common.master.BaseRequest;
import com.zhen.common.master.Master;
import com.zhen.utils.shiro.ShiroUser;
import com.zhen.utils.shiro.ShiroUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author wuhengzhen
 * @Description：
 * @date：2018-09-17 17:27
 */
@Component
public class DemoServiceImpl implements IDemoService {
    private static final Logger logger = LoggerFactory.getLogger(DemoServiceImpl.class);

    /**
     * say hello
     *
     * @param request
     * @return
     */
    @Override
    public String sayHello(BaseRequest request) {
        String str;
        /*JSONObject reqData = new JSONObject();
        JSONObject msg = new JSONObject();
        msg.put("name", name);
        reqData.put("data", msg);
        reqData.put("msgNo", "1001");
        reqData.put("reqTime", DateUtils.formatDateTime(new Date()));
        String url = "http://10.10.10.93:9696/test-outapiweb/outapi/testApi";
        String resp = HttpClientUtil.doPostJson(url, reqData.toJSONString());

        logger.info("service 返回消息为：" + resp);
        return resp;*/
        str = " hello !!";
        // 获取登陆信息
        Master master = request.getValueFormData("master");
        ShiroUser user = ShiroUtil.getSessionInfo(master);
        logger.info(user.toString());
        String userCde = user.getUserCde();
        System.out.println(userCde + str);
        return userCde + str;
    }

    @Override
    public void insertUpmsLogSelective(RequestLog reqlog) {
        logger.info("请求日志入库...");
    }
}
