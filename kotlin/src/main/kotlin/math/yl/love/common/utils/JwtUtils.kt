package math.yl.love.common.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import math.yl.love.common.utils.JsonUtils.toJson
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.jvm.isAccessible


/**
 * Jwt的工具 + 配置合集
 * 因为用配置文件配置太过分散，所以配置就放到了这里
 */
object JwtUtils {

    private val log: Logger = LoggerFactory.getLogger(javaClass)

    /**
     * header 配置，使用加密算法
     */
    private val headers = mapOf(
        "alg" to "HS256",
        "typ" to "JWT"
    )

    /**
     * 密钥配置，这个最好不要太过简单
     */
    private const val SECRET = "#-#love-yuri#-#"

    /**
     * 默认过期时间配置
     * 默认是8h
     */
    private const val DEFAULT_EXPIRES = 60 * 60 * 8

    /**
     * 创建jwtToken
     * @param dataClassInstance 需要传递的类实例
     * @param expires 默认过期时间，单位s
     * 该类必须是data class!!
     * 该类必须是data class!!
     * 该类必须是data class!!
     */
    fun <T : Any> createToken(dataClassInstance: T, expires: Int = DEFAULT_EXPIRES): String {
        return JWT.create().apply {
            // 第一部分的headers
            withHeader(headers)

            // 第二部分Payload
            dataClassInstance::class.declaredMemberProperties.forEach { property ->
                @Suppress("UNCHECKED_CAST")
                val value = (property as KProperty1<T, Any?>).get(dataClassInstance)

                if (value != null) {
                    when (value) {
                        is Int -> withClaim(property.name, value)
                        is String -> withClaim(property.name, value)
                        is Boolean -> withClaim(property.name, value)
                        is Date -> withClaim(property.name, value)
                        is Double -> withClaim(property.name, value)
                        is Long -> withClaim(property.name, value)
                        else -> throw IllegalArgumentException("该类型不在序列化列表中: ${property.name}")
                    }
                }
            }

            // 过期时间
            withExpiresAt(Calendar.getInstance().let {
                it.add(Calendar.SECOND, expires)
                it.time
            })

        }.sign(Algorithm.HMAC256(SECRET))
    }

    /**
     * 解析token 并返回相应的对象属性
     * 如果需要支持更多的类型，请自行添加
     * 该类必须是data class!!
     * 该类必须是data class!!
     * 该类必须是data class!!
     */
    fun <T : Any> verifyTokenAndParse(token: String, clazz: KClass<T>): T? {
        val jwtVerifier = JWT.require(Algorithm.HMAC256(SECRET)).build()
        val decodedJWT = jwtVerifier.verify(token)

        val constructor = clazz.primaryConstructor ?: return null
        val params = constructor.parameters.associateWith { param ->
            val name = param.name ?: return@associateWith null
            val claim = decodedJWT.getClaim(name)

            // 根据字段类型来进行相应的转换
            val property = clazz.declaredMemberProperties.find { it.name == name } ?: return@associateWith null
            property.isAccessible = true

            when (property.returnType.classifier) {
                String::class -> claim.asString()
                Int::class -> claim.asInt()
                Boolean::class -> claim.asBoolean()
                Long::class -> claim.asLong()
                Double::class -> claim.asDouble()
                else -> null
            }
        }

        return constructor.callBy(params)
    }
}