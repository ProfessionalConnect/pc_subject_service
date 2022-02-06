package com.pro.subject.service

import com.pro.subject.dto.ExecResponse
import com.pro.subject.dto.GradeRequest
import com.pro.subject.dto.SubjectRequest
import com.pro.subject.dto.SubjectResponse

/**
 * Created by Minky on 2022-02-05
 */

interface SubjectService {
    fun getSubjectsByTeamId(teamId: Long): List<SubjectResponse>
    fun setSubject(subjectRequest: SubjectRequest): Long?
    fun setGrade(gradeRequest: GradeRequest): ExecResponse
}