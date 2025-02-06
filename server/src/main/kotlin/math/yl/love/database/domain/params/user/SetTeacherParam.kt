package math.yl.love.database.domain.params.user

import io.swagger.v3.oas.annotations.media.Schema
import kotlinx.serialization.Serializable

/**
 * 登陆接口请求 params
 */
@Serializable
data class SetTeacherParam(
    @Schema(description = "组织id")
    val departmentId: Long,

    @Schema(description = "用户id")
    val teacherId: Long,
)