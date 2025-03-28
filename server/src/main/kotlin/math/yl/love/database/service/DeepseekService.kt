package math.yl.love.database.service

import io.github.pigmesh.ai.deepseek.core.DeepSeekClient
import io.github.pigmesh.ai.deepseek.core.chat.ChatCompletionRequest
import math.yl.love.common.utils.JsonUtils.parseJson
import math.yl.love.database.domain.entity.AiChatRecord
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DeepseekService(
    private val deepSeekClient: DeepSeekClient,
    private val aiChatRecordService: AiChatRecordService
) {

    /**
     * 和deepseek对话
     * @param msg 对话消息
     */
    fun chat(msg: String) : String {
        val request: ChatCompletionRequest = ChatCompletionRequest.builder()
            .model("deepseek-chat")
            .addUserMessage(msg).build()

        val res = deepSeekClient.chatCompletion(request).execute().content()

        aiChatRecordService.save(
            AiChatRecord(
                question = msg,
                answer = res
            )
        )

        return res
    }
}