package com.united_iot.demo.aspect;

import com.iemylife.iot.logging.IotLogger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @auther jiahaowei
 * @date： 2017/11/24 0024
 * @time： 9:06
 * @project_name： mc_elasticsearch
 * @Description ：
 */
@Aspect
@Component
public class LoggingAspect {

    @Autowired
    private IotLogger iotLogger;

    @Pointcut("execution(public * com.united_iot.demo.controller..*(..))")
    public void log() {
    }

    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        //method
        iotLogger.debug("method= " + request.getMethod());

        //ip
        iotLogger.debug("ip=  " + request.getRemoteAddr());


        //类方法
        iotLogger.debug("class_method=  " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());

        //参数
        iotLogger.debug("args=  " + joinPoint.getArgs().length);
    }

    @After("log()")
    public void doAfter() {
        iotLogger.info("【调用完成】-----------完成");
    }

    @AfterReturning(returning = "object", pointcut = "log()")
    public void doAfterReturning(Object object) {

        iotLogger.info("response=  " + object.toString());
    }
}
