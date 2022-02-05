package com.pro.subject.dto

import com.pro.subject.domain.CodeType
import com.pro.subject.domain.Subject

/**
 * Created by Minky on 2022-02-05
 */
data class SubjectRequest(
    val teamId: Long,
    val uuid: String,
    val title: String,
    val description: String,
    val correctCode: String,
    val codeType: String,
) {
    fun toEntity(): Subject {
        return Subject(
            teamId,
            uuid,
            title,
            description,
            correctCode,
            CodeType.valueOf(codeType)
        )
    }
}