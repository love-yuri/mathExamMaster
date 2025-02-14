package math.yl.love.database.domain.result.examPageRelease

import com.baomidou.mybatisplus.annotation.*
import io.swagger.v3.oas.annotations.media.Schema
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlinx.serialization.builtins.LongAsStringSerializer
import math.yl.love.common.constant.DataBaseConstant
import math.yl.love.database.domain.entity.Department
import math.yl.love.database.domain.entity.ExamPage
import math.yl.love.database.domain.entity.User
import java.time.LocalDateTime

@Serializable
data class ExamPageReleaseResult(
    @Schema(description = "主键id")
    @Serializable(with = LongAsStringSerializer::class)
    val id: Long,

    @Contextual
    @Schema(description = "创建时间")
    val createTime: LocalDateTime? = null,

    @Schema(description = "试卷信息")
    val examPage: ExamPage,

    @Contextual
    @Schema(description = "开始时间")
    val startTime: LocalDateTime,

    @Contextual
    @Schema(description = "结束时间")
    val endTime: LocalDateTime,

    @Schema(description = "所有发布的班级")
    val department: Department
)