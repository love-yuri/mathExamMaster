package math.yl.love.database.domain.params.system

import math.yl.love.database.domain.result.system.ColumnInfoResult

data class CreateTableFileParam(
    val tableName: String,
    val entityName: String,
    val tableComment: String?,
    var entity: String = "",
    var mapper: String = "",
    var service: String = "",
    var controller: String = "",
    val column: List<ColumnInfoResult>,
    val override: Boolean = false
)