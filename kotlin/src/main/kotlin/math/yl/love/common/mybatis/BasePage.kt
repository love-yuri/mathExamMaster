package math.yl.love.common.mybatis

import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
class BasePage<T>(
    @Transient
    val page: Page<T> = Page()
) {
    val current: Long = page.current
    val size: Long = page.size
    val records: List<T> = page.records
    val total: Long = page.total

    constructor(current: Long, size: Long, records: List<T>, total: Long) : this(Page<T>(current, size).apply {
        this.records = records
        this.total = total
    })
}

