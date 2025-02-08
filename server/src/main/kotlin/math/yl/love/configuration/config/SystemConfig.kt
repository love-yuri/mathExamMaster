package math.yl.love.configuration.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "system")
data class SystemConfig(
    val projectPath: String? = null,
    val uploadPath: String? = null,

    /**
     * redis里用户信息的过期时间 单位s
     * 默认 6 小时
     */
    val userInfoTimeout: Long = 60 * 60 * 6,

    /**
     * redis里默认信息的过期时间 单位s
     * 默认 6 小时
     */
    val redisDefaultTimeout: Long = 60 * 60 * 6,
)