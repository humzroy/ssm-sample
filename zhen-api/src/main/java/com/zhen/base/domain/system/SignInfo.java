package com.zhen.base.domain.system;

import java.io.Serializable;

public class SignInfo implements Serializable {
    /**
     * id id
     */
    private Integer id;

    /**
     * 状态 stauts
     */
    private String stauts;

    /**
     * 创建者 create_by
     */
    private String createBy;

    /**
     * 创建时间 create_dt
     */
    private String createDt;

    /**
     * s_sign_info
     */
    private static final long serialVersionUID = 1L;

    /**
     * id
     * @author wuhen
     * @date 2020-04-09 11:25:29
     * @return id id
     */
    public Integer getId() {
        return id;
    }

    /**
     * id
     * @author wuhen
     * @date 2020-04-09 11:25:29
     * @param id id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 状态
     * @author wuhen
     * @date 2020-04-09 11:25:29
     * @return stauts 状态
     */
    public String getStauts() {
        return stauts;
    }

    /**
     * 状态
     * @author wuhen
     * @date 2020-04-09 11:25:29
     * @param stauts 状态
     */
    public void setStauts(String stauts) {
        this.stauts = stauts == null ? null : stauts.trim();
    }

    /**
     * 创建者
     * @author wuhen
     * @date 2020-04-09 11:25:29
     * @return create_by 创建者
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * 创建者
     * @author wuhen
     * @date 2020-04-09 11:25:29
     * @param createBy 创建者
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    /**
     * 创建时间
     * @author wuhen
     * @date 2020-04-09 11:25:29
     * @return create_dt 创建时间
     */
    public String getCreateDt() {
        return createDt;
    }

    /**
     * 创建时间
     * @author wuhen
     * @date 2020-04-09 11:25:29
     * @param createDt 创建时间
     */
    public void setCreateDt(String createDt) {
        this.createDt = createDt == null ? null : createDt.trim();
    }

    /**
     *
     * @mbg.generated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", stauts=").append(stauts);
        sb.append(", createBy=").append(createBy);
        sb.append(", createDt=").append(createDt);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}