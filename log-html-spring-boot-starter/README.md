日志扩展

通过 socket 将日志信息推送到前端页面上

一、安装、配置 1、编译安装(安装至本地mvn仓库)
mvn install

2、项目的pom.xml加入如下内容(SpringBoot项目)

```xml

<dependency>
    <groupId>name.xu</groupId>
    <artifactId>log-html-spring-boot-starter</artifactId>
    <version>0.1.0</version>
</dependency>

```

配置过滤器

```xml
 <appender name="stdout" class="ch.qos.logback.core.ConsoleAppender">       
        <filter class="name.xu.log.html.logback.XuLogFilter"/>
    </appender>
```
