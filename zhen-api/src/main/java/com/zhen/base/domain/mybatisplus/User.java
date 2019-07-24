package com.zhen.base.domain.mybatisplus;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA
 *
 * @ClassName：User
 * @Description：Mybatis Plus 测试-实体类
 * @Author：wuhengzhen
 * @Date：2019-07-24 16:49
 */
public class User implements Serializable {

    private static final long serialVersionUID = -3778655381224099403L;
    private int userId;
    private String userLoginName;
    private String userPwd;
    private String userName;
    private int state;
    private String createTime;
    private int delState;

    public User(String userLoginName, String userPwd) {
        super();
        this.userLoginName = userLoginName;
        this.userPwd = userPwd;
    }

    public User(String userName, int state) {
        super();
        this.userName = userName;
        this.state = state;
    }

    public User(int userId, String userLoginName, String userPwd, String userName, int state, String createTime, int delState) {
        super();
        this.userId = userId;
        this.userLoginName = userLoginName;
        this.userPwd = userPwd;
        this.userName = userName;
        this.state = state;
        this.createTime = createTime;
        this.delState = delState;
    }

    public User() {
        super();
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserLoginName() {
        return userLoginName;
    }

    public void setUserLoginName(String userLoginName) {
        this.userLoginName = userLoginName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getDelState() {
        return delState;
    }

    public void setDelState(int delState) {
        this.delState = delState;
    }
}
