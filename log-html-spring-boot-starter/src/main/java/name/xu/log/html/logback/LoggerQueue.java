package name.xu.log.html.logback;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author Created by Xu
 */


public class LoggerQueue {
    /**
     * 阻塞队列
     */
    private final static BlockingQueue<String> BLOCKING_QUEUE = new LinkedBlockingQueue<>(10000);

    private LoggerQueue() {
    }


    /**
     * 消息入队
     */
    public static boolean push(String log) {
        //队列满了就抛出异常，不阻塞
        return BLOCKING_QUEUE.add(log);
    }

    /**
     * 消息出队
     */
    public static String poll() {
        String result = null;
        try {
            result = BLOCKING_QUEUE.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }
}
