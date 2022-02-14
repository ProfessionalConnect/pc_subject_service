package com.pro.subject.repository

import com.pro.subject.domain.Grade
import com.pro.subject.domain.Subject
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * Created by Minky on 2022-02-06
 */

@Repository
interface GradeRepository: JpaRepository<Grade, Long> {
    fun findBySubject(subject: Subject, pageable: Pageable): Page<Grade>
    fun findBySubjectAndUuid(subject: Subject, uuid: String, pageable: Pageable): Page<Grade>
    fun findByUuid(uuid: String): List<Grade>
}