package math.yl.love

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*
import kotlin.test.Test

/**
 * jwt token的创建和解析测试
 * 如果不知道这是什么东西 可以看👇
 * https://blog.csdn.net/Zong_0915/article/details/127714702
 */
class JwtTest {
    private val log: Logger = LoggerFactory.getLogger(javaClass)

    // 秘钥，你可以随便取，可以取的难一点
    private val secret: String = "ASD!@#F^%A"

    /**
     * 创建token测试
     */
    @Test
    fun tokenCreteTest() {
        val headers = HashMap<String, Any>()
        // 过期时间，60s
        val expires = Calendar.getInstance().apply {
            add(Calendar.SECOND, 600)
        }

        val jwtToken = JWT.create() // 第一部分Header
            .withHeader(headers) // 第二部分Payload
            .withClaim("userId", 20)
            .withClaim("userName", "夏亦寒")
            .withExpiresAt(expires.time) // 第三部分Signature
            .sign(Algorithm.HMAC256(secret))

        log.info(jwtToken)
    }

    /**
     * 解析token测试
     */
    @Test
    fun verifyTokenTest() {
        val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOjIwLCJ1c2VyTmFtZSI6IuWkj-S6puWvkiIsImV4cCI6MTcyMzQ4MDE2NH0.F9FMmkGUGNTrWsv26ySOdg24BbxQNWS7zIMT1S0kl4M"
        val jwtVerifier = JWT.require(Algorithm.HMAC256(secret)).build();
        val verify = jwtVerifier.verify(token);
        log.info("""
            userId: ${verify.getClaim("userId").asString()}
            userName: ${verify.getClaim("userName").asString()}
            过期时间: ${verify.expiresAt} 
        """.trimIndent())
    }
}