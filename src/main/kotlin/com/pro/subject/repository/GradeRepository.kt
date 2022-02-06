package com.pro.subject.repository

import com.pro.subject.domain.Grade
import org.springframework.data.jpa.repository.JpaRepository

/**
 * Created by Minky on 2022-02-06
 */
interface GradeRepository: JpaRepository<Grade, Long> {
}