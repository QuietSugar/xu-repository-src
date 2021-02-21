package name.xu.log.html.service;


import lombok.extern.slf4j.Slf4j;
import name.xu.log.html.logback.LoggerQueue;
import org.apache.commons.lang3.StringUtils;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author xu
 */
@Service
@Slf4j
public class LogMessageService {
    /**
     * 连接标志
     * 当至少有一个订阅之后开启
     */
    public static Boolean FLAG = false;

    @Resource
    @NotNull
    private SimpMessagingTemplate messagingTemplate;

    /**
     * 推送日志到/topic/log
     */
    @PostConstruct
    public void pushLog() {
        log.info("push log start ......");
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Runnable runnable = () -> {
            log.info("push log thread start ......");
            while (true) {
                if (FLAG) {
                    String log = LoggerQueue.poll();
                    if (StringUtils.isNotBlank(log)) {
                        messagingTemplate.convertAndSend("/topic/log", log);
                    }
                }
            }
        };
        executorService.submit(runnable);
    }
}
