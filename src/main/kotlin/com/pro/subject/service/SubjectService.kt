package com.pro.subject.service

import com.pro.subject.dto.*

/**
 * Created by Minky on 2022-02-05
 */

interface SubjectService {
    fun getSubjectsByTeamId(teamId: Long): List<SubjectResponse>
    fun getSubject(subjectId: Long): SubjectResponse
    fun setSubject(uuid: String, subjectRequest: SubjectRequest): Long?
    fun setGrade(uuid: String, gradeRequest: GradeRequest): ExecResponse
    fun getGradeBySubjectAndUUID(uuid: String, subjectId: Long): List<GradeResponse>
    fun getGradeBySubject(subjectId: Long): List<GradeResponse>
    fun getGradeByUUID(uuid: String): List<GradeResponse>
}