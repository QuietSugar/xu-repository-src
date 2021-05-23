日志 AOP

- 安装、配置
1、编译安装(安装至本地mvn仓库)
mvn install

- 项目的pom.xml加入如下内容(SpringBoot项目)
<dependency>
    <groupId>name.xu</groupId>
    <artifactId>xu-log-spring-boot-starter</artifactId>
    <version>0.0.1</version>
</dependency>


日志组件注解适用于所有方法 
- @ApiAccessLog 注解 调用日志
   方法被调用时会生成调用日志，方便开发调测
   日志级别为DEBUG，需要在配置文件配置，参考配置如下

3、ApiAccessLog 注解 调用日志
方法被调用时会生成调用日志，方便开发调测
日志级别为DEBUG，需要在配置文件配置，参考配置如下

'''
logging:
file: "/data/logs/log-xwe-sbs.log"
level:
root: INFO
name.xu.log.extension.aop: DEBUG
'''

