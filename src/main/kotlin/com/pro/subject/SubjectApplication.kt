package com.pro.subject

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@EnableDiscoveryClient
@SpringBootApplication
class SubjectApplication

fun main(args: Array<String>) {
    runApplication<SubjectApplication>(*args)
}
