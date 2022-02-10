package com.pro.subject.exception.custom

import com.pro.subject.exception.message.ErrorCode

/**
 * Created by Minky on 2022-02-09
 */
open class CustomException(val errorCode: ErrorCode): RuntimeException() {
    override val message: String?
        get() = errorCode.message
}