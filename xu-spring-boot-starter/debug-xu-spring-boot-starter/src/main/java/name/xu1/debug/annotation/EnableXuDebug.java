package name.xu1.debug.annotation;

import name.xu1.debug.Config;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启用 XuDebug 的注解
 *
 * @author xu
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Import(Config.class)
public @interface EnableXuDebug {

}
