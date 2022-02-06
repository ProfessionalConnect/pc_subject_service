package com.pro.subject.client

import com.pro.subject.dto.ExecRequest
import com.pro.subject.dto.ExecResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

/**
 * Created by Minky on 2022-02-06
 */

@FeignClient(name = "exec-service")
interface ExecServiceClient {

    @PostMapping
    fun matchTestCases(@RequestBody execRequest: ExecRequest): ResponseEntity<ExecResponse>
}