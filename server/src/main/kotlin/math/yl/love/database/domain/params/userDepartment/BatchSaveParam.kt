package math.yl.love.database.domain.params.userDepartment

import io.swagger.v3.oas.annotations.media.Schema
import kotlinx.serialization.Serializable

@Serializable
data class BatchSaveParam(
    @Schema(description = "组织id") val departmentId: Long,
    @Schema(description = "用户") val userIds: List<Long>,
)