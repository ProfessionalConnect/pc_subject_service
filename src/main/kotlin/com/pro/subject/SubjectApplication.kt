package com.pro.subject

import feign.Logger
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Bean

@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
class SubjectApplication

fun main(args: Array<String>) {
    runApplication<SubjectApplication>(*args)

    @Bean
    fun feignLoggerLevel() = Logger.Level.FULL
}
