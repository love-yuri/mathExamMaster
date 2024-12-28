package math.yl.love.database.mapper

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import math.yl.love.database.domain.entity.ExamPage
import math.yl.love.database.domain.entity.QuestionBank
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select

@Mapper
interface ExamPageMapper: BaseMapper<ExamPage> {


    @Select("""
        select * from question_bank where id in (
            select question_bank_id from exam_page_question_relation 
            where exam_page_id = #{id}
        );
    """)
    fun questionInfo(id: Long):List<QuestionBank>
}