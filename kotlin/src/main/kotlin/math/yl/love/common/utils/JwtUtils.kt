package math.yl.love.common.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import java.util.*


/**
 * Jwt的工具 + 配置合集
 * 因为用配置文件配置太过分散，所以配置就放到了这里
 */
object JwtUtils {
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
     * @param claim 需要传递的参数
     * @param expires 默认过期时间，单位s
     */
    fun createToken(claim: Map<String, Any>, expires: Int = DEFAULT_EXPIRES): String {
        return JWT.create().apply {
            withHeader(headers) // 第一部分的headers

            // 第二部分Payload
            claim.forEach { (key, value) ->
                when (value) {
                    is Int -> withClaim(key, value)
                    is String -> withClaim(key, value)
                    is Boolean -> withClaim(key, value)
                    is Date -> withClaim(key, value)
                    is Double -> withClaim(key, value)
                    is Long -> withClaim(key, value)
                    // 添加其他类型的处理（如果有需要）
                    else -> throw IllegalArgumentException("该类型不在序列化列表中: $key")
                }
            }

            // 过期时间
            withExpiresAt(Calendar.getInstance().let {
                it.add(Calendar.SECOND, expires)
                it.time
            })

        }.sign(Algorithm.HMAC256(SECRET))
    }
}