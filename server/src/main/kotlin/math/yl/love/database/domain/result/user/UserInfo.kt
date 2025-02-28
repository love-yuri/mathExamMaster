package math.yl.love.database.domain.result.user

import io.swagger.v3.oas.annotations.media.Schema
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.LongAsStringSerializer
import math.yl.love.database.domain.typeEnum.UserRoleEnum

@Serializable
data class UserInfo (
    @Schema(description = "主键id")
    @Serializable(with = LongAsStringSerializer::class)
    var id: Long? = null,

    @Schema(description = "用户角色")
    val role: UserRoleEnum = UserRoleEnum.STUDENT,

    @Schema(description = "用户名")
    val username: String,

    @Schema(description = "昵称")
    val nickname: String,

    @Schema(description = "主页目录")
    val homePath: String = "",
)