package math.yl.love

import io.github.pigmesh.ai.deepseek.core.DeepSeekClient
import io.github.pigmesh.ai.deepseek.core.chat.ChatCompletionRequest
import math.yl.love.common.base.Log.log
import math.yl.love.database.domain.params.system.AiCreateQuestionParam
import math.yl.love.database.domain.typeEnum.QuestionTypeEnum
import math.yl.love.database.service.SystemService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.Test


@SpringBootTest
class DeepseekTest {

    @Autowired
    private val deepSeekClient: DeepSeekClient? = null

    @Autowired
    private lateinit var systemService: SystemService

    @Test
    fun chatTest() {
        val request: ChatCompletionRequest = ChatCompletionRequest.builder()
            .model("deepseek-chat")
            .addUserMessage("你好").build()

        val msg = deepSeekClient!!.chatCompletion(request).execute()
        log.info("msg ${msg.content()}")
    }

    @Test
    fun createTest() {
        val res = systemService.aiCreateQuestion(AiCreateQuestionParam(
            type = QuestionTypeEnum.SUBJECTIVE,
            description = "困难一点"
        ))
        log.info("$res")
    }

}