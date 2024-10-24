package math.yl.love.configuration.auth

import org.slf4j.LoggerFactory
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class SimplePasswordEncoder : PasswordEncoder {
    private val log = LoggerFactory.getLogger(SimplePasswordEncoder::class.java)
    override fun encode(rawPassword: CharSequence): String {
        return rawPassword.toString()
    }

    override fun matches(rawPassword: CharSequence, encodedPassword: String): Boolean {
        log.info("yuri is yes")
        return rawPassword.toString() == encodedPassword
    }
}
