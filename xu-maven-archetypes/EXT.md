#### Maven archetype 创建自定义模板：

##### 0.快捷使用
```shell
# 下载本项目源码
git clone https://github.com/QuietSugar/blank.git
# 进入目录
cd blank
# 安装到本机，并且更新
mvn clean install 
# 或者 mvn -Dmaven.test.skip=true clean install
mvn archetype:update-local-catalog
# 去找一个空的目录运行，根据提示设置
mvn archetype:generate
# 或者一步到位（存疑）
mvn archetype:generate -DgroupId=com.maybe -DartifactId=test -Dversion=1.0-SNAPSHOT -DarchetypeArtifactId=blank-archetype -DinteractiveMode=false -DarchetypeVersion=1.0-SNAPSHOT -X -DarchetypeCatalog=local

# 使用官方提供的已存在的模板
# webapp
mvn archetype:generate -DgroupId=com.jsoft.test -DartifactId=testproject -Dversion=1.0-SNAPSHOT -DarchetypeArtifactId=maven-archetype-webapp -DinteractiveMode=false -DarchetypeVersion=RELEASE
# 普通APP
mvn archetype:generate -DgroupId=com.jsoft.test -DartifactId=testproject -Dversion=1.0-SNAPSHOT -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false -DarchetypeVersion=RELEASE
```

##### 1.初始化：
> 随便找一个maven项目(最好是想要创建的模板项目)，运行命令:
`mvn archetype:create-from-project`
这个命令是将一个已有的项目生成Archetype模板到 target/generated-sources/archetype。 这还是需要一些微调，要找准package name和变量，将变量转为符号，将目录结构收缩

##### 2.安装到本机maven仓库
> `mvn clean install` 或者 `mvn -Dmaven.test.skip=true clean install`
如果找不到，需要运行
`mvn archetype:update-local-catalog`
本地仓库archetype存放在：${user.home}/.m2/archetype-catalog.xml中）

##### 3.IDEA支持开启：
> 安装插件： maven archetype catalogs （可能非必须）
 `File -> setting -> Build,Execution,Deployment:`
将之前的 archetype-catalog.xml 文件添加进去。
之后创建Maven项目的时候就可以选择它了

#### 进阶
##### 0.自定义文件
重要文件和目录
> 资源文件夹：该文件夹下面的所有文件都会当做模板，放到生成的项目中
`src\main\resources\archetype-resources`
配置文件：该文件过滤筛选了上面的过程；
`src\main\resources\META-INF\maven\archetype-metadata.xml`
eg:在`archetype-resources`文件夹下面加入一个·`.gitignore`文件
然后在archetype-metadata.xml中加入以下内容：
```xml
<fileSets>
    <fileSet encoding="UTF-8" filtered="true">
         <directory></directory>
         <includes>
              <include>.gitignore</include>
         </includes>
    </fileSet>
</fileSets>
```

##### 0.属性替换
> 能够在上面的.gitignore增加${pro-to-replace}内容
在archetype-metadata.xml中加入内容：
```xml
<requiredProperties>
    <requiredProperty key="pro-to-replace"/>
</requiredProperties>
```
这个占位符在实际使用的时候进行替换。archetype-metadata.xml中filtered="true"必须配置
也就是说，在初始化项目的时候，在生成的向导里会提示我们输入pro-to-replace，在生成的命令行中用-Dpro-to-replace=XXX进行设置
也能够像这样设置默认值：
```xml
<requiredProperty key="pro-to-replace">
         <defaultValue>pro-to-replace</defaultValue>
</requiredProperty>
```
当然假设这样。就不会提示你输入属性值，假设不想使用属性值。可以通过-Dpro-to-replace=XXX进行更改
之后运行创建的时候设置属性：`mvn archetype:generate -DarchetypeCatalog=local -Dpro-to-replace=pro_value`
生成project的.gitignore中的${pro-to-replace}就会被替换为pro_value

_注意_：这里的属性名称不能包括点.
也就是说：pro.to.replace是不符合规范的


[参考1](https://blog.csdn.net/a5518007/article/details/62885432)
[参考2](https://www.cnblogs.com/bhlsheji/p/4639191.html)
