package com.pro.subject.service.implement

import com.pro.subject.client.ExecServiceClient
import com.pro.subject.client.TeamServiceClient
import com.pro.subject.client.UserServiceClient
import com.pro.subject.domain.ResultType
import com.pro.subject.domain.TestCase
import com.pro.subject.dto.*
import com.pro.subject.exception.custom.FeignClientException
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
import java.util.concurrent.ConcurrentHashMap

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
    @Autowired
    private lateinit var userServiceClient: UserServiceClient

    @Transactional(readOnly = true)
    override fun getSubjectsByTeamId(uuid: String, teamId: Long, pageable: Pageable): Page<SubjectResponse> {
        validateTeamMember(uuid, teamId)
        val subjects = subjectRepository.findByTeamId(teamId, pageable)
        val userMap = getUserMap(subjects.content.map { subject -> subject.uuid })

        val subjectResponses = SubjectResponse.listOf(subjects.content)
        setUsersInSubjects(subjectResponses, userMap)

        return PageImpl(subjectResponses, pageable, subjects.totalElements)
    }

    @Transactional(readOnly = true)
    override fun getSubject(uuid: String, subjectId: Long): SubjectResponse {
        val subject = subjectRepository.findById(subjectId).orElseThrow{ throw NotFoundSubjectException() }
        validateTeamMember(uuid, subject.teamId)
        val userMap = getUserMap(listOf(subject.uuid))

        val subjectResponse = SubjectResponse.of(subject)
        setUsersInSubject(subjectResponse, userMap)

        return subjectResponse
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
    override fun getGradeBySubjectAndUUID(uuid: String, subjectId: Long, pageable: Pageable): Page<GradeResponse> {
        val subject = subjectRepository.findById(subjectId)
            .orElseThrow { throw NotFoundSubjectException() }

        validateTeamMember(uuid, subject.teamId)
        val grades = gradeRepository.findBySubjectAndUuid(subject, uuid, pageable)
        val userMap = getUserMap(grades.content.map { grade -> grade.uuid })

        val gradeResponses = GradeResponse.listOf(grades.content)
        setUsersInGrades(gradeResponses, userMap)

        return PageImpl(gradeResponses, pageable, grades.totalElements)
    }

    @Transactional(readOnly = true)
    override fun getGradeBySubject(subjectId: Long, pageable: Pageable): Page<GradeResponse> {
        val subject = subjectRepository.findById(subjectId)
            .orElseThrow{ throw NotFoundSubjectException() }

        val grades = gradeRepository.findBySubject(subject, pageable)
        val userMap = getUserMap(grades.content.map { grade -> grade.uuid })

        val gradeResponses = GradeResponse.listOf(grades.content)
        setUsersInGrades(gradeResponses, userMap)

        return PageImpl(gradeResponses, pageable, grades.totalElements)
    }

    @Transactional(readOnly = true)
    override fun getGradeByUUID(uuid: String) =
        GradeResponse.listOf(gradeRepository.findByUuid(uuid))

    private fun getUserMap(
        uuidList: List<String>
    ): ConcurrentHashMap<String, UserResponse> {
        return try {
            UserDetailResponse.mapOf(userServiceClient.getUsersByUuid(
                    listToDetailRequests(uuidList)))
        } catch (e: FeignException) {
            throw FeignClientException()
        }
    }

    private fun setUsersInSubject(
        subjectResponse: SubjectResponse,
        userMap: ConcurrentHashMap<String, UserResponse>
    ) {
        if (userMap.containsKey(subjectResponse.uuid)) {
            subjectResponse.user = userMap[subjectResponse.uuid]
        }
    }

    private fun setUsersInSubjects(
        subjectResponses: List<SubjectResponse>,
        userMap: ConcurrentHashMap<String, UserResponse>
    ) = subjectResponses.forEach { it ->
        if (userMap.containsKey(it.uuid)) {
            it.user = userMap[it.uuid]
        }
    }

    private fun setUsersInGrades(
        gradeResponses: List<GradeResponse>,
        userMap: ConcurrentHashMap<String, UserResponse>
    ) = gradeResponses.forEach { it ->
        if (userMap.containsKey(it.uuid)) {
            it.user = userMap[it.uuid]
        }
    }

    private fun listToDetailRequests(uuidList: List<String>): List<DetailRequest> =
        uuidList.map { it -> DetailRequest(it) }

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