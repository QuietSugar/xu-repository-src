package name.xu1.debug;

import name.xu1.debug.controller.XuDebugController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @author Created by Xu
 */
@Configuration
@EnableWebMvc
public class Config {
    @Bean
    public XuDebugController getXuDebugController() {
        return new XuDebugController();
    }
}