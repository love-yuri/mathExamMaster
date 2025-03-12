package math.yl.love

import io.github.pigmesh.ai.deepseek.core.DeepSeekClient
import io.github.pigmesh.ai.deepseek.core.chat.ChatCompletionRequest
import math.yl.love.common.base.Log.log
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.Test


@SpringBootTest
class DeepseekTest {

    @Autowired
    private val deepSeekClient: DeepSeekClient? = null

    @Test
    fun chatTest() {
        val request: ChatCompletionRequest = ChatCompletionRequest.builder()
            .model("deepseek-chat")
            .addUserMessage("你好").build()

        val msg = deepSeekClient!!.chatCompletion(request).execute()
        log.info("msg ${msg.content()}")
    }

}