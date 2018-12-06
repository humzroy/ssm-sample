package com.zhen.utils.shiro;


import com.github.pagehelper.StringUtil;
import com.zhen.common.constant.BaseConstant;
import com.zhen.common.master.Master;
import com.zhen.exception.BusinessException;
import com.zhen.utils.RedisUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA
 * <p>
 * Description：Shiro工具类
 * Auth：wuhengzhen
 * Date：2018-12-05
 * Time：14:57
 */
public class ShiroUtil {

    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(ShiroUtil.class);

    private ShiroUtil() {
    }

    /**
     * 获得ShiroUser
     *
     * @return ShiroUser class
     * @author wuhengzhen
     */
    public static ShiroUser getShiroUser() {
        Subject subject = SecurityUtils.getSubject();
        return (ShiroUser) subject.getPrincipal();
    }

    /**
     * 获得 Subject
     *
     * @return Subject class
     * @author wuhengzhen
     */
    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    /**
     * 判断是否登录
     *
     * @return boolean
     * @author wuhengzhen
     */
    public static boolean isLogin() {
        Subject subject = SecurityUtils.getSubject();
        return subject != null && subject.isAuthenticated();
    }

    /**
     * 判断是否是锁定状态
     *
     * @param
     * @return
     */
    public static boolean isLocked(String enabled) {
        if (enabled.equals(BaseConstant.USER_STATUS_LOCKED) || enabled.equals(BaseConstant.USER_STATUS_UNACTIVATED_LOCKED)) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否是异常状态
     *
     * @param
     * @return
     */
    public static boolean isUnusual(String enabled) {
        return enabled.equals(BaseConstant.USER_STATUS_UNUSUAL) || enabled.equals(BaseConstant.USER_STATUS_UNACTIVATED_UNUSUAL);
    }

    /**
     * 判断是否是异常状态
     *
     * @param
     * @return
     */
    public static boolean isActivate(String enabled) {
        return enabled.equals(BaseConstant.USER_STATUS_ACTIVATED) || enabled.equals(BaseConstant.USER_STATUS_LOCKED) || enabled.equals(BaseConstant.USER_STATUS_UNUSUAL);
    }

    /**
     * 用户退出登录
     *
     * @param subject
     */
    public static void logout(Subject subject) {
        if (subject != null && subject.isAuthenticated()) {
            subject.logout();
        }

    }

    /**
     * 或权限判断
     *
     * @param roles
     */
    public static boolean hasRoleOr(String[] roles) {
        if (ShiroUtil.getSubject() == null) {
            return false;
        }
        for (String role : roles) {
            if (ShiroUtil.getSubject().hasRole(role)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 或权限判断
     *
     * @param
     */
    public static boolean hasRole(String role) {
        if (ShiroUtil.getSubject() == null) {
            return false;
        }
        if (ShiroUtil.getSubject().hasRole(role)) {
            return true;
        }
        return false;
    }

    /**
     * 从redis中获取登陆信息
     *
     * @param master 公共参数
     * @return ShiroUser class
     * @author wuhengzhen
     */
    public static ShiroUser getSessionInfo(Master master) {
        ShiroUser user;
        // 获取session信息
        if (StringUtil.isEmpty(master.getSign())) {
            logger.error("上送参数异常");
            // 抛出异常，让查询中断
            throw new BusinessException("sessionId为空！无法从redis获取登录信息！");
        } else {
            String sessionId = master.getSign();
            logger.info("SESSION:" + sessionId);
            // 从redis获取登录信息
            user = RedisUtils.getObject(master.getSign());
            if (user != null) {
                logger.info(user.getUserCde());
                return user;
            } else {
                logger.error("无法从redis获取登录信息！");
                //抛出异常，让查询中断
                throw new BusinessException("无法从redis获取登录信息！");
            }
        }
    }
}
