package math.yl.love.database.service

import cn.dev33.satoken.stp.StpUtil
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import math.yl.love.common.constant.RedisConstant
import math.yl.love.common.mybatis.BasePage
import math.yl.love.common.mybatis.BaseService
import math.yl.love.configuration.config.SystemConfig
import math.yl.love.configuration.exception.BizException
import math.yl.love.controller.UserController
import math.yl.love.database.domain.entity.User
import math.yl.love.database.domain.params.user.GetStudentEnum
import math.yl.love.database.domain.params.user.LoginQuery
import math.yl.love.database.domain.params.user.SetTeacherParam
import math.yl.love.database.domain.result.user.LoginResult
import math.yl.love.database.domain.result.user.UserInfo
import math.yl.love.database.domain.result.user.UserResult
import math.yl.love.database.domain.typeEnum.UserRoleEnum
import math.yl.love.database.mapper.UserMapper
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.reflect.KClass

@Service
@Transactional(readOnly = true)
class UserService(
    val redisService: RedisService,
    val systemConfig: SystemConfig,
    @Lazy val departmentService: DepartmentService
): BaseService<User, UserMapper>() {

    fun login(loginQuery: LoginQuery): LoginResult {
        val user = getByUsername(loginQuery.username) ?: throw RuntimeException("用户不存在")
        check(user.password == loginQuery.password) { "密码错误" }
        StpUtil.login(user.username)
        return LoginResult(
            token = StpUtil.getTokenValue(),
            role = user.role,
        )
    }

    /**
     * 根据id获取所有人的信息
     */
    fun getResultByIds(ids: List<*>): List<UserResult> {
        if (ids.isEmpty()) {
            return emptyList()
        }
        return queryWrapper.`in`(User::id, ids).list().map {
            UserResult(it.id!!, it.username, it.nickname)
        }
    }

    /**
     * 根据id获取个人的信息
     */
    fun getResultById(id: Long?): UserResult? {
        if (id == null) {
            return null
        }
        val list = queryWrapper.eq(User::id, id).list().map {
            UserResult(it.id!!, it.username, it.nickname)
        }

        if (list.isNotEmpty()) {
            return list.first()
        }
        return null
    }

    /**
     * 退出登录
     */
    fun logout() {

        try {
            val username = StpUtil.getLoginId().toString()
            redisService.del("${RedisConstant.USER_INFO}:${username}")
        } catch (e: Exception) {
            log.error("yuri: 退出登录失败", e)
        }

        StpUtil.logout()
    }

    /**
     * 根据用户名查询
     */
    fun getByUsername(username: String?): User? = getOne(queryWrapper.eq(!username.isNullOrEmpty(), User::username, username))

    /**
     * 查找用户信息
     * 默认使用
     */
    fun getUserInfo(): UserInfo {
        val username = StpUtil.getLoginId().toString()
        return redisService.getOrReSet(
            "${RedisConstant.USER_INFO}:${username}",
            systemConfig.userInfoTimeout
        ) {
            // 理论上不可能不存在
            val user = getByUsername(username) ?: throw RuntimeException("用户不存在!!")
            UserInfo(
                id = user.id,
                role = user.role,
                username = user.username,
                nickname = user.nickname,
                homePath = "",
            )
        }
    }

    /**
     * 分页查询
     * @param param 分页参数
     */
    fun page(param: UserController.PageParam): BasePage<UserResult> {
        val p = when(param.studentFlag) {
            GetStudentEnum.All -> page(Page(param.current, param.size), queryWrapper)
            GetStudentEnum.HasClass -> TODO("暂未实现")
            GetStudentEnum.NoClass -> baseMapper.getNoClassStudents(Page<User>(param.current, param.size))
        }
        return BasePage(
            current = p.current,
            size = p.size,
            records = p.records.map {
                UserResult(it.id!!, it.username, it.nickname)
            },
            total = p.total
        )
    }

    /**
     * 为指定组织设置老师
     * @param param 参数
     */
    @Transactional(rollbackFor = [Exception::class])
    fun setTeacher(param: SetTeacherParam): Boolean {
        val department = departmentService.getById(param.departmentId) ?: throw BizException("组织不存在!!")
        val nextDepartments = departmentService.getByParent(param.departmentId)
        if (nextDepartments.isNotEmpty()) {
            throw BizException("该组织下有子组织，无法设置老师!!")
        }
        return departmentService.updateById(department.copy(
            teacherId = param.teacherId
        ))
    }

    /**
     * 获取所有老师
     * @param flag 0:所有老师 1:有班级的老师 2:没有班级的老师
     */
    fun teachers(flag: Int): List<UserResult> {
        val qw = queryWrapper
            .eq(User::role, UserRoleEnum.TEACHER)
            .or { it.eq(User::role, UserRoleEnum.ADMIN) }

        return when(flag) {
            0 -> qw.list()
            1 -> {
                val teacherIds = departmentService.getHasTeacher().map { it.teacherId }
                qw.`in`(User::id, teacherIds).list()
            }
            2 -> {
                val teacherIds = departmentService.getHasTeacher().map { it.teacherId }
                qw.notIn(User::id, teacherIds).list()
            }
            else -> emptyList()
        }.map { UserResult(it.id!!, it.username, it.nickname) }
    }
}