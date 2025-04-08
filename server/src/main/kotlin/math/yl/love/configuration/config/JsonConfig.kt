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
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import math.yl.love.common.mybatis.typeHandler.QuestionAnswerTypeHandler
import math.yl.love.database.domain.result.questionBank.QuestionAnswer
import math.yl.love.database.domain.result.questionBank.SingleChoiceAnswer
import math.yl.love.database.domain.typeEnum.ExamPageStatusEnum
import org.apache.ibatis.type.LocalDateTimeTypeHandler
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import java.time.LocalDateTime
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
                polymorphic(QuestionAnswer::class) {
                    subclass(SingleChoiceAnswer::class)
                }
            }
        }
    }

    override fun customize(configuration: MybatisConfiguration?) {
        configuration?.typeHandlerRegistry?.register(LocalDateTimeTypeHandler::class.java)
        configuration?.typeHandlerRegistry?.register(QuestionAnswerTypeHandler::class.java)
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
            return LocalDateTime.parse(str, DateTimeFormatter.ofPattern("yyyy/M/d HH:mm:ss"))
        }
    }

    /**
     * ExamPageStatusEnum 序列化器
     */
    object ExamPageStatusSerializer : KSerializer<ExamPageStatusEnum> {
        override val descriptor: SerialDescriptor get() = PrimitiveSerialDescriptor("ExamPageStatusEnum", PrimitiveKind.STRING)

        override fun deserialize(decoder: Decoder): ExamPageStatusEnum {
            return ExamPageStatusEnum.valueOf(decoder.decodeString())
        }

        override fun serialize(encoder: Encoder, value: ExamPageStatusEnum) {
            return encoder.encodeString(value.value.toString())
        }

    }

    @Bean
    open fun objectMapper(): ObjectMapper {
        val mapper = ObjectMapper()
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        return mapper
    }
}