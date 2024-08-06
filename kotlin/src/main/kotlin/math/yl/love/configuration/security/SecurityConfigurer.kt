package math.yl.love.configuration.security


import math.yl.love.configuration.config.SystemConfig
import math.yl.love.configuration.handler.AccessDeniedHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
open class SecurityConfigurer(
    val systemConfig: SystemConfig
) {

    @Bean
    open fun securityFilterChain(http: HttpSecurity, authenticationConfiguration: AuthenticationConfiguration): SecurityFilterChain {
        val securityIgnoreUrls = systemConfig.securityIgnoreUrls
        val ignores = securityIgnoreUrls?.toTypedArray() ?: emptyArray()

        http
            /* 授权认证 */
            .authorizeHttpRequests {
                it.requestMatchers(*ignores).permitAll() // 安全连接直接放行
                it.requestMatchers("/**").permitAll() // 禁止放行
                it.anyRequest().permitAll() // 不匹配，默认放行
            }

            /* 异常处理 */
            .exceptionHandling {
                it.accessDeniedHandler(AccessDeniedHandler())
            }

        return http.build()
    }

}
