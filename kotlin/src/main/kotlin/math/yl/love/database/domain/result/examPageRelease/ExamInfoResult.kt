package math.yl.love.database.domain.result.examPageRelease

import com.baomidou.mybatisplus.annotation.TableField
import io.swagger.v3.oas.annotations.media.Schema
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.LongAsStringSerializer
import math.yl.love.common.base.NoArg
import math.yl.love.configuration.config.JsonConfig
import math.yl.love.database.domain.typeEnum.ExamPageStatusEnum
import java.time.LocalDateTime

@Serializable
@NoArg
data class ExamInfoResult (
    @Schema(description = "发布id")
    @Serializable(with = LongAsStringSerializer::class)
    val releaseId: Long,

    @Schema(description = "试卷id")
    @Serializable(with = LongAsStringSerializer::class)
    val examPageId: Long,

    @Schema(description = "考试状态")
    @Serializable(with = JsonConfig.ExamPageStatusSerializer::class)
    val status: ExamPageStatusEnum,

    @Contextual
    @Schema(description = "考试开始时间")
    val startTime: LocalDateTime,

    @Contextual
    @Schema(description = "考试结束时间")
    val endTime: LocalDateTime,

    @Contextual
    @Schema(description = "开始考试时间")
    val examStartTime: LocalDateTime? = null,

    @Schema(description = "试卷的难度，默认5，最高9")
    @TableField(value = "difficulty")
    val difficulty: Int,

    @Schema(description = "试卷限时（秒），默认7200秒（2小时）")
    @TableField(value = "limited_time")
    val limitedTime: Int,

    @Schema(description = "试卷科目，默认0: 高等数学")
    @TableField(value = "subject")
    val subject: Int,

    @Schema(description = "试卷的标题")
    @TableField(value = "title")
    val title: String,

    @Schema(description = "试卷总分，默认100")
    @TableField(value = "total_score")
    val totalScore: Int,

    @Schema(description = "试卷的类型，默认0: 普通试卷")
    @TableField(value = "type")
    val type: Int,
)