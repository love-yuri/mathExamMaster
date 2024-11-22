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
import math.yl.love.common.base.Log.log
import org.apache.ibatis.type.LocalDateTimeTypeHandler
import org.slf4j.LoggerFactory
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
            val str = decoder.decodeString()
            if (str.contains("T")) {
                return LocalDateTime.parse(str, formatter)
            }
            return LocalDateTime.parse(str, DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"))
        }
    }

    @Bean
    fun objectMapper(): ObjectMapper {
        val mapper = ObjectMapper()
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        return mapper
    }
}