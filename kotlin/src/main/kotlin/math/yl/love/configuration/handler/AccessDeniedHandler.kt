package math.yl.love.configuration.handler

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import math.yl.love.base.Log.log
import org.springframework.security.web.access.AccessDeniedHandler

class AccessDeniedHandler : AccessDeniedHandler {

    override fun handle(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        accessDeniedException: org.springframework.security.access.AccessDeniedException?
    ) {

        response?.status = HttpServletResponse.SC_FORBIDDEN
        response?.contentType = "application/json;charset=UTF-8"
        response?.writer?.write("{\"message\": \"Access Denied\"}")

        log.info("yuri: 访问拦截:")
    }
}
