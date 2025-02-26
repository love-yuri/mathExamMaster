package math.yl.love.database.mapper

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import math.yl.love.database.domain.entity.UserScore
import math.yl.love.database.domain.result.userScore.UserScoreQuestion
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select

@Mapper
interface UserScoreMapper: BaseMapper<UserScore> {
    @Select("""
        select
            qb.type as type,
            qb.id as question_id,
            qb.answer question_answer,
            epqr.score as question_score
            from exam_page_user_relation epur
            left join exam_page_release epr on epr.id = epur.page_release_id
            left join exam_page_question_relation epqr on epqr.exam_page_id = epr.exam_page_id
            left join question_bank qb on qb.id = epqr.question_bank_id
        where epur.id = #{id} 
    """)
    fun questions(id: Long): List<UserScoreQuestion>
}