package com.pro.subject.dto

import com.pro.subject.domain.TestCase

/**
 * Created by Minky on 2022-02-06
 */
data class TestArgumentRequest(
    val testArgument: String,
    val matchResult: String,
) {
    companion object {
        fun of(testCase: TestCase): TestArgumentRequest =
            TestArgumentRequest(testCase.testArgument, testCase.matchResult)


        fun listOf(testCaseList: List<TestCase>): List<TestArgumentRequest> =
            testCaseList.map(TestArgumentRequest::of)
    }
}