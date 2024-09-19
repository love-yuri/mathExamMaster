package math.yl.love.common.utils

import java.util.*
import kotlin.String

/**
 * 字符串工具箱
 */
object StrUtils {
    /**
     * 将下划线命名转小驼峰命名
     * @param lower 是否是小驼峰，默认返回小驼峰命名法
     */
    fun String.snakeToCamel(lower: Boolean = true): String {
        return this.split("_")
            .mapIndexed { index, part ->
                if (index == 0) {
                    if (lower) {
                        part.lowercase()
                    } else {
                        part.lowercase().replaceFirstChar { it.uppercaseChar() }
                    }
                } else {
                    // 其余部分首字母大写
                    part.replaceFirstChar { it.uppercaseChar() }
                }
            }
            .joinToString("")
    }

}