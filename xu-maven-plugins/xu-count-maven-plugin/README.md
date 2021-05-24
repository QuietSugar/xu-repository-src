#### 使用教程

- 调用方式一
> 放入 pom文件中
```xml
   <build>
        <plugins>
            <!--自定义插件-->
            <plugin>
                <groupId>name.xu</groupId>
                <artifactId>xu-count-maven-plugin</artifactId>
                <version>0.0.1</version>
                <configuration>
                    <!--向插件传递参数-->
                    <currentBaseDir>${basedir}\src\main\java</currentBaseDir>
                    <suffix>.java</suffix>
                    <msg>my-msg</msg>
                </configuration>
                <executions>
                    <execution>
                        <!-- 指定目标周期-->
                        <phase>package</phase>
                        <!--  指定目标方法-->
                        <goals>
                            <goal>fileCountTotal</goal>
                            <goal>projectInfo</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
```
- 调用方式二
> 命令行执行

```mvn name.xu:xu-count-maven-plugin:0.0.1:projectInfo -Dmsg=myMsg```
