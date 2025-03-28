package math.yl.love.database.service

import math.yl.love.common.mybatis.BaseService
import math.yl.love.database.domain.entity.AiChatRecord
import math.yl.love.database.mapper.AiChatRecordMapper
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.reflect.KClass

@Service
@Transactional(readOnly = true)
class AiChatRecordService: BaseService<AiChatRecord, AiChatRecordMapper>() 