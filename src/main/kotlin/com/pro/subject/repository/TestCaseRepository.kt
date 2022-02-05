package com.pro.subject.repository

import com.pro.subject.domain.TestCase
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * Created by Minky on 2022-02-06
 */

@Repository
interface TestCaseRepository: JpaRepository<TestCase, Long> {
}