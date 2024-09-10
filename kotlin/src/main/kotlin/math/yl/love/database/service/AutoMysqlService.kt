package math.yl.love.database.service

import math.yl.love.database.mapper.SystemMapper
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

/**
 * MySql 自动生成代码service
 *
 */
@Service
@Transactional(readOnly = true)
class AutoMysqlService(
    private val systemMapper: SystemMapper
) {

    private val log = LoggerFactory.getLogger(javaClass)

    /**
     * 数据库类型转kotlin type
     */
    fun String.toKotlinType(): String {
        return when {
            startsWith("bigint") -> "Long"
            startsWith("int") -> "Int"
            startsWith("tinyint") -> "Byte"
            startsWith("varchar") || startsWith("text") -> "String"
            startsWith("datetime") || startsWith("timestamp") -> "LocalDateTime"
            startsWith("float") -> "Float"
            startsWith("double") -> "Double"
            startsWith("decimal") -> "BigDecimal"
            startsWith("boolean") -> "Boolean"
            else -> "String"
        }
    }

    /**
     * 数据库下划线命名转小驼峰命名
     */
    fun String.snakeToCamel(): String {
        return this.split("_")
            .mapIndexed { index, part ->
                if (index == 0) {
                    part.lowercase() // 保持第一个部分小写
                } else {
                    part.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() } // 其余部分首字母大写
                }
            }
            .joinToString("")
    }


    /**
     * 生成代码测试
     */
    fun generate(): String {
        val res = systemMapper.getColumnInfo("zyl", "user")
        res.forEach {
            log.info(" 表名: ${it.field} 类型: ${it.type} 注释: ${it.comment} 命名: ${it.field.snakeToCamel()}")
        }
        return ""
    }

}