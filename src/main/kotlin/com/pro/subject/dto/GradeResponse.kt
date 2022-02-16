package com.pro.subject.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.pro.subject.domain.Grade
import java.time.LocalDateTime

/**
 * Created by Minky on 2022-02-06
 */

@JsonIgnoreProperties(value = ["uuid"])
data class GradeResponse(
    val id: Long?,
    val uuid: String,
    val testCode: String,
    val codeType: String,
    val resultType: String,
    val createdDate: LocalDateTime,
    val lastModifiedDate: LocalDateTime,
    var user: UserResponse? = null
) {
    companion object {
        fun of(grade: Grade): GradeResponse =
            GradeResponse(
                grade.id,
                grade.uuid,
                grade.testCode,
                grade.codeType.name,
                grade.resultType.name,
                grade.createdDate,
                grade.lastModifiedDate
            )

        fun listOf(gradeList: List<Grade>): List<GradeResponse> =
            gradeList.map(GradeResponse::of)
    }
}