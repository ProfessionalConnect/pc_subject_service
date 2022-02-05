package com.pro.subject.service.implement

import com.pro.subject.dto.SubjectRequest
import com.pro.subject.dto.SubjectResponse
import com.pro.subject.repository.SubjectRepository
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

    override fun getSubjectsByTeamId(teamId: Long): List<SubjectResponse> {
        return SubjectResponse.listOf(subjectRepository.findByTeamId(teamId))
    }

    override fun setSubject(subjectRequest: SubjectRequest): Long? {
        // TODO(사용자에 따른 팀 인증)
        val subject = subjectRepository.save(subjectRequest.toEntity())
        return subject.id
    }
}