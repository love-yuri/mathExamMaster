package math.yl.love

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import math.yl.love.common.base.Log.log
import math.yl.love.common.utils.JsonUtils.toJson
import math.yl.love.database.domain.entity.User
import java.time.LocalDateTime
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor
import kotlin.test.Test
import kotlin.test.fail

/**
 * kotlin 反射测试工具
 */
class ReflectionTest {

    @Serializable
    data class Uu(
        val id: Long = 22,
        val name: String = "",

        @Contextual
        val createdTime: LocalDateTime? = null,
    )

    @Suppress("UNCHECKED_CAST")
    private fun <T: Any, V: Any> T.toBean(clazz: KClass<V>): V? {
        try {
            // 创建目标类的实例
            val constructor = clazz.primaryConstructor ?: fail("不存在默认构造函数!")
            val params = constructor.parameters.mapNotNull { param ->
                // 查找与参数名称匹配的属性
                this::class.memberProperties.firstOrNull { p -> p.name == param.name }?.let { prop ->
                    (prop as KProperty1<T, *>).get(this)
                }?.let { param to it }
            }.toMap()
            return constructor.callBy(params)
        } catch (e: Exception) {
            return null
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T: Any> T.iter(): T {
        val constructor = this::class.primaryConstructor ?: fail("不存在默认构造函数!")
        this::class.createInstance()

        constructor.parameters.map {
            val value = this::class.memberProperties.first { p -> p.name == it.name }.let { param ->
                (param as KProperty1<T, *>).get(this)
            }
            log.info("yuri: key: ${it.name} $value")
        }
        this::class.memberProperties.forEach {
            val value = (it as KProperty1<T, *>).get(this)
            log.info("yuri: name: ${it.name}, type: ${it.returnType} value: $value")
        }
        return constructor.callBy(mapOf())
    }
}