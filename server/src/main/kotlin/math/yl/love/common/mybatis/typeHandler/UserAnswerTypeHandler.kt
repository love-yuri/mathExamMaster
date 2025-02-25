package math.yl.love.common.mybatis.typeHandler

import math.yl.love.common.utils.JsonUtils.parseJson
import math.yl.love.common.utils.JsonUtils.toJson
import math.yl.love.database.domain.result.examPageUserRelation.UserAnswer
import math.yl.love.database.domain.result.userScore.UserScoreDetail
import org.apache.ibatis.type.BaseTypeHandler
import org.apache.ibatis.type.JdbcType
import java.sql.CallableStatement
import java.sql.PreparedStatement
import java.sql.ResultSet

class UserAnswerTypeHandler : BaseTypeHandler<UserAnswer>() {
    override fun setNonNullParameter(
        ps: PreparedStatement,
        i: Int,
        parameter: UserAnswer,
        jdbcType: JdbcType?
    ) {
        ps.setString(i, parameter.toJson())
    }

    override fun getNullableResult(rs: ResultSet, columnName: String): UserAnswer? {
        val json = rs.getString(columnName) ?: return null
        return json.parseJson()
    }

    override fun getNullableResult(rs: ResultSet, columnIndex: Int): UserAnswer? {
        val json = rs.getString(columnIndex) ?: return null
        return json.parseJson()
    }

    override fun getNullableResult(cs: CallableStatement, columnIndex: Int): UserAnswer? {
        val json = cs.getString(columnIndex) ?: return null
        return json.parseJson()
    }
}
