package com.pro.subject.service.implement

import com.pro.subject.domain.TestCase
import com.pro.subject.dto.SubjectRequest
import com.pro.subject.dto.SubjectResponse
import com.pro.subject.repository.SubjectRepository
import com.pro.subject.repository.TestCaseRepository
import com.pro.subject.service.SubjectService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * Created by Minky on 2022-02-05
 */

@Service
class SubjectServiceImplement: SubjectService {
    @Autowired
    private lateinit var subjectRepository: SubjectRepository
    @Autowired
    private lateinit var testCaseRepository: TestCaseRepository

    override fun getSubjectsByTeamId(teamId: Long) = SubjectResponse.listOf(subjectRepository.findByTeamId(teamId))

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
}