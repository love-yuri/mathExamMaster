package math.yl.love.common.utils


import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import math.yl.love.configuration.config.JsonConfig

object JsonUtils {

    val json: Json get() = JsonConfig.json

    /**
     * 将任何数据转为json
     * 前提是需要添加 @Serializable 注解
     */
    inline fun <reified T> T.toJson() = json.encodeToString(this)

    /**
     * 解析字符串成json
     * 待解析的类需要添加注解
     */
    inline fun <reified T> String.parseJson() = json.decodeFromString<T>(this)
}