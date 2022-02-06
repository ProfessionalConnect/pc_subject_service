package com.pro.subject.service.implement

import com.pro.subject.client.ExecServiceClient
import com.pro.subject.domain.TestCase
import com.pro.subject.dto.*
import com.pro.subject.repository.GradeRepository
import com.pro.subject.repository.SubjectRepository
import com.pro.subject.repository.TestCaseRepository
import com.pro.subject.service.SubjectService
import org.springframework.beans.factory.annotation.Autowired
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
    private lateinit var execServiceClient: ExecServiceClient

    @Transactional(readOnly = true)
    override fun getSubjectsByTeamId(teamId: Long) = SubjectResponse.listOf(subjectRepository.findByTeamId(teamId))

    @Transactional
    override fun setSubject(subjectRequest: SubjectRequest): Long? {
        // TODO(사용자에 따른 팀 인증)
        val subject = subjectRepository.save(subjectRequest.toEntity())
        var testCaseList = mutableListOf<TestCase>()

        for (testArgument in subjectRequest.testArguments) {
            testCaseList.add(
                TestCase(subject, testArgument.testArgument, testArgument.matchResult)
            )
        }

        testCaseRepository.saveAll(testCaseList)

        return subject.id
    }

    @Transactional
    override fun setGrade(gradeRequest: GradeRequest): ExecResponse {
        // TODO(Exception 처리)
        // TODO(사용자에 따른 팀 인증)
        val subject = subjectRepository.findById(gradeRequest.subjectId).get()
        val testCase = testCaseRepository.findBySubject(subject)

        // TODO(FeignClient 예외처리)
        val execResponse = execServiceClient.matchTestCases(ExecRequest(gradeRequest.uuid, gradeRequest.subjectId,
                gradeRequest.testCode, TestArgumentRequest.listOf(testCase), gradeRequest.codeType))

        gradeRepository.save(gradeRequest.toEntity(execResponse, subject))

        return execResponse
    }
}