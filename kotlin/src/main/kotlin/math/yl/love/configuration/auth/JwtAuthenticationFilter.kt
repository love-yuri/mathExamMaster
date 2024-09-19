package math.yl.love.configuration.auth

import com.auth0.jwt.exceptions.JWTVerificationException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import math.yl.love.common.base.R
import math.yl.love.common.base.SystemCode
import math.yl.love.common.constant.HeadersConstant
import math.yl.love.common.utils.JwtUtils
import math.yl.love.database.domain.result.user.LoginJwtResult
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
        "/user/login",
        "favicon.ico"
    )

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        // 判断当前请求路径是否在排除的路径列表中
        if (safeUrl.any { request.requestURI.startsWith(it) }) {
            filterChain.doFilter(request, response) // 放行请求
            return
        }

        request.getHeader(HeadersConstant.AUTHORIZATION)?.also {
            try {
                val res = JwtUtils.verifyTokenAndParse(it, LoginJwtResult::class)
                val authentication = UsernamePasswordAuthenticationToken(res, null, emptyList())
                authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = authentication
                filterChain.doFilter(request, response)
            } catch (e: JWTVerificationException) {
                /* token 解析失败 或 过期 */
                R.fail(response, SystemCode.AccessTokenError)
            } catch (e: Exception) {
                // 捕获其他异常
                R.fail(response, SystemCode.UNAUTHORIZED)
            }
        } ?: run {
            R.fail(response, SystemCode.UNAUTHORIZED)
        }
    }
}
