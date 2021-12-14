package com.atos.atosproject.helpers;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class loggingAspect {


    long startTime = 0;
    long endTime = 0;
    Logger logger = Logger.getLogger(loggingAspect.class.getName());
    
    /*
   FileHandler file= new FileHandler("log.xml");

    public loggingAspect() throws IOException {
        logger.addHandler(file);
        logger.setUseParentHandlers(false);
    }
    */

    @Around("execution(* com.atos.atosproject.sevices.UserService.edit*(..))")
    public Object callEditUser( ProceedingJoinPoint proceedingJoinPoint ) throws IOException {
        
        logger.info("************* Beford  execution editUser    *************\n");
    logger.info("************* Beford  execution editUser    *************\n");
        startTime = System.currentTimeMillis();
        Object value = null;
        try {
            value = proceedingJoinPoint.proceed(); // call edit
        } catch (Throwable e) {
            logger.info("*************  editUser  problem   *************");
            logger.info(e.getMessage());
          
        }
         endTime = System.currentTimeMillis();
        logger.info("************* After  execution editUser , duration = "+(endTime-startTime) +" *************");
        return value;
    }


    @Around("execution(* com.atos.atosproject.sevices.UserService.add*(..))")
    public Object callAddUser( ProceedingJoinPoint proceedingJoinPoint ) throws IOException {
        logger.info("************* Beford  execution addUser    *************");
        Object value = null;
        startTime = System.currentTimeMillis();
        try {
            value = proceedingJoinPoint.proceed(); // call add
        } catch (Throwable e) {
            logger.info("*************  addUser  problem   *************");
            e.printStackTrace();
        }
        endTime = System.currentTimeMillis();
        logger.info("************* After  execution addUser , duration = "+(endTime-startTime) +" *************");
        return value;
    }


    @Before("execution(* com.atos.atosproject.sevices.UserService.delete(..))")
    public void beforeDeleteUser() {
        logger.info("************* Before  execution delete    *************");
        startTime = System.currentTimeMillis();
    }

    @After("execution(* com.atos.atosproject.sevices.UserService.delete(..))")
    public void afterDeleteUser() {
        endTime = System.currentTimeMillis();
        logger.info("************* After execution delete  , duration = "+(endTime-startTime) +" *************");
    }


    @Before("execution(* com.atos.atosproject.sevices.UserService.find*(..))")
    public void callGetUser() {
        logger.info("************* Before  execution find    *************");
    }

}

