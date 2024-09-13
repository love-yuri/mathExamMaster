package math.yl.love.database.service

import math.yl.love.database.entity.query.system.params.GenerateParam
import math.yl.love.database.mapper.SystemMapper
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.RequestBody

@Service
@Transactional(readOnly = true)
class SystemService(
    private val autoMysqlService: AutoMysqlService
) {
    fun generate(param: GenerateParam) = autoMysqlService.generate(param)
}