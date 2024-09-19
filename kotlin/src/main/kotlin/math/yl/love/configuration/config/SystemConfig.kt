package math.yl.love.configuration.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "system")
data class SystemConfig(
    val securityIgnoreUrls: List<String>? = null,
    val projectPath: String? = null,
)