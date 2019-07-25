package com.zhen.base.domain.mybatisplus;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA
 *
 * @ClassName：User
 * @Description：Mybatis Plus 测试-实体类
 * @Author：wuhengzhen
 * @Date：2019-07-24 16:49
 */
@TableName("t_user")
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class User extends Model<User> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;
    @TableField("user_login_name")
    private String userLoginName;
    @TableField("user_pwd")
    private String userPwd;
    @TableField("user_name")
    private String userName;
    /**
     * state 1启用 0冻结
     */
    private Integer state;
    @TableField("create_time")
    private Date createTime;
    /**
     * delState 删除状态1 删除 0未删除
     */
    @TableField("del_state")
    private Integer delState;

    @Override
    protected Serializable pkVal() {
        return this.userId;
    }
}
