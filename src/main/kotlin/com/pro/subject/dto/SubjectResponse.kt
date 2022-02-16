package com.pro.subject.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.pro.subject.domain.ResultType
import com.pro.subject.domain.Subject
import java.time.LocalDateTime

/**
 * Created by Minky on 2022-02-05
 */

@JsonIgnoreProperties(value = ["uuid"])
data class SubjectResponse(
    val id: Long?,
    val teamId: Long,
    val uuid: String,
    val title: String,
    val description: String,
    val codeType: String,
    val createdDate: LocalDateTime,
    val lastModifiedDate: LocalDateTime,
    val isPass: Boolean,
    var user: UserResponse? = null
) {
    companion object {
        fun of(subject: Subject): SubjectResponse =
            SubjectResponse(
                subject.id,
                subject.teamId,
                subject.uuid,
                subject.title,
                subject.description,
                subject.codeType.name,
                subject.createdDate,
                subject.lastModifiedDate,
                subject.gradeList.any {grade -> grade.resultType == ResultType.SUCCESS }
            )

        fun listOf(subjectList: List<Subject>): List<SubjectResponse> =
            subjectList.map(SubjectResponse::of)
    }
}