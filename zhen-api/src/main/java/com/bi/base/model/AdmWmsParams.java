package com.bi.base.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class AdmWmsParams implements Serializable {
    /**
     * null PARAM_TYPE
     */
    private String paramType;

    /**
     * null PARAM_CODE
     */
    private String paramCode;

    /**
     * null DATA_LOCAL
     */
    private String dataLocal;

    /**
     * null PARAM_NAME
     */
    private String paramName;

    /**
     * null PARAM_ENAME
     */
    private String paramEname;

    /**
     * null MEMO
     */
    private String memo;

    /**
     * null CACHE_YN
     */
    private String cacheYn;

    /**
     * null PARENT_PARAM_CODE
     */
    private String parentParamCode;

    /**
     * null SHOW_ORDER
     */
    private int showOrder;

    /**
     * null CREATE_DT
     */
    private Date createDt;

    /**
     * null MODIFY_DT
     */
    private Date modifyDt;

    /**
     * null CREATE_BY
     */
    private String createBy;

    /**
     * null MODIFY_BY
     */
    private String modifyBy;

    /**
     * ADM_WMS_PARAMS
     */
    private static final long serialVersionUID = 1L;

    /**
     * null
     * @author wuhen
     * @date 2019-10-25 14:25:33
     * @return PARAM_TYPE null
     */
    public String getParamType() {
        return paramType;
    }

    /**
     * null
     * @author wuhen
     * @date 2019-10-25 14:25:33
     * @param paramType null
     */
    public void setParamType(String paramType) {
        this.paramType = paramType == null ? null : paramType.trim();
    }

    /**
     * null
     * @author wuhen
     * @date 2019-10-25 14:25:33
     * @return PARAM_CODE null
     */
    public String getParamCode() {
        return paramCode;
    }

    /**
     * null
     * @author wuhen
     * @date 2019-10-25 14:25:33
     * @param paramCode null
     */
    public void setParamCode(String paramCode) {
        this.paramCode = paramCode == null ? null : paramCode.trim();
    }

    /**
     * null
     * @author wuhen
     * @date 2019-10-25 14:25:33
     * @return DATA_LOCAL null
     */
    public String getDataLocal() {
        return dataLocal;
    }

    /**
     * null
     * @author wuhen
     * @date 2019-10-25 14:25:33
     * @param dataLocal null
     */
    public void setDataLocal(String dataLocal) {
        this.dataLocal = dataLocal == null ? null : dataLocal.trim();
    }

    /**
     * null
     * @author wuhen
     * @date 2019-10-25 14:25:33
     * @return PARAM_NAME null
     */
    public String getParamName() {
        return paramName;
    }

    /**
     * null
     * @author wuhen
     * @date 2019-10-25 14:25:33
     * @param paramName null
     */
    public void setParamName(String paramName) {
        this.paramName = paramName == null ? null : paramName.trim();
    }

    /**
     * null
     * @author wuhen
     * @date 2019-10-25 14:25:33
     * @return PARAM_ENAME null
     */
    public String getParamEname() {
        return paramEname;
    }

    /**
     * null
     * @author wuhen
     * @date 2019-10-25 14:25:33
     * @param paramEname null
     */
    public void setParamEname(String paramEname) {
        this.paramEname = paramEname == null ? null : paramEname.trim();
    }

    /**
     * null
     * @author wuhen
     * @date 2019-10-25 14:25:33
     * @return MEMO null
     */
    public String getMemo() {
        return memo;
    }

    /**
     * null
     * @author wuhen
     * @date 2019-10-25 14:25:33
     * @param memo null
     */
    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }

    /**
     * null
     * @author wuhen
     * @date 2019-10-25 14:25:33
     * @return CACHE_YN null
     */
    public String getCacheYn() {
        return cacheYn;
    }

    /**
     * null
     * @author wuhen
     * @date 2019-10-25 14:25:33
     * @param cacheYn null
     */
    public void setCacheYn(String cacheYn) {
        this.cacheYn = cacheYn == null ? null : cacheYn.trim();
    }

    /**
     * null
     * @author wuhen
     * @date 2019-10-25 14:25:33
     * @return PARENT_PARAM_CODE null
     */
    public String getParentParamCode() {
        return parentParamCode;
    }

    /**
     * null
     * @author wuhen
     * @date 2019-10-25 14:25:33
     * @param parentParamCode null
     */
    public void setParentParamCode(String parentParamCode) {
        this.parentParamCode = parentParamCode == null ? null : parentParamCode.trim();
    }

    /**
     * null
     * @author wuhen
     * @date 2019-10-25 14:25:33
     * @return SHOW_ORDER null
     */
    public int getShowOrder() {
        return showOrder;
    }

    /**
     * null
     * @author wuhen
     * @date 2019-10-25 14:25:33
     * @param showOrder null
     */
    public void setShowOrder(int showOrder) {
        this.showOrder = showOrder;
    }

    /**
     * null
     * @author wuhen
     * @date 2019-10-25 14:25:33
     * @return CREATE_DT null
     */
    public Date getCreateDt() {
        return createDt;
    }

    /**
     * null
     * @author wuhen
     * @date 2019-10-25 14:25:33
     * @param createDt null
     */
    public void setCreateDt(Date createDt) {
        this.createDt = createDt;
    }

    /**
     * null
     * @author wuhen
     * @date 2019-10-25 14:25:33
     * @return MODIFY_DT null
     */
    public Date getModifyDt() {
        return modifyDt;
    }

    /**
     * null
     * @author wuhen
     * @date 2019-10-25 14:25:33
     * @param modifyDt null
     */
    public void setModifyDt(Date modifyDt) {
        this.modifyDt = modifyDt;
    }

    /**
     * null
     * @author wuhen
     * @date 2019-10-25 14:25:33
     * @return CREATE_BY null
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * null
     * @author wuhen
     * @date 2019-10-25 14:25:33
     * @param createBy null
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    /**
     * null
     * @author wuhen
     * @date 2019-10-25 14:25:33
     * @return MODIFY_BY null
     */
    public String getModifyBy() {
        return modifyBy;
    }

    /**
     * null
     * @author wuhen
     * @date 2019-10-25 14:25:33
     * @param modifyBy null
     */
    public void setModifyBy(String modifyBy) {
        this.modifyBy = modifyBy == null ? null : modifyBy.trim();
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
        sb.append(", paramType=").append(paramType);
        sb.append(", paramCode=").append(paramCode);
        sb.append(", dataLocal=").append(dataLocal);
        sb.append(", paramName=").append(paramName);
        sb.append(", paramEname=").append(paramEname);
        sb.append(", memo=").append(memo);
        sb.append(", cacheYn=").append(cacheYn);
        sb.append(", parentParamCode=").append(parentParamCode);
        sb.append(", showOrder=").append(showOrder);
        sb.append(", createDt=").append(createDt);
        sb.append(", modifyDt=").append(modifyDt);
        sb.append(", createBy=").append(createBy);
        sb.append(", modifyBy=").append(modifyBy);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}