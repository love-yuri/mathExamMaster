package math.yl.love

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.apache.catalina.webresources.TomcatURLStreamHandlerFactory.disable
import org.junit.jupiter.api.Test

class JsonTest {

    data class JsonTest(
        val id: Int,
        val dateTime: LocalDateTime,
    )

    @Test
    fun test() {
        val mapper = ObjectMapper().apply {
            registerModule(KotlinModule.Builder().build())
            registerModule(JavaTimeModule())
            disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        }

        val testObject = JsonTest(id = 1, dateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()))
        val json = mapper.writeValueAsString(testObject)
        println(json)  // 输出 JSON，检查是否包含 LocalDateTime 字段
    }
}