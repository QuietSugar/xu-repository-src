package name.xu.log.html.logback;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.util.Date;

/**
 * @author Created by Xu
 */
@Component
@Slf4j
public class XuLogFilter extends Filter<ILoggingEvent> {

    @Override
    public FilterReply decide(ILoggingEvent event) {


        String loggerMessage = event.getFormattedMessage();
        LoggerQueue.push(loggerMessage);
        return FilterReply.ACCEPT;
    }

    /**
     * 将消息转化成对象
     */
    private LoggerMessage parseILoggingEvent(ILoggingEvent event) {
        StringBuilder exception = new StringBuilder();
        IThrowableProxy iThrowableProxy1 = event.getThrowableProxy();
        if (iThrowableProxy1 != null) {
            String className = iThrowableProxy1.getClassName();
            String message = iThrowableProxy1.getMessage();
            exception.append("className ");
            exception.append(className);
            exception.append("message ");
            exception.append(message);
            for (int i = 0; i < iThrowableProxy1.getStackTraceElementProxyArray().length; i++) {
                exception.append("StackTrace").append(i);
                String s = iThrowableProxy1.getStackTraceElementProxyArray()[i].toString();
                exception.append(s);
            }
        }
        return new LoggerMessage(
                event.getMessage()
                , DateFormat.getDateTimeInstance().format(new Date(event.getTimeStamp())),
                event.getThreadName(),
                event.getLoggerName(),
                event.getLevel().levelStr,
                exception.toString(),
                ""
        );
    }
}
