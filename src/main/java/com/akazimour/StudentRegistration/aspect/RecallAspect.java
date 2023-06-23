package com.akazimour.StudentRegistration.aspect;

import ch.qos.logback.core.net.SocketConnector;
import com.akazimour.StudentRegistration.service.CourseService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.hibernate.tool.schema.spi.ExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class RecallAspect {
    @Autowired
    CourseService courseService;

    @Pointcut("@annotation(com.akazimour.StudentRegistration.aspect.ListeningErrors) || @within(com.akazimour.StudentRegistration.aspect.ListeningErrors)")
    public void recallPointCut() {
    }
    @AfterThrowing(value = "recallPointCut()", throwing = "e")
    public void returnThrowable(JoinPoint joinPoint, Throwable e) {
        System.out.println( "Thread interrupted because " +e.toString());
        courseService.recallUpdates(500);
        }

    }


