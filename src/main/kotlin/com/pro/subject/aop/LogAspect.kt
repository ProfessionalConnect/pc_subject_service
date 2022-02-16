package com.pro.subject.aop

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

/**
 * Created by Minky on 2022-02-16
 */

@Aspect
@Component
class LogAspect {
    private val logger = LoggerFactory.getLogger(LogAspect::class.java)

    @Around("execution(* com.pro..controller.*Controller.*(..))\n || execution(* com.pro..service.*Service.*(..))\n")
    @Throws(Throwable::class)
    fun infoLogging(joinPoint: ProceedingJoinPoint): Any? {
        val startTime = System.currentTimeMillis()
        logger.info("Start [${joinPoint.signature.declaringTypeName}: By ${joinPoint.signature.name}]")
        val result = joinPoint.proceed()
        val runningTime = System.currentTimeMillis() - startTime
        logger.info("Stop [${joinPoint.signature.declaringTypeName}: By ${joinPoint.signature.name} in ${runningTime}ms ]")
        return result
    }

    @Around("execution(* com.pro..repository.*Repository.*(..))\n || execution(* com.pro..client.*Client.*(..))\n")
    @Throws(Throwable::class)
    fun infoIOLogging(joinPoint: ProceedingJoinPoint): Any? {
        val startTime = System.currentTimeMillis()
        logger.info("Connect [${joinPoint.signature.declaringTypeName}: By ${joinPoint.signature.name}]")
        val result = joinPoint.proceed()
        val runningTime = System.currentTimeMillis() - startTime
        logger.info("Disconnect [${joinPoint.signature.declaringTypeName}: By ${joinPoint.signature.name} in ${runningTime}ms ]")
        return result
    }

    @Around("execution(* com.pro..CustomExceptionHandler.*(..))\n")
    @Throws(Throwable::class)
    fun exceptionLogging(joinPoint: ProceedingJoinPoint): Any? {
        logger.warn("Try [${joinPoint.signature.declaringTypeName}: By ${joinPoint.signature.name}]")
        val result = joinPoint.proceed()
        logger.warn("Catch [${joinPoint.signature.declaringTypeName}: By ${joinPoint.signature.name}]")
        return result
    }
}