package math.yl.love.configuration.config

import com.baomidou.mybatisplus.annotation.DbType
import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor
import math.yl.love.common.mybatis.typeHandler.*
import math.yl.love.common.utils.JsonUtils.parseJson
import math.yl.love.common.utils.JsonUtils.toJson
import math.yl.love.database.domain.result.questionBank.QuestionAnswer
import math.yl.love.database.domain.result.userScore.UserScoreDetail
import org.apache.ibatis.type.BaseTypeHandler
import org.apache.ibatis.type.JdbcType
import org.apache.ibatis.type.TypeHandlerRegistry
import org.mybatis.spring.annotation.MapperScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.sql.CallableStatement
import java.sql.PreparedStatement
import java.sql.ResultSet

@Configuration
@MapperScan("math.yl.love.database.mapper")
class MyBatisConfig {

    /**
     * 分页插件
     */
    @Bean
    fun mybatisPlusInterceptor(): MybatisPlusInterceptor {
        val interceptor = MybatisPlusInterceptor()
        interceptor.addInnerInterceptor(PaginationInnerInterceptor(DbType.MYSQL)) // 如果配置多个插件, 切记分页最后添加
        // 如果有多数据源可以不配具体类型, 否则都建议配上具体的 DbType
        return interceptor
    }

    @Bean
    fun configurationCustomizer(): ConfigurationCustomizer {
        return ConfigurationCustomizer { configuration ->
            val typeHandlerRegistry: TypeHandlerRegistry = configuration.typeHandlerRegistry
            typeHandlerRegistry.register(QuestionAnswerTypeHandler::class.java)
            typeHandlerRegistry.register(UserScoreDetailTypeHandler::class.java)
            typeHandlerRegistry.register(UserAnswerTypeHandler::class.java)
            typeHandlerRegistry.register(ListUserScoreDetailTypeHandler::class.java)
            typeHandlerRegistry.register(ListQuestionAnswerTypeHandler::class.java)
            typeHandlerRegistry.register(ListUserAnswerTypeHandler::class.java)
        }
    }

    class QuestionAnswerTypeHandler : BaseTypeHandler<QuestionAnswer>() {
        override fun setNonNullParameter(
            ps: PreparedStatement,
            i: Int,
            parameter: QuestionAnswer,
            jdbcType: JdbcType?
        ) {
            ps.setString(i, parameter.toJson())
        }

        override fun getNullableResult(rs: ResultSet, columnName: String): QuestionAnswer? {
            val json = rs.getString(columnName) ?: return null
            return json.parseJson()
        }

        override fun getNullableResult(rs: ResultSet, columnIndex: Int): QuestionAnswer? {
            val json = rs.getString(columnIndex) ?: return null
            return json.parseJson()
        }

        override fun getNullableResult(cs: CallableStatement, columnIndex: Int): QuestionAnswer? {
            val json = cs.getString(columnIndex) ?: return null
            return json.parseJson()
        }
    }
}