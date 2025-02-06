package math.yl.love.database.service

import math.yl.love.common.mybatis.BaseService
import math.yl.love.database.domain.entity.User
import math.yl.love.database.domain.entity.UserDepartment
import math.yl.love.database.domain.params.userDepartment.BatchSaveParam
import math.yl.love.database.domain.result.user.UserResult
import math.yl.love.database.mapper.UserDepartmentMapper
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.io.Serializable
import kotlin.reflect.KClass

@Service
@Transactional(readOnly = true)
class UserDepartmentService(
    val userService: UserService
): BaseService<UserDepartment, UserDepartmentMapper>() {
    override val entityClass: KClass<UserDepartment> get() = UserDepartment::class


    /**
     * 根据组织id获取所有用户
     * @param id 组织id
     */
    fun findByDepartmentId(id: Long): List<UserResult> {
        return queryWrapper.eq(UserDepartment::departmentId, id)
            .list().map { it.userId }
            .let { userService.getResultByIds(it) }
    }

    /**
     * 批量保存用户组织关系
     */
    @Transactional(rollbackFor = [Exception::class])
    fun batchSave(param: BatchSaveParam): Boolean {
        val data = queryWrapper.eq(UserDepartment::departmentId, param.departmentId).list()
        val oldUserIds = data.map { it.userId }
        val removeUserIds = oldUserIds - param.userIds.toSet()
        data.filter { it.userId in removeUserIds }.map { it.id!! }.also {
            if (it.isNotEmpty()) {
                removeByIds(it)
            }
        }

        (param.userIds - oldUserIds.toSet()).map {
            UserDepartment(
                departmentId = param.departmentId,
                userId = it
            )
        }.also {
            if (it.isNotEmpty()) {
                saveBatch(it)
            }
        }

        return true
    }

    /**
     * 根据组织id删除用户组织关系
     */
    @Transactional(rollbackFor = [Exception::class])
    fun removeByDepartmentId(id: Serializable) = remove(queryWrapper.eq(UserDepartment::departmentId, id))

    /**
     * 根据组织id查询用户
     */
    fun getUsersByDepartmentId(lng: Long) = queryWrapper.eq(UserDepartment::departmentId, lng).list()
}