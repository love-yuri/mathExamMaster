package math.yl.love.database.service

import math.yl.love.common.mybatis.BaseService
import math.yl.love.common.utils.JsonUtils.toJson
import math.yl.love.database.domain.entity.UserScore
import math.yl.love.database.domain.result.examPageUserRelation.UserAnswer
import math.yl.love.database.domain.result.questionBank.*
import math.yl.love.database.domain.result.userScore.UserScoreDetail
import math.yl.love.database.mapper.UserScoreMapper
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.reflect.KClass

@Service
@Transactional(readOnly = true)
class UserScoreService(
    private val examPageUserRelationService: ExamPageUserRelationService,
): BaseService<UserScore, UserScoreMapper>() {
    override val entityClass: KClass<UserScore> get() = UserScore::class

    /**
     * 根据relation id查询用户的答题信息
     */
    @Transactional(rollbackFor = [Exception::class])
    fun detail(id: Long): UserScore {
        val relation = examPageUserRelationService.getById(id) ?: throw RuntimeException("用户答题为找到")
        val userScore = queryWrapper
            .eq(UserScore::userId, relation.userId)
            .eq(UserScore::pageReleaseId, relation.pageReleaseId)
            .selectOne()

        if (userScore != null) {
            return userScore
        }

        val questions = baseMapper.questions(id)
        val userAnswers = (relation.answer ?: questions.map {
            UserAnswer (
                questionId = it.questionId,
                hasAnswer = false,
                questionAnswer = when(it.questionAnswer) {
                    is GapFillingAnswer -> it.questionAnswer.copy(
                        answer = it.questionAnswer.answer.map { "" }
                    )
                    is JudgeAnswer -> it.questionAnswer.copy(answer = null)
                    is MultipleChoiceAnswer -> it.questionAnswer.copy(answer = listOf())
                    is SingleChoiceAnswer -> it.questionAnswer.copy(answer = null)
                    is SubjectiveAnswer -> it.questionAnswer.copy(answer = "")
                }
            )
        }).associateBy { it.questionId }

        return UserScore(
            pageReleaseId = relation.pageReleaseId,
            userId = relation.userId,
            score = 0,
            totalScore = questions.sumOf { it.questionScore },
            detail = questions.map {
                UserScoreDetail(
                    questionId = it.questionId,
                    questionAnswer = it.questionAnswer,
                    userAnswer = userAnswers[it.questionId]!!,
                )
            }
        ).apply { save(this) }
    }

    fun updateDetail(data: UserScore, details: List<UserScoreDetail>) {
        updateWrapper
            .eq(UserScore::id, data.id)
            .set(UserScore::detail, details.toJson())
            .update()
    }
}