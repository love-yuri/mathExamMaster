package math.yl.love.database.service

import io.swagger.v3.oas.annotations.media.Schema
import math.yl.love.common.mybatis.BaseService
import math.yl.love.configuration.exception.BizException
import math.yl.love.database.domain.entity.UserScore
import math.yl.love.database.domain.result.examPageUserRelation.UserAnswer
import math.yl.love.database.domain.result.questionBank.*
import math.yl.love.database.domain.result.userScore.ScoreDetailResult
import math.yl.love.database.domain.result.userScore.UserScoreDetail
import math.yl.love.database.domain.typeEnum.ExamPageStatusEnum
import math.yl.love.database.domain.typeEnum.QuestionTypeEnum
import math.yl.love.database.domain.typeEnum.QuestionTypeEnum.*
import math.yl.love.database.mapper.UserScoreMapper
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.reflect.KClass

@Service
@Transactional(readOnly = true)
class UserScoreService(
    private val examPageUserRelationService: ExamPageUserRelationService,
    private val examPageReleaseService: ExamPageReleaseService,
): BaseService<UserScore, UserScoreMapper>() {
    override val entityClass: KClass<UserScore> get() = UserScore::class

    @Transactional(rollbackFor = [Exception::class])
    override fun updateById(entity: UserScore?): Boolean {
        if (entity?.id == null) {
            return false
        }
        entity.score = entity.detail?.sumOf { it.score } ?: 0
        return super.updateById(entity)
    }

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
        ).apply {
            // 保存记录
            this.score = this.detail?.sumOf { it.score } ?: 0
            save(this)

            // 更新试卷状态
            examPageUserRelationService.setStatus(id, ExamPageStatusEnum.REVIEWING)
        }
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

    /**
     * 阅卷结束
     * @param id relation id
     */
    @Transactional(rollbackFor = [Exception::class])
    fun reviewingCompleted(id: Long): Boolean {
        return examPageUserRelationService.setStatus(id, ExamPageStatusEnum.REVIEW_COMPLETED)
    }

    /**
     * 获取结束考试后的得分详情
     * @param id 发布id
     */
    fun scoreDetail(id: Long): ScoreDetailResult {
        val studentDetails = examPageReleaseService.studentDetail(id)
        if (studentDetails.isEmpty()) {
            throw BizException("发布不存在")
        }
        studentDetails.firstOrNull { !it.hasGrading }?.let {
            throw BizException("还有学生未阅卷")
        }
        val details = queryWrapper
            .`in`(UserScore::userId, studentDetails.map { it.userId })
            .list()

        val scores = details.map { it.score }.sorted()

        if (details.isEmpty()) {
            throw BizException("学生得分详情为空!!!")
        }

        // 学生总数
        val totalStudent = studentDetails.size
        val totalScore = details[0].totalScore
        var maxScore = 0
        var minScore = totalScore
        var averageScore = 0.0
        val medianScore = when {
            totalStudent == 0 -> 0.0
            totalStudent % 2 == 1 -> scores[totalStudent / 2].toDouble()
            else -> (scores[totalStudent / 2 - 1] + scores[totalStudent / 2]) / 2.0
        }
        var over90 = 0
        var over80 = 0
        var over70 = 0
        var over60 = 0
        var below60 = 0
        details.forEach {
            when {
                it.score > 90 -> over90++
                it.score > 80 -> over80++
                it.score > 70 -> over70++
                it.score > 60 -> over60++
                else -> below60++
            }
            if (totalScore != it.totalScore) {
                throw BizException("试卷总分不一致")
            }
            maxScore = maxScore.coerceAtLeast(it.score)
            minScore = minScore.coerceAtMost(it.score)
            averageScore += it.score.toDouble() / totalStudent
        }

        return ScoreDetailResult(
            totalStudent = totalStudent,
            totalScore = totalScore,
            over60 = over60,
            over70 = over70,
            over80 = over80,
            over90 = over90,
            below60 = below60,
            averageScore = averageScore,
            medianScore = medianScore,
            maxScore = maxScore,
            minScore = minScore,
            passRate = (over60 + over70 + over80 + over90) * 1.0 / totalStudent,
            questionStatistics = getQuestionStatistics(details),
        )
    }

    fun getQuestionStatistics(userScores: List<UserScore>): List<ScoreDetailResult.QuestionStatistic> {
        data class Data(
            var correctCount: Int,
            var maxScore: Int,
            var minScore: Int,
            var score: Int,
            var totalScore: Int,
            val pageTotalScore: Int,
            var type: QuestionTypeEnum
        )
        val questionStatsMap = mutableMapOf<Long, Data>()

        // 遍历所有用户的答题详情
        userScores.forEach { userScore ->
            userScore.detail?.forEach { detail ->
                val questionId = detail.questionId
                val score = detail.score
                val totalScore = detail.totalScore

                val data = questionStatsMap.getOrPut(questionId) {
                    Data(
                        correctCount = 0,
                        maxScore = Int.MIN_VALUE,
                        minScore = Int.MAX_VALUE,
                        totalScore = 0,
                        score = 0,
                        pageTotalScore = totalScore,
                        type = detail.type
                    )
                }

                // 计算正确人数（满分算正确）
                if (score == totalScore) {
                    data.correctCount += 1 // 正确人数 +1
                }

                // 更新最高分和最低分
                data.maxScore = data.maxScore.coerceAtLeast(score) // 最高分
                data.minScore = data.minScore.coerceAtMost(score)  // 最低分

                // 计算总得分和总分
                data.score += score      // 总得分
                data.totalScore += totalScore // 总分
            }
        }

        // 生成统计结果
        var index = 0
        return questionStatsMap.map { (questionId, data) ->
            ScoreDetailResult.QuestionStatistic(
                index = ++index,
                questionId = questionId,
                correctCount = data.correctCount,
                maxScore = data.maxScore,
                minScore = data.minScore,
                type = data.type,
                totalScore = data.pageTotalScore,
                scoreRate = if (data.totalScore > 0) data.score.toDouble() / data.totalScore else 0.0
            )
        }
    }

}