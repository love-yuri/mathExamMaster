package math.yl.love

import math.yl.love.configuration.config.SystemConfig
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import java.net.InetAddress


@EnableConfigurationProperties(SystemConfig::class)
@SpringBootApplication(exclude = [JacksonAutoConfiguration::class])
class Application

fun main(args: Array<String>) {
    val log = LoggerFactory.getLogger(Application::class.java)

    val application = runApplication<Application>(*args)
    val env = application.environment

    val host = InetAddress.getLocalHost().hostAddress
    val port = env.getProperty("server.port", "8080")

    log.info("""
        程序启动成功，访问api文档: http://$host:$port/doc.html
    """.trimIndent())

}
