package math.yl.love.configuration.security
//
//
//import com.fasterxml.jackson.databind.ObjectMapper
//import math.yl.love.configuration.SystemConfig
//import math.yl.love.configuration.handler.AccessDeniedHandler
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.security.authentication.AuthenticationManager
//import org.springframework.security.authentication.AuthenticationProvider
//import org.springframework.security.authentication.ProviderManager
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider
//import org.springframework.security.config.Customizer
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
//import org.springframework.security.config.annotation.web.builders.HttpSecurity
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
//import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer.AuthorizationManagerRequestMatcherRegistry
//import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer
//import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer
//import org.springframework.security.core.userdetails.User
//import org.springframework.security.core.userdetails.UserDetails
//import org.springframework.security.core.userdetails.UserDetailsService
//import org.springframework.security.provisioning.InMemoryUserDetailsManager
//import org.springframework.security.web.SecurityFilterChain
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
//
//@Configuration
//@EnableWebSecurity
//class SecurityConfigurer(
//    val systemConfig: SystemConfig
//) {
//
////    @Bean
////    fun securityFilterChain(http: HttpSecurity, authenticationConfiguration: AuthenticationConfiguration): SecurityFilterChain {
////        val securityIgnoreUrls = systemConfig.securityIgnoreUrls
////        val ignores = securityIgnoreUrls?.toTypedArray() ?: emptyArray()
////
////        http
////            /* 授权认证 */
////            .authorizeHttpRequests {
////                it.requestMatchers(*ignores).permitAll() // 安全连接直接放行
////                it.requestMatchers("/**").authenticated() // 禁止放行
////                it.anyRequest().permitAll() // 不匹配，默认放行
////            }
////
////            /* 异常处理 */
////            .exceptionHandling {
////                it.accessDeniedHandler(AccessDeniedHandler())
////            }
////
////        return http.build()
////    }
//
//
//    @Bean
//    @Throws(Exception::class)
//    fun securityFilterChain(
//        httpSecurity: HttpSecurity,
//        objectMapper: ObjectMapper?
//    ): SecurityFilterChain {
//        // 定义安全请求拦截规则
//        httpSecurity.authorizeHttpRequests { request ->
//            request.anyRequest().authenticated()
//        }
//        // 给SpringSecurity注入 /login登录页面及用户密码表单处理的登录请求
//        httpSecurity.formLogin(Customizer.withDefaults())
//        // 登出请求注册
//        httpSecurity.logout { obj: LogoutConfigurer<HttpSecurity?> -> obj.permitAll() }
//        //        httpSecurity.addFilterBefore(magnusUsernamePasswordFilter(objectMapper),
////                                     UsernamePasswordAuthenticationFilter.class);
//        // 关闭csrf
//        httpSecurity.csrf { obj: CsrfConfigurer<HttpSecurity> -> obj.disable() }
//        return httpSecurity.build()
//    }
//
//    @Bean
//    fun magnusUsernamePasswordFilter(objectMapper: ObjectMapper?): UsernamePasswordAuthenticationFilter {
//        // 配置自定义的UsernamePasswordAuthenticationFilter
//        val magnusUsernamePasswordFilter = MagnusUsernamePasswordFilter()
//        magnusUsernamePasswordFilter.setAuthenticationManager(providerManager())
//        return magnusUsernamePasswordFilter
//    }
//
//    @Bean
//    fun userDetailsService(): UserDetailsService {
//        val build: UserDetails = User.withUsername("username").password("{noop}password").roles("user").build()
//        return InMemoryUserDetailsManager(build)
//    }
//
//    @Bean
//    fun providerManager(): AuthenticationManager {
//        val providers: MutableList<AuthenticationProvider> = ArrayList()
//        // 自定义Provider,此处可以定义多数据源。
//        val provider = DaoAuthenticationProvider()
//        provider.setUserDetailsService(userDetailsService())
//        providers.add(provider)
//        return ProviderManager(providers)
//    }
//
//    class MagnusUsernamePasswordFilter: UsernamePasswordAuthenticationFilter()
//
//}
