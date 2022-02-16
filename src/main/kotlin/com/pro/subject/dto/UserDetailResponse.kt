package com.pro.subject.dto

import java.util.concurrent.ConcurrentHashMap

/**
 * Created by Minky on 2022-02-17
 */
data class UserDetailResponse(
    val uuid: String,
    val nickname: String,
    val college: String,
    val rectal: String,
    val description: String,
) {
    fun getUserResponse() = UserResponse(nickname, college, rectal, description)

    companion object {
        fun mapOf(userDetailResponses: List<UserDetailResponse>): ConcurrentHashMap<String, UserResponse> {
            val usersMap = ConcurrentHashMap<String, UserResponse>()
            userDetailResponses.forEach { it -> usersMap[it.uuid] = it.getUserResponse() }
            return usersMap
        }
    }
}