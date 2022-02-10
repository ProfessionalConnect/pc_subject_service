package com.pro.subject.dto

/**
 * Created by Minky on 2022-02-07
 */
enum class ValidateType(val message: String) {
    IN_MEMBER("Authorized Team Member"),
    OUT_OF_MEMBER("UnAuthorized Team Member");
}