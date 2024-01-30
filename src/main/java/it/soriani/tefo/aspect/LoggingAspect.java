package it.soriani.tefo.aspect;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author christiansoriani on 25/01/24
 * @project TEFO_BE
 */
@Aspect
@Component
public class LoggingAspect {

    Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("within(it.soriani.tefo.controller..*)")
    public void loggingPointCut() {
        //only pointcut
    }

    @Around("loggingPointCut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        if (log.isInfoEnabled()) {
            Object[] args = joinPoint.getArgs();
            log.info(
                    "Enter: {}.{}() with argument[s] = {}",
                    joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(),
                    Arrays.toString(args));
        }
        try {
            Object result = joinPoint.proceed();
            if (Objects.nonNull(result)) {
                if (log.isInfoEnabled()) {
                    log.info("Exit: {}.{}() with result = {}",
                            joinPoint.getSignature().getDeclaringTypeName(),
                            joinPoint.getSignature().getName(),
                            result);
                }
            }
            return result;
        } catch (Exception e) {
            log.error(
                    "Exit: {}.{}() with error = {}, {}",
                    joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(),
                    e.getMessage(),
                    ExceptionUtils.getStackTrace(e));
            throw e;
        }
    }

}