package com.pro.subject.exception.custom

import com.pro.subject.exception.message.ErrorCode

/**
 * Created by Minky on 2022-02-17
 */
class FeignClientException:
    CustomException(ErrorCode.FEIGN_CLIENT_ERROR)