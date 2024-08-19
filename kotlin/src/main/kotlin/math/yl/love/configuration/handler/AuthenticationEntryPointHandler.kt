package math.yl.love.configuration.handler

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import math.yl.love.common.base.R
import math.yl.love.common.base.SystemCode
import math.yl.love.common.utils.JsonUtils.toJson
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

@Component
class AuthenticationEntryPointHandler: AuthenticationEntryPoint {
    override fun commence(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authException: AuthenticationException?
    ) {
        response?.apply {
            status = HttpServletResponse.SC_FORBIDDEN
            contentType = "application/json;charset=UTF-8" // 指定 UTF-8 编码
            writer.write(R.fail(SystemCode.UNAUTHORIZED).toJson())
        }
    }
}