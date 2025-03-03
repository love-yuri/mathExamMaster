package math.yl.love.database.service

import math.yl.love.common.mybatis.BaseService
import math.yl.love.database.domain.entity.UserScore
import math.yl.love.database.domain.result.examPageUserRelation.UserAnswer
import math.yl.love.database.domain.result.questionBank.*
import math.yl.love.database.domain.result.userScore.UserScoreDetail
import math.yl.love.database.domain.typeEnum.QuestionTypeEnum.*
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
        val defaultMsg = ""
        val userAnswers = (relation.answer ?: questions.map {
            UserAnswer (
                questionId = it.questionId,
                hasAnswer = false,
                questionAnswer = when(it.questionAnswer) {
                    is GapFillingAnswer -> it.questionAnswer.copy(
                        answer = it.questionAnswer.answer.map { defaultMsg }
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
                val detail = UserScoreDetail(
                    score = 0,
                    totalScore = it.questionScore,
                    type = it.type,
                    questionId = it.questionId,
                    questionAnswer = it.questionAnswer,
                    userAnswer = userAnswers[it.questionId]!!,
                    hasSetScore = false
                ).apply { setUserScore(this) }
                return@map detail
            }
        ).apply { save(this) }
    }

    /**
     * 设置用户得分
     */
    private fun setUserScore(detail: UserScoreDetail) {
        when(detail.type) {
            SINGLE_CHOICE -> {
                val q = detail.questionAnswer as SingleChoiceAnswer
                val u = detail.userAnswer.questionAnswer as SingleChoiceAnswer
                if (q.answer == u.answer) {
                    detail.score = detail.totalScore
                }
                detail.hasSetScore = true
            }
            MULTIPLE_CHOICE -> {
                val q = detail.questionAnswer as MultipleChoiceAnswer
                val u = detail.userAnswer.questionAnswer as MultipleChoiceAnswer

                if (
                    q.answer.size == u.answer.size &&
                    q.answer.containsAll(u.answer)
                ) {
                    detail.score = detail.totalScore
                }
                detail.hasSetScore = true
            }
            JUDGE -> {
                val q = detail.questionAnswer as JudgeAnswer
                val u = detail.userAnswer.questionAnswer as JudgeAnswer
                if (q.answer == u.answer) {
                    detail.score = detail.totalScore
                }
                detail.hasSetScore = true
            }
            GAP_FILLING -> {
                val q = detail.questionAnswer as GapFillingAnswer
                val u = detail.userAnswer.questionAnswer as GapFillingAnswer
                var count = 0
                q.answer.forEachIndexed { i, it ->
                    if (it == u.answer[i]) {
                        count++
                    }
                }

                detail.score = (detail.totalScore * (count.toDouble() / q.answer.size)).toInt()
                detail.hasSetScore = true
            }
            SUBJECTIVE -> {
                detail.score = 0
                detail.hasSetScore = false
            }
        }
    }
}