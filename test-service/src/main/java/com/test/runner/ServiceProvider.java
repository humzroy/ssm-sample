/**
 * 软件著作权：长安新生（深圳）金融投资有限公司
 * 
 * 系统名称：马达贷
 * 
 */


package com.test.runner;
import java.io.IOException;

/**
*
*  启动spring容器
* @author 郑翔
*/

public class ServiceProvider {
	/**
	 * 启动类
	 * @param args
	 */
	 public static void main(String[] args) throws IOException {  
	        //ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(  
	      //          new String[] { "spring-provider.xml" });  
	       // context.start();  
	  
	     //   System.in.read(); 
		  com.alibaba.dubbo.container.Main.main(args);
	    }  
}
