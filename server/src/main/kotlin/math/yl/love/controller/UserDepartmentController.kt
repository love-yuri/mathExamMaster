package math.yl.love.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import math.yl.love.common.base.R
import math.yl.love.common.mybatis.BaseController
import math.yl.love.controller.UserController.PageParam
import math.yl.love.database.domain.entity.UserDepartment
import math.yl.love.database.domain.params.userDepartment.BatchSaveParam
import math.yl.love.database.mapper.UserDepartmentMapper
import math.yl.love.database.service.UserDepartmentService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user/department")
@Tag(name = "用户-组织关联表")
class UserDepartmentController: BaseController<UserDepartment, UserDepartmentMapper, UserDepartmentService>() {

    @PostMapping("batch/save")
    @Operation(summary = "批量保存")
    fun pageSimple(@RequestBody param: BatchSaveParam) = R.success(baseService.batchSave(param))
}