package math.yl.love.configuration.auth

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import math.yl.love.common.utils.JwtUtils
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter : OncePerRequestFilter() {

    private val log = LoggerFactory.getLogger(javaClass)

    /**
     * 过滤器安全路由
     */
    private val safeUrl = listOf(
        "/doc.html",
        "/webjars",
        "/v3/api-docs",
        "/swagger-ui",
        "/swagger-resources",
        "/user/login"
    )

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {

        log.info("uri: ${request.requestURI}")

        // 判断当前请求路径是否在排除的路径列表中
        if (safeUrl.any { request.requestURI.startsWith(it) }) {
            filterChain.doFilter(request, response) // 放行请求
            return
        }

        getTokenFromRequest(request)?.also {
            val username = JwtUtils.verifyTokenTest(it)
            log.info("yuri: user: $username")
            val authentication = UsernamePasswordAuthenticationToken(username, null, emptyList())
            authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
            SecurityContextHolder.getContext().authentication = authentication

        }
        filterChain.doFilter(request, response)
    }

    private fun getTokenFromRequest(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader("Authorization")
        return if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            bearerToken.substring(7)
        } else null
    }
}
