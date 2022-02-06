package com.pro.subject.dto

import com.pro.subject.domain.CodeType
import com.pro.subject.domain.Grade
import com.pro.subject.domain.Subject

/**
 * Created by Minky on 2022-02-06
 */
data class GradeRequest(
    val uuid: String,
    val subjectId: Long,
    val testCode: String,
    val codeType: String,
) {
    fun toEntity(execResponse: ExecResponse, subject: Subject): Grade =
        Grade(uuid, testCode, CodeType.valueOf(codeType), execResponse.result, subject)
}