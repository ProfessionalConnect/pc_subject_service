package com.pro.subject.repository

import com.pro.subject.domain.Subject
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * Created by Minky on 2022-02-05
 */

@Repository
interface SubjectRepository: JpaRepository<Subject, Long> {
    fun findByTeamId(teamId: Long, pageable: Pageable): Page<Subject>
}