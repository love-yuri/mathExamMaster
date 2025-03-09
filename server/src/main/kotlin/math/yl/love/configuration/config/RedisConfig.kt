package math.yl.love.configuration.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.RedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class RedisConfig {

    @Bean
    fun bytesRedisTemplate(connectionFactory: RedisConnectionFactory?): RedisTemplate<String, ByteArray> {
        val redisTemplate = RedisTemplate<String, ByteArray>()
        redisTemplate.connectionFactory = connectionFactory
        // 设置key和value的序列化规则
        redisTemplate.valueSerializer = RedisSerializer.byteArray()
        redisTemplate.keySerializer = StringRedisSerializer()
        redisTemplate.afterPropertiesSet()

        return redisTemplate
    }
}