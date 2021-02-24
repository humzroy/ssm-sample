package com.zhen.excel.easyexcel;

import com.alibaba.excel.read.context.AnalysisContext;
import com.alibaba.excel.read.event.AnalysisEventListener;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 * <p>
 * Description：Excel监听类
 * Author：wuhengzhen
 * Date：2018-09-26
 * Time：14:24
 */
@Slf4j
public class ExcelListener extends AnalysisEventListener {

    private volatile int totalCount = 0;

    /**
     * 自定义用于暂时存储data
     * 可以通过实例获取该值
     */
    private List<Object> datas = new ArrayList<>();

    /**
     * 通过 AnalysisContext 对象还可以获取当前 sheet，当前行等数据
     */
    @Override
    public void invoke(Object object, AnalysisContext context) {
        totalCount++;
        //数据存储到list，供批量处理，或后续自己业务逻辑处理。
        datas.add(object);
        //根据自己业务做处理
        dealData(context);
    }

    private void doSomething(Object object) {
    }

    /**
     * 加上存储数据库
     */
    private void dealData(AnalysisContext context) {
        log.info("当前正在处理第[{}]行数据，本次处理第[{}]条数据,总共有：{}条数据", context.getCurrentRowNum(), datas.size(), totalCount);
        // 实际处理逻辑
        System.out.println(context.getCurrentRowAnalysisResult());
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        /*
            datas.clear();
            解析结束销毁不用的资源
         */
    }

    public List<Object> getDatas() {
        return datas;
    }

    public void setDatas(List<Object> datas) {
        this.datas = datas;
    }
}
