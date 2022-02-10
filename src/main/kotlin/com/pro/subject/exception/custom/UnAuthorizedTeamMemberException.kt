package com.pro.subject.exception.custom

import com.pro.user.exception.custom.CustomException
import com.pro.user.exception.message.ErrorCode

/**
 * Created by Minky on 2022-02-10
 */
class UnAuthorizedTeamMemberException:
        CustomException(ErrorCode.UNAUTHORIZED_TEAM_MEMBER)