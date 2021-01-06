package com.zhen.common.multi.execute.impl;

import com.zhen.common.master.ResultBean;
import com.zhen.common.multi.execute.ITask;
import com.zhen.common.multi.execute.MultiThreadUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wuhengzhen
 * @date 2020/12/18 17:51
 **/

public class TestTask implements ITask<ResultBean<String>, Integer> {
    /**
     * 任务执行方法接口
     *
     * @param e      传入对象
     * @param params 其他辅助参数
     * @return T <BR> 返回值类型
     * @author wuhengzhen
     * @date 2020/12/18 17:48
     */
    @Override
    public ResultBean<String> execute(Integer e, Map<String, Object> params) {
        /*
         * 具体业务逻辑：将list中的元素加上辅助参数中的数据返回
         */
        int addNum = Integer.parseInt(String.valueOf(params.get("addNum")));
        e = e + addNum;
        ResultBean<String> resultBean = ResultBean.newInstance();
        resultBean.setData(e.toString());
        return resultBean;
    }


    public static void main(String[] args) {
        // 需要多线程处理的大量数据list
        List<Integer> data = new ArrayList<>(10000);
        for (int i = 0; i < 10000; i++) {
            data.add(i + 1);
        }

        // 创建多线程处理任务
        MultiThreadUtils<Integer> threadUtils = MultiThreadUtils.newInstance(5);
        ITask<ResultBean<String>, Integer> task = new TestTask();
        // 辅助参数  加数
        Map<String, Object> params = new HashMap<>();
        params.put("addNum", 4);
        // 执行多线程处理，并返回处理结果
        ResultBean<List<ResultBean<String>>> resultBean = threadUtils.execute(data, params, task);
        System.out.println(resultBean.json());
    }


}