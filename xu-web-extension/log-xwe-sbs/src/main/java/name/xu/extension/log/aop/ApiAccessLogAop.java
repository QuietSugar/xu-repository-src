package name.xu.extension.log.aop;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @author xu
 */
@Aspect
@Component
@Slf4j
public class ApiAccessLogAop {
    ThreadLocal<Long> timestamps = new ThreadLocal<>();
    @Resource
    Gson gson;
    @Pointcut("@annotation(name.xu.extension.log.annotation.ApiAccessLog)")
    private void accessLog() {
        //Signature
    }

    @Before("accessLog()")
    public void before(JoinPoint joinPoint) {
        if (log.isDebugEnabled()) {
            timestamps.set(System.currentTimeMillis());
        }
    }

    @AfterReturning(returning = "object", pointcut = "accessLog()")
    public void afterReturningLogGenerate(JoinPoint joinPoint, Object object) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request;
        if (requestAttributes != null) {
            request = requestAttributes.getRequest();
        } else {
            log.warn("requestAttributes is null");
            return;
        }

        if (log.isDebugEnabled()) {
            StringBuilder sb = new StringBuilder();
            sb.append("Ip: {").append(getIpAddress(request)).append("}, ");
            sb.append("Request: {").append(request.getMethod()).append(" ").append(request.getRequestURL().toString());
            if (StringUtils.isNotBlank(request.getQueryString())) {
                sb.append("?").append(request.getQueryString());
            }
            sb.append("}, ");

            sb.append("Args: {").append(Arrays.toString(joinPoint.getArgs())).append("}, ");
            sb.append("Response: {").append(gson.toJson(object)).append("}, ");
            sb.append("ProcessMillis: {").append(System.currentTimeMillis() - timestamps.get()).append("ms}");
            log.debug(sb.toString());
            timestamps.remove();
        }

    }

    /**
     * 异常通知
     */
    @AfterThrowing(value = "accessLog()", throwing = "e")
    public void throwLog(JoinPoint joinPoint, Throwable e) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request;
        if (requestAttributes != null) {
            request = requestAttributes.getRequest();
        } else {
            log.warn("requestAttributes is null");
            return;
        }
        if (log.isErrorEnabled()) {
            StringBuilder sb = new StringBuilder();
            sb.append("Ip: {").append(getIpAddress(request)).append("}, ");
            sb.append("Request: {").append(request.getMethod()).append(" ").append(request.getRequestURL().toString());
            if (StringUtils.isNotBlank(request.getQueryString())) {
                sb.append("?").append(request.getQueryString());
            }
            sb.append("}, ");

            sb.append("Args: {").append(Arrays.toString(joinPoint.getArgs())).append("}, ");
            sb.append("Exception: {").append(e.getMessage()).append("}");

            log.error(sb.toString());
            log.error(e.getMessage(), e);
        }
        timestamps.remove();
    }

    /**
     * 从请求中获取IPAddress
     */
    String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-forwarded-for");
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        //"***.***.***.***".length() = 15
        if (StringUtils.isNotBlank(ip)) {
            if (ip.indexOf(",") > 0) {
                ip = ip.substring(0, ip.indexOf(","));
            }
        }
        return ip;
    }
}
