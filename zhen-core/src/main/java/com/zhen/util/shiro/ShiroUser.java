package com.zhen.util.shiro;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA
 * <p>
 * Description：用户信息
 * Author：wuhengzhen
 * Date：2018-12-05
 * Time：14:42
 */
public class ShiroUser implements Serializable {

    private static final long serialVersionUID = -1737237586764042913L;
    /**
     * 用户ID
     */
    private String userCde;
    /**
     * 密码
     */
    private String password;
    /**
     * 用户姓名
     */
    private String userName;

    /**
     * 盐
     */
    private String salt;

    /**
     * email
     */
    private String email;

    /**
     * session
     */
    private String sessionId;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getUserCde() {
        return userCde;
    }

    public void setUserCde(String userCde) {
        this.userCde = userCde;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public String toString() {
        return "ShiroUser{" +
                "userCde='" + userCde + '\'' +
                ", password='" + password + '\'' +
                ", userName='" + userName + '\'' +
                ", salt='" + salt + '\'' +
                ", email='" + email + '\'' +
                ", sessionId='" + sessionId + '\'' +
                '}';
    }
}
