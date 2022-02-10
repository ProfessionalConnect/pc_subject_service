package com.pro.subject.dto

/**
 * Created by Minky on 2022-02-07
 */
data class MemberValidateResponse(
    val result: Boolean,
    val validateType: ValidateType,
    val message: String,
) {
}