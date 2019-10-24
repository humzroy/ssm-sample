# ssm框架脚手架，基于dubbo、zookeeper
---
- [一、后端开发规范](#后端开发规范)
- [二、Apollo配置中心](#apollo配置中心)
- [三、lombok介绍](#lombok介绍)
# 后端开发规范

## 1.注释
**注意：原代码和注释不论是不是自己写的，修改代码后记得修改对应的注释。
方法注释必须要有方法作用描述和参数描述。**
### Controller注释示例
```
/**
 * 验证该笔业务是否需要走绑卡流程
 *
 * @param fund     资金方
 * @param bankCode 银行编号
 * @return
 */
@RequestMapping("/checkIfTieCard")
@ResponseBody
public JsonResponse checkIfTieCard(@Param("fund") String fund, @Param("bankCode") String bankCode) {

    if (StringUtils.isEmpty(fund)) {
        return JsonResponse.fail(FUND_NOT_FOUND);
    }
    if (StringUtils.isEmpty(bankCode)){
        return JsonResponse.fail(BANK_CDE_NOT_FOUND);
    }

    try {
        return preTieCardService.checkIfTieCard(fund, bankCode);
    } catch (Exception e) {
        log.error("绑卡校验异常", e);
        return JsonResponse.fail(SYSTEM_ERROR);
    }
}
```
### Mapper、Service接口注释示例

```
/**
 * 验证该笔业务是否需要走绑卡流程
 * @param fund 资金方
 * @param bankCode 银行编号
 * @return
 */
JsonResponse checkIfTieCard(String fund, String bankCode);
```
### 常量类注释示例

```
/**
 * 配置参数常量类
 * @author wuhengzhen
 * @date 2018/4/11
 */
public class BaseConstant {

    /**
     * 未激活
     */
    public static final String USER_STATUS_UNACTIVATED = "0";
    /**
     * 已激活
     */
    public static final String USER_STATUS_ACTIVATED = "1";
    /**
     * 锁定
     */
    public static final String USER_STATUS_LOCKED = "2";
    /**
     * 作废
     */
    public static final String USER_STATUS_DELETED = "3";

}
```
### 实体注释示例：

```
/**
 * 用户信息表
 *
 * @author wuhengzhen
 * @date 2018-02-28
 */
public class Usr implements Serializable{
    private static final long serialVersionUID = 6926597860236556332L;

    /**
     * 用户编号
     */
    private String usrCde;

    /**
     * 用户姓名
     */
    private String usrName;

    /**
     * 证件号码
     */
    private String idNo;
}
```

### 复杂业务逻辑注释示例：
```
/**
 * XX Api返回值示例
 * {"msg":
 *      {"71":{
 *          "data":{},
 *          "resCode":"9999",
 *          "resultflag":"",
 *          "resMsg":"查询失败"},
 *      "remark":["71:银行卡四要素一致性（即时）"]},
 * "code":"000000",
 * "success":true}
 */
if (!"000000".equals(object.get("code"))) {
    logger.info("XX Api验证失败code:" + object.get("code"));
    resultMap.put("result", "2");
    return resultMap;
}

Map msgMap = (Map) object.get("msg");
Map typeMap = (Map) msgMap.get(type);

if (!"0000".equals(typeMap.get("resCode"))) {
    logger.info("XX Api验证失败resCode:" + typeMap.get("resCode"));
    resultMap.put("result", "2");
    return resultMap;
}
Map dataMap = (Map) typeMap.get("data");
idenMsg = dataMap.get("idenMsg").toString();
```

## 2.日志
关键的业务逻辑一定要加上注释，方便定位测试和生产问题。
日志打印一定要加上关键业务信息。
错误范例：
```
log.info("登录成功!");
```
正确范例：
```
log.info("用户id=" + id + ",登录成功!");
```

## 3.复制代码
如果开发过程中需要搬其他项目代码，除工具类以外，注意只需要取和自己业务相关的代码，避免出现无用的代码。

## 4.sosoapi接口维护
响应信息示例：
```
{
  "success": true,
  "messageCode": "0",
  "message": "string, 信息描述",
  "data": {
    "access_token": "string, token值",
    "firstLogin": "string, 是否首次登录(0:首次登录, 1:非首次登录)",
    "userData": {
      "mobile": "string, 手机号",
      "email": "string, 邮箱",
      "userType": "string, 用户类型",
      "name": "string, 姓名",
      "nickName": "string, 昵称",
      "gender": "string, 用户性别",
      "identType": "string, 证件类型",
      "identNo": "string, 证件号码",
      "headFile": "string, 头像路径",
      "headThumbnail": "string, 头像缩略图路径",
      "auLevel": "string, 是否实名认证(0:未认证, 1:已认证)"
    }
  }
}
```

## 5.代码格式（建议）
**建议使用IDEA快捷键CTRL+ALT+L规范代码格式。
+、=、==、>、<等表达式两边保留空格。**

未格式化代码示例：
```
public class Test {
        //贷款初审
        public static final String PREAPPV_PASS="PREAPPV_PASS";

        //贷款初审
        public static final String PREAPPV_COUNT_THRESHOLD="PREAPPV_COUNT_THRESHOLD";

    //贷款初审
    public static final String PREAPPV_CHARGE_FLAG="PREAPPV_CHARGE_FLAG";

    public static final String PREAPPV_CHARGE_FLAG_ON="1";

}
```
使用快捷键格式化后代码示例：
```
public class Test {
    //贷款初审
    public static final String PREAPPV_PASS = "PREAPPV_PASS";

    //贷款初审
    public static final String PREAPPV_COUNT_THRESHOLD = "PREAPPV_COUNT_THRESHOLD";

    //贷款初审
    public static final String PREAPPV_CHARGE_FLAG = "PREAPPV_CHARGE_FLAG";

    public static final String PREAPPV_CHARGE_FLAG_ON = "1";

}
```

# apollo配置中心
官方文档：
>https://github.com/ctripcorp/apollo/wiki

apollo配置中心地址：
>http://ip:port

apollo配置中心新增用户(需要超级管理员权限)：
>http://ip:port/user-manage.html


## Apollo启动参数
zhen service和web项目启动参数在之前的基础上还需要增加以下apollo参数：
>-Denv=DEV -Dapp.id=zhen-sit -Dnamespace=application -Dapollo.meta=http://ip:port

完整项目启动参数示例,不再需要CONF_HOME：
service:
>-Denv=DEV -Dapp.id=zhen-sit -Dnamespace=application -Dapollo.meta=http://ip:port

web
>-Dfile.encoding=UTF-8 -Denv=DEV -Dapp.id=zhen-sit -Dnamespace=application -Dapollo.meta=http://ip:port

## Apollo调用方式
工具类：ApolloUtil  
调用方式：String jettyPort = ApolloUtil.getConfig("jetty.port");  
**调用之前需要先在配置中心进行配置。**

测试类：BaseTest
>从配置中心获取配置成功后，会将配置文件缓存到 **"C:\opt\data\zhen\config-cache\\"** 下

# lombok介绍

## 问题背景
目前项目中实体类都没有重写toString方法，这样在打印实体的时候只能打印出来一个对象的地址信息,比如：
>com.zhen.base.domain.TokenUserAccount@46c1e988

这样的信息对我们来说没有意义，所以需要重写toString方法得到如下结果：
>TokenUserAccount(token=chenli1527640011072, userNo=chenli, userName=陈力, mobile=null, firstLogin=null)

lombok可以使用简单的注解帮助我们完成toString方法的生成，并且新增或修改属性时不需要对toString方法进行维护。


## lombok定义
>lombok是一款可以精减java代码、提升开发人员生产效率的辅助工具，利用注解在编译期自动生成setter/getter/toString()/constructor之类的代码。

## pom文件中引入lombok
```
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.18.2</version>
    <scope>provided</scope>
</dependency>
```

## idea安装lombok插件
使用lombok之前需要先安装插件，否则相关注解会标红并无法自动提示lombok代码。
### 线上安装
打开Settings页面(Ctrl+Alt+S)-->plugins,搜索lombok-->Search in repositories-->选择Lombok Plugin进行安装并重启idea。
### 本地安装
lombok插件下载路径：  
https://github.com/mplushnikov/lombok-intellij-plugin/releases  
打开Settings页面(Ctrl+Alt+S)-->plugins，Install plugin from disk... 选择下载的zip包安装，重启idea即可。

### Annotation Processors设置

打开Settings页面(Ctrl+Alt+S)-->Compiler-->Annotation Processors-->勾选 Enable Annotation Processors。

没有此设置可能导致报错 Lombok Requires Annotation Processing。

## 使用lombok注解重写toString

```
@ToString
public class TokenUserAccount implements Serializable{
```

## 原理
编译器在编译java文件时，对于lombok的ToString注解，最终会在class文件中新增以下代码，实现toString的重写：
```
@Override
public String toString() {
    return "TokenUserAccount{" +
            "token='" + token + '\'' +
            ", userNo='" + userNo + '\'' +
            ", userName='" + userName + '\'' +
            ", mobile='" + mobile + '\'' +
            ", firstLogin='" + firstLogin + '\'' +
            '}';
}
```
