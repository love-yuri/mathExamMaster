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
     * @param key key
     * @param value value
     * @param timeout 超时时间
     */
    final inline fun <reified T> set(
        key: String, // key
        value: T, // value
        timeout: Long? = systemConfig.redisDefaultTimeout
    ) {
        if (timeout == null) {
            stringRedisTemplate.opsForValue().set(key, value.toJson())
        } else {
            stringRedisTemplate.opsForValue().set(key, value.toJson(), timeout, TimeUnit.SECONDS)
        }
    }

    /**
     * 获取缓存
     */
    final inline fun <reified T> get(key: String): T? {
        return stringRedisTemplate.opsForValue().get(key)?.parseJson()
    }

    /**
     * 获取缓存里的数据，如果不存在则调用函数重新获取
     * @param f 获取数据的函数 返回许哟啊获取的数据
     * @param timeout 过期时间，默认为null
     */
    final inline fun <reified T> getOrReSet(key: String, f: () -> T, timeout: Long? = systemConfig.redisDefaultTimeout): T {
        val data = get<T>(key)
        if (data != null) {
            return data
        }
        val value = f()
        set(key, value, timeout)
        return value
    }
}