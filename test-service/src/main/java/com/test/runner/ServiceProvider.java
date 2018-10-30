package com.test.runner;

import com.alibaba.dubbo.container.Main;
import com.test.utils.Base64Util;

/**
 * Created with IntelliJ IDEA
 * <p>
 * Description：启动类
 * Auth：wuhengzhen
 * Date：2018-09-27
 * Time：14:54
 */
public class ServiceProvider {
    /**
     * description :启动MAIN方法
     * author : wuhengzhen
     * date : 2018-9-27 14:55
     */
    public static void main(String[] args) throws Exception {
        pritNoBug();
        Main.main(args);
    }

    /**
     * print No Bug
     *
     * @throws Exception
     */
    private static void pritNoBug() throws Exception {
        String base64fozu = "Li4uLi4uLi4uLi4uLi4uLi4uLuaIkeS9m+aFiOaCsi4uLi4uLi4uLi4uLi4uLi4uLi4KICAgICAg\n" +
                            "ICAgICAgICAgICAgIF9vb09vb18KICAgICAgICAgICAgICAgICAgbzg4ODg4ODhvCiAgICAgICAg\n" +
                            "ICAgICAgICAgIDg4IiAuICI4OAogICAgICAgICAgICAgICAgICAofCAtXy0gfCkKICAgICAgICAg\n" +
                            "ICAgICAgICAgT1wgID0gIC9PCiAgICAgICAgICAgICAgIF9fX18vYC0tLSdcX19fXwogICAgICAg\n" +
                            "ICAgICAgLicgIFxcfCAgICAgfC8vICBgLgogICAgICAgICAgICAvICBcXHx8fCAgOiAgfHx8Ly8g\n" +
                            "IFwKICAgICAgICAgICAvICBffHx8fHwgLTotIHx8fHx8LSAgXAogICAgICAgICAgIHwgICB8IFxc\n" +
                            "XCAgLSAgLy8vIHwgICB8CiAgICAgICAgICAgfCBcX3wgICcnXC0tLS8nJyAgfCAgIHwKICAgICAg\n" +
                            "ICAgICBcICAuLVxfXyAgYC1gICBfX18vLS4gLwogICAgICAgICBfX19gLiAuJyAgLy0tLi0tXCAg\n" +
                            "YC4gLiBfXwogICAgICAuIiIgJzwgIGAuX19fXF88fD5fL19fXy4nICA+JyIiLgogICAgIHwgfCA6\n" +
                            "ICBgLSBcYC47YFwgXyAvYDsuYC8gLSBgIDogfCB8CiAgICAgXCAgXCBgLS4gICBcXyBfX1wgL19f\n" +
                            "IF8vICAgLi1gIC8gIC8KPT09PT09YC0uX19fX2AtLl9fX1xfX19fXy9fX18uLWBfX19fLi0nPT09\n" +
                            "PT09CiAgICAgICAgICAgICAgICAgICBgPS0tLT0nCl5eXl5eXl5eXl5eXl5eXl5eXl5eXl5eXl5e\n" +
                            "Xl5eXl5eXl5eXl5eXl5eXl5eXgogICAgICAgICAgICDkvZvnpZbkv53kvZEgICAgICAg5rC45peg\n" +
                            "QlVH";
        System.out.println(new String(Base64Util.decryptBASE64(base64fozu)));
    }
}
