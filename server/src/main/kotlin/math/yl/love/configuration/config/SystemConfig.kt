package math.yl.love.configuration.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "system")
data class SystemConfig(
    val securityIgnoreUrls: List<String>? = null,
    val projectPath: String? = null,
    val uploadPath: String? = null,

    /**
     * 是否为快速开发环境，如果是将会取消token验证
     */
    val isYuriDevelop: Boolean? = false,
)