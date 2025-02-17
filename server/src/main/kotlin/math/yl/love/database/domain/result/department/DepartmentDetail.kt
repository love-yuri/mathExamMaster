package math.yl.love.database.domain.result.department

import io.swagger.v3.oas.annotations.media.Schema
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.LongAsStringSerializer
import math.yl.love.database.domain.result.user.UserResult
import java.time.LocalDateTime

@Serializable
data class DepartmentDetail (
    @Serializable(with = LongAsStringSerializer::class)
    @Schema(description = "组织ID")
    val id: Long,

    @Schema(description = "组织名称")
    val name: String,

    @Schema(description = "教师信息")
    val teacherInfo: UserResult?,

    @Serializable(with = LongAsStringSerializer::class)
    @Schema(description = "父级组织ID")
    val parentId: Long?,

    @Contextual
    @Schema(description = "创建时间")
    val createTime: LocalDateTime,

    @Schema(description = "组织用户")
    val users: List<UserResult>
)