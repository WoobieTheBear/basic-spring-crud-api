package ch.black.util.aspect;

import java.util.logging.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class BlackLoggingAspect {
    private Logger logger = Logger.getLogger(getClass().getName());

    @Pointcut("execution(* ch.black.gravel.controllers.*.*(..))")
    private void onControllerPackage() {}

    @Pointcut("execution(* ch.black.gravel.services.*.*(..))")
    private void onServicesPackage() {}

    @Pointcut("execution(* ch.black.gravel.daos.*.*(..))")
    private void onDaosPackage() {}

    @Pointcut("onControllerPackage() || onServicesPackage() || onDaosPackage()")
    private void onBlackFlow() {}
    
    @Before("onBlackFlow()")
    public void preExecution(JoinPoint meta) {
        String method = meta.getSignature().toShortString();
        String message = "BEFORE: " + method;
        Object[] arguments = meta.getArgs();
        if (arguments.length > 0) {
            message += " ARGUMENTS: ";
        }
        for (Object argument : arguments) {
            message += " " + argument;
        }

        logger.info(message);
    }

    @AfterReturning(pointcut = "onBlackFlow()", returning = "result")
    public void postExecution(JoinPoint meta, Object result) {
        String method = meta.getSignature().toShortString();
        String message = "BEFORE: " + method + " RESULT: " + result;

        logger.info(message);
    }
}
