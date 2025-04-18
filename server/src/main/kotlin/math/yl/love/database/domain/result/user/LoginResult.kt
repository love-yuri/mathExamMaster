package math.yl.love.database.domain.result.user

import com.baomidou.mybatisplus.annotation.*
import io.swagger.v3.oas.annotations.media.Schema
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlinx.serialization.builtins.LongAsStringSerializer
import math.yl.love.common.constant.DataBaseConstant
import math.yl.love.database.domain.entity.User
import math.yl.love.database.domain.typeEnum.UserRoleEnum
import java.time.LocalDateTime

@Serializable
data class LoginResult(
    @Schema(description = "主键id")
    @Serializable(with = LongAsStringSerializer::class)
    var id: Long? = null,

    @Schema(description = "用户角色")
    val role: UserRoleEnum = UserRoleEnum.STUDENT,

    @Schema(description = "token")
    val token: String,
)