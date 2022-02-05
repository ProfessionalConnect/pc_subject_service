package com.pro.subject.service

import com.pro.subject.dto.SubjectRequest
import com.pro.subject.dto.SubjectResponse

/**
 * Created by Minky on 2022-02-05
 */

interface SubjectService {
    fun getSubjectsByTeamId(teamId: Long): List<SubjectResponse>
    fun setSubject(subjectRequest: SubjectRequest): Long?
}