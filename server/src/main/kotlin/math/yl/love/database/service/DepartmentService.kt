package math.yl.love.database.service

import math.yl.love.common.mybatis.BaseService
import math.yl.love.configuration.exception.BizException
import math.yl.love.database.domain.entity.Department
import math.yl.love.database.domain.result.department.DepartmentDetail
import math.yl.love.database.domain.result.department.TreeResult
import math.yl.love.database.mapper.DepartmentMapper
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.io.Serializable
import kotlin.reflect.KClass

@Service
@Transactional(readOnly = true)
class DepartmentService(
    private val userDepartmentService: UserDepartmentService
): BaseService<Department, DepartmentMapper>() {
    override val entityClass: KClass<Department> get() = Department::class

    /**
     * 返回部门树结果
     */
    fun tree(): TreeResult {
        val parents = queryWrapper.isNull(Department::parentId).list()
        if (parents.size != 1) {
            throw BizException("部门树根节点数量不正确!!")
        }

        return TreeResult(parents[0].id.toString(), parents[0].name, listOf()).apply {
            children = getChildren(this)
        }
    }

    fun getChildren(parent: TreeResult) : List<TreeResult> {
        val children = queryWrapper.eq(Department::parentId, parent.key).list()
        return children.map {
            TreeResult(it.id.toString(), it.name, listOf()).apply {
                this.children = getChildren(this)
            }
        }
    }

    /**
     * 根据id返回组织详情
     * @param id 组织id
     */
    fun detail(id: Long): DepartmentDetail {
        val department = getById(id) ?: throw BizException("组织不存在!!")
        return DepartmentDetail(
            department.id!!,
            department.name,
            department.parentId,
            department.createTime!!,
            userDepartmentService.findByDepartmentId(id)
        )
    }


    @Transactional(rollbackFor = [Exception::class])
    override fun removeById(id: Serializable): Boolean {
        val child = queryWrapper.eq(Department::parentId, id).list()
        if (child.isNotEmpty()) {
            throw BizException("该组织下有子组织，无法删除!!!")
        }
        return super.removeById(id) && userDepartmentService.removeByDepartmentId(id)
    }
}