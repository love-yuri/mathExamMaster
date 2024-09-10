package math.yl.love.database.entity.entity

import com.baomidou.mybatisplus.annotation.*
import io.swagger.v3.oas.annotations.media.Schema
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

/**
 * 基础实体类
 * id 为自动增长
 * 创建时间-更新时间会自动注入
 */
sealed interface BaseEntity {
    val id: Long?
    val deleted: Boolean
    val createTime: LocalDateTime
    val createBy: Long
    val updateTime: LocalDateTime
}
