package math.yl.love

import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@SpringBootTest
class KotlinApplicationTests(

) {
    private val log = LoggerFactory.getLogger(KotlinApplicationTests::class.java)

    /**
     * 测试密码序列化器
     */
    @Test
    fun testPasswordEncode() {
        val encoder = BCryptPasswordEncoder()
        val testPwd = "yuri"
        val encoded = encoder.encode(testPwd)
        log.info("序列化后的结果: $encoded")
        assert(encoder.matches(testPwd, encoded)) {
            "密码不匹配"
        }
        log.info("检测密码是否匹配： ${encoder.matches(testPwd, encoded)}")
    }

}
