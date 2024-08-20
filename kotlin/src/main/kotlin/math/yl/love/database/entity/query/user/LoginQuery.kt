package math.yl.love.database.entity.query.user

import io.swagger.v3.oas.annotations.media.Schema

/**
 * 登陆接口请求 query
 */
data class LoginQuery(
    @Schema(description = "用户名") val id: Long,
    @Schema(description = "密码") val password: String
)