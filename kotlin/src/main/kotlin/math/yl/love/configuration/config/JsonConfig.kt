package math.yl.love.configuration.config

import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer
import com.baomidou.mybatisplus.core.MybatisConfiguration
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import org.apache.ibatis.type.LocalDateTimeTypeHandler
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter


@Configuration
@Import(JacksonAutoConfiguration::class)
class JsonConfig : ConfigurationCustomizer {

    companion object {
        /**
         * 全局json反序列化配置
         */
        @OptIn(ExperimentalSerializationApi::class)
        val json = Json {
            encodeDefaults = true  // 启用序列化默认值
            explicitNulls = true // 反序列化null值
            allowStructuredMapKeys = true
            ignoreUnknownKeys = true // 忽略未知字段
            serializersModule = SerializersModule {
                contextual(LocalDateTime::class, LocalDateTimeSerializer)
            }
        }
    }

    override fun customize(configuration: MybatisConfiguration?) {
        configuration?.typeHandlerRegistry?.register(LocalDateTimeTypeHandler::class.java)
    }

    /**
     * java LocalDateTime 序列化器
     */
    object LocalDateTimeSerializer : KSerializer<LocalDateTime> {
        private val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

        override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("LocalDateTime", PrimitiveKind.STRING)

        override fun serialize(encoder: Encoder, value: LocalDateTime) {
            encoder.encodeString(value.format(formatter))
        }

        override fun deserialize(decoder: Decoder): LocalDateTime {
            val formatter = DateTimeFormatter.ISO_DATE_TIME
            return ZonedDateTime.parse(decoder.decodeString(), formatter).toLocalDateTime()
        }
    }

    @Bean
    fun objectMapper(): ObjectMapper {
        val mapper = ObjectMapper()
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        return mapper
    }
}