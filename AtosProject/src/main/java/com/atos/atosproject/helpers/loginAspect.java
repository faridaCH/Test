package com.atos.atosproject.helpers;

import java.io.IOException;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;



@Aspect
@Component
public class loginAspect {

    @Around("execution(* com.atos.atosproject.sevices.UserService.edit*(..))")
    public Object callEditUser( ProceedingJoinPoint proceedingJoinPoint ) throws IOException {
        System.out.println("************************edit Post Avant");
        Object value = null;
        try {
            value = proceedingJoinPoint.proceed(); // Appel editPost
        } catch (Throwable e) {
            System.out.println("****************************edit Post Problem");
            e.printStackTrace();
        }
        System.out.println("**********************************edit Post Après");
        return value;
    }
    
}
