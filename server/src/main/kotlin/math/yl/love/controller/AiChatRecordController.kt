package math.yl.love.controller

import io.swagger.v3.oas.annotations.tags.Tag
import math.yl.love.common.mybatis.BaseController
import math.yl.love.database.domain.entity.AiChatRecord
import math.yl.love.database.mapper.AiChatRecordMapper
import math.yl.love.database.service.AiChatRecordService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/ai/chat/record")
@Tag(name = "AI对话记录表")
class AiChatRecordController: BaseController<AiChatRecord, AiChatRecordMapper, AiChatRecordService>()