package math.yl.love.configuration.handler

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import math.yl.love.base.Log.log
import math.yl.love.base.R
import math.yl.love.base.SystemCode
import math.yl.love.base.utils.JsonUtils.toJson
import org.springframework.security.web.access.AccessDeniedHandler

class AccessDeniedHandler : AccessDeniedHandler {

    override fun handle(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        accessDeniedException: org.springframework.security.access.AccessDeniedException?
    ) {
        response?.status = HttpServletResponse.SC_FORBIDDEN
        response?.contentType = "application/json;charset=UTF-8"
        val res = R.fail(SystemCode.AccessDenied.code, SystemCode.AccessDenied.message)

        log.info("yuri: request -> ${request?.requestURI}")
        response?.writer?.write(res.toJson())
    }
}

