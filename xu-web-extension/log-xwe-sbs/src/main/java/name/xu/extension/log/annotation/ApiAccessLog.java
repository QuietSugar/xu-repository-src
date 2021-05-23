package name.xu.extension.log.annotation;

import java.lang.annotation.*;

/**
 * 打印日志注解
 *
 * @author xu
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiAccessLog {
}
