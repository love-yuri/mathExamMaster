package math.yl.love.base.utils


import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object JsonUtils {
    /**
     * 全局json反序列化配置
     */
    @OptIn(ExperimentalSerializationApi::class)
    val json = Json {
        encodeDefaults = true  // 启用序列化默认值
        explicitNulls = true // 反序列化null值
    }

    /**
     * 任何数据转为json
     */
    inline fun <reified T> T.toJson() = json.encodeToString(this)

    /**
     * 解析字符串成json
     */
    inline fun <reified T> String.parseJson() = json.decodeFromString<T>(this)
}