package math.yl.love.database.mapper

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import math.yl.love.database.domain.entity.User
import math.yl.love.database.domain.result.system.ColumnInfoResult
import org.apache.ibatis.annotations.Select

interface SystemMapper: BaseMapper<User> {
    @Select("""
        SELECT
            COLUMN_NAME AS `field`,
            COLUMN_TYPE AS `type`,
            COLUMN_COMMENT AS `comment`,
            TABLE_COMMENT AS `table_comment`,
            COLUMN_DEFAULT AS `default_value`,
            IS_NULLABLE AS is_nullable
        FROM
            INFORMATION_SCHEMA.COLUMNS as col
            left join INFORMATION_SCHEMA.TABLES as tab on col.TABLE_NAME = tab.TABLE_NAME
        WHERE
            col.TABLE_SCHEMA = #{schema} AND col.TABLE_NAME = #{tableName} AND
            tab.TABLE_SCHEMA = #{schema} AND tab.TABLE_NAME = #{tableName};
    """)
    fun getColumnInfo(schema: String, tableName: String): List<ColumnInfoResult>

    @Select("SHOW DATABASES")
    fun getDataBases(): List<String>

    @Select("SHOW TABLES FROM \${dbName}")
    fun getTables(dbName: String): List<String>
}