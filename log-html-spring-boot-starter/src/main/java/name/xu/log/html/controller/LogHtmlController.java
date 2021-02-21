package name.xu.log.html.controller;

import lombok.extern.slf4j.Slf4j;
import name.xu.log.html.service.LogMessageService;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

/**
 * @author Created by Xu
 */
@Controller
@Slf4j
public class LogHtmlController {

    /**
     * 订阅
     * 日志
     */
    @SubscribeMapping("/topic/log")
    public String subscribeLog() {
        LogMessageService.FLAG = true;
        return "已订阅 : [ /topic/log ]";
    }
}
