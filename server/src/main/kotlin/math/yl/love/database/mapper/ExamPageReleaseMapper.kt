package math.yl.love.database.mapper

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import math.yl.love.database.domain.entity.ExamPageRelease
import math.yl.love.database.domain.result.examPageRelease.ExamInfoResult
import math.yl.love.database.domain.result.examPageRelease.StudentDetailResult
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select

@Mapper
interface ExamPageReleaseMapper: BaseMapper<ExamPageRelease> {

    @Select("""
       select exam_page_release.id as releaseId,
       exam_page_user_relation.id as relationId,
       title,
       subject,
       type,
       difficulty,
       total_score,
       limited_time,
       status,
       exam_page_release.start_time as start_time,
       exam_page_id,
       end_time,
       exam_page_user_relation.exam_start_time as exam_start_time
        from exam_page_release left join exam_page_user_relation
        on exam_page_release.id = exam_page_user_relation.page_release_id
        left join exam_page on exam_page_id = exam_page.id
        where exam_page_release.id = #{id} and user_id = #{userId}
        and exam_page_release.deleted = false and exam_page_user_relation.deleted = false;
    """)
    fun examInfo(id: Long, userId: Long?): ExamInfoResult?


    @Select("""
        SELECT
            user.id AS user_id,
            epur.id AS relation_id,
            nick_name,
            user_name,
            COALESCE(user_score.has_grading, FALSE) AS has_grading
        FROM exam_page_release epr
                 LEFT JOIN exam_page_user_relation epur
                           ON epr.id = epur.page_release_id
                 LEFT JOIN user ON user.id = epur.user_id
                 LEFT JOIN user_score ON user.id = user_score.user_id
            AND user_score.page_release_id = #{id}
        WHERE epr.id = #{id};
    """)
    fun studentDetail(id: Long): List<StudentDetailResult>
}