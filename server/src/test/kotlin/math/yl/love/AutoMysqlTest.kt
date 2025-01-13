package math.yl.love

import math.yl.love.common.base.Log.log
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import kotlin.test.Test

class AutoMysqlTest {

    @Test
    fun test() {
        val res = getTableMeta("users")
        res.forEach {
            log.info("yuri: ${it["COLUMN_NAME"]}")
        }
    }

    fun getTableMeta(tableName: String): List<Map<String, String>> {
        val url = "jdbc:mysql://localhost:3306/zyl"
        val username = "root"
        val password = "yuri"

        val connection: Connection = DriverManager.getConnection(url, username, password)
        val metadata = connection.metaData
        val columns: ResultSet = metadata.getColumns(null, null, tableName, null)

        val result = mutableListOf<Map<String, String>>()

        while (columns.next()) {
            val columnMeta = mapOf(
                "COLUMN_NAME" to columns.getString("COLUMN_NAME"),
                "TYPE_NAME" to columns.getString("TYPE_NAME"),
                "COLUMN_SIZE" to columns.getString("COLUMN_SIZE"),
                "IS_NULLABLE" to columns.getString("IS_NULLABLE"),
                "IS_AUTOINCREMENT" to columns.getString("IS_AUTOINCREMENT")
            )
            result.add(columnMeta)
        }

        columns.close()
        connection.close()
        return result
    }
}