package math.yl.love.database.service

import math.yl.love.database.domain.params.system.GenerateParam
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class SystemService(
    private val autoMysqlService: AutoMysqlService
) {
    fun generate(param: GenerateParam) = autoMysqlService.generate(param)
}