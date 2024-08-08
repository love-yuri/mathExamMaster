package math.yl.love

import math.yl.love.configuration.config.SystemConfig
import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(SystemConfig::class)
@MapperScan("math.yl.love.database.mapper")
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
