package com.pro.subject.dto

import com.pro.subject.domain.Subject

/**
 * Created by Minky on 2022-02-05
 */
data class SubjectResponse(
    val id: Long?,
    val teamId: Long,
    val uuid: String,
    val title: String,
    val description: String,
    val codeType: String,
) {
    companion object {
        fun of(subject: Subject): SubjectResponse {
            return SubjectResponse(
                subject.id,
                subject.teamId,
                subject.uuid,
                subject.title,
                subject.description,
                subject.codeType.name
            )
        }

        fun listOf(subjectList: List<Subject>): List<SubjectResponse> {
            return subjectList.map(SubjectResponse::of)
        }
    }
}