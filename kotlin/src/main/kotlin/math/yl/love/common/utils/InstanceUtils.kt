package math.yl.love.common.utils

import kotlin.reflect.KClass
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor

object InstanceUtils {

    /**
     * 将一个类转换成另外一个实例
     * 前提: 第二个类所需的构造函数的参数都必须能够在T中找到
     */
    @Suppress("UNCHECKED_CAST")
    inline fun <reified T: Any, reified V: Any> T.toBean(clazz: KClass<V>): V? {
        try {
            // 创建目标类的实例
            val constructor = clazz.primaryConstructor ?: return null
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
}