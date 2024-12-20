package math.yl.love.database.service

import math.yl.love.common.mybatis.BaseService
import math.yl.love.common.utils.CommonUtils
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
     * @param userIds 用户id列表
     */
    fun removeByRelease(id: Long, userIds: List<Long>) {
        remove(queryWrapper
            .eq(ExamPageUserRelation::pageReleaseId, id)
            .`in`(ExamPageUserRelation::userId, userIds)
        )
    }

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
        val pages = queryWrapper.eq(ExamPageUserRelation::userId, user!!.id)

        when(param.mode) {
            0 -> pages.`in`(ExamPageUserRelation::status, listOf(
                ExamPageStatusEnum.NOT_START,
                ExamPageStatusEnum.DOING
            ))

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

    fun relation(releaseId: Long): ExamPageUserRelation {
        val userId = userService.getUserInfo()!!.id
        return findByReleaseIdAndUserId(releaseId, userId) ?: throw BizException("不存在的发布!!")
    }
}