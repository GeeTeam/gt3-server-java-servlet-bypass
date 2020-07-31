# **gt3-server-java-servlet-bypass**

# **ByPass方案极验服务端新增接口**

## **API文档概述**
条目|监控接口
----|----
说明|监控极验云服务是否正常
域名|bypass.geetest.com
接口路径名|/v1/bypass_status.php
完整地址示例|http://bypass.geetest.com/v1/bypass_status.php
请求方式|GET、POST皆可
请求头|(POST)Content-Type: application/x-www-form-urlencoded 
请求参数格式| GET: 键值对, urlEncode<br> POST: 表单, urlEncode
响应头|Content-Type: application/json;charset=UTF-8
响应结果类型|json格式

## **请求参数**
名称|类型|必填|说明
----|----|----|----
gt|string|Y|向极验申请的账号id

> 请求参数示例

```
gt=c9c4facd1a6feeb80802222cbb74ca8e
```

## **响应参数**
名称|类型|必填|说明
----|----|----|----
status|string|Y|success: 极验云服务正常<br>fail: 极验云服务异常

> 响应结果示例

```
{
    "status": "success"
}
```

# **项目说明**
## 流程概述
- 项目部署启动，监听器开始执行轮询定时器任务，每隔一段时间(可配置)向极验监控接口(http://bypass.geetest.com/v1/bypass_status.php)发起请求，并将返回的标识字段status(success/fail)存入redis数据库中(仅demo示例，可自行设计存储架构)。

- 当客户端向服务端发起register验证初始化和validate二次验证请求时， 先从redis中取出极验状态的标识字段status。若是status是success则走正常流程，后续向极验云发起请求；若status是fail则走宕机模式，后续不论客户端还是服务端，都不会与极验云有任何交互。

## **示例部署环境**
条目|说明
----|----
操作系统|ubuntu 16.04.6 lts
jdk版本|openjdk version 1.8.0_252 64-bit
tomcat版本|apache-tomcat-8.5.55
maven版本|apache maven 3.6.3
redis数据库|3.0.6

## **部署流程**

### **下载demo**
请联系极验客户

### 修改项目配置，修改参数
> 修改项目配置

从[极验管理后台](https://auth.geetest.com/login/)获取公钥(id)和私钥(key), 获取redis数据库的相关信息。配置文件的相对路径如下(配置参数说明详见代码)：
```
/Users/liuquan/private/work/workspace/myself/gt3-server-java-servlet-bypass/src/main/resources/config.properties
```

> 修改请求参数（可选）

名称|说明
----|------
user_id|客户端用户的唯一标识，作用于提供进阶数据分析服务，可在register和validate接口传入，不传入也不影响验证服务的使用；若担心用户信息风险，可作预处理(如哈希处理)再提供到极验
client_type|客户端类型，web：电脑上的浏览器；h5：手机上的浏览器，包括移动应用内完全内置的web_view；native：通过原生sdk植入app应用的方式；unknown：未知
ip_address|客户端请求sdk服务器的ip地址

### 关键文件说明
名称|说明|相对路径
----|----|----
FirstRegisterServlet.java|处理验证初始化接口请求|src/main/java/com/geetest/demo/controller
SecondValidateServlet.java|处理二次验证接口请求|src/main/java/com/geetest/demo/controller
CheckGeetestStatusListener.java|监听器，随项目启动开始执行定时器任务，监控极验云状态|src/main/java/com/geetest/demo/listener/
CheckGeetestStatusTask.java|定时器任务，处理极验监控接口请求|src/main/java/com/geetest/demo/task/
GeetestLib.java|核心sdk，处理各种业务|src/main/java/com/geetest/sdk/
GeetestLibResult.java|核心sdk返回数据的包装对象|src/main/java/com/geetest/sdk/entity
EncryptionUtils.java、HttpClientUtils.java、PropertiesUtils.java、RedisPoolUtils.java|工具类，如编码算法、http请求连接池、redis连接池等|src/main/java/com/geetest/sdk/utils/
config.properties|项目配置，如向极验申请的id和key、redis数据库连接池配置、定时器任务轮询间隔时间等|src/main/resources/
web.xml|server配置，主要路由配置|src/main/webapp/WEB-INF/
index.html|demo示例首页|src/main/webapp/
gt.js|本地加载的js文件|src/main/webapp/
pom.xml|maven依赖管理配置文件|

### 运行demo
tomcat部署启动项目，在浏览器中访问`http://localhost:8080`即可看到demo界面。

## 发布日志

### tag：20200731
- 版本：jave-servlet:3.1.1


