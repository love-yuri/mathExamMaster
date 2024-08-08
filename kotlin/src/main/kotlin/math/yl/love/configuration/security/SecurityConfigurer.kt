package math.yl.love.configuration.security


import math.yl.love.configuration.config.SystemConfig
import math.yl.love.configuration.handler.AccessDeniedHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfigurer(
    val systemConfig: SystemConfig
) {

    @Bean fun PasswordEncoder (): PasswordEncoder = BCryptPasswordEncoder()

//    @Bean
//    fun securityFilterChain(http: HttpSecurity, authenticationConfiguration: AuthenticationConfiguration): SecurityFilterChain {
//        val securityIgnoreUrls = systemConfig.securityIgnoreUrls
//        val ignores = securityIgnoreUrls?.toTypedArray() ?: emptyArray()
//
//        http
//            /* 授权认证 */
////            .authorizeHttpRequests {
////                it.requestMatchers(*ignores).permitAll() // 安全连接直接放行
////                it.requestMatchers("/doc.html", "/webjars/**", "v3/api-docs/**").permitAll() // 允许Knife4j相关路径
////                it.anyRequest().authenticated()
////            }
////
////            .logout {
////                it.permitAll()
////            }
////
//            .csrf {
//                it.disable()
//            }
//
//            /* 异常处理 */
//            .exceptionHandling {
//                it.accessDeniedHandler(AccessDeniedHandler())
//            }
//
//        return http.build()
//    }

}
