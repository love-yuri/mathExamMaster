package math.yl.love.configuration.auth

import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.stereotype.Component

@Component
class SimpleAuthenticationProvider(
    private val loginAuth: LoginAuth,
    private val passwordEncoder: SimplePasswordEncoder
) : AuthenticationProvider {

    @Throws(AuthenticationException::class)
    override fun authenticate(authentication: Authentication): Authentication? {
        val username = authentication.name
        val password = authentication.credentials.toString()
        val user = loginAuth.loadUserByUsername(username)

        if (passwordEncoder.matches(password, user.password)) {
            return UsernamePasswordAuthenticationToken(
                user, password, user.authorities
            )
        }
        throw RuntimeException("密码错误!!!")
    }

    override fun supports(authentication: Class<*>): Boolean {
        return UsernamePasswordAuthenticationToken::class.java.isAssignableFrom(authentication)
    }
}
