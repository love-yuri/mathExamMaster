package math.yl.love.database.service

import io.github.pigmesh.ai.deepseek.core.DeepSeekClient
import io.github.pigmesh.ai.deepseek.core.chat.ChatCompletionRequest
import math.yl.love.common.utils.JsonUtils.parseJson
import org.springframework.stereotype.Service

@Service
final class DeepseekService(
    private val deepSeekClient: DeepSeekClient
) {

    /**
     * 和deepseek对话
     * @param msg 对话消息
     */
    fun chat(msg: String) : String {
        val request: ChatCompletionRequest = ChatCompletionRequest.builder()
            .model("deepseek-chat")
            .addUserMessage(msg).build()

        return deepSeekClient.chatCompletion(request).execute().content()
    }
}