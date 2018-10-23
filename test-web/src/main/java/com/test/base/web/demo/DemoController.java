package com.test.base.web.demo;

import com.test.base.domain.excel.ExportInfo;
import com.test.base.domain.excel.ImportInfo;
import com.test.base.service.demo.IDemoService;
import com.test.excel.easyexcel.EasyExcelUtil;
import com.test.utils.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wuhengzhen
 * @Description：
 * @date：2018-09-17 17:24
 */
@RestController
@RequestMapping("/demo")
public class DemoController {
    private static final Logger logger = LoggerFactory.getLogger(DemoController.class);

    @Autowired
    @Qualifier("demoService")
    private IDemoService demoService;

    /**
     * 调用服务端 dubbo 测试 helloWorld 方法
     *
     * @param request
     * @return
     */
    @RequestMapping("/test")
    public Object test(HttpServletRequest request) {
        String name = request.getParameter("name");
        logger.info("请求的参数name = " + name);
        String msg = demoService.sayHello(name);
        ResponseUtil responseUtil = ResponseUtil.createResponseUtil();
        responseUtil.putValueToData("msg", msg);
        return responseUtil;
    }

    /**
     * 读取 Excel（允许多个 sheet）
     */
    @RequestMapping(value = "readExcel", method = RequestMethod.POST)
    public Object readExcel(MultipartFile excel) {
        return EasyExcelUtil.readExcel(excel, new ImportInfo());
    }

    /**
     * 导出 Excel（一个 sheet）
     */
    @RequestMapping(value = "writeExcel", method = RequestMethod.GET)
    public void writeExcel(HttpServletResponse response) throws IOException {
        List<ExportInfo> list = getList();
        String fileName = "一个 Excel 文件";
        String sheetName = "第一个 sheet";

        EasyExcelUtil.writeExcel(response, list, fileName, sheetName, new ExportInfo());
    }

    /**
     * 导出 Excel（多个 sheet）
     */
    @RequestMapping(value = "writeExcelWithSheets", method = RequestMethod.GET)
    public void writeExcelWithSheets(HttpServletResponse response) throws IOException {
        List<ExportInfo> list = getList();
        String fileName = "一个 Excel 文件";
        String sheetName1 = "第一个 sheet";
        String sheetName2 = "第二个 sheet";
        String sheetName3 = "第三个 sheet";

        EasyExcelUtil.writeExcelWithSheets(response, list, fileName, sheetName1, new ExportInfo())
                .write(list, sheetName2, new ExportInfo())
                .write(list, sheetName3, new ExportInfo())
                .finish();
    }

    /**
     * description : 获得list
     * author : wuhengzhen
     * date : 2018-9-26 14:34
     */
    private List<ExportInfo> getList() {
        List<ExportInfo> list = new ArrayList<>();
        ExportInfo model1 = new ExportInfo();
        model1.setName("wuhengzhen");
        model1.setAge("24");
        model1.setAddress("123456789");
        model1.setEmail("wuhengzhen.qd@gmail.com");
        list.add(model1);
        ExportInfo model2 = new ExportInfo();
        model2.setName("harry");
        model2.setAge("20");
        model2.setAddress("198752233");
        model2.setEmail("harry@gmail.com");
        list.add(model2);
        return list;
    }
}
