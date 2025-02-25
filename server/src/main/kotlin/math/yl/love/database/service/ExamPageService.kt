package math.yl.love.database.service

import com.baomidou.mybatisplus.extension.kotlin.KtUpdateWrapper
import math.yl.love.common.mybatis.BasePage
import math.yl.love.common.mybatis.BaseService
import math.yl.love.configuration.exception.BizException
import math.yl.love.database.domain.entity.*
import math.yl.love.database.domain.params.examPage.ExamPageQuestion
import math.yl.love.database.domain.params.examPage.ReleasePageParam
import math.yl.love.database.domain.params.examPage.UpdateUserAnswerParam
import math.yl.love.database.domain.params.examPageRelease.QuestionInfoParam
import math.yl.love.database.domain.result.examPage.ExamPageResult
import math.yl.love.database.domain.result.examPage.QuestionInfoResult
import math.yl.love.database.domain.result.examPageUserRelation.UserAnswer
import math.yl.love.database.domain.result.questionBank.*
import math.yl.love.database.domain.typeEnum.ExamPageStatusEnum
import math.yl.love.database.mapper.ExamPageMapper
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.io.Serializable
import kotlin.reflect.KClass

@Service
@Transactional(readOnly = true)
class ExamPageService(
    private val examPageQuestionRelationService: ExamPageQuestionRelationService,
    private val examPageUserRelationService: ExamPageUserRelationService,
    private val questionBankService: QuestionBankService,
    private val userService: UserService
): BaseService<ExamPage, ExamPageMapper>() {

    override val entityClass: KClass<ExamPage> get() = ExamPage::class

    /**
     * 发布试卷
     * @param param 试卷参数
     */
    @Transactional(rollbackFor = [Exception::class])
    fun release(param: ReleasePageParam): Boolean {
        // 保存试卷
        val page = ExamPage (
            title = param.title,
            subject = param.subject,
            type = param.type,
            totalScore = param.totalScore,
            limitedTime = param.limitedTime,
            difficulty = param.difficulty
        )
        save(page)

        // 保存试卷-题目
        val questions = param.questions.map {
            ExamPageQuestionRelation(
                examPageId = page.id!!,
                questionBankId = it.questionBankId,
                score = it.score
            )
        }
        examPageQuestionRelationService.saveBatch(questions)

        return true
    }

    /**
     * 更新试卷
     * @param param 试卷参数
     */
    @Transactional(rollbackFor = [Exception::class])
    fun updatePage(param: ReleasePageParam): Boolean {
        param.id ?: throw RuntimeException("id不能为空!!")
        // 更新试卷
        val page = ExamPage (
            id = param.id,
            title = param.title,
            subject = param.subject,
            type = param.type,
            totalScore = param.totalScore,
            limitedTime = param.limitedTime,
            difficulty = param.difficulty
        )
        updateById(page)

        // 更新试卷-题目
        val oldQuestionMap = examPageQuestionRelationService.findByPageId(param.id).associateBy { it.questionBankId }.toMutableMap()

        val updateQuestion = mutableListOf<ExamPageQuestionRelation>()
        val newQuestion = mutableListOf<ExamPageQuestionRelation>()
        param.questions.forEach {
            // 如果存在就更新数据
            if (oldQuestionMap.containsKey(it.questionBankId)) {
                updateQuestion.add(ExamPageQuestionRelation(
                    id = oldQuestionMap[it.questionBankId]!!.id!!,
                    examPageId = page.id!!,
                    questionBankId = it.questionBankId,
                    score = it.score
                ))
                oldQuestionMap.remove(it.questionBankId)
            } else {
                newQuestion.add(ExamPageQuestionRelation(
                    examPageId = page.id!!,
                    questionBankId = it.questionBankId,
                    score = it.score
                ))
            }
        }

        examPageQuestionRelationService.updateBatchById(updateQuestion)
        examPageQuestionRelationService.saveBatch(newQuestion)
        examPageQuestionRelationService.removeByIds(oldQuestionMap.values)
        return true
    }

    /**
     * 分页获取试卷详情
     * @param current 当前页
     * @param size 每页大小
     */
    fun pageSimple(current: Long, size: Long): BasePage<ExamPageResult> {
        val pages = page(current, size)
        val pageList = mutableListOf<ExamPageResult>()
        pages.records.forEach {
            pageList.add(setByExamPage(it))
        }
        return BasePage(current, size, pageList, pages.total)
    }

    /**
     * 根据试卷信息获取详细数据
     * @param page 试卷信息
     */
    private fun setByExamPage(page: ExamPage): ExamPageResult {
        val questions = examPageQuestionRelationService.findByPageId(page.id!!)
        return ExamPageResult(
            id = page.id!!,
            title = page.title,
            difficulty = page.difficulty,
            limitedTime = page.limitedTime,
            subject = page.subject,
            type = page.type,
            releaseTime = page.createTime!!,
            totalScore = page.totalScore,
            questions = questions.map { k ->
                ExamPageQuestion(
                    questionBankId = k.questionBankId,
                    score = k.score,
                )
            }
        )
    }

    /**
     * 删除试卷，需要同步删除另外两个试卷的数据
     * TODO: 删除试卷还需要删除试卷发布
     */
    @Transactional(rollbackFor = [Exception::class])
    override fun removeById(id: Serializable?): Boolean {
        return super.removeById(id)
                && examPageQuestionRelationService.deleteByPageId(id.toString().toLong())
    }

    /**
     * 根据id获取详细数据
     * @param id 试卷id
     */
    fun detail(id: Long): ExamPageResult {
        val page = getById(id) ?: throw BizException("试卷不存在!!")
        val result = setByExamPage(page)
        result.questions.forEach {
            it.fullQuestionBank = questionBankService.detail(it.questionBankId)
        }
        return result
    }

    /**
     * 获取用户的答案，如果不存在则赋值默认值
     */
    fun getUserAnswer(questions: List<QuestionBank>, relation: ExamPageUserRelation): List<UserAnswer> {
        if (relation.answer == null || relation.answer.size != questions.size) {
            return questions.map {
                UserAnswer(
                    hasAnswer = false,
                    questionAnswer = when(it.answer) {
                        is GapFillingAnswer -> it.answer.copy(
                            answer = it.answer.answer.map { "" }
                        )
                        is JudgeAnswer -> it.answer.copy(answer = null)
                        is MultipleChoiceAnswer -> it.answer.copy(answer = listOf())
                        is SingleChoiceAnswer -> it.answer.copy(answer = null)
                        is SubjectiveAnswer -> it.answer.copy(answer = "")
                    },
                    questionId = it.id!!,
                )
            }
        }
        return relation.answer
    }

    /**
     * 获取问题信息
     * @param param 题目信息参数
     */
    fun questionInfo(param: QuestionInfoParam): List<QuestionInfoResult> {
        val questions = baseMapper.questionInfo(param.examPageId)
        val relation = examPageUserRelationService.getById(param.relationId) ?: throw BizException("详情不存在")
        val answers = getUserAnswer(questions, relation).associateBy { it.questionId }
        var index = 0
        return questions.groupBy { it.type }.map {
            QuestionInfoResult(
                type = it.key,
                infos = it.value.map { q ->
                    QuestionInfoResult.QuestionInfo(
                        type = it.key,
                        id = q.id,
                        content = q.content,
                        index = ++index,
                        userAnswer = answers[q.id]!!
                    )
                }
            )
        }
    }

    /**
     * 更新用户答案
     */
    @Transactional(rollbackFor = [Exception::class])
    fun updateUserAnswer(param: UpdateUserAnswerParam): Boolean {
        return examPageUserRelationService.updateAnswer(param.relationId, param.userAnswers)
    }

    /**
     * 交卷
     * @param id 发布id
     */
    @Transactional(rollbackFor = [Exception::class])
    fun overExam(id: Long): Boolean {
        val relation = examPageUserRelationService.getById(id) ?: throw BizException("未找到考试关联!!")
        if (relation.status != ExamPageStatusEnum.DOING) {
            throw BizException("试卷状态不正确!!!")
        }
        return examPageUserRelationService.updateById(
            relation.copy(status = ExamPageStatusEnum.FINISHED)
        )
    }
}