#### 子模块
- quickstart
> 参考的是 `org.apache.maven.archetypes:maven-archetype-quickstart`(删除了test模块)

##### 0.安装使用
```shell
# 下载本项目源码
git clone https://github.com/QuietSugararchetype-resources/blank.git
# 进入目录
cd blank
# 安装到本机，并且更新
mvn clean install 
# 或者 mvn -Dmaven.test.skip=true clean install
mvn archetype:update-local-catalog
# 去找一个空的目录运行，根据提示设置
mvn archetype:generate
```

IntelliJ IDEA 如何删除自定义的 Maven 骨架(Archetype)

windows 
找到以下配置文件 
C:\Users\${user}.IntelliJIdea${version}\system\Maven\Indices\UserArchetypes.xml 
~/.m2/repository/archetype-catalog.xml
删除里面的条目,或者删除文件
