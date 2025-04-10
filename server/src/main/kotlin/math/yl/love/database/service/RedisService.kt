package math.yl.love.database.service

import kotlinx.serialization.ExperimentalSerializationApi
import math.yl.love.common.utils.JsonUtils.parseJson
import math.yl.love.common.utils.JsonUtils.toJson
import math.yl.love.configuration.config.SystemConfig
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import kotlinx.serialization.protobuf.ProtoBuf
import java.util.concurrent.TimeUnit
import kotlinx.serialization.decodeFromByteArray
import kotlinx.serialization.encodeToByteArray
import math.yl.love.common.base.Log.log

@Service
class RedisService(
    val redisTemplate: RedisTemplate<String, ByteArray>,
    val systemConfig: SystemConfig
) {
    
    // 删除缓存
    fun del(key: String) {
        redisTemplate.delete(key)
    }

    /**
     * 设置缓存
     * @param key key
     * @param value value
     * @param timeout 超时时间
     */
    @OptIn(ExperimentalSerializationApi::class)
    final inline fun <reified T> set(
        key: String, // key
        value: T, // value
        timeout: Long? = systemConfig.redisDefaultTimeout
    ) {
        val byteData = ProtoBuf.encodeToByteArray<T>(value)
        if (timeout == null) {
            redisTemplate.opsForValue().set(key, byteData)
        } else {
            redisTemplate.opsForValue().set(key, byteData, timeout, TimeUnit.SECONDS)
        }
    }

    /**
     * 获取缓存
     */
    @OptIn(ExperimentalSerializationApi::class)
    final inline fun <reified T> get(key: String): T? {
        val byteData = redisTemplate.opsForValue().get(key)
        return byteData?.let { ProtoBuf.decodeFromByteArray<T>(it) }
    }

    /**
     * 获取缓存中的数据。如果数据不存在，则调用指定函数重新获取。
     *
     * @param key 缓存键
     * @param fetch 数据获取的函数
     * @param timeout 缓存过期时间，默认为 `null`
     * @return 返回缓存数据
     */
    final inline fun <reified T> getOrReSet(
        key: String,
        timeout: Long? = systemConfig.redisDefaultTimeout,
        fetch: () -> T,
    ): T {
        // 尝试从缓存中获取数据
        return get<T>(key) ?: run {
            // 如果缓存中没有数据，则调用传入的获取函数并设置缓存
            val value = fetch()
            set(key, value, timeout)
            value
        }
    }
}