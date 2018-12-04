package com.zhen.base.web.shiro.realm;

import com.zhen.base.domain.system.User;
import com.zhen.base.service.ILoginService;
import com.zhen.exception.BusinessException;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created with IntelliJ IDEA
 * <p>
 * Description：自定义Realm
 * Auth：wuhengzhen
 * Date：2018-12-03
 * Time：17:23
 */
public class UserRealm extends AuthorizingRealm {
    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(UserRealm.class);
    @Autowired
    private ILoginService loginService;

    /**
     * 授权：验证权限时调用
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = (String) principalCollection.getPrimaryPrincipal();
        // UpmsUser upmsUser = upmsApiService.selectUpmsUserByUsername(username);
        //
        // // 当前用户所有角色
        // List<UpmsRole> upmsRoles = upmsApiService.selectUpmsRoleByUpmsUserId(upmsUser.getUserId());
        // Set<String> roles = new HashSet<>();
        // for (UpmsRole upmsRole : upmsRoles) {
        //     if (StringUtils.isNotBlank(upmsRole.getName())) {
        //         roles.add(upmsRole.getName());
        //     }
        // }
        //
        // // 当前用户所有权限
        // List<UpmsPermission> upmsPermissions = upmsApiService.selectUpmsPermissionByUpmsUserId(upmsUser.getUserId());
        // Set<String> permissions = new HashSet<>();
        // for (UpmsPermission upmsPermission : upmsPermissions) {
        //     if (StringUtils.isNotBlank(upmsPermission.getPermissionValue())) {
        //         permissions.add(upmsPermission.getPermissionValue());
        //     }
        // }

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        // simpleAuthorizationInfo.setStringPermissions(permissions);
        // simpleAuthorizationInfo.setRoles(roles);
        return simpleAuthorizationInfo;
    }

    /**
     * 认证：登录时调用
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //1.将token转换为UsernamePasswordToken
        UsernamePasswordToken userToken = (UsernamePasswordToken) authenticationToken;
        //2.获取token中的登录账户
        String userName = userToken.getUsername();
        //3.查询数据库，是否存在指定的用户名和密码的用户(主键/账户/密码/账户状态/盐)
        // 用户名
        // String userName = (String) authenticationToken.getPrincipal();
        logger.info("userName:" + userName);
        // 密码
        String password = new String((char[]) authenticationToken.getCredentials());
        logger.info("password:" + password);
        // client无密认证
        // String upmsType = PropertiesFileUtil.getInstance("zhen-upms-client").get("zhen.upms.type");
        // if ("client".equals(upmsType)) {
        //     return new SimpleAuthenticationInfo(userName, password, getName());
        // }

        // 查询用户信息
        User user = null;
        try {
            user = loginService.selectUserByUsername(userName);
        } catch (BusinessException e) {
            throw new UnknownAccountException();
        }
        if (null == user) {
            throw new UnknownAccountException();
        }
        if (!user.getPassword().equals(password)) {
            throw new IncorrectCredentialsException();
        }
        // if (user.getLocked() == 1) {
        //     throw new LockedAccountException();
        // }

        //4.2 如果查询到了，封装查询结果，
        Object principal = user.getLoginName();
        Object credentials = user.getPassword();
        String realmName = this.getName();
        // String salt = user.getSalt();
        //获取盐，用于对密码在加密算法(MD5)的基础上二次加密ֵ
        // ByteSource byteSalt = ByteSource.Util.bytes(salt);
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(principal, credentials, realmName);
        //5. 返回给调用login(token)方法
        return info;

        // return new SimpleAuthenticationInfo(userName, password, getName());
    }
}
