#### xu-md-maven-plugin

maven插件: 提取 Markdown 文件

提取 md 文件

文件生成的目录是 target/md-doc

- 调用方式一
> 放入 pom文件中
```xml
   <build>
        <plugins>
            <!--自定义插件-->
            <plugin>
                <groupId>name.xu</groupId>
                <artifactId>xu-md-maven-plugin</artifactId>
                <version>0.0.1</version>
                <executions>
                    <execution>
                        <!--  指定目标方法-->
                        <goals>
                            <goal>md-doc</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
```
- 调用方式二
> 命令行执行
> 
```shell
 mvn name.xu:xu-md-maven-plugin:0.0.1:md-doc
```