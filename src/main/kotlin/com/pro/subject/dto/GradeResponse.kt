package com.pro.subject.dto

import com.pro.subject.domain.Grade
import java.time.LocalDateTime

/**
 * Created by Minky on 2022-02-06
 */
data class GradeResponse(
    val id: Long?,
    val testCode: String,
    val codeType: String,
    val resultType: String,
    val createdDate: LocalDateTime,
    val lastModifiedDate: LocalDateTime,
) {
    companion object {
        fun of(grade: Grade): GradeResponse =
            GradeResponse(
                grade.id,
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