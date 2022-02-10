package com.pro.subject.client

import com.pro.subject.dto.MemberValidateResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.*

/**
 * Created by Minky on 2022-02-10
 */

@FeignClient(name = "team-service")
interface TeamServiceClient {

    @GetMapping("/api/v1/teams/ms/{teamId}/match")
    fun validateUserTargetTeamMember(
        @RequestHeader(value = "uuid") uuid: String,
        @PathVariable teamId: Long
    ): MemberValidateResponse
}