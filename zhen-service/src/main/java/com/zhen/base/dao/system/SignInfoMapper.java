package com.zhen.base.dao.system;

import com.zhen.base.domain.system.SignInfo;

public interface SignInfoMapper {
    /**
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbg.generated
     */
    int insert(SignInfo record);

    /**
     *
     * @mbg.generated
     */
    int insertSelective(SignInfo record);

    /**
     *
     * @mbg.generated
     */
    SignInfo selectByPrimaryKey(Integer id);

    /**
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(SignInfo record);

    /**
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(SignInfo record);
}