package com.zhen.common.multi.execute;

import java.util.Map;

/**
 * 任务处理接口
 * 具体业务逻辑可实现该接口
 * T 返回值类型
 * E 传入值类型
 * ITask<BR>
 * 创建人:wuhengzhen <BR>
 * 时间：2020年12月18日17:47:22 <BR>
 *
 * @version 2.0
 */
public interface ITask<T, E> {


    /**
     * 任务执行方法接口
     *
     * @param e      传入对象
     * @param params 其他辅助参数
     * @return T <BR> 返回值类型
     * @author wuhengzhen
     * @date 2020/12/18 17:48
     */
    T execute(E e, Map<String, Object> params);
}