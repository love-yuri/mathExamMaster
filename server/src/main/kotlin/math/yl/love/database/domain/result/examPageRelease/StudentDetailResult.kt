package math.yl.love.database.domain.result.examPageRelease

import com.baomidou.mybatisplus.annotation.TableField
import io.swagger.v3.oas.annotations.media.Schema
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.LongAsStringSerializer
import math.yl.love.common.base.NoArg
import java.time.LocalDateTime

@NoArg
@Serializable
data class StudentDetailResult (
    @Serializable(with = LongAsStringSerializer::class)
    @Schema(description = "用户id")
    @TableField("user_id")
    val userId: Long,

    @TableField("user_name")
    @Schema(description = "用户名称")
    val username: String,

    @TableField("nick_name")
    @Schema(description = "昵称")
    val nickname: String,

    @Serializable(with = LongAsStringSerializer::class)
    @Schema(description = "用户发布id")
    @TableField("relation_id")
    val relationId: Long,

    @TableField("has_grading")
    @Schema(description = "是否已经阅卷")
    val hasGrading: Boolean
)