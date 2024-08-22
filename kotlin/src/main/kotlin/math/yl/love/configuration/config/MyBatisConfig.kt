package math.yl.love.configuration.config

import org.mybatis.spring.annotation.MapperScan
import org.springframework.context.annotation.Configuration

@Configuration
@MapperScan("math.yl.love.database.mapper")
class MyBatisConfig {
}