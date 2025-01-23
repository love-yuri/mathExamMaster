package math.yl.love.database.service

import math.yl.love.common.constant.RedisConstant
import math.yl.love.common.utils.JsonUtils.json
import math.yl.love.common.utils.JsonUtils.parseJson
import math.yl.love.common.utils.JsonUtils.toJson
import math.yl.love.configuration.config.SystemConfig
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

@Service
class RedisService(
    val stringRedisTemplate: StringRedisTemplate,
    val systemConfig: SystemConfig
) {
    /**
     * 设置缓存
     */
    final inline fun <reified T> set(
        key: String, // key
        value: T, // value
        timeout: Long = systemConfig.redisDefaultTimeout
    ) {
        stringRedisTemplate.opsForValue().set(key, value.toJson(), timeout, TimeUnit.SECONDS)
    }

    /**
     * 获取缓存
     */
    final inline fun <reified T> get(key: String): T? {
        return stringRedisTemplate.opsForValue().get(key)?.parseJson()
    }
}