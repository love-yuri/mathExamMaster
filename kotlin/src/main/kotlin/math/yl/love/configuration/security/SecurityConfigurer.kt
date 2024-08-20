package math.yl.love.configuration.security


import math.yl.love.configuration.auth.JwtAuthenticationFilter
import math.yl.love.configuration.config.SystemConfig
import math.yl.love.configuration.handler.AccessDeniedHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authorization.AuthorizationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.HttpStatusEntryPoint
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfigurer(
    val systemConfig: SystemConfig,
    val jwtAuthenticationFilter: JwtAuthenticationFilter
) {

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun authenticationManager(config: AuthenticationConfiguration): AuthenticationManager = config.authenticationManager


    @Bean
    fun securityFilterChain(http: HttpSecurity, authenticationConfiguration: AuthenticationConfiguration): SecurityFilterChain {
        val securityIgnoreUrls = systemConfig.securityIgnoreUrls
        val ignores = securityIgnoreUrls?.toTypedArray() ?: emptyArray()

        http
            /* 授权认证 */
            .authorizeHttpRequests {
                it.requestMatchers(*ignores).permitAll() // 安全连接直接放行
                it.requestMatchers("/doc.html", "/webjars/**", "v3/api-docs/**").permitAll() // 允许Knife4j相关路径
                it.requestMatchers("/user/login").permitAll()
                it.anyRequest().authenticated()
            }


            .logout {
                it.permitAll()
            }

            .csrf {
                it.disable()
            }

            /* 异常处理 */
            .exceptionHandling {
                it.accessDeniedHandler(AccessDeniedHandler()) // 自定义处理权限不足
//                it.authenticationEntryPoint(LoginUrlAuthenticationEntryPoint("/user/login"))

            }
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }
}
