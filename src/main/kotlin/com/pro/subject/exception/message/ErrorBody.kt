package com.pro.user.exception.message

import org.springframework.http.HttpStatus


/**
 * Created by Minky on 2022-02-09
 */
data class ErrorBody(
    val code: Int,
    val message: String,
    val httpStatus: HttpStatus
) {
}