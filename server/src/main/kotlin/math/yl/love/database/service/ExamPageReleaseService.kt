package math.yl.love.database.service

import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import math.yl.love.common.mybatis.BaseController.PageParam
import math.yl.love.common.mybatis.BasePage
import math.yl.love.common.mybatis.BaseService
import math.yl.love.common.utils.CommonUtils
import math.yl.love.configuration.exception.BizException
import math.yl.love.database.domain.entity.ExamPageRelease
import math.yl.love.database.domain.entity.ExamPageUserRelation
import math.yl.love.database.domain.entity.User
import math.yl.love.database.domain.params.examPageRelease.ExamListParam
import math.yl.love.database.domain.params.examPageRelease.ExamPageReleaseParam
import math.yl.love.database.domain.result.examPageRelease.ExamInfoResult
import math.yl.love.database.domain.result.examPageRelease.ExamListResult
import math.yl.love.database.domain.result.examPageRelease.ExamPageReleaseResult
import math.yl.love.database.domain.result.examPageRelease.StartExamResult
import math.yl.love.database.domain.typeEnum.ExamPageStatusEnum
import math.yl.love.database.mapper.ExamPageReleaseMapper
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Duration
import java.time.LocalDateTime
import kotlin.reflect.KClass

@Service
@Transactional(readOnly = true)
class ExamPageReleaseService(
    val userService: UserService,
    val examPageService: ExamPageService,
    val userRelationService: ExamPageUserRelationService
): BaseService<ExamPageRelease, ExamPageReleaseMapper>() {
    override val entityClass: KClass<ExamPageRelease> get() = ExamPageRelease::class

    /**
     * 发布试卷
     */
    @Transactional(rollbackFor = [Exception::class])
    fun release(param: ExamPageReleaseParam): Boolean {
        // 先保存试卷本体
        val examPageRelease = ExamPageRelease(
            startTime = param.startTime,
            endTime = param.endTime,
            examPageId = param.examPageId
        )
        save(examPageRelease)

        val userIds = userService.departmentService.getUserIds(param.departmentId)

        // 保存试卷与用户的关系
        val relations = userIds.map {
            ExamPageUserRelation(
                pageReleaseId = examPageRelease.id!!,
                userId = it,
            )
        }
        userRelationService.saveBatch(relations)
        return true
    }

    /**
     * 更新发布试卷
     */
    @Transactional(rollbackFor = [Exception::class])
    fun releaseUpdate(param: ExamPageReleaseParam): Boolean {
        val origin = getById(param.id ?: throw BizException("发布id不能为空!!!"))
        // 先更新试卷本体
        val examPageRelease = origin.copy(
            id = param.id,
            startTime = param.startTime,
            endTime = param.endTime,
            examPageId = param.examPageId
        )
        updateById(examPageRelease)
        return true
    }


    /**
     * 获取当前用户所有发布过的试卷
     */
    fun pageSimple(param: PageParam): BasePage<ExamPageReleaseResult> {
        val pages = page(Page(param.current, param.size), queryWrapper.eq(ExamPageRelease::createBy, CommonUtils.username))
        val records = pages.records.map {
            ExamPageReleaseResult(
                id = it.id!!,
                createTime = it.createTime,
                examPage = examPageService.getById(it.examPageId),
                startTime = it.startTime,
                endTime = it.endTime,
                users = findByReleaseId(it.id!!)
            )
        }
        return BasePage(pages.current, pages.size, records, pages.total)
    }

    /**
     * 根据发布id获取所有发布人员
     * @param id 发布id
     */
    fun findByReleaseId(id: Long): List<User> {
        val res = userRelationService.findByReleaseId(id)
        return userService.findByIds(res.map { it.userId })
    }

    /**
     * 根据id获取详细数据
     * @param id 主键id
     */
    fun detail(id: Long): ExamPageReleaseResult {
        val it = getById(id)
        return ExamPageReleaseResult(
            id = it.id!!,
            createTime = it.createTime,
            examPage = examPageService.getById(it.examPageId),
            startTime = it.startTime,
            endTime = it.endTime,
            users = findByReleaseId(it.id!!)
        )
    }

    /**
     * 根据id删除数据
     */
    @Transactional(rollbackFor = [Exception::class])
    fun removeDetail(id: String): Boolean {
        return removeById(id.toLong()) && userRelationService.removeByRelease(id.toLong())
    }

    /**
     * 获取试卷列表
     */
    fun examList(param: ExamListParam): List<ExamListResult> {
        val pages = userRelationService.findByUser(param)
        val result = mutableListOf<ExamListResult>()
        pages.forEach {
            val release = getById(it.pageReleaseId)
            val examPage = examPageService.getById(release!!.examPageId)
            result.add(ExamListResult(
                id = release.id!!,
                createTime = release.createTime,
                name = examPage.title
            ))
        }
        return result
    }

    /**
     * 开始考试
     * @param id 发布id
     */
    @Transactional(rollbackFor = [Exception::class])
    fun startExam(id: Long): StartExamResult {
        // 检查信息
        val release = getById(id) ?: throw BizException("发布不存在")
        val relation = userRelationService.checkExamInfo(release.id!!)
        val examPage = examPageService.getById(release.examPageId) ?: throw BizException("试卷不存在!")

        // 开始考试 -  更新状态
        if (relation.status == ExamPageStatusEnum.NOT_START) {
            val now = LocalDateTime.now()
            if (now.isBefore(release.startTime)) {
                throw BizException("考试未开始!!!")
            }
            userRelationService.updateById(relation.copy(
                status = ExamPageStatusEnum.DOING,
                examStartTime = now
            ))
        }
        
        return StartExamResult (
            examPageId = examPage.id!!,
            name = examPage.title,
            startTime = release.startTime,
            endTime = release.endTime
        )
    }

    /**
     * 获取当前用户考试信息
     * @param id 发布id
     */
    fun examInfo(id: Long): ExamInfoResult {
        val userId = userService.getUserInfo().id
        return baseMapper.examInfo(id, userId) ?: throw BizException("考试不存在!!")
    }

    /**
     * 检查用户的试卷是否已结束
     */
    @Transactional(rollbackFor = [Exception::class])
    fun check(id: Long): Boolean {
        val examInfo = examInfo(id)
        if (examInfo.status == ExamPageStatusEnum.DOING) {
            val diff = Duration.between(examInfo.examStartTime, LocalDateTime.now())
            if (diff.toSeconds() > examInfo.limitedTime) {
                userRelationService.finish(examInfo.relationId)
                return false
            }
            return true
        }
        return false
    }
}