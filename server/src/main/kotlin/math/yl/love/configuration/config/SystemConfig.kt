package math.yl.love.configuration.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "system")
data class SystemConfig(
    val securityIgnoreUrls: List<String>? = null,
    val projectPath: String? = null,
    val uploadPath: String? = null,

    /**
     * redis里用户信息的过期时间 单位s
     * 默认 1 天
     */
    val userInfoTimeout: Long = 60 * 60 * 24,

    /**
     * redis里默认信息的过期时间 单位s
     * 默认 1 天
     */
    val redisDefaultTimeout: Long = 60 * 60 * 24,

    /**
     * 是否为快速开发环境，如果是将会取消token验证
     */
    val isYuriDevelop: Boolean? = false,
)