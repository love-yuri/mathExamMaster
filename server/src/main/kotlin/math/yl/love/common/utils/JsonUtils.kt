package math.yl.love.common.utils


import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encodeToString
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import math.yl.love.configuration.config.JsonConfig
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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