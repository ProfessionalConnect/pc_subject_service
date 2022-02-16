package com.pro.subject.client

import com.pro.subject.dto.DetailRequest
import com.pro.subject.dto.UserDetailResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

/**
 * Created by Minky on 2022-02-17
 */

@FeignClient(name = "user-service")
interface UserServiceClient {

    @PostMapping("/api/v1/users/details")
    fun getUsersByUuid(
        @RequestBody detailRequests: List<DetailRequest>
    ): List<UserDetailResponse>
}