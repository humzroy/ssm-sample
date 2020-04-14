package com.zhen.base.dao.generator;

import com.zhen.base.domain.generator.WmsHundsunTradeResult;

public interface WmsHundsunTradeResultMapper {
    /**
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String tranCode);

    /**
     *
     * @mbg.generated
     */
    int insert(WmsHundsunTradeResult record);

    /**
     *
     * @mbg.generated
     */
    int insertSelective(WmsHundsunTradeResult record);

    /**
     *
     * @mbg.generated
     */
    WmsHundsunTradeResult selectByPrimaryKey(String tranCode);

    /**
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(WmsHundsunTradeResult record);

    /**
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(WmsHundsunTradeResult record);
}