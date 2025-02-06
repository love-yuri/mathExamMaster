package math.yl.love.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import math.yl.love.common.base.R
import math.yl.love.common.mybatis.BaseController
import math.yl.love.database.domain.entity.Department
import math.yl.love.database.domain.params.examPage.ReleasePageParam
import math.yl.love.database.mapper.DepartmentMapper
import math.yl.love.database.service.DepartmentService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/department")
@Tag(name = "组织-部门表")
class DepartmentController: BaseController<Department, DepartmentMapper, DepartmentService>() {
    @GetMapping("tree")
    @Operation(summary = "获取部门树")
    fun tree() = R.success(baseService.tree())

    @PostMapping("detail")
    @Operation(summary = "查找详细信息")
    fun generate(@RequestBody id: Long) = R.success(baseService.detail(id))

    @PostMapping("owner/departments")
    @Operation(summary = "获取当前用户管理的班级")
    fun ownerDepartments() = R.success(baseService.ownerDepartments())
}