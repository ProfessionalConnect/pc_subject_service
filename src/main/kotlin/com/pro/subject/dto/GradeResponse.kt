package com.pro.subject.dto

import com.pro.subject.domain.Grade

/**
 * Created by Minky on 2022-02-06
 */
data class GradeResponse(
    val id: Long?,
    val testCode: String,
    val codeType: String,
    val resultType: String,
) {
    companion object {
        fun of(grade: Grade): GradeResponse =
            GradeResponse(grade.id, grade.testCode, grade.codeType.name, grade.resultType.name)

        fun listOf(gradeList: List<Grade>): List<GradeResponse> =
            gradeList.map(GradeResponse::of)
    }
}