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
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object JsonUtils {
    /**
     * 全局json反序列化配置
     */
    @OptIn(ExperimentalSerializationApi::class)
    val json = Json {
        encodeDefaults = true  // 启用序列化默认值
        explicitNulls = true // 反序列化null值

        /**
         * 序列化配置
         */
        serializersModule = SerializersModule {
            contextual(LocalDateTime::class, LocalDateTimeSerializer)
        }
    }

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


    /**
     * LocalData 时间序列化
     */
    object LocalDateTimeSerializer : KSerializer<LocalDateTime> {
        private val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

        override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("LocalDateTime", PrimitiveKind.STRING)

        override fun serialize(encoder: Encoder, value: LocalDateTime) {
            encoder.encodeString(value.format(formatter))
        }

        override fun deserialize(decoder: Decoder): LocalDateTime {
            return LocalDateTime.parse(decoder.decodeString(), formatter)
        }
    }

}