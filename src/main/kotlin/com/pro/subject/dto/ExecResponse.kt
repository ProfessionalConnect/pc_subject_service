package com.pro.subject.dto

import com.pro.subject.domain.ResultType

/**
 * Created by Minky on 2022-02-06
 */
data class ExecResponse(
    val result: ResultType,
    val message: String
) {
}