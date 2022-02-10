package com.pro.user.exception.message

import org.springframework.http.HttpStatus

/**
 * Created by Minky on 2022-02-09
 */
enum class ErrorCode(
    val code: Int,
    val message: String,
    val httpStatus: HttpStatus
) {
    UNAUTHORIZED_TEAM_MEMBER(401, "[ERROR] You are not join this Team, check your team list", HttpStatus.UNAUTHORIZED),
    NOT_FOUND_SUBJECT(404, "[ERROR] Subject is not found", HttpStatus.NOT_FOUND), ;
}