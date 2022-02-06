package com.pro.subject.domain

/**
 * Created by Minky on 2022-02-06
 */
enum class ResultType(val message: String) {
    SUCCESS("Test is Success"),
    FAIL("Test is Fail"),
    FILE_CRASH("File is Crashed, Check your code"),
    TIMEOUT("Timeout, change your code"),
    COMPILE_ERROR("Compile Error, Check your code"),
    MISSMATCH("Code Type is Miss Match, Check Code Type")
}