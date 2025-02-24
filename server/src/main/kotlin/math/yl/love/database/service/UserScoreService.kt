package math.yl.love.database.service

import math.yl.love.common.mybatis.BaseService
import math.yl.love.database.domain.entity.UserScore
import math.yl.love.database.mapper.UserScoreMapper
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.reflect.KClass

@Service
@Transactional(readOnly = true)
class UserScoreService(
    private val examPageUserRelationService: ExamPageUserRelationService,
    private val questionBankService: QuestionBankService,
    private val examPageReleaseService: ExamPageReleaseService,
): BaseService<UserScore, UserScoreMapper>() {
    override val entityClass: KClass<UserScore> get() = UserScore::class

    /**
     * 根据relation id查询用户的答题信息
     */
    fun detail(id: Long) {
        val relation = examPageUserRelationService.getById(id) ?: throw RuntimeException("用户答题为找到")
        val examPageRelease = examPageReleaseService.detail(relation.pageReleaseId)
        if (relation.answer == null) {

        }
        val questions = questionBankService.findByIds(relation.answer.map { it.questionId })
    }
}