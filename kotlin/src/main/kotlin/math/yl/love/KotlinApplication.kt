package math.yl.love

import math.yl.love.configuration.SystemConfig
import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(SystemConfig::class)
@MapperScan("math.yl.love.database.mapper")
open class KotlinApplication

fun main(args: Array<String>) {
    runApplication<KotlinApplication>(*args)
}
