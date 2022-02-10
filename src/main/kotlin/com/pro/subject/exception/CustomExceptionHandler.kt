package com.pro.user.exception

import com.pro.subject.exception.custom.NotFoundSubjectException
import com.pro.subject.exception.custom.UnAuthorizedTeamMemberException
import com.pro.user.exception.custom.*
import com.pro.user.exception.message.ErrorBody
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestControllerAdvice

/**
 * Created by Minky on 2022-02-09
 */

@RestControllerAdvice
class CustomExceptionHandler {

    @ExceptionHandler(CustomException::class)
    @ResponseBody
    fun handleCustomException(e: CustomException): ResponseEntity<ErrorBody> =
        ResponseEntity(getErrorBody(e), getHttpStatus(e))

    @ExceptionHandler(UnAuthorizedTeamMemberException::class)
    @ResponseBody
    fun handleUnAuthorizedTeamMemberException(e: UnAuthorizedTeamMemberException): ResponseEntity<ErrorBody> =
        ResponseEntity(getErrorBody(e), getHttpStatus(e))

    @ExceptionHandler(NotFoundSubjectException::class)
    @ResponseBody
    fun handleNotFoundSubjectException(e: NotFoundSubjectException): ResponseEntity<ErrorBody> =
        ResponseEntity(getErrorBody(e), getHttpStatus(e))

    private fun getHttpStatus(e: CustomException): HttpStatus =
        e.errorCode.httpStatus

    private fun getErrorBody(e: CustomException): ErrorBody {
        val errorCode = e.errorCode
        return ErrorBody(errorCode.code, errorCode.message, errorCode.httpStatus)
    }
}