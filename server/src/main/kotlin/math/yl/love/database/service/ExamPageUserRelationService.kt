package math.yl.love.database.service

import com.baomidou.mybatisplus.extension.kotlin.KtUpdateWrapper
import math.yl.love.common.mybatis.BaseService
import math.yl.love.configuration.exception.BizException
import math.yl.love.database.domain.entity.ExamPageUserRelation
import math.yl.love.database.domain.params.examPageRelease.ExamListParam
import math.yl.love.database.domain.typeEnum.ExamPageStatusEnum
import math.yl.love.database.mapper.ExamPageUserRelationMapper
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.reflect.KClass

@Service
@Transactional(readOnly = true)
class ExamPageUserRelationService(
    val userService: UserService
): BaseService<ExamPageUserRelation, ExamPageUserRelationMapper>() {
    override val entityClass: KClass<ExamPageUserRelation> get() = ExamPageUserRelation::class

    fun findByReleaseId(id: Long): List<ExamPageUserRelation> = list(queryWrapper.eq(ExamPageUserRelation::pageReleaseId, id))

    /**
     * 删除发布试卷的用户
     * @param id 发布试卷id
     */
    fun removeByRelease(id: Long): Boolean {
        return remove(queryWrapper
            .eq(ExamPageUserRelation::pageReleaseId, id)
        )
    }

    /**
     * 查询当前用户所有被发布的试卷
     */
    fun findByUser(param: ExamListParam): List<ExamPageUserRelation> {
        val user = userService.getUserInfo()
        val pages = queryWrapper.eq(ExamPageUserRelation::userId, user.id)

        when(param.mode) {
            // 所有未开始获取进行中
            0 -> pages.`in`(ExamPageUserRelation::status, listOf(
                ExamPageStatusEnum.NOT_START,
                ExamPageStatusEnum.DOING
            ))

            // 已经完成的试卷
            1 -> pages.eq(ExamPageUserRelation::status, ExamPageStatusEnum.FINISHED)
        }

        return pages.list()
    }

    /**
     * 根据发布id和用户id查找发布数据
     * @param id 发布试卷id
     * @param userId 用户id
     */
    fun findByReleaseIdAndUserId(id: Long, userId: Long?): ExamPageUserRelation? = queryWrapper
        .eq(ExamPageUserRelation::pageReleaseId, id)
        .eq(ExamPageUserRelation::userId, userId)
        .selectOne()

    /**
     * 检查该用户的该发布是否有考试权限
     */
    fun checkExamInfo(id: Long): ExamPageUserRelation {
        val userId = userService.getUserInfo().id
        val relation = findByReleaseIdAndUserId(id, userId) ?: throw BizException("该用户不存在该练习!!!")
        if (relation.status == ExamPageStatusEnum.FINISHED) {
            throw BizException("该练习已结束!!!")
        }
        return relation
    }

    fun relation(releaseId: Long): ExamPageUserRelation {
        val userId = userService.getUserInfo().id
        return findByReleaseIdAndUserId(releaseId, userId) ?: throw BizException("不存在的发布!!")
    }

    @Transactional(rollbackFor = [Exception::class])
    fun finish(relationId: Long) {
        val updateWrapper = KtUpdateWrapper(ExamPageUserRelation::class.java)
            .eq(ExamPageUserRelation::id, relationId)
            .set(ExamPageUserRelation::status, ExamPageStatusEnum.FINISHED)

        baseMapper.update(updateWrapper)
    }

    /**
     * 更新用户答案
     * @param relationId 考试关联id
     * @param newAnswer 新答案
     * @return 是否更新成功
     */
    fun updateAnswer(relationId: Long, newAnswer: String): Boolean {
        val updateWrapper = KtUpdateWrapper(ExamPageUserRelation::class.java)
            .eq(ExamPageUserRelation::id, relationId)
            .set(ExamPageUserRelation::answer, newAnswer)

        return baseMapper.update(updateWrapper) > 0
    }
}