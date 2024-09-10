package math.yl.love.database.mapper

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import math.yl.love.database.entity.entity.User
import math.yl.love.database.entity.result.system.ColumnInfo
import org.apache.ibatis.annotations.Select

interface SystemMapper: BaseMapper<User> {
    @Select("""
        SELECT 
            COLUMN_NAME AS `field`, 
            COLUMN_TYPE AS `type`, 
            COLUMN_COMMENT AS `comment` 
        FROM 
            INFORMATION_SCHEMA.COLUMNS 
        WHERE 
            TABLE_SCHEMA = #{schema} 
            AND TABLE_NAME = #{tableName};
    """)
    fun getColumnInfo(schema: String, tableName: String): List<ColumnInfo>

    @Select("""
        SELECT
            TABLE_COMMENT
        FROM
            INFORMATION_SCHEMA.TABLES
        WHERE
            TABLE_SCHEMA = #{schema} 
            AND TABLE_NAME = #{tableName};
    """)
    fun getTableComment(schema: String, tableName: String): String

}