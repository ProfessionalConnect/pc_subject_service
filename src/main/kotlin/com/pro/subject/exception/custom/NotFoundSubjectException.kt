package com.pro.subject.exception.custom

import com.pro.subject.exception.message.ErrorCode

/**
 * Created by Minky on 2022-02-10
 */
class NotFoundSubjectException:
        CustomException(ErrorCode.NOT_FOUND_SUBJECT)