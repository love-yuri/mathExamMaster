package math.yl.love.database.domain.entity

import java.time.LocalDateTime

/**
 * 基础实体类
 * id 为自动增长
 * 创建时间-更新时间会自动注入
 */
sealed interface BaseEntity {
    var id: Long?
    val deleted: Boolean
    val createTime: LocalDateTime?
    val createBy: String?
    val updateTime: LocalDateTime?
    val updateBy: String?
}
