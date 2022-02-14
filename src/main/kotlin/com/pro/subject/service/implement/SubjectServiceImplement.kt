package com.pro.subject.service.implement

import com.pro.subject.client.ExecServiceClient
import com.pro.subject.client.TeamServiceClient
import com.pro.subject.domain.ResultType
import com.pro.subject.domain.TestCase
import com.pro.subject.dto.*
import com.pro.subject.exception.custom.NotFoundSubjectException
import com.pro.subject.exception.custom.UnAuthorizedTeamMemberException
import com.pro.subject.repository.GradeRepository
import com.pro.subject.repository.SubjectRepository
import com.pro.subject.repository.TestCaseRepository
import com.pro.subject.service.SubjectService
import feign.FeignException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Created by Minky on 2022-02-05
 */

@Service
class SubjectServiceImplement: SubjectService {
    @Autowired
    private lateinit var subjectRepository: SubjectRepository
    @Autowired
    private lateinit var testCaseRepository: TestCaseRepository
    @Autowired
    private lateinit var gradeRepository: GradeRepository
    @Autowired
    private lateinit var teamServiceClient: TeamServiceClient
    @Autowired
    private lateinit var execServiceClient: ExecServiceClient

    @Transactional(readOnly = true)
    override fun getSubjectsByTeamId(uuid: String, teamId: Long, pageable: Pageable): Page<SubjectResponse> {
        validateTeamMember(uuid, teamId)
        val subjects = subjectRepository.findByTeamId(teamId, pageable)
        return PageImpl(SubjectResponse.listOf(subjects.content), pageable, subjects.totalElements)
    }

    @Transactional(readOnly = true)
    override fun getSubject(uuid: String, subjectId: Long): SubjectResponse {
        val subject = subjectRepository.findById(subjectId).orElseThrow{ throw NotFoundSubjectException() }
        validateTeamMember(uuid, subject.teamId)
        return SubjectResponse.of(subject)
    }

    @Transactional
    override fun setSubject(uuid: String, subjectRequest: SubjectRequest): Long? {
        validateTeamMember(uuid, subjectRequest.teamId)

        val subject = subjectRepository.save(subjectRequest.toEntity(uuid))
        val testCaseList = subjectRequest.testArguments.map { testArgument -> TestCase(subject, testArgument.testArgument, testArgument.matchResult) }

        testCaseRepository.saveAll(testCaseList)
        return subject.id
    }

    @Transactional
    override fun setGrade(uuid: String, gradeRequest: GradeRequest): ExecResponse {
        val subject = subjectRepository.findById(gradeRequest.subjectId)
            .orElseThrow { throw NotFoundSubjectException() }

        validateTeamMember(uuid, subject.teamId)

        val testCase = testCaseRepository.findBySubject(subject)
        val execResponse = gradingCode(uuid, gradeRequest, TestArgumentRequest.listOf(testCase))

        gradeRepository.save(gradeRequest.toEntity(uuid, execResponse, subject))

        return execResponse
    }

    @Transactional(readOnly = true)
    override fun getGradeBySubjectAndUUID(uuid: String, subjectId: Long): List<GradeResponse> {
        val subject = subjectRepository.findById(subjectId)
            .orElseThrow { throw NotFoundSubjectException() }

        validateTeamMember(uuid, subject.teamId)

        return GradeResponse.listOf(gradeRepository.findBySubjectAndUuid(subject, uuid))
    }

    @Transactional(readOnly = true)
    override fun getGradeBySubject(subjectId: Long): List<GradeResponse> {
        val subject = subjectRepository.findById(subjectId)
            .orElseThrow{ throw NotFoundSubjectException() }
        return GradeResponse.listOf(gradeRepository.findBySubject(subject))
    }

    @Transactional(readOnly = true)
    override fun getGradeByUUID(uuid: String) =
        GradeResponse.listOf(gradeRepository.findByUuid(uuid))

    private fun gradingCode(
        uuid: String,
        gradeRequest: GradeRequest,
        testCaseArguments: List<TestArgumentRequest>
    ): ExecResponse {
        return try {
            execServiceClient.matchTestCases(
                ExecRequest(uuid, gradeRequest.subjectId, gradeRequest.testCode, testCaseArguments, gradeRequest.codeType)
            )
        } catch (e: FeignException) {
            ExecResponse(ResultType.COMPILE_ERROR, "Feign Exception, Must be check the server")
        }
    }

    private fun validateTeamMember(uuid: String, teamId: Long) {
        try {
            val memberValidateResponse = teamServiceClient.validateUserTargetTeamMember(uuid, teamId)

            if (!memberValidateResponse.result) {
                throw UnAuthorizedTeamMemberException()
            }
        } catch (e: FeignException) {
            throw UnAuthorizedTeamMemberException()
        }
    }
}