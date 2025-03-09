package math.yl.love

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromByteArray
import kotlinx.serialization.encodeToByteArray
import kotlin.test.Test
import kotlinx.serialization.protobuf.ProtoBuf
import math.yl.love.database.domain.entity.User
import math.yl.love.database.domain.result.user.UserInfo
import math.yl.love.database.domain.result.user.UserResult
import math.yl.love.database.service.UserService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.redis.core.RedisTemplate
import java.io.ByteArrayOutputStream
import java.io.ObjectOutputStream

@SpringBootTest
class ProtobufTest {
    private val log = LoggerFactory.getLogger(ProtobufTest::class.java)

    @Autowired
    private lateinit var redisTemplate: RedisTemplate<String, ByteArray>

    @Autowired
    private lateinit var userService: UserService

    @OptIn(ExperimentalSerializationApi::class)
    @Test
    fun encodeTest() {
        val user = userService.getById(1)

        val encode = ProtoBuf.encodeToByteArray(user)
        redisTemplate.opsForValue().set("yuri:test", encode)
        log.info("encode: ${encode.size}")
        val encode2 = redisTemplate.opsForValue().get("yuri:test")
        val newSample = ProtoBuf.decodeFromByteArray<User>(encode2!!)

        log.info("newSample: $newSample")
    }
}